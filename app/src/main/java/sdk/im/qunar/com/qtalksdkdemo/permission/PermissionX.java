package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;
import android.os.Build;

import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class PermissionX {

    private final       String TAG_SOON_PERMISSION_FRAGMENT = "TAG_SOON_PERMISSION_FRAGMENT";
    public final static int    REQUEST_CODE                 = 9001;

    private boolean               hasDeniedPermission = false;
    private PermissionFragment    mSoonPermissionFragment;
    private PermissionTask        mCurrentTask;
    private Queue<PermissionTask> mPermissionTaskQueue;


    @IntDef({SEQUENCE, INTERCEPT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CheckStrategy {}

    public static final int SEQUENCE = 1;//按照顺序检查权限
    public static final int INTERCEPT = 2;//按照顺序检查权限，拦截模式
    private int checkStrategy = INTERCEPT;

    private PermissionX(FragmentActivity activity, FragmentManager manager) {
        findSoonPermissionFragment(activity, manager);
        /**
         * 默认拦截模式
         */
        checkStrategy(INTERCEPT);
    }

    public static PermissionX with(FragmentActivity activity) {
        return new PermissionX(activity, activity.getSupportFragmentManager());
    }

    public static PermissionX with(Fragment fragment) {
        if (fragment == null) {
            return new PermissionX(null, null);
        } else {
            return new PermissionX(fragment.getActivity(), fragment.getChildFragmentManager());
        }
    }

    private void findSoonPermissionFragment(FragmentActivity activity, FragmentManager manager) {
        if (activity != null && manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG_SOON_PERMISSION_FRAGMENT);
            if (fragment == null) {
                mSoonPermissionFragment = new PermissionFragment();
                manager.beginTransaction().add(mSoonPermissionFragment, TAG_SOON_PERMISSION_FRAGMENT).commitAllowingStateLoss();
                manager.executePendingTransactions();
            } else if (fragment instanceof PermissionFragment) {
                mSoonPermissionFragment = (PermissionFragment) fragment;
            }
        }
    }

    //    targetSDK = 29, 默认开启Scoped Storage, 但可通过在manifest里添加requestLegacyExternalStorage = true关闭;
    //    targetSDK < 29, 默认不开启Scoped Storage, 但可通过在manifest里添加requestLegacyExternalStorage = false打开;
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {//未适配存储权限
            if (permissions == null || permissions.length == 0) {
                return true;
            } else if (permissions.length == 1 && Permission.READ_PHONE_STATE.equals(permissions[0])) {
                return true;
            }else {
                return AndPermission.hasPermissions(context, permissions);
            }
        } else {
            return AndPermission.hasPermissions(context, permissions);
        }
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {//适配存储权限
//            if (permissions == null || permissions.length == 0) {
//                return true;
//            } else if (permissions.length == 1 && Permission.READ_PHONE_STATE.equals(permissions[0])) {
//                return true;
//            } else if (permissions.length ==2 && Permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0]) && Permission.READ_EXTERNAL_STORAGE.equals(permissions[1])){
//                return true;
//            }else {
//                return AndPermission.hasPermissions(context, permissions);
//            }
//        } else {
//            return AndPermission.hasPermissions(context, permissions);
//        }
    }

    private void initPermissionGroupList() {
        if (mPermissionTaskQueue == null) {
            mPermissionTaskQueue = new ConcurrentLinkedQueue<>();
        }
    }

    /**
     * 设置检查模式
     * @return
     */
    public PermissionX checkStrategy(@CheckStrategy int strategy){
        switch (strategy){
            case SEQUENCE:
            case INTERCEPT:
                checkStrategy = strategy;
                break;
        }
        return this;
    }

    public boolean isSequenceStrategy(){
        return checkStrategy == SEQUENCE;
    }

    public boolean isInterceptStrategy(){
        return checkStrategy == INTERCEPT;
    }

    public PermissionTask getCurrentTask() {
        return mCurrentTask;
    }

    public PermissionX setPermissionListener(OnPermissionListener listener) {
        if (mSoonPermissionFragment != null) {
            mSoonPermissionFragment.setTargetListener(listener);
        }
        return this;
    }


    public void restartCurrentTask() {
        if (mCurrentTask != null && mPermissionTaskQueue != null) {
            mPermissionTaskQueue.add(mCurrentTask);
            start();
        }
    }

    /**
     * 开启检查权限
     */
    public void start() {
        if (mSoonPermissionFragment != null) {
            if (mPermissionTaskQueue != null) {
                if (mCurrentTask != null && !hasDeniedPermission) {
                    if (!PermissionX.hasPermissions(mSoonPermissionFragment.getContext(), mCurrentTask.permissionGroup())) {
                        hasDeniedPermission = true;
                    }
                }
                mCurrentTask = mPermissionTaskQueue.poll();
                if (mCurrentTask != null) {
                    mSoonPermissionFragment.setSoonPermission(this);
                    mSoonPermissionFragment.requestPermission();
                } else {
                    if (hasDeniedPermission) {
                        mSoonPermissionFragment.getPermissionOptional().onDenied();
                    } else {
                        mSoonPermissionFragment.getPermissionOptional().onGranted();
                    }
                    removeSoonPermissionFragment();
                }
            } else {
                removeSoonPermissionFragment();
            }
        }
    }

    private void removeSoonPermissionFragment() {
        if (mSoonPermissionFragment != null) {
            mSoonPermissionFragment.getFragmentManager().beginTransaction().remove(mSoonPermissionFragment).commitAllowingStateLoss();
        }
    }

    /*********************************************************************/

    private PermissionX addPermissionTask(PermissionTask task) {
        initPermissionGroupList();
        boolean have = false;
        for (PermissionTask permissionTask : mPermissionTaskQueue) {
            if (task.getClass().getSimpleName().equals(permissionTask.getClass().getSimpleName())) {
                have = true;
                break;
            }
        }
        if (!have) {
            mPermissionTaskQueue.add(task);
        }
        return this;
    }

    public PermissionX addPermissionTask(Class<? extends PermissionTask> clazz) {
        try {
            PermissionTask task = clazz.newInstance();
            if (task != null) {
                addPermissionTask(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设备状态权限(Android10无法获取到该权限)
     *
     * @return
     */
    @Deprecated
    public PermissionX readPhoneState() {
        return addPermissionTask(PermissionReadPhoneState.class);
    }

    /**
     * 定位权限
     *
     * @return
     */
    public PermissionX location() {
        return addPermissionTask(new PermissionLocation());
    }

    /**
     * 存储权限
     *
     * @return
     */
    @Deprecated
    public PermissionX storage() {
        return addPermissionTask(new PermissionStorage());
    }

    /**
     * 相机权限
     *
     * @return
     */
    public PermissionX camera() {
        return addPermissionTask(new PermissionCamera());
    }

    /**
     * 联系人权限
     *
     * @return
     */
    public PermissionX contacts() {
        return addPermissionTask(new PermissionContacts());
    }


    /**
     * 录音权限
     *
     * @return
     */
    public PermissionX audio() {
        return addPermissionTask(new PermissionAudio());
    }

    /*********************************************************************/
}

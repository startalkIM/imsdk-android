package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import androidx.fragment.app.Fragment;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * 说明：SoonPermissionFragment
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019/6/3 18:16
 * <p/>
 * 版本：verson 1.0
 */
public class PermissionFragment extends Fragment {

    private PermissionX        mSoonPermission;
    private PermissionOptional mPermissionOptional = new PermissionOptional();

    private Rationale<List<String>> mRationale = new Rationale<List<String>>() {
        @Override
        public void showRationale(Context context, List<String> data, RequestExecutor executor) {
            executor.execute();
        }
    };

    private Action<List<String>> deniedAction  = new Action<List<String>>() {
        @Override
        public void onAction(List<String> data) {
            if (mPermissionOptional != null){
                mPermissionOptional.onOptionalDenied(getActivity(), PermissionFragment.this);
            }
        }
    };
    private Action<List<String>> grantedAction = new Action<List<String>>() {
        @Override
        public void onAction(List<String> data) {
            if (mSoonPermission != null){
                mSoonPermission.start();
            }
        }
    };

    public PermissionFragment(){}

    public void setTargetListener(OnPermissionListener listener){
        if (mPermissionOptional != null){
            mPermissionOptional.setTarget(listener);
        }
    }

    public PermissionOptional getPermissionOptional() {
        return mPermissionOptional;
    }

    public void setSoonPermission(PermissionX soonPermission){
        mSoonPermission = soonPermission;
    }

    public void requestPermission(){
        if (mSoonPermission != null){
            if (PermissionX.hasPermissions(getActivity(), mSoonPermission.getCurrentTask().permissionGroup())){
                mSoonPermission.start();
            }else {
                AndPermission.with(this)
                        .runtime()
                        .permission(mSoonPermission.getCurrentTask().permissionGroup())
                        .rationale(mRationale)
                        .onDenied(deniedAction)
                        .onGranted(grantedAction)
                        .start();
            }
        }
    }

    public PermissionX getSoonPermission(){
        return mSoonPermission;
    }

    public boolean hasAlwaysDenied(Context context) {
        return AndPermission.hasAlwaysDeniedPermission(context, mSoonPermission.getCurrentTask().permissionGroup());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionX.REQUEST_CODE){
            PermissionTask task = mSoonPermission.getCurrentTask();
            if (task != null){
                if (PermissionX.hasPermissions(getContext(), task.permissionGroup())){
                    mSoonPermission.restartCurrentTask();
                }else {
                    if (!isMeizu()){
                        if (mPermissionOptional != null){
                            mPermissionOptional.onOptionalDenied(getActivity(), PermissionFragment.this);
                        }
                    }
                }
            }
        }
    }

    private boolean isMeizu(){
        return Build.MANUFACTURER.toLowerCase().contains("meizu");
    }

    public void showDeniedDialog(){
        DialogAlert.create(getContext())
                .setCancel(false)
                .setMessage(mSoonPermission.getCurrentTask().deniedMessage())
                .setCancelText(IPermissionDialogDeploy.CANCEL)
                .setCancelListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mPermissionOptional != null){
                            mPermissionOptional.onCancel();
                        }
                    }
                })
                .setConfirmText(IPermissionDialogDeploy.TO_SETTING)
                .setConfirmListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AndPermission.with(PermissionFragment.this)
                                .runtime()
                                .setting()
                                .start(PermissionX.REQUEST_CODE);
                    }
                })
                .show();
    }
}

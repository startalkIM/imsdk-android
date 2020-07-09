package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

/**
 * 说明：PermissionOptional
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019/6/4 10:53
 * <p/>
 * 版本：verson 1.0
 */
public class PermissionOptional {

    private OnPermissionListener mTarget;

    public PermissionOptional(){}

    public void setTarget(OnPermissionListener listener){
        this.mTarget = listener;
    }

    public boolean onOptionalDenied(final Context context,final PermissionFragment fragment) {
        if (fragment != null){
            PermissionTask task = fragment.getSoonPermission().getCurrentTask();
            if (task != null){
                if (!task.deniedPermission(context,fragment)){
                    if (fragment.getSoonPermission().isSequenceStrategy()){
                        if (fragment.hasAlwaysDenied(context)){
                            fragment.showDeniedDialog();
                        }else {
                            fragment.getSoonPermission().start();
                        }
                    }else if (fragment.getSoonPermission().isInterceptStrategy()){
                        if (task.isRequestAlways() || task.mustHave()){
                            if (fragment.hasAlwaysDenied(context)){
                                fragment.showDeniedDialog();
                            }else {
                                fragment.requestPermission();
                            }
                        }else {
                            if (fragment.hasAlwaysDenied(context)){
                                fragment.showDeniedDialog();
                            }else {
                                fragment.getPermissionOptional().onDenied();
                            }
                        }
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public void onDenied(){
        if (mTarget != null){
            mTarget.onDenied();
        }
    }

    public void onGranted() {
        if (mTarget != null){
            mTarget.onGranted();
        }
    }

    public void onCancel() {
        if (mTarget != null){
            mTarget.onCancel();
        }
    }

}

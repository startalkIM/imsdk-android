package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

import com.yanzhenjie.permission.runtime.Permission;

/**
 * 说明：PermissionReadPhoneState(Android10废弃这个权限)
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019-06-19 13:43
 * <p/>
 * 版本：verson 1.0
 */
@Deprecated
public class PermissionReadPhoneState extends AbsPermissionTask{

    public PermissionReadPhoneState(){
        setRequestAlways();
    }

    @Override
    public String[] permissionGroup() {
        return new String[]{Permission.READ_PHONE_STATE};
    }

    @Override
    public String deniedMessage() {
        return IPermissionDialogDeploy.MSG_READ_PHONE_STATE;
    }

    @Override
    public boolean mustHave() {
        return true;
    }

    /**
     * 是否有获取设备状态权限
     * @param context
     * @return
     */
    public static boolean havePermission(Context context){
        return PermissionX.hasPermissions(context, Permission.READ_PHONE_STATE);
    }
}

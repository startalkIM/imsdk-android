package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

import com.yanzhenjie.permission.runtime.Permission;

/**
 * 说明：PermissionLocation
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019-06-19 13:43
 * <p/>
 * 版本：verson 1.0
 */
public class PermissionLocation extends AbsPermissionTask{

    @Override
    public String[] permissionGroup() {
        return new String[]{Permission.ACCESS_COARSE_LOCATION,Permission.ACCESS_FINE_LOCATION};
    }

    @Override
    public String deniedMessage() {
        return IPermissionDialogDeploy.MSG_LOCATION;
    }

    public static boolean havePermission(Context context){
        return PermissionX.hasPermissions(context, Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION);
    }
}

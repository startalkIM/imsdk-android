package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

import com.yanzhenjie.permission.runtime.Permission;

/**
 * 说明：PermissionAudio
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019-06-19 13:43
 * <p/>
 * 版本：verson 1.0
 */
public class PermissionAudio extends AbsPermissionTask{

    @Override
    public String[] permissionGroup() {
        return new String[]{Permission.RECORD_AUDIO};
    }

    @Override
    public String deniedMessage() {
        return IPermissionDialogDeploy.MSG_AUDIO;
    }

    public static boolean havePermission(Context context){
        return PermissionX.hasPermissions(context, Permission.RECORD_AUDIO);
    }
}

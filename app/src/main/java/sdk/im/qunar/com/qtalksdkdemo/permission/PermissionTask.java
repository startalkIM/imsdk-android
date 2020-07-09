package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

/**
 * 说明：PermissionTask
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019-06-19 13:39
 * <p/>
 * 版本：verson 1.0
 */
public interface PermissionTask {

    /**
     * 拒绝权限
     * @param context
     * @param fragment
     * @return
     */
    boolean deniedPermission(final Context context, final PermissionFragment fragment);

    /**
     * 权限组
     * @return
     */
    String[] permissionGroup();

    /**
     * 是否必须获取权限
     * @return
     */
    @Deprecated
    boolean mustHave();

    /**
     * 拒绝提示内容
     * @return
     */
    String deniedMessage();

    boolean isRequestAlways();
    PermissionTask setRequestAlways();
}

package sdk.im.qunar.com.qtalksdkdemo.permission;


/**
 * 说明：OnPermissionListener
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019/4/23 17:12
 * <p/>
 * 版本：verson 1.0
 */
public interface OnPermissionListener {

    /**
     * 当用户拒绝某一项权限后回调
     */
    void onDenied();

    /**
     * 用户允许所有权限后回调
     */
    void onGranted();

    /**
     * 用户取消权限后回调
     */
    void onCancel();
}

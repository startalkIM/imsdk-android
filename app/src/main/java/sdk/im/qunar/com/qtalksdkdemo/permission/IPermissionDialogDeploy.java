package sdk.im.qunar.com.qtalksdkdemo.permission;


public class IPermissionDialogDeploy {
    public static String CANCEL               = "暂不";
    public static String TO_SETTING           = "去设置";
    public static String MSG_READ_PHONE_STATE = String.format("您未允许%s获取手机状态权限，您可在系统设置中开启", appName());
    public static String MSG_STORAGE          = String.format("您未允许%s获取存储权限，您可在系统设置中开启", appName());
    public static String MSG_CAMERA           = String.format("您未允许%s获取摄像头权限，您可在系统设置中开启", appName());
    public static String MSG_AUDIO            = String.format("您未允许%s获取录音权限，您可在系统设置中开启", appName());
    public static String MSG_CONTACTS         = String.format("您未允许%s获取通讯录权限，您可在系统设置中开启", appName());
    public static String MSG_LOCATION         = String.format("您未允许%s获取定位权限，您可在系统设置中开启", appName());


    /**
     * 说明：获取应用名称
     * @return
     */
    public static String appName(){
        return "";
    }
}

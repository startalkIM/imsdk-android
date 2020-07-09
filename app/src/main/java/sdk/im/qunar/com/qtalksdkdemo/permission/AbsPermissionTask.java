package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;

public abstract class AbsPermissionTask implements PermissionTask{

    private boolean request_permission_always = false;//总是请求权限，如果被拒绝就弹窗提示

    @Override
    public boolean deniedPermission(Context context, PermissionFragment fragment) {
        return false;
    }

    @Override
    public boolean mustHave() {
        return false;
    }

    @Override
    public boolean isRequestAlways() {
        return request_permission_always;
    }

    @Override
    public PermissionTask setRequestAlways() {
        request_permission_always = true;
        return this;
    }
}

package sdk.im.qunar.com.qtalksdkdemo.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import sdk.im.qunar.com.qtalksdkdemo.R;


/**
 * 说明：DialogAlert
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2019-06-18 13:41
 * <p/>
 * 版本：verson 1.0
 */
public class DialogAlert {

    public final Context mContext;

    private DialogAlert(Context context) {
        this.mContext = context;
    }

    public static DialogAlert create(Context context){
        return new DialogAlert(context);
    }

    public DialogInterface.OnClickListener confirmListener;
    public DialogInterface.OnClickListener cancelListener;
    public String                          confirmText;
    public String                          cancelText;
    public String                          title;
    public String                          message;
    public boolean                         cancel = true;

    public DialogAlert setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    public DialogAlert setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    public DialogAlert setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public DialogAlert setConfirmListener(DialogInterface.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }

    public DialogAlert setCancelListener(DialogInterface.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public DialogAlert setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogAlert setMessage(String message) {
        this.message = message;
        return this;
    }

    private String getTitle(){
        if (TextUtils.isEmpty(title)){
            return "提示";
        }else {
            return title;
        }
    }

    public String getMessage() {
        if (TextUtils.isEmpty(message)){
            return "";
        }else {
            return message;
        }
    }

    public void show(){
        if (mContext != null) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
            alertBuilder.setTitle(getTitle());
            alertBuilder.setMessage(getMessage());
            if (!TextUtils.isEmpty(confirmText)) {
                alertBuilder.setPositiveButton(confirmText, confirmListener);
            }
            if (!TextUtils.isEmpty(cancelText)) {
                alertBuilder.setNegativeButton(cancelText, cancelListener);
            }
            alertBuilder.setCancelable(cancel);
            alertBuilder.create().show();
        }
    }

}

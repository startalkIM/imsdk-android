package com.qunar.im.ui.schema;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.qunar.im.base.util.Constants;
import com.qunar.im.core.services.QtalkNavicationService;
import com.qunar.im.ui.activity.FontSizeActivity;
import com.qunar.im.ui.activity.IMBaseActivity;
import com.qunar.im.ui.activity.QunarWebActvity;

import java.util.Map;

/**
 * Created by hubin on 2018/4/10.
 */

public class QDressUpVcImpl implements QChatSchemaService {
    public final static QDressUpVcImpl instance = new QDressUpVcImpl();
    //这个方法后面都要返回false , 否则会出现白屏
    @Override
    public boolean startActivityAndNeedWating(final IMBaseActivity context, Map<String, String> map) {


        Intent intent = new Intent(context.getApplication(), FontSizeActivity.class);
        context.startActivity(intent);
        return false;
    }
}
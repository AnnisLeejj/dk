package com.annis.baselib.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.annis.baselib.R;

/**
 * @author Lee
 * @date 2018/12/17 12:41
 * @Description
 */
public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);
    }

    TextView tvMessage;

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_waiting);//loading的xml文件
        tvMessage = findViewById(R.id.tv_load_dialog);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }
}

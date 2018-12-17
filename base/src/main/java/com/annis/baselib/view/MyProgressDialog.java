package com.annis.baselib.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.dialog_waiting, null);
        setContentView(view);//loading的xml文件
        tvMessage = view.findViewById(R.id.tv_load_dialog);
        tvMessage.setText(message);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
//        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.45);
        attributes.height = (int) (getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.45);
        getWindow().setAttributes(attributes);
    }

    String message = "加载中...";

    public void setMessage(String message) {
        this.message = message;
    }
}

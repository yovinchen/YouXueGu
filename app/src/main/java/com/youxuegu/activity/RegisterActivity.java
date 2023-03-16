package com.youxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youxuegu.R;
import com.youxuegu.utils.UtilsHelper;

/**
 * @author YoVinchen
 */
public class RegisterActivity extends AppCompatActivity {

    //标题栏与返回按钮
    private TextView tv_mian_title, tv_back;
    //标题栏布局
    private RelativeLayout rl_title_bar;
    //注册按钮
    private Button btn_register;
    //用户名密码二次密码输入框
    private EditText et_user_name, et_psw, et_psw_again;
    private String userName, psw, pswAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void init() {
        tv_mian_title = findViewById(R.id.tv_main_title);
        tv_mian_title.setText("注册");
        tv_back = findViewById(R.id.tv_back);
        rl_title_bar = findViewById(R.id.title_bar);
        //设置标题栏的背景颜色为透明
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);

        //返回按钮的点击事件
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        //注册按钮的点击事件
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "输入两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if (psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "输入两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if (UtilsHelper.isExistUserName(RegisterActivity.this,userName)) {
                    Toast.makeText(RegisterActivity.this, "此用户名已存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把用户名和密码保存到SharedPreferences文件中
                    UtilsHelper.saveUserInfo(RegisterActivity.this, userName, psw);
                    //注册成功后把用户名传递到LoginActivity中
                    Intent data =new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }

    /**
     * 获取界面控件中的注册信息
     */
    private void getEditString() {
        //获取注册界面中输入的用户名信息
        userName = et_user_name.getText().toString().trim();
        //获取注册界面中输入的密码信息
        psw = et_psw.getText().toString().trim();
        //获取注册界面中输入的二次密码信息
        pswAgain = et_psw_again.getText().toString().trim();
    }

}
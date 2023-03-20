package com.youxuegu.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youxuegu.R;
import com.youxuegu.utils.MD5Utils;
import com.youxuegu.utils.UtilsHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_main_title;
    private TextView tv_back, tv_register, tv_find_psw;
    private Button btn_login;
    private String userName, psw, spPsw;
    private EditText et_user_name, et_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back = findViewById(R.id.tv_back);
        tv_register = findViewById(R.id.tv_register);
        tv_find_psw = findViewById(R.id.tv_find_psw);
        btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        tv_back.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_find_psw.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //从注册界面回传过来的用户名
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                et_user_name.setText(userName);
                //设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //"返回"按钮的点击事件
            case R.id.tv_back:
                this.finish();
                break;
            //"立即注册"文本的点击事件
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                break;
            //"找回密码"文本的点击事件
            case R.id.tv_find_psw:
                //跳转到找回密码界面
                //Intent findPswIntent = new Intent(LoginActivity.this, FindPswActivity.class);
                //startActivity(findPswIntent);
                break;
            //"登录"按钮的点击事件
            case R.id.btn_login:
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                String md5Psw = MD5Utils.md5(psw);
                //根据用户名读取系统中密码
                spPsw = UtilsHelper.readPsw(LoginActivity.this, userName);
                if (TextUtils.isEmpty(userName)) {
                    //用户名为空
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(spPsw)) {
                    //用户名未注册
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    //密码未输入
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((!TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    //密码输入错误
                    Toast.makeText(LoginActivity.this, "输入的密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                } else if (md5Psw.equals(spPsw)) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态和登录的用户名
                    UtilsHelper.saveLoginStatus(LoginActivity.this, true, userName);
                    //把登录成功的状态传递到MainActivity中
                    Intent data = new Intent();
                    data.putExtra("isLogin", true);
                    setResult(RESULT_OK, data);
                    LoginActivity.this.finish();
                }
                break;
        }
    }
}
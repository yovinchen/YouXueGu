package com.youxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * @author YoVinchen
 */
public class UtilsHelper {
    /**
     * 判断SharedPreferences文件中是否存在要保存的用户名
     */
    public static boolean isExistUserName(Context context, String userName) {
        boolean has_userName = false;
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        if (!TextUtils.isEmpty(spPsw)) {
            has_userName = true;
        }
        return has_userName;
    }

    /**
     * 将密码用MD5加密
     */
    public static void saveUserInfo(Context context, String userName, String psw) {
        //将密码用MD5加密
        String md5Psw = MD5Utils.md5(psw);
        //获取SharedPreferences类的对象sp
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        //获取编辑器对象editor
        SharedPreferences.Editor editor = sp.edit();
        //将用户名和密码封装到编辑器对象editor中
        editor.putString(userName, md5Psw);
        editor.commit();//提交保存信息
    }

    /**
     * 根据用户名在文件loginInfo中返回密码信息
     */
    public static String readPsw(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        return spPsw;
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences文件中
     */
    public static void saveLoginStatus(Context context, boolean status, String userName) {
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }
}

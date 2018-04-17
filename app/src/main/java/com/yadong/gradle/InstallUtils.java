package com.yadong.gradle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @packInfo:com.yadong.gradle
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/12
 * Time: 14:22
 */

public class InstallUtils {
    private Activity mActivity;


    public InstallUtils(Activity activity) {
        mActivity = activity;

    }

//    public void installApk(final String url, final String md5) {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    File file = DownLoadUtils.getFileFromServer(url);
//
//                    //md5检验
//                    String fileMD5 = CheckSum.getFileMD5(file);
//                    if (fileMD5.equals(md5)){
//                        slientInstall(file);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }.start();
//    }



    private static final String KEY_TAG = "InstallUtils";
    /**
     * 静默安装
     * 如果没有root权限，进行智能安装
     *
     * @param file
     * @return
     */
    public  boolean slientInstall(File file) {

        if (file.exists()) {
            //判断是否由root权限
            if (hasRootPerssion()){
                // 有root权限，利用静默安装实现
                return clientInstall(file);
            }else {
                // 无root权限，利用安装实现
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                mActivity.startActivity(intent);
                return true;
            }
        }else {
            return false;
        }
    }

    //静默卸载
    private void uninstallSlient() {
        String cmd = "pm uninstall " + mActivity.getPackageName();
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            //卸载也需要root权限
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.writeBytes("exit\n");
            os.flush();
            //执行命令
            process.waitFor();
            //获取返回结果
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //显示结果
    }


    /**
     * 静默安装
     */
    public  boolean clientInstall(File apkPath){
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("chmod 777 "+apkPath);
            PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("pm install -r "+apkPath.getPath());
//          PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }
        }
        return false;
    }



    /**
     * 判断手机是否有root权限
     */
    public static boolean hasRootPerssion(){
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 权限检查结果处理
     * @param value
     * @return
     */
    private static boolean returnResult(int value){
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }
}

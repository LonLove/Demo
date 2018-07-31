package com.example.a83776.netease.im.business;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.a83776.netease.DemoCache;
import com.example.a83776.netease.base.http.NimHttpClient;
import com.example.a83776.netease.base.util.log.LogUtil;
import com.example.a83776.netease.im.util.CheckSumBuilder;
import com.example.a83776.netease.im.util.IMServers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 网易云信直播Demo账号注册Http接口
 * <p/>
 * Created by huangjun on 2015/3/6.
 */
public class RegisterHttpClient {
    private static final String TAG = "RegisterHttpClient";

    // code
    private static final int RESULT_CODE_SUCCESS = 200;

    // api
    //private static final String API_NAME_REGISTER = "createDemoUser";
    private static final String API_NAME_REGISTER = "user/create.action";

    // header
    private static final String HEADER_KEY_APP_KEY = "Appkey";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_USER_AGENT = "User-Agent";
    //--
    private static final String HEADER_NONCE = "Nonce";
    private static final String HEADER_CURTIME = "CurTime";
    private static final String HEADER_CHECKSUM = "CheckSum";

    // request
    //    private static final String REQUEST_USER_NAME = "username";
    //    private static final String REQUEST_NICK_NAME = "nickname";
    //    private static final String REQUEST_PASSWORD = "password";
    //--
    private static final String REQUEST_USER_ACCID = "accid";//网易云通信ID
    private static final String REQUEST_USER_NAME = "name";//网易云通信ID昵称，最大长度64字符,用来PUSH推送时显示的昵称
    private static final String REQUEST_USER_PROPS = "props";//json属性，第三方可选填，最大长度1024字符
    private static final String REQUEST_USER_ICON = "icon";//网易云通信ID头像URL，第三方可选填，最大长度1024
    private static final String REQUEST_USER_TOKEN = "token";//网易云通信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回

    // result
    private static final String RESULT_KEY_RES = "code";
    private static final String RESULT_KEY_ERROR_MSG = "desc";


    public interface ContactHttpCallback<T> {
        void onSuccess(T t);

        void onFailed(int code, String errorMsg);
    }

    private static RegisterHttpClient instance;

    public static synchronized RegisterHttpClient getInstance() {
        if (instance == null) {
            instance = new RegisterHttpClient();
        }

        return instance;
    }

    private RegisterHttpClient() {
        NimHttpClient.getInstance().init(DemoCache.getContext());
    }


    /**
     * 向应用服务器创建账号（注册账号）
     * 由应用服务器调用WEB SDK接口将新注册的用户数据同步到云信服务器
     */
    public void register(String accid, String nickName, String password, String avatar, final ContactHttpCallback<Void> callback) {
        //String url = DemoServers.apiServer() + API_NAME_REGISTER;
        String url = IMServers.CREATE;
        //password = MD5.getStringMD5(password);
        try {
            nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        //String appSecret = "dad0ceba0d15";
        String appSecret = readAppSercet();
        //String nonce = "12345";
        String nonce = getRandomNonce(15);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_USER_AGENT, "nim_kocla_tv_android");
        headers.put(HEADER_KEY_APP_KEY, appKey);
        headers.put(HEADER_NONCE, nonce);
        headers.put(HEADER_CURTIME, curTime);
        headers.put(HEADER_CHECKSUM, checkSum);


        StringBuilder body = new StringBuilder();
        //        body.append(REQUEST_USER_NAME).append("=").append(account.toLowerCase()).append("&")
        //                .append(REQUEST_NICK_NAME).append("=").append(nickName).append("&")
        //                .append(REQUEST_PASSWORD).append("=").append(password);
        body.append(REQUEST_USER_ACCID).append("=").append(accid.toLowerCase()).append("&")
                .append(REQUEST_USER_NAME).append("=").append(nickName).append("&")
                .append(REQUEST_USER_ICON).append("=").append(avatar).append("&")
                //.append(REQUEST_USER_PROPS).append("=").append(xxxxx).append("&")
                .append(REQUEST_USER_TOKEN).append("=").append(password);
        String bodyString = body.toString();

        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, String errorMsg) {
                if (code != 0) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = " + errorMsg);
                    if (callback != null) {
                        callback.onFailed(code, errorMsg);
                    }
                    return;
                }

                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue(RESULT_KEY_RES);
                    if (resCode == RESULT_CODE_SUCCESS) {
                        callback.onSuccess(null);
                    } else {
                        String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }

    public void refreshToken(String accid, String password, final ContactHttpCallback<UpdateTokenResult> callback) {
        String url = IMServers.UPDATE;
        Map<String, String> headers = new HashMap<>(1);
        String appKey = readAppKey();
        String appSecret = readAppSercet();
        //String nonce = "12345";
        String nonce = getRandomNonce(15);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_USER_AGENT, "nim_kocla_tv_android");
        headers.put(HEADER_KEY_APP_KEY, appKey);
        headers.put(HEADER_NONCE, nonce);
        headers.put(HEADER_CURTIME, curTime);
        headers.put(HEADER_CHECKSUM, checkSum);

        StringBuilder body = new StringBuilder();
        body.append(REQUEST_USER_ACCID).append("=").append(accid.toLowerCase()).append("&")
                .append(REQUEST_USER_TOKEN).append("=").append(password);
        String bodyString = body.toString();

        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, String errorMsg) {
                if (code != 0) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = " + errorMsg);
                    if (callback != null) {
                        callback.onFailed(code, errorMsg);
                    }
                    return;
                }

                //
                String log = "[注册NIM]:更新密码成功(" + response + ")";
                Log.e(TAG, log);
                //

                try {
                    //JSONObject resObj = JSONObject.parseObject(response);
                    UpdateTokenResult result = JSON.parseObject(response, UpdateTokenResult.class);
                    int resCode = result.getCode();
                    if (resCode == RESULT_CODE_SUCCESS) {
                        callback.onSuccess(result);
                    } else {
                        String error = result.getDes();
                        callback.onFailed(resCode, error);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
                }
            }
        });
    }

    public static class UpdateTokenResult implements Serializable {

        /**
         * code : 200
         * info : {"token":"f7b65f960a6b9b1013a8ab552d5fb089","accid":"10018760"}
         */

        private int code;
        private InfoBean info;
        private String des;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * token : f7b65f960a6b9b1013a8ab552d5fb089
             * accid : 10018760
             */

            private String token;
            private String accid;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }
        }
    }

    private String readAppKey() {
        try {
            ApplicationInfo appInfo = DemoCache.getContext().getPackageManager()
                    .getApplicationInfo(DemoCache.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readAppSercet() {
        try {
            ApplicationInfo appInfo = DemoCache.getContext().getPackageManager()
                    .getApplicationInfo(DemoCache.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appSecret");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机字符串
     *
     * @param length 生成随机字符串的长度
     * @return
     */
    public static String getRandomNonce(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//含有字符和数字的字符串
        Random random = new Random();//随机类初始化
        StringBuffer sb = new StringBuffer();//StringBuffer类生成，为了拼接字符串

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}

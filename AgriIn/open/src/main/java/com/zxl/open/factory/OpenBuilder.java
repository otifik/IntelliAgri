package com.zxl.open.factory;

import android.app.Activity;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;



/**
 * @author zxl on 2018/8/25.
 *         discription:
 */

public class OpenBuilder {
    private Activity mActivity;

    public static String saveShare(Bitmap bitmap){
        return null;
    }

    public static OpenBuilder with(Activity activity){
        OpenBuilder openBuilder = new OpenBuilder();
        openBuilder.mActivity = activity;
        return openBuilder;
    }

    public TencentOperator useTencent(String appId) {
        Tencent tencent = Tencent.createInstance(appId, mActivity);
        return new TencentOperator(tencent);
    }

    public class TencentOperator{
        Tencent mTencent;

        public TencentOperator(Tencent tencent) {
            mTencent = tencent;
        }

        public Tencent login(IUiListener listener, Callback callback){
            int login = mTencent.login(mActivity, "all", listener);
            return mTencent;
        }
    }

    public WeiboOperator useWeibo(String appKey) {
        return new WeiboOperator(appKey);
    }

    public class WeiboOperator{
        String appKey;

        public WeiboOperator(String appKey) {
            this.appKey = appKey;
        }

        public SsoHandler login(WbAuthListener wbAuthListener){
            AuthInfo authInfo = new AuthInfo(mActivity, appKey, "https://api.weibo.com/oauth2/default.html", null);
            WbSdk.install(mActivity,authInfo);
            SsoHandler ssoHandler = new SsoHandler(mActivity);
            ssoHandler.authorize(wbAuthListener);
            return ssoHandler;
        }

    }

    public interface Callback {
        void onFailed();

        void onSuccess();
    }
}

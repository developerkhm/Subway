package tmaphot.skt.com.mysyruptable;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

/**
 * Created by home on 2018-05-26.
 */

public class SyrupWebViewClient extends WebViewClient {
    private Activity activity;
    private WebView target;
    private final String TAG = "syrup";
    public String mCallbackparam1, mCallbackparam2, mCallbackparam3;
    private String mRequestUrl = null;
    public final int REQ_KFTC = 1;

    public SyrupWebViewClient(Activity activity, WebView target) {
        this.activity = activity;
        this.target = target;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        //url="intent://TID=moncube20180531011257#Intent;scheme=ispmobile;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=kvp.jjy.MispAndroid320;end";
        //Log.d("shouldOverrid",url);

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {

            //Log.d("shouldOverrid2",url);
            Intent intent = null;
            //인텐트 정합성 체크
            try{

                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                //Log.d(TAG, "intent.getScheme = " + intent.getScheme());
                //Log.d(TAG, "intent.getDataString = " + intent.getDataString());

            }catch(URISyntaxException ex){

                //Log.e(TAG, "Bad URI " + url + ":" + ex.getMessage());
                return false;
            }

            try {

                Uri uri = Uri.parse(intent.getDataString());
                //Log.d(TAG, "Intent.ACTION_VIEW");
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;

            } catch (ActivityNotFoundException e) {
                //Log.e(TAG, "error ====> " + e.getMessage());
                //e.printStackTrace();
                //if (intent == null) return false;

                //if (handleNotFoundPaymentScheme(intent.getScheme())) return true;

                String packageName = intent.getPackage();

                //Log.e(TAG, "packageName ====> " +packageName);

                if (packageName != null) {
                    //Log.e(TAG, "packageName ====> " + packageName);
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    return true;
                }

                return false;
            }
        }

        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     *  KFTC앱(인터넷뱅킹)에서 callbackparam1,2,3를 돌려주지 않아서
     *  callbackparam1, 2, 3을 저장함.
     * @param reqParam
     */
    private void getCallbackparam(String reqParam){
        String[] arry = reqParam.split("&");

        for(int i=0; i<arry.length; i++){

            if(arry[i].startsWith("callbackparam1=")){
                mCallbackparam1 = arry[i].substring("callbackparam1=".length());
                //Log.d(TAG, "mCallbackparam1 == " + mCallbackparam1);

            }else if(arry[i].startsWith("callbackparam2=")){
                mCallbackparam2 = arry[i].substring("callbackparam2=".length());
                //Log.d(TAG, "mCallbackparam2 == " + mCallbackparam2);

            }else if(arry[i].startsWith("callbackparam3=")){
                mCallbackparam3 = arry[i].substring("callbackparam3=".length());
                //Log.d(TAG, "mCallbackparam3 == " + mCallbackparam3);

            }
        }
    }

    protected boolean handleNotFoundPaymentScheme(String scheme) {
        //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
        if (PaymentScheme.ISP.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
            return true;
        } else if (PaymentScheme.BANKPAY.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
            return true;
        }

        return false;
    }
    @Override
    public void onPageFinished(WebView view, String url) {

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }

            // 여기서 WebView의 데이터를 가져오는 작업을 한다.
            if (url.equals(MainActivity.ENTRY_URL)) {
                TelephonyManager mgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                String userPhone = mgr.getLine1Number();

                String script = "javascript:function afterLoad() {"
                        + "document.getElementById('userPhone').value = '" + userPhone + "';"
                        + "};";
                //+ "afterLoad();"
                //+ "__init();";
                view.loadUrl(script);
            }
        }catch(Exception e){}
    }
}

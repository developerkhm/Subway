package tmaphot.skt.com.mysyruptable;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by home on 2018-05-26.
 */

public class AndroidBridge {
    private Activity activity;
    public AndroidBridge(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public double getLatitudeValue(){
        return SplashActivity.dLatitude ;
    }

    @JavascriptInterface
    public double getLongitudeValue(){
        return SplashActivity.dLongitude ;
    }

    @JavascriptInterface
    public String getPhoneNumber(){
//        TelephonyManager mgr2 = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
//        String userPhone2 = mgr2.getLine1Number();
        return "" ;
    }
}

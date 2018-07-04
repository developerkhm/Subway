package tmaphot.skt.com.mysyruptable;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);  //추가 !!
    }
}

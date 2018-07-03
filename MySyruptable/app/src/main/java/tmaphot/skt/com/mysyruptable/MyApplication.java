package tmaphot.skt.com.mysyruptable;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);  //추가 !!
    }
}

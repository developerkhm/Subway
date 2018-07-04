package tmaphot.skt.com.mysyruptable.Common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import tmaphot.skt.com.mysyruptable.MainActivity;
import tmaphot.skt.com.mysyruptable.SplashActivity;

public class PermissionList implements  ActivityCompat.OnRequestPermissionsResultCallback{

    private Context mContext;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public PermissionList(Context context){
        mContext = context;
    }

    public boolean checkAndRequestPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionReadphone = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        int permissionReadphoneNumvers = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_NUMBERS);
        int permissionCallPhone = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        int permissionReadSMS = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS);
        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionReadphone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionReadphoneNumvers != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS);
        }
        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionReadSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }


        if (!listPermissionsNeeded.isEmpty()) { ActivityCompat.requestPermissions((SplashActivity)mContext,
                listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted Successfully. Write working code here.
                    ((SplashActivity)mContext).initStart();
                } else {
                    //You did not accept the request can not use the functionality.
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
                break;
        }
    }
}

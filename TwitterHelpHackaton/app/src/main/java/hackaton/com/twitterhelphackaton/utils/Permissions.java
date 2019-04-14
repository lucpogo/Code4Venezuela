package hackaton.com.twitterhelphackaton.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import hackaton.com.twitterhelphackaton.interfaces.PermissionsResponse;

/**
 * Created by ederdoski on 10/04/2018.
 */

public class Permissions {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermisionStatus(Activity act, String permission){
        if(act.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    public static void getSimplePermission(Activity act, String permision, final PermissionsResponse callback){
        Dexter.withActivity(act)
                .withPermission(permision)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        callback.onSuccess();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        callback.onDefuse();}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) { token.continuePermissionRequest();}
                }).check();
    }

}

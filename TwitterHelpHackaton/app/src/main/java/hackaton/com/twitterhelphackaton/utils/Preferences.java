package hackaton.com.twitterhelphackaton.utils;

import android.content.Context;
import android.content.SharedPreferences;

import hackaton.com.twitterhelphackaton.App;

/**
 * Created by Eder Dominguez on 24/10/18.
 */

public class Preferences {

    private static final String TAG = "ederdoski.toolbox";
    private static final String TYPE_MAP  = TAG+"_TYPE_MAP";

    //----------------------------- Set Preferences ---------------------------------------------

    public static void setHeaderAuthorization(String data){
        setValue(App.getAppContext(), TAG, data);
    }

    public static void setTypeMap(String data){
        setValue(App.getAppContext(),TYPE_MAP,data);
    }


    //----------------------------- Get Preferences ---------------------------------------------

    public static String getHeaderAuthorization(){
        return getStringValue(App.getAppContext(), TAG, Constants.NULL);
    }

    public static String getTypeMap(){
        return getStringValue(App.getAppContext(),TYPE_MAP,"Normal");
    }

    /*---------------------------------------------------------------------------*/

    private static void setValue(Context ctx, String key, String value){
        SharedPreferences prefs = getPreferences(ctx);
        prefs.edit().putString(key, value).apply();
    }

    private static void setValue(Context ctx, String key, int value){
        SharedPreferences prefs = getPreferences(ctx);
        prefs.edit().putInt(key, value).apply();
    }

    private static String getStringValue(Context ctx, String key, String value){
        SharedPreferences prefs = getPreferences(ctx);
        return prefs.getString(key, value);
    }
    private static int getIntValue(Context ctx, String key, int value){
        SharedPreferences prefs = getPreferences(ctx);
        return prefs.getInt(key, value);
    }

    private static SharedPreferences getPreferences(Context ctx){
        return ctx.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

}

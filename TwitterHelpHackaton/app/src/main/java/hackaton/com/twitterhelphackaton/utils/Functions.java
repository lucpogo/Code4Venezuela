package hackaton.com.twitterhelphackaton.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by Eder on 20-10-2016.
 */
public class Functions {

    public static void changeActivity(Context ctx, Class newActivity) {
        Intent mainIntent = new Intent().setClass(
                ctx, newActivity);
        ctx.startActivity(mainIntent);
    }

    public static void closeActivitys(Activity act, Class newClass) {
        Intent intent = new Intent(act.getApplicationContext(), newClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
    }

    public static void changeActivity(Context ctx, Class newActivity, String extra) {
        Intent mainIntent = new Intent().setClass(
                ctx, newActivity);
        mainIntent.putExtra(Constants.EXTRA, extra);
        ctx.startActivity(mainIntent);
    }

    public static void changeActivity(Context ctx, Class newActivity, String extra, String extra2) {
        Intent mainIntent = new Intent().setClass(
                ctx, newActivity);
        mainIntent.putExtra(Constants.EXTRA, extra);
        mainIntent.putExtra(Constants.EXTRA2, extra2);
        ctx.startActivity(mainIntent);
    }

    public static void changeActivity(Context ctx, Class newActivity, String[] extra) {
        Intent mainIntent = new Intent().setClass(
                ctx, newActivity);
        mainIntent.putExtra(Constants.EXTRA, extra);
        ctx.startActivity(mainIntent);
    }

    public static void changeActivity(Context ctx, Class newActivity, Integer extra) {
        Intent mainIntent = new Intent().setClass(
                ctx, newActivity);
        mainIntent.putExtra(Constants.EXTRA, extra);
        ctx.startActivity(mainIntent);
    }

    public static String getIntent(Activity act, Integer position) {
        return act.getIntent().getStringArrayExtra(Constants.EXTRA)[position];
    }

    public static String getIntent(Activity act) {
        return act.getIntent().getStringExtra(Constants.EXTRA);
    }

    public static String getIntent(Activity act, String field) {
        return act.getIntent().getStringExtra(field);
    }

    public static String[] getIntentArray(Activity act) {
        return act.getIntent().getStringArrayExtra(Constants.EXTRA) ;
    }

    public static void popupAct(Activity ctx, Class newActivity, String extra, Integer requestCode) {
        Intent mainIntent = new Intent(ctx, newActivity);
        mainIntent.putExtra(Constants.EXTRA, extra);
        ctx.startActivityForResult(mainIntent, requestCode);
    }

    public static void popupAct(Activity ctx, Class newActivity, Integer requestCode) {
        Intent mainIntent = new Intent(ctx, newActivity);
        ctx.startActivityForResult(mainIntent, requestCode);
    }

    public static void returnPoupParameter(Activity act, String extra) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("extra", extra);
        act.setResult(Activity.RESULT_OK, returnIntent);
        act.finish();
    }

    public static boolean isOnline(Context ctx){
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}

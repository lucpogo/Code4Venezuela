package hackaton.com.twitterhelphackaton.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Edermar Dominguez on 25/08/16.
 */

public class Map {

    private static GoogleMap myMap;

    private static Marker mark;

    private static MarkerOptions mapMarker;

    public Map(GoogleMap _myMap) {

        mapMarker = new MarkerOptions();
        mark = null;
        myMap = _myMap;

        typeMap(Preferences.getTypeMap());
    }

    public static GoogleMap getMap(){
        return myMap;
    }

    public static void moveListener(GoogleMap.OnCameraMoveStartedListener listener, GoogleMap.OnCameraIdleListener listener2){
        myMap.setOnCameraMoveStartedListener(listener);
        myMap.setOnCameraIdleListener(listener2);
    }

    public static void lockZoom(){
        myMap.setMaxZoomPreference(16.0f);
    }

    public  static void resetObjectMap(){
        if(myMap != null) {
            mark = null;
            myMap.clear();
        }
    }

    public  static void removeMark(Marker mark){
        if(mark != null) {
            mark.remove();
        }
    }

    public static void moveCamera(LatLng latLng, float zoom){
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public static void moveCamera(LatLng latLng){
        myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public static void moveMarket(LatLng latlng, Marker marker, String title){
        if(marker != null){
            marker.setPosition(latlng);
            marker.setTitle(title);
        }
    }

    private static Bitmap getMarkerBitmapFromView(ImageView mMarkerImageView, View view, @DrawableRes int resId) {

        mMarkerImageView.setImageResource(resId);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();

        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static Bitmap getMarkerBitmapFromView(ImageView mMarkerImageView, View view, Bitmap bitmap) {

        mMarkerImageView.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static Marker addMarker(LatLng latlng, float icon, String title){

        return myMap.addMarker(new MarkerOptions()
                .position(latlng)
                .icon(BitmapDescriptorFactory.defaultMarker(icon))
                .title(title));
    }

    public static Marker addMarker(LatLng latlng, int icon, String title){

        return myMap.addMarker(new MarkerOptions()
                .position(latlng)
                .icon(BitmapDescriptorFactory.fromResource(icon))
                .title(title));
    }

    public static void onClickMarker(final Activity act, final Marker marker, final Class toClass){

        if(marker != null) {
            marker.showInfoWindow();
            myMap.setOnInfoWindowClickListener(arg0 -> {
                if(Functions.isOnline(act)) {
                    Map.removeMark(marker);
                    Functions.changeActivity(act, toClass, "Travel");
                }
            });
        }
    }

    public static void updateCameraBearing(float bearing) {
        if (myMap != null) {
            CameraPosition camPos = CameraPosition
                    .builder(myMap.getCameraPosition())
                    .bearing(bearing)
                    .build();
            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
        }
    }

    public static double getDistance(LatLng LatLngA, LatLng LatLngB) {

        double distanceAct;

        Location locationA = new Location("A");

        Location locationB = new Location("B");

        locationA.setLatitude(LatLngA.latitude);
        locationA.setLongitude(LatLngA.longitude);

        locationB.setLatitude(LatLngB.latitude);
        locationB.setLongitude(LatLngB.longitude);

        distanceAct = locationA.distanceTo(locationB);

        return distanceAct;
    }

    public static void typeMap(String item) {
        switch (item) {
            case "Satellite":
                myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Normal":
                myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "None":
                myMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }


}

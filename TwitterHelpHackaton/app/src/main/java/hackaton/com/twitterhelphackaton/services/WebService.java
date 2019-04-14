package hackaton.com.twitterhelphackaton.services;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import hackaton.com.twitterhelphackaton.interfaces.HTTPResponse;
import hackaton.com.twitterhelphackaton.utils.Constants;


/**
 * Created by Eder on 20-10-2016.
 */

public class WebService {

    public static String url = "http://40.117.115.124:8080";

    private static final int TIME_OUT = 6000;

    private static final ArrayList<String> aKeys  = new ArrayList<>();
    private static final ArrayList<String> aValue = new ArrayList<>();

    private static final String TAG            = "API";
    private static final String TWEETS         = url + "/tweets/";
    private static final String QUERY          = url + "/querys";

    private static final String PARAMETER_DATA = "data";

    private static FutureCallback<Response<String>> setFutureCallback(final HTTPResponse callback) {

        return (e, result) -> {
            if (e == null) {

                int response = result.getHeaders().code();
                Log.e(TAG, response + " Header");

                if (response == 200 || response == 201 || response == 204) {
                    Log.e(TAG, result.getResult() + "");
                    callback.onSuccess(result.getResult());
                }

                if (response == 401 || response == 403 || response == 422) {
                    Log.e(TAG, result.getResult() + "");
                    callback.onForbidden(result.getResult());
                }

                if(response == 404 || response == 405){
                    Log.d(TAG, result.getResult());
                    callback.onError(result.getResult());
                }

                if (response != 200 && response != 201 && response != 204 && response != 401 && response != 403 && response != 404 && response != 405 && response!= 422){
                    Log.d(TAG, result.getResult());
                    callback.onError(result.getResult());
                }

            } else {
                Log.e(TAG, e + "");
                callback.onException(e);
            }
        };
    }

    //----------------------------------------- ION REQUEST ---------------------------------------------------

    private static void IonRequest(Activity ctx, String contenType, HTTPResponse callback, String url){
        Ion.with(ctx)
                .load(url)
                .setTimeout(TIME_OUT)
                .setHeader(Constants.CONTENT_TYPE, contenType)
                .setHeader(Constants.ACCEPT, Constants.APLICATION_JSON)
                .setHeader(Constants.LANGUAGE, "en")
                .asString()
                .withResponse().setCallback(setFutureCallback(callback));
        Logger.e(url);
    }

    private static void IonRequest(Activity ctx, String contenType, HTTPResponse callback, String url, JsonObject param){

        Ion.with(ctx)
                .load(url)
                .setTimeout(TIME_OUT)
                .setHeader(Constants.CONTENT_TYPE,contenType)
                .setHeader(Constants.ACCEPT,Constants.APLICATION_JSON)
                .setHeader(Constants.LANGUAGE, "en")
                .setJsonObjectBody(param)
                .asString()
                .withResponse().setCallback(setFutureCallback(callback));
        Logger.e(url);
    }

    private static void IonRequest(Activity ctx, String contenType, HTTPResponse callback, String url, JsonArray param){

        Ion.with(ctx)
                .load(url)
                .setTimeout(TIME_OUT)
                .setHeader(Constants.CONTENT_TYPE,contenType)
                .setHeader(Constants.ACCEPT,Constants.APLICATION_JSON)
                .setHeader(Constants.LANGUAGE, "en")
                .setJsonArrayBody(param)
                .asString()
                .withResponse().setCallback(setFutureCallback(callback));
        Logger.e(url);
    }

    private static void IonRequest(Activity ctx, String contenType, HTTPResponse callback, String url, String foo, String bar){

        Ion.with(ctx)
            .load(url)
            .setTimeout(TIME_OUT)
            .setHeader(Constants.CONTENT_TYPE,contenType)
            .setHeader(Constants.ACCEPT,Constants.APLICATION_JSON)
            .setBodyParameter(foo, bar)
            .asString()
            .withResponse().setCallback(setFutureCallback(callback));
        Logger.e(url);
    }

    private static JsonObject mainJson(JsonObject obj) {
        return obj;
    }

    private static JsonObject mainJson(String data) {
        JsonObject json = new JsonObject();
        json.addProperty(PARAMETER_DATA, data);
        Logger.e(String.valueOf(json));
        return json;
    }

    private static JsonObject mainJson(JsonArray obj) {
        JsonObject json = new JsonObject();
        json.add(PARAMETER_DATA, obj);
        Logger.e(String.valueOf(json));
        return json;
    }

    private static JsonArray setJsonArray(JsonObject data){
        JsonArray jArray = new JsonArray();
        jArray.add(data);
        Logger.e(String.valueOf(jArray));
        return jArray;
    }

    private static JsonObject setJsonBody(ArrayList aKeys, ArrayList aValue){
        JsonObject json = new JsonObject();

        for (int i=0; i < aKeys.size(); i++){
            json.addProperty(aKeys.get(i).toString(), aValue.get(i).toString());
        }

        Logger.e(String.valueOf(json));
        return json;
    }

    private static void clearArrays(){
        aKeys.clear();
        aValue.clear();
    }

    //----------------------------------------- ENCODE URL ---------------------------------------------------

    private static String encodeUrl(String url, String firstKey, String secondKey, String firstParameter, String secondParameter){

        return Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(firstKey, firstParameter)
                .appendQueryParameter(secondKey, secondParameter)
                .build().toString();
    }

    private static String encodeUrl(String url, String firstKey, String firstParameter){

        return Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(firstKey, firstParameter)
                .build().toString();
    }

    //-----------------------------------------  API  ---------------------------------------------------

    public static void getTweets(final Activity ctx, String query, HTTPResponse callback) {
        IonRequest(ctx, Constants.APLICATION_JSON, callback, TWEETS + query);
    }

    public static void getQuerys(final Activity ctx, HTTPResponse callback) {
        IonRequest(ctx, Constants.APLICATION_JSON, callback, QUERY);
    }
}


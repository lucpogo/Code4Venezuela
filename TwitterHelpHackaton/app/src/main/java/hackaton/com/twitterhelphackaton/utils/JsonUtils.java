package hackaton.com.twitterhelphackaton.utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {
    public static Object getFromJson(JSONObject item, String name){
        try {
            if(!item.isNull(name))
                return item.get(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromJson(JSONObject item, String name){
        try {
            if(!item.isNull(name))
                return item.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntFromJson(JSONObject item, String name){
        try {
            if(!item.isNull(name))
                return item.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean getBooleanFromJson(JSONObject item, String name){
        try {
            if(!item.isNull(name))
                return item.getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Object getFromJsonArray(JSONArray item, int position){
        try {
            if(!item.isNull(position))
                return item.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject getJObjectFromJsonArray(JSONArray item, int position){
        try {
            if(!item.isNull(position))
                return (JSONObject) item.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject parseJson(String json){
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONArray parseJsonArray(String json){
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONArray removeFromJsonArray(JSONArray json, String value){
        JSONArray newJson = new JSONArray();
        String current;
        for (int i = json.length()-1; i >= 0 ; i--) {
            current = (String) getFromJsonArray(json,i);
            if( !value.equals(current) ){
                newJson.put(current);
            }
        }
        return newJson;
    }
    public static JSONArray removeFromJsonArray(JSONArray json, int position){
        JSONArray newJson = new JSONArray();
        String current;
        for (int i = json.length()-1; i >= 0 ; i--) {
            current = (String) getFromJsonArray(json,i);
            if( position!=i ){
                newJson.put(current);
            }
        }
        return newJson;
    }
    public static void addToJson(JSONObject json, String key, String value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void addToJson(JSONObject json, String key, boolean value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void addToJson(JSONObject json, String key, Uri value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void addToJson(JSONObject json, String key, int value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void addToJson(JSONObject json, String key, Object value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static int searchJsonByField(JSONArray array, String field, String value){
        if(array==null)
            return -1;
        JSONObject item;
        for (int i = 0; i <array.length() ; i++) {
            item = (JSONObject) getFromJsonArray(array,i);
            if(getFromJson(item,field).equals(value))
                return i;
        }
        return -1;
    }
    public static int searchJsonByField(JSONArray array, String field, String value, int index){
        if(array==null)
            return -1;
        JSONObject item;
        for (int i = index+1; i <array.length() ; i++) {
            item = (JSONObject) getFromJsonArray(array,i);
            if(getFromJson(item,field).equals(value))
                return i;
        }
        return -1;
    }
    public static int searchJsonByField(JSONArray array, String field, boolean value){
        if(array==null)
            return -1;
        JSONObject item;
        for (int i = 0; i <array.length() ; i++) {
            item = (JSONObject) getFromJsonArray(array,i);
            if(getFromJson(item,field)==null)
                continue;
            if((boolean)getFromJson(item,field)==value)
                return i;
        }
        return -1;
    }
    public static int searchString(JSONArray array, String value){
        if(array==null)
            return -1;
        String item;
        for (int i = 0; i <array.length() ; i++) {
            item = (String) getFromJsonArray(array,i);
            if(item.equals(value))
                return i;
        }
        return -1;
    }

    public static List<String> getOnlyStrings(JSONArray items, String key){
        if(items==null)
            return null;
        List<String> values =  new ArrayList<String>();
        String item;
        for (int i = 0; i <items.length() ; i++) {
            item = (String) getFromJson( (JSONObject)getFromJsonArray(items,i),key);
            values.add(item);
        }
        return values;
    }
    public static CharSequence[] getOnlyStringsInCS(JSONArray items, String key){
        if(items==null)
            return null;
        CharSequence[] values =  new CharSequence[items.length()];
        String item;
        for (int i = 0; i <items.length() ; i++) {
            item = (String) getFromJson( (JSONObject)getFromJsonArray(items,i),key);
            values[i]=item;
        }
        return values;
    }
    public static boolean[] getOnlyBooleans(JSONArray items, String key){
        if(items==null)
            return null;
        boolean[] values =  new boolean[items.length()];
        Object item;
        boolean val;
        for (int i = 0; i <items.length() ; i++) {
            item = getFromJson( (JSONObject)getFromJsonArray(items,i),key);
            val = (item!=null) ? (boolean)item : false;
            values[i]=val;
        }
        return values;
    }

    public static ArrayList<String> getKeysFromJson(String data){

        ArrayList<String> aKeys = new ArrayList<>();
        JSONObject aux;
        aux = JsonUtils.parseJson(data);

        if(aux != null) {
            Iterator iteratorObj = aux.keys();
            while (iteratorObj.hasNext()) {
                String getJsonObj = (String) iteratorObj.next();
                aKeys.add(getJsonObj);
            }
        }

        return aKeys;
    }
}

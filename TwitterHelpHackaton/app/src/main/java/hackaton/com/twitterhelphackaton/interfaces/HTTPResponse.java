package hackaton.com.twitterhelphackaton.interfaces;

import android.util.Log;

public abstract class HTTPResponse {

    private static boolean debug = true;

    public void onSuccess(String data){};

    public void onForbidden(String data){
        if(debug)
            Log.e("HTTPResponse", "FORBIDDEN");
    }

    public void onException(Exception e){
        if(debug)
            Log.e("HTTPResponse", String.valueOf(e));
    }

    public void onError(String data){
        if(debug)
            Log.e("HTTPResponse",data);
    }

}

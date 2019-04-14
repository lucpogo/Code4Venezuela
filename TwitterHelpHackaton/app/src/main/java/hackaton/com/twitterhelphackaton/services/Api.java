package hackaton.com.twitterhelphackaton.services;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.google.gson.JsonArray;
import com.orhanobut.logger.Logger;

import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hackaton.com.twitterhelphackaton.R;
import hackaton.com.twitterhelphackaton.interfaces.BasicCallback;
import hackaton.com.twitterhelphackaton.interfaces.HTTPResponse;
import hackaton.com.twitterhelphackaton.utils.Dialogs;
import hackaton.com.twitterhelphackaton.utils.JsonUtils;

/**
 * Created by ederdoski on 13/03/2018.
 */

public class Api {

    private static Activity act;
    private static AlertDialog dialogLoad;
    private static AlertDialog basicDialog;

    public Api(Activity _act) {
        new Dialogs(act);
        act = _act;
    }

    private static void loading(boolean status){
        if(!act.isFinishing()) {
            if (status) {
                dialogLoad = Dialogs.loading();
                Dialogs.show(dialogLoad);
            } else {
                Dialogs.hide(dialogLoad);
            }
        }
    }

    private static AlertDialog setDialogInfo(int title, int message){

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        LayoutInflater inflater = act.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_standard, null);

        TextView btnNeutral = view.findViewById(R.id.btnNeutral);
        TextView txtTitle   = view.findViewById(R.id.txtTitle);
        TextView txtMessage = view.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);

        btnNeutral.setOnClickListener(view1 -> basicDialog.dismiss());

        builder.setView(view);
        return builder.create();
    }

    private static AlertDialog setDialogInfo(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        LayoutInflater inflater = act.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_standard, null);

        TextView btnNeutral = view.findViewById(R.id.btnNeutral);
        TextView txtTitle   = view.findViewById(R.id.txtTitle);
        TextView txtMessage = view.findViewById(R.id.txtMessage);

        txtTitle.setText(title);
        txtMessage.setText(message);

        btnNeutral.setOnClickListener(view1 -> basicDialog.dismiss());

        builder.setView(view);
        return builder.create();
    }

    private static void showErrors(JSONObject aux){
        ArrayList<String> aErrors;
        String title = JsonUtils.getStringFromJson(aux, "message");
        StringBuilder error = new StringBuilder();

        aErrors = JsonUtils.getKeysFromJson(JsonUtils.getStringFromJson(aux, "errors"));
        String dataError = JsonUtils.getStringFromJson(aux, "errors");
        aux = JsonUtils.parseJson(dataError);

        for (int i=0; i<aErrors.size(); i++) {
            dataError = JsonUtils.getStringFromJson(aux, aErrors.get(i));
            JSONArray jErrors = JsonUtils.parseJsonArray(dataError);

            for (int j=0; j<jErrors.length(); j++){
                if(!error.toString().equals("")) {
                    error.append("\n\n").append(JsonUtils.getFromJsonArray(jErrors, j).toString());
                }else{
                    error.append(JsonUtils.getFromJsonArray(jErrors, j).toString());
                }
            }
        }

        basicDialog = setDialogInfo(title, error.toString());
        Dialogs.show(basicDialog);
    }


    public static void getTweets(String query, BasicCallback callback){
        WebService.getTweets(act, query, new HTTPResponse() {
            @Override
            public void onException(Exception e) {
                super.onException(e);
                getTweets(query, callback);
            }

            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                callback.onSuccess(data);
            }

            @Override
            public void onError(String data) {
                super.onError(data);
                callback.onError();
            }

            @Override
            public void onForbidden(String data) {
                super.onForbidden(data);
                callback.onError();
            }
        });
    }

    public static void getQuerys(BasicCallback callback){
        WebService.getQuerys(act, new HTTPResponse() {
            @Override
            public void onException(Exception e) {
                super.onException(e);
                getQuerys(callback);
            }

            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                callback.onSuccess(data);
            }

            @Override
            public void onError(String data) {
                super.onError(data);
                callback.onError();
            }

            @Override
            public void onForbidden(String data) {
                super.onForbidden(data);
                callback.onError();
            }
        });
    }

}

package hackaton.com.twitterhelphackaton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackaton.com.twitterhelphackaton.adapters.QuerysAdapter;
import hackaton.com.twitterhelphackaton.models.Querys;
import hackaton.com.twitterhelphackaton.models.States;
import hackaton.com.twitterhelphackaton.utils.Functions;
import hackaton.com.twitterhelphackaton.utils.JsonUtils;

public class MainActivity extends TwitterHelp {

    ArrayList<Querys> aQuerys  = new ArrayList<>();

    @BindView(R.id.listQuerys) RecyclerView listQuerys;

    private void parseQuerys(){

        if(Functions.getIntent(this) != null || !Functions.getIntent(this).equals("")) {

            JSONArray data = JsonUtils.parseJsonArray(Functions.getIntent(this));

            for (int i=0; i < data.length(); i++) {

                if (i != 0){
                    i++;
                }

                String info     = JsonUtils.getFromJsonArray(data, i).toString();
                JSONObject aux  = JsonUtils.parseJson(info);

                if(i+1 != data.length()) {
                    String info2    = JsonUtils.getFromJsonArray(data, i+1).toString();
                    JSONObject aux2 = JsonUtils.parseJson(info2);

                    aQuerys.add(new Querys(String.valueOf(i), JsonUtils.getStringFromJson(aux, "query"),
                            JsonUtils.getStringFromJson(aux2, "query")));
                }else{
                    aQuerys.add(new Querys(String.valueOf(i), JsonUtils.getStringFromJson(aux, "query"), ""));
                }

            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);

        parseQuerys();

        QuerysAdapter adapter = new QuerysAdapter(MainActivity.this, aQuerys);
        LinearLayoutManager verticalLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        listQuerys.setLayoutManager(verticalLayout);
        listQuerys.setAdapter(adapter);

    }
}

package hackaton.com.twitterhelphackaton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackaton.com.twitterhelphackaton.adapters.BasicListAdapter;
import hackaton.com.twitterhelphackaton.models.States;
import hackaton.com.twitterhelphackaton.utils.Constants;
import hackaton.com.twitterhelphackaton.utils.Functions;
import hackaton.com.twitterhelphackaton.utils.JsonUtils;
import hackaton.com.twitterhelphackaton.utils.Map;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class Maps extends TwitterHelp implements OnMapReadyCallback {

    private Marker mark;

    ArrayList<States> aStates = new ArrayList<>();

    @BindView(R.id.listCountrys)    ListView listCountrys;
    @BindView(R.id.txtTextCountrys) TextView txtTextCountrys;
    @BindView(R.id.txtQuery)        TextView txtQuery;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Map.removeMark(mark);
        mark = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        new Map(googleMap);
        Map.resetObjectMap();

        parseDataMap();

        String strAux = getResources().getString(R.string.txt_states_affected);
        txtTextCountrys.setText(String.format(strAux, aStates.size()));
        txtQuery.setText("#"+Functions.getIntent(this,  Constants.EXTRA2));

        for (int i=0; i<aStates.size(); i++){

            CircleOptions circleOptions = new CircleOptions()
                    .center(aStates.get(i).getUbication())
                    .fillColor(Color.argb(117, 68, 138, 255))
                    .strokeColor(Color.argb(117, 68, 138, 255))
                    .radius(15000);

            Map.addMarker(aStates.get(i).getUbication(), Constants.MARKER_RED,aStates.get(i).getCity()+ ": " + aStates.get(i).getQuantity());
            googleMap.addCircle(circleOptions);

        }

        setListCountrysAffected(aStates);
        Map.moveCamera(aStates.get(0).getUbication(), 7);
    }

    private void setListCountrysAffected(ArrayList<States> aStates){

        listCountrys.setAdapter(new BasicListAdapter(this, R.layout.node_list_countrys, aStates) {
            @Override
            public void onItem(Object item, View view, int position) {
                TextView txtCountry   = view.findViewById(R.id.txtCountry);
                txtCountry.setText(((States) item).getCity());
            }
        });

        listCountrys.setOnItemClickListener((pariente, view, posicion, id) -> {
            States item = (States) pariente.getItemAtPosition(posicion);
            Map.moveCamera(item.getUbication(), 10);
        });
    }

    private void parseDataMap(){

        if(Functions.getIntent(this) != null || !Functions.getIntent(this).equals("")) {

            JSONArray data = JsonUtils.parseJsonArray(Functions.getIntent(this));

            for (int i=0; i < data.length(); i++) {

                String info    = JsonUtils.getFromJsonArray(data, i).toString();
                JSONObject aux = JsonUtils.parseJson(info);

                aStates.add(new States(String.valueOf(i), JsonUtils.getStringFromJson(aux, "count"),
                        JsonUtils.getStringFromJson(aux, "ubicacion"),
                        new LatLng(Float.parseFloat(JsonUtils.getStringFromJson(aux, "lat")), Float.parseFloat(JsonUtils.getStringFromJson(aux, "lon")))));

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_maps);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}

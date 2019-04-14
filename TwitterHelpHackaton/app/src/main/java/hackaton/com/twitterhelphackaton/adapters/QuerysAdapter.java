package hackaton.com.twitterhelphackaton.adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import hackaton.com.twitterhelphackaton.Maps;
import hackaton.com.twitterhelphackaton.R;
import hackaton.com.twitterhelphackaton.interfaces.BasicCallback;
import hackaton.com.twitterhelphackaton.models.Querys;
import hackaton.com.twitterhelphackaton.services.Api;
import hackaton.com.twitterhelphackaton.utils.Dialogs;
import hackaton.com.twitterhelphackaton.utils.Functions;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class QuerysAdapter extends RecyclerView.Adapter<QuerysAdapter.viewHolder> {

    AlertDialog dLoading;

    int colors[] = {R.color.blue_dark, R.color.blue_light, R.color.green, R.color.red, R.color.blue,
            R.color.orange, R.color.purple, R.color.green_dark};

    int colorsLenght = colors.length;
    int posiTemp     = 0;

    private Activity act;
    private List<Querys> queryList;

    public QuerysAdapter(Activity act, List<Querys> queryList) {
        this.act       = act;
        this.queryList = queryList;
        new Dialogs(act);
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView     txtQuery1;
        TextView     txtQuery2;
        LinearLayout lyQuery1;
        LinearLayout lyQuery2;

        viewHolder(View view) {
            super(view);
            txtQuery1       = view.findViewById(R.id.txtQuery1);
            txtQuery2       = view.findViewById(R.id.txtQuery2);
            lyQuery1        = view.findViewById(R.id.lyQuery1);
            lyQuery2        = view.findViewById(R.id.lyQuery2);
        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_querys, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {

        Querys aQuerys = queryList.get(position);

        holder.txtQuery1.setText(aQuerys.getQuery());
        chooseColor();
        holder.lyQuery1.setBackgroundColor(act.getResources().getColor(colors[posiTemp]));

        holder.lyQuery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLoading = Dialogs.loading();
                dLoading.show();

                Api.getTweets(aQuerys.getQuery(), new BasicCallback(){
                    @Override
                    public void onSuccess(String data) {
                        super.onSuccess(data);
                        dLoading.hide();
                        Functions.changeActivity(act, Maps.class, data, aQuerys.getQuery());
                    }

                    @Override
                    public void onError() {
                        super.onError();
                        dLoading.hide();
                    }
                });
            }
        });

        if(!aQuerys.getQuery2().equals("")) {
            holder.txtQuery2.setText(aQuerys.getQuery2());
            chooseColor();
            holder.lyQuery2.setBackgroundColor(act.getResources().getColor(colors[posiTemp]));

            holder.lyQuery2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dLoading = Dialogs.loading();
                    dLoading.show();

                    Api.getTweets(aQuerys.getQuery2(), new BasicCallback() {
                        @Override
                        public void onSuccess(String data) {
                            super.onSuccess(data);
                            dLoading.hide();
                            Functions.changeActivity(act, Maps.class, data, aQuerys.getQuery2());
                        }

                        @Override
                        public void onError() {
                            super.onError();
                            dLoading.hide();
                        }
                    });
                }
            });
        }else{
            holder.lyQuery2.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    private void chooseColor(){
        if(posiTemp >= colorsLenght-1){
            posiTemp = 0;
        }else{
            posiTemp++;
        }
    }

}

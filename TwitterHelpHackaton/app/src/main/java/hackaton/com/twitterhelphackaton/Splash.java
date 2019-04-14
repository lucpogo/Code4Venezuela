package hackaton.com.twitterhelphackaton;

import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import hackaton.com.twitterhelphackaton.interfaces.BasicCallback;
import hackaton.com.twitterhelphackaton.services.Api;
import hackaton.com.twitterhelphackaton.utils.Dialogs;
import hackaton.com.twitterhelphackaton.utils.Functions;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class Splash extends TwitterHelp {

    private void configureSplash(){

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {

                    if(Functions.isOnline(Splash.this)) {
                        Api.getQuerys(new BasicCallback() {
                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                Functions.changeActivity(Splash.this, MainActivity.class, data);
                                finish();
                            }

                            @Override
                            public void onError() {
                                super.onError();
                            }
                        });
                    }else{
                        Dialogs.setDialogInfo(R.string.txt_ups, R.string.txt_not_internet, new BasicCallback(){
                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                finish();
                            }
                        });
                    }

                });
            }
        };
        timer.schedule(task, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        ButterKnife.bind(this);

        configureSplash();
    }


}

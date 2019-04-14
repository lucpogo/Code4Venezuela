package hackaton.com.twitterhelphackaton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import hackaton.com.twitterhelphackaton.services.Api;
import hackaton.com.twitterhelphackaton.utils.Dialogs;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class TwitterHelp extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        new Api(this);
        new Dialogs(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Api(this);
        new Dialogs(this);
    }


}

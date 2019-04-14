package hackaton.com.twitterhelphackaton;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class App extends MultiDexApplication {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

        //---- Loggers

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    }

    public static Context getAppContext() {
        return context;
    }

}

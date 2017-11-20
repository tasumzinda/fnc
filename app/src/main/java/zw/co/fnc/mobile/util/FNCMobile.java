package zw.co.fnc.mobile.util;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;

/**
 * Created by User on 3/8/2017.
 */
public class FNCMobile extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this).create();
        ActiveAndroid.initialize(dbConfiguration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}

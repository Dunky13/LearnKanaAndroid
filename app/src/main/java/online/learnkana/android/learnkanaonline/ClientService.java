package online.learnkana.android.learnkanaonline;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Marc on 7/15/2016.
 */
public class ClientService extends Service {
    public static MainActivity mainActivity;
    public static String localStorageName;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

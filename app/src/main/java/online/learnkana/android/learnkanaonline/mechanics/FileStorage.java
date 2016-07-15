package online.learnkana.android.learnkanaonline.mechanics;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marc on 7/15/2016.
 */
public class FileStorage {
    public static String saveName = "LearnKana";
    public static void save(Context c, String JSONData)
    {
        TinyDB tinydb = new TinyDB(c);
        tinydb.putString(FileStorage.saveName, JSONData);
    }
    public static String load(Context c)
    {
        TinyDB tinydb = new TinyDB(c);
        return tinydb.getString(FileStorage.saveName);
    }
}

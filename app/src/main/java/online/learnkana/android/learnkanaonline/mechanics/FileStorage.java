package online.learnkana.android.learnkanaonline.mechanics;

import android.content.Context;
import android.content.SharedPreferences;

import online.learnkana.android.learnkanaonline.R;

/**
 * Created by Marc on 7/15/2016.
 */
public class FileStorage {
    public static void save(Context c, String JSONData)
    {
        TinyDB tinydb = new TinyDB(c);
        tinydb.putString(c.getString(R.string.preference_file_key), JSONData);
    }
    public static String load(Context c)
    {
        TinyDB tinydb = new TinyDB(c);
        return tinydb.getString(c.getString(R.string.preference_file_key));
    }
}

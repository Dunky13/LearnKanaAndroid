package online.learnkana.android.mechanics;

import android.content.Context;

import online.learnkana.android.R;

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

package example.johnleonardrada.dbsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DBHelper {
    public static final String KEY_ID = "_id";
    public static final String KEY_QUOTE = "quote";
    private static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = "QuotesDB";
    private static final String DATABASE_TABLE = "quotes";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table quotes (_id integer primary key autoincrement, "
                    + "quote text not null);";

    private final Context context;

    private Helper DBH;
    private SQLiteDatabase db;
    public DBHelper(Context ctx)
    {
        this.context = ctx;
        DBH = new Helper(context);
    }

    private static class Helper extends SQLiteOpenHelper
    {
        Helper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }//end of Helper Class

    //---opens the database---
    public DBHelper open() throws SQLException
    {
        db = DBH.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBH.close();
    }


    //---insert a quote into the database---
    public long insertQuote(String quote)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUOTE, quote);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular quotes---
    public boolean deleteQuote(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    //---retrieves all the quotes---
    public Cursor getAllQuotes()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ID,
                        KEY_QUOTE},
                null,
                null,
                null,
                null,
                null);
    }


    //---retrieves a particular quote---
    public Cursor getQuote(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                                KEY_ID,
                                KEY_QUOTE
                        },
                        KEY_ID + "=" + rowId,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates an quote---
    public boolean updateQuote(long rowId, String quote)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_QUOTE, quote);
        return db.update(DATABASE_TABLE, args,
                KEY_ID + "=" + rowId, null) > 0;
    }



}//end of DBHelper class

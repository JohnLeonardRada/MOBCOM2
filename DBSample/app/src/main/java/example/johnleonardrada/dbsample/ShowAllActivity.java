package example.johnleonardrada.dbsample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

//additional imports
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowAllActivity extends AppCompatActivity {
    final DBHelper db = new DBHelper(this);
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        listView = (ListView)findViewById(R.id.LvAll);

        //interact with the DB
        db.open();
        Cursor cursor =  db.getAllQuotes();
        cursor.moveToFirst();

        //extract records from cursor and move into stringArrayList
        while (cursor.isAfterLast()==false){
            String record = cursor.getString(0) + " " + cursor.getString(1);
            stringArrayList.add(record);
            cursor.moveToNext();
        }

        //associate ListView to an ArrayAdapter to populate it
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, stringArrayList);

        listView.setAdapter(arrayAdapter);
    }
}
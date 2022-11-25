package example.johnleonardrada.dbsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    final DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnAdd = (Button) findViewById(R.id.button1);
        Button btnShow = (Button) findViewById(R.id.button2);
        final EditText txtQuote = (EditText) findViewById(R.id.editText1);
        final EditText txtRow = (EditText) findViewById(R.id.editText2);

        OnClickListener add = new OnClickListener(){
            public void onClick(View view){
                db.open();
                long id;
                id = db.insertQuote(txtQuote.getText().toString());
                db.close();
            }
        };

        OnClickListener show = new OnClickListener(){
            public void onClick(View view){

                db.open();
                Cursor c = db.getQuote(Long.parseLong(txtRow.getText().toString()));
                if (c.moveToFirst()){
                    Toast.makeText(getApplicationContext(),
                            "id: " + c.getString(0) + "\n" +
                                    "Quote: " + c.getString(1) + "\n",
                            Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "No Quote!",
                            Toast.LENGTH_LONG).show();

                }

                db.close();
            }
        };

        btnAdd.setOnClickListener(add);
        btnShow.setOnClickListener(show);
    }

    public void doDelete(View view){
        //Toast.makeText(this,"Button Ok",Toast.LENGTH_SHORT).show();
        EditText deleteData = (EditText)findViewById(R.id.txtDelete);
        String s = deleteData.getText().toString();
        Long rowID = Long.parseLong(s);


        db.open();
        Boolean result = db.deleteQuote(rowID);
        db.close();

        if(result==true){
            Toast.makeText(this,"Delete Successful!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Nothing Deleted!",Toast.LENGTH_SHORT).show();
        }
    }

    public void doViewAll(View view){
        Intent intent = new Intent(getApplicationContext(), ShowAllActivity.class);
        startActivity(intent);
    }


}//end of class


package com.bikash.moneymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    ListView listView;
    private MemoAdapter memoAdapter;
    ArrayList<Memo> historyList;
    SQLiteDatabase myDatabase;
    TextView historyTextView;
    Double total = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.historyListView);
        myDatabase = this.openOrCreateDatabase("Cost", MODE_PRIVATE, null);
        memoAdapter = new MemoAdapter(this, R.layout.list_item, historyList);
        historyTextView = (TextView) findViewById(R.id.tatalinHistoryTextview);


        historyList.clear();


        /**
         * Retriving data from history table
         */

        try{
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS history (name VARCHAR, cost VARCHAR)");


            Cursor c = myDatabase.rawQuery("SELECT * FROM history", null);

            int nameIndex = c.getColumnIndex("name");
            int costIndex = c.getColumnIndex("cost");

            c.moveToFirst();
            while(c!=null){

                String name = c.getString(nameIndex);
                String costString = c.getString(costIndex);


                Double l = Double.valueOf(costString);
                Memo m = new Memo(name, l);

                historyList.add(m);

                c.moveToNext();
            }
        } catch (Exception e){

            e.printStackTrace();

            Log.i("Error", "Not found");

        }

        listView.setAdapter(memoAdapter);

        for(int i=0; i<historyList.size(); i++){
            total += historyList.get(i).getCost();
        }

        historyTextView.setText(Double.toString(total)+ " /=");



    }
}

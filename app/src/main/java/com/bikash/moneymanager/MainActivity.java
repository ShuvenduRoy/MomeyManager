package com.bikash.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDatabase;

    public static ArrayList<Memo> fixedCostArrayList;
    public static ArrayList<Memo> extraSpentArrayList;
    public static Double totalFixed;
    public static Double totalExtra;
    public static Double total;
    public static Map<Integer, String> monthMap = new HashMap<>();

    int month;
    int year;
    int monthInFile;
    int yearInFile;



    TextView fixedCostTextView;
    TextView extraSpentTextView;
    TextView historyView;
    TextView totalTextView;

    public static String password = "";
    public static String userPass="";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthMap.put(0, "January");
        monthMap.put(1, "February");
        monthMap.put(2, "March");
        monthMap.put(3, "April");
        monthMap.put(4, "May");
        monthMap.put(5, "June");
        monthMap.put(6, "July");
        monthMap.put(7, "August");
        monthMap.put(8, "September");
        monthMap.put(9, "October");
        monthMap.put(10, "November");
        monthMap.put(11, "December");

        Calendar calendar = Calendar.getInstance();
        Log.i("calender", calendar.toString());

        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


        sharedPreferences = this.getSharedPreferences("com.bikash.moneymanager", Context.MODE_PRIVATE);
        try {
            String s = sharedPreferences.getString("password", "");
            monthInFile = sharedPreferences.getInt("month", -1);
            yearInFile = sharedPreferences.getInt("year", -1);

            Log.i("c month", String.valueOf(month));
            Log.i("f month", String.valueOf(monthInFile));

            if (s.equals("")){
                //Go to Sign up


            } else {
                password =s;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        authorise();



        /**
         * Local Databbase system initialised
         */
        myDatabase = this.openOrCreateDatabase("Cost", MODE_PRIVATE, null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS fixedCost (name VARCHAR, cost VARCHAR)");

        fixedCostArrayList = new ArrayList<>();
        extraSpentArrayList = new ArrayList<>();

        try{
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS fixedCost (name VARCHAR, cost VARCHAR)");


            Cursor c = myDatabase.rawQuery("SELECT * FROM fixedCost", null);

            int nameIndex = c.getColumnIndex("name");
            int costIndex = c.getColumnIndex("cost");

            c.moveToFirst();
            while(c!=null){

                String name = c.getString(nameIndex);
                String costString = c.getString(costIndex);


                Double l = Double.valueOf(costString);
                Memo m = new Memo(name, l);

                fixedCostArrayList.add(m);

                c.moveToNext();
            }
        } catch (Exception e){

            e.printStackTrace();

            Log.i("Error", "Not found");

        }

        try{
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS extraSpent (name VARCHAR, cost VARCHAR)");


            Cursor c = myDatabase.rawQuery("SELECT * FROM extraSpent", null);

            int nameIndex = c.getColumnIndex("name");
            int costIndex = c.getColumnIndex("cost");

            c.moveToFirst();
            while(c!=null){

                String name = c.getString(nameIndex);
                String costString = c.getString(costIndex);


                Double l = Double.valueOf(costString);
                Memo m = new Memo(name, l);

                extraSpentArrayList.add(m);

                c.moveToNext();
            }
        } catch (Exception e){

            e.printStackTrace();

            Log.i("Error", "Not found");

        }


        fixedCostTextView = (TextView) findViewById(R.id.fixedCostTextView);
        extraSpentTextView = (TextView) findViewById(R.id.extraSpentTextView);
        historyView = (TextView) findViewById(R.id.historyMainActivityTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        fixedCostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FixedCost.class);
                startActivity(i);
            }
        });

        extraSpentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ExtraSpent.class);
                startActivity(i);
            }
        });
        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, History.class);
                startActivity(i);
            }
        });

        totalFixed = Calculation.sumofFixed();
        totalExtra = Calculation.sumofExtra();
        total = totalExtra + totalFixed;

        checkMonthChanged();



        totalTextView.setText(Double.toString(total)+ " /=");

    }

    public void authorise(){
        if(password.equals("")){
            Intent i = new Intent(MainActivity.this, SetPassword.class);
            startActivity(i);
        }

        else if (userPass.equals("")){
            Intent i = new Intent(MainActivity.this, SignIn.class);
            startActivity(i);

        }
    }


    /**
     * Cheching dunction for month change
     * if month change it has to be saved in history
     * and reset some property
     */

    public void checkMonthChanged(){
        if(monthInFile!=month && monthInFile!=-1){
            //Save to history
            String name = monthMap.get(monthInFile) + ", " + String.valueOf(year);
            String cost = Double.toString(total);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS history (name VARCHAR, cost VARCHAR)");
            String sql = "INSERT INTO history (name, cost) VALUES ('"+name+"', '"+ cost+  "') ";
            myDatabase.execSQL(sql);


            //Reset extraspent
            totalExtra = 0.0;
            extraSpentArrayList.clear();
            myDatabase.delete("extraSpent",null, null);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resume", "on");

        //authorise();

        totalFixed = Calculation.sumofFixed();
        totalExtra = Calculation.sumofExtra();
        total = totalExtra + totalFixed;


        totalTextView.setText(Double.toString(total)+ " /=");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Restrat", "on");

        //authorise();

        if(password.equals("")){
            finish();
        }

        else if (userPass.equals("")){
            finish();

        }

        totalFixed = Calculation.sumofFixed();
        totalExtra = Calculation.sumofExtra();
        total = totalExtra + totalFixed;


        totalTextView.setText(Double.toString(total) + " /=");
    }

    @Override
    protected void onDestroy() {

        myDatabase.delete("fixedCost", null, null);
        myDatabase.delete("extraSpent", null, null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS fixedCost (name VARCHAR, cost VARCHAR)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS extraSpent (name VARCHAR, cost VARCHAR)");


        for(int i=0; i<fixedCostArrayList.size(); i++){
            String name = fixedCostArrayList.get(i).getMemoName();
            String cost = Double.toString(fixedCostArrayList.get(i).getCost());
            String sql = "INSERT INTO fixedCost (name, cost) VALUES ('"+name+"', '"+ cost+  "') ";
            myDatabase.execSQL(sql);

            Log.i("Insert", "Successful");
        }

        for(int i=0; i<extraSpentArrayList.size(); i++){
            String name = extraSpentArrayList.get(i).getMemoName();
            String cost = Double.toString(extraSpentArrayList.get(i).getCost());
            String sql = "INSERT INTO extraSpent (name, cost) VALUES ('"+name+"', '"+ cost+  "') ";
            myDatabase.execSQL(sql);

            Log.i("Insert", "Successful");
        }

        fixedCostArrayList.clear();
        extraSpentArrayList.clear();

        sharedPreferences.edit().putString("password", password).apply();
        sharedPreferences.edit().putInt("year", year).apply();
        sharedPreferences.edit().putInt("month", month).apply();

        userPass="";
        myDatabase.close();
        super.onDestroy();
    }
}

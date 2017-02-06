package com.bikash.moneymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ExtraSpent extends AppCompatActivity {

    private MemoAdapter memoAdapter;
    ListView listView;

    TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_cost);


        totalTextView = (TextView) findViewById(R.id.totalExtraSpentTextView);



        memoAdapter = new MemoAdapter(this, R.layout.list_item, MainActivity.extraSpentArrayList);
        listView = (ListView) findViewById(R.id.fextraSpentListView);
        listView.setAdapter(memoAdapter);

        MainActivity.totalExtra = Calculation.sumofExtra();
        MainActivity.total = MainActivity.totalExtra + MainActivity.totalFixed;
        totalTextView.setText(Double.toString(MainActivity.totalExtra)+ " /=");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_extra);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), AddMemo.class);
                i.putExtra("Id", 2);
                startActivity(i);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AddMemo.class);
                i.putExtra("Id", position);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ExtraSpent.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                MainActivity.extraSpentArrayList.remove(position);

                                memoAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MainActivity.totalExtra = Calculation.sumofExtra();
        totalTextView.setText(Double.toString(MainActivity.totalExtra)+ " /=");
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MainActivity.totalExtra = Calculation.sumofExtra();
        totalTextView.setText(Double.toString(MainActivity.totalExtra)+ " /=");
        memoAdapter.notifyDataSetChanged();
    }
}

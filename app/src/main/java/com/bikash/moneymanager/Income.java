package com.bikash.moneymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Income extends AppCompatActivity {

    private MemoAdapter memoAdapter;
    ListView listView;
    TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed__spent);

        totalTextView = (TextView) findViewById(R.id.totalFixedAmountTextView);



        memoAdapter = new MemoAdapter(this, R.layout.list_item, MainActivity.incomeArrayList);
        listView = (ListView) findViewById(R.id.fixedCostListView);
        listView.setAdapter(memoAdapter);

        MainActivity.totalIncome = Calculation.sumofFixed();
        MainActivity.total = MainActivity.totalSpent + MainActivity.totalIncome;
        totalTextView.setText(Double.toString(MainActivity.totalIncome)+ " /=");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_fixed);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), AddMemo.class);
                i.putExtra("Id", 1);
                startActivity(i);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AddMemo.class);
                i.putExtra("Id", (position+1)*10+1);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(Income.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.remaining -= MainActivity.incomeArrayList.get(position).getCost();
                                MainActivity.incomeArrayList.remove(position);
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

        MainActivity.totalIncome = Calculation.sumofFixed();
        totalTextView.setText(Double.toString(MainActivity.totalIncome)+ " /=");
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.totalIncome = Calculation.sumofFixed();
        totalTextView.setText(Double.toString(MainActivity.totalIncome)+ " /=");
        memoAdapter.notifyDataSetChanged();
    }
}

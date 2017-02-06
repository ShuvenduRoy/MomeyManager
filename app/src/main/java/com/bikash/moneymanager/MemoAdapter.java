package com.bikash.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bikash on 9/28/16.
 */
public class MemoAdapter extends ArrayAdapter<Memo> {

    private ArrayList<Memo> objects;

    public MemoAdapter(Context context, int resource, ArrayList<Memo> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        Memo i = objects.get(position);

        if(i!=null){

            TextView t1 = (TextView) v.findViewById(R.id.textViewname);
            TextView t2 = (TextView) v.findViewById(R.id.textViewCost);


            if(t1!=null){
                t1.setText(i.getMemoName());
            }

            if(t2!=null){
                Double l = i.getCost();
                t2.setText(Double.toString(l));
            }
        }


        return v;
    }
}

package com.bikash.moneymanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassword extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        editText1 = (EditText) findViewById(R.id.setpass1);
        editText2 = (EditText) findViewById(R.id.setpass2);
        textView = (TextView) findViewById(R.id.bikash1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/shuvendu.roy.758";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    public void btn(View view){
        String s1 = editText1.getText().toString();
        String s2 = editText2.getText().toString();

        if(s1.equals(s2)){
            if(s1.equals("")){
                Toast.makeText(SetPassword.this, "Password field Empty", Toast.LENGTH_SHORT).show();
                editText1.setText("");
                editText2.setText("");
            } else{
                MainActivity.password = s1;
                MainActivity.userPass = s1;
                finish();
            }


        } else {
            Toast.makeText(SetPassword.this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
        }
    }
}

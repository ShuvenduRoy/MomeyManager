package com.bikash.moneymanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText editText;
    Button passButton;
    String newPass;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editText = (EditText) findViewById(R.id.password);
        passButton = (Button) findViewById(R.id.passwordButton);

        textView = (TextView) findViewById(R.id.bikash2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/shuvendu.roy.758";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("pass", MainActivity.password);
                 newPass= editText.getText().toString();
                Log.i("pass", newPass);
                if(MainActivity.password.equals(newPass)){
                    MainActivity.userPass = newPass;
                    finish();

                } else {
                    editText.setText("");
                    Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

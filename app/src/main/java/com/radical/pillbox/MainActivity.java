package com.radical.pillbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.radical.pillbox.R.id.CloseActivityCross;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            final EditText email = (EditText) findViewById(R.id.EmailEditText);
            EditText password = (EditText) findViewById(R.id.PasswordEditText);
            TextView close = (TextView) findViewById(R.id.CloseActivityCross);
            Button sign_up = (Button) findViewById(R.id.SignUpButton);
            Button sign_in = (Button) findViewById(R.id.SignInButton);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Closing.", Toast.LENGTH_SHORT).show();
                    MainActivity.this.finish();
                    Log.d("Cross:", "Activity closed");
                }
            });

            sign_in.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    Log.d("Event:", "Signed in with correct credentials.");
                    if (email.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Signed in with creds.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(signUpIntent);
                    Log.d("Signn Up","Signed Up mofo");
                }
            });
        }

    }

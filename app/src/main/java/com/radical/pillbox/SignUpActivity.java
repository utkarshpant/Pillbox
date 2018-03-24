package com.radical.pillbox;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText ageEditText;
    TextView backArrow;
    Button submitSignUp;
    Button cancelSignUp;

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //UI elements initialised;
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailSignUpEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        ageEditText = findViewById(R.id.ageEditText);
        backArrow = findViewById(R.id.backButton);
        submitSignUp = findViewById(R.id.submitSignUpButton);
        cancelSignUp = findViewById(R.id.cancelSignUpButton);

        //logic elements initialised;
        mFirebaseInstance = FirebaseDatabase.getInstance();

        //get reference to 'users' node:
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        //store app title to 'app_title' node;
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated.");

                String appTitle = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //failed to read value;
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        //setting necessary listeners;
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.this.finish();
            }
        });

        cancelSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.this.finish();
            }
        });

        submitSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = firstNameEditText.getText().toString();
                String lName = lastNameEditText.getText().toString();
                String emailId = emailEditText.getText().toString();
                String phoneNo = phoneEditText.getText().toString();
                String ageString = ageEditText.getText().toString();

                if (ageString.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Age cannot be empty!", Toast.LENGTH_LONG).show();
                    ageEditText.setHintTextColor(Color.RED);
                    ageEditText.setAlpha((float) 0.6);
                }
                //check for existing user ID;
                else {
                    if (TextUtils.isEmpty(userId)) {
                        createUser(fName, lName, emailId, phoneNo, Integer.parseInt(ageString));
                    } else {
                        updateUser(fName, lName, emailId, phoneNo, Integer.parseInt(ageString));
                    }
                }

            }
        });

        //TODO: Add listener for Submit button (submitSignUp);

    }

    private void createUser(String fName, String lName, String emailId, String phoneNo, int age) {
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(fName, lName, emailId, age, phoneNo);
        mFirebaseDatabase.child(userId).setValue(user);
    }

    private void addUserChangeListener() {
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Log.e(TAG, "User data is null.");
                    return;
                }

                Log.e(TAG, "User data was changed. " + user.firstName + ", " + user.email);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //failed to read value;
                Log.e(TAG, "Failed to read user.", databaseError.toException());
            }
        });
    }

    private void updateUser(String fName, String lName, String emailId, String phoneNo, int age) {
        //updating user via child nodes;
        if (!TextUtils.isEmpty(fName)) {
            mFirebaseDatabase.child(userId).child("firstName").setValue(fName);
        }
    }
}

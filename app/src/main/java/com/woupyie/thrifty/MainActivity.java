package com.woupyie.thrifty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.woupyie.thrifty.Model.Users;
import com.woupyie.thrifty.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, logInButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = findViewById(R.id.main_signup_btn);
        logInButton = findViewById(R.id.main_login_btn);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(joinIntent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "") {

            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){

                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait ...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()) {

                    Users usersData = snapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){

                        if(usersData.getPassword().equals(password)){

                            Toast.makeText(MainActivity.this, "You are logged in...", Toast.LENGTH_SHORT);
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUsers = usersData;
                            startActivity(intent);

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Phone number or Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, " Account with this number does not exist. ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, " You need to create a new account. ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
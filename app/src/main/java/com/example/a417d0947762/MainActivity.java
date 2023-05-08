package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView etUsername;
    private TextView etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_userNameLogin);
        etPassword = findViewById(R.id.et_passwordLogin);
        Button login = findViewById(R.id.bt_login);
        Button signup = findViewById(R.id.bt_signup);

        View.OnClickListener listener = view -> {
            if(view.getId() == R.id.bt_login) {
                SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
                String userName = sharedPreferences.getString("username", "");
                String password = sharedPreferences.getString("password", "");

                if(userName.equals(etUsername.getText().toString()) && password.equals(etPassword.getText().toString()) && !etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    Intent intent = new Intent(MainActivity.this, activity_showlist.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }


            } else if(view.getId() == R.id.bt_signup) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };
        login.setOnClickListener(listener);
        signup.setOnClickListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
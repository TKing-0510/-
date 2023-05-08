package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private TextView etUsername;
    private TextView etPassword;
    private TextView etCheckPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.et_userNameSignUp);
        etPassword = findViewById(R.id.et_passwordSignUp);
        etCheckPassword = findViewById(R.id.et_checkPassword);
        Button btCreateAccount = findViewById(R.id.bt_createAccount);

        View.OnClickListener listener = view -> {
            String userName = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String checkPassword = etCheckPassword.getText().toString();

            if(userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "帳號或密碼不得為空" + userName, Toast.LENGTH_LONG).show();
                return;
            }
            if(!password.equals(checkPassword)) {
                Toast.makeText(SignUpActivity.this, "密碼不一致", Toast.LENGTH_LONG).show();
                return;
            }



            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", userName);
            editor.putString("password", password);
            editor.apply();

            Toast.makeText(SignUpActivity.this, "註冊成功  用戶 : " + userName, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        };

        btCreateAccount.setOnClickListener(listener);
    }
}
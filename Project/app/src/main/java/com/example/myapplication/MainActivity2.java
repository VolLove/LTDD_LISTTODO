package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Data.DatabaseQuery;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText username = findViewById(R.id.edtUserName);
        EditText password = findViewById(R.id.edtPassword);
        DatabaseQuery db = new DatabaseQuery(MainActivity2.this);
        TextView errorTV = findViewById(R.id.tvErrorLogin);
        Button buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString() == "") {
                    username.setError("Yêu cầu nhập tài khoản!");
                } else if (password.getText().toString() == "") {
                    password.setError("Yêu cầu nhập mật khẩu!");
                } else {
                    String usernamelogin = username.getText().toString();
                    String passwordlogin = password.getText().toString();
                    int user = db.login(usernamelogin, passwordlogin);
                    TextView textView = findViewById(R.id.tvLog);
                    textView.setText(usernamelogin + "\n" + passwordlogin);
                    if (user == -1) {
                        errorTV.setText("Tài khảon hoặc mật khẩu không đúng!");
                    } else {
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                        intent.putExtra("id", user);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
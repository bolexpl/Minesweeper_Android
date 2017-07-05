package com.example.bolek.minesweeper_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.bolek.minesweeper_android.R;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private ProgressBar progressBar;
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        Button btLogin = (Button) findViewById(R.id.login_button);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) login();
            }
        });
        Button btRegister = (Button) findViewById(R.id.register_button);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO register
            }
        });
    }

    private boolean isValid() {
        if (login.getText().toString().trim().equals("")) {
            login.setError(getResources().getString(R.string.input_required));
            return false;
        }
        if (password.getText().toString().trim().equals("")) {
            password.setError(getResources().getString(R.string.input_required));
            return false;
        }
        return true;
    }

    private void login() {
        //TODO login
    }
}

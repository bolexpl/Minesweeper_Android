package com.example.bolek.minesweeper_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bolek.minesweeper_android.R;
import com.example.bolek.minesweeper_android.model.ApiService;
import com.example.bolek.minesweeper_android.model.ApiUtils;
import com.example.bolek.minesweeper_android.pojo.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private ProgressBar progressBar;
    private EditText login;
    private EditText password;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        service = ApiUtils.getApiService();

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
        progressBar.setVisibility(View.VISIBLE);
        String loginParam = login.getText().toString().trim();
        String passParam = password.getText().toString().trim();

        service.login(loginParam, passParam).enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error2), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

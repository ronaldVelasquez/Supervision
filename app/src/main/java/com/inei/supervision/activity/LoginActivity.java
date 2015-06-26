package com.inei.supervision.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.gc.materialdesign.views.ButtonRectangle;
import com.inei.supervision.R;
import com.inei.supervision.business.UserBL;
import com.inei.supervision.entity.UserEntity;
import com.inei.supervision.library.SessionManager;

public class LoginActivity extends Activity{

    private static final String TAG = LoginActivity.class.getSimpleName();
    ButtonRectangle btnLogin;
    private EditText edtxtDni;
    SessionManager sessionManager;
    private UserBL userBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        //Declare components
        edtxtDni = (EditText) findViewById(R.id.edtxt_dni);
        btnLogin = (ButtonRectangle) findViewById(R.id.btn_login);

        Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = edtxtDni.getText().toString();

                userBL = new UserBL(getApplicationContext());
                UserEntity user = userBL.getUser(dni);
                Log.v(TAG, "Username : " + String.valueOf(user != null));
                if(user != null){
                    //Get serie number device
                    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    String serieNumber = telephonyManager.getDeviceId();
                    //Initial session
                    sessionManager.createLoginSession(dni, serieNumber);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "El usuario ingresado es incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

package com.inei.supervision.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.inei.supervision.R;
import com.inei.supervision.library.SessionManager;

import java.util.HashMap;

public class MainActivity extends Activity {
    private ButtonRectangle btnLoginSupervision;
    SessionManager sessionManager;
    private TextView txtDni, txtSede;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_actitivty);

        sessionManager = new SessionManager(getApplicationContext());

        txtDni = (TextView) findViewById(R.id.txt_dni);
        txtSede = (TextView) findViewById(R.id.txt_location);
        btnLoginSupervision = (ButtonRectangle) findViewById(R.id.btn_login_supervision);

        if (sessionManager.checkLogin()){
            finish();
        }

        HashMap<String, String> user = sessionManager.getUserDetails();
        String dni = user.get(SessionManager.KEY_DNI);
        String nroDevice = user.get(SessionManager.KEY_NRO_DEVICE);
        txtDni.setText(dni);
        txtSede.setText(nroDevice);
        btnLoginSupervision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

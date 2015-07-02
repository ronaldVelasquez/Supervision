package com.inei.supervision.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.inei.supervision.R;
import com.inei.supervision.library.SessionManager;
import com.inei.supervision.service.GpsTrackerService;

import java.util.HashMap;

public class MainActivity extends Activity {
    private ButtonRectangle btnLoginSupervision;
    SessionManager sessionManager;
    private TextView txtDni, txtSede;
    private EditText edtxtPassword;
    private String dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.checkLogin()) {
            finish();
        }
        setContentView(R.layout.activity_person_actitivty);
        Intent i = new Intent(getApplicationContext(), GpsTrackerService.class);
        startService(i);

        txtDni = (TextView) findViewById(R.id.txt_dni);
        txtSede = (TextView) findViewById(R.id.txt_location);
        edtxtPassword = (EditText) findViewById(R.id.edtxt_password);


        HashMap<String, Object> user = sessionManager.getUserDetails();
        dni = String.valueOf(user.get(SessionManager.KEY_DNI));
        final String nroDevice = String.valueOf(user.get(SessionManager.KEY_NRO_DEVICE));

        txtDni.setText(dni);
        txtSede.setText(nroDevice);
        btnLoginSupervision = (ButtonRectangle) findViewById(R.id.btn_login_supervision);

    }

    public Boolean isConnectedInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isInternet = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isInternet;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isConnectedInternet()) {
                btnLoginSupervision.setEnabled(true);
                btnLoginSupervision.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = edtxtPassword.getText().toString();
                        if (password.isEmpty()) {
                            Toast.makeText(MainActivity.this.getApplicationContext(), "Falta agregar un password para poder acceder al sistema", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, SupervisionActivity.class);
                            intent.putExtra(SupervisionActivity.USER, dni);
                            intent.putExtra(SupervisionActivity.PASSWORD, password);
                            startActivity(intent);
                        }
                    }
                });
            } else {
                btnLoginSupervision.setEnabled(false);
                btnLoginSupervision.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this.getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}

package com.inei.supervision.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.gc.materialdesign.views.ButtonRectangle;
import com.inei.supervision.R;

public class LoginActivity extends Activity{

    private ButtonRectangle btnLogin;
    private EditText edtxtDni;
    private EditText edtxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declare components
        edtxtDni = (EditText) findViewById(R.id.edtxt_dni);
        btnLogin = (ButtonRectangle) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this.getApplicationContext(), PersonActivity.class);
                startActivity(intent);
            }
        });
    }
}

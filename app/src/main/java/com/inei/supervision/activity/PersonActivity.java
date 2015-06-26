package com.inei.supervision.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;
import com.inei.supervision.R;

public class PersonActivity extends Activity {
    private ButtonRectangle btnLoginSupervision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_actitivty);
        getIntent();

        btnLoginSupervision = (ButtonRectangle) findViewById(R.id.btn_login_supervision);
        btnLoginSupervision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this.getApplicationContext(), SupervisionActivity.class);
                startActivity(intent);
            }
        });
    }


}

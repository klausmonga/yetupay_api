package com.b6555s.yetupay_api.network;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.b6555s.yetupay_api.R;

public class getLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_login);
        final EditText numEd=(EditText)findViewById(R.id.num);
        final EditText pwdEd=(EditText)findViewById(R.id.pwd);
        final String dev = getIntent().getStringExtra("dev");
        final String p_info = getIntent().getStringExtra("p_info");
        final String run_env = getIntent().getStringExtra("run_env");
        findViewById(R.id.paybt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessPayment processPayment = new ProcessPayment(getApplicationContext());
                processPayment.setDev(dev);
                processPayment.setP_info(p_info);
                processPayment.setRun_env(run_env);
                processPayment.addBill_to(numEd.getText().toString(),pwdEd.getText().toString());
                processPayment.do_tx();
            }
        });

    }

}

package com.b6555s.yetupay_api.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.b6555s.yetupay_api.R;

public class getLogin extends AppCompatActivity {
    BroadcastReceiver sentSmsBroadcastCome = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "api", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_login);
        final com.google.android.material.textfield.TextInputEditText numEd=findViewById(R.id.num);
        final com.google.android.material.textfield.TextInputEditText pwdEd=findViewById(R.id.pwd);
        final String dev = getIntent().getStringExtra("dev");
        final String p_info = getIntent().getStringExtra("p_info");
        final String run_env = getIntent().getStringExtra("run_env");
        IntentFilter filterSend = new IntentFilter();
        filterSend.addAction("YETUPAY-API");
        registerReceiver(sentSmsBroadcastCome, filterSend);
        findViewById(R.id.paybt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar ProcessPayment = findViewById(R.id.loading);
                ProcessPayment.setVisibility(View.VISIBLE);
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

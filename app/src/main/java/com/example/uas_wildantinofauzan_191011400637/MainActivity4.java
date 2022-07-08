package com.example.uas_wildantinofauzan_191011400637;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText smsBody;
    private Button smsManagerBtn;
    private Button smsSendToBtn;
    private Button smsViewBtn;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        smsBody = (EditText) findViewById(R.id.smsBody);
        smsManagerBtn = (Button) findViewById(R.id.smsManager);
        smsSendToBtn = (Button) findViewById(R.id.smsSIntent);
        smsViewBtn = (Button) findViewById(R.id.smsVIntent);

        smsManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                sendSmsByManager();


            }
        });
        smsSendToBtn.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                sendSmsBodySIntent();

            }
        });
        smsViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                sendSmsByVIntent();

            }
        });
    }

    public void sendSmsByManager() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber.getText().toString(),
                    null,
                    smsBody.getText().toString(),
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "Sms terkirim",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Sms terkirim",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        }
    }

    public void sendSmsBodySIntent() {
        Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());
        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO,uri);
        smsSIntent.putExtra("sms_body", smsBody.getText().toString());
        try {
            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Sms gagal",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();


        }
    }


    public  void  sendSmsByVIntent(){
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        smsVIntent.setType("vnd.android-dir/mms-sms");
        smsVIntent.putExtra("address",phoneNumber.getText().toString());
        smsVIntent.putExtra("sms_body",smsBody.getText().toString());
        try {
            startActivity(smsVIntent);
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Sms terkirim",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        }
    }
}
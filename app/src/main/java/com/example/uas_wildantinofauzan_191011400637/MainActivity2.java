package com.example.uas_wildantinofauzan_191011400637;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText editText_name, editText_email;
    Button button_save;
    SharedPreferences sharedPreferences;
    //So create a shared preferences name and also create key name

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText_name = findViewById(R.id.editext_name);
        editText_email = findViewById(R.id.editext_email);
        button_save = findViewById(R.id.button_save);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        //when open activity first check shared preferences data aviable or not

        String name = sharedPreferences.getString(KEY_NAME, null);

        if(name != null){
            //if data is avialble so directly call on HomeActivity..
            Intent intent= new Intent(MainActivity2.this,HomeActivity.class);
        }



        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when click a button put data on Shared preferences..
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME,editText_name.getText().toString());
                editor.putString(KEY_EMAIL, editText_email.getText().toString());
                editor.apply();
                Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity2.this,"Login Success", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
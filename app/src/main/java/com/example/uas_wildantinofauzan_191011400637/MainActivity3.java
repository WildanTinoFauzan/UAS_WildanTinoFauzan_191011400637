package com.example.uas_wildantinofauzan_191011400637;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    public static final int download_progress = 0;
    private String file_url="https://dev.naskahkode.com/cv.pdf";
    Button btn_download;
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn_download = (Button) findViewById(R.id.button1);
        btn_download.setOnClickListener(this);
    }
    @Override

    public void onClick(View v) {
        if(ContextCompat.checkSelfPermission(MainActivity3.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            Log.i("Permission","Permission is Denied");
            ActivityCompat.requestPermissions(MainActivity3.this,new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }else{
            new DownloadFileAsync().execute(file_url);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case download_progress:
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("Downloading file...");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prgDialog.setCancelable(false);
                prgDialog.show();
                return prgDialog;
            default:
                return null;
        }
    }




    class DownloadFileAsync extends AsyncTask<String, Integer, String> {
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(download_progress);
        }
        @Override
        protected String doInBackground(String... aurl) {

            int count;
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                Log.i("koneksi","koneksi berhasil");
                int lenghtOfFile = conexion.getContentLength();
                Log.i("UkuranFile", "ukuran"+String.valueOf(lenghtOfFile));
                InputStream input = new BufferedInputStream(url.openStream(),10*1024);
                String fileName = conexion.getHeaderField("Content-Disposition");
                if (fileName == null || fileName.length() < 1){
                    URL downloadUrl = conexion.getURL();
                    fileName = downloadUrl.getFile();
                    fileName = fileName.substring(fileName.lastIndexOf("/")+1);
                } else {
                    fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9),
                            "UTF-8");
                    fileName = fileName.replaceAll("\"", "");
                }
                OutputStream output = new
                        FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Download/"+fileName);
                byte data[] = new byte[1024];
                long total = 8;
                while ((count = input.read(data))!= -1){
                    total += count;
                    publishProgress(((int) (total*100)/ lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("koneksi","koneksi gagal");
            }


            return null;
        }


        protected void onProgressUpdate(Integer... progress) {
            prgDialog.setProgress(progress[0]);

        }

        protected void onPostExecute(String result) {
            dismissDialog(download_progress);
            Toast.makeText(getApplicationContext(), "Download complete. File in /sdcard/Download",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
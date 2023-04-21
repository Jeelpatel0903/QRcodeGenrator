package com.ajdev.qrcodegenrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.Manifest;


public class genrateactivity extends AppCompatActivity {

    private Button genratebtn,savebtn;
    private EditText editText;
    private ImageView qrcodegenrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrateactivity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        genratebtn = findViewById(R.id.genratebtn);
        savebtn = findViewById(R.id.savebtn);
        editText = findViewById(R.id.edittext);
        qrcodegenrate = findViewById(R.id.qrgenrate);

        ActivityCompat.requestPermissions(genrateactivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(genrateactivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);




        genratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myText = editText.getText().toString().trim();
                if(myText.equals("")){
                    Toast.makeText(genrateactivity.this, "Please Enter some URL..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MultiFormatWriter mWriter = new MultiFormatWriter();
                    try {
                        //BitMatrix class to encode entered text and set Width & Height
                        BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 500,500);
                        BarcodeEncoder mEncoder = new BarcodeEncoder();
                        Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                        qrcodegenrate.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                        // to hide the keyboard
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }



                BitmapDrawable bitmapDrawable = (BitmapDrawable)qrcodegenrate.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                savebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) qrcodegenrate.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        shareImageandText(bitmap);
                    }
                });
            }

            private void shareImageandText(Bitmap bitmap) {
                Uri uri = getmageToShare(bitmap);
                Intent intent = new Intent(Intent.ACTION_SEND);

                // putting uri of image to be shared
                intent.putExtra(Intent.EXTRA_STREAM, uri);

                // adding text to share
                intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image");

                // Add subject Here
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

                // setting type to image
                intent.setType("image/png");

                // calling startactivity() to share
                startActivity(Intent.createChooser(intent, "Share Via"));
            }

            // Retrieving the url to share
            private Uri getmageToShare(Bitmap bitmap) {
                File imagefolder = new File(getCacheDir(), "images");
                Uri uri = null;
                try {
                    imagefolder.mkdirs();
                    File file = new File(imagefolder, "shared_image.png");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    uri = FileProvider.getUriForFile(genrateactivity.this, "com.anni.shareimage.fileprovider", file);
                } catch (Exception e) {
                    Toast.makeText(genrateactivity.this, "some problemm occure", Toast.LENGTH_SHORT).show();
                }
                return uri;
            }
        });

    }
}
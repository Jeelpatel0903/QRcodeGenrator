package com.ajdev.qrcodegenrator;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class infoactivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission has already been granted
            startScanning();
        }
        setContentView(R.layout.activity_infoactivity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission is required to scan QR codes",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String contents = result.getContents();
                Log.d("QRCodeScanner", "Scanned: " + contents);
                if (contents.startsWith("http://") || contents.startsWith("https://")) {
                    // Open the URL in the browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contents));
                    startActivity(browserIntent);
                } else {
                    // Do something with the scanned result (e.g. display it in a TextView)
                    Toast.makeText(this, "Scanned: " + contents, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Redirect on : "+contents, Toast.LENGTH_SHORT).show();
            } else {
                Log.d("QRCodeScanner", "Cancelled");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
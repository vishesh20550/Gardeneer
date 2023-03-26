package com.example.gardneer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent= new Intent(this,HomeActivity.class);
                startActivity(intent);
                return;
            } else {
                Toast.makeText(this, "Please allow the Permission", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean checkPermission(String[] permissions){
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
            Intent intent = new Intent(this,HomeActivity.class);
            this.startActivity(intent);
        }
        else{
            Log.i("Permissions","Permitted");
            Intent intent= new Intent(this,HomeActivity.class);
            startActivity(intent);
        }

    }
}
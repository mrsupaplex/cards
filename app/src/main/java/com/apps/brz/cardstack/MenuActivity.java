package com.apps.brz.cardstack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button startSwipingButton;
    private Button showFavoritesButton;
    private Button recycleBinButton;
    private Button settingsButton;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startSwipingButton = (Button) findViewById(R.id.start_swiping_btn);

        startSwipingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePermissions();
            }
        });

        showFavoritesButton = (Button) findViewById(R.id.view_favorites_btn);

        showFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFavorites();
            }
        });

        recycleBinButton = (Button) findViewById(R.id.view_recycle_bin_btn);

        recycleBinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRecycleBin();
            }
        });

        settingsButton = (Button) findViewById(R.id.view_settings_btn);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSettings();
            }
        });
    }

    private void viewSettings() {
        Intent i = new Intent(MenuActivity.this, OldPreferenceActivity.class);
        startActivity(i);
    }

    private void viewRecycleBin() {
        Intent i = new Intent(MenuActivity.this, DeleteImagesActivity.class);
        startActivity(i);
    }

    private void viewFavorites() {
        Intent i = new Intent(MenuActivity.this, FavoriteActivity.class);
        startActivity(i);
    }

    private void handlePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }
        moveToSwiping();
    }

    private void moveToSwiping() {
        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    moveToSwiping();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }



            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}

package com.apps.brz.cardstack;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // createShortCut(); TODO: add shortcut
        setContentView(R.layout.activity_splash);

        doSplash();
    }

    private void doSplash() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
        t.start();
    }


    public void createShortCut(){
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.shortut_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher_foreground);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutintent);
    }
}

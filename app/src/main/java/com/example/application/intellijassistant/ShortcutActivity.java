package com.example.application.intellijassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ShortcutActivity extends AppCompatActivity {

    private static final String EXTRA_SHORTCUT = "com.example.application.intellijassistant.extra_shortcut";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            fragment = new ShortcutFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public static Intent newIntent(Context context, Shortcut shortcut) {
        Intent intent = new Intent(context, ShortcutActivity.class);
        intent.putExtra(EXTRA_SHORTCUT, shortcut);
        return intent;
    }
}

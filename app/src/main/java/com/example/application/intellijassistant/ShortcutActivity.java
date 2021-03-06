package com.example.application.intellijassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ShortcutActivity extends AppCompatActivity {

    private static final String EXTRA_SHORTCUT = "com.example.application.intellijassistant.extra_shortcut";
    private static final String SHORTCUT_ID = "com.example.application.intellijassistant.shortcut_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public static Intent newAddShortcutIntent(Context context, Shortcut shortcut) {
        Intent intent = new Intent(context, ShortcutActivity.class);
        intent.putExtra(EXTRA_SHORTCUT, shortcut);
        return intent;
    }

    private Fragment createFragment() {

        Shortcut shortcut = (Shortcut) getIntent().getSerializableExtra(EXTRA_SHORTCUT);
        return ShortcutFragment.newInstance(shortcut);
    }
}

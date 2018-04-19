package com.example.application.intellijassistant;

import android.support.v4.app.Fragment;

public class ShortcutListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ShortcutListFragment();
    }
}

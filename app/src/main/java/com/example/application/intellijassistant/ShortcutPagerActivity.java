package com.example.application.intellijassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Said on 23/04/2018.
 */

public class ShortcutPagerActivity extends AppCompatActivity {
    private static final String EXTRA_SHORTCUT_ID =
            "com.example.application.intellijassistant.shortcut_id";

    private ViewPager mViewPager;
    private List<Shortcut> mShortcuts;

    public static Intent newIntent(Context packageContext, UUID shortcutId) {
        Intent intent = new Intent(packageContext, ShortcutPagerActivity.class);
        intent.putExtra(EXTRA_SHORTCUT_ID, shortcutId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut_pager);

        UUID shortcutId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_SHORTCUT_ID);

        mViewPager = (ViewPager) findViewById(R.id.shortcut_view_pager);

        mShortcuts = ShortcutList.get(this).getShortcuts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Shortcut shortcut = mShortcuts.get(position);
                return PreviewShortcutFragment.newInstance(shortcut.getId());
            }

            @Override
            public int getCount() {
                return mShortcuts.size();
            }
        });

        for(int i = 0; i < mShortcuts.size(); i++){
            if(mShortcuts.get(i).getId().equals(shortcutId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}

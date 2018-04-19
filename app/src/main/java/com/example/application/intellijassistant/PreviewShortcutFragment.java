package com.example.application.intellijassistant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PreviewShortcutFragment extends Fragment {

    private TextView mShortcutText, mShortcutDetailsText, mShortcutCategoryText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preview_shortcut, container, false);

        mShortcutText = v.findViewById(R.id.shortcut_preview_title);
        mShortcutDetailsText = v.findViewById(R.id.shortcut_preview_details);
        mShortcutCategoryText = v.findViewById(R.id.shortcut_preview_category);

        return v;
    }
}

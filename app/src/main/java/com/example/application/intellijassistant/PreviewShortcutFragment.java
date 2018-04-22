package com.example.application.intellijassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;


public class PreviewShortcutFragment extends Fragment {

    private static final String SHORTCUT_UUID = "com.example.application.intellijassistant.shortcut_uuid";
    private TextView mShortcutText, mShortcutDetailsText, mShortcutCategoryText;
    private Shortcut mShortcut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preview_shortcut, container, false);

        UUID id = (UUID) getArguments().getSerializable(SHORTCUT_UUID);
        mShortcut = ShortcutList.get(getActivity()).getShortcut(id);

        mShortcutText = v.findViewById(R.id.shortcut_preview_title);
        mShortcutText.setText(mShortcut.getShortcut());

        mShortcutDetailsText = v.findViewById(R.id.shortcut_preview_details);
        mShortcutDetailsText.setText(mShortcut.getDescription());

        mShortcutCategoryText = v.findViewById(R.id.shortcut_preview_category);
        mShortcutCategoryText.setText(mShortcut.getCategory().toString());

        return v;
    }

    // Used to create preview fragment containing data from a shortcut object with shortcutId
    public static Fragment newInstance(UUID shortuctId) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(SHORTCUT_UUID, shortuctId);

        PreviewShortcutFragment previewShortcutFragment = new PreviewShortcutFragment();
        previewShortcutFragment.setArguments(bundle);
        return previewShortcutFragment;
    }
}

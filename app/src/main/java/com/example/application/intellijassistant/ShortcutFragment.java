package com.example.application.intellijassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by malek on 4/12/2018.
 */

public class ShortcutFragment extends Fragment {

    private Shortcut mShortcut;
    private EditText mShortcutTitle;
    private EditText mShortcutDescription;
    private CheckBox mShortcutFavouriteCheckBox;

    /**
     * Serves only to instantiate a Shortcut object in memory
     * @param savedInstanceState Needed to recover the app's state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortcut = new Shortcut();
    }

    /**
     * Inflating View objects from fragment_shortcut.xml
     * @param inflater
     * @param container Place for fragment hosting
     * @param savedInstanceState Needed to recover the app's state
     * @return Inflated View object with set listeners on child View objects
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shortcut, container, false);

        mShortcutTitle = v.findViewById(R.id.shortcut_title);

        /**
         * Updating view whenever a user starts editing the View's text
         */
        mShortcutTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShortcut.shortcut(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mShortcutDescription = v.findViewById(R.id.shortcut_description);
        mShortcutDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShortcut.description(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mShortcutFavouriteCheckBox = v.findViewById(R.id.shortcut_favourite);
        mShortcutFavouriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mShortcut.favourite(isChecked);
            }
        });

        return v;
    }
}

package com.example.application.intellijassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.intellijassistant.Shortcut.ShortcutBuilder;

/**
 * Created by malek on 4/12/2018.
 */

public class ShortcutFragment extends Fragment {

    private static final String SHORTCUT_FRAGMENT = "com.example.application.intellijassistant.shortcut_fragment";

    private ShortcutBuilder mShortcutBuilder;
    private Shortcut mShortcut;
    private EditText mShortcutTitle;
    private EditText mShortcutDescription;
    private CheckBox mShortcutFavouriteCheckBox;
    private Button mAddButton;

    /**
     * Serves only to instantiate a Shortcut object in memory
     * @param savedInstanceState Needed to recover the app's state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortcutBuilder = new ShortcutBuilder();

        if(savedInstanceState != null) {
            mShortcut = (Shortcut) savedInstanceState.getSerializable(SHORTCUT_FRAGMENT);

            if(mShortcut != null) {
                mShortcutBuilder.shortcut(mShortcut.getShortcut()).
                        description(mShortcut.getDescription()).
                        favourite(mShortcut.isFavourite()).
                        category(mShortcut.getCategory());
            }
        }
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

        if(mShortcut != null) {
            mShortcutTitle.setText(mShortcut.getShortcut());
        }

        /**
         * Updating view whenever a user starts editing the View's text
         */
        mShortcutTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShortcutBuilder.shortcut(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mShortcutDescription = v.findViewById(R.id.shortcut_description);

        if(mShortcut != null) {
            mShortcutDescription.setText(mShortcut.getDescription());
        }

        mShortcutDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShortcutBuilder.description(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mShortcutFavouriteCheckBox = v.findViewById(R.id.shortcut_favourite);

        if(mShortcut != null) {
            mShortcutFavouriteCheckBox.setChecked(mShortcut.isFavourite());
        }

        mShortcutFavouriteCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                mShortcutBuilder.favourite(isChecked));

        mAddButton = v.findViewById(R.id.add_shortcut_button);
        mAddButton.setOnClickListener( view -> {
            Toast.makeText(getContext(), "Button clicked!", Toast.LENGTH_LONG).show();
            /* TODO save the newly created shortcut AND return to the activity that called the activity that hosts this fragment */
            getActivity().finish();
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        try {
            mShortcut = mShortcutBuilder.getShortcut();
            savedInstanceState.putSerializable(SHORTCUT_FRAGMENT, mShortcut);

        } catch(IllegalStateException e) {
            // Serves only to not crash the app
        }
    }
}

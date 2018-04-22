package com.example.application.intellijassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.application.intellijassistant.Shortcut.ShortcutBuilder;

/**
 * Created by malek on 4/12/2018.
 */

public class ShortcutFragment extends Fragment {

    private static final String SHORTCUT_FRAGMENT = "com.example.application.intellijassistant.shortcut_fragment";
    private static final String NEW_SHORTCUT = "com.example.application.intellijassistant.new_shortcut";

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

        /************************************* SHORTCUT TITLE *************************************/
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

        /********************************** SHORTCUT DESCRIPTION **********************************/
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

        /*********************************** SHORTCUT CHECKBOX ************************************/
        mShortcutFavouriteCheckBox = v.findViewById(R.id.shortcut_favourite);

        if(mShortcut != null) {
            mShortcutFavouriteCheckBox.setChecked(mShortcut.isFavourite());
        }

        mShortcutFavouriteCheckBox.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            mShortcutBuilder.favourite(isChecked);
        });

        /************************************ SHORTCUT SPINNER ************************************/
        Spinner mCategorySpinner = v.findViewById(R.id.category_spinner);

        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categories));

        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategorySpinner.setAdapter(categoryArrayAdapter);


        /************************************** SHORTCUT ADD **************************************/
        mAddButton = v.findViewById(R.id.add_shortcut_button);
        mAddButton.setOnClickListener( view -> {

            try {

                String category = mCategorySpinner.getSelectedItem().toString();

                switch (category) {
                    case "Editing":
                        mShortcutBuilder.category(Shortcut.Category.EDITING);
                        break;
                    case "Search":
                        mShortcutBuilder.category(Shortcut.Category.SEARCH);
                        break;
                    case "Usage/Search":
                        mShortcutBuilder.category(Shortcut.Category.USAGE_SEARCH);
                        break;
                    case "Compile/Run":
                        mShortcutBuilder.category(Shortcut.Category.COMPILE_RUN);
                        break;
                    case "Debugging":
                        mShortcutBuilder.category(Shortcut.Category.DEBUGGING);
                        break;
                    case "Navigation":
                        mShortcutBuilder.category(Shortcut.Category.NAVIGATION);
                        break;
                    case "Version Control":
                        mShortcutBuilder.category(Shortcut.Category.VERSION_CONTROL);
                        break;
                    case "Live Templates":
                        mShortcutBuilder.category(Shortcut.Category.LIVE_TEMPLATES);
                        break;
                    case "General":
                        mShortcutBuilder.category(Shortcut.Category.GENERAL);
                        break;
                    case "Other":
                        mShortcutBuilder.category(Shortcut.Category.OTHER);
                        break;
                }

                mShortcut = mShortcutBuilder.getShortcut();
                returnResult();
                getActivity().finish();

            } catch(IllegalStateException e) {
                Toast.makeText(getContext(), "Shortcut Title must be specified!", Toast.LENGTH_LONG).show();
            }

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

    // Used to create preview fragment containing data from a shortcut object with shortcutId
    public static Fragment newInstance(Shortcut shortcut) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(NEW_SHORTCUT, shortcut);

        ShortcutFragment shortcutFragment = new ShortcutFragment();
        shortcutFragment.setArguments(bundle);
        return shortcutFragment;
    }

    public void returnResult() {
        Intent intent = new Intent();
        intent.putExtra(NEW_SHORTCUT, mShortcut);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    public static Shortcut getResult(Intent data) {
        return (Shortcut) data.getSerializableExtra(NEW_SHORTCUT);
    }
}

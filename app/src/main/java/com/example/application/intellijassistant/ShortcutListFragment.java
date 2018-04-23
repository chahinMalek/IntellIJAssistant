package com.example.application.intellijassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class ShortcutListFragment extends Fragment {

    private static final int REQUEST_CODE_NEW_SHORTCUT = 1;

    private RecyclerView mShortcutRecyclerView;
    private ShortcutAdapter mAdapter;
    private boolean onBind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_shortcut_list, container, false);
        mShortcutRecyclerView = view.findViewById(R.id.shortcut_recycler_view);
        mShortcutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI(){
        ShortcutList shortcutList = ShortcutList.get(getActivity());
        List<Shortcut> shortcuts = shortcutList.getShortcuts();

        if(mAdapter == null) {
            mAdapter = new ShortcutAdapter(shortcuts);
            mShortcutRecyclerView.setAdapter(mAdapter);
        } else {
            if(!onBind){
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    protected void updateUI(UUID shortcutId, boolean isChecked){

        ShortcutList shortcutList = ShortcutList.get(getActivity());

        // Raising and sinking shortcuts
        if(isChecked){
            shortcutList.raiseShortcut(shortcutList.getShortcutIndex(shortcutId));
        } else {
            shortcutList.sinkShortcut(shortcutList.getShortcutIndex(shortcutId));
        }

        if(!onBind){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_NEW_SHORTCUT) {
            if(data != null) {
                ShortcutList shortcutList = ShortcutList.get(getActivity());
                Shortcut shortcut = ShortcutFragment.getResult(data);
                shortcutList.addShortcut(shortcut);
                updateUI(shortcut.getId(), shortcut.isFavourite());

                Toast.makeText(getActivity(), "Shortcut successfully added!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            ShortcutList shortcutList = ShortcutList.get(getContext());
            shortcutList.storeShortcuts(getContext());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*************************************** TOOLBAR METHODS **************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_new_shortcut) {
            Shortcut shortcut = null;
            Intent intent = ShortcutActivity.newAddShortcutIntent(getContext(), shortcut);
            startActivityForResult(intent, REQUEST_CODE_NEW_SHORTCUT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*************************************** SHORTCUT HOLDER **************************************/
    private class ShortcutHolder extends ViewHolder implements View.OnClickListener {
        private Shortcut mShortcut;
        private TextView mShortcutTitle;
        private TextView mShortcutDescription;
        private CheckBox mFavouriteCheckBox;

        public ShortcutHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_shortcut, parent, false));

            itemView.setOnClickListener(this);
            mShortcutTitle = itemView.findViewById(R.id.list_item_shortcut_title);
            mShortcutDescription = itemView.findViewById(R.id.list_item_shortcut_description);
            mFavouriteCheckBox = itemView.findViewById(R.id.favourite_check_box);
            mFavouriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mShortcut.setFavourite(isChecked);
                    updateUI(mShortcut.getId(), isChecked);
                }
            });
        }
        
        public void bind(Shortcut shortcut){

            mShortcut = shortcut;
            mShortcutTitle.setText(shortcut.getShortcut());
            mShortcutDescription.setText(shortcut.getDescription());

            mFavouriteCheckBox.setChecked(mShortcut.isFavourite());
        }
        
        @Override
        public void onClick(View view) {

            Intent intent = ShortcutPagerActivity.newIntent(getActivity(), mShortcut.getId());
            startActivity(intent);
        }
    }

    /************************************** SHORTCUT ADAPTER **************************************/
    private class ShortcutAdapter extends RecyclerView.Adapter<ShortcutHolder>{
        private List<Shortcut> mShortcutList;

        public ShortcutAdapter(List<Shortcut> shortcutList){
            mShortcutList = shortcutList;
        }

        @Override
        public ShortcutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ShortcutHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ShortcutHolder holder, int position) {
            Shortcut shortcut = mShortcutList.get(position);
            onBind = true;
            holder.bind(shortcut);
            onBind = false;
        }

        @Override
        public int getItemCount() {
            return mShortcutList.size();
        }
    }
}

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
import android.widget.TextView;

import java.util.List;


public class ShortcutListFragment extends Fragment {

    private static final int REQUEST_CODE_NEW_SHORTCUT = 1;

    private RecyclerView mShortcutRecyclerView;
    private ShortcutAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        List<Shortcut> shortcutHeap = shortcutList.getShortcuts();

        mAdapter = new ShortcutAdapter(shortcutHeap);
        mShortcutRecyclerView.setAdapter(mAdapter);
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
        }
        
        public void bind(Shortcut shortcut){
            mShortcut = shortcut;
            mShortcutTitle.setText(shortcut.getShortcut());
            mShortcutDescription.setText(shortcut.getDescription());
            mFavouriteCheckBox.setChecked(mShortcut.isFavourite());
        }
        
        @Override
        public void onClick(View view) {
            Intent intent = ShortcutActivity.newPreviewShortcutIntent(getActivity(), mShortcut.getId());
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
            holder.bind(shortcut);
        }

        @Override
        public int getItemCount() {
            return mShortcutList.size();
        }
    }
}

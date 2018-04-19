package com.example.application.intellijassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ShortcutListFragment extends Fragment {
    private RecyclerView mShortcutRecyclerView;
    private ShortcutAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_shortcut_list, container, false);
        mShortcutRecyclerView = (RecyclerView) view.findViewById(R.id.shortcut_recycler_view);
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

    private class ShortcutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            Toast.makeText(getActivity(), mShortcut.getShortcut() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class ShortcutAdapter extends RecyclerView.Adapter<ShortcutHolder>{
        private List<Shortcut> mShortcutHeap;

        public ShortcutAdapter(List<Shortcut> shortcutHeap){
            mShortcutHeap = shortcutHeap;
        }

        @Override
        public ShortcutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ShortcutHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ShortcutHolder holder, int position) {
            Shortcut shortcut = mShortcutHeap.get(position);
            holder.bind(shortcut);
        }

        @Override
        public int getItemCount() {
            return mShortcutHeap.size();
        }
    }
}

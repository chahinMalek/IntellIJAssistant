package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Shortcut.Category;
import com.example.application.intellijassistant.Shortcut.ShortcutBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Malek on 19/04/2018.
 */

public class ShortcutList {

    private static ShortcutList sShortcutList;

    private List<Shortcut> mShortcuts;
    private ShortcutBuilder mShortcutBuilder;

    private ShortcutList(Context context){
        mShortcuts = new ArrayList<>();
        mShortcutBuilder = new ShortcutBuilder();

        for(int i = 1; i <= 100; i++){
            mShortcuts.add(mShortcutBuilder.shortcut("Shortcut #" + i)
                    .description("Description #" + i)
                    .favourite(i % 2 == 0)
                    .getShortcut());
        }
    }

    public static ShortcutList get(Context context){

        if (sShortcutList == null) {
            sShortcutList = new ShortcutList(context);
        }
        return sShortcutList;
    }

    public List<Shortcut> getShortcuts(){
        return mShortcuts;
    }

    public Shortcut getShortcut(UUID id){
        for(Shortcut shortcut: mShortcuts){
            if(shortcut.getId().equals(id)){
                return shortcut;
            }
        }
        return null;
    }

    public List<Shortcut> getShortcutsByCategory(Category category) {
        ArrayList<Shortcut> shortcutsByCategory = new ArrayList<>();

        for(Shortcut s : mShortcuts) {
            if (s.getCategory().equals(category)) {
                shortcutsByCategory.add(s);
            }
        }
        return shortcutsByCategory;
    }

    public int raiseShortcut (int index) {

        int temp = index-1;
        while(index > 0 && !mShortcuts.get(temp).isFavourite()) {
            swap(temp--, index--);
        }
        return index;
    }

    public int sinkShortcut (int index) {

        int temp = index+1;
        while(temp < mShortcuts.size() && mShortcuts.get(temp).isFavourite()) {
            swap(temp++, index++);
        }
        return index;
    }

    private void swap(int i, int j) {
        Shortcut s = mShortcuts.get(i);
        mShortcuts.set(i, mShortcuts.get(j));
        mShortcuts.set(j, s);
    }
}

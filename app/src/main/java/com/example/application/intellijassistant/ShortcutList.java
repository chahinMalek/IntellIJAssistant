package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Shortcut.ShortcutBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Said on 19/04/2018.
 */

public class ShortcutList {
    private static ShortcutList sShortcutList;

    private List<Shortcut> mShortcuts;
    private ShortcutBuilder mShortcutBuilder;

    public static ShortcutList get(Context context){
        if (sShortcutList == null) {
            sShortcutList = new ShortcutList(context);
        }

        return sShortcutList;
    }

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
}

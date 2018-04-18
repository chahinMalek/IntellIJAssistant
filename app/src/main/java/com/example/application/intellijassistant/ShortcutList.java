package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Heap.Heap;

/**
 * User: malek
 * Date: 4/11/2018
 * Time: 11:03 PM
 */

public class ShortcutList {

    private static ShortcutList sShortcutList;
    private Heap<Shortcut> mShortcutHeap;

    private ShortcutList(Context context) {

        mShortcutHeap = new Heap<>(Heap.Type.GREATER);
        for(int i=0; i<100; i++) {
            mShortcutHeap.add(new Shortcut.ShortcutBuilder().shortcut("CTRL + ALT + UP").getShortcut());
        }
    }

    public static ShortcutList getShortcutList(Context context) {

        if(sShortcutList == null) {
            sShortcutList = new ShortcutList(context);
        }
        return sShortcutList;
    }

    public Heap<Shortcut> getShortcutHeap() {
        return mShortcutHeap;
    }

    /**
     * This way any category of shortcuts can be extracted
     * @return Heap data structure containing favourite shortcuts
     */
    public Heap<Shortcut> getFavourites() {

        Heap<Shortcut> favourites = new Heap<>(Heap.Type.GREATER);

        for(int i=0; i<mShortcutHeap.getSize(); i++) {
            if(mShortcutHeap.get(i).isFavourite()) {
                favourites.add(mShortcutHeap.get(i));
            }
        }

        return favourites;
    }
}

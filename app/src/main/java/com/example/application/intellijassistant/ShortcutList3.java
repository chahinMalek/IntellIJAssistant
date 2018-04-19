package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Heap.Heap;

import java.util.Random;
import java.util.UUID;

/**
 * User: malek
 * Date: 4/11/2018
 * Time: 11:03 PM
 */

public class ShortcutList3 {

    private static ShortcutList3 sShortcutList;
    private Heap<Shortcut> mShortcutHeap;

    private ShortcutList3(Context context) {

        Random rand = new Random();
        mShortcutHeap = new Heap<>(Heap.Type.GREATER);
        for(int i=0; i<100; i++) {
            mShortcutHeap.add(new Shortcut.ShortcutBuilder().shortcut("Shortcut #" + (i + 1))
                    .description("Description #" + (i + 1))
                    .favourite(rand.nextBoolean())
                    .getShortcut());
        }

        for(int i = 0; i < 100; i++){
            System.out.println(mShortcutHeap.get(i).getShortcut());
        }
    }

    public static ShortcutList3 getShortcutList(Context context) {

        if(sShortcutList == null) {
            sShortcutList = new ShortcutList3(context);
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

    public Shortcut getShortcut(UUID id){
        for(int i = 0; i < mShortcutHeap.getSize(); i++){
            if(mShortcutHeap.get(i).getId().equals(id)){
                return mShortcutHeap.get(i);
            }
        }
        return null;
    }
}

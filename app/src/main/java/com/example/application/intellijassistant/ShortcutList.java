package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Heap.*;

/**
 * User: malek
 * Date: 4/11/2018
 * Time: 11:03 PM
 */

public class ShortcutList {

    private static ShortcutList shortcutList;
    private Heap<Shortcut> shortcutHeap;

    public static ShortcutList getShortcutList(Context context) {

        if(shortcutList == null) {
            shortcutList = new ShortcutList(context);
        }
        return shortcutList;
    }

    public Heap<Shortcut> getShortcutHeap() {
        return shortcutHeap;
    }

    private ShortcutList(Context context) {
        shortcutHeap = new Heap(Greater.class);
    }
}

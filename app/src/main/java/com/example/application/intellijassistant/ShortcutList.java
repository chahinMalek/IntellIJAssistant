package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Heap.Greater;
import com.example.application.intellijassistant.Heap.Heap;

/**
 * User: malek
 * Date: 4/11/2018
 * Time: 11:03 PM
 */

public class ShortcutList {

    private static ShortcutList shortcutList;
    private Heap<Shortcut> shortcutHeap;

    private ShortcutList(Context context) {

        shortcutHeap = new Heap(Greater.class);

        for(int i=0; i<100; i++) {
            shortcutHeap.add(new Shortcut().description("Shortcut " + i+1).
                    favourite(i%2 == 0).
                    shortcut("CTRL + ALT + " + i+1));
        }
    }

    public static ShortcutList getShortcutList(Context context) {

        if(shortcutList == null) {
            shortcutList = new ShortcutList(context);
        }
        return shortcutList;
    }

    public Heap<Shortcut> getShortcutHeap() {
        return shortcutHeap;
    }
}

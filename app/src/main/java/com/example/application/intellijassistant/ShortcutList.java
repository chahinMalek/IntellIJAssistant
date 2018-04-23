package com.example.application.intellijassistant;

import android.content.Context;

import com.example.application.intellijassistant.Shortcut.Category;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Malek on 19/04/2018.
 */

public class ShortcutList {

    private static String fileName = "mShortcuts";
    private static ShortcutList sShortcutList;
    private List<Shortcut> mShortcuts;

    private ShortcutList(Context context) throws IOException, ClassNotFoundException {

        try (FileInputStream in = context.openFileInput(fileName)) {

            // If file exists read serialized ArrayList<Shortcut> from that file instead of embed shortcuts.txt file
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            mShortcuts = (ArrayList<Shortcut>) objectInputStream.readObject();

        } catch (FileNotFoundException e) {

            // Read shortcuts from embed shortcuts.txt file
            mShortcuts = readShortcuts(context);

            // Create a file in device's internal memory
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(mShortcuts);
            objectOutputStream.close();
        }
    }

    public static ShortcutList get(Context context) {

        if (sShortcutList == null) {
            try {
                sShortcutList = new ShortcutList(context);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sShortcutList;
    }

    public int getShortcutIndex(UUID id) {

        for(int i = 0; i < mShortcuts.size(); i++){
            if(mShortcuts.get(i).getId().equals(id))
                return i;
        }

        return -1;
    }

    public void addShortcut(Shortcut s) {
        mShortcuts.add(s);
    }

    public void storeShortcuts(Context context) throws IOException {

        if(mShortcuts == null) {
            throw new IllegalStateException("Error loading parameter");
        }

        // Storing objects into phone's internal memory i MODE_PRIVATE. This way only our app can access this file
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
        objectOutputStream.writeObject(mShortcuts);
        objectOutputStream.close();
    }

    public List<Shortcut> getShortcuts(){
        return mShortcuts;
    }

    public Shortcut getShortcut(UUID id) {

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

        if(index < 0 || index >= mShortcuts.size()) {
            throw new IndexOutOfBoundsException();
        }

        int temp = index-1;
        while(index > 0 && !mShortcuts.get(temp).isFavourite()) {
            swap(temp--, index--);
        }
        return index;
    }

    public int sinkShortcut (int index) {

        if(index < 0 || index >= mShortcuts.size()) {
            throw new IndexOutOfBoundsException();
        }

        int temp = index+1;
        while(temp < mShortcuts.size() && mShortcuts.get(temp).isFavourite()) {
            swap(temp++, index++);
        }
        return index;
    }

    private ArrayList<Shortcut> readShortcuts(Context context) throws IOException, ClassNotFoundException {

        InputStream in = context.getAssets().open("shortcuts.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        ArrayList<Shortcut> data = (ArrayList<Shortcut>) objectInputStream.readObject();
        objectInputStream.close();

        return data;
    }

    private void swap(int i, int j) {

        Shortcut s = mShortcuts.get(i);
        mShortcuts.set(i, mShortcuts.get(j));
        mShortcuts.set(j, s);
    }

}

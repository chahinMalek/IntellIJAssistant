package com.example.application.intellijassistant;

import java.util.UUID;

/**
 * User: malek
 * Date: 4/10/2018
 * Time: 8:41 PM
 */

public class Shortcut implements Comparable<Shortcut> {

    private UUID id;
    private String shortcut;
    private String description;
    private boolean favourite;
    private int category;

    public Shortcut() {
        id = UUID.randomUUID();
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return this.category;
    }

    public Shortcut setShortcut(String shortcut) {
        this.shortcut = shortcut;
        id = UUID.randomUUID();
        return this;
    }

    public Shortcut setDescription(String description) {
        this.description = description;
        return this;
    }

    public Shortcut setFavourite(boolean favourite) {
        this.favourite = favourite;
        return this;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavourite() {
        return favourite;
    }

    @Override
    public int compareTo(Shortcut o) {
        if(this.favourite == o.favourite) return 0;
        else {
            if(this.favourite) return 1;
            return -1;
        }
    }
}

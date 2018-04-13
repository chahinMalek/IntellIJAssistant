package com.example.application.intellijassistant;

/**
 * User: malek
 * Date: 4/10/2018
 * Time: 8:41 PM
 */

public class Shortcut implements Comparable<Shortcut> {

    private String shortcut;
    private String description;
    private boolean favourite;

    public Shortcut() {
    }

    public Shortcut shortcut(String shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public Shortcut description(String description) {
        this.description = description;
        return this;
    }

    public Shortcut favourite(boolean favourite) {
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

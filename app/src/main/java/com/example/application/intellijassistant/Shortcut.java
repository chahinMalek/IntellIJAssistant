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

    static class ShortcutBuilder {

        private String shortcut;
        private String description;
        private boolean favourite;

        public ShortcutBuilder shortcut(String shortcut) {
            this.shortcut = shortcut;
            return this;
        }

        public ShortcutBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ShortcutBuilder favourite(boolean favourite) {
            this.favourite = favourite;
            return this;
        }

        public Shortcut create() {
            return new Shortcut(this);
        }
    }

    private Shortcut() {

    }

    public Shortcut(ShortcutBuilder shortcutBuilder) {
        this.description = shortcutBuilder.description;
        this.favourite = shortcutBuilder.favourite;
        this.shortcut = shortcutBuilder.shortcut;
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
    public String toString() {
        return shortcut + " " + description + " " + favourite;
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

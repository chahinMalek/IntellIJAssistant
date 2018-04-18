package com.example.application.intellijassistant;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * User: malek
 * Date: 4/10/2018
 * Time: 8:41 PM
 */

enum Category {
    EDITING, SEARCH, USAGE_SEARCH, COMPILE_RUN, DEBUGGING, NAVIGATION, REFACTORING,
    VERSION_CONTROL, LIVE_TEMPLATES, GENERAL, OTHER
}

public class Shortcut implements Comparable<Shortcut> {

    @Nullable
    private String description;

    private UUID id;
    private String shortcut;
    private boolean favourite;
    private Category category;

    static class ShortcutBuilder {

        private UUID id;
        private String description;
        private String shortcut;
        private boolean favourite;
        private Category category;

        public ShortcutBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ShortcutBuilder shortcut(String shortcut) {
            this.shortcut = shortcut;
            return this;
        }

        public ShortcutBuilder favourite(boolean favourite) {
            this.favourite = favourite;
            return this;
        }

        public ShortcutBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Shortcut getShortcut() {

            if(this.shortcut == null) {
                throw new IllegalStateException("Shortcut title must be specified!");

            } else if (this.category == null) {
                this.category = Category.OTHER;
            }

            return new Shortcut(shortcut, description, favourite, category);
        }
    }

    private Shortcut(String shortcut, @Nullable String description, boolean favourite, Category category) {
        this.id = UUID.randomUUID();
        this.shortcut = shortcut;
        this.description = description;
        this.favourite = favourite;
        this.category = (category == null) ? Category.GENERAL : category;
    }

    public Category getCategory() {
        return category;
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

    public UUID getId() {
        return id;
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

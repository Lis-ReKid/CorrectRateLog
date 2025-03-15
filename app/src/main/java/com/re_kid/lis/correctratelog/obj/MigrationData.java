package com.re_kid.lis.correctratelog.obj;

import java.util.List;

public class MigrationData {
    final List<History> histories;
    final List<Category> categories;

    public MigrationData(List<History> histories, List<Category> categories) {
        this.histories = histories;
        this.categories = categories;
    }

    public List<History> getHistories() {
        return histories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}

package com.re_kid.lis.correctratelog.obj;

public class Category {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        if (id < 0) throw new IllegalArgumentException("不正なカテゴリです。");
        this.id = id;
        this.name = name;
    }
}

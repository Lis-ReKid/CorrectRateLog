package com.re_kid.lis.correctratelog.model;

import com.re_kid.lis.correctratelog.DatabaseHelper;

public class CategoryModel implements AutoCloseable{
    DatabaseHelper _helper;
    @Override
    public void close() throws Exception {
        _helper.close();
    }
}

package com.starttech.base;

import java.nio.file.Paths;

public class Constant {

    public static final String PARENT_DIR=System.getProperty("user.dir");
    public static final String TEST_RESOURCES_DIR= Paths.get(PARENT_DIR,"allure-results").toString();
}

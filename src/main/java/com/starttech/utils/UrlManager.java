package com.starttech.utils;


import static com.starttech.utils.ConfigManager.getTestUrlConfig;

public class UrlManager {


    public static String getTestUrl() {
        String env = getTestUrlConfig();
        String siteUrl = null;

        if (env.equals("prod")) {
            siteUrl = DataManager.getData("url.base");
        } else {
            siteUrl = "http://" + env + ".nesine.com";
        }

        return siteUrl;
    }


}

package com.gss.chs.Utility;

public class Temprory {
    public static MyDbHandler getMyDbHandler() {
        return myDbHandler;
    }

    public static void setMyDbHandler(MyDbHandler myDbHandler) {
        Temprory.myDbHandler = myDbHandler;
    }

    public static MyDbHandler myDbHandler;
}


package com.example.web_task_manager;

import java.util.regex.Pattern;

public class Properties {
    public static final Pattern REGEX_LOGIN_PATTERN = Pattern.compile("^[A-Za-z0-9]{4,16}$");
    public static final Pattern REGEX_MAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    public static final String MAIL_LOGIN_OUT = "critskov@yandex.ru";
    public static final String DEFAULT_MAIL_IN = "dezen53412gml@gmail.com";// "dezen53412gml@gmail.com"; anonnyasd@gmail.com
    public static final String DEFAULT_SAVE_DIR = "";
    public static final String MAIL_PASSWORD_OUT = "sfcbhaepcweijobd";
}

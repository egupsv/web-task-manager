package com.example.web_task_manager.servlet;

public class TryChecker {
    private static String propertyOfLoginDiv = "none";
    private static String propertyOfSignupDiv = "none";
    public static String getPropertyOfLoginDiv() { return propertyOfLoginDiv; }
    public static void setPropertyOfLoginDiv(String newProperty) { propertyOfLoginDiv = newProperty; }
    public static String getPropertyOfSignupDiv() { return propertyOfSignupDiv; }
    public static void setPropertyOfSignupDiv(String newProperty) { propertyOfSignupDiv = newProperty; }
}

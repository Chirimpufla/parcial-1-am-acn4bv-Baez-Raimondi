package com.example.turnera;

public class Constant {

    //Direccion del server
    public static String MAIN_URL = "https://192.168.10.100/Turnera";

    //Direcciones de los recursos del servidor
    public static String LOGIN_URL = MAIN_URL + "/login.php";

    //Claves para comunicaciòn dentro del servidor
    public static String KEY_USER = "user";
    public static String KEY_PASS = "pass";

    //Shared preferences
    public static String SHARED_PREF_NAME = "com.example.turnera.userLogin";
    public static String USER_SHARED_PREF = "user";
}

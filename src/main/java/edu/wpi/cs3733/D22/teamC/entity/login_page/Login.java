package edu.wpi.cs3733.D22.teamC.entity.login_page;

public class Login {
    private static String username;
    private static String password;

    public static void initializeLogin(String username, String password)
    {
        Login.username = username;
        Login.password = password;
    }

    public static void resetUserLogin()
    {
        Login.username = null;
        Login.password = null;
    }
}

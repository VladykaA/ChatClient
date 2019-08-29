package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utils {
    private static final String URL = "http://127.0.0.1";
    private static final int PORT = 8080;
    private static String login;
    private static String room = "main";

    public static String getURL() {
        return URL + ":" + PORT;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String userLogin) {
        login = userLogin;
    }

    public static String getRoom() {
        return room;
    }

    public static void help() {
        System.out.println("______________________________");
        System.out.println("status - change your status,");
        System.out.println("room - change room,");
        System.out.println("pm - write a private message,");
        System.out.println("users - shows all registered users & their status");
        System.out.println("quit - finish the session");
        System.out.println("______________________________");
    }

    public static void SignIn(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter your login: ");
                String login = scanner.nextLine();
                System.out.println("Enter your password: ");
                String pass = scanner.nextLine();

                java.net.URL obj = new URL(Utils.getURL() + "/login?login=" + login + "&password=" + pass);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == 200) {
                    System.out.println("Logged in as " + login);
                    Utils.setLogin(login);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getUsers() {
        try {
            URL obj = new URL(Utils.getURL() + "/users");
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);

            InputStream is = conn.getInputStream();

            String strBuf = new String(GetThread.requestBodyToArray(is), StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            String[] users = gson.fromJson(strBuf, String.class).split("next");

            for (String user : users)
                System.out.println(user);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void setStatus(String status) {
        try {
            URL obj = new URL(Utils.getURL() + "/status?login=" + login + "&status=" + status);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                System.out.println("Status changed to " + status);
                return;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void setRoom(String newRoom) {
        try {
            URL obj = new URL(Utils.getURL() + "/room?login=" + login + "&room=" + newRoom);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                System.out.println("You are now in " + newRoom + " room");
                room = newRoom;
                return;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}

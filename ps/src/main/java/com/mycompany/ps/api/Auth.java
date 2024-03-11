/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ps.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author javie
 */
public class Auth {
  private static final String API_URL = "http://64.225.78.184:8000/";

  public static String register(String username, String email, String password) throws IOException {
    URL url = new URL(API_URL + "users/register");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");
    conn.setRequestProperty("Accept", "application/json");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    String input = "{\"username\": \"" + username + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

    conn.getOutputStream().write(input.getBytes());

    int responseCode = conn.getResponseCode();
    System.out.println("POST Response Code :: " + responseCode);
//    if (responseCode != 201) {
//      throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//    }

    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    String output;
    StringBuilder response = new StringBuilder();
    while ((output = br.readLine()) != null) {
      response.append(output);
    }
    br.close();

    // TODO: Habria que convertirlo a json / hashmap
    return response.toString();
  }
}

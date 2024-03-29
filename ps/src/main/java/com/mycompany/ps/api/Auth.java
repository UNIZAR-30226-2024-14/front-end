/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ps.api;

import com.google.gson.Gson;
import com.mycompany.ps.api.http.HttpRequest;
import com.mycompany.ps.api.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author javie
 */
public class Auth {
  private static final String API_URL = "http://64.225.78.184:8000/";

  public static Map register(String username, String email, String password) throws IOException {
    // Esto es el ejemplo de peticion que de la API
    // curl -X 'POST' \
    //   'http://localhost:8000/users/register' \
    //   -H 'accept: application/json' \
    //   -H 'Content-Type: application/json' \
    //   -d '{
    //   "username": "string",
    //   "email": "string",
    //   "password": "string"
    // }'

    // Se puede traducir a:
    String endpoint = API_URL + "users/register";
    String[] headers = {
      "accept: application/json",
      "Content-Type: application/json"
    };
    String data = "{\"username\": \"" + username + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

    HttpResponse response = HttpRequest.POST(endpoint, headers, data);

    // Pasar a Map
    Gson gson = new Gson();
    Map jsonMap = new HashMap<>();
    jsonMap = gson.fromJson(response.getBody(), jsonMap.getClass());
    jsonMap.put("code", response.getCode());

    return jsonMap;
  }
  
  public static Map login(String username, String password) throws IOException {
    // Esto es el ejemplo de peticion que de la API
//   curl -X 'POST' \
//  'http://localhost:8000/users/token' \
//  -H 'accept: application/json' \
//  -H 'Content-Type: application/x-www-form-urlencoded' \
//  -d 'grant_type=&username=test&password=test&scope=&client_id=&client_secret='

    // Se puede traducir a:
    String endpoint = API_URL + "users/token";
    String[] headers = {
      "Accept: application/json",
      "Content-Type: application/x-www-form-urlencoded"
    };
    String data = "grant_type=&username=" + username + "&password=" + password + "&scope=&client_id=&client_secret=";

    HttpResponse response = HttpRequest.POST(endpoint, headers, data);

    // Pasar a Map
    Gson gson = new Gson();
    Map jsonMap = new HashMap<>();
    jsonMap = gson.fromJson(response.getBody(), jsonMap.getClass());
    jsonMap.put("code", response.getCode());

    return jsonMap;
    }
}

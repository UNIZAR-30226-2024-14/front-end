/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ps.api;

import com.google.gson.Gson;
import com.mycompany.ps.api.http.HttpRequest;
import com.mycompany.ps.api.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author javie
 */
public class Auth {
  private static final String API_URL = "http://64.225.78.184:8000/";
  private static String tokenUsuario;

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
    tokenUsuario = jsonMap.get("access_token").toString();

    return jsonMap;
  }

  
  public static int crearMesa() throws IOException {
    // Crear una nueva mesa
    String endpoint = API_URL + "bj/create";
    String[] headers = {
      "accept: application/json"
    };
    String data = "";

    HttpResponse response = HttpRequest.POST(endpoint, headers, data);

    if (response.getCode() == 200) {
      Gson gson = new Gson();
      Map<String, Object> jsonResponse = gson.fromJson(response.getBody(), Map.class);
      if (jsonResponse.containsKey("table_id")) {
        double tableId = Double.parseDouble(jsonResponse.get("table_id").toString());
        return (int) tableId; // Convertir el número decimal a entero
      } else {
        throw new RuntimeException("La respuesta no contiene la ID de la mesa.");
      }
    } else {
      throw new RuntimeException("Error al crear la mesa, código de estado: " + response.getCode());
    }
  }
  
  public static int buscarMesa() throws IOException {
        String endpoint = API_URL + "bj/search";
        String[] headers = {
                "accept: application/json",
                "Content-Type: application/json"
        };
        String data = "";

        HttpResponse response = HttpRequest.POST(endpoint, headers, data);

        if (response.getCode() == 200) {
            Gson gson = new Gson();
            JsonElement jsonElement = JsonParser.parseString(response.getBody());

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("table_id")) {
                    return jsonObject.get("table_id").getAsInt();
                } else {
                    throw new RuntimeException("La respuesta no contiene la ID de la mesa.");
                }
            } else {
                throw new RuntimeException("La respuesta no es un objeto JSON válido.");
            }
        } else {
            throw new RuntimeException("Error al buscar la mesa, código de estado: " + response.getCode());
        }
    }


    public static void joinMesa(int mesaId, String token) throws IOException {
        // Unirse a una mesa específica
        String endpoint = API_URL + "bj/join/" + mesaId;
        String[] headers = {
          "accept: application/json",
          "Authorization: Bearer " + token // Incluir el token de autenticación en el encabezado de autorización
        };
        String data = ""; // No necesitas enviar datos en esta solicitud

        HttpResponse response = HttpRequest.POST(endpoint, headers, data);

        if (response.getCode() == 200) {
          System.out.println("Unido exitosamente a la mesa con ID: " + mesaId);
        } else {
          throw new RuntimeException("Error al unirse a la mesa, código de estado: " + response.getCode());
        }
    }
  
  public static int getMesaCount() throws IOException {
    // Obtener el número de mesas desde el endpoint
    String endpoint = API_URL + "bj/tables";
    String[] headers = {
      "Accept: application/json"
    };

    HttpResponse response = HttpRequest.GET(endpoint, headers);

    if (response.getCode() == 200) {
      // Analizar la respuesta para obtener el número de mesas
      Gson gson = new Gson();
      JsonElement jsonElement = JsonParser.parseString(response.getBody());

      if (jsonElement.isJsonObject()) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("tables") && jsonObject.get("tables").isJsonArray()) {
          List<?> tableList = gson.fromJson(jsonObject.get("tables"), List.class);
          return tableList.size(); // Devuelve el número de mesas
        } else {
          throw new RuntimeException("El campo 'tables' no es un array.");
        }
      } else {
        throw new RuntimeException("La respuesta no es un objeto JSON válido.");
      }
    } else {
      throw new RuntimeException("Error al obtener datos, código de estado: " + response.getCode());
    }
  }
  
  public static String devolverToken(){
      return tokenUsuario;
  }
}

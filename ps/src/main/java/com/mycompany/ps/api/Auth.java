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
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 *
 * @author javie
 */
public class Auth {

    private static final String API_URL = "http://64.225.78.184:8000/";
    private static String tokenUsuario;
    private static String nombre;
    private static String correo;

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
        nombre = username;
        Object accessTokenObj = jsonMap.get("access_token");
        if (accessTokenObj != null) {
            tokenUsuario = accessTokenObj.toString();
        } else {
            // Manejar el caso en el que no se encuentra el token de acceso
            tokenUsuario = null; // O cualquier otro manejo que necesites
        }

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

    public static boolean mesaEstaLlena(int mesaId, String token) throws IOException {
        // Endpoint para obtener todas las mesas
        String endpoint = API_URL + "bj/tables";
        String[] headers = {
            "Accept: application/json",
            "Authorization: Bearer " + token
        };

        // Realizar la solicitud GET para obtener todas las mesas
        HttpResponse response = HttpRequest.GET(endpoint, headers);

        // Verificar si la solicitud fue exitosa (código de respuesta 200)
        if (response.getCode() == 200) {
            // Analizar la respuesta para obtener la lista de mesas
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.getBody(), JsonObject.class);
            JsonArray mesas = jsonResponse.get("tables").getAsJsonArray();

            // Iterar sobre cada mesa para encontrar la mesa con el ID proporcionado
            for (JsonElement mesaElement : mesas) {
                JsonObject mesa = mesaElement.getAsJsonObject();
                int mesaActualId = mesa.get("id").getAsInt();
                // Verificar si la mesa actual tiene el mismo ID que el proporcionado
                if (mesaActualId == mesaId) {
                    int numJugadores = 0;
                    // Contar el número de jugadores asignados a la mesa (excluyendo valores nulos)
                    for (Map.Entry<String, JsonElement> entry : mesa.entrySet()) {
                        String key = entry.getKey();
                        // Solo contar los jugadores (cuyas claves comienzan con "player") y que no sean nulos
                        if (key.startsWith("player") && !entry.getValue().isJsonNull()) {
                            numJugadores++;
                        }
                    }
                    // Comprobar si la mesa está llena
                    int maxJugadores = 4; // Suponiendo que el máximo de jugadores por mesa es 4
                    return numJugadores == maxJugadores;
                }
            }
            // Si no se encontró ninguna mesa con el ID proporcionado, lanzar una excepción
            throw new IllegalArgumentException("No se encontró ninguna mesa con el ID proporcionado: " + mesaId);
        } else {
            // Manejar el caso en que la solicitud no fue exitosa
            throw new RuntimeException("Error al obtener las mesas, código de estado: " + response.getCode());
        }
    }

    public static String devolverToken() {
        return tokenUsuario;
    }
    
    public static String devolverNombre() {
        return nombre;
    }
    
    public static String obtenerCorreoUsuario(String nombreUsuario) throws IOException {
        String endpoint = API_URL + "users/";
        String[] headers = {
            "accept: application/json"
        };

        HttpResponse response = HttpRequest.GET(endpoint, headers);

        if (response.getCode() == 200) {
            // Analizar la respuesta para obtener el correo electrónico del usuario
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.getBody(), JsonObject.class);
            JsonArray usersArray = jsonResponse.getAsJsonArray("users");

            // Buscar el usuario por su nombre en la lista de usuarios
            for (int i = 0; i < usersArray.size(); i++) {
                JsonObject usuario = usersArray.get(i).getAsJsonObject();
                if (usuario.get("username").getAsString().equals(nombreUsuario)) {
                    return usuario.get("email").getAsString();
                }
            }

            // Si no se encontró el usuario, lanzar una excepción
            throw new RuntimeException("El usuario '" + nombreUsuario + "' no fue encontrado.");
        } else {
            throw new RuntimeException("Error al obtener datos, código de estado: " + response.getCode());
        }
    }
}

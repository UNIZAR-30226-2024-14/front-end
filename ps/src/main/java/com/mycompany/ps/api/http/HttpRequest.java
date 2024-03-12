package com.mycompany.ps.api.http;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author javie
 */
public class HttpRequest {
  private static String readStream(InputStreamReader input) throws IOException {
    String line;
    StringBuilder output = new StringBuilder();
    BufferedReader reader = new BufferedReader(input);
    while ((line = reader.readLine()) != null)
      output.append(line);
    reader.close();
    return output.toString();
  }

  public static HttpResponse POST(String endpoint, String[] headers, String data) throws IOException {
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");
    conn.setDoOutput(true);

    for (String header : headers) {
      String[] parts = header.split(":");
      conn.setRequestProperty(parts[0], parts[1]);
    }

    conn.getOutputStream().write(data.getBytes());

    int responseCode = conn.getResponseCode();
    String response;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      response = readStream(new InputStreamReader(conn.getInputStream()));
    } else {
      response = readStream(new InputStreamReader(conn.getErrorStream()));
    }

    return new HttpResponse(responseCode, response);
  }

  public static HttpResponse GET(String endpoint, String[] headers) throws IOException {
    return GET(endpoint, headers, null);
  }
  public static HttpResponse GET(String endpoint, String[] headers, String data) throws IOException {
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("GET");

    for (String header : headers) {
      String[] parts = header.split(":");
      conn.setRequestProperty(parts[0], parts[1]);
    }

    if (data != null && !data.isEmpty())
      conn.getOutputStream().write(data.getBytes());

    int responseCode = conn.getResponseCode();
    String response;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      response = readStream(new InputStreamReader(conn.getInputStream()));
    } else {
      response = readStream(new InputStreamReader(conn.getErrorStream()));
    }

    return new HttpResponse(responseCode, response);
  }
}

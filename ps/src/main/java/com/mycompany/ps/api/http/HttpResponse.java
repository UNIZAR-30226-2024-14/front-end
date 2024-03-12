package com.mycompany.ps.api.http;

public class HttpResponse {
  private final int code;
  private final String body;

  public HttpResponse(int code, String body) {
    this.code = code;
    this.body = body;
  }

  public int getCode() { return code; }

  public String getBody() { return body; }
}

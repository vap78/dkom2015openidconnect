package com.sap.dkom2015.openidconnect;

public class ASResponsePojo {
//  {
//    "access_token": "ya29.LwHWotKjw_BLcpSlSzm6YAGu-jb80fIm8GN7CLYcfDUcV_cQ8LlHWLAtfeX7yF0IpgaieWIuROFqCw",
//    "token_type": "Bearer",
//    "expires_in": 3600,
//    "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6ImNhODhiZGQ5OWY3YTgyNWZjODc5MjFmZTA3NWIyYTY0MGY4M2IzMDUifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTE0NzY0NzA2ODkyNDI2NDM5NTYwIiwiYXpwIjoiODI2OTcyMzYwNzA0LXBjMmw4ZTRidnZnN2U0YTY0bmVuOG9mZHNrNDFjNjBwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJ2YXNpbC5wYW51c2hldkBnbWFpbC5jb20iLCJhdF9oYXNoIjoiVUJFaTBfWm1sd0IxNDhSNU5ZQjhDdyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdWQiOiI4MjY5NzIzNjA3MDQtcGMybDhlNGJ2dmc3ZTRhNjRuZW44b2Zkc2s0MWM2MHAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJpYXQiOjE0MjU3NjUyMjcsImV4cCI6MTQyNTc2OTAwN30.OfCBKd91CFj0OPuhSJPeGt_aVbxzrZOHzVYvmdPqt0XTvHidQ_VrQXeplJ3BfBOQ63Ts40KBLiKdeb75cAE3KGA3g0RJyscSFlYjjQN-qh6tjxAPhfChwn7D7BDh3awMsTLY3WI8jC-CQQYFMiZYkIuSogRAARIg3q62O3SvaU8"
//   }
  
  private String access_token;
  private String token_type;
  private String expires_in;
  private String id_token;
  public String getAccess_token() {
    return access_token;
  }
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  public String getToken_type() {
    return token_type;
  }
  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }
  public String getExpires_in() {
    return expires_in;
  }
  public void setExpires_in(String expires_in) {
    this.expires_in = expires_in;
  }
  public String getId_token() {
    return id_token;
  }
  public void setId_token(String id_token) {
    this.id_token = id_token;
  }
  
}

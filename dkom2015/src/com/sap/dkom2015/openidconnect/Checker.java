package com.sap.dkom2015.openidconnect;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;


public class Checker {
  private final List<String> clientIDs;
  private final String audience;
  private final GoogleIdTokenVerifier verifier;
  private final JsonFactory jFactory;
  private String problem = "Verification failed. (Time-out?)";

  public Checker(String[] clientIDs, String audience) {
    this.clientIDs = Arrays.asList(clientIDs);
    this.audience = audience;
    NetHttpTransport transport = new NetHttpTransport();
    jFactory = new GsonFactory();
    verifier = new GoogleIdTokenVerifier(transport, jFactory);
  }

  public GoogleIdToken.Payload check(String tokenString) {
    GoogleIdToken.Payload payload = null;
    try {
      GoogleIdToken token = GoogleIdToken.parse(jFactory, tokenString);
      if (verifier.verify(token)) {
        GoogleIdToken.Payload tempPayload = token.getPayload();
        if (!tempPayload.getAudience().equals(audience))
          problem = "Audience mismatch";
        else if (!clientIDs.contains(tempPayload.getAuthorizedParty()))
          problem = "Client ID mismatch";
        else
          payload = tempPayload;
      }
    } catch (Exception e) {
      e.printStackTrace();
      problem = e.getClass().getName() + ":" + e.getMessage();
    }
    return payload;
  }

  public String getProblem() {
    return problem;
  }
}
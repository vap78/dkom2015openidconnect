package com.sap.dkom2015.openidconnect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.gson.Gson;

/**
 * Servlet implementation class AuthEndPoint
 */
public class AuthEndPoint extends HttpServlet {
  private static final String CLIENT_SECRET = "_YaeX6K_eavSyzsTERnA88Iq";
  private static final String CLIENT_ID = "826972360704-pc2l8e4bvvg7e4a64nen8ofdsk41c60p.apps.googleusercontent.com";
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AuthEndPoint() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String state = request.getParameter("state");
    // String stateDecoded = URLDecoder.decode(state, "UTF-8");

    String code = request.getParameter("code");

    String authResponse = getAuthResponse(code);
    
    Gson gson = new Gson();
    ASResponsePojo asResponsePojo = gson.fromJson(authResponse, ASResponsePojo.class);
    String[] encodedJwtTokenParts = asResponsePojo.getId_token().split("\\.");
    String[] jwtTokenParts = new String[3];
    jwtTokenParts[0] = new String(Base64.decodeBase64(encodedJwtTokenParts[0]), "UTF-8");
    jwtTokenParts[1] = new String(Base64.decodeBase64(encodedJwtTokenParts[1]), "UTF-8");
    jwtTokenParts[2] = encodedJwtTokenParts[2];
    
    GoogleIdToken token = GoogleIdToken.parse(new GsonFactory(), asResponsePojo.getId_token());

    String subject = token.getPayload().getSubject();
    System.err.println(authResponse);
    
    String userProfile = getUserProfile(asResponsePojo);
    
    response.setContentType("text/html");
    print(response, "<html>");
    print(response, "<body>");
    print(response, "<p><strong>Authorization respose:</strong></p>");
    print(response, "<pre>");

    print(response, authResponse);
    print(response, "</pre>");
    print(response, "<hr/>");
    print(response, "<p><strong>JWT token:</strong></p>");
    print(response, "<p>Header</p>");
    print(response, "<pre>");
    print(response, formatJson(jwtTokenParts[0]));
    print(response, "</pre>");
    print(response, "<p>Body</p>");
    print(response, "<pre>");
    print(response, formatJson(jwtTokenParts[1]));
    print(response, "</pre>");
    print(response, "<p>Signature</p>");
    print(response, "<pre>");
    print(response, jwtTokenParts[2]);
    print(response, "</pre>");
    print(response, "<hr/>");
    print(response, "<p><strong>User Profile Data:</strong></p>");
    print(response, "<pre>");
    print(response, userProfile);
    print(response, "</pre>");
    print(response, "</body>");
    print(response, "</html>");
  }

  public static String getUserProfile(ASResponsePojo asResponsePojo) throws IOException {
    CloseableHttpClient client = null;
    try {
      client = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

      HttpGet reqToProfileService = new HttpGet("https://www.googleapis.com/plus/v1/people/me/openIdConnect");
      reqToProfileService.addHeader("Authorization", "Bearer " + asResponsePojo.getAccess_token());
      HttpResponse profileResponse = client.execute(reqToProfileService);

      return EntityUtils.toString(profileResponse.getEntity());
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }

  public static String formatJson(String string) {
    String toReturn = string.replace(",", ",\n");
    toReturn = toReturn.replace("{", "{\n");
    toReturn = toReturn.replace("}", "\n}");
    return toReturn;
  }

  private void print(HttpServletResponse response, String text) throws IOException {
    response.getWriter().println(text);
  }

  public static String getAuthResponse(String authCode) throws IOException {
    CloseableHttpClient client = null;
    String responseStr = null;
    try {
      client = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

      HttpPost reqToAS = new HttpPost("https://www.googleapis.com/oauth2/v3/token");
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      nameValuePairs.add(new BasicNameValuePair("code", authCode));
      nameValuePairs.add(new BasicNameValuePair("client_id", CLIENT_ID));
      nameValuePairs.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
      nameValuePairs.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/dkom2015/oauth2endpoint.jsp"));
      nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
      reqToAS.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      HttpResponse asResponse = client.execute(reqToAS);

      responseStr = EntityUtils.toString(asResponse.getEntity());
      System.err.println(responseStr);
      return responseStr;
    } finally {
      if (client != null) {
        client.close();
      }
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
  }
  
  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(new String(Base64.decodeBase64("eyJhbGciOiJSUzI1NiIsImtpZCI6IjVmNzQ5MzY4OTVmMzVjMDNjMmZjZTY3NGFhY2IyNDk5ZWZkZjY5MTAifQ"), "UTF-8"));
  }

}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login via Google example</title>
</head>
<body>

<%
  String url = "https://accounts.google.com/o/oauth2/auth?" +
 "client_id=826972360704-pc2l8e4bvvg7e4a64nen8ofdsk41c60p.apps.googleusercontent.com&" +
 "response_type=code&" +
 "scope=openid%20email%20profile&" +
 "redirect_uri=http://localhost:8080/dkom2015/oauth2endpoint.jsp&" +
 "state=http://localhost:8080/dkom2015/protected.jsp&" +
 "login_hint=vasil.panushev@gmail.com";

  response.sendRedirect(url);
 %>
</body>
</html>
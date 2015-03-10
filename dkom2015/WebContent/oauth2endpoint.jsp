<%@page import="com.sap.dkom2015.openidconnect.AuthEndPoint"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.api.client.util.Base64"%>
<%@page import="com.sap.dkom2015.openidconnect.ASResponsePojo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Authentication End Point</title>
<%
	String state = request.getParameter("state");
	// String stateDecoded = URLDecoder.decode(state, "UTF-8");
	
	String code = request.getParameter("code");
	
	String authResponse = AuthEndPoint.getAuthResponse(code);
	
	Gson gson = new Gson();
	ASResponsePojo asResponsePojo = gson.fromJson(authResponse, ASResponsePojo.class);
	String[] encodedJwtTokenParts = asResponsePojo.getId_token().split("\\.");
	String[] jwtTokenParts = new String[3];
	jwtTokenParts[0] = new String(Base64.decodeBase64(encodedJwtTokenParts[0]), "UTF-8");
	jwtTokenParts[1] = new String(Base64.decodeBase64(encodedJwtTokenParts[1]), "UTF-8");
	jwtTokenParts[2] = encodedJwtTokenParts[2];
	
	System.err.println(authResponse);
	
	String userProfile = AuthEndPoint.getUserProfile(asResponsePojo);
	
	String formattedAuthnResponse = AuthEndPoint.formatJson(authResponse);
	String startTag = "<a href=\"#\" onClick=\"showIDToken()\">";
	String endTag = "</a>";
	
	int idTokenIndex = formattedAuthnResponse.indexOf("\"id_token\"");
	int idTokenEndIndex = formattedAuthnResponse.indexOf("\"", idTokenIndex + 15) + 1;
    String fr = formattedAuthnResponse.substring(0, idTokenIndex);
    fr += startTag;
    fr += formattedAuthnResponse.substring(idTokenIndex, idTokenEndIndex+1);
    fr += endTag;
    fr += formattedAuthnResponse.substring(idTokenEndIndex+1);
%>
<script type="text/javascript">
	function showAuthData() {
		//profileData
		document.getElementById("profileData").style.display = "block";
	}
	
	function showIDToken() {
		document.getElementById("idToken").style.display = "block";
	}
</script>
</head>
<body>

	<div id="authnresponse">
		<p><strong>Authorization response:</strong></p>
		<pre><%=fr%></pre>
		<hr/>
	</div>
	
	
	<div id="idToken" style="display: none">
		<p><strong>ID token:</strong></p>
		<p>Header</p>
		<pre>
<%=AuthEndPoint.formatJson(jwtTokenParts[0])%>
		</pre>
		<p>Body</p>
		<pre>
<%=AuthEndPoint.formatJson(jwtTokenParts[1])%>
		</pre>
		<p>Signature</p>
		<pre>
<%=AuthEndPoint.formatJson(jwtTokenParts[2])%>
		</pre>
		<br/>
		<button onClick="showAuthData()">Show User Profile Data</button>
		<hr/>
	</div>
	<div id="profileData" style="display: none">
		<p><strong>User profile data:</strong></p>
		<pre>
<%=AuthEndPoint.formatJson(userProfile) %>
		</pre>
	</div>
</body>
</html>
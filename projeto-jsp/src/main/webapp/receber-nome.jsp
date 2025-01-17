<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	
	String nome = request.getParameter("nome");
	out.println("nome: "+ nome);
	
	String idade = request.getParameter("idade");
	out.println("idade: " + idade);
		
	%>

</body>
</html>
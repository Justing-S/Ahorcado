<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int vidas=(int)request.getAttribute("vidasRestantes");
	int intentos=(int)request.getAttribute("intentosUsados");
	String palabraAleatoria=(String)request.getAttribute("palabraAleatoria");
	String letrasUsadas=(String)request.getAttribute("letrasUsadas");
	String ultimaLetra=(String)request.getAttribute("ultimaLetra");
	String[]celdas=(String[])request.getAttribute("celdas");
	String error=(String)request.getAttribute("error");
	String finJuego=(String)request.getAttribute("finJuego");
	String imagen=(String)request.getAttribute("imagen");
	
%>
<!DOCTYPE html>
<html>
<head>
<style>
	img{
		float:left;
		width:300px;
		margin-left:100px;
		margin-right:200px;
		margin-top:100px;
	}
	div{
		float:left;
		margin-top:100px;
		width:400px;
		text-align:center;
		margin-top:80px;
	}
	
	div.datos{
		border:2px solid black;
		border-radius:12px 12px;
		text-align:center;
	}
	
	div.juego{
		margin-top:10px;
		text-align:center;
	}
	
	p{
		font-weight:bold;
	}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 align="center">Juego del ahorcado</h1>
<img src="<%=imagen %>" style="left:100px" width="250"/>
<form action="Juego" method="post">
<div>
	<div class="datos">
		<%if(imagen.equals("./imagenes/pingu.png")){
			%><p>Palabra oculta: <%=palabraAleatoria %></p>
		<% }%>
		<p>Última letra recibida: <%=ultimaLetra %></p>
		<p>Letras ya probadas: <%=letrasUsadas %></p>
		<p>Número de intentos: <%=intentos %></p>
		<p>Intentos fallidos restantes: <%=vidas %></p>
	</div>
	<div class="juego">
		<%if(finJuego==null){
			%>
		<table width="100" align="center">
			<tr>
			<%
			for(int i=0;i<celdas.length;i++){
				%><td><%=celdas[i]%></td><%
				}
			%>
			</tr>
		</table><br/>
		
		<input type="text" name="letra" size="1" maxlength="1">
		<input type="submit" value="enviar">
		<br/>
		<%if(error!=null){
			%><p><%=error %>
		<% }%>
	</div>




<%}else{
	%><h2><%=finJuego %></h2>
		<a href="http://localhost:8080/Ahorcadov2/Juego?empezar=nuevo">Nuevo Juego</a>
<%} %>
</div>


</form>

</body>
</html>
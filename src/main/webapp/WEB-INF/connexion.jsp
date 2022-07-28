<%--
  Created by IntelliJ IDEA.
  User: Ordi
  Date: 20/07/2022
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <title>connexion</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

  <link href="${pageContext.request.contextPath }/css/styleConnexion.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath }/css/navFinal.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <div class="topnav" id="myTopnav">
        <a href="${pageContext.request.contextPath}/accueil" class="color">ENI-Enchères</a>
        <%--Si un utilisateur est connecte alors afficher les liens sinon afficher S'inscrire / Se connecter--%>
        <%
            if (session.getAttribute("pseudoUser") != null || session.getAttribute("pseudoUser")=="") {

        %>

        <div style="float:right !important;display:flex;align-items:center;">
            <a class="color-a" href="#news">Enchères</a>
            <a  class="color-a" href="${pageContext.request.contextPath}/nouvelleVente">Vendre un article</a>
            <a  class="color-a" href="${pageContext.request.contextPath}/profil">Mon profil</a>
            <form method="post"><button name="disconnect" type="submit" style="align-items:center;">Déconnexion</button></form>
        </div>

        <%
        }else{
        %>

        <div style="float:right !important;">
            <a  class="color-a" href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
            <a  class="color-a" href="${pageContext.request.contextPath}/connexion">Se connecter</a>
        </div>

        <%
            }
        %>


        <h1>Connexion</h1>
        <a href="javascript:void(0);" class="icon" onclick="myFunction()">
            <i class="fa fa-bars"></i>
        </a>
    </div>


</header>
<%@include file="connexion.html"%>
</body>
</html>

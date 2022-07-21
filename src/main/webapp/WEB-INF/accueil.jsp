        <%--
  Created by IntelliJ IDEA.
  User: Ordi
  Date: 20/07/2022
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath }/css/styleAccueil.css" rel="stylesheet" type="text/css">
    <title>Accueil</title>


</head>
<body>

<header>
    <div class="topnav" id="myTopnav">
        <a href="#home" class="active">ENI-Enchères</a>
<%--Si un utilisateur est connecte alors afficher les liens sinon afficher S'inscrire / Se connecter--%>
        <%
            if (session.getAttribute("pseudoUser") != null) {

        %>
        <div style="float:right !important;">
            <a href="#news">Enchères</a>
            <a href="#news">Vendre un article</a>
            <a href="#news">Mon profil</a>
            <button formmethod="POST" onclick="disconnect()">Déconnexion</button>
        </div>

        <%
        }else{
        %>
        <div style="float:right !important;">
            <a href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
            <a href="${pageContext.request.contextPath}/connexion">Se connecter</a>
        </div>
        <%
            }
        %>


        <h1>Liste des enchères</h1>
        <a href="javascript:void(0);" class="icon" onclick="myFunction()">
            <i class="fa fa-bars"></i>
        </a>
    </div>


</header>




<%@include file="accueil.html"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fonction.js"></script>
</body>
</html>

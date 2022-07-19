<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Listes de courses</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath }/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath }/css/4-col-portfolio.css"
	rel="stylesheet">

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body class="container">

	<header class="py-3 bg-dark header-demodule fixed-top">
		<div class="container text-center text-white">
			<h1>Courses</h1>
		</div>
	</header>

	<div class="col-12">
    <h2 class="my-5 text-center">Listes prédéfinies</h2>
    
    <c:if test="${!empty listes }">
	    <div class="row">
			<ul class="list-group col-12">
				<c:forEach var="l" items="${listes }">
					<li class="list-group-item d-flex justify-content-between align-items-center">${l.nom }
						<div>
							<a href="${pageContext.request.contextPath }/panier?id=${l.id}" 
								class="badge" title="Commencer ses courses"><i
								class="material-icons">shopping_cart</i></a>
								
							<a href="${pageContext.request.contextPath }/listes?id=${l.id}"
								class="badge text-danger" title="Supprimer"><i
								class="material-icons">delete</i></a>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
    </c:if>
    <c:if test="${empty listes }">
    	<h3>Pas de listes</h3>
    </c:if>

	<!-- Footer -->
	<footer class="row bg-dark footer-demodule fixed-bottom py-1">
		<div class="col-lg-4 offset-lg-4 text-center">
			<a class="btn" href="${pageContext.request.contextPath }/nouvelle"
				title="Créer une nouvelle liste"><i class="material-icons">add</i></a>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script
		src="${pageContext.request.contextPath }/vendor/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
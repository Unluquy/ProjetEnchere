<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Listes de courses - Nouvelle liste</title>

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
		<h2 class="my-5 text-center">Nouvelle liste</h2>


		<form action="${pageContext.request.contextPath }/nouvelle" 
			  class="row justify-content-center mb-2" method="POST">
			<label for="nom" class="col-2 col-form-label">Nom :</label>
			<div class="col-10">
				<c:choose>
				<c:when test="${!empty nouvelleListe }">
					<input class="form-control" type="text" name="nom_liste" id="nom" value="${nouvelleListe.nom }" disabled>
					<input type="hidden" name="id_liste" value="${nouvelleListe.id }">
				</c:when>
				<c:otherwise>
					<input class="form-control" type="text" name="nom_liste" id="nom">
				</c:otherwise>
				</c:choose>
				
			</div>
			
			<div class="row">
				<ul class="list-group col-12">
	
					<c:if test="${!empty nouvelleListe }">
						<c:forEach var="a" items="${nouvelleListe.articles }"> 
							<li class="list-group-item d-flex justify-content-between align-items-center">${a.nom }
								<div>
									<a href="${pageContext.request.contextPath }/nouvelle?id_liste=${nouvelleListe.id}&id_article=${a.id}" class="badge text-danger" title="Supprimer"><i class="material-icons">delete</i></a>
								</div>
							</li>
						</c:forEach>
					</c:if>
	
					<li
						class="list-group-item d-flex justify-content-between align-items-center">
						<input class="form-control" type="text" id="nom" name="nom_article">
						<div>
<!-- 							<a href="#add" class="badge text-success" title="Ajouter"><i
								class="material-icons">add</i></a> -->
							<input class="btn btn-success" type="submit" value="+">
						</div>
					</li>
				</ul>
			</div>
		
		</form>


		<div class="row py-5"></div>
	</div>



	<!-- Footer -->
	<footer class="row bg-dark footer-demodule fixed-bottom py-1">
		<div class="col-lg-4 offset-lg-4 text-center">
			<a class="btn" href="${pageContext.request.contextPath }/listes"
				title="Retour à la liste des courses"><i class="material-icons">arrow_back</i></a>
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
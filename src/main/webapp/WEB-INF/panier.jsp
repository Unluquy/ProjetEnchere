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

<title>Listes de courses - Panier</title>

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath }/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath }/css/4-col-portfolio.css" rel="stylesheet">
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
		<h2 class="my-5 text-center">Votre panier <small>${uneListe.nom }</small></h2>

		<div class="row">
			<div class="col-lg-4"></div>
			<ul class="list-group col col-lg-4">
				
				<c:forEach var="a" items="${uneListe.articles}">
					<li class="list-group-item d-flex justify-content-between align-items-center">
						<div>
							<form action="${pageContext.request.contextPath }/panier" method="get" id="form${a.id }">
								<label class="custom-control custom-checkbox">
									<input type="hidden" name="id" value="${uneListe.id }"/>
									<input type="hidden" name="id_article" value="${a.id }"/>
									<c:choose>
										<c:when test="${a.estCoche }">
											<input type="checkbox" name="coche" class="custom-control-input"
							  					onclick="document.getElementById('form${a.id}').submit();" checked>
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="coche" class="custom-control-input"
							  					onclick="document.getElementById('form${a.id}').submit();">
										</c:otherwise>
									</c:choose>
	
								  	<span class="custom-control-indicator"></span>
							  		<span class="custom-control-description">${a.nom }</span>
								</label>
							</form>
						</div>
					</li>
				
				</c:forEach>
				
			</ul>
			<div class="col-lg-4"></div>
		</div>
		<div class="row py-5"></div>
	</div>

	<!-- Footer -->
    <footer class="row bg-dark footer-demodule fixed-bottom py-1">
		      <div class="text-center col-2 offset-3">
		        <a class="btn" href="${pageContext.request.contextPath }/listes" title="Retour à la liste des courses"><i class="material-icons">arrow_back</i></a>
		      </div>
		      <!-- /.container -->
		<div class="text-center col-2 offset-2">
			<a class="btn" href="${pageContext.request.contextPath }/clear?id=${uneListe.id}" title="clear"><i class="material-icons">clear</i></a>
		</div>
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="${pageContext.request.contextPath }/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Ordi
  Date: 19/07/2022
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath }/css/pageConnect.css" rel="stylesheet" type="text/css">

    <title>JSP - Connect</title>

</head>
<body>

    <p style="items-center:center;text-align:center;text-color:red">${errorString}</p>
    <%@include file="pageConnect.html"%>


</body>
</html>

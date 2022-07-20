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
    <title>JSP - Connect</title>
    <link href="${pageContext.request.contextPath }/css/pageConnect.css" rel="stylesheet" type="text/css">
</head>
<body>

    <p style="items-center:center;text-align:center;text-color:red">${errorString}</p>
    <%@include file="pageConnect.html"%>


</body>
</html>

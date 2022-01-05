<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Reports</title>
</head>
<body>
<%@ include file="header.jsp" %>
<c:if test="${not empty sessionScope.report}">
    <h1>Time report:</h1>
    <ul>
        <c:forEach var="report" items="${sessionScope.report.get(0)}">
            ${report}<br>
        </c:forEach>
    </ul>
    <h1>Statistic report:</h1>
    <ul>
        <c:forEach var="report" items="${sessionScope.report.get(1)}">
            ${report}<br>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>

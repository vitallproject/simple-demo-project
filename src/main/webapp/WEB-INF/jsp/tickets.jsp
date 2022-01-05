<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tickets</title>
</head>
<body>
<%@ include file="header.jsp"%>
<c:if test="${not empty requestScope.tickets}">
    <h1><fmt:message key="page.tickets"/>:</h1>
    <ul>
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>${ticket.seatNo}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<img src="${pageContext.request.contextPath}/images/Downloads/IMG_7814.JPG" alt="User image">
<form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
    <label for="nameId">Name:
        <input type="text" name="name" id="nameId">
    </label><br>
    <label for="birthdayId">Birthday:
        <input type="date" name="birthday" id="birthdayId" required>
    </label><br>
    <label for="imageId">Image:
        <input type="file" name="image" id="imageId">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId" required>
    </label><br>
    <label for="passwordId">Password:
        <input type="password" name="password" id="passwordId" required>
    </label><br>
    <c:forEach var="gender" items="${requestScope.gender}">
        <input type="radio" name="gender" value="${gender}"> ${gender}
    </c:forEach>
    <br>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
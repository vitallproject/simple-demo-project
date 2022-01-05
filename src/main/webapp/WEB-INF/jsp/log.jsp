<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LogFile</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/log" method="post" enctype="multipart/form-data">
    <label for="logId">Upload log file (txt):
        <input type="file" name="log" id="logId">
    </label><br>
    <button type="submit">Convert</button>
</form>
</body>
</html>

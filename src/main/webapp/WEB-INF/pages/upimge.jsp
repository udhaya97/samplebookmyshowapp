<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Upload Image</h3>
<form:form action="/uploadImage" method="post" enctype="multipart/form-data" modelAttribute="uplo">
<table>

<tr><td>Name</td><td><input type="text" name="name" required="required"></td></tr>
<tr><td>File</td><td><input type="file" name="file" required="required"></td></tr>
<tr><td></td><td><input type="submit"></td></tr>

</table>




</form:form>

</body>
</html>
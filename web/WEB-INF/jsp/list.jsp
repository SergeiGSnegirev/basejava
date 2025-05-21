<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<h2>Список резюме</h2>
<section>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%= ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL)) %>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">&nbsp;<img src="img/pencil.png" alt="Edit"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="Delete"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <title>Список всех резюме</title>
    <style>
        table, th, td {
            padding: 5px 10px;
            margin: 20px 0;
        }

        tr:nth-child(even) {
            background-color: #dbe7f1;
        }
    </style>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<h2 style="margin: 15px">Список резюме</h2>
<section>
    <a href="resume?action=add" style="margin: 15px"><img src="img/add.png" alt="Add" title="Добавить резюме"></a>
    <hr>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Телефон</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%= ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL)) %>
                </td>
                <td><%= ContactType.MOBILE_PHONE.toHtml(resume.getContact(ContactType.MOBILE_PHONE)) %>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">&nbsp;
                    <img src="img/pencil.png" title="Редактировать резюме" alt="Edit"></a>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"
                       onclick="return confirm('Вы уверены, что хотите удалить резюме?');">
                    <img src="img/trash.png" title="Удалить резюме" alt="Delete"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

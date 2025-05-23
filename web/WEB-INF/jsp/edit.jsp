<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">

        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><b>Имя</b></dt>
            <dd><input type="text" name="fullName" required pattern="[a-zA-Zа-яёА-ЯЁ]{1,}.*"
                       title="Имя, начинается с буквы" size=30 value="${resume.fullName}">
            </dd>
        </dl>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%= ContactType.values() %>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}"
                           title="Контакт ${type.title}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <c:forEach var="type" items="<%= SectionType.values() %>">

            <c:if test="${type != 'EXPERIENCE' && type != 'EDUCATION'}"> <%--stub --%>

                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
                <h3>${type.title}</h3>
                <c:choose>

                    <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                        <textarea name="${type}" title="${type.title}" rows="4"><%= section %></textarea>
                    </c:when>

                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                        <textarea name="${type}" title="${type.title}"
                                  rows="10"><%= String.join("\n", ((ListSection) section).getItems()) %></textarea>
                    </c:when>

                </c:choose>
            </c:if>
        </c:forEach>

        <button class="button" type="submit">Сохранить</button>
        <button class="button" type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
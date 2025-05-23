<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.Section" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">
        <img src="img/pencil.png" title="Редактировать резюме" alt="Edit">
    </a></h1>

    <c:if test="${resume.contacts.size() > 0}">
        <h3>Контакты</h3>
        <ul style="list-style-type: none">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <li><%= contactEntry.getKey().toHtml(contactEntry.getValue()) %>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <hr>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <h3>${type.title}</h3>

        <c:choose>

            <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                <p style="margin-left: 40px"><c:out value="${sectionEntry.value}"/></p>
            </c:when>

            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                <c:set var="listSection" value="${sectionEntry.value}"/>
                <jsp:useBean id="listSection" type="com.urise.webapp.model.ListSection"/>
                <ul>
                    <c:forEach var="item" items="${listSection.items}">
                        <li><c:out value="${item}"/></li>
                    </c:forEach>
                </ul>
            </c:when>

            <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                <c:set var="organizationSection" value="${sectionEntry.value}"/>
                <jsp:useBean id="organizationSection" type="com.urise.webapp.model.OrganizationSection"/>
                <ul>
                    <c:forEach var="organization" items="${organizationSection.organizations}">
                        <p><b>
                            <c:choose>
                                <c:when test="${organization.homePage.url == null}">
                                    <c:out value="${organization.homePage.name}"/>
                                </c:when>
                                <c:otherwise>
                                    <a href="${organization.homePage.url}">${organization.homePage.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </b></p>
                        <table style="width:100%">
                            <c:forEach var="period" items="${organization.periods}">
                                <tr>
                                    <td style="width: 150px">
                                        <c:out value="${period.startDate.format(DateTimeFormatter.ofPattern('MM/yyyy - '))}"/>
                                        <c:out value="${period.startDate.format(DateTimeFormatter.ofPattern('MM/yyyy'))}"/>
                                    </td>
                                    <td>
                                        <c:out value="${period.title}"/>
                                    </td>
                                </tr>
                                <c:if test="${!period.description.toString().isEmpty()}">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <c:out value="${period.description}"/>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </c:forEach>
                </ul>
            </c:when>
        </c:choose>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

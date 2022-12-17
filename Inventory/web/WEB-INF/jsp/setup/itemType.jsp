<%@ page pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>
	<spring:message code="label.itemTypeManagement"/>
	<a style="float:right" href="${contextPath}/itemType/list"><img style="vertical-align:middle" src="<c:url value="/resources/images/back.png" />">&nbsp;<spring:message code="label.backToList"/></a>
</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/itemType"></c:url>
<form:form action="${formAction}" modelAttribute="itemType">
	<form:hidden path="id" />
	<p>
		<label for="nameInput"><spring:message code="label.name"/><span class="mandatory">*</span></label>
		<form:input path="name" id="nameInput" />
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="codeInput"><spring:message code="label.code"/><span class="mandatory">*</span></label>
		<form:input path="code" id="codeInput" />
		<form:errors path="code" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="codeInput"><spring:message code="label.lowMark"/><span class="mandatory">*</span></label>
		<form:input path="lowMark" id="codeInput" />
		<form:errors path="lowMark" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="codeInput"><spring:message code="label.highMark"/><span class="mandatory">*</span></label>
		<form:input path="highMark" id="codeInput" />
		<form:errors path="highMark" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="descriptionInput"><spring:message code="label.description"/> </label>
		<form:textarea   style="vertical-align: middle" rows="1" cols="50" path="description" id="descriptionInput" />
		<form:errors path="description" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="<spring:message code="label.save"/>" />
	</p>
</form:form>
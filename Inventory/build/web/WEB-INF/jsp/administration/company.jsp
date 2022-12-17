<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript">
	$(function() {
		$("#userNameInput").focus();
	});
</script>
<style type="text/css">
</style>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3><spring:message code="label.companyInfo"/></h3>
<c:url var="formAction" value="/company"></c:url>
<form:form method="POST" action="${formAction}" modelAttribute="company">
	<form:hidden path="id"/>
	<p>
		<label for="name"><spring:message code="label.name"/></label>
		<form:input path="name"/>
		<form:errors path="name" cssClass="error"></form:errors>
	</p>

	<p>
		<label for="proprietor"><spring:message code="label.proprietor"/> </label>
		<form:input path="proprietor"/>
		<form:errors path="proprietor" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="businessInfo"><spring:message code="label.businessInfo"/></label>
		<form:input path="businessInfo"/>
		<form:errors path="businessInfo" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="address"><spring:message code="label.address"/></label>
		<form:input path="address"/>
		<form:errors path="address" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="contactInfo"><spring:message code="label.contact"/></label>
		<form:input path="contactInfo"/>
		<form:errors path="contactInfo" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="<spring:message code="label.save"/>" />
	</p>
</form:form>
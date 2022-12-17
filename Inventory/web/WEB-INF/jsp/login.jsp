<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
	$(function() {
		$("#userName").focus();
	});
</script>
<style>
#login {
	margin: 0px auto;
	border: 2px solid lightgray;
	padding: 20px;
	/* background: #EEE; */ 
	width: 400px;
	/*margin-top: 50px;*/
}

#msg {
	border: 1px solid red;
	padding: 5px;
}
</style>
<c:url var="formAction" value="/login-post"></c:url>
<div id="login">
	<c:if test="${!empty msg}">
		<p id="msg">${msg}</p>
	</c:if>
	<h3><spring:message code="label.login"/></h3>
	<form:form method="POST" action="${formAction}" commandName="customLogin">
		<p>
			<label><spring:message code="label.username"/></label>
			<form:input path="userName" />
		</p>
		<p>
			<label><spring:message code="label.password"/></label>
			<form:password path="password" />
		</p>
		<p>
			<label></label>
			<input type="submit" value="<spring:message code="label.login"/>">
			<input type="reset" value="<spring:message code="label.reset"/>">
		</p>
	</form:form>
</div>

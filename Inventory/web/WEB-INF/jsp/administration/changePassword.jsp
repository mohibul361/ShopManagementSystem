<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	$(function() {
		$("#userNameInput").focus();
	});
</script>
<style type="text/css">
#password-policy {
	width: 300px;
	float: right;
	background: lightyellow;
	padding: 10px;
	margin-right: 300px
}
</style>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Change Password</h3>
<c:if test="${not empty msg}">
	<div class="message green">${msg}</div>
</c:if>
<c:url var="formAction" value="/changePassword"></c:url>
<form:form method="POST" action="${formAction}" commandName="changePassword">
	<%-- <form:form action="user" method="POST"> --%>
	<div id="password-policy">
		Password Policy
		<ul>
			<li>Passwords must contain at least six characters</li>
			<li>It must include uppercase, lowercase letters and numbers.</li>
		</ul>
	</div>
	<p>
		<label for="oldPasswordInput">Current Password: </label>
		<form:password path="oldPassword" id="oldPasswordInput" />
		<form:errors path="oldPassword" cssClass="error"></form:errors>
	</p>

	<p>
		<label for="passwordInput">New Password: </label>
		<form:password path="password" id="passwordInput" />
		<form:errors path="password" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="confirmPasswordInput">Re-enter Password: </label>
		<form:password path="confirmPassword" id="confirmPasswordInput" />
		<form:errors path="confirmPassword" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="Save" /> <input type="reset" value="Reset" />
	</p>
</form:form>

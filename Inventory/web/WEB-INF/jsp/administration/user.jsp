<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	$(function() {
		$("#userNameInput").focus();
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit User</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/user"></c:url>
<form:form action="${formAction}" modelAttribute="user">
	<form:hidden path="id" />
	<p>
		<label for="userNameInput">Name: </label>
		<form:input path="userName" id="userNameInput" />
		<form:errors path="userName" cssclass="error"></form:errors>
	</p>
	<p>
		<label for="passwordInput">password: </label>
		<form:input path="password" id="passwordInput" />
		<form:errors path="password" cssclass="error"></form:errors>
	</p>
	<p>
		<label for="fullNameInput">Full Name: </label>
		<form:input path="fullName" id="fullNameInput" />
		<form:errors path="fullName" cssclass="error"></form:errors>
	</p>
	<p>
		<label for="roleInput">Role: </label>
		<form:select path="role">
			<form:option value="" label="Select Role"></form:option>
			<form:options items="${roleList}" itemValue="id" itemLabel="roleName"></form:options>
		</form:select>
		<form:errors path="role" cssclass="error"></form:errors>
	</p>
	<p>
		<label for="active">Active</label>
		<form:checkbox path="active" id="active" />
	</p>
	<p>
		<label></label> <input type="submit" value="Save" /> <input type="reset" value="Reset" />
	</p>
</form:form>
<h3>User List</h3>

<table id="global-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>Role</th>
			<th>Active</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td><c:out value="${user.userName}"></c:out></td>
				<td><c:out value="${user.role.roleName}"></c:out></td>
				<td><c:out value="${user.active}"></c:out></td>
				<td><a href="${contextPath}/admin/user/${user.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	$(function() {
		$("#loginInput").focus();
	});
</script>

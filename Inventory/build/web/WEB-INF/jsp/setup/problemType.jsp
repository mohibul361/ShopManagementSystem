<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit Problem Type</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/problemType"></c:url>
<form:form action="${formAction}" modelAttribute="problemType">
	<form:hidden path="id" />
	<p>
		<label for="nameInput">Name: </label>
		<form:input path="name" id="nameInput" />
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label> <input type="submit" value="Submit" />
	</p>
</form:form>
<h3>ProblemType List</h3>

<table id="global-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="problemType" items="${problemTypeList}">
			<tr>
				<td><c:out value="${problemType.name}"></c:out></td>
				<td><a href="${contextPath}/problemType/${problemType.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	$(function() {
		$("#nameInput").focus();
	});
</script>

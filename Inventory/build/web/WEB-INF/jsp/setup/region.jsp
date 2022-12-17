<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit Region</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/region"></c:url>
<form:form action="${formAction}" modelAttribute="region">
	<form:hidden path="id" />
	<p>
		<label for="nameInput">Name: </label>
		<form:input path="name" id="nameInput" />
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="descriptionInput">Description: </label>
		<form:textarea style="vertical-align: middle" rows="1" cols="50" path="description" id="descriptionInput" />
		<form:errors path="description" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label> <input type="submit" value="Submit" />
	</p>
</form:form>
<h3>Region List</h3>

<table id="global-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="region" items="${regionList}">
			<tr>
				<td><c:out value="${region.name}"></c:out></td>
				<td><c:out value="${region.description}"></c:out></td>
				<td><a href="${contextPath}/region/${region.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	$(function() {
		$("#nameInput").focus();
	});
</script>

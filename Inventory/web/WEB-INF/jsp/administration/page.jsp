<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
	$(function() {
		//define a json object
	/* 	var employee = {
			"name" : "John Johnson",
			"street" : "Oslo West 16",
			"phone" : "555 1234567"
		};

		//use JSON.stringify to convert it to json string
		var jsonstring = JSON.stringify(employee);
		$("#result").append('<p>json string: ' + jsonstring + '</p>')

		//convert json string to json object using JSON.parse function

		/*var jsonobject = JSON.parse(jsonstring);
		var info = '<ul><li>Name:' + jsonobject.name + '</li><li>Street:'
				+ jsonobject.street + '</li><li>Phone:' + jsonobject.phone
				+ '</li></ul>'

		$("#result").append('<p>json object:</p>');
		$("#result").append(info); */
	})
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit Page</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/page"></c:url>
<form:form action="${formAction}" modelAttribute="page">
	<form:hidden path="id" />
	<p>
		<label for="parentPageInput">Parent page: </label>
		<form:select path="parentPage">
			<form:option value="" label="Select Type"></form:option>
			<form:options items="${parentPageList}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
		<form:errors path="parentPage" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="nameInput">Name: </label>
		<form:input path="name" id="nameInput" />
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="nameInput">Name (Bengali): </label>
		<form:input path="nameBn" id="nameInput" />
		<form:errors path="nameBn" cssClass="error"></form:errors>
	</p>
	
	<p>
		<label for="urlInput">Url: </label>
		<form:input path="url" id="urlInput" />
		<form:errors path="url" cssClass="error"></form:errors>
	</p>

	<p>
		<label for="pageOrderInput">Page order: </label>
		<form:input path="pageOrder" id="pageOrderInput" />
		<form:errors path="pageOrder" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="Save" />
	</p>
</form:form>
<h3>Page List</h3>

<table id="global-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>Name (Bengali)</th>
			<th>Url</th>
			<th>Page order</th>
			<th>Parent page</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="page" items="${pageList}">
			<tr>
				<td><c:out value="${page.name}"></c:out></td>
				<td><c:out value="${page.nameBn}"></c:out></td>
				<td><c:out value="${page.url}"></c:out></td>
				<td><c:out value="${page.pageOrder}"></c:out></td>
				<td><c:out value="${page.parentPage.name}"></c:out></td>
				<td><a href="${contextPath}/page/${page.id}">Edit</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="result"></div>
<script type="text/javascript">
	$(function() {
		$("#nameInput").focus();
	});
</script>

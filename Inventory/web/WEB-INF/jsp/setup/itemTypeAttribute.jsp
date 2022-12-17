<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
	$(function() {
		("#nameInput").focus();
	})
	function deleteAttributeValue(attributeId)
		{
			if(confirm('Do you want to delete this Attribute ?')){
				var url = '${pageContext.request.contextPath}/itemTypeAttribute/delete/'+attributeId;
				window.location.href = url;
			}
		}
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit ItemType Attribute</h3>
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<c:url var="formAction" value="/itemTypeAttribute"></c:url>
<form:form action="${formAction}" modelAttribute="itemTypeAttribute">
	<form:hidden path="id" />
	<p>
		<label for="typeInput">ItemType: </label>
		<form:select path="itemType">
			<%-- <form:option value="" label="Select Type"></form:option> --%>
			<form:options items="${itemTypeList}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
		<form:errors path="itemType" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="nameInput">Attribute Name: </label>
		<form:input path="name" id="nameInput" />
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="typeInput">Type: </label>
		<form:select path="attributeType" id="typeInput">
			<form:option value="" label="Select Type"></form:option>
			<form:options items="${nothing_required}"></form:options>
		</form:select>
		<form:errors path="attributeType" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="serialInput">Serial: </label>
		<form:input path="serial" id="serialInput" />
		<form:errors path="serial" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="Submit" />
	</p>
</form:form>
<h3>Attribute List</h3>
<c:choose>
	<c:when test="${!empty itemTypeAttributeList}">
		<table id="global-table">
			<thead>
				<tr>
					<th>ItemType</th>
					<th>Name</th>
					<th>Type</th>
					<th>Serial</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="itemTypeAttribute" items="${itemTypeAttributeList}">
					<tr>
						<td><c:out value="${itemTypeAttribute.itemType.name}"></c:out></td>
						<td><c:out value="${itemTypeAttribute.name}"></c:out></td>
						<td><c:out value="${itemTypeAttribute.attributeType}"></c:out></td>
						<td><c:out value="${itemTypeAttribute.serial}"></c:out></td>
						<td><a href="${contextPath}/itemTypeAttribute/${itemTypeAttribute.id}">Edit</a> <c:if test="${itemTypeAttribute.attributeType eq 'COMBO'}">
						&nbsp;||&nbsp;<a href="${contextPath}/itemTypeAttributeComboData/attribute/${itemTypeAttribute.id}">Add Attribute Value</a>&nbsp;||&nbsp; 
						<a href="#" onclick="deleteAttributeValue(${itemTypeAttribute.id})">Delete</a></c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
       No data found
	</c:otherwise>
</c:choose>


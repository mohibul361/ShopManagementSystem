<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
	$(function() {
		$("#valueInput").focus();
	});
	function deleteAttributeValue(attrValueId)
		{
			if(confirm('Do you want to delete this Value ?')){
				var url = '${pageContext.request.contextPath}/delete/'+attrValueId;
				window.location.href = url;
			}
		}
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add/Edit ItemType Attribute Combo Data for [${itemTypeAttributeComboData.itemTypeAttribute.itemType.name}:${itemTypeAttributeComboData.itemTypeAttribute.name}]</h3>
<div style="float: right">
	<a href="${contextPath}/itemTypeAttribute">Back to ItemType Attribute Setup</a>
</div>
<c:url var="formAction" value="/itemTypeAttributeComboData"></c:url>
<form:form action="${formAction}" modelAttribute="itemTypeAttributeComboData">
	<form:hidden path="id" />
	<form:hidden path="itemTypeAttribute" value="${itemTypeAttributeComboData.itemTypeAttribute.id}" />
	<p>
		<label for="valueInput">Value: </label>
		<form:input path="value" id="valueInput" />
		<form:errors path="value" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="Submit" />
	</p>
</form:form>
<h3>Attribute Value List</h3>
<c:choose>
	<c:when test="${!empty itemTypeAttributeComboDataList}">
		<table id="global-table">
			<thead>
				<tr>
					<th>Value</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="itemTypeAttributeComboData" items="${itemTypeAttributeComboDataList}">
					<tr>
						<td><c:out value="${itemTypeAttributeComboData.value}"></c:out></td>
						<td><a href="${contextPath}/itemTypeAttributeComboData/${itemTypeAttributeComboData.id}">Edit</a>&nbsp;||&nbsp; <a href="#"
							onclick="deleteAttributeValue(${itemTypeAttributeComboData.id})">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
       No data found
	</c:otherwise>
</c:choose>

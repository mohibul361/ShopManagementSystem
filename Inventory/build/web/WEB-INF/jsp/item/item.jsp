<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<script type="text/javascript">
	$(function() {
		$('#slipDateInput').datepicker({
			dateFormat : 'dd/mm/yy'
		}).datepicker("setDate", new Date());
		$("#itemDetails").hide();
		$("#itemType")
				.change(
						function() {
							var id = $('option:selected', this).val();
							if (!id) {
								$("#itemAttributes").html("");
								return;
							}
							var itemId = $("#itemId").val();
							var reqUrl;
							if (!itemId) {
								reqUrl = "${pageContext.request.contextPath}/attributeData/"
										+ id;
							} else {
								reqUrl = "${pageContext.request.contextPath}/attributeData/"
										+ id + "/item/" + itemId;
							}

							$.ajax({
								url : reqUrl,
								type : 'GET',
								dataType : 'html',
								success : function(data) {
									$("#itemAttributes").html(data);
								},
								error : function(data, status, er) {
									console.log(data);
									alert("error: " + data + " status: "
											+ status + " er:" + er);
								}
							});
						});
		$('#itemType').trigger('change');
		$('#addItem').click(function(event) {
			$("#itemDetails").show();
		});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Item Setup</h3>
<c:url var="actionUrl" value="/item" />
<form:form action="${actionUrl}" modelAttribute="item" method="POST">
	<form:hidden id="itemId" path="id" />
	<div>
		<label for="typeInput">ItemType: </label>
		<form:select path="itemType">
			<%-- <form:option value="" label="Select Type"></form:option> --%>
			<form:options items="${itemTypeList}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
		<form:errors path="itemType" cssclass="error"></form:errors>
	</div>
	<div>
		<form:label path="name">Name</form:label>
		<form:input path="name" />
		<form:errors path="name" />
	</div>
	<div>
		<form:label path="quantity">Quantity</form:label>
		<form:input path="quantity" />
		<form:errors path="quantity" />
	</div>
	<!-- <div>
		<h4>Item Attributes</h4>
	</div> -->
	<div id="itemAttributes"></div>
	<div>
		<button type="submit">Save</button>
	</div>
</form:form>
<h3>Item List</h3>
<c:choose>
	<c:when test="${!empty itemList}">
		<table id="global-table">
			<thead>
				<tr>
					<th>ItemType</th>
					<th>Name</th>
					<th>Quantity</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${itemList}">
					<tr>
						<td><c:out value="${item.itemType.name}"></c:out></td>
						<td><c:out value="${item.name}"></c:out></td>
						<td><c:out value="${item.quantity}"></c:out></td>
						<td><a href="${contextPath}/item/${item.id}">Edit</a> &nbsp;||&nbsp;<a href="${contextPath}/item/${item.id}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
       No data found
	</c:otherwise>
</c:choose>


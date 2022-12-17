<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<script type="text/javascript">
	$(function() {
		$('#slipDateInput').datepicker({
			dateFormat : 'dd/mm/yy'
		}).datepicker("setDate", new Date());
		$("#itemGeneration").hide();
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
									$("#itemGeneration").show();
								},
								error : function(data, status, er) {
									console.log(data);
									alert("error: " + data + " status: "
											+ status + " er:" + er);
								}
							});
						});
		$('#itemType').trigger('change');
		$('#generate').click(
				function(event) {
					var count = $("#quantity").val();
					$.ajax({
						url : "${pageContext.request.contextPath}/generate/"
								+ count,
						type : 'GET',
						dataType : 'html',
						success : function(data) {

							$("tbody").html("").append(data);

						},
						error : function(data, status, er) {
							console.log(data);
							alert("error: " + data + " status: " + status
									+ " er:" + er);
						}
					});
				});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Add Item</h3>
<c:url var="actionUrl" value="/itemForm" />
<div id="itemGeneration">
		<input type="text" id="quantity" placeholder="quantity"> <input type="submit" id="generate" value="geneate">
	</div>
<form:form action="${actionUrl}" modelAttribute="itemForm" method="POST">
<%-- 	<div>
		<label for="typeInput">ItemType: </label>
		<select name="itemType" id="itemType">
			<option value="" label="Select Type"></option>
			<c:forEach items="${itemTypeList}" var="itemType">
				<option value="${itemType.id}">${itemType.name}</option>
				<form:options items="${itemTypeList}" itemValue="id" itemLabel="name"></form:options>
			</c:forEach>
		</select>
	</div>
 --%>	
 	<div>
		<label for="typeInput">ItemType: </label>
		<form:select path="itemType">
			<%-- <form:option value="" label="Select Type"></form:option> --%>
			<form:options items="${itemTypeList}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
		<form:errors path="itemType" cssclass="error"></form:errors>
	</div>
	<div id="itemAttributes"></div>
	
	<h3>Item List</h3>

	<table id="global-table">
		<thead>
			<tr>
				<th>No.</th>
				<th>Serial</th>
				<th>Quantity</th>
			</tr>
		</thead>
		<tbody></tbody>
		<%-- <c:forEach items="${itemForm.items}" var="item" varStatus="status">
			<tr>
				<td align="center">${status.count}</td>
				<td><input name="items[${status.index}].serial" value="" /></td>
				<td><input name="items[${status.index}].quantity" value="" /></td>
			</tr>
		</c:forEach> --%>
	</table>
	<div>
		<input type="submit">
	</div>
</form:form>


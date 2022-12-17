<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
body {
	width: 100%;
}
</style>
<script>
	function myFunction() {
		var input, filter, table, tr, td, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("global-table");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1]; // name

			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	};
	$(function() {
		$(".select").click(function() {
			var $row = $(this).closest("tr"); // Find the row
			var id = $row.find(".id").text(); // Find the id
	
			$.ajax({
				url : "${pageContext.request.contextPath}/recipientByAjax/"+id,						
				type : 'GET',
				dataType : 'html',
				success : function(response) {
					console.log(response);
					var obj = JSON.parse(response);
					window.opener.document.getElementById('deliveredTo').value = obj.id;
					/* window.opener.document.getElementById('customerName').value = obj.name; */
					window.opener.$("#customerName").val(obj.name).change();					
					self.close();
				},
				error : function(data, status, er) {
					console.log(data);
					alert("error: " + data
							+ " status: " + status
							+ " er:" + er);
					}
				});
		});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>
	<spring:message code="label.recipientList" />
</h3>
<input type="text" id="myInput" onkeyup="myFunction()"
	placeholder="Search for names.." title="Type in a name">
<br>
<table id="global-table">
	<thead>
		<tr>
			<th>id</th>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.mobileNo1" /></th>
			<th><spring:message code="label.mobileNo2" /></th>
			<th><spring:message code="label.address" /></th>
			<th><spring:message code="label.action" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="recipient" items="${recipientList}">
			<tr>
				<td class="id"><c:out value="${recipient.id}"></c:out></td>
				<td><c:out value="${recipient.name}"></c:out></td>
				<td><c:out value="${recipient.mobileNo1}"></c:out></td>
				<td><c:out value="${recipient.mobileNo2}"></c:out></td>
				<td><c:out value="${recipient.address}"></c:out></td>
				<td><a href="#" class="select">Select</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	
</script>

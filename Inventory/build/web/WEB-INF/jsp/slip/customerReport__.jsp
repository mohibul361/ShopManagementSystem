<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
</style>
<script src="<c:url value="/resources/scripts/popup.js" />"></script>
<script type="text/javascript">
	var path = '${pageContext.request.contextPath}';
	$(function() {
		$('#fromDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());

		$('#toDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());

		$('#toDateInputForCB').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());

		$('#submitCLForm').submit(function(e) {
			var frm = $('#submitCLForm');
			e.preventDefault();

			var data = {}
			var Form = this;

			//Gather Data also remove undefined keys(buttons)
			$.each(this, function(i, v) {
				var input = $(v);
				data[input.attr("name")] = input.val();
				delete data["undefined"];
			});
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : frm.attr('method'),
				url : frm.attr('action'),
				dataType : 'json',
				data : JSON.stringify(data),
				success : function(callback) {
					alert("Response: Name: " + callback);
					$(this).html("Success!");
				},
				error : function() {
					$(this).html("Error!");
				}
			});
		});
		$('#submitCBForm').submit(function(e) {
			var frm = $('#submitCBForm');
			e.preventDefault();

			var data = {}
			var Form = this;

			//Gather Data also remove undefined keys(buttons)
			$.each(this, function(i, v) {
				var input = $(v);
				data[input.attr("name")] = input.val();
				delete data["undefined"];
			});
			alert("hello");
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : frm.attr('method'),
				url : frm.attr('action'),
				dataType : 'json',
				data : JSON.stringify(data),
				success : function() {
					alert("Response: Name: ");
					$(this).html("Success!");
				},
				error : function(xhr, textStatus, errorThrown) {
					alert(xhr.responseText);
					alert(textStatus);
					alert(errorThrown);

					//$(this).html(xhr.responseText);
				}
			});
		});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>
	<spring:message code="label.customerReport" />
</h3>
<fieldset style="width: 45%; float: left;">
	<legend>
		<spring:message code="label.customerLedger" />
	</legend>
	<form method="post" action="/Inventory/viewCLedger" id="submitCLForm">
	<%-- <form method="get" action="/Inventory/viewCustomerBalance"> --%>
		<p>
			<%-- <label for="recipient"><spring:message code="label.recipient" /></label>
			<select name="recipient" id="recipient">
				<option value=""><spring:message code="label.selectOne" /></option>
				<c:forEach items="${recipientList}" var="eachItem">
					<option value="${eachItem.id}"
						<c:if test="${eachItem.id eq recipientId}"> selected="selected"</c:if>>${eachItem.name}</option>
				</c:forEach>
			</select> --%>			
			<label for="recipientInput"><spring:message code="label.recipient" /></label> 
			<input type="hidden" id="deliveredTo" name="recipient">			
			<input type="text" id="customer-name" value="" readonly="readonly"> 
			<a href="${contextPath}/recipient/list" onClick="return PopupCenter(this, '', 800, 800)"><img style="height: 25px; vertical-align: middle" src="<c:url value="/resources/images/search.png" />"></a>
		</p>
		<p>
			<label for="fromDateInput"><spring:message
					code="label.fromDate" /></label> <input name="fromDate" id="fromDateInput" />
		</p>
		<p>
			<label for="toDateInput"><spring:message code="label.toDate" /></label>
			<input name="toDate" id="toDateInput" />
		</p>
		<%-- <label></label> <input type="submit" name="submitCLForm"
			value="<spring:message code="label.viewLedger"/>">
 --%>
		<label></label> <input type="submit" value="<spring:message code="label.viewLedger"/>">
	</form>
</fieldset>
<fieldset style="width: 45%; float: right;">
	<legend>
		<spring:message code="label.customerBalance" />
	</legend>
	<form method="post" action="/Inventory/viewCBalance" id="submitCBForm">
	<%-- <form method="get" action="/Inventory/viewCustomerBalance"> --%>
		<p>
			<label for="toDateInputForCB"><spring:message
					code="label.toDate" /></label> <input name="toDateForCB"
				id="toDateInputForCB" />
		</p>
		<%-- <label></label> <input type="submit" name="submitCBForm"
			value="<spring:message code="label.viewBalance"/>"> --%>
		<label></label> <input type="submit" value="<spring:message code="label.viewBalance"/>">
	</form>
</fieldset>
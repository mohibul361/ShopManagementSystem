<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">	
</style>

<script type="text/javascript">
	var path = '${pageContext.request.contextPath}';
	$(function() {
		$("#export").click(
				function() {
					var itemTypeId = $('#itemType option:selected').val();
					var recipientId = $('#recipient option:selected').val();

					window.location.href = path
							+ "/export/deliveryReport?itemTypeId=" + itemTypeId
							+ "&recipientId=" + recipientId;
				});
		$("#createPayment").click(function() {
			$("#showPayment").css("display", "block");
		});

		$('#dateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
		
	})
</script>
<h3>
	<spring:message code="label.payment" />
	<a style="float:right" href="${pageContext.request.contextPath}/payments"><img style="vertical-align:middle" src="<c:url value="/resources/images/back.png" />">&nbsp;<spring:message code="label.backToList" /></a>
</h3>

<%-- <form:form cssStyle="width: 800px; float: left;" method="post" action="/Inventory/payment/create" --%>
<form:form  method="post" action="${pageContext.request.contextPath}/payment/create"
	modelAttribute="transaction">
	<c:if test="${!empty msg}">
		<p id="msg">${msg}</p>
	</c:if>
	<%-- <form:errors path="*" cssClass="error" element="div" /> --%>
	
	<form:hidden path="id"/>
	<form:hidden path="transactionType"/>
	<form:hidden path="transactionNo"/>
	<form:hidden path="createdBy"/>
	
	<p>
		<label for="vendor"><spring:message code="label.vendor" /><span class="mandatory">*</span></label>
		<form:select path="vendor" id="vendor">
			<option value=""><spring:message code="label.selectOne" /></option>
			<c:forEach items="${vendorList}" var="eachItem">
				<option value="${eachItem.id}"
					<c:if test="${eachItem.id eq transaction.vendor.id}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</form:select>
		<form:errors path="vendor" cssClass="error"></form:errors>
	</p>

	<p>
		<label for="dateInput"><spring:message
				code="label.paymentDate" /><span class="mandatory">*</span></label>
		<form:input path="date" id="dateInput" readonly="true" />
		<form:errors path="date" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="amountInput"><spring:message
				code="label.paymentAmount" /><span class="mandatory">*</span></label>
		<form:input path="amount" id="amountInput" />
		<form:errors path="amount" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="remarksInput"><spring:message
				code="label.paymentRemarks" /><span class="mandatory">*</span></label>
		<form:textarea style="vertical-align: middle" rows="1" cols="40"
			path="remarks" id="remarksInput" />
		<form:errors path="remarks" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="<spring:message code="label.createPayment" />">
	<p>
</form:form>
<!-- <div id="customerInfo"></div> -->

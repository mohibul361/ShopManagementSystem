<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

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

		/* $("#recipient")
				.change(
						function() {
							var id = $('option:selected', this).val();
							if (!id) {
								$("#customerInfo").html("");								
								return;
							}
							reqUrl = "${pageContext.request.contextPath}/customerInfo/"
									+ id;

							$.ajax({
								url : reqUrl,
								type : 'GET',
								dataType : 'html',
								success : function(data) {
									$("#customerInfo").html(data);									
								},
								error : function(data, status, er) {
									console.log(data);
									alert("error: " + data + " status: "
											+ status + " er:" + er);
								}
							});
						}); */
	})
</script>
<h3>
	<spring:message code="label.collection" />
	<a id="view" style="float:right" href="${pageContext.request.contextPath}/collections"><img style="vertical-align:middle" src="<c:url value="/resources/images/back.png" />">&nbsp;<spring:message code="label.backToList" /></a>
</h3>
<form:form method="post" action="${pageContext.request.contextPath}/collection/create" modelAttribute="transaction">
	<c:if test="${!empty msg}">
		<div id="msg">${msg}</div>
	</c:if>
	<%-- <form:errors path="*" cssClass="error" element="div" /> --%>
	
	<form:hidden path="id"/>
	<form:hidden path="transactionType"/>
	<form:hidden path="transactionNo"/>
	<form:hidden path="createdBy"/>
	
	<p>
		<label for="recipient"><spring:message code="label.recipient" /><span class="mandatory">*</span></label>
		<form:select path="recipient" id="recipient">
			<option value=""><spring:message code="label.selectOne" /></option>
			<c:forEach items="${recipientList}" var="eachItem">
				<option value="${eachItem.id}"
					<c:if test="${eachItem.id eq transaction.recipient.id}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</form:select>
		<form:errors path="recipient" cssClass="error"></form:errors>
	</p>

	<p>
		<label for="dateInput"><spring:message
				code="label.collectionDate" /><span class="mandatory">*</span></label>
		<form:input path="date" id="dateInput" readonly="true" />
		<form:errors path="date" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="amountInput"><spring:message
				code="label.collectionAmount" /><span class="mandatory">*</span></label>
		<form:input path="amount" id="amountInput" />
		<form:errors path="amount" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="remarksInput"><spring:message
				code="label.collectionRemarks" /><span class="mandatory">*</span></label>
		<form:textarea style="vertical-align: middle" rows="1" cols="40"
			path="remarks" id="remarksInput" />
		<form:errors path="remarks" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label>
		<input type="submit" value="<spring:message code="label.createCollection" />">
	</p>
</form:form>
<!-- <div id="customerInfo"></div> -->

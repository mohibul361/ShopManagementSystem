<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
#attribute {
	list-style-type: none;
	width: 400px;
}

#attribute li {
	padding: 10px;
	border-bottom: 1px solid lightgrey;
}

#attribute li:first-child {
	border-top: 1px solid lightgrey;
}

ul li span {
	width: 200px;
	display: inline-block;
	color: #013D6C;
}

.slip-info {
	list-style-type: none
}

.slip-info li {
	padding: 5px;
	border-bottom: 2px solid white;
}

.slip-info li:last-child {
	padding: 5px;
	border-bottom: 0px solid white;
}
.slip-info li:first-child {
	/*  border-top: 2px solid white;*/
}

.slip {
	background-color: #EEE;
	padding: 10px;
	/* display: table-cell;
	width: 300px; */
	height: 300px;
	float: left;
	margin: 10px;
	padding: 10px;
}

.bold {
	font-size: 14px;
	font-weight: bold;
	padding: 5px;
	background-color: lightblue;
}

#wrapper {
	/* display: table;
	border-collapse: separate;
	border-spacing: 10px; */
}

.label {
	padding-bottom: 5px;
	color: #013D6C;
}

.data {
	font-size: 16px;
	background-color: #FAFAD2;
	padding: 5px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#myElem").show();
		setTimeout(function() {
			$("#myElem").hide();
		}, 5000);
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Item Tracking</h3>
<p>This page shows all slips information associated with the item</p>
<div>
	<c:url var="formAction" value="/itemDetails"></c:url>
	<form:form action="${formAction}">
		<input type="text" name="serialOrImei" id="serialOrImei" placeholder="Serial or IMEI" value="${serialOrImei}">
		<input type="submit" id="search" value="Search" />
	</form:form>
	<h3></h3>
	<c:if test="${item != null}">
		<div>
			<h3>Item Description</h3>	
			<p>
				Type: <span class="bold">${item.itemType.name}</span> Serial: <span class="bold">${item.serial}</span>
				IMEI1:
				<c:choose>
				    <c:when test="${item.imei1 != null}">
				       	<span class="bold">${item.imei1}</span>  
				    </c:when>    
				    <c:otherwise>
				        N/A 
				    </c:otherwise>
				</c:choose>
				IMEI2:
				<c:choose>
				    <c:when test="${item.imei2 != null}">
				       	<span class="bold">${item.imei2}</span>  
				    </c:when>    
				    <c:otherwise>
				        N/A 
				    </c:otherwise>
				</c:choose>
			</p>
			<h4>Attributes</h4>
			<ul id="attribute">
				<c:forEach items="${item.itemAttributeValues}" var="itemAttributeValue">
					<li><span><c:out value="${itemAttributeValue.itemTypeAttribute.name}"></c:out>:</span> <c:out value="${itemAttributeValue.attributeValue}"></c:out></li>
				</c:forEach>
			</ul>
		</div>
		<c:if test="${slipList != null}">
			<div id="wrapper">
				<c:forEach items="${slipList}" var="slip" varStatus="loop">
							<div class="slip">
								<h3>
									<c:choose>
										<c:when test="${slip.slipType eq 'INCOMING'}">
											Incoming Info
										</c:when>
										<c:when test="${slip.slipType eq 'QC_INSPECTION'}">
											Inspection Info
										</c:when>
										<c:when test="${slip.slipType eq 'DELIVERY'}">
											Delivery Info
										</c:when>
										<c:otherwise>
											Return Info
										</c:otherwise>
									</c:choose>
									#${loop.index +1}
								</h3>
								<ul class="slip-info">
									<li>
										<div class="label">SLip Number:</div>
										<div>
											<c:out value="${slip.slipNumber}"></c:out>
										</div>
									</li>
									<li>
										<div class="label">
											<c:choose>
												<c:when test="${slip.slipType eq 'INCOMING'}">
													Received Date:
												</c:when>
												<c:when test="${slip.slipType eq 'QC_INSPECTION'}">
													Inspection Date:
												</c:when>
												<c:when test="${slip.slipType eq 'DELIVERY'}">
													Delivery Date:
												</c:when>
												<c:otherwise>
													Return Date:
												</c:otherwise>
											</c:choose>
										</div>
										<div>
											<fmt:formatDate type="date" value="${slip.slipDate}" />
										</div>
									</li>
									<li>
										<div class="label">
											<c:choose>
												<c:when test="${slip.slipType eq 'INCOMING'}">
													Received By:
												</c:when>
												<c:when test="${slip.slipType eq 'QC_INSPECTION'}">
													Inspected By:
												</c:when>
												<c:when test="${slip.slipType eq 'DELIVERY'}">
													Delivered By:
												</c:when>
												<c:otherwise>
													Returned By:
												</c:otherwise>
											</c:choose>
										</div>
										<div>
											<c:out value="${slip.createdBy.userName}"></c:out>
										</div>
									</li>
									<c:if test="${slip.slipType eq 'INCOMING'}">
										<li>
											<div class="label">Purchase date:</div>
											<div>
												<fmt:formatDate type="date" value="${item.purchaseDate}" />
											</div>
										</li>
										<li>
											<div class="label">Warranty date:</div>
											<div>
												<fmt:formatDate type="date" value="${item.warrantyDate}" />
											</div>
										</li>
									</c:if>
									<c:if test="${slip.slipType eq 'DELIVERY'}">
										<li>
											<div class="label">Delivered To:</div>
											<div class="data">
												<c:out value="${slip.deliveredTo.name}"></c:out>
											</div>
										</li>
										<li>
											<div class="label">Customer Warranty date:</div>
											<div class="data">
												<fmt:formatDate type="date" value="${item.customerwarrantyDate}" />
											</div>
										</li>
									</c:if>
							
								</ul>
							</div>
				</c:forEach>
			</div>
			<div style="clear: both"></div>
		</c:if>

	</c:if>

</div>

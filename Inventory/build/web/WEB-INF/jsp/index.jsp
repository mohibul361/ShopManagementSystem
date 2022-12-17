<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<style type="text/css">
.block{
    width: 300px;
    text-align: center;
    padding: 5px;
    font-weight: bold;
    height: 200px;
    line-height: 200px;
    font-size: 24px;
}
</style>
<script type="text/javascript">
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div style="width:50%; margin:0 auto">
	<h1><spring:message code="label.welcomeMsg"/></h1>
	<spring:message code="label.advantages"/>
	<ul>		
		<li><spring:message code="label.adv1"/></li>
		<li><spring:message code="label.adv2"/></li>
		<li><spring:message code="label.adv3"/></li>
		<li><spring:message code="label.adv4"/></li>
		<li><spring:message code="label.adv5"/></li>
	</ul>	
</div>
<div style="margin:50px 20%">
	<div class="block" style="float:left; background: lightskyblue;"><a href="${contextPath}/stock"><spring:message code="label.viewStock"/></a></div>
	<div class="block" style="float:right; background: lightsalmon;"><a href="${contextPath}/dailyTransactions"><spring:message code="label.viewDailyTransactions"/></a></div>
</div>
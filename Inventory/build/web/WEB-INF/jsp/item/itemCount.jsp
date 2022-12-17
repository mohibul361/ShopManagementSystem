<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	$(function() {

		$('.panel-heading').text($('#itemType option:selected').text());
		var sum = 0;
		$('li span.badge').each(function() {
			sum += parseInt(this.innerHTML, 10)
		});

		$('.panel-footer span.badge').text(sum);
	});
</script>

<div class="panel panel-default">
	<div class="panel-heading"></div>
	<div class="panel-body">
		<p>Count based on Status</p>
	</div>
	<c:forEach var="itemStatusCount" items="${itemStatusCountMap}">
		<ul class="list-group">
			<li class="list-group-item"><span class="badge">${itemStatusCount.value}</span>${itemStatusCount.key}</li>
		</ul>
	</c:forEach>
	<div class="panel-footer">
		<span class="badge"></span>Total
	</div>
</div>

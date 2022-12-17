<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>

td {
	font-size: 12px;
}

label {
	width: 100%;
}

table.datatable tbody tr.odd td {
    background: lightcyan;
}

table.datatable td,
table.datatable th {
    padding: 5px;
    border-top: 1px solid lightblue;
}
table.dataTable thead th {    
    border-bottom: 1px solid lightblue !important; 
    font-size: 14px !important;
    text-align: left !important;
}


</style>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.8/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.8/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	var path = '${pageContext.request.contextPath}';
	$(function() {
		$('#datatable').DataTable({
			"processing" : true,
			"pageLength" : 20,
			"lengthMenu" : [ 20, 35, 50 ],
			"serverSide" : true,
			"pagingType" : "full_numbers",
			"ajax" : {
				"url" : path + "/itemList",
				"type" : "POST"
			}
		});

	});
</script>
<h3>Item List</h3>

<!-- <table id="datatable" class="display" cellspacing="0" width="100%"> -->
<table id="datatable" class="display compact" style="padding-top: 10px;">
	<thead>
		<tr>
			<th>Item Type</th>
			<th>Serial</th>
			<th>Status</th>
			<th>Purchase Date</th>
			<th>Warranty Date</th>
			<th>Customer Wr. Date</th>
		</tr>
	</thead>
</table>

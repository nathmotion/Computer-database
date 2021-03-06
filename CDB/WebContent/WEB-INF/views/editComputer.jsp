<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:${computer.id}</div>
					<h1>Edit Computer</h1>

					<form:form action="editComputer.html" modelAttribute="computerDto" method="POST">
						<form:input type="hidden" value="${computer.id}" id="id"
							name="computerId" path="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <form:input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computer.name}"  path="name"/>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <form:input
									type="date" class="form-control" id="introduced"
									name="introduced" value="${computer.date_introduced}" min="1970-01-01" path="date_introduced"/>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <form:input
									type="date" class="form-control" id="discontinued"
									name="discontinued" value="${computer.date_discontinued }" min="1970-01-01" path="date_discontinued"/>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <form:select
									class="form-control" id="companyId" name="companyId" path="companyId">
									<option value="${computer.companyId}">${computer.companyId}-${computer.companyName}</option>
									<c:forEach items="${ListeCompany}" var="company">
										<option value="${company.id}">${company.id}-${company.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard.html" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
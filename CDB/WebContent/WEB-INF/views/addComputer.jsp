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
					<h1>Add Computer</h1>
					<c:forEach items="${error}" var="err">
						<h3 class="error" style='color: red'>${err}</h3>
						<br>
					</c:forEach>
					<form:form action="addComputer.html" method="POST"
						modelAttribute="computerDto" id="formAddComputer">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label>
								<form:input path="name" type="text" class="form-control"
									id="computerName" name="computerName"
									placeholder="Computer name" />
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label>
								<form:input path="date_introduced" type="date"
									class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date" min="1970-01-01" />
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label>
								<form:input type="date" class="form-control" id="discontinued"
									path="date_discontinued" name="discontinued"
									placeholder="Discontinued date" min="1970-01-01" />
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<form:select path="companyId" class="form-control"
									id="companyId" name="companyId">
									<option value="0">None</option>
									<c:forEach items="${ListeCompany}" var="company">
										<option value="${company.id}">${company.id}-
											${company.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="addButton"
								class="btn btn-primary"> or <a href="dashboard.html"
								class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/addComputer.js"></script>

	<script
		src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
</body>
</html>
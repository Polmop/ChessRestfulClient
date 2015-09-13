<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="false" %>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<!-- StyleSheets -->
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="resources/jquery-ui-1.11.4/jquery-ui.min.css">
		<link rel="stylesheet" href="resources/jquery-ui-1.11.4/jquery-ui.theme.css">
		<link rel="stylesheet" href="resources/jquery-ui-1.11.4/jquery-ui.structure.css">
		<link rel="stylesheet" href="resources/style.css">
		<!-- Scripts -->
		<script src="resources/bootstrap/js/jquery-2.1.4.min.js"></script>
		<script src="resources/jquery-ui-1.11.4/jquery-ui.js"></script>
		<script src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script src="https://maps.googleapis.com/maps/api/js"></script>
		<script src="http://malsup.github.io/jquery.blockUI.js"></script>
		<title>Multiplex Chess Machine</title>
	</head>
	<body>
		<nav class="navbar navbar-przystan navbar-fixed-top">
			<div class="container hidden-xs">
				<ul class="nav nav-justified nav-highlight-underglow">
					<li><a href="#knowledge">Ściągnij silnik</a></li>
					<li><a href="#contact">Stan silników</a></li>
					<li>
						<a id="logo" href="#">
							<img id="logo-image" src="resources/logo.png" alt="Multiplex Chess Machine" />
						</a>
					</li>
					<li><a href="#offer">Graj</a></li>
					<li><a href="#aboutMe">O nas</a></li>
				</ul>
			</div>
			<div class="container visible-xs-block">
				<div class="navbar-header">
				  <button type="button" class="navbar-toggle btn-lg collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="glyphicon glyphicon-list"></span>
				  </button>
				  <a id="small-logo" class="navbar-brand " href="#">
					<img id="logo-small-image" src="resources/logo.png" alt="Multiplex Chess Machine" />
				  </a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav nav-highlight">
						<li><a href="#knowledge">Ściągnij silnik</a></li>
						<li><a href="#contact">Sprawdź stan silników</a></li>
						<li><a href="#offer">Graj</a></li>
						<li><a href="#aboutMe">O nas</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</body>
</html>
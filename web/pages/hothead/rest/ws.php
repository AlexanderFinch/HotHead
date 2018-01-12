<?php
require_once("RestHandler.php");

$view = "";
if(isset($_GET["view"]))
	$view = $_GET["view"];
/*
controls the RESTful services
URL mapping
*/
switch($view){

	case "all":
		// to handle REST Url /rest/list/
		$restHandler = new RestHandler();
		$restHandler->getAllSauces();
		break;

	case "single":
		// to handle REST Url /rest/show/<id>/
		$restHandler = new RestHandler();
		$restHandler->getSauce($_GET["id"]);
		break;

	case "" :
		//404 - not found;
		break;
}
?>
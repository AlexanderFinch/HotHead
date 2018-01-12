<?php
require_once("SimpleRest.php");
require_once("SauceDAO.php");

class RestHandler extends SimpleRest {
    private $host = "168.144.248.85";
    private $username = "x_finchrat_com";
    private $pass = "calad3n1";
    private $database = "x_finchrat_com";

	function getAllSauces() {

		$sauceDAO = new SauceDAO($host, $username, $pass, $database);
		$rawData = $sauceDAO->getAllSauces();

		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No sauces found!');
		} else {
			$statusCode = 200;
		}

		$requestContentType = $_SERVER['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);

		if(strpos($requestContentType,'application/json') !== false){
			$response = $this->encodeJson($rawData);
			echo $response;
		} else if(strpos($requestContentType,'text/html') !== false){
			$response = $sauceDAO->encodeHtml($rawData);
			echo $response;
		} else if(strpos($requestContentType,'application/xml') !== false){
			$response = $this->encodeXml($rawData);
			echo $response;
		}
	}

	public function encodeJson($responseData) {
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;
	}

	public function encodeXml($responseData) {
		// creating object of SimpleXMLElement
		$xml = new SimpleXMLElement('<?xml version="1.0"?><mobile></mobile>');
		foreach($responseData as $key=>$value) {
			$xml->addChild($key, $value);
		}
		return $xml->asXML();
	}

	public function getSauce($id) {
		$sauceDAO = new SauceDAO($host, $username, $pass, $database);
		$rawData = $sauceDAO->getSauce($id);

		if(empty($rawData)) {
			$statusCode = 404;
			echo "No Sauces Found!";
		} else {
			$statusCode = 200;
            $requestContentType = $_SERVER['HTTP_ACCEPT'];
            $this ->setHttpHeaders($requestContentType, $statusCode);

            if(strpos($requestContentType,'application/json') !== false){
                $response = $this->encodeJson($rawData);
                echo $response;
            } else if(strpos($requestContentType,'text/html') !== false){
                $response = $sauceDAO->encodeHtml($rawData);
                echo $response;
            } else if(strpos($requestContentType,'application/xml') !== false){
                $response = $this->encodeXml($rawData);
                echo $response;
            }
		}
	}
}
?>
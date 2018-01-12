<?php
/*
A domain Class to demonstrate RESTful web services
*/
Class SauceVO {

	protected $key;
	protected $name;
	protected $description;
	protected $companyKey;
	protected $shu;
	protected $hotness;
	protected $pepper;
	protected $image;
	protected $verified;

	public function setKey($key){
		$this->key = $key;
	}

	public function getKey(){
		return $this->key;
	}

	public function setName($name){
		$this->name = $name;
	}

	public function getName(){
		return $this->name;
	}

	public function setDescription($description){
		$this->description = $description;
	}

	public function getDescription(){
		return $this->description;
	}

	public function setCompanyKey($companyKey){
		$this->companyKey = $companyKey;
	}

	public function getCompanyKey(){
		return $this->companyKey;
	}

	public function setShu($shu){
		$this->shu = $shu;
	}

	public function getShu(){
		return $this->shu;
	}

	public function setHotness($hotness){
		$this->hotness = $hotness;
	}

	public function getHotness(){
		return $this->hotness;
	}

	public function setPepper($pepper){
		$this->pepper = $pepper;
	}

	public function getPepper(){
		return $this->pepper;
	}

	public function setImage($image){
		$this->image = $image;
	}

	public function getImage(){
		return $this->image;
	}

	public function setVerified($verified){
		$this->verified = $verified;
	}

	public function getVerified(){
		return $this->verified;
	}
}
?>
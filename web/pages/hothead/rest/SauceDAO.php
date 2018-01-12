<?php
require_once("SauceVO.php");
/*
A domain Class to demonstrate RESTful web services
*/
Class SauceDAO {
    protected $connect;
    protected $db;

	public function SauceDAO($host, $username, $password, $database){
	    $this->connect = mysql_connect($host, $username, $password);
	    //$this->db = mysql_select_db($database);
	    return $this;
	}
/*
    // Executes the specified query and returns an associative array of results.
    protected function execute($sql) {
        $res = mysql_query($sql, $this->connect) or die(mysql_error());

        if(mysql_num_rows($res) > 0) {
            for($i = 0; $i < mysql_num_rows($res); $i++) {
                $row = mysql_fetch_assoc($res);
                $sauceVO[$i] = new SauceVO();

                $sauceVO[$i]->setKey($row[key]);
                $sauceVO[$i]->setName($row[name]);
                $sauceVO[$i]->setDescription($row[description]);
                $sauceVO[$i]->setCompanyKey($row[companyKey]);
                $sauceVO[$i]->setShu($row[shu]);
                $sauceVO[$i]->setHotness($row[hotness]);
                $sauceVO[$i]->setPepper($row[pepper]);
                $sauceVO[$i]->setImage($row[image]);
                $sauceVO[$i]->setVerified($row[verified]);
            }
        }
        return $sauceVO;
    }

	public function getAllSauces(){
	    $sql = "SELECT " + columnNameList +
                           ", (select avg(rating) from review where review.sauce_key = sauce.sauce_key) as " + Review.COL.RATING +
                           ", (select count(*) from review where review.sauce_key = sauce.sauce_key) as " + RATING_COUNT +
                           " FROM " + TABLE +
                           " WHERE " + COL.NAME + " LIKE ? OR " +
                           COL.DESCRIPTION + " LIKE ? OR " +
                           COL.PEPPER + " LIKE ? OR " +
                           COL.HOTNESS + " LIKE ?";
		return $this->sauces;
	}*/

	public function getSauce($id){
        $sauceVO = new SauceVO();
        $sauceVO->setKey($id);
        $sauceVO->setName("test");
        $sauceVO->setDescription("Good Sauce");
        $sauceVO->setCompanyKey("Unknown");
        $sauceVO->setShu("1 mil");
        $sauceVO->setHotness("so so hot");
        $sauceVO->setPepper("all of them");
        $sauceVO->setImage("none");
        $sauceVO->setVerified("no");
		return array(1 => $sauceVO);
	}

    public function encodeHtml($responseData) {
        $htmlResponse = "<table border='1'>";
        foreach($responseData as $key=>$sauce) {
            $htmlResponse .= "<tr><td>". $sauce->getKey(). "</td>";
            $htmlResponse .= "<td>". $sauce->getName(). "</td>";
            $htmlResponse .= "<td>". $sauce->getDescription(). "</td>";
            $htmlResponse .= "<td>". $sauce->getCompanyKey(). "</td>";
            $htmlResponse .= "<td>". $sauce->getShu(). "</td>";
            $htmlResponse .= "<td>". $sauce->getHotness(). "</td>";
            $htmlResponse .= "<td>". $sauce->getPepper(). "</td>";
            $htmlResponse .= "<td>". $sauce->getImage(). "</td>";
            $htmlResponse .= "<td>". $sauce->getVerified(). "</td></tr>";
        }
        $htmlResponse .= "</table>";
        return $htmlResponse;
    }
}
?>
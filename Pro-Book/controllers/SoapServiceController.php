<?php

require_once('./libs/nusoap/nusoap.php');

class SoapServiceController extends Controller {
  private $client;

  public function __construct()
  {
    $this->client = new nusoap_client('http://localhost:4789/services/order?wsdl', 'wsdl');

    if ($this->client->getError()){
      echo 'Error occurs';
    }
  }

  public function order($request)
  {
    $result = $this->client->call('order', array('userId' => 10, 'bookId' => 'lp0QAwAAQBAJ', 'quantity' => 5, 'accountNumber' => 10001005));

    if ($result != null){
      echo $this->client->response;
    } else {
      echo 'Failed to order!';
    }
  }
}

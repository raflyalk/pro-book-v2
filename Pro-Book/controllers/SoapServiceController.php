<?php

require_once('./libs/nusoap/nusoap.php');

class SoapServiceController extends Controller
{
  public function __construct(){
    $client = new nusoap_client('http://localhost:4789/services/order?wsdl', 'wsdl');

    if ($client->getError()){
      echo 'error!';
    } else {
      // specific service call
      $result = $client->call('getOrders');

      print_r($result);
    }
  }
}

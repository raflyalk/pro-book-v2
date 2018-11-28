<?php

require_once('./controllers/Auth.php');
require_once('./models/Review.php');
require_once('./libs/nusoap/nusoap.php');

class ReviewController extends Controller
{
  private $clientSearch;
  private $clientOrder;

  /**
   * Constructs ReviewController.
   *
   */
  public function __construct()
  {
    $this->clientSearch = new nusoap_client('http://localhost:4789/services/search?wsdl', 'wsdl');
    $this->clientOrder = new nusoap_client('http://localhost:4789/services/order?wsdl', 'wsdl');

    if ($this->clientSearch->getError() || $this->clientOrder->getError()){
      echo 'error!';
    }

    if (!Auth::check()){
      return $this->redirect('/index.php/login');
    }
  }

  /**
   * Review handler.
   * Show review page.
   * @param request User's order to review.
   * @return view review.php.
   */
  public function create($request)
  {
    $book = $this->clientSearch->call('getBookDetails', array('id' => $request['book-id']));
    $book = json_decode($book);

    $username = Auth::user()['username'];

    return $this->view('review.php', [
      'orderId' => $request['id'],
      'username' => $username,
      'book' => $book
    ]);
  }

  /**
   * Store review handler.
   * @param request user's review from view review.php
   */
  public function store($request)
  {
    $order = $this->clientOrder->call('updateOrder', array('id' => $request['order_id'], 'rating' => $request['rating'], 'comment' => $request['comment']));
    $order = json_decode($order);

    if ($order->success == 1){
      return $this->redirect('/index.php/history',
        ['message' => "Your review was successfully added!"]
      );
    } else {
      return $this->redirect('/index.php/review',
        ['message' => "Failed to add review"]
      );
    }
  }
}

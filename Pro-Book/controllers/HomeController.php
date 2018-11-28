<?php

require_once('./models/Book.php');
require_once('./controllers/Auth.php');
require_once('./libs/nusoap/nusoap.php');

class HomeController extends Controller
{
  private $clientSearch;

	/**
	 * Constructs HomeController.
	 *
	 */
  public function __construct()
  {
    $this->clientSearch = new nusoap_client($_ENV['BOOK_WS_API'] . '/services/search?wsdl', 'wsdl');

    if ($this->clientSearch->getError()){
      echo 'error!';
    }
		if (!Auth::check()){
			return $this->redirect('/index.php/login');
		}
	}

	public function index()
	{
    if (Session::exist('isloginbygoogle')){
      $isSignedIn = Session::get('isloginbygoogle');
      if ($isSignedIn){
        return $this->view('home.php', [
          'username' => Session::get('google')['username']
        ]);
      } else {
        return $this->redirect('/index.php/login', [
          'message' => 'Problem encountered'
        ]);
      }
    }

		return $this->view('home.php', [
		  'username' => Auth::user()['username']
		]);
	}

  public function search($request)
  {
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $keyword = $request->keyword;

    if ($keyword == null){
      echo "Keyword not specified";
    } else {
      $result = $this->clientSearch->call('searchBooksByKeyword', array('title'=>$keyword));

      if ($result == null){
        echo "Error, result null!";
      } else {
        echo $result;
      }
    }
  }
}

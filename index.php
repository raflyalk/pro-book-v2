<?php

require_once('./web.php');
require_once('./autoload.php');

$url = substr($_SERVER['PATH_INFO'], 1);
$bootstrap = new Bootstrap(Route::getList());

if (preg_match('/^apis\/+/', $url)) {
  $bootstrap->init($url, true);
} else {
  $bootstrap->init($url);
}

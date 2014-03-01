var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'cascadiaControllers'
]);

cascadia.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/assign-wp', {controller: 'PackageController', templateUrl:'assign-wp.html'}).
    when('/assign-re', {controller: 'ManagerController', templateUrl:'assign-re.html'}).
    when('/login', {controller: 'LoginController', templateUrl:'login.html'})
    otherwise({redirectTo:'/'});
}]);

cascadia.directive('content', function() {
  return  {
    restrict: 'E',
    transclude: true,
    scope: {},
    template:
      '<div id="wrapper">' +
        '<div id="content" ng-transclude>' +
        '</div>' +
      '</div>',
    replace: true
  };
});
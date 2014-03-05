var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'cascadiaControllers',
    'restangular'
]);

cascadia.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/assign-wp', {controller: 'PackageController', templateUrl:'assign-wp.html'}).
    when('/assign-re', {controller: 'ManagerController', templateUrl:'assign-re.html'}).
    when('/login', {controller: 'LoginController', templateUrl:'login.html'}).
    when('/users-management', {controller: 'UsersManagementController', templateUrl:'users-management.html'}).
    when('/profile', {controller: 'ProfileController', templateUrl:'profile.html'}).
    otherwise({redirectTo:'/'});
}]);

cascadia.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('http://www.comp4911.com/api');
});

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
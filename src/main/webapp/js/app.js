var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'cascadiaControllers',
    'restangular'
]);

cascadia.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/assign-wp', {controller: 'PackageController', templateUrl:'Partials/assign-wp.html'}).
    when('/assign-re', {controller: 'ManagerController', templateUrl:'Partials/assign-re.html'}).
    when('/login', {controller: 'LoginController', templateUrl:'Partials/login.html'}).
    when('/users-management', {controller: 'UsersManagementController', templateUrl:'Partials/users-management.html'}).
    when('/profile', {controller: 'ProfileController', templateUrl:'Partials/profile.html'}).
    otherwise({redirectTo:'/'});
}]);

cascadia.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/comp4911/api');
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
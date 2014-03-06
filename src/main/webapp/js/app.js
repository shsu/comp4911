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
    when('/logout', {controller: 'LogoutController'}).
    when('/users-management', {controller: 'UsersManagementController', templateUrl:'Partials/users-management.html'}).
    when('/profile', {controller: 'ProfileController', templateUrl:'Partials/profile.html'}).
    when('/dashboard', {controller: 'DashboardController', templateUrl:'Partials/dashboard.html'}).
    otherwise({redirectTo:'/'});
}])
.run(function($rootScope, authenticateUser) {
  $rootScope.$on('$routeChangeSuccess', function() {
     authenticateUser($rootScope);
  })
})
.factory('authenticateUser', function($location) {
  return function(scope){
    scope.isAuthenticated = (localStorage.getItem('token')) ? true : false;
    if(!scope.isAuthenticated) {
        $location.path('/login');
    }
  }
});

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
var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'cascadiaControllers',
    'restangular'
]);

cascadia.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/add-engineer', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html'}).
    when('/assign-employee-project', {controller: 'AEPController', templateUrl:'Partials/assign-employee-project.html'}).
    when('/assign-employee-wp/:id', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html'}).
    when('/assign-manager', {controller: 'ManagerController', templateUrl:'Partials/assign-manager.html'}).
    when('/assign-project', {controller: 'PackageController', templateUrl:'Partials/assign-project.html'}).
    when('/assign-re/:id', {controller: 'ARController', templateUrl:'Partials/assign-re.html'}).
    when('/assign-supervisor', {controller: 'ASController', templateUrl:'Partials/assign-supervisor.html'}).
    when('/assign-wp', {controller: 'PackageController', templateUrl:'Partials/assign-wp.html'}).
    when('/create-project', {controller: 'CreateProjectsController', templateUrl:'Partials/create-project.html'}).
    when('/create-wp', {controller: 'CreateWPController', templateUrl:'Partials/create-wp.html'}).
    when('/dashboard', {controller: 'DashboardController', templateUrl:'Partials/dashboard.html'}).
    when('/edit-pay-rates', {controller: 'EditPayRatesController', templateUrl:'Partials/edit-pay-rates.html'}).
    when('/engineer-budget', {controller: 'EngineerBudgetController', templateUrl:'Partials/engineer-budget.html'}).
    when('/login', {controller: 'LoginController', templateUrl:'Partials/login.html'}).
    when('/logout', {controller: 'LogoutController'}).
    when('/manage-approver', {controller: 'ManageApproverController', templateUrl:'Partials/manage-approver.html'}).
    when('/manage-project', {controller: 'ManageProjectController', templateUrl:'Partials/manage-project.html'}).
    when('/profile', {controller: 'ProfileController', templateUrl:'Partials/profile.html'}).
    when('/project', {controller: 'ProjectManagementController', templateUrl:'Partials/project-management.html'}).
    when('/timesheet', {controller: 'TimesheetController', templateUrl:'Partials/timesheet-management.html'}).
    when('/user-management-hr', {controller: 'UsersManagementController', templateUrl:'Partials/user-management-hr.html'}).
    when('/user-management-super', {controller: 'UsersManagementController', templateUrl:'Partials/user-management-super.html'}).
    otherwise({redirectTo:'/'});
}])
.run(function($rootScope, authenticateUser) {
  $rootScope.$on('$routeChangeSuccess', function() {
     authenticateUser($rootScope);
  })
})
// Runs every time page is reloaded or routed, ensures authentication and headers are set appropriately
.factory('authenticateUser', function($location, Restangular) {
  return function(scope){
    scope.isAuthenticated = (localStorage.getItem('token')) ? true : false;
    if(!scope.isAuthenticated) {
        $location.path('/login');
    } else {
      Restangular.setDefaultHeaders({
        'Authorization': 'Basic ' + localStorage.getItem('token')
      });
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
var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'cascadiaControllers',
    'restangular'
]);

cascadia.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/add-engineer', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html'}).
    when('/assign-employee-project', {controller: 'AEPController', templateUrl:'Partials/assign-employee-project.html'}).
    when('/assign-employee-wp', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html'}).
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
    when('/manage-wp', {controller: 'WPManagementController', templateUrl:'Partials/manage-wp.html'}).
    when('/monthly-wp', {controller: 'MonthlyWPController', templateUrl:'Partials/monthly-wp.html'}).
    when('/pcbac', {controller: 'PCBACController', templateUrl:'Partials/pcbac.html'}).
    when('/pcpr', {controller: 'PCPRController', templateUrl:'Partials/pcpr.html'}).
    when('/project', {controller: 'ProjectManagementController', templateUrl:'Partials/project-management.html'}).
    when('/project-summary', {controller: 'ProjectSummaryController', templateUrl:'Partials/project-summary.html'}).
    when('/rate-sheet', {controller: 'RateSheetController', templateUrl:'Partials/rate-sheet.html'}).
    when('/search-project', {controller: 'SearchProjectController', templateUrl:'Partials/search-project.html'}).
    when('/timesheet-approval', {controller: 'TimesheetController', templateUrl:'Partials/timesheet-approval.html'}).
    when('/timesheet-management', {controller: 'TimesheetController', templateUrl:'Partials/timesheet-management.html'}).
    when('/timesheet', {controller: 'TimesheetController', templateUrl:'Partials/timesheet.html'}).
    when('/user-management-hr', {controller: 'UsersManagementController', templateUrl:'Partials/user-management-hr.html'}).
    when('/user-management-super', {controller: 'UsersManagementController', templateUrl:'Partials/user-management-super.html'}).
    when('/user-profile/:id', {controller: 'UserProfileController', templateUrl:'Partials/user-profile.html'}).
    when('/weekly-project', {controller: 'WeeklyProjectController', templateUrl:'Partials/weekly-project.html'}).
    when('/wp-details', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html'}).
    when('/wp-details/:id', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html'}).
    when('/wp-status-report', {controller: 'WPStatusReportController', templateUrl:'Partials/wp-status-report.html'}).
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
    var xhr = new XMLHttpRequest();
    xhr.open('HEAD', "http://localhost:8080/comp4911/api", false);
    try {
        xhr.send();
        if (xhr.status >= 200 && xhr.status < 304) {
            RestangularProvider.setBaseUrl('http://localhost:8080/comp4911/api');
        } else {
            RestangularProvider.setBaseUrl('http://www.comp4911.com/api');
        }
    } catch (e) {
        RestangularProvider.setBaseUrl('http://www.comp4911.com/api');
    }
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
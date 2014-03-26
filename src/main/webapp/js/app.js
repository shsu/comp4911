var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'restangular',
    'cascadiaControllers',
    'cascadiaServices',
    'cascadiaDirectives'
]);

cascadia.config(['$routeProvider',
    function($routeProvider) {
      $routeProvider.
        when('/add-engineer', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html'}).
        when('/add-engineer/:id', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html'}).
        when('/add-manager/:id', {controller: 'ManagerController', templateUrl:'Partials/add-project-manager.html'}).
        when('/assign-employee-project/:id', {controller: 'AEPController', templateUrl:'Partials/assign-employee-project.html'}).
        when('/assign-employee-wp', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html'}).
        when('/assign-employee-wp/:id', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html'}).
        when('/assign-manager', {controller: 'ManagerController', templateUrl:'Partials/assign-manager.html'}).
        when('/assign-project', {controller: 'PackageController', templateUrl:'Partials/assign-project.html'}).
        when('/assign-re', {controller: 'ARController', templateUrl:'Partials/assign-re.html'}).
        when('/assign-supervisor/:id', {controller: 'ASController', templateUrl:'Partials/assign-supervisor.html'}).
        when('/assign-wp', {controller: 'PackageController', templateUrl:'Partials/assign-wp.html'}).
        when('/create-project', {controller: 'CreateProjectsController', templateUrl:'Partials/create-project.html'}).
        when('/create-wp/:id', {controller: 'CreateWPController', templateUrl:'Partials/create-wp.html'}).
        when('/users/new', {controller: 'CreateUserController', templateUrl:'Partials/create-user.html'}).
        when('/dashboard', {controller: 'DashboardController', templateUrl:'Partials/dashboard.html'}).
        when('/users/pay-rates', {controller: 'EditPayRatesController', templateUrl:'Partials/edit-pay-rates.html'}).
        when('/engineer-budget', {controller: 'EngineerBudgetController', templateUrl:'Partials/engineer-budget.html'}).
        when('/login', {controller: 'LoginController', templateUrl:'Partials/login.html'}).
        when('/logout', {controller: 'LogoutController'}).
        when('/manage-approver', {controller: 'ManageApproverController', templateUrl:'Partials/manage-approver.html'}).
        when('/manage-project', {controller: 'ProjectManagementController', templateUrl:'Partials/manage-project.html'}).
        when('/manage-project/:id', {controller: 'ManageProjectController', templateUrl:'Partials/manage-project.html'}).
        when('/manage-wp', {controller: 'WPManagementController', templateUrl:'Partials/manage-wp.html'}).
        when('/monthly-wp', {controller: 'MonthlyWPController', templateUrl:'Partials/monthly-wp.html'}).
        when('/pcbac', {controller: 'PCBACController', templateUrl:'Partials/pcbac.html'}).
        when('/pcpr', {controller: 'PCPRController', templateUrl:'Partials/pcpr.html'}).
        when('/project', {controller: 'ProjectManagementController', templateUrl:'Partials/project-management.html'}).
        when('/project-details/:id', {controller: 'ProjectDetailsController', templateUrl: 'Partials/project-details.html'}).
        when('/project-summary', {controller: 'ProjectSummaryController', templateUrl:'Partials/project-summary.html'}).
        when('/rate-sheet', {controller: 'RateSheetController', templateUrl:'Partials/rate-sheet.html'}).
        when('/search-project', {controller: 'SearchProjectController', templateUrl:'Partials/search-project.html'}).
        when('/timesheet-approval', {controller: 'TAController', templateUrl:'Partials/timesheet-approval.html'}).
        when('/timesheet-management', {controller: 'TimesheetController', templateUrl:'Partials/timesheet-management.html'}).
        when('/timesheet', {controller: 'TimesheetController', templateUrl:'Partials/timesheet.html'}).
        when('/timesheet/:id', {controller: 'TADetailsController', templateUrl:'Partials/single-timesheet.html'}).
        when('/users', {controller: 'UsersManagementController', templateUrl:'Partials/user-management.html'}).
        when('/user', {controller: 'UserProfileController', templateUrl:'Partials/user-profile.html'}).
        when('/users/:id', {controller: 'ManagedUserProfileController', templateUrl:'Partials/user-profile-managed.html'}).
        when('/weekly-project', {controller: 'WeeklyProjectController', templateUrl:'Partials/weekly-project.html'}).
        when('/wp-details', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html'}).
        when('/wp-details/:id', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html'}).
        when('/wp-status-report', {controller: 'WPStatusReportController', templateUrl:'Partials/wp-status-report.html'}).
        otherwise({redirectTo:'/'});
    }])
    .run(function($rootScope, $location, permissions, AuthenticateUser, InitUserMap, permissions) {
        $rootScope.$on('$routeChangeSuccess',
            function() {
                if(localStorage.getItem('permissions')){
                    permissions.setPermissions(JSON.parse(localStorage.getItem('permissions')));
                }
                AuthenticateUser($rootScope);
                InitUserMap($rootScope);
            })
    });
// Runs every time page is reloaded or routed, ensures authentication and headers are set appropriately

cascadia.config(function(RestangularProvider) {
    if(checkServerStatus("http://localhost:8080/comp4911/api")){
        RestangularProvider.setBaseUrl('http://localhost:8080/comp4911/api');
    } else if(checkServerStatus("http://www.comp4911.com/api")){
        RestangularProvider.setBaseUrl('http://www.comp4911.com/api');
    } else {
        $.growl.warning({ message: "Unable to make connection to an API" });
    }
});

function checkServerStatus(url){
    var xhr = new XMLHttpRequest();
    xhr.open('HEAD', url, false);
    try {
        xhr.send();
        if (xhr.status >= 200 && xhr.status < 304) {
            return true;
        } else {
            return false;
        }
    } catch (e) {
       return false;
    }
}

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
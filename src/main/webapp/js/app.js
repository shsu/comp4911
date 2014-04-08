var cascadia = angular.module('cascadiaApp', [
    'ngRoute',
    'ngAnimate',
    'restangular',
    'ui.bootstrap',
    'cascadiaControllers',
    'cascadiaServices',
    'cascadiaFilters',
    'cascadiaDirectives',
    'chieffancypants.loadingBar'
]);

cascadia.config(['$routeProvider',
    function($routeProvider) {
      $routeProvider.
        //when('/add-engineer', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html',
             // resolve: requiresAuthentication}).
        when('/add-engineer/:id', {controller: 'EngineerController', templateUrl:'Partials/add-engineer.html',
             resolve: requiresAuthentication}).
        when('/assign-employee-project/:id', {controller: 'AEPController', templateUrl:'Partials/assign-employee-project.html',
             resolve: requiresAuthentication}).
        //when('/assign-employee-wp', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html',
             // resolve: requiresAuthentication}).
        when('/assign-employee-wp/:id', {controller: 'AEWPController', templateUrl:'Partials/assign-employee-wp.html',
             resolve: requiresAuthentication}).
        // when('/assign-project', {controller: 'APController', templateUrl:'Partials/assign-project.html',
        //      resolve: requiresAuthentication}).
        // when('/assign-wp', {controller: 'PackageController', templateUrl:'Partials/assign-wp.html',
        //      resolve: requiresAuthentication}).
        when('/create-wp/:id', {controller: 'CreateWPController', templateUrl:'Partials/create-wp.html',
             resolve: requiresAuthentication}).
        when('/dashboard', {controller: 'DashboardController', templateUrl:'Partials/dashboard.html', 
            resolve: requiresAuthentication}).
        when('/engineer-budget', {controller: 'EngineerBudgetController', templateUrl:'Partials/engineer-budget.html',
            resolve: requiresAuthentication}).
        when('/login', {controller: 'LoginController', templateUrl:'Partials/login.html'}).
        when('/logout', {controller: 'LogoutController', template:' '}).
        when('/manage-approver', {controller: 'ManageApproverController', templateUrl:'Partials/manage-approver.html',
             resolve: requiresAuthentication}).
        when('/manage-wp-pm', {controller: 'WPManagementPMController', templateUrl:'Partials/manage-wp-pm.html',
             resolve: requiresAuthentication, permission: 'ProjectManager'}).
        when('/manage-wp-re', {controller: 'WPManagementREController', templateUrl:'Partials/manage-wp-re.html',
            resolve: requiresAuthentication, permission: 'ResponsibleEngineer'}).
        when('/monthly-wp', {controller: 'MonthlyWPController', templateUrl:'Partials/monthly-wp.html',
            resolve: requiresAuthentication}).
        // when('/pcbac', {controller: 'PCBACController', templateUrl:'Partials/pcbac.html',
        //     resolve: requiresAuthentication}).
        // when('/pcpr', {controller: 'PCPRController', templateUrl:'Partials/pcpr.html',
        //     resolve: requiresAuthentication}).
        when('/projects', {controller: 'ProjectManagementController', templateUrl:'Partials/manage-project.html',
            resolve: requiresAuthentication, permission: 'ProjectManager, Hr'}).
        when('/projects/new', {controller: 'CreateProjectsController', templateUrl:'Partials/create-project.html',
            resolve: requiresAuthentication, permission: 'Hr'}).
        when('/projects/:id', {controller: 'ProjectDetailsController', templateUrl: 'Partials/project-details.html',
            resolve: requiresAuthentication}).
        when('/projects/:id/assign-manager', {controller: 'ManagerController', templateUrl:'Partials/add-project-manager.html',
            resolve: requiresAuthentication}).
        when('/project-summary/:id', {controller: 'ProjectSummaryController', templateUrl:'Partials/project-summary.html',
            resolve: requiresAuthentication}).
        when('/rate-sheet', {controller: 'RateSheetController', templateUrl:'Partials/rate-sheet.html',
            resolve: requiresAuthentication}).
        when('/timesheet-approval', {controller: 'TAController', templateUrl:'Partials/timesheet-approval.html',
            resolve: requiresAuthentication, permission:'TimesheetApprover'}).
        when('/timesheet-approval/:id', {controller: 'TADetailsController', templateUrl:'Partials/single-timesheet.html',
            resolve: requiresAuthentication, permission:'TimesheetApprover'}).
        when('/timesheet', {controller: 'TimesheetController', templateUrl:'Partials/timesheet.html',
            resolve: requiresAuthentication}).
        when('/timesheet/:id', {controller: 'TimesheetCorrectionController', templateUrl:'Partials/timesheet-correction.html',
            resolve: requiresAuthentication}).
        when('/unauthorized', {controller: 'UnauthorizedController', templateUrl:'Partials/unauthorized.html',
            resolve: requiresAuthentication}).
        when('/user', {controller: 'UserProfileController', templateUrl:'Partials/user-profile.html',
            resolve: requiresAuthentication}).
        when('/users', {controller: 'UsersManagementController', templateUrl:'Partials/user-management.html',
            resolve: requiresAuthentication, permission: 'Supervisor, Hr'}).
        when('/users/new', {controller: 'CreateUserController', templateUrl:'Partials/create-user.html',
            resolve: requiresAuthentication}).
        when('/users/pay-rates', {controller: 'EditPayRatesController', templateUrl:'Partials/edit-pay-rates.html',
            resolve: requiresAuthentication}).
        when('/users/:id', {controller: 'ManagedUserProfileController', templateUrl:'Partials/user-profile-managed.html',
            resolve: requiresAuthentication}).
        when('/users/:id/assign-supervisor', {controller: 'ASController', templateUrl:'Partials/assign-supervisor.html',
            resolve: requiresAuthentication}).
        when('/users/:id/assign-ta', {controller: 'ATAController', templateUrl:'Partials/assign-ta.html',
            resolve: requiresAuthentication}).
        when('/weekly-project', {controller: 'WeeklyProjectController', templateUrl:'Partials/weekly-project.html',
            resolve: requiresAuthentication}).
        when('/wp-details', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html',
            resolve: requiresAuthentication}).
        when('/wp-details/:id', {controller: 'WPDetailsController', templateUrl:'Partials/wp-details.html',
            resolve: requiresAuthentication}).
        when('/wp-status-report', {controller: 'WPStatusReportController', templateUrl:'Partials/wp-status-report.html',
            resolve: requiresAuthentication}).
        when('/wp-status-report/:id', {controller: 'WPStatusReportController', templateUrl:'Partials/wp-status-report.html',
            resolve: requiresAuthentication}).
        when('/wp-status-report/:wpid/:id', {controller: 'WPStatusReportClosedController', templateUrl:'Partials/wp-status-report-view.html',
            resolve: requiresAuthentication}).
        otherwise({redirectTo:'/login'});
    }])
    .run(function($rootScope, $location, permissions) {
        $rootScope.$on('$routeChangeStart', function(scope, next, current) {
            if(localStorage.getItem('permissions')){
                permissions.setPermissions(JSON.parse(localStorage.getItem('permissions')));
            }
            if(next.$$route && next.$$route.permission){
                var array = next.$$route.permission.split(',');
                var accessible = false;
                for(var i = 0; i < array.length; ++i){
                    if(_.isString(array[i]) && permissions.hasPermission(array[i].trim())){
                        accessible = true;
                    } 
                }
                if(!accessible) {
                    $location.path('/logout');
                }
            }
        });
    });

var requiresAuthentication = {
  user: function(userService) {
    return userService.isAuthenticatedResolve();
  }
}

cascadia.config(function(RestangularProvider) {
    if(checkServerStatus("http://localhost:8080/comp4911/api")){
        RestangularProvider.setBaseUrl('http://localhost:8080/comp4911/api');
    } else if(checkServerStatus("http://localhost:8080/Cascadia/api")){
        RestangularProvider.setBaseUrl("http://localhost:8080/Cascadia/api");
    }  else if(checkServerStatus("http://www.comp4911.com/api")){
        RestangularProvider.setBaseUrl('http://www.comp4911.com/api');
    } else {
        RestangularProvider.setBaseUrl('https://comp4911.apiary.io');
        toastr.warning("Unable to make connection to an real API.");
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

toastr.options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-bottom-right",
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "3000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}
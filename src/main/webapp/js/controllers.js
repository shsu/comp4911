var cascadiaControllers = angular.module('cascadiaControllers', ['base64', 'restangular']);

cascadiaControllers.service('CascadiaService', function($rootScope) {
  $rootScope.user = JSON.parse(localStorage.getItem('user'));

  this.initMapOfUsers = function(listOfUsers) {
    var worker = new Worker('js/web-worker.js');
    worker.addEventListener('message', function(e){
      $rootScope.mapOfUsers = JSON.parse(e.data);
    }, false);
    worker.postMessage(JSON.stringify(listOfUsers));
  };
});



/*
    ADD ENGINEER CONTROLLER
*/
cascadiaControllers.controller('EngineerController', ['$scope',
  function($scope) {
    $scope.engineers = [{
      number: 'A1111',
      name: 'Javier Olson',
      paygrade: 'P4',
      selected: false,
      disabled: false
    }, {
      number: 'A1112',
      name: 'Nancy Garcia',
      paygrade: 'P5',
      selected: false,
      disabled: false
    }, {
      number: 'A1113',
      name: 'David Bowden',
      paygrade: 'SS',
      selected: false,
      disabled: false
    }];

    $scope.selectedEngineers = [];

    $scope.selectE = function($index) {
      if ($scope.engineers[$index].disabled) {
        return;
      }

      if (!$scope.engineers[$index].selected) {
        $scope.engineers[$index].selected = true;
      } else {
        $scope.engineers[$index].selected = false;
      }
    }

    $scope.addedSelectE = function($index) {
      if (!$scope.selectedEngineers[$index].selected) {
        $scope.selectedEngineers[$index].selected = true;
      } else {
        $scope.selectedEngineers[$index].selected = false;
      }
    }

    $scope.addE = function() {
      $scope.engineers.forEach(addToSelectedE);
    }

    function addToSelectedE(obj) {
      if (obj.selected === true && obj.disabled !== true) {
        $scope.selectedEngineers.push({
          number: obj.number,
          name: obj.name,
          paygrade: obj.paygrade,
          selected: false
        });
        obj.disabled = true;
        obj.selected = false;
      }
    }

    $scope.removeE = function() {
      var length = $scope.selectedEngineers.length;

      for (var i = 0; i < length;) {
        if (!removeFromSelectedE($scope.selectedEngineers[i]))
          i++;
      };
    }

    function removeFromSelectedE(obj) {
      if (obj !== undefined && obj.selected === true) {
        var num = obj.number;
        for (var i = 0; i < $scope.engineers.length; i++) {
          if ($scope.engineers[i].number == num) {
            $scope.engineers[i].disabled = false;
          }
        }

        var index = $scope.selectedEngineers.indexOf(obj);

        if (index > -1) {
          $scope.selectedEngineers.splice(index, 1);
        }
        return true;
      }
      return false;
    }
  }
]);



/*
    ASSIGN EMPLOYEE TO PROJECT CONTROLLER
*/
cascadiaControllers.controller('AEPController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    Restangular.all('projects').getList().then(function(response){
      $scope.projects = response;
    });
  }
]);



/*
    ASSIGN EMPLOYEE TO WORK PACKAGE CONTROLLER
*/
cascadiaControllers.controller('AEWPController', ['$scope', 'CascadiaService', '$location', 'Restangular', '$routeParams',
  function($scope, CascadiaService, $location, Restangular, $params){
    $scope.param = $params.id;

    Restangular.all('work_packages/' + $scope.param).getList().then(function(response){
      $scope.packages = response;
    });

    // code for work package assignment - /work_packages/$scope.param/assignments?
  }
]);



/*
    ASSIGN MANAGER CONTROLLER
*/
cascadiaControllers.controller('ManagerController', ['$scope',
  function($scope) {
    $scope.managers = [{
      id: 'A1111',
      name: 'Javier Olson',
      paygrade: 'P4',
      selected: false
    }, {
      id: 'A1112',
      name: 'Nancy Garcia',
      paygrade: 'P2',
      selected: false
    }, {
      id: 'A1113',
      name: 'David Bowden',
      paygrade: 'P5',
      selected: false
    }, {
      id: 'A1114',
      name: 'Juan Burdine',
      paygrade: 'SS',
      selected: false
    }];

    $scope.selectedManager = {};

    $scope.selectM = function($index) {
      var id = $scope.managers[$index].id;

      $scope.managers.forEach(setSelectedFalse);

      $scope.managers[$index].selected = true;
      $scope.selectedManager = $scope.managers[$index];
    }

    function setSelectedFalse(obj) {
      obj.selected = false;
    }

    $scope.cancel = function() {
      $scope.selectedManager = {};
      $scope.managers.forEach(setSelectedFalse);
    }
  }
]);



/*
    ASSIGN TO PROJECTS CONTROLLER
*/
cascadiaControllers.controller('APController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){

    Restangular.all('projects').getList().then(function(response){
      $scope.projects = response;
    });

    // code for project assignment
  }
]);



/*
    ASSIGN RESPONSIBLE ENGINEER CONTROLLER
*/
cascadiaControllers.controller('ARController', ['$scope', 'CascadiaService', '$location', 'Restangular', '$routeParams',
  function($scope, CascadiaService, $location, Restangular, $params){
    $scope.param = $params.id;

    Restangular.one('work_packages/' + $scope.param).get().then(function(response){
      $scope.package = response;
    });

    Restangular.all('users').getList().then(function(response){
      $scope.engineers = response;
    });

    // code for work package assignment - /work_packages/$scope.param/assignments?
  }
]);



/*
    ASSIGN SUPERVISOR CONTROLLER
*/
cascadiaControllers.controller('ASController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){

    Restangular.all('users').getList().then(function(response){
      $scope.projects = response;
    });

    // code for supervisor assignment
  }
]);



/*
    ASSIGN WP CONTROLLER
*/
cascadiaControllers.controller('PackageController', ['$scope',
  function($scope) {
    $scope.packages = [{
      number: 'B1111',
      title: 'Project Setup',
      selected: false,
      disabled: false
    }, {
      number: 'B1112',
      title: 'Ongoing Updating',
      selected: false,
      disabled: false
    }, {
      number: 'B1113',
      title: 'Detailed Planning',
      selected: false,
      disabled: false
    }];

    $scope.selectedPackages = [];

    $scope.select = function($index) {
      if ($scope.packages[$index].disabled) {
        return;
      }

      if (!$scope.packages[$index].selected) {
        $scope.packages[$index].selected = true;
      } else {
        $scope.packages[$index].selected = false;
      }
    }

    $scope.addedSelect = function($index) {
      if (!$scope.selectedPackages[$index].selected) {
        $scope.selectedPackages[$index].selected = true;
      } else {
        $scope.selectedPackages[$index].selected = false;
      }
    }

    $scope.add = function() {
      $scope.packages.forEach(addToSelected);
    }

    // Helper forEach function for $scope.add
    function addToSelected(obj) {
      if (obj.selected === true && obj.disabled !== true) {
        $scope.selectedPackages.push({
          number: obj.number,
          title: obj.title,
          selected: false
        });
        obj.disabled = true;
        obj.selected = false;
      }
    }

    $scope.remove = function() {
      var length = $scope.selectedPackages.length;

      for (var i = 0; i < length;) {
        if (!removeFromSelected($scope.selectedPackages[i]))
          i++;
      };
    }

    // Helper forEach function for $scope.remove
    // I'm sure there is a cleaner, nicer way to do this...
    function removeFromSelected(obj) {
      if (obj !== undefined && obj.selected === true) {
        var num = obj.number;

        for (var i = 0; i < $scope.packages.length; i++) {
          if ($scope.packages[i].number == num) {
            $scope.packages[i].disabled = false;
          }
        }

        var index = $scope.selectedPackages.indexOf(obj);

        if (index > -1) {
          $scope.selectedPackages.splice(index, 1);
        }
        return true;
      }
      return false;
    }
  }
]);



/*
    CREATE PROJECTS CONTROLLER
*/
cascadiaControllers.controller('CreateProjectsController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    // Restangular.one('project').post() ...
  }
]);



/*
    CREATE WP CONTROLLER
*/
cascadiaControllers.controller('CreateWPController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    
  }
]);



/*
    DASHBOARD CONTROLLER
*/
cascadiaControllers.controller('DashboardController', ['$scope', '$rootScope', 'Restangular', 'CascadiaService',
  function($scope, $rootScope, Restangular, CascadiaService) {
    Restangular.one('user/timesheets').get().then(function(response) {
      $scope.timesheets = response;
    });

    $scope.date = function($index){
      var timesheet = $scope.timesheets[$index];
      var day = (1 + (timesheet.weekNumber - 1) * 7);  
      return new Date(timesheet.year, 0, day);
    }

    $scope.delete = function(timesheet, $index) {
      user.remove().then(function() {
        $scope.timesheets.splice($index, 1);
      });

      console.log("timesheet deleted");
    }
    
  }
]);



/*
    EDIT PAY RATES CONTROLLER
*/
cascadiaControllers.controller('EditPayRatesController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular) {
    var base = Restangular.all('pay_rates');

    base.getList().then(function(response){
      $scope.payRates = response;
    });

    payRatesChanged = [];

    $scope.change = function(payRate) {
      unique = true;
      for(var i = 0; i < payRatesChanged.length; i++) {
        if(payRatesChanged[i].id == payRate.id) {
          unique = false;
          break;
        } 
      }

      if(unique) {
        payRatesChanged.push(payRate);
      }
    };

    $scope.edit = function() {
      for(var i = 0; i < payRatesChanged.length; i++) {
        payRatesChanged[i].put();
      }
    }
  }
]);



/*
    ENGINEER BUDGET CONTROLLER
*/
cascadiaControllers.controller('EngineerBudgetController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    
  }
]);



/*
    INDEX CONTROLLER
*/
cascadiaControllers.controller('IndexController', ['$scope', 'CascadiaService', '$location',
  function($scope, CascadiaService, $location) {
    $scope.logout = function() {
      localStorage.clear();
      $location.path('/login');
      $.growl.notice({ title: "Success!", message: "You have been logged out." });
    }
  }
]);



/*
    LOGIN CONTROLLER
*/
cascadiaControllers.controller('LoginController', ['$scope', '$base64', 'Restangular', '$rootScope', '$location', 'CascadiaService',
  function($scope, $base64, Restangular, $rootScope, $location, CascadiaService) {
    $scope.login = function() {
      var data = {
        'username': $scope.username,
        'password': $scope.password
      };

      Restangular.one('user').post('token', data).then(function(response) {
        $scope.encodedString = $base64.encode(response.token + ":");
        localStorage.setItem('token', $scope.encodedString);
        Restangular.setDefaultHeaders({
          'Authorization': 'Basic ' + $scope.encodedString
        });

        Restangular.one('user').get().then(function(response) {
          localStorage.setItem('user', JSON.stringify(response));
            $rootScope.user = response
          $location.path('/dashboard');
        });
      },function handleError(response){
              if(response.status == 400){
                  $.growl.error({title: "Error!", message: "Missing username or password."})
              }
              else if(response.status == 401){
                  $.growl.error({title: "Error!", message: "Authentication failed, please check your credentials again."})
              } else {
                  $.growl.error({title: "Error!", message: "Status Code "+ response.status})
              }
          }
      );
    };
  }
]);



/*
    LOGOUT CONTROLLER
*/
cascadiaControllers.controller('LogoutController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);


/*
    MANAGE APPROVER CONTROLLER
*/
cascadiaControllers.controller('ManageApproverController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    
  }
]);



/*
    MANAGE PROJECT CONTROLLER
*/
cascadiaControllers.controller('ManageProjectController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    
  }
]);



/*
   MANAGE WP CONTROLLER
*/
cascadiaControllers.controller('WPManagementController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    $scope.statuses = [ 'Open', 'Closed'];
    $scope.project = {};
    mapOfWP = {};
    wpChanged = [];

    $scope.listWP = function(project) {
      Restangular.one('work_packages/project').getList(project.projectNumber).then(function(response){
        $scope.workPackages = response;
      });
    }  

    Restangular.one('user/projects/managed').getList().then(function(response){
      $scope.projects = response;
    });

    $scope.change = function(wp) {
      unique = true;
      for(var i = 0; i < wpChanged.length; i++) {
        if(wpChanged[i].workPackageNumber == wp.workPackageNumber) {
          unique = false;
          break;
        } 
      }

      if(unique) {
        wpChanged.push(wp);
      }
    };
    
    $scope.edit = function() {
      for(var i = 0; i < wpChanged.length; i++) {
        el = wpChanged[i];
        Restangular.one('work_packages', el.workPackageNumber).customPUT(el);
      }
      wpChanged = [];
    }
    
  }
]);



/*
    MONTHLY WP CONTROLLER
*/
cascadiaControllers.controller('MonthlyWPController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);



/*
    PCBAC CONTROLLER
*/
cascadiaControllers.controller('PCBACController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);



/*
    PCPR CONTROLLER
*/
cascadiaControllers.controller('PCPRController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);



/*
    PROJECT MANAGEMENT CONTROLLER
*/
cascadiaControllers.controller('ProjectManagementController', ['$scope', 'CascadiaService', 'Restangular',
  function($scope, CascadiaService, Restangular) {
    var base = Restangular.all('projects');

    Restangular.one('user/projects/managed').getList().then(function(response){
      $scope.projects = response;
    })

    $scope.add = function() {
      $scope.add_project = true;
    }

    $scope.save = function() {
      newproject = $scope.newproject;

      base.post(newproject).then(function(response) {
        $scope.projects.push(newproject);
        $scope.add_project = false;
      })
    }
  }
]);



/*
    PROJECT SUMMARY CONTROLLER
*/
cascadiaControllers.controller('ProjectSummaryController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);



/*
    RATE SHEET CONTROLLER
*/
cascadiaControllers.controller('RateSheetController', ['$scope', 'Restangular',
  function($scope, Restangular){
    Restangular.all('users').getList().then(function(response){
      $scope.users = response;
    });
  }
]);



/*
    SEARCH PROJECT CONTROLLER
*/
cascadiaControllers.controller('SearchProjectController', ['$scope', 'Restangular',
  function($scope, Restangular){

  }
]);



/*
    TIMESHEET CONTROLLER
*/
cascadiaControllers.controller('TimesheetController', ['$scope', '$rootScope', 'CascadiaService', '$location', 'Restangular',
  function($scope, $rootScope, CascadiaService, $location, Restangular) {
    var base = Restangular.one('user/timesheets');

    $scope.workPackageNumbers = {};
    $scope.projectNumbers = [];

    Restangular.one('user/projects/managed').getList().then(function(response){
      projects = response;
      for(var i = 0; i < projects.length; ++i) {
        $scope.projectNumbers.push(projects[i].projectNumber);
      }
    });

    $scope.listWP = function(p) {
      Restangular.one('work_packages/project', p).getList().then(function(response){
        workPackages = response;
        $scope.workPackageNumbers[p] = []

        for(var i = 0; i < workPackages.length; ++i) {
          $scope.workPackageNumbers[p].push(workPackages[i].workPackageNumber);
        }
      });
    } 

    $scope.prior = function() {
      year = $scope.timesheet.year;
      week = ($scope.timesheet.weekNumber - 1);

      base.get({"year": year, "week": week}).then(function(response){
        $scope.timesheet = response;
      })
    }

    $scope.next = function() {
      year = $scope.timesheet.year;
      week = ($scope.timesheet.weekNumber + 1);

      base.get({"year": year, "week": week}).then(function(response){
        $scope.timesheet = response;
      });
    }

    base.get({"filter":"current"}).then(function(response){
      currentTimesheet = response;
      $scope.timesheet = currentTimesheet;
    });

    $scope.save = function() {  
      if($scope.default && ($rootScope.user.defaultTimesheetID != $scope.timesheet.id)) {
        $rootScope.user.defaultTimesheetID = $scope.timesheet.id;
        $rootScope.user.put();
      }
      $scope.timesheet.put();
    }

    $scope.delete = function($index) {
      $scope.timesheet.timesheetRows.splice($index, 1);
    }

    $scope.add = function() {
      $scope.timesheet.timesheetRows.push(
         { workPackageNumber: "", projectNumber: "", saturday: 0, sunday: 0, monday: 0, tuesday: 0, wednesday: 0,
          thursday: 0, friday: 0, total: 0, note: ""});
    }
  }
]);



/*
    USER PROFILE CONTROLLER
*/
cascadiaControllers.controller('UserProfileController', ['$scope', 'CascadiaService', '$routeParams', 'Restangular',
  function($scope, CascadiaService, $params, Restangular) {
    $scope.param = $params.id;

    var base = Restangular.one('users' + $params.id);

    base.get().then(function(response){
      $scope.user = response;
    });
  }
]);



/*
    WEEKLY PROJECT CONTROLLER
*/
cascadiaControllers.controller('WeeklyProjectController', ['$scope', 'Restangular',
  function($scope, Restangular){
    
  }
]);



/*
    WORK PACKAGE DETAILS CONTROLLER
*/
cascadiaControllers.controller('WPDetailsController', ['$scope', 'Restangular', '$routeParams',
  function($scope, Restangular, $params){
    $scope.param = $params.id;

    var base = Restangular.one('work_packages/' + $scope.param);

    base.get().then(function(response){
      $scope.package = response;
    });
  }
]);



/*
    WORK PACKAGE STATUS REPORT CONTROLLER
*/
cascadiaControllers.controller('WPStatusReportController', ['$scope', 'Restangular',
  function($scope, Restangular){
    
  }
]);



///////////////   API TESTING   ///////////////


/*
    USERS CONTROLLER
    for Users Management API Endpoints

      GET /users
      POST /users
      GET /users/:user_id
      PUT /users/:user_id  
*/
cascadiaControllers.controller('UsersManagementController', ['$scope', '$rootScope', 'Restangular', 'CascadiaService',
  function($scope, $rootScope, Restangular, CascadiaService) {
    var base = Restangular.all('users');

    $scope.items = [ 'P1', 'P2', 'P3', 'P4', 'P5' ];
    $scope.statuses = [ 'Active', 'Inactive' ];


    usersChanged = [];

    base.getList().then(function(response) {
      $scope.users = response;
      CascadiaService.initMapOfUsers($scope.users);
    })

    $scope.change = function(user) {
      unique = true;
      for(var i = 0; i < usersChanged.length; i++) {
        if(usersChanged[i].id == user.id) {
          unique = false;
          break;
        } 
      }

      if(unique) {
        usersChanged.push(user);
      }
    };

    $scope.delete = function(user, $index) {
      user.remove().then(function() {
        $scope.users.splice($index, 1);
      })

      console.log("user deleted");
    }

    $scope.edit = function() {
      for(var i = 0; i < usersChanged.length; i++) {
        usersChanged[i].put();
      }
      usersChanged = [];
    }

    $scope.add = function() {
      $scope.add_user = true;
    }

    $scope.save = function() {
      newuser = $scope.newuser;

      base.post(newuser).then(function(response) {
        $scope.add_user = false;
      });
    }
  }
]);
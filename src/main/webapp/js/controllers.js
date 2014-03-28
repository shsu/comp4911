var cascadiaControllers = angular.module('cascadiaControllers', ['base64']);

/*
    ADD ENGINEER CONTROLLER
*/
cascadiaControllers.controller('EngineerController', ['$scope', 'GrowlResponse', '$routeParams', 'Restangular',
  function($scope, GrowlResponse, $params, Restangular) {
    $scope.param = $params.id;

    var base = Restangular.one('work_packages/' + $scope.param);

    base.get().then(function(response){
      $scope.package = response;
    });

    Restangular.all('users').getList().then(function(response){
      $scope.engineers = response;
    });

  }
]);

/*
    ADD MANAGER CONTROLLER
*/
cascadiaControllers.controller('ManagerController', ['$scope', '$rootScope', '$location', 'GrowlResponse', '$routeParams', 'Restangular',
  function($scope, $rootScope, $location, GrowlResponse, $params, Restangular) {
    var param = $params.id;
    $scope.project = {};

    Restangular.one('projects/' + param).get().then(function(response){
      $scope.project = response;
    })

    Restangular.all('users').getList().then(function(response){
      $scope.users = response;
    });

    $scope.select = function (s) {
      $scope.selectedManager= s;
    };

    
    $scope.search = function (s) {
      if (s.id.toString().indexOf($scope.query) != -1 || s.firstName.indexOf($scope.query) != -1 
          || s.lastName.indexOf($scope.query) != -1){
        return true;
      }
      return false;
    };

    /* Should be checking if user is already assigned to project before updating it */
    $scope.save = function () {
      var data = {
        userId: $scope.selectedManager.id,
        projectNumber: $scope.project.projectNumber,
        projectManager: true,
        active: true
      }
      Restangular.one('projects/' + $scope.project.projectNumber + '/assignments').customPOST(data).then(function(response){
        $location.path('manage-project');
        $.growl.notice({ message: "Project Assigned" });
      })
    };
  }
]);


/*
    ASSIGN EMPLOYEE TO PROJECT W
*/
cascadiaControllers.controller('AEPController', ['$rootScope', '$scope', '$location', '$routeParams', 'Restangular', 'GrowlResponse',
  function($rootScope, $scope, $location, $params, Restangular, GrowlResponse){
    var param = $params.id;
    $scope.cUser = $rootScope.userMap[param];
    $scope.selectedProjects = [];

    Restangular.all('users/' + $scope.cUser.id + '/projects').getList().then(function(response){
      $scope.selectedProjects = response;
    });

    Restangular.all('projects').getList().then(function(response){
      $scope.projects = response;
    });

    $scope.selectP = function($index) {
      if ($scope.projects[$index].disabled) {
        return;
      }

      if (!$scope.projects[$index].selected) {
        $scope.projects[$index].selected = true;
      } else {
        $scope.projects[$index].selected = false;
      }
    }

    $scope.addedSelectP = function($index) {
      if (!$scope.selectedProjects[$index].selected) {
        $scope.selectedProjects[$index].selected = true;
      } else {
        $scope.selectedProjects[$index].selected = false;
      }
    }

    $scope.addP = function() {
      $scope.projects.forEach(addToSelectedP);
    }

    function addToSelectedP(obj) {
      if (obj.selected === true && obj.disabled !== true) {
        $scope.selectedProjects.push({
          projectNumber: obj.projectNumber,
          projectName: obj.projectName,
          selected: false
        });
        obj.disabled = true;
        obj.selected = false;
        var data = {
          userId: $scope.cUser.id,
          projectNumber: obj.projectNumber,
          active: true
        }
        Restangular.one('projects/' + obj.projectNumber + '/assignments').customPOST(data).then(function(response){
            $.growl.notice({ message: "Employee Assigned" });
        });
      }
    }

    $scope.removeP = function() {
      var length = $scope.selectedProjects.length;

      for (var i = 0; i < length;) {
        if (!removeFromSelectedP($scope.selectedProjects[i]))
          i++;
      };
    }

    function removeFromSelectedP(obj) {
      if (obj !== undefined && obj.selected === true) {
        var num = obj.projectNumber;
        for (var i = 0; i < $scope.projects.length; i++) {
          if ($scope.projects[i].projectNumber == num) {
            $scope.projects[i].disabled = false;
          }
        }
        /*
        var data = {
          userId: $scope.cUser.id,
          projectNumber: obj.projectNumber,
          active: false
        }
        Restangular.one('projects/' + obj.projectNumber + '/assignments').customPUT(data).then(function(response){
          $.growl.notice("Success", "Object Created");
        });*/

        var index = $scope.selectedProjects.indexOf(obj);

        if (index > -1) {
          $scope.selectedProjects.splice(index, 1);
        }
        return true;
      }
      return false;
    }
  }
]);



/*
    ASSIGN EMPLOYEE TO WORK PACKAGE CONTROLLER
*/
cascadiaControllers.controller('AEWPController', ['$scope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
  function($scope, $location, Restangular, $params, GrowlResponse){
    $scope.param = $params.id;

    $scope.userList = [];
    $scope.assignedUserList = [];

    var base = Restangular.one('work_packages/' + $scope.param);

    base.get().then(function(response){
      $scope.package = response;
    });

    base.getList("assignments").then(function(response){
      $scope.assignedUsers = response;

      angular.forEach($scope.assignedUsers, function(obj){
        //console.log(obj.id);

        if (obj !== undefined){
          $scope.assignedUserList.push({
            id: obj.id,
            username: obj.username
          });
        }  
      });
    });

    var userBase = Restangular.all('users');

    userBase.getList().then(function(response){
      $scope.users = response;

      angular.forEach($scope.users, function(obj){
        if (obj !== undefined){
          $scope.userList.push({
            id: obj.id,
            username: obj.username
          });
        }   
      });   
    });

    console.log($scope.userList.length);
      //console.log($scope.assignedUserList);

    

    $scope.select = function(id){
      alert(id);
    }

    // code for work package assignment - /work_packages/$scope.param/assignments?
  }
]);



/*
    ASSIGN MANAGER CONTROLLER
*/
cascadiaControllers.controller('ProjectManagementController', ['$scope', '$location', 'GrowlResponse', 'Restangular',
  function($scope, $location, GrowlResponse, Restangular) {
    
    Restangular.all('projects').getList().then(function(response){
      $scope.projects = response;
    });

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

    $scope.select = function(p) {
      $location.path('project-details/' + p.projectNumber);
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
cascadiaControllers.controller('APController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $location, Restangular, GrowlResponse){

    Restangular.one('users', 3).get().then(function(response){
      $scope.manUser = response;
    });

    Restangular.all('projects').getList().then(function(response){
      $scope.projects = response;
    });

    // code for project assignment
  }
]);



/*
    ASSIGN RESPONSIBLE ENGINEER CONTROLLER
*/
cascadiaControllers.controller('ARController', ['$scope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
  function($scope, $location, Restangular, $params, GrowlResponse){
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
    ASSIGN TIMESHEET APPROVER CONTROLLER
*/
cascadiaControllers.controller('ATAController', ['$scope', '$modal', '$rootScope', '$routeParams', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $modal, $rootScope, $params, $location, Restangular, GrowlResponse){
    var base = Restangular.all('users');
    var param = $params.id;
    $scope.cUser = {};
    $scope.ta = {};

    base.getList().then(function(response) {
      $scope.users = response;
    }, function(response){
      GrowlResponse(response);
    });

    Restangular.one('users', param).get().then(function(response){
      $scope.cUser = response;
      if($scope.cUser.timesheetApproverUserID) {
        loadTimsheetApprover();
      }
    });

    $scope.select = function (s) {
      $scope.ta= s;
    };

    var loadTimesheetApprover = function() {
      Restangular.one('users', $scope.cUser.timesheetApproverUserID).get().then(function(response){
        $scope.ta = response;
      });
    }

    $scope.hasTimesheetApprover = function() {
        var u = $scope.cUser;
        if(u && u.timesheetApproverUserID) {
          return true;
        }
        return false;
    }
    
    $scope.search = function (s) {
      if (s.id.toString().indexOf($scope.query) != -1 || s.firstName.indexOf($scope.query) != -1 
          || s.lastName.indexOf($scope.query) != -1 || !$scope.query){
        return true;
      }
      return false;
    };

    $scope.open = function () {

      var modalInstance = $modal.open({
        templateUrl: 'myModalContent.html',
        controller: ModalInstanceCtrl,
        resolve: {
          item: function () {
            return $scope.ta;
          }
        }
      });

      modalInstance.result.then(function () {
        var user = $scope.cUser;
        user.timesheetApproverUserID = $scope.ta.id;
        persist(user);
      }, function(){
        console.log("dismissed")
      });

      var persist = function(user) {
        Restangular.one('users', user.id).customPUT(user).then(function(response){
          $location.path('users/' + user.id);
        })
      }
    }
  }
]);


/*
    ASSIGN SUPERVISOR CONTROLLER
*/
cascadiaControllers.controller('ASController', ['$scope', '$modal', '$rootScope', '$routeParams', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $modal, $rootScope, $params, $location, Restangular, GrowlResponse){
    var base = Restangular.all('users');
    var param = $params.id;
    $scope.cUser = {};
    $scope.supervisor = {};

    base.getList().then(function(response) {
      $scope.users = response;
    }, function(response){
      GrowlResponse(response);
    });

    Restangular.one('users', param).get().then(function(response){
      $scope.cUser = response;
      if($scope.cUser.supervisorUserID) {
        loadSupervisor();
      }
    });

    $scope.select = function (s) {
      $scope.selectedEngineer= s;
    };

    var loadSupervisor = function() {
      Restangular.one('users', $scope.cUser.supervisorUserID).get().then(function(response){
        $scope.supervisor = response;
      });
    }

    $scope.hasSupervisor = function() {
        var u = $scope.cUser;
        if(u && u.supervisorUserID) {
          return true;
        }
        return false;
    }
    
    $scope.search = function (s) {
      if (s.id.toString().indexOf($scope.query) != -1 || s.firstName.indexOf($scope.query) != -1 
          || s.lastName.indexOf($scope.query) != -1 || !$scope.query){
        return true;
      }
      return false;
    };

    $scope.open = function () {

      var modalInstance = $modal.open({
        templateUrl: 'myModalContent.html',
        controller: ModalInstanceCtrl,
        resolve: {
          item: function () {
            return $scope.selectedEngineer;
          }
        }
      });

      modalInstance.result.then(function () {
        var user = $scope.cUser;
        user.supervisorUserID = $scope.selectedEngineer.id;
        if(!user.timesheetApproverUserID){
          user.timesheetApproverUserID = $scope.selectedEngineer.id;
          persist(user);
        } else{
          persist(user);
        }
      }, function(){
        console.log("dismissed")
      });

      var persist = function(user) {
        Restangular.one('users', user.id).customPUT(user).then(function(response){
          $location.path('users/' + user.id);
        })
      }
    }
  }
]);

var ModalInstanceCtrl = function ($scope, $modalInstance, item) {

  $scope.item = item;

  $scope.ok = function () {
    $modalInstance.close();
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
};

/*
    ASSIGN WP CONTROLLER
*/
cascadiaControllers.controller('PackageController', ['$scope', 'GrowlResponse',
  function($scope, GrowlResponse) {
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
cascadiaControllers.controller('CreateProjectsController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $location, Restangular, GrowlResponse){
    var base = Restangular.all('projects');
    $scope.rates = [1.2, 1.6, 2.0]
    
    $scope.save = function () {
      if (!($scope.createProjectForm.$valid)) {
        $.growl.warning({ message: "Invalid Input" });
      } else {
        base.post($scope.project).then(function (response) {
            $.growl.notice({ message: "Project Created" });
            $location.path("/projects");
        }, function (response) {
          GrowlResponse(response);
        });
      }
      
    }
  }
]);



/*
    CREATE WP CONTROLLER
*/
cascadiaControllers.controller('CreateWPController', ['$scope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
  function($scope, $location, Restangular, $params, GrowlResponse){
    $scope.param = $params.id;
    $scope.workPackage = {}
    $scope.project = {}

    Restangular.one('projects/' + $scope.param).get().then(function(response){
        $scope.project = response;
      });

    $scope.statuses = [ 'Open', 'Closed'];

    $scope.save = function() {
      workPackage = $scope.workPackage;
      workPackage.projectNumber = $scope.project.projectNumber;

      Restangular.one('work_packages').customPOST(workPackage).then(function(response){
        $.growl.notice({ message: "Work Package Created" });
      }, function(response){
        GrowlResponse(response);
      })
    }
  }
]);



/*
    DASHBOARD CONTROLLER
*/
cascadiaControllers.controller('DashboardController', ['$scope', '$rootScope', 'Restangular', 'GrowlResponse',
  function($scope, $rootScope, Restangular, GrowlResponse) {

    Restangular.all('user/timesheets').getList().then(function(response) {
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
cascadiaControllers.controller('EditPayRatesController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $location, Restangular, GrowlResponse) {
    var base = Restangular.all('pay_rates');
    $scope.payRatesMap = {}
    $scope.payRates = []

    base.getList().then(function(response){
      $scope.payRates = response;

      for(var i = 0; i < $scope.payRates.length; ++i) {
        $scope.payRatesMap[$scope.payRates[i].year] = []
      }

      for(var i = 0; i < $scope.payRates.length; ++i){
        $scope.payRatesMap[$scope.payRates[i].year].push($scope.payRates[i]);
      }
    });

    $scope.index = 2014;

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
      $.growl.notice({ message: "Pay Rates Saved" });
    }

    $scope.prior = function() {
      if($scope.payRatesMap[($scope.index - 2)])
      {
        $scope.index--;
      }
    }

    $scope.next = function() {
      if($scope.payRatesMap[($scope.index + 2)])
      {
        $scope.index++;
      }
    }
    $scope.current = function() {
        $scope.index=2014;
    }
    
  }
                                                          
]);



/*
    ENGINEER BUDGET CONTROLLER
*/
cascadiaControllers.controller('EngineerBudgetController', ['$scope', '$location', 'Restangular',
  function($scope, $location, Restangular){
    
  }
]);



/*
    NAVIGATION CONTROLLER
*/
cascadiaControllers.controller('NavigationController', ['$scope', '$rootScope', '$location', 'Restangular', 'GrowlResponse', 'AuthenticateUser',
  function($scope, $rootScope, $location, Restangular, GrowlResponse, AuthenticateUser) {
    AuthenticateUser($rootScope);
    $scope.user = JSON.parse(localStorage.getItem('user'));

    $scope.logout = function() {
      localStorage.clear();
      $location.path('/login');
      $.growl.notice({ message: "You Have Been Logged Out" });
    }
  }
]);

/*
    UNAUTHORIZED CONTROLLER

*/

cascadiaControllers.controller('UnauthorizedController', ['$scope', 
  function($scope) {

  }
])

/*
    LOGIN CONTROLLER
*/
cascadiaControllers.controller('LoginController', ['$scope', '$base64', 'Restangular', '$rootScope', '$location', 'GrowlResponse', 'permissions',
  function ($scope, $base64, Restangular, $rootScope, $location, GrowlResponse, permissions) {
    $scope.login = function () {

      if (!($scope.loginForm.$valid)) {
        $.growl.warning({ message: "Invalid Input" });
      } else {
        var data = {
          'username': $scope.username,
          'password': $scope.password
        };

        Restangular.one('user').post('token', data).then(function (response) {
          $scope.encodedString = $base64.encode(response.token + ":");
          localStorage.setItem('token', $scope.encodedString);
          Restangular.setDefaultHeaders({
            'Authorization': 'Basic ' + $scope.encodedString
          });

          Restangular.one('user').get().then(function (response) {
            localStorage.setItem('user', JSON.stringify(response));
            $rootScope.user = response;

            Restangular.one('user/permissions').get().then(function (response) {
              permissions.setPermissions(response);
              localStorage.setItem('permissions', JSON.stringify(response))
            }, function (response) {
              var per = [{ Name: 'ProjectManager' }, { Name: 'Supervisor' }, { Name: 'Hr' }, { Name: 'TimesheetApprover' }];
              localStorage.setItem('permissions', JSON.stringify(per));
              permissions.setPermissions(per)
            });

            $location.path('/dashboard');
          });
        }, function (response) {
          GrowlResponse(response);
        });
      }
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
cascadiaControllers.controller('ManageApproverController', ['$scope', '$location', 'Restangular',
  function($scope, $location, Restangular){
    
  }
]);



/*
    MANAGE PROJECT CONTROLLER
*/
cascadiaControllers.controller('ManageProjectController', ['$scope', '$location', 'Restangular',
  function($scope, $location, Restangular){
    
  }
]);



/*
   MANAGE WP CONTROLLER
*/
cascadiaControllers.controller('WPManagementController', ['$scope', 'Restangular', 'GrowlResponse',
  function($scope, Restangular, GrowlResponse){
    $scope.statuses = [ 'Open', 'Closed'];
    $scope.project = {};
    $scope.projectChosen = false;
    mapOfWP = {};
    wpChanged = [];

    $scope.loadProjects = function() {
      Restangular.all('projects').getList().then(function(response){
        $scope.projects = response;
      });
    }

    $scope.loadWorkPackages = function(projectNumber) {
      Restangular.one('work_packages/project').getList($scope.project.projectNumber).then(function(response){
        $scope.projectChosen = true;
        $scope.workPackages = response;
      });
    }

    Restangular.one('user/projects/managed').getList().then(function(response){
      $scope.projects = response;
    });

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
cascadiaControllers.controller('ProjectManagementSupervisorController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $location, Restangular, GrowlResponse) {
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
          $location.path('/projects');
          $.growl.notice({ message: "Project Created" });
      })
    }
  }
]);


/*
    PROJECT DETAILS CONTROLLER
*/
cascadiaControllers.controller('ProjectDetailsController', ['$scope', '$routeParams', 'Restangular',
  function($scope, $params, Restangular){
    var param = $params.id;

    $scope.project = {}

    Restangular.one('projects', param).get().then(function(response){
      $scope.project = response;
    })
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
cascadiaControllers.controller('RateSheetController', ['$scope', 'Restangular', 'GrowlResponse',
  function($scope, Restangular, GrowlResponse){
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
    TIMESHEET DETAILS FOR APPROVAL CONTROLLER
*/

cascadiaControllers.controller('TADetailsController', ['$scope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
  function($scope, $location, Restangular, $params, GrowlResponse){
    $scope.param = $params.id;

    Restangular.one('timesheets', $scope.param).get().then(function(response){
      $scope.timesheet = response;
    });

    $scope.approve = function() {
      $scope.timesheet.approved = true;
      $scope.timesheet.pending = false;
      $scope.timesheet.put();
      $location.path('/timesheet-approval');
    }

    $scope.reject = function() {
      $scope.timeshet.pending = false;
      $scope.timesheet.put();
      $location.path('/timesheet-approval');
    }
  }
]);

/*
    TIMESHEET APPROVAL CONTROLLER
*/

cascadiaControllers.controller('TAController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
  function($scope, $location, Restangular, GrowlResponse){

    $scope.timesheets = []

    Restangular.all('user/timesheets/to_approve').getList().then(function(response){
      $scope.timesheets = response;
    })

    $scope.select = function($index) {
      var ndx = $index;
      var str = "/timesheet/";
      var path = str.concat($scope.timesheets[ndx].id);
      $location.path(path);
    }
  }
]);


/*
    TIMESHEET CONTROLLER
*/
cascadiaControllers.controller('TimesheetController', ['$scope', '$rootScope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
  function($scope, $rootScope, $location, Restangular, $params, GrowlResponse) {
    var base = Restangular.one('user/timesheets');

    $scope.workPackageNumbers = {};
    $scope.projectNumbers = [];

    base.get({"filter":"current"}).then(function(response){
      currentTimesheet = response;
      $scope.timesheet = currentTimesheet;
    }, function(response){
      GrowlResponse(response);
    });

    Restangular.one('user/projects/managed').getList().then(function(response){
      projects = response;
      for(var i = 0; i < projects.length; ++i) {
        $scope.projectNumbers.push(projects[i].projectNumber);
      }
    }, function(response){
      GrowlResponse(response);
    });

    $scope.listWP = function(p) {
      Restangular.one('work_packages/project', p).getList().then(function(response){
        workPackages = response;
        $scope.workPackageNumbers[p] = []

        for(var i = 0; i < workPackages.length; ++i) {
          $scope.workPackageNumbers[p].push(workPackages[i].workPackageNumber);
        }
      }, function(response){
        GrowlResponse(response);
      });
    } 

    $scope.prior = function() {
      year = $scope.timesheet.year;
      week = ($scope.timesheet.weekNumber - 1);

      base.get({"year": year, "week": week}).then(function(response){
        $scope.timesheet = response;
      }, function(response){
        GrowlResponse(response);
      })
    }

    $scope.next = function() {
      year = $scope.timesheet.year;
      week = ($scope.timesheet.weekNumber + 1);

      base.get({"year": year, "week": week}).then(function(response){
        $scope.timesheet = response;
      }, function(response){
        GrowlResponse(response);
      });
    }

    $scope.save = function() {  
      if($scope.default && ($rootScope.user.defaultTimesheetID != $scope.timesheet.id)) {
        $rootScope.user.defaultTimesheetID = $scope.timesheet.id;
        $rootScope.user.put();
      }
      $scope.timesheet.put();
      $.growl.notice({ message: "TimeSheet Saved" });
    }

    $scope.submit = function() {
      $scope.timsheet.pending = true;
    }

    $scope.delete = function($index) {
      $scope.timesheet.timesheetRows.splice($index, 1);
    }

    $scope.add = function() {
      $scope.timesheet.timesheetRows.push(
         { workPackageNumber: "", projectNumber: "", saturday: 0, sunday: 0, monday: 0, tuesday: 0, wednesday: 0,
          thursday: 0, friday: 0, note: ""});
    }
  }
]);



/*
    USER PROFILE CONTROLLER
*/
cascadiaControllers.controller('UserProfileController', ['$scope', '$rootScope', '$routeParams', 'Restangular',
  function($scope, $rootScope, $params, Restangular) {
    $rootScope.user = JSON.parse(localStorage.getItem('user'));

    $scope.hasSupervisor = function() {
      var user = $rootScope.user;
      if(user && user.supervisorUserID) {
        return true;
      }
      return false;
    }

    $scope.hasTimesheetApprover = function() {
      user = $rootScope.user;
      if(user && user.timesheetApproverUserID) {
        return true;
      }
      return false;
    }

    $scope.loadSupervisor = function() {
      Restangular.one('users', $rootScope.user.supervisorUserID).get().then(function(response){
        $scope.supervisor = response;
      });
    }

    $scope.loadTimesheetApprover = function() {
      Restangular.one('users', $rootScope.user.timesheetApproverUserID).get().then(function(response){
        $scope.timesheetApprover = response;
      });
    }

    if($rootScope.user) {
      if($rootScope.user.supervisorUserID){
        $scope.loadSupervisor();
        if($rootScope.user.supervisorUserID != $rootScope.user.timesheetApproverUserID) {
          $scope.loadTimesheetApprover();
        }
      }
    }
  }
]);

/*
    MANAGED USER PROFILE CONTROLLER
*/
cascadiaControllers.controller('ManagedUserProfileController', ['$scope', '$location', '$rootScope', '$routeParams', 'Restangular',
  function($scope, $location, $rootScope, $params, Restangular) {
    var param = $params.id;
    $scope.supervisor = {};
    $scope.timesheetApprover = {};

    Restangular.one('users', param).get().then(function(response){
      $scope.cUser = response;
      if($scope.cUser.supervisorUserID){
        loadSupervisor();
        if($scope.cUser.supervisorUserID != $scope.cUser.timesheetApproverUserID) {
          loadTimesheetApprover();
        } else if($scope.cUser.timesheetApproverUserID) {
          $scope.timesheetApprover = $scope.supervisor;
        }
      }
    });

    var loadSupervisor = function() {
      Restangular.one('users', $scope.cUser.supervisorUserID).get().then(function(response){
        $scope.supervisor = response;
      });
    }

    var loadTimesheetApprover = function() {
      Restangular.one('users', $scope.cUser.timesheetApproverUserID).get().then(function(response){
        $scope.timesheetApprover = response;
      });
    }

    $scope.hasSupervisor = function() {
      user = $scope.cUser;
      if(user && user.supervisorUserID) {
        return true;
      }
      return false;
    }

    $scope.hasTimesheetApprover = function() {
      user = $scope.cUser;
      if(user && user.timesheetApproverUserID) {
        return true;
      }
      return false;
    }

    $scope.assignSupervisor = function() {
      $location.path('/assign-supervisor/' + $scope.cUser.id);
    }

    $scope.assignTA = function() {
      $location.path('/assign-ta/' + $scope.cUser.id);
    }
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
cascadiaControllers.controller('WPDetailsController', ['$scope', 'Restangular', '$routeParams', 'GrowlResponse', '$timeout',
  function($scope, Restangular, $params, GrowlResponse, $timeout){
    $scope.param = $params.id;

    var base = Restangular.one('work_packages/' + $scope.param);

    base.get().then(function(response){
      $scope.package = response;
    });

    Restangular.all('work_packages/' + $scope.param + '/assignments').getList().then(function(response){
      $scope.assignedUsers = response;
    });

    $timeout(function(){
      Restangular.one('projects/' + $scope.package.projectNumber).get().then(function(response){
        $scope.project = response;
      });
    }, 300);
         
  }
]);



/*
    WORK PACKAGE STATUS REPORT CONTROLLER
*/
cascadiaControllers.controller('WPStatusReportController', ['$scope', 'Restangular',
  function($scope, Restangular){
    
  }
]);

/*
    CREATE USER CONTROLLER
*/
cascadiaControllers.controller('CreateUserController', ['$scope', 'Restangular', 'GrowlResponse', '$location',
  function($scope, Restangular, GrowlResponse, $location){

    $scope.items = [ 'P1', 'P2', 'P3', 'P4', 'P5' ];
    $scope.statuses = [ 'Active', 'Inactive' ];
    
    $scope.cUser = {}
    $scope.cUser.supervisorUserID = $scope.user.id;

    $scope.save = function () {

      if(!($scope.createUserForm.$valid)) {
        $.growl.warning({ message: "Invalid or Incomplete Input" });
      } else {
        user = $scope.cUser;

        Restangular.one('users').customPOST(user).then(function (response) {
          $location.path('/users');
          $.growl.notice({ message: "User Created" });
        }, function (response) {
          GrowlResponse(response);
        })
      }
    }
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
cascadiaControllers.controller('UsersManagementController', ['$scope', '$location', '$rootScope', 'Restangular', 'GrowlResponse',
  function($scope, $location, $rootScope, Restangular, GrowlResponse) {
    var base = Restangular.all('users');

    $scope.items = [ 'P1', 'P2', 'P3', 'P4', 'P5' ];
    $scope.statuses = [ 'Active', 'Inactive' ];
    $scope.cUser = {}

    base.getList().then(function(response) {
      $scope.users = response;
    }, function(response){
      GrowlResponse(response);
    });

    $scope.hasSupervisor = function(u) {
      if(u && u.supervisorUserID) {
        return true;
      }
      return false;
    }

    $scope.select = function(u) {
      $location.path('/users/' + u.id);
    }

    $scope.search = function(item) {
      if (item.id.toString().indexOf($scope.query) != -1 || item.firstName.indexOf($scope.query) != -1 ||
                item.lastName.indexOf($scope.query) != -1 || item.username.indexOf($scope.query) != -1 ||
                item.pLevel == $scope.query || !$scope.query) {
        return true;
      }
      return false;
    }

    $scope.delete = function(user, $index) {
      user.remove().then(function() {
        $scope.users.splice($index, 1);
      })

      console.log("user deleted");
    }

    $scope.add = function() {
      $scope.add_user = true;
    }
  }
]);
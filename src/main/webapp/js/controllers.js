var cascadiaControllers = angular.module('cascadiaControllers', ['base64', 'restangular']);

cascadiaControllers.service('CascadiaService', function($rootScope) {
  $rootScope.user = JSON.parse(localStorage.getItem('user'));
});



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
    // Restangular.one('package').post() ...
  }
]);



/*
    EDIT PAY RATES CONTROLLER
*/
cascadiaControllers.controller('EditPayRatesController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular){
    
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
    INDEX CONTROLLER
*/
cascadiaControllers.controller('IndexController', ['$scope', 'CascadiaService', '$location',
  function($scope, CascadiaService, $location) {
    $scope.logout = function() {
      localStorage.clear();
      $location.path('/login');
    }
  }
]);



/*
    TIMESHEET MANAGEMENT CONTROLLER
*/
cascadiaControllers.controller('TimesheetController', ['$scope', 'CascadiaService', '$location', 'Restangular',
  function($scope, CascadiaService, $location, Restangular) {
    Restangular.all('timesheets').getList().then(function(response) {
      $scope.timesheets = response;
    });
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
          $location.path('/dashboard');
        });
      });
    };
  }
]);



/*
    DASHBOARD CONTROLLER
*/
cascadiaControllers.controller('DashboardController', ['$scope', '$rootScope', 'Restangular', 'CascadiaService',
  function($scope, $rootScope, Restangular, CascadiaService) {
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
    PACKAGE CONTROLLER
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
    ENGINEER CONTROLLER
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
    MANAGER CONTROLLER
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
    PROJECT MANAGEMENT CONTROLLER
*/
cascadiaControllers.controller('ProjectManagementController', ['$scope', 'CascadiaService', 'Restangular',
  function($scope, CascadiaService, Restangular) {
    var base = Restangular.all('projects');

    base.getList().then(function(response){
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


cascadiaControllers.controller('ProfileController', ['$scope', 'CascadiaService',
  function($scope, CascadiaService) {}
]);



/*
    USERS CONTROLLER
    for Users Management API Endpoints

      GET /users
      POST /users
      GET /users/:user_id
      PUT /users/:user_id  
*/
cascadiaControllers.controller('UsersManagementController', ['$scope', 'Restangular',
  function($scope, Restangular) {
    var base = Restangular.all('users');

    base.getList().then(function(response) {
      $scope.users = response;
    })

    $scope.delete = function(user, $index) {
      user.remove().then(function() {
        $scope.users.splice($index, 1);
      })

      console.log("user deleted");
    }

    $scope.edit = function(user) {
      user.put();
      console.log(user);
    }

    $scope.add = function() {
      $scope.add_user = true;
    }

    $scope.save = function() {
      newuser = $scope.newuser;

      base.post(newuser).then(function(response) {
        $scope.add_user = false;
      })
    }
  }
]);
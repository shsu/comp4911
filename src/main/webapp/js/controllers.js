var cascadiaControllers = angular.module('cascadiaControllers', ['base64', 'restangular']);

/*
    LOGIN CONTROLLER
*/
cascadiaControllers.controller('LoginController', ['$scope', '$base64', 'Restangular', function($scope, $base64, Restangular) {

  $scope.login = function() {
    var data = {'username' : $scope.username, 'password' : $scope.password};
    Restangular.one('user').post('token', data).then(function(response) {
      $scope.encodedString = $base64.encode(response.token + ":");
     Restangular.setDefaultHeaders({'Authorization': 'Basic ' + $scope.encodedString});
    });
  };
}]);



///////////////////////////////////////////////////////////////////////////////////////////////

/*
    PACKAGE CONTROLLER
*/
cascadiaControllers.controller('PackageController', ['$scope', 
  function ($scope){
    $scope.packages = [
      {number: 'B1111', title: 'Project Setup', selected: false, disabled: false},
      {number: 'B1112', title: 'Ongoing Updating', selected: false, disabled: false},
      {number: 'B1113', title: 'Detailed Planning', selected: false, disabled: false}
    ];

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
        $scope.selectedPackages.push({number: obj.number, title: obj.title, selected: false});
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

///////////////////////////////////////////////////////////////////////////////////////////////

/*
    ENGINEER CONTROLLER
*/
cascadiaControllers.controller('EngineerController', ['$scope',
  function ($scope){
    $scope.engineers = [
      {number: 'A1111', name: 'Javier Olson', paygrade: 'P4', selected: false, disabled: false},
      {number: 'A1112', name: 'Nancy Garcia', paygrade: 'P5', selected: false, disabled: false},
      {number: 'A1113', name: 'David Bowden', paygrade: 'SS', selected: false, disabled: false}
    ];

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
        $scope.selectedEngineers.push({number: obj.number, name: obj.name, paygrade: obj.paygrade, selected: false});
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

///////////////////////////////////////////////////////////////////////////////////////////////

/*
    MANAGER CONTROLLER
*/
cascadiaControllers.controller('ManagerController', ['$scope',
  function ($scope){
    $scope.managers = [
      {id: 'A1111', name: 'Javier Olson', paygrade: 'P4', selected: false},
      {id: 'A1112', name: 'Nancy Garcia', paygrade: 'P2', selected: false},
      {id: 'A1113', name: 'David Bowden', paygrade: 'P5', selected: false},
      {id: 'A1114', name: 'Juan Burdine', paygrade: 'SS', selected: false}
    ];

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

///////////////////////////////////////////////////////////////////////////////////////////////

/*
    USERS CONTROLLER
    for Users Management API Endpoints

      GET /users
      POST /users
      GET /users/:user_id
      PUT /users/:user_id  
*/

cascadiaControllers.controller('UsersManagementController', ['$scope', 'Restangular',
  function ($scope, Restangular){
    Restangular.one('user').get().then(function(response){
      $scope.user = response;
    });

    /*
    $scope.users = [
      {employeeID: 1111, name: "steven", username: "shsu", plevel: 3},
      {employeeID: 1112, name: "graeme", username: "gfunk", plevel: 3},
      {employeeID: 1113, name: "chris", username: "charris", plevel: 1}
    ];

  */

    $scope.delete = function($index){
      $scope.users.splice($index, 1);
      var user = Restangular.one('users');

      console.log("user deleted");
    }

    $scope.edit = function($user, $index){ 
      alert($scope.users[0].plevel);
      console.log($user);
    }

  }
]);



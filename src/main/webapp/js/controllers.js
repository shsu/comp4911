var cascadiaControllers = angular.module('cascadiaControllers', ['base64']);

/*
    ADD ENGINEER CONTROLLER
    */
    cascadiaControllers.controller('EngineerController', ['$scope', '$modal', 'FilterUser', 'GrowlResponse', 
      '$routeParams', 'Restangular',
      function($scope, $modal, FilterUser, GrowlResponse, $params, Restangular) {
        $scope.param = $params.id;
        $scope.package = {};
        $scope.quantity = 20;
        $scope.engineers = [];
        $scope.selectedEngineer = {};

        var base = Restangular.one('work_packages/' + $scope.param);

        base.get().then(function(response){
          $scope.package = response;
          loadProject();
        });

        var loadProject = function() {
          Restangular.one('projects', $scope.package.projectNumber).get().then(function(response){
            $scope.project = response;
          })
        }

        Restangular.all('users').getList().then(function(response){
          $scope.engineers = response;
        });

        $scope.search = function(user) {
          return FilterUser(user, $scope.query);
        }

        $scope.select = function(user) {
          $scope.selectedEngineer = user;
        }

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
            var base = Restangular.one('work_packages', $scope.package.workPackageNumber);
            base.one('assignments', $scope.selectedEngineer.id).get().then(function(response){
              if(response.length){
                wpAssignment = response[0];
                wpAssignment.responsibleEngineer = true;
                base.one('assignments', $scope.selectedEngineer.id).customPUT(wpAssignment).then(function(response){
                  GrowlResponse(response)
                }, function(response){
                  GrowlResponse(response)
                })
              } else {
                var data = {
                  workPackageNumber: $scope.package.workPackageNumber,
                  userId: $scope.selectedEngineer.id,
                  responsibleEngineer: true
                }
                base.one('assignments').customPOST(data).then(function(response){
                  GrowlResponse(response)
                }, function(response){
                  GrowlResponse(response)
                })
              }
            })
          }, function(){
            console.log("dismissed")
          });
}
}
]);

/*
    ADD MANAGER CONTROLLER
    */
    cascadiaControllers.controller('ManagerController', ['$scope', '$rootScope', '$modal', '$location', 'FilterUser',
     'GrowlResponse', '$routeParams', 'Restangular',
     function($scope, $rootScope, $modal, $location, FilterUser, GrowlResponse, $params, Restangular) {
      var param = $params.id;
      $scope.project = {};
      $scope.quantity = 20;

      Restangular.one('projects/' + param).get().then(function(response){
        $scope.project = response;
        loadManager();
      })

      Restangular.all('users').getList().then(function(response){
        $scope.users = response;
      });

      var loadManager = function() {
        Restangular.one('projects/' + $scope.project.projectNumber + '/assignments/manager').get().then(function(response) {
          $scope.curProjectManager = response[0];
        })
      }

      $scope.hasProjectManager = function() {
        if(angular.isDefined($scope.curProjectManager)) {
          return ($scope.curProjectManager.id);
        }
      }

      $scope.select = function (s) {
        $scope.selectedManager= s;
      };


      $scope.search = function (user) {
        return FilterUser(user, $scope.query);
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
          toastr.success("Project Assigned");
        })
      };

      $scope.open = function () {

        var modalInstance = $modal.open({
          templateUrl: 'myModalContent.html',
          controller: ModalInstanceCtrl,
          resolve: {
            item: function () {
              return $scope.selectedManager;
            }
          }
        });

        modalInstance.result.then(function () {
          var base = Restangular.one('projects', $scope.project.projectNumber);

          if(angular.isDefined($scope.curProjectManager)) {
            base.one('assignments', $scope.curProjectManager.id).get().then(function(response){
              var assign = response[0];
              assign.projectManager = false;
              base.one('assignments/' + $scope.curProjectManager.id).customPUT(assign).then(function(response) {
                GrowlResponse(response);
              }, function(response) {
                GrowlResponse(response);
              })
            })
          }
          base.one('assignments', $scope.selectedManager.id).get().then(function(response){
            if(response.length){
              projectAssignment = response[0];
              projectAssignment.projectManager = true;
              base.one('assignments/' + $scope.selectedManager.id).customPUT(projectAssignment).then(function(response){
                $location.path('/projects');
              }, function(response){
                GrowlResponse(response)
              })
            } 
            else {
              var data = {
                projectNumber: $scope.project.projectNumber,
                userId: $scope.selectedManager.id,
                projectManager: true,
                active: true
              }
              base.one('assignments').customPOST(data).then(function(response){
                $location.path('/projects');
              }, function(response){
                GrowlResponse(response)
              })
            }
          })
        }, function(){
          console.log("dismissed")
        });
}
}
]);


/*
    ASSIGN EMPLOYEE TO PROJECT W
    */
    cascadiaControllers.controller('AEPController', ['$rootScope', '$scope', '$location', '$routeParams', 'Restangular', 'GrowlResponse',
      function($rootScope, $scope, $location, $params, Restangular, GrowlResponse){
        var param = $params.id;
        var projectList = [];
        var userProjectList = [];
        $scope.projects = [];

        $scope.quantity = 20;

        // get the current user and force a synchronous loading of projects
        Restangular.one('users', param).get().then(function(response){
          $scope.cUser = response;
          getAllProjects();
        });

        /*  
          get all projects, give them a default state of selected=false, and store in projectList[]
        */
        var getAllProjects = function(){
          Restangular.all('projects').getList().then(function(response){
            projectList = response;

            for (var i = 0; i < projectList.length; i++){
              projectList[i].selected = false;
            }

            getAllUserProjects();
          });
        }

        /*
          get all assigned projects, store in userProjectList
          compare userProjectList with projectList, assigning selected=true to matching projects
          finally, store in $scope.projects
        */
        var getAllUserProjects = function(){
          Restangular.all('users/' + $scope.cUser.id + '/projects').getList().then(function(response){
            userProjectList = response;

            for (var i = 0; i < userProjectList.length; i++){
              for (var j = 0; j < projectList.length; j++){
                if (userProjectList[i].projectNumber == projectList[j].projectNumber){
                  projectList[j].selected = true;
                  projectList[j].markedForRemoval = false;
                }
              }
            }

            angular.copy(projectList, $scope.projects);
          });
        } 

        $scope.markForAssignment = function($index){
          var state = $scope.projects[$index] && $scope.projects[$index].markedForAssignment;

          if (state == undefined || state == false){
            $scope.projects[$index].markedForAssignment = true;
          } else {
            $scope.projects[$index].markedForAssignment = false;
          }
        }

        $scope.markForRemoval = function($index){
          var state = $scope.projects[$index] && $scope.projects[$index].markedForRemoval;

          if (state == undefined || state == false){
            $scope.projects[$index].markedForRemoval = true;
          } else {
            $scope.projects[$index].markedForRemoval = false;
          }
          
        }

        $scope.add = function(){
          var length = $scope.projects.length;

          for (var i = 0; i < length; i++){
            if ($scope.projects[i].markedForAssignment){
              var projNum = $scope.projects[i].projectNumber;
              $scope.projects[i].selected = true;

              var updateAssignment = function(assignment) {
                assignment.active = true;
                Restangular.one('projects/' + projNum + '/assignments/' + $scope.cUser.id).customPUT(assignment).then(function(response) {
                  toastr.success("Employee Assigned");
                })
              }

              var createAssignment = function() {
                var data = {
                  userId: $scope.cUser.id,
                  projectNumber: projNum,
                  active: true,
                }

                Restangular.one('projects/' + projNum + '/assignments').customPOST(data).then(function(response){
                  toastr.success("Employee Assigned");
                });
              }

              Restangular.all('projects/' + projNum + '/assignments/' + $scope.cUser.id).getList().then(function(response) {
                if(response.length > 0) {
                  console.log("Greater than 0");
                  updateAssignment(response[0]);
                } else {
                  console.log("zero");
                  createAssignment();
                }
              });
            }
          }
        }

        $scope.remove = function(){
          var length = $scope.projects.length;

          for (var i = 0; i < length; i++){
            if ($scope.projects[i].markedForRemoval){
              var projNum = $scope.projects[i].projectNumber;

              Restangular.all('projects/' + projNum + '/assignments/' + $scope.cUser.id).getList().then(function(response){
                console.log(response);
                var data = response[0];
                data.active = false;
                Restangular.one('projects/' + projNum + '/assignments/' + $scope.cUser.id).customPUT(data);
              });

              $scope.projects[i].selected = false;
            }
          }
        }
}]);



/*
    ASSIGN EMPLOYEE TO WORK PACKAGE CONTROLLER
    */
    cascadiaControllers.controller('AEWPController', ['$scope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
      function($scope, $location, Restangular, $params, GrowlResponse){
        var param = $params.id;
        $scope.project = {};
        $scope.package = {};
        $scope.users = [];
        $scope.quantity = 20;

        var userList = [];
        var assignedUserList = [];

        Restangular.one('work_packages', param).get().then(function(response){
          $scope.package = response;
          getProjectNumber($scope.package);
        });

        var getProjectNumber = function(obj){
          Restangular.one('projects', obj.projectNumber).get().then(function(response){
            $scope.project = response;
            getUsersAssignedToProject($scope.project);
          });    
        }

        var getUsersAssignedToProject = function(obj){
          Restangular.all('projects/' + obj.projectNumber + '/users').getList().then(function(response){
            userList = response;

            for (var i = 0; i < userList.length; i++){
              userList[i].selected = false;
            }

            getUsersAssignedToWorkPackage();
          });   
        }

        var getUsersAssignedToWorkPackage = function(){
          Restangular.all('work_packages/' + param + '/assignments').getList().then(function(response){
            assignedUserList = response;

            for (var i = 0; i < assignedUserList.length; i++){
              for (var j = 0; j < userList.length; j++){
                if (assignedUserList[i].Id == userList[j].Id){
                  userList[j].selected = true;
                  userList[j].markedForRemoval = false;
                }
              } 
            }

            angular.copy(userList, $scope.users);
          });
        }   

        $scope.markForAssignment = function($index){
          var state = $scope.users[$index] && $scope.users[$index].markedForAssignment;

          if (state == undefined || state == false){
            $scope.users[$index].markedForAssignment = true;
          } else {
            $scope.users[$index].markedForAssignment = false;
          }
        }

        $scope.markForRemoval = function($index){
          var state = $scope.users[$index] && $scope.users[$index].markedForRemoval;

          if (state == undefined || state == false){
            $scope.users[$index].markedForRemoval = true;
          } else {
            $scope.users[$index].markedForRemoval = false;
          }   
        }

        $scope.remove = function(){
          var length = $scope.users.length;

          for (var i = 0; i < length; i++){
            if ($scope.users[i].markedForRemoval){
              var user_id = $scope.users[i].id;

              Restangular.all('work_packages/' + param + '/assignments/' + user_id).getList().then(function(response){
                console.log(response);
                var data = response[0];
                data.active = false;
                Restangular.one('work_packages/' + param + '/assignments/' + user_id).customPUT(data);
              });

              $scope.users[i].selected = false;
            }
          }
        }

        $scope.add = function(){
          // TODO
        }
        
        // var data = {
        //   workPackageNumber: $scope.package.workPackageNumber,
        //   userId: obj.id,
        //   active: true
        // }

        // Restangular.one('work_packages/' + $scope.package.workPackageNumber + '/assignments').customPOST(data).then(function(response){
        //   GrowlResponse(response)
        // }, function(response){
        //   GrowlResponse(response)
        // });
  
}]);


/*
    ASSIGN MANAGER CONTROLLER
    */
    cascadiaControllers.controller('ProjectManagementController', ['$scope', '$location', 'GrowlResponse', 'Restangular',
      function($scope, $location, GrowlResponse, Restangular) {

        $scope.quantity = 20;
        $scope.checked = {};

        var loadManaged = function() {
          Restangular.all('user/projects/managed').getList().then(function(response){
            $scope.projects = response;
          });
        }

        var loadAll = function() {
          Restangular.all('projects').getList().then(function(response){
            $scope.projects = response;
          });
        }

        var str = localStorage.getItem('permissions');
        (str.indexOf('ProjectManager') != -1) ? loadManaged() : loadAll();

        var getManager = function(project) {
          Restangular.one('projects/' + project.projectNumber + '/assignments/manager').get().then(function(response) {
            return (response.length);
          })
        }

        $scope.hasProjectManager = function(project) {
          if(angular.isDefined(project) && !$scope.checked[project.projectNumber]) {
            $scope.checked[project.projectNumber] = true;
            return getManager(project);
          }
        }

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

        $scope.select = function (project) {
          $location.path('/projects/' + project.projectNumber);
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
    ASSIGN TIMESHEET APPROVER CONTROLLER
    */
    cascadiaControllers.controller('ATAController', ['$scope', '$modal', '$rootScope', '$routeParams', '$location', 
      'FilterUser', 'Restangular', 'GrowlResponse',
      function($scope, $modal, $rootScope, $params, $location, FilterUser, Restangular, GrowlResponse){
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

        $scope.search = function (user) {
          return FilterUser(user, $scope.query);
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
              if($rootScope.user.id == response.id){
                  localStorage.setItem('user', JSON.stringify(response));
                  $rootScope.user = response;
              }
              $location.path('users/' + user.id);
              $.growl.notice({message:"Timesheet Approver Assigned"});
            })
          }
        }
      }
      ]);


/*
    ASSIGN SUPERVISOR CONTROLLER
    */
    cascadiaControllers.controller('ASController', ['$scope', '$modal', '$rootScope', '$routeParams', '$location',
     'FilterUser', 'Restangular', 'GrowlResponse',
     function($scope, $modal, $rootScope, $params, $location, FilterUser, Restangular, GrowlResponse){
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

      $scope.search = function (user) {
        return FilterUser(user, $scope.query);
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

          var persist = function (user) {
              Restangular.one('users', user.id).customPUT(user).then(function (response) {
                  if ($rootScope.user.id == response.id) {
                      localStorage.setItem('user', JSON.stringify(response));
                      $rootScope.user = response;
                  }
                  $location.path('users/' + user.id);
                  toastr.success("Supervisor Assigned");
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
            toastr.warning("Invalid Input");
          } else {
            base.post($scope.project).then(function (response) {
              toastr.success("Project Created");
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

        $scope.save = function () {
          if (!($scope.createWPForm.$valid)) {
            toastr.warning("Invalid Input");
          } else if (!validateWPNumber($scope.workPackage.workPackageNumber)) {
            toastr.warning("Invalid Input - Work Package Number: if a character is 0, then the characters following it must all be 0 too.");
          }else {
            workPackage = $scope.workPackage;
            workPackage.progressStatus = 'Active';
            workPackage.issueDate = new Date();
            workPackage.projectNumber = $scope.project.projectNumber;
            
            Restangular.one('work_packages').customPOST($scope.workPackage).then(function (response) {
              $location.path('manage-wp-pm');
              toastr.success("Work Package Created");
            }, function (response) {
              GrowlResponse(response);
            })
          }
        }
      }
      ]);

/*  
    Function to validate WP Number
    */
    function validateWPNumber(wpNumberStr) {
        var length = wpNumberStr.length;
        for (var i = 1; i < length; i++) {
          if ( (wpNumberStr.charAt(i-1) == 0) && (wpNumberStr.charAt(i) != 0) ) {
            break;
          }
        }
        
        if (i < length) {
          return false;
        }
        
        return true;
      }

/*
    DASHBOARD CONTROLLER
    */
    cascadiaControllers.controller('DashboardController', ['$scope', '$rootScope', 'Restangular', 'GrowlResponse',
      function($scope, $rootScope, Restangular, GrowlResponse) {

        $scope.quantity = 20;

        Restangular.all('user/timesheets/rejected').getList().then(function(response) {
          $scope.timesheets = response;
        });

        $scope.date = function($index){
          var timesheet = $scope.timesheets[$index];
          var day = (1 + (timesheet.weekNumber - 1) * 7);  
          return new Date(timesheet.year, 0, day);
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
          toastr.success("Pay Rates Saved");
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
    cascadiaControllers.controller('NavigationController', ['$scope', '$rootScope', '$location', 'Restangular', 'GrowlResponse',
      function($scope, $rootScope, $location, Restangular, GrowlResponse) {
        $rootScope.user = JSON.parse(localStorage.getItem('user'));
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
      function($scope, $base64, Restangular, $rootScope, $location, GrowlResponse, permissions) {
        $scope.login = function () {

          if (!($scope.loginForm.$valid)) {
            toastr.warning("Invalid Input");
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
                  GrowlResponse(response);
                });
                $rootScope.isAuthenticated = true;
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
    cascadiaControllers.controller('LogoutController', ['$scope', '$location', '$rootScope', 'Restangular',
      function($scope, $location, $rootScope, Restangular){
        localStorage.clear();
        $rootScope.isAuthenticated = false;
        $location.path('/login');
        toastr.success("You Have Been Logged Out");
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
   MANAGE WP CONTROLLER FOR PM
   */
   cascadiaControllers.controller('WPManagementPMController', ['$scope', '$location', 'Restangular', 'GrowlResponse',
    function($scope, $location, Restangular, GrowlResponse){
      $scope.statuses = [ 'Open', 'Closed'];
      $scope.projectChosen = false;
      $scope.quantity = 20;

      var findSelected = function() {
        if(localStorage.getItem('project-wp-creation')) {
          var projectNumber = JSON.parse(localStorage.getItem('project-wp-creation'));
          Restangular.one('projects', projectNumber).get().then(function(response){
            $scope.project = response;
            localStorage.removeItem('project-wp-creation');
          })
        }
      }

      $scope.loadWorkPackages = function(projectNumber) {
        Restangular.one('work_packages/project').getList($scope.project.projectNumber).then(function(response){
          $scope.projectChosen = true;
          $scope.workPackages = response;
        });
      }

      $scope.loadProjects = function() {
        Restangular.one('user/projects/managed').getList().then(function(response){
          $scope.projects = response;
          findSelected();
        });
      }

      $scope.create = function() {
        localStorage.setItem('project-wp-creation', JSON.stringify($scope.project.projectNumber));
        $location.path('/create-wp/' + $scope.project.projectNumber);
      }
    }
    ]);

/*
   MANAGE WP CONTROLLER FOR RE
   */
   cascadiaControllers.controller('WPManagementREController', ['$scope', 'Restangular', 'GrowlResponse',
    function($scope, Restangular, GrowlResponse){
      $scope.statuses = [ 'Open', 'Closed'];
      $scope.quantity = 20;

      Restangular.all('user/work_packages?filter=responsibleEngineer').getList().then(function(response){
        $scope.projectChosen = true;
        $scope.workPackages = response;
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
    cascadiaControllers.controller('PCPRController', ['$scope', 'Restangular', 'GrowlResponse',
      function($scope, Restangular, GrowlResponse){
        $scope.statuses = [ 'Open', 'Closed'];
        $scope.project = {};
        $scope.projectChosen = false;
        $scope.quantity = 20;

        $scope.loadProjects = function() {
          Restangular.all('projects').getList().then(function(response){
            $scope.projects = response;
          });
        }

        Restangular.one('user/projects/managed').getList().then(function(response){
          $scope.projects = response;
        });

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

        $scope.select = function (project) {
          $location.path('/projects/' + project.projectNumber);
        }

        $scope.add = function() {
          $scope.add_project = true;
        }

        $scope.save = function() {
          newproject = $scope.newproject;

          base.post(newproject).then(function(response) {
            $scope.projects.push(newproject);
            $scope.add_project = false;
            $location.path('/projects');
            toastr.success("Project Created");
          })
        }
      }
      ]);


/*
    PROJECT DETAILS CONTROLLER
    */
    cascadiaControllers.controller('ProjectDetailsController', ['$scope', '$routeParams', 'FilterUser', 'Restangular', 'calculateBudget',
      function($scope, $params, FilterUser, Restangular, calculateBudget){
        var param = $params.id;

        $scope.project = {}
        $scope.assignedUsers = [];
        $scope.quantity = 20;
        $scope.pLevelBudget = [];

        Restangular.one('projects', param).get().then(function(response){
          $scope.project = response;
          loadUsers();
          loadManager();
          loadBudget();
        });

        var loadBudget = function() {
          Restangular.one('reports/budget/' + $scope.project.projectNumber).get().then(function(response){
            $scope.report = response;
            var initialEstimate = $scope.report.InitialEstimate;
            var remainingEstimate = $scope.report.RemainingEstimate;
            var currentSpending = $scope.report.CurrentSpending;
            $scope.dataArray = calculateBudget(initialEstimate, remainingEstimate, currentSpending);
          })
        }

        var loadManager = function() {
          Restangular.one('projects/' + $scope.project.projectNumber + '/assignments/manager').get().then(function(response) {
            $scope.projectManager = response[0];
          })
        }

        var loadUsers = function() {
          Restangular.all('projects/' + $scope.project.projectNumber + '/users').getList().then(function(response){
            $scope.assignedUsers = response;
          })
        }

        $scope.hasProjectManager = function() {
          if(angular.isDefined($scope.projectManager)) {
            return ($scope.projectManager.id);
          }
        }

        $scope.search = function(user) {
          return FilterUser(user, $scope.query);
        }
      }
      ]);

/*
    PROJECT SUMMARY CONTROLLER
    */
    cascadiaControllers.controller('ProjectSummaryController', ['$scope', '$routeParams', 'Restangular',
      function($scope, $params, Restangular){
        var param = $params.id;
        $scope.project = {};

        Restangular.all('reports/matrix/' + param).getList().then(function(response) {
          $scope.project = response[0];
          loadManager();
          $scope.payRates = $scope.project.payRates; 
          response.splice(0, 1);
          $scope.rows = response;
          console.log($scope.rows[0]);
        });

        var loadManager = function() {
          Restangular.one('projects/' + $scope.project.projectNumber + '/assignments/manager').get().then(function(response){
            $scope.projectManager = response[0];
          });
        }
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
          $scope.timesheet.put().then(function(response){
            $location.path('/timesheet-approval');
          });
        }

        $scope.reject = function() {
          $scope.timesheet.pending = false;
          $scope.timesheet.signed = false;
          $scope.timesheet.put().then(function(response){
            $location.path('/timesheet-approval');
          });
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


        $scope.select = function(id) {
          var str = "/timesheet-approval/";
          var path = str.concat(id);
          $location.path(path);
        }
      }
      ]);

/*
    TIMESHEET CORRECTION CONTROLLER
    */

    cascadiaControllers.controller('TimesheetCorrectionController', ['$scope', '$rootScope', '$routeParams', 'Restangular', 'GrowlResponse',
      function($scope, $rootScope, $params, Restangular, GrowlResponse) {
        var param = $params.id;

        $scope.workPackageNumbers = {};
        $scope.projectNumbers = [];

        $rootScope.user = JSON.parse(localStorage.getItem('user'));

        Restangular.one('timesheets', param).get().then(function(response) {
          $scope.timesheet = response;
          $scope.timesheet.signed = false;
        })

        Restangular.one('user/projects').getList().then(function(response){
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

          $scope.save = function() {
            $scope.timesheet.put();
          }

          $scope.submit = function() {

          }
        } 
      }
      ])

/*
    TIMESHEET CONTROLLER
    */
    cascadiaControllers.controller('TimesheetController', ['$scope', '$rootScope', '$location', 'Restangular', '$routeParams', 'GrowlResponse',
      function($scope, $rootScope, $location, Restangular, $params, GrowlResponse) {
        var base = Restangular.one('user/timesheets');

        $scope.workPackageNumbers = {};
        $scope.projectNumbers = [];

        base.get({"filter":"current"}).then(function(response){
          $scope.timesheet = response;
        }, function(response){
          GrowlResponse(response);
        });

        Restangular.one('user/projects').getList().then(function(response){
          projects = response;
          for(var i = 0; i < projects.length; ++i) {
            $scope.projectNumbers.push(projects[i].projectNumber);
          }
        }, function(response){
          GrowlResponse(response);
        });

        $scope.hasProjects = function() {
          if($scope.projectNumbers) {
            return $scope.projectNumbers.length > 0;
          }
        }

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
            Restangular.one('users', $rootScope.user.id).get().then(function(response) {
              var user = response;
              user.defaultTimesheetID = $scope.timesheet.id;
              user.put();
            })
          }
          $scope.timesheet.put();
          toastr.success("TimeSheet Saved");
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
    cascadiaControllers.controller('UserProfileController', ['$scope', '$modal', 'GrowlResponse', '$rootScope', '$routeParams', 'Restangular',
      function($scope, $modal, GrowlResponse, $rootScope, $params, Restangular) {
        $rootScope.user = JSON.parse(localStorage.getItem('user'));

        var loadSupervisor = function(){
          Restangular.one('users', $rootScope.user.supervisorUserID).get().then(function(response){
            $scope.supervisor = response;
          });
        }

        var loadTimesheetApprover = function() {
          Restangular.one('users', $rootScope.user.timesheetApproverUserID).get().then(function(response){
            $scope.timesheetApprover = response;
          });
        }

        if($rootScope.user && $rootScope.user.supervisorUserID){
          loadSupervisor();
          loadTimesheetApprover();
        }

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

        $scope.open = function () {

          var modalInstance = $modal.open({
            templateUrl: 'myModalContent2.html',
            controller: ModalInstanceCtrl2
          });

          modalInstance.result.then(function (np) {
            Restangular.one('users', $rootScope.user.id).get().then(function(response) {
              var user = response;
              
              user.password = np;
              console.log(np);

              user.put().then(function(response) {
                $.growl.notice({message:"Password saved."});
              }, function(response) {
                GrowlResponse(response);
              });
            });
          });
        }
      }
      ]);

    var ModalInstanceCtrl2 = function ($scope, $modalInstance) {

      $scope.ok = function (np) {
        $modalInstance.close(np);
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
    };

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
            loadTimesheetApprover();
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
    cascadiaControllers.controller('WPDetailsController', ['$scope', 'Restangular', '$routeParams', 'GrowlResponse', 'calculateBudget', 'FilterUser',
      function($scope, Restangular, $params, GrowlResponse, calculateBudget, FilterUser){
        $scope.param = $params.id;
        $scope.quantity = 20;

        var base = Restangular.one('work_packages/' + $scope.param);

        base.get().then(function(response){
          $scope.package = response;
          loadProject();
          loadBudget();
        });

        base.getList('assignments').then(function(response){
          $scope.assignedUsers = response;
        });

        base.getList('status_reports').then(function(response) {
          $scope.reports = response;
        })

        var loadProject = function() {
          Restangular.one('projects/' + $scope.package.projectNumber).get().then(function(response){
            $scope.project = response;
          });
        };

        var loadBudget = function() {
          Restangular.one('reports/work_package/budget/' + $scope.package.workPackageNumber).get().then(function(response){
            $scope.report = response;
            var initialEstimate = $scope.report.InitialEstimate;
            var remainingEstimate = $scope.report.RemainingEstimate;
            var currentSpending = $scope.report.CurrentSpending;
            $scope.dataArray = calculateBudget(initialEstimate, remainingEstimate, currentSpending);
          })
        }
        
        $scope.search = function(user) {
          return FilterUser(user, $scope.query);
        }

      }
      ]);



/*
    WORK PACKAGE STATUS REPORT CONTROLLER
    */
    cascadiaControllers.controller('WPStatusReportController', ['$scope', '$location', '$routeParams', 'Restangular', 'GrowlResponse',
      function($scope, $location, $params, Restangular, GrowlResponse){
        var param = $params.id;
        $scope.package = {};
        $scope.problemEncountered;
        $scope.problemAnticipated;
        $scope.workPlanned;
        $scope.comment;
        $scope.workAccomplished;


        Restangular.one('work_packages', param).get().then(function(response){
          $scope.package = response;
        })

        $scope.listOfEffort = [
        {
          pLevel: "P1",
          personDays: 0
        },
        {
          pLevel: "P2",
          personDays: 0
        },
        {
          pLevel: "P3",
          personDays: 0
        },
        {
          pLevel: "P4",
          personDays: 0
        },
        {
          pLevel: "P5",
          personDays: 0
        },
        {
          pLevel: "DS",
          personDays: 0
        },
        {
          pLevel: "SS",
          personDays: 0
        }
        ]

        $scope.submit = function() {
          var data = {
            reportDate: $scope.reportDate,
            workPackageNumber: $scope.package.workPackageNumber,
            comment: $scope.comment,
            workAccomplished: $scope.workAccomplished,
            problemEncountered: $scope.problemEncountered,
            problemAnticipated: $scope.problemAnticipated,
            workPlanned: $scope.workPlanned,
            estimatedWorkRemainingInPD: $scope.listOfEffort
          }

          Restangular.one('work_packages/' + $scope.package.workPackageNumber + '/status_reports').customPOST(data).then(function(response){
            $location.path('wp-details/' + $scope.package.workPackageNumber);
            GrowlResponse(response)
          }, function(response){
            GrowlResponse(response)
          })
        }
      }
      ]);

/*
    CREATE USER CONTROLLER
    */
    cascadiaControllers.controller('CreateUserController', ['$scope', '$rootScope', 'Restangular', 'GrowlResponse', '$location',
      function($scope, $rootScope, Restangular, GrowlResponse, $location){

        $scope.items = [ 'P1', 'P2', 'P3', 'P4', 'P5' ];
        $scope.statuses = [ 'Active', 'Inactive' ];

        $scope.cUser = {}

        $scope.save = function () {

          if(!($scope.createUserForm.$valid)) {
            toastr.warning("Invalid or Incomplete Input");
          } else {
            user = $scope.cUser;

            Restangular.one('users').customPOST(user).then(function (response) {
              $location.path('/users');
              toastr.success("User Created");
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
      cascadiaControllers.controller('UsersManagementController', ['$scope', '$location', '$rootScope', 'Restangular',
       'FilterUser', 'GrowlResponse',
       function($scope, $location, $rootScope, Restangular, FilterUser, GrowlResponse) {
        var base = Restangular.all('users');

        $scope.items = [ 'P1', 'P2', 'P3', 'P4', 'P5', 'DS', 'SS' ];
        $scope.statuses = [ 'Active', 'Inactive' ];
        $scope.quantity = 20;
        $scope.cUser = {};

        var loadUser = function() {
          Restangular.one('user').get().then(function(response) {
              $scope.cUser = response;
          })
        }

        base.getList().then(function(response) {
          loadUser();
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

        $scope.search = function(user) {
          return FilterUser(user, $scope.query);
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
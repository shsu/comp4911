var cascadiaServices = angular.module('cascadiaServices', []);

/** Service to be used for filtering users */
cascadiaServices.factory('FilterUser', [
  function() {
    return function(user, query) {
      if(!query) {
        return true;
      }

      query = query.toLowerCase();

      if (user.id.toString().indexOf(query) != -1 || user.firstName.toLowerCase().indexOf(query) != -1 ||
                user.lastName.toLowerCase().indexOf(query) != -1 || user.pLevel == query.toUpperCase()) {
        return true;
      }
      
      return false;
    }
}]);

cascadiaServices.factory('userService', ['$q', '$rootScope', '$location', 'Restangular',
 function($q, $rootScope, $location, Restangular) {
  return {
    user: null,

    isAuthenticatedResolve: function() {
      var thisObject = this;
      var defer = $q.defer();
      if(thisObject.user) {
        defer.resolve(thisObject.user);
      } else if (localStorage.getItem('token')) {
          Restangular.one('user').head({},
            {'Authorization': 'Basic ' + localStorage.getItem('token')}).then(function(response){
              $rootScope.isAuthenticated = true;
              Restangular.setDefaultHeaders({
                'Authorization':'Basic ' + localStorage.getItem('token')
              });
              thisObject.user = localStorage.getItem('user');
              defer.resolve(thisObject.user);
          }, function(response) {
            localStorage.clear();
            $rootScope.isAuthenticated = false;
            defer.reject(response);
          })
        } else {
          $rootScope.isAuthenticated = false;
          localStorage.clear();
          defer.reject();
        }
        return defer.promise;
      }
    }
  }
]);

cascadiaServices.factory('calculateBudget',
  function() {
    return function(a, b, c) {
        if(a && b && c){
          var data = [
            {level: 'p1', ie: a.P1, re: b.P1, cs: c.P1},
            {level: 'p2', ie: a.P2, re: b.P2, cs: c.P2},
            {level: 'p3', ie: a.P3, re: b.P3, cs: c.P3},
            {level: 'p4', ie: a.P4, re: b.P4, cs: c.P4},
            {level: 'p5', ie: a.P5, re: b.P5, cs: c.P5},
            {level: 'ds', ie: a.DS, re: b.DS, cs: c.DS},
            {level: 'ss', ie: a.SS, re: b.SS, cs: c.SS}
          ];
        }

        return data;
      }
    
  })

cascadiaServices.factory('permissions',
  function($rootScope) {
    var permissionList;
    return {
      setPermissions: function(permissions) {
        permissionList = permissions;
        $rootScope.$broadcast('permissionsChanged')
      },
      hasPermission: function(permission) {
        permission = permission.trim();
        return _.some(permissionList, function(item) {
          if(_.isString(item.name))
            return item.name.trim() === permission
        });
      }
    };
  });

cascadiaServices.factory('GrowlResponse', [
  function() {
    return function(response) {
      if(response.status >= 200 && response.status <=304) {
          toastr.success("Operation Successful");
      }
      else if(response.status == 400){
          if(response.data.errors){
              var errorMessage = "";
              for(var i = 0; i < response.data.errors.length; ++i) {
                  errorMessage += ('\n' + response.data.errors[i].error);
              }
              toastr.error(errorMessage);
          } else {
              toastr.error("Bad Request");
          }

      }
      else if(response.status == 401){
          toastr.error("Unauthorized. Please re-login.");
      }
      else if(response.status == 403){
          toastr.error("Forbidden");
      }
      else if(response.status == 404){
          toastr.error("Not Found");
      } else {
          toastr.error("Status Code "+ response.status);
      }
    }
}]);



cascadiaServices.factory('AssignUser', function() {
 return function (scope) {
    scope.user = JSON.parse(localStorage.getItem('user'));
  }
});


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
              console.log(response);
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
        $.growl.notice({message:"Operation Successful"});
      }
      else if(response.status == 400){
          if(response.data.errors){
              var errorMessage = "";
              for(var i = 0; i < response.data.errors.length; ++i) {
                  errorMessage += ('\n' + response.data.errors[i].error);
              }
              $.growl.error({message: errorMessage})
          } else {
              $.growl.error({message: "Bad Request"})
          }

      }
      else if(response.status == 401){
          $.growl.error({message: "Unauthorized. Check your username and password."})
      }
      else if(response.status == 403){
        $.growl.error({message: "Forbidden"})
      }
      else if(response.status == 404){
        $.growl.error({message: "Not found. You may be pointing to the wrong endpoint."})
      } else {
          $.growl.error({message: "Status Code "+ response.status})
      }
    }
}]);



cascadiaServices.factory('AssignUser', function() {
 return function (scope) {
    scope.user = JSON.parse(localStorage.getItem('user'));
  }
});


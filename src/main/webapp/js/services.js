var cascadiaServices = angular.module('cascadiaServices', []);

// Ensures we have a collection of users at our disposal at all times
cascadiaServices.factory('InitUserMap', ['Restangular',
 function(Restangular) {
    return function(scope){
        if(scope.isAuthenticated && (!scope.userMap || scope.userMap.length == 0)) {
            scope.userMap = {}

            Restangular.all('users').getList().then(function(response){
                scope.userList = response;
                for(var i = 0; i < scope.userList.length; ++i) {
                  scope.userMap[scope.userList[i].id] = scope.userList[i];
                }
            });
        }
    }
}]);


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


cascadiaServices.factory('AuthenticateUser', ['$location', 'Restangular',
 function($location, Restangular) {
  return function(scope) {
    scope.isToken = (localStorage.getItem('token')) ? true : false;
    if(scope.isToken){
      Restangular.one('user').head({}, 
        {'Authorization': 'Basic ' + localStorage.getItem('token')}).then(function(response){
        console.log("Token is Valid");
        scope.isAuthenticated = true;
        Restangular.setDefaultHeaders({
          'Authorization': 'Basic ' + localStorage.getItem('token')
        });
        console.log(Restangular.defaultHeaders);
      }, function(response){
        console.log("Token not Valid");
        localStorage.clear();
        scope.isAuthenticated = false;
        $location.path('/login');
      })
    }
    else {
        scope.isAuthenticated = false;
        $location.path('/login')
    } 
  }
}]);

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


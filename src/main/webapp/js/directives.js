var cascadiaDirectives = angular.module('cascadiaDirectives', []);

	cascadiaDirectives.directive('hasPermission', function (permissions) {
	  return {
	    link: function(scope, element, attrs) {
	      if(!_.isString(attrs.hasPermission))
	        throw "hasPermission value must be a string";
	 
	 			var array = attrs.hasPermission.split(',');
	      // var notPermissionFlag = value[0] === '!';
	      // if(notPermissionFlag) {
	      //   value = value.slice(1).trim();
	      // }
	 
	      function toggleVisibilityBasedOnPermission() {
	      	var hasPermission = false;

	      	for(var i = 0; i < array.length; ++i){
	      		if(permissions.hasPermission(array[i].trim())) {	
	      			hasPermission = true;
	      		}
	      	}
	 
	        if(hasPermission)
	          element.show();
	        else
	          element.hide();
	      }
	      toggleVisibilityBasedOnPermission();
	      scope.$on('permissionsChanged', toggleVisibilityBasedOnPermission);
	    }
	  }
	});

	cascadiaDirectives.directive('totenth', function() {
	    return {
	        restrict: 'A',
	        require: 'ngModel',
	        link: function(scope, element, attr, ngModel) {

	            function fromUser(text) {
	                return (text || '') * 10
	            }

	            function toUser(text) {
	                return (text || '') / 10;
	            }
	            ngModel.$parsers.push(fromUser);
	            ngModel.$formatters.push(toUser);
	        }
	    };
	});
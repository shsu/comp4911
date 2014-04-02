var cascadiaFilters = angular.module('cascadiaFilters', []);

cascadiaFilters.filter('uniqueUser', function(){
	return function(users, currentUser) {
		if(angular.isDefined(users) && angular.isDefined(currentUser)) {
			var returnList = [];

			angular.forEach(users, function(user) {
				if(!angular.equals(currentUser.id, user.id)) {
					returnList.push(user)
				}
			});
			return returnList;
		} else {
			return users;
		}
	}
});
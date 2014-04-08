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


cascadiaFilters.filter('uniqueUsers', function(){
	return function(users, omittedUsers) {
		if(angular.isDefined(users) && angular.isDefined(omittedUsers)) {
			var returnList = users;

			angular.forEach(omittedUsers, function(oUser) {
				angular.forEach(users, function(user) {
					if(angular.equals(oUser.id, user.id)) {
						var index = returnList.indexOf(user);
						returnList.splice(index, 1);
					}
				});
			})

			return returnList;
		}
	}
});

cascadiaFilters.filter('alterStatus', function() {
	return function(status) {
		if(status) {
			return "Open"
		} else {
			return "Closed"
		}
	}
})
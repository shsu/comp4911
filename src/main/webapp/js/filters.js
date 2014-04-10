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

cascadiaFilters.filter('alterStatus', function() {
	return function(status) {
		if(status) {
			return "Open"
		} else {
			return "Closed"
		}
	}
});

cascadiaFilters.filter('fromTenth', function() {
	return function(amount) {
		return amount / 10;
	}
});
'use strict';

sydneyWildlifeApp.factory('MemberService',['Restangular', 'DateService', function (Restangular, DateService) {
	
	/**
	 * Local method to massage the date if needed.
	 */
	function massageDate(aDate){
		return DateService.convertJSONToMMDDYYYYDate(aDate);
	};

    return {
        save: function(member) {
        	var baseMembers = Restangular.all("members");
        	
        	var aPromise = baseMembers.post(member);
        	return aPromise;
        },
        list: function() {
        	var baseMembers = Restangular.all("members");
        	
        	var aPromise = baseMembers.getList();
        	return aPromise;
        },
        memberDetail: function(id) {
        	return Restangular.one("members", id);
        },
        deleteMember: function(id) {
        	var member = Restangular.one("members", id);
        	
        	var aPromise = member.remove();
        	return aPromise;
        }
    };
}]);

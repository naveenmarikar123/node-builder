
var app = angular.module("store",[]);

app.controller("DataController" , function($http , $scope){
	$http.get('http://localhost:8080/tt/loadallprojects').success(function(data){
		$scope.projects = data
	});
});


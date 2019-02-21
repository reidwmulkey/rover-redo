(function(){
    angular.module('controller')

    .controller('appController', function($mdSidenav, $http, $q,$resource, $mdDialog){

      let vm = this;
      vm.minimumAverageStayRating = 2.5;
      vm.sitters = null;
      
      // resources
      var sitterSearchResource = $resource('http://localhost:8001/V1/Sitters');
      var sitterDetailResource = $resource('http://localhost:8001/V1/Sitters/:sitterId', {sitterId: '@sitterId'});

      // implementation 

      vm.searchToggle = function(){
        console.log("toggle!");
        $mdSidenav("left").toggle();
      }

      vm.search = function(){
      	console.log('searching with ' + vm.minimumAverageStayRating);
      	sitterSearchResource.query({minimumAverageStayRating:vm. minimumAverageStayRating})
      	.$promise
      	.then(function(sitters){
      		console.log(sitters);
      		vm.sitters = sitters;
      	})
      	.catch(console.error);
      	$mdSidenav("left").toggle();	
      }
      
      vm.selectSitter = function(sitter){
    	  sitterDetailResource.get({sitterId: sitter.id})
    	  .$promise
    	  .then(function(sitterDetails){
				$mdDialog.show({
					controller: ['$scope', '$mdDialog', sitterDetailController],
					templateUrl: './assets/sitter-detail.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					locals: {
						sitter: sitterDetails
					}
				});
    		  
				function sitterDetailController($scope, $mdDialog){
					$scope.sitter = sitterDetails;
					$scope.close = function(){
						$mdDialog.hide();
					}
				}
    	  })
    	  .catch(console.error);
      }
      
    });
})();
(function(){

	angular.module('config')
	.config(setTheme)
	.factory(config);

	setTheme.$inject = ['$mdThemingProvider'];

	function setTheme($mdThemingProvider) {
	    $mdThemingProvider.theme('default')
	    .primaryPalette('teal', {
	      'default': '600', 
	      'hue-1': '100', 
	      'hue-2': '600', 
	      'hue-3': 'A100' 
	    })
	    .accentPalette('amber', {
	      'default': '800' 
	    });
  	}

  	function config(){
  		var config = {
  			"serverUrl" : "http://localhost:8001"
  		};
  		return config;
  	}

})();
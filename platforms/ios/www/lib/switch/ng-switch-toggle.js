/**
 * @rdevera timactive
 */
angular.module('ngSwitchToggle', [])
.directive('ngSwitchToggleSimple', 
function () {
  'use strict';
  return {
    restrict: 'A',
    replace:true,
    transclude:false,
    require: '?ngModel',
   template:'<label class="ngst-toggle {{theme}} header-toggle ">' +
            '<input type="checkbox" ng-model="ngModel" />'+
                '<p><span>{{textOn}}</span><span>{{textOff}}</span></p>'+
                '<a class="slide-button"></a>'+
                '</label>',
   	scope: {
    		ngModel: '=',
    		textOff:'@textOff',
    		textOn:'@textOn',
    		theme:'@theme'
    	}
      };
})
.directive('ngSwitchToggleGroup', 
function ($compile) {
  'use strict';
  return {
    restrict: 'E',
    replace:true,
    require: '?ngModel',
    template:'<div class="ngst-switch  {{theme}} {{color}}" >' +
             '</div>',
   	scope: {
    		groupname:'@groupName',	ngModel:'=',
    		choices:'=',theme:'@theme',color:'@theme'
    	},
    	
    link: function (scope, element, attrs) {
    		scope.$watch('choices', function(choices) {
    			var i=1;
    			var percentWidth = Math.round((10000/choices.length))/100;
    			element.html("");
    			/*build radio and item*/
    			angular.forEach(choices, function(choice){
    				var inputradio = angular.element($compile('<input id="name'+i+'" name="'+scope.groupname+'" type="radio" value="'+choice.value+'" ng-model="ngModel" ></input>')(scope));
    				var label = angular.element('<label for="name'+i+'" onclick="">'+choice.label+'</label>');
    				label.css("width",percentWidth+"%")
    				element.append(inputradio);
    				element.append(label);
    				i++;
    			});
    			var slider = angular.element('<span class="slide-button"></span>');
    			slider.css("width",percentWidth+"%");
    			element.append(slider);
    			element.find("input:radio[name='"+scope.groupname+"']").change(function() {
				});
    		});
    		scope.$watch('ngModel', function(model) {
    			/* model is change? 
    			 * change position slider
    			 * change selected label*/
    			element.find("label").removeClass("selected");
    			/* calc ratio */
    			var percentWidth =Math.round((10000/scope.choices.length))/100;
    			/* what index?*/
    			var radioButtons = $("input:radio[name='"+scope.groupname+"']");
    			var radioButtonToSelect = radioButtons.filter(function(index) {
    				return($(this).val() == model);
				});
				
				var selectedIndex = radioButtons.index(radioButtonToSelect);
				if(selectedIndex==-1)
				{
					/*default label selected first and position slider 0*/
					selectedIndex=0;
					element.find("label").first().addClass("selected");
				}
				else
				{
					var radioid=radioButtonToSelect.attr("id");
	    			element.find("label[for='"+radioid+"']").addClass("selected");;
	    		}
				/*Calc left position*/
				element.find('.slide-button').css("left",percentWidth*selectedIndex+"%");
    		});
        }
      };
});



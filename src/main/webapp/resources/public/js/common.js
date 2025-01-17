angular.module('common', ['ngMessages'])
    .controller('BaseFormCtrl', ['$scope', '$http', function ($scope, $http) {
        var fieldWithFocus;

        $scope.vm = {
            submitted: false,
            errorMessages: []
        };

        $scope.focus = function (fieldName) {
            fieldWithFocus = fieldName;
        };

        $scope.blur = function (fieldName) {
            fieldWithFocus = undefined;
        };

        $scope.isMessagesVisible = function (fieldName) {
            return fieldWithFocus === fieldName || $scope.vm.submitted;
        };

        $scope.preparePostData = function (username, password) {
        	 var username = username != undefined ? username : '';
             var password = password != undefined ? password : '';
             var email = username != undefined ? username : '';

             return 'username=' + username + '&password=' + password + '&email=' + email;
        }

        $scope.login = function (username, password) {        	
        	var postData = $scope.preparePostData(username,password);
            $http({
                method: 'POST',
                url: '/authenticate',
                data: postData,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Login-Ajax-call": 'true'
                }
            })
            .then(function(response) {                
                if (response.data == 'ok') {
                    window.location.replace('/resources/dashboard.html');
                }
                else {
                    $scope.vm.errorMessages = [];
                    $scope.vm.errorMessages.push({description: 'Authentication failed'});
                }
            });
        }


    }])
    .directive('checkPasswordsMatch', function () {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ngModel) {
                ngModel.$validators.checkPasswordsMatch = function (modelValue, viewValue) {
                    if (scope.vm && scope.vm.password && viewValue) {
                        return scope.vm.password === viewValue;
                    }
                    return true;
                };
            }
        };
    });
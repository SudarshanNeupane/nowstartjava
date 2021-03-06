angular
		.module("categoryApp", [ 'ngRoute' ])
		.controller(
				'submitCategory',
				[
						'$scope',
						'$http',
						'$location',
						'$route',
						function($scope, $http, $location, $route) {
							$scope.title = "Add ";
							$scope.submitClick = function(id) {
								var dataObj = {
									name : $scope.name
								};
								if (id) {
									var updateSubmit = $http.post(
											"/updateCategory/" + id, dataObj);
									updateSubmit
											.success(function(data, status,
													headers, config) {
												// getCat();
												$scope.message = "Data has been changed! ";
											});
									updateSubmit
											.error(function(data, status,
													headers, config) {
												$scope.message = "Error while Updating data.";
											});
								} else {
									var result = $http
											.post("/category", dataObj);
									result
											.success(function(data, status,
													headers, config) {
												// getCat();
												
												$scope.message = "Data Inserted successfully";
											});
									result
											.error(function(data, status,
													headers, config) {
												$scope.message = "Error while inserting the data.";
											});
								}
								$scope.name = '';
								$scope.id = '';
							}
							
							$scope.allCategories = function() {
								var categories = $http({
									method : 'GET',
									url : '/category'
								});
								categories.success(function(data, status, header,
										config) {
									$scope.displayCategory = data;
								});

							}
							
							// Delete Category
							$scope.deleteCategory = function(category) {
								var deletedata = $http({
									method : 'DELETE',
									url : '/deleteCategory/' + category.id
								})
								deletedata.success(function(data, status,
										header, config) {
									$location.path('/subjects');
									var index = $scope.displayCategory
											.indexOf(category);
									$scope.displayCategory.splice(index, 1);

								})
								deletedata.error(function(data, status,
										headers, config) {
									alert("error while data delete")
								});
							}
							$scope.editCategory = function(name, id) {
								$scope.name = name;
								$scope.editId = id;
								$scope.title = " Update ";

							}
						} ]);
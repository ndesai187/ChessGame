(function() {

	var url = "api/customer";

	var CustomerApp = angular.module("CustomerApp", []);

	var CustomerSvc = function($http, $q, $httpParamSerializerJQLike) {
		this.findCustomer = function(custId) {
			var defer = $q.defer();
			$http.get(url + "/" + custId)
				.then(function(result) {
					defer.resolve(result.data);
				}, function(error) {
					defer.reject(error);
				});
			return (defer.promise);
		}

		this.addCustomer = function(customer) {
			var defer = $q.defer();
			$http({
				url: url,
				method: "POST",
				headers: {
					"Content-Type": "application/x-www-form-urlencoded",
				},
				data: $httpParamSerializerJQLike(customer)
			}).then(function() {
				defer.resolve()
			}).catch(function(error) {
				defer.reject(error);
			});
			return (defer.promise);
		}
	}

	var CustomerCtrl = function(CustomerSvc) {
		var customerCtrl = this;
		customerCtrl.customer = {
			custId: null
		};
		customerCtrl.found = false;
		customerCtrl.notFound = false;

		customerCtrl.find = function() {
			customerCtrl.found = false;
			customerCtrl.notFound = false;
			if (!customerCtrl.customer.custId) {
				customerCtrl.customer = {
					custId: null
				};
				return;
			}
			CustomerSvc.findCustomer(customerCtrl.customer.custId)
				.then(function(customer) {
					customerCtrl.customer = customer;
					customerCtrl.found = true;
				}).catch(function() {
					customerCtrl.notFound = true;
					var custId = customerCtrl.customer.custId;
					customerCtrl.customer = {
						custId: custId
					}
				}) ;
		}

		customerCtrl.add = function() {
			customerCtrl.found = false;
			customerCtrl.notFound = false;
			CustomerSvc.addCustomer(customerCtrl.customer)
				.then(function() {
					alert("Added customer " + customerCtrl.customer.custId);
					customerCtrl.found = true;
				}).catch(function(error) {
					customerCtrl.notfound = true;
					alert("Error: " + JSON.stringify(error));
				});
		}
	};

	CustomerApp.service("CustomerSvc", ["$http", "$q", "$httpParamSerializerJQLike", CustomerSvc]);

	CustomerApp.controller("CustomerCtrl", ["CustomerSvc", CustomerCtrl]);

})();


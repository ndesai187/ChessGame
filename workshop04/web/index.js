(function() {

	var url = "ws://localhost:8080/workshop04/chess-event";

	var ChessApp = angular.module("ChessApp", ["ui.router"]);

	var ChessGame = function(gameId, pieceColor) {
		this.gameId = gameId;
		this.pieceColor = pieceColor;
	}
	ChessGame.prototype.move = function(src, dst, piece) {
		return ({
			gameId: this.gameId,
			pieceColor: piece || this.pieceColor,
			action: "move",
			payload: src + "-" + dst
		});
	}

	var ChessConfig = function($stateProvider, $urlRouterProvider) {

		$stateProvider.state("start", {
			url: "/start",
			templateUrl: "views/start.html",
			controller: "StartCtrl as startCtrl"

		}).state("game", {
			url: "/game/:name/:gid/:pieceColor",
			templateUrl: "views/game.html",
			controller: "GameCtrl as gameCtrl"
		});

		$urlRouterProvider.otherwise("/start");
	}

	var StartCtrl = function($state, ChessSvc) {
		var startCtrl = this;
		startCtrl.gameId = "";
		startCtrl.name = "";

		startCtrl.joinGame = function() {
			$state.go("game", {
				name: startCtrl.name,
				gid: startCtrl.gameId, 
				pieceColor: "black"
			});
		};
		startCtrl.createGame = function() {
			ChessSvc.createGame({
				name: startCtrl.name,
				gameId: startCtrl.gameId
			}).then(function(gameInfo) {
				$state.go("game", {
					name: startCtrl.name,
					gid: gameInfo.gameId, 
					pieceColor: "white"
				});
			});
		}
	};

	var GameCtrl = function($stateParams, ChessSvc) {
		var gameCtrl = this;
		gameCtrl.gameId = $stateParams.gid;
		gameCtrl.pieceColor = $stateParams.pieceColor;
		gameCtrl.name = $stateParams.name;

		var pieceColor = $stateParams.pieceColor === "white"? "wP": "bP";
		var chessGame = new ChessGame($stateParams.gid, pieceColor);

		var onDrop = function(src, dst, piece) {
			conn.send(chessGame.move(src, dst, piece));
		}

		var chessboard = ChessBoard("chessboard", {
			draggable: true,
			position: 'start',
			orientation: $stateParams.pieceColor,
			pieceTheme: "bower_components/chessboardjs/img/chesspieces/wikipedia/{piece}.png",
			onDrop: onDrop
		});

		var conn = ChessSvc.joinGame(gameCtrl.gameId);
		conn.onopen = function() {
			console.info("Connected");
		}
		conn.onmessage = function(data) {
			if (data.gameId !== gameCtrl.gameId)
				return;

			if (pieceColor === data.pieceColor)
				return;

			switch (data.action) {
				case "move":
					chessboard.move(data.payload);
					break;

				default:
			}
		}
	};

	var ChessCtrl = function() {
		var chessCtrl = this;
	};

	var ChessSvc = function($rootScope, $http, $httpParamSerializerJQLike, $q) {

		this.createGame = function(formData) {
			var defer = $q.defer();
			$http({
				url: "game",
				method: "POST",
				headers: {
					"Content-Type": "application/x-www-form-urlencoded",
					"Accept": "application/json"
				},
				data: $httpParamSerializerJQLike(formData)
			}).then(function(result) {
				defer.resolve(result.data);
			});
			return (defer.promise);
		};

		this.joinGame = function(gameId) {
			var conn = { };
			conn.socket = new WebSocket(url + "/" + gameId);
			conn.send = function(pkt) {
				conn.socket.send(JSON.stringify(pkt));
			}
			conn.socket.onopen = function() {
				if (conn.onopen)
					$rootScope.$apply(function() {
						conn.onopen();
					});
			}
			conn.socket.onclose = function(evt) {
				if (conn.onclose)
					$rootScope.$apply(function() {
						conn.onclose(evt);
					})
			}
			conn.socket.onmessage = function(evt) {
				if (conn.onmessage)
					$rootScope.$apply(function() {
						conn.onmessage(JSON.parse(evt.data));
					})
			}
			conn.socket.onerror = function() {
				if (conn.onerror)
					$rootScope.$apply(function() {
						conn.onerror();
					})
			}
			conn.close = function() {
				conn.close = function() {
					conn.socket.close();
				}
			}
			return (conn);
		}
	};

	ChessApp.config(["$stateProvider", "$urlRouterProvider", ChessConfig]);

	ChessApp.service("ChessSvc", 
			["$rootScope", "$http", "$httpParamSerializerJQLike", "$q", ChessSvc]);

	ChessApp.controller("ChessCtrl", [ChessCtrl]);
	ChessApp.controller("StartCtrl", ["$state", "ChessSvc", StartCtrl]);
	ChessApp.controller("GameCtrl", ["$stateParams", "ChessSvc", GameCtrl]);

})();
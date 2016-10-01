
var clientSetting = {
	events: {
		useSocket: false
	}
}


var RBCOM = {
    subscribe: function(channel, callback) {
        amplify.subscribe(channel, callback);
    },
    publish: function(channel, params) {

		if (clientSetting.events.useSocket && stompClient != null) {

			//console.log('Publishing to socket:');

			var message = {
				channel: channel,
				params: params,
				cid: model.session.cid
			};
			//console.log(message);

			stompClient.send("/app/publish", {}, JSON.stringify(message));
		} else {
			amplify.publish(channel, params);
		}


    },
	socketReceiveEvents(message) {
		//console.log('socketReceiveEvents:');
		//console.log(message);
		amplify.publish(message.channel, message.params, message);
	}
};


var stompClient = null;

function setConnected(connected) {

}

function connect() {
	var socket = new SockJS('/publish');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Socket Connected: ' + frame);
		stompClient.subscribe('/socket/events', function(greeting){
			RBCOM.socketReceiveEvents(JSON.parse(greeting.body));
		});
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

connect();
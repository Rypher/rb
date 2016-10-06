var map;

var mapState = {
	lastCenterRecieved: new Date().getMilliseconds()
};

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 8,
		mapTypeId: google.maps.MapTypeId.TERRAIN
    });

	var input = document.getElementById('pac-input');
	var searchBox = new google.maps.places.SearchBox(input);
	//map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});

	var markers = [];
	// [START region_getplaces]
	// Listen for the event fired when the user selects a prediction and retrieve
	// more details for that place.
	searchBox.addListener('places_changed', function() {


		console.log("hi.");

		var places = searchBox.getPlaces();

		if (places.length == 0) {
			return;
		}

		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		markers = [];

		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		places.forEach(function(place) {
			var icon = {
				url: place.icon,
				size: new google.maps.Size(71, 71),
				origin: new google.maps.Point(0, 0),
				anchor: new google.maps.Point(17, 34),
				scaledSize: new google.maps.Size(25, 25)
			};

			// Create a marker for each place.
			markers.push(new google.maps.Marker({
				map: map,
				icon: icon,
				title: place.name,
				position: place.geometry.location
			}));

			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		map.fitBounds(bounds);
	});

	map.addListener('zoom_changed', function() {
		RBCOM.publish('zoom_changed', map.getZoom());
	});

	map.addListener('center_changed', function(event) {

		//console.log('local center_changed:');



			mapState.lastCenterRecieved = new Date().getTime();

			var center = {
				lat: map.getCenter().lat(),
				lng: map.getCenter().lng()
			};
			console.log(center);

			RBCOM.publish('center_changed', center);


	});


}
RBCOM.subscribe('zoom_changed', function(params, message) {

	console.log('zoom_changed:');
	console.log(message);

	if (message.cid != model.session.cid) {
		map.setZoom(params);
	}

});

RBCOM.subscribe('center_changed', function(params, message) {

	//console.log('center_changed:');
	//console.log(message);

	var curTimeMillis = new Date().getTime();

	//if (curTimeMillis - mapState.lastCenterRecieved > 500) {
		if (message.cid != model.session.cid) {
			map.panTo(params);
		}
	//}
});



var markerAdded = function(marker) {

    console.log('here');

    var myLatlng = new google.maps.LatLng(marker.y, marker.x);
    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        draggable: true,
        title: "Drag me!"
    });

	marker.addListener('click', markerMoved);
    marker.setMap(map);
};

var markerMoved = function(event) {
	console.log(event);
};

RBCOM.subscribe('markerAdded', markerAdded);
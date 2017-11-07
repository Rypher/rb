var Index = React.createClass({
	handleChange: function(key) {

		var marker = {
			x: 131.044922,
			y: -25.363882
		};
		RBCOM.publish('markerAdded', marker);
	},
    render: function() {
        return <div>
        	<input type="text" ref="input" onChange={this.handleChange} />;
		</div>
    }
});
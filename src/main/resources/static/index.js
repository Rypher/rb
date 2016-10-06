'use strict';

var Index = React.createClass({
	displayName: 'Index',

	handleChange: function handleChange(key) {

		var marker = {
			x: 131.044922,
			y: -25.363882
		};
		RBCOM.publish('markerAdded', marker);
	},
	render: function render() {
		return React.createElement('div', null);
		//return <ReactBootstrap.Input type="text" ref="input" onChange={this.handleChange} />;
	}
});
'use strict';

var Nav = React.createClass({
    displayName: 'Nav',

    getInitialState: function getInitialState() {

        console.log(this.props.model);
        return {
            data: {}

        };
    },
    handleSelect: function handleSelect(key) {
        console.log(key);
        console.log('2');

        if (key == 1) {
            var marker = {
                x: 131.044922,
                y: -25.363882
            };
            RBCOM.publish('markerAdded', marker);
        } else if (key == 2) {

            infoWindow = new google.maps.InfoWindow();

            map.addListener('click', showMaxZoom);
        }
    },
    render: function render() {

        var session = this.props.model.session;

        return React.createElement(
            ReactBootstrap.Navbar,
            { fixedTop: true },
            React.createElement(
                ReactBootstrap.NavBrand,
                null,
                React.createElement(
                    'a',
                    { href: '' },
                    'rossbailey.com'
                )
            ),
            React.createElement(
                ReactBootstrap.Nav,
                { right: true },
                React.createElement(
                    ReactBootstrap.NavItem,
                    { eventKey: 1, onSelect: this.handleSelect },
                    session.cid
                )
            )
        );
    }
});
var Nav = React.createClass({
    getInitialState: function() {

        console.log(this.props.model)
        return {
            data: {},

        }
    },
    handleSelect: function(key) {
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



        }

    },
    render: function() {

        var session = this.props.model.session;

        return <ReactBootstrap.Navbar fixedTop>
            <ReactBootstrap.NavBrand><a href="">rossbailey</a></ReactBootstrap.NavBrand>
            <ReactBootstrap.Nav right={true}>
                <ReactBootstrap.NavItem eventKey={1} onSelect={ this.handleSelect} >{session.cid}</ReactBootstrap.NavItem>
            </ReactBootstrap.Nav>
        </ReactBootstrap.Navbar>;
    }
});
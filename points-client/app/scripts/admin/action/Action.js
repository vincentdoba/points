var React = require("react");

var Action = React.createClass({
  getInitialState: function() {
    return {actions: []};
  },
  render: function() {
    return (
      <div>
        <h3>Gérer les actions</h3>
      </div>
    );
  }
});

module.exports = Action;

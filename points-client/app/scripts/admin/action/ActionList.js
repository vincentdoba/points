"use strict";

var React = require("react");

var ActionList = React.createClass({
  render: function() {
    var createActionDisplay = function(action) {
      return <li key={action.id}>{action.name}</li>;
    };
    return <ul>{this.props.actions.map(createActionDisplay)}</ul>;
  }
});

module.exports = ActionList;

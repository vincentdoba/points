"use strict";

var React = require("react");

var ActionList = React.createClass({
  render: function() {
    var createActionDisplay = function(action) {
      return <div className="row" key={action.id}>
       <div className="col-md-4">{action.name}</div>
       <div className="col-md-4">{action.description}</div>
       <div className="col-md-4">{action.points}</div>
      </div>;
    };
    return <div>
      <div className="row">
        <div className="col-md-4">nom</div>
        <div className="col-md-4">description</div>
        <div className="col-md-4">points</div>
      </div>
      <div>{this.props.actions.map(createActionDisplay)}</div>
    </div>;
  }
});

module.exports = ActionList;

"use strict";

var React = require("react");
var ActionList = require("./ActionList");

var Action = React.createClass({
  getInitialState: function() {
    return {actions: [
      {
        "id": "1210955b-27b1-40c2-9d33-81601fbcfc31",
        "name": "first rewarding action",
        "category": {
          "id": "6220b616-990c-4961-9876-05cfded83552",
          "name": "category 1",
          "description": "none",
          "createdAt": "2015-12-20T10:21:23.203+01:00",
          "updatedAt": "2015-12-21T10:21:23.210+01:00"
        },
        "description": "--",
        "points": 1,
        "createdAt": "2015-12-22T09:21:23.210+01:00",
        "updatedAt": "2015-12-22T10:21:23.210+01:00"
      },
      {
        "id": "1210955b-27b1-40c2-9d33-81601fbcfc32",
        "name": "second rewarding action",
        "category": {
          "id": "6220b616-990c-4961-9876-05cfded83552",
          "name": "category 1",
          "description": "none",
          "createdAt": "2015-12-22T10:21:23.203+01:00",
          "updatedAt": "2015-12-22T10:21:23.210+01:00"
        },
        "description": "--",
        "points": 1,
        "createdAt": "2015-12-22T10:21:23.210+01:00",
        "updatedAt": "2015-12-22T10:21:23.210+01:00"
      },
      {
        "id": "1210955b-27b1-40c2-9d33-81601fbcfc33",
        "name": "third rewarding action",
        "category": {
          "id": "6220b616-990c-4961-9876-05cfded83553",
          "name": "category 2",
          "description": "none",
          "createdAt": "2015-12-22T10:21:23.203+01:00",
          "updatedAt": "2015-12-22T10:21:23.210+01:00"
        },
        "description": "--",
        "points": 1,
        "createdAt": "2015-12-22T10:21:23.210+01:00",
        "updatedAt": "2015-12-22T10:21:23.210+01:00"
      }
    ]};
  },
  render: function() {
    return (
      <div>
        <h3>Gérer les actions</h3>
        <ActionList actions={this.state.actions}/>
      </div>
    );
  }
});

module.exports = Action;

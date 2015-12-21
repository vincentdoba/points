
var React = window.React = require("react");
var ReactDOM = require("react-dom");
var ReactRouter = require("react-router");

var IndexRoute = ReactRouter.IndexRoute;
var Link = ReactRouter.Link;
var Route = ReactRouter.Route;
var Router = ReactRouter.Router;

// var Timer = require("./ui/Timer");
var ActionAdmin = require("./admin/action/Action");

var mountNode = document.getElementById("app");

// var TodoList = React.createClass({
//   render: function() {
//     var createItem = function(itemText) {
//       return <li>{itemText}</li>;
//     };
//     return <ul>{this.props.items.map(createItem)}</ul>;
//   }
// });
var Index = React.createClass({
  render: function() {
    return (
      <h3>Bienvenue sur l&apos;application de gestion des points de Lateral-Thoughts</h3>
    );
  }
});

var App = React.createClass({
  getInitialState: function() {
    return {};
  },
  // onChange: function(e) {
  //   this.setState({text: e.target.value});
  // },
  // handleSubmit: function(e) {
  //   e.preventDefault();
  //   var nextItems = this.state.items.concat([this.state.text]);
  //   var nextText = "";
  //   this.setState({items: nextItems, text: nextText});
  // },
  render: function() {
    return (
      <div className="container">
          <div className="header">
              <ul className="nav nav-pills pull-right">
                  <li><Link to="/" activeClassName="active">Accueil</Link></li>
                  <li><Link to="/admin" activeClassName="active">Administration</Link></li>
              </ul>
              <h3 className="text-muted">Gestion des Points Business</h3>
          </div>

          {this.props.children}

          <div className="footer">
              <p>Lateral-Thoughts</p>
          </div>

      </div>

      // <div>
      //   <h3>TODO</h3>
      //   <TodoList items={this.state.items} />
      //   <form onSubmit={this.handleSubmit}>
      //     <input onChange={this.onChange} value={this.state.text} />
      //     <button>{"Add #" + (this.state.items.length + 1)}</button>
      //   </form>
      //   <Timer />
      // </div>
    );
  }
});

var routes = (
  <Router>
    <Route path="/" component={App}>
      <IndexRoute component={Index}/>
      <Route path="admin" component={ActionAdmin}/>
    </Route>
  </Router>
);

ReactDOM.render(routes, mountNode);

// ReactDOM.render(<TodoApp />, mountNode);

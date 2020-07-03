import React from 'react';
import './App.less';
import {BrowserRouter as Router, Route} from 'react-router-dom'
import Login from './views/Login'


const App = () => {
  return (
    <Router>
      <Route exact path="/" component={Login}/>
      {/*<Route exact path="/browser" component={Browser}/>*/}
    </Router>
  );
};

export default App;

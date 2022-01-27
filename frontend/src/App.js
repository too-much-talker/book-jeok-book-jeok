import { BrowserRouter, Switch, Route } from "react-router-dom";
import Home from "./views/main/Home";
import React from "react";
import Header from "./views/main/Header";
import Mypage from "./views/user/myPage";

function App(){
  return(
    <BrowserRouter>
      <Header/>
      <Switch>
        <Route exact path='/' component={Home}/>
        <Route path='/mypage' component={Mypage}/>
      </Switch>
    </BrowserRouter>
  );
}


export default App;

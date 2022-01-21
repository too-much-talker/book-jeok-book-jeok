import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Login from "./views/user/login/LoginPresenter";
import Signup from "./views/user/signup/SignupPresenter";
import MyPage from "./views/user/myPage/index";
import Home from "./views/main/Home";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/signup">
          <Signup />
        </Route>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/mypage">
          <MyPage />
        </Route>
        <Route path="/">
          <Home />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;

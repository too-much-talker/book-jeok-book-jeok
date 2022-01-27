import UserInfoPresenter from "./userInfo/UserInfoPresenter";
import UserBooklogPresenter from "./userBooklog/UserBooklogPresenter";
import UserBookclubPresenter from "./userBookclub/UserBookclubPresenter";
import UserChallengePresenter from "./userChallenge/UserChallengePresenter";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Sidebar from "../../main/Sidebar";
import styled from "styled-components";

const Center = styled.div`
  height: 90vh;
  display: flex;
  flex-direction: row;
  `

function Mypage(){
    return(
      <BrowserRouter>
        <Center>
          <Sidebar/>
          <Switch>
            <Route exact path="/mypage" component={UserInfoPresenter}/>
            <Route path="/mybooklog" component={UserBooklogPresenter} />
            <Route path="/mybookclub" component={UserBookclubPresenter} />
            <Route path="/mychallenge" component={UserChallengePresenter} />
          </Switch>
        </Center>
      </BrowserRouter>
    );
  }

  export default Mypage;

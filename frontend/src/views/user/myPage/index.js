import UserInfoContainer from "./userInfo/UserInfoContainer";
import UserBooklogContainer from "./userBooklog/UserBooklogContainer";
import UserBookclubPresenter from "./userBookclub/UserBookclubPresenter";
import UserChallengePresenter from "./userChallenge/UserChallengePresenter";
import { Route, Routes } from "react-router-dom";
import Sidebar from "../../main/Sidebar";
import styled from "styled-components";
import Login from "../login/LoginContainer";

const Center = styled.div`
  height: 90vh;
  display: flex;
  flex-direction: row;
`;

function Mypage() {
  return (
    <Center>
      <Sidebar />
      <Routes>
        <Route index path="/" element={<UserInfoContainer />} />
        <Route path="/mybooklog" exact element={<UserBooklogContainer />} />
        <Route path="/mybookclub" exact element={<UserBookclubPresenter />} />
        <Route path="/mychallenge" exact element={<UserChallengePresenter />} />
      </Routes>
    </Center>
  );
}

export default Mypage;

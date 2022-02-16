import UserInfoContainer from "./userInfo/UserInfoContainer";
import UserBooklogContainer from "./userBooklog/UserBooklogContainer";
import UserReadingGroupContainer from "./userReadingGroup/UserReadingGroupContainer";
import UserHeartBooklogContainer from "./userHeartBooklog/UserHeartBooklogContainer";
import { Route, Routes } from "react-router-dom";
import Sidebar from "../../main/Sidebar";
import styled from "styled-components";
import Login from "../login/LoginContainer";
import React from "react";
import GroupMemberReview from "./userReadingGroup/groupMemberReview/GroupMemberReview";

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
        <Route
          path="/myheartbooklog"
          exact
          element={<UserHeartBooklogContainer />}
        />
        <Route
          path="/mybookclub"
          exact
          element={<UserReadingGroupContainer />}
        />
        <Route
          path="/mybookclub/memberreview/:groupSeq"
          exact
          element={<GroupMemberReview />}
        />
      </Routes>
    </Center>
  );
}

export default Mypage;

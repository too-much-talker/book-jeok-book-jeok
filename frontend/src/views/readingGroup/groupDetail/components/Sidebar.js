import React from "react";
import styled from "styled-components";

const Li = styled.li`
  margin: 30px;
  &:hover {
    cursor: pointer;
  }
  font-size: 1.3rem;
  font-weight: bold;
`;

const Button = styled.button`
  margin: 30px;
  padding: 10px;
  border-radius: 20px;
  background: white;
  &:hover {
    cursor: pointer;
  }
`;

const Side = styled.div`
  //   background: blue;
  width: 20%;
  float: left;
  height: 100vh;
  background: rgb(199, 199, 199);
  padding-top: 50px;
`;

function Sidebar({ onClickBoard, onClickInfo, onClickMeeting }) {
  return (
    <>
      <Side>
        {" "}
        <ul>
          <Li onClick={onClickInfo}>독서 모임 정보</Li>
          <Li onClick={onClickBoard}>독서 모임 게시판</Li>
        </ul>
        <Button onClick={onClickMeeting}>미팅 룸 들어가기</Button>
      </Side>
    </>
  );
}

export default Sidebar;

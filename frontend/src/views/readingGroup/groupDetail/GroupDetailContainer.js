import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useNavigate, useParams } from "react-router";
import { useSelector } from "react-redux";
import Sidebar from "./components/Sidebar";
import GroupInfo from "./components/GroupInfo";

const Wrapper = styled.div`
  margin: 0 auto;
  width: 100%;
  margin-top: 100px;
`;

const ButtonMenu = styled.div`
  width: 100%;
  text-align: right;
  //   border-bottom: solid 1px;
`;

const Delete = styled.button`
  padding: 10px;
  border-radius: 5px;
  background: black;
  color: white;
  &:hover {
    cursor: pointer;
  }
`;

function GrouDetailContainer() {
  const params = useParams();
  const [groupInfo, setGroupInfo] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const userNickname = useSelector(
    (state) => state.authReducer.memberInfo.nickname
  );

  function onClickMeeting() {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    const date = now.getDate();
    alert("go Meeting");
    // 반복문 groupInfo의 모임 날짜 배열 돌려서 날짜가 현재와 일치하는지 검사
    // 일치하는 게 하나라도 있으면,  navigate(`/readinggroup/meeting/${params.meetingSeq}`); 하고 리턴
    // 불일치하면 alert("모임 일자가 아닙니다.")
  }

  const [tab, setTab] = useState("info");

  useEffect(() => {
    setGroupInfo({
      members: ["형다은", "김수민", "김경석", "김은선", "이재경"],
      content: "독서 모임 내용",
      title: "독서 모임 제목",
      startDate: "2022.02.01",
      endDate: "2022.02.28",
      weeks: ["월요일", "화요일", "수요일"],
      leaderNickname: "bbjjjjj",
      isStart: false,
    });
    setIsLoading(false);
  }, [userNickname]);

  const onDelete = () => {
    // 모임 삭제 비동기통신
  };

  const printBoard = () => {
    setTab("board");
  };

  const printInfo = () => {
    setTab("info");
  };

  return (
    <>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <Wrapper>
          <Sidebar
            onClickMeeting={onClickMeeting}
            onClickBoard={printBoard}
            onClickInfo={printInfo}
          />
          {userNickname === groupInfo.leaderNickname && (
            <ButtonMenu>
              <Delete onClick={onDelete}>독서 모임 삭제</Delete>
            </ButtonMenu>
          )}
          {tab === "info" ? (
            <GroupInfo groupInfo={groupInfo} />
          ) : (
            <div>게시판</div>
          )}
        </Wrapper>
      )}
    </>
  );
}

export default GrouDetailContainer;

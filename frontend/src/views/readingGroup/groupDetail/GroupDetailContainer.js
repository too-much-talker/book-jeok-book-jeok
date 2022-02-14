import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useNavigate, useParams } from "react-router";
import { useSelector } from "react-redux";
import Sidebar from "./components/Sidebar";
import GroupInfo from "./components/GroupInfo";
import BoardMainContainer from "./components/Board/BoardMainContainer";
import {
  getGroupDetail,
  checkGoMeeting,
  deleteGroup,
} from "../../../common/api/readingGroup";
import ArticleDetailContainer from "../Board/ArticleDetailContainer";
import ModifyArticleContainer from "../Board/ModifyArticleContainer";

const Wrapper = styled.div`
  margin: 0 auto;
  width: 100%;
  margin-top: 20px;
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
  const [readingGroupSeq, setReadingGroupSeq] = useState(params.meetingSeq);
  const navigate = useNavigate();
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const userNickname = useSelector(
    (state) => state.authReducer.memberInfo.nickname
  );

  function onClickMeeting() {
    // const now = new Date();
    // const year = now.getFullYear();
    // const month = now.getMonth();
    // const date = now.getDate();
    // alert("go Meeting");
    // 반복문 groupInfo의 모임 날짜 배열 돌려서 날짜가 현재와 일치하는지 검사
    // 일치하는 게 하나라도 있으면,  navigate(`/readinggroup/meeting/${params.meetingSeq}`); 하고 리턴
    // 불일치하면 alert("모임 일자가 아닙니다.")

    checkGoMeeting(
      params.meetingSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.status === 200) {
          navigate(`/readinggroup/meeting/${params.meetingSeq}`);
        } else {
          alert("독서 모임 일자가 아닙니다.");
        }
      },
      (error) => {
        console.log("오류가 발생했습니다.");
        navigate("/");
      }
    );
  }

  const [tab, setTab] = useState("info");

  useEffect(() => {
    getGroupDetail(
      params.meetingSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.status === 200) {
          setGroupInfo(response.data.data.readingGroupDetail);
          setIsLoading(false);
        } else {
          alert("오류가 발생했습니다.");
          navigate("/");
        }
      },
      (error) => {
        console.log("오류가 발생했습니다.");
        navigate("/");
      }
    );
  }, [userNickname]);

  const onDelete = () => {
    // 모임 삭제 비동기통신
    deleteGroup(
      params.meetingSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.status === 200) {
          alert("삭제 되었습니다.");
          navigate(`/mypage/mybookclub`);
        } else {
          alert("독서 모임 삭제가 불가합니다.");
          console.log(response);
        }
      },
      (error) => {
        console.log("오류가 발생했습니다.");
        navigate("/");
      }
    );
  };

  const printBoard = () => {
    setTab("board");
  };

  const printInfo = () => {
    setTab("info");
  };

  const printDetail = () => {
    setTab("detail");
  };
  const printModify = () => {
    setTab("modify");
  };
  function handleSetSelected(param) {
    setSelectedSeq(param);
  }
  const [selectedSeq, setSelectedSeq] = useState();
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
          {userNickname === groupInfo.writer && (
            <ButtonMenu>
              <Delete onClick={onDelete}>독서 모임 삭제</Delete>
            </ButtonMenu>
          )}
          {tab === "info" && <GroupInfo groupInfo={groupInfo} />}
          {tab === "board" && (
            <BoardMainContainer
              handleSetSelected={handleSetSelected}
              printDetail={printDetail}
              tab={tab}
              readingGroupSeq={readingGroupSeq}
            />
          )}
          {tab === "detail" && (
            <ArticleDetailContainer
              handleSetSelected={handleSetSelected}
              printModify={printModify}
              articleSeq={selectedSeq}
            />
          )}
          {tab === "modify" && (
            <ModifyArticleContainer articleSeq={selectedSeq} />
          )}
        </Wrapper>
      )}
    </>
  );
}

export default GrouDetailContainer;

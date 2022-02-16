import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import PostingDetailPresenter from "./PostingDetailPresenter";
const url = "https://i6a305.p.ssafy.io:8443";

function PostingDetailContainer() {
  const location = useLocation();
  const readingGroupSeq = location.state.logSeq;
  // console.log(readingGroupSeq);
  const userInfo = useSelector((state) => state.authReducer).memberInfo;
  const [group, setGroup] = useState({});
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  let isParticipated;
  const [minLevel, setMinLevel] = useState(1);
  const [writer, setWriter] = useState();
  const [detailState, setDetailState] = useState("detail");

  function changeModifyState() {
    setDetailState("modify");
  }

  function changeDetailState() {
    setDetailState("detail");
  }

  const getPage = async () => {
    const response = await axios.get(
      url + `/api/v1/reading-groups/${readingGroupSeq}`,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    console.log(response.data.data);
    setMinLevel(response.data.data.readingGroupDetail.minLevel);
    setGroup(response.data.data.readingGroupDetail);
    setWriter(response.data.data.readingGroupDetail.writer);
  };

  function subscriptionGroup() {
    if (minLevel <= userInfo.level) {
      axios
        .post(
          url + `/api/v1/reading-groups/${readingGroupSeq}/members`,
          {},
          {
            headers: {
              Authorization: `Bearer ` + jwtToken,
            },
          }
        )
        .then(function (response) {
          alert(response.data.data.msg);
          getPage();
        })
        .catch(function (error) {
          console.log(error);
        });
    } else {
      alert(`최소 레벨 ${minLevel}이상 신청 가능합니다.`);
    }
  }

  function cancelSubcription() {
    axios
      .delete(
        url +
          `/api/v1/reading-groups/${readingGroupSeq}/members/${userInfo.seq}`,
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        alert(response.data.data.msg);
        getPage();
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goDelete() {
    axios
      .delete(url + `/api/v1/reading-groups/${readingGroupSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response);
        alert(response.data.data.msg);
        document.location.href = "/postinglist";
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  useEffect(() => {
    getPage();
  }, []);

  if (group.participants) {
    isParticipated = group.participants.includes(userInfo.nickname);
  }

  return (
    <PostingDetailPresenter
      jwtToken={jwtToken}
      url={url}
      userInfo={userInfo}
      writer={writer}
      isParticipated={isParticipated}
      group={group}
      subscriptionGroup={subscriptionGroup}
      cancelSubcription={cancelSubcription}
      goDelete={goDelete}
      detailState={detailState}
      changeModifyState={changeModifyState}
      changeDetailState={changeDetailState}
      readingGroupSeq={readingGroupSeq}
      getPage={getPage}
    />
  );
}
export default PostingDetailContainer;

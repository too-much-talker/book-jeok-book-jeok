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
  const userInfo = useSelector((state) => state.authReducer).memberInfo;
  const [group, setGroup] = useState({});
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  let isParticipated;
  const getPage = async () => {
    console.log('asdasdasd');
    const response = await axios.get(url + `/api/v1/reading-groups/${readingGroupSeq}`, {
      headers: {
        Authorization: `Bearer ` + jwtToken,
      },
    });
    setGroup(response.data.data.readingGroupDetail);
  };
  const subscriptionGroup = async () => {
    const response = await axios.post(
      url + `/api/v1/reading-groups/${readingGroupSeq}/members`,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    console.log(response);
  };
  const cancelSubcription = async () => {
    const response = await axios.delete(
      url + `/api/v1/reading-groups/${readingGroupSeq}/members/${userInfo.seq}`,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    console.log(response);
  };
  useEffect(() => {
    getPage();
  }, []);
  if (group.participants) {
    isParticipated = group.participants.includes(userInfo.nickname);
  }
  return (
    <PostingDetailPresenter 
      isParticipated={isParticipated}
      group={group}
      subscriptionGroup={subscriptionGroup}
      cancelSubcription={cancelSubcription}
    />
  );
}
export default PostingDetailContainer;

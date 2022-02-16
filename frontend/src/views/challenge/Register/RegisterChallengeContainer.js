import RegisterChallengePresenter from "./RegisterChallengePresenter";
import React, { useState, useEffect, useReducer } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

function RegisterChallengeContainer() {
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const url = "https://i6a305.p.ssafy.io:8443";

  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [deadline, setDeadLine] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [limitMember, setLimitMember] = useState();
  const [reward, setReward] = useState();

  function handleTitle(event) {
    if (event.target.value.length > 11) {
      alert("최대 11자까지 가능합니다.");
    } else {
      setTitle(event.target.value);
    }
  }
  function handleContent(event) {
    setContent(event.target.value);
  }
  function handleDeadline(event) {
    setDeadLine(event.target.value);
  }
  function handleStartDate(event) {
    if (event.target.value < deadline) {
      alert("모집 기한은 챌린지 시작일보다 최소 하루 빨라야 합니다");
    } else {
      setStartDate(event.target.value);
    }
  }
  function handleEndDate(event) {
    if (startDate + 6 > event.target.value) {
      alert("시작일+6 날짜가 종료일보다 빠르거나 같아야 합니다.");
    } else {
      setEndDate(event.target.value);
    }
  }
  function handleLimitMember(event) {
    if (event.target.value < 3 || event.target.value > 10) {
      alert("인원은 최소 3명 최대 10명이어야 합니다.");
    } else {
      setLimitMember(event.target.value);
    }
  }
  function handleReward(event) {
    if (event.target.value > 1000) {
      alert("reward는 1000이 최대입니다.");
    } else {
      setReward(event.target.value);
    }
  }
  function goRegister() {
    axios
      .post(
        url + `/api/v1/challenges`,
        {
          title: title,
          content: content,
          deadline: deadline,
          startDate: startDate,
          endDate: endDate,
          maxMember: limitMember,
          reward: reward,
        },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        if (response.data.status == 201) {
          alert(response.data.data.msg);
          document.location.href = `/challenge`;
        }
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <RegisterChallengePresenter
      handleTitle={handleTitle}
      handleContent={handleContent}
      handleDeadline={handleDeadline}
      handleStartDate={handleStartDate}
      handleEndDate={handleEndDate}
      handleLimitMember={handleLimitMember}
      handleReward={handleReward}
      goRegister={goRegister}
    ></RegisterChallengePresenter>
  );
}
export default RegisterChallengeContainer;

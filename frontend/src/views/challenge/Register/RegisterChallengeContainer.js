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
    setTitle(event.target.value);
  }
  function handleContent(event) {
    setContent(event.target.value);
  }
  function handleDeadline(event) {
    setDeadLine(event.target.value);
  }
  function handleStartDate(event) {
    setStartDate(event.target.value);
  }
  function handleEndDate(event) {
    setEndDate(event.target.value);
  }
  function handleLimitMember(event) {
    setLimitMember(event.target.value);
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

import DatailChallengePresenter from "./DatailChallengePresenter";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";
function DatailChallengeContainer() {
  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";
  const user = useSelector((state) => state.authReducer);
  const userInfo = user.memberInfo;
  console.log(user.memberInfo);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  let isParticipated;
  const [challengeSeq, setChallengeSeq] = useState(useParam.challengeSeq);
  const [participant, setParticipant] = useState(useParam.cnt);
  const [details, setDetails] = useState([
    {
      title: "",
      content: "",
      deadline: "",
      views: "",
      startDate: "",
      endDate: "",
      maxMember: "",
      reward: "",
    },
    {
      title: "",
      content: "",
      deadline: "",
      views: "",
      startDate: "",
      endDate: "",
      maxMember: "",
      reward: "",
    },
  ]);

  const setDate = new Date(details.deadline);
  const setDateYear = setDate.getFullYear();
  const setDateMonth = setDate.getMonth() + 1;
  const setDateDay = setDate.getDate();
  const now = new Date();
  const distance = setDate.getTime() - now.getTime();
  const dday = Math.ceil(distance / (1000 * 60 * 60 * 24));

  const [isClicked, setIsClicked] = useState(false);
  const [writer, setWriter] = useState();

  function handleClick(param) {
    setIsClicked(param);
  }
  useEffect(() => {
    getDetail();
  }, []);

  useEffect(() => {
    getDetail();
  }, [isClicked]);

  if (details.participantNicknames) {
    isParticipated = details.participantNicknames.includes(userInfo.nickname);
  }
  console.log(isParticipated);
  function goRegister() {
    axios
      .post(
        url + `/api/v1/challenges/${challengeSeq}/join`,
        {},
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        if (response.data.status === 201) {
          console.log(response);
          alert(response.data.data.msg);
          getDetail();
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goDelete() {
    axios
      .delete(url + `/api/v1/challenges/${challengeSeq}/join`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response);
        alert(response.data.data.msg);
        getDetail();
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function getDetail() {
    axios
      .get(url + `/api/v1/challenges/${challengeSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response.data.data.challengeInfo);
        setDetails(response.data.data.challengeInfo);
        setWriter(response.data.data.challengeInfo.writerSeq);
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <DatailChallengePresenter
      isParticipated={isParticipated}
      goRegister={goRegister}
      goDelete={goDelete}
      dday={dday}
      participant={participant}
      details={details}
      writer={writer}
      user={user}
    ></DatailChallengePresenter>
  );
}
export default DatailChallengeContainer;

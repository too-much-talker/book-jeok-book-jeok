import DatailChallengePresenter from "./DatailChallengePresenter";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
function DatailChallengeContainer() {
  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

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

  useEffect(() => {
    getDetail();
  }, []);

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
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <DatailChallengePresenter
      dday={dday}
      participant={participant}
      details={details}
    ></DatailChallengePresenter>
  );
}
export default DatailChallengeContainer;

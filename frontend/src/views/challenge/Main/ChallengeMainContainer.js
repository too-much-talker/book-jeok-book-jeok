import ChallengeMainPresenter from "./ChallengeMainPresenter";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";

function ChallengeMainContainer() {
  const url = "https://i6a305.p.ssafy.io:8443";
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const [page, setPage] = useState(1);
  const [order, serOrder] = useState(false);
  const [challengeSeq, setChallengeSeq] = useState();
  const [deadline, setDeadline] = useState();
  const [participantCount, setParticipantCount] = useState();
  const [title, setTitle] = useState();
  const [views, setViews] = useState();
  const [totalCnt, setTotalCnt] = useState();
  const [challenges, setChallenges] = useState([
    {
      challengeSeq: "",
      title: "",
      deadline: "",
      participantCount: "",
      views: "",
    },
    {
      challengeSeq: "",
      title: "",
      deadline: "",
      participantCount: "",
      views: "",
    },
  ]);
  function onRegister() {
    document.location.href = `/challenge/register`;
  }

  function handlePageChange(event) {
    setPage(event);
  }

  useEffect(() => {
    getList();
  }, []);

  useEffect(() => {
    getList();
  }, [page]);

  function getList() {
    axios
      .get(
        url + `/api/v1/challenges`,
        { params: { page: page, size: 8, all: order } },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        setChallenges(response.data.data.challengeMiniDtos);
        setTotalCnt(response.data.data.totalCnt);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  return (
    <ChallengeMainPresenter
      totalCnt={totalCnt}
      handlePageChange={handlePageChange}
      challenges={challenges}
      onRegister={onRegister}
    ></ChallengeMainPresenter>
  );
}
export default ChallengeMainContainer;

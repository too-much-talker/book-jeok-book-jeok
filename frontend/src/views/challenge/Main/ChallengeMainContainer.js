import ChallengeMainPresenter from "./ChallengeMainPresenter";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";

function ChallengeMainContainer() {
  const url = "https://i6a305.p.ssafy.io:8443";
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const [page, setPage] = useState(1);
  const [order, setOrder] = useState(false);
  const [totalCnt, setTotalCnt] = useState();
  const [maxMember, setMaxMember] = useState();
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

  function orderFalse() {
    setOrder(false);
    console.log("false");
  }
  function orderTrue() {
    setOrder(true);
    console.log("true");
  }
  useEffect(() => {
    getList();
  }, []);

  useEffect(() => {
    getList();
  }, [page]);
  useEffect(() => {
    getList();
  }, [order]);

  function getList() {
    axios
      .get(
        url + `/api/v1/challenges`,
        { params: { page: page, size: 12, all: order } },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        console.log(response.data);
        setChallenges(response.data.data.challengeMiniDtos);
        setTotalCnt(response.data.data.totalCnt);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  return (
    <ChallengeMainPresenter
      page={page}
      totalCnt={totalCnt}
      handlePageChange={handlePageChange}
      challenges={challenges}
      onRegister={onRegister}
      orderFalse={orderFalse}
      orderTrue={orderTrue}
      order={order}
    ></ChallengeMainPresenter>
  );
}
export default ChallengeMainContainer;

import MeetingPresenter from "./MeetingPresenter";
import "./index.css";
import React, { useEffect, useState } from "react";
import { checkGoMeeting } from "../../../common/api/readingGroup";
import { useParams, useNavigate } from "react-router";

function MeetingContainer() {
  const params = useParams();
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    checkGoMeeting(
      params.meetingSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.data.status === 200) {
          setIsLoading(false);
        } else {
          alert("독서 모임 참여가 불가능 합니다.");
        }
      },
      (error) => {
        console.log("오류가 발생했습니다.");
        navigate("/");
      }
    );
  }, []);
  return <>{isLoading ? <div>Loading...</div> : <MeetingPresenter />}</>;
}

export default MeetingContainer;

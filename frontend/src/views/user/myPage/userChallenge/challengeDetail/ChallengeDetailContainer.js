import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getChallengeDetail } from "../../../../../common/api/challenge";
import ChallengeDetailPresenter from "./ChallengeDetailPresenter";
import axios from "axios";
import { URL } from "../../../../../common/config/index.js";
import { useNavigate } from "react-router";

function ChallengeDetailContainer() {
  const params = useParams();
  const navigate = useNavigate();

  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const [challengeInfo, setChallengeInfo] = useState({});
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [todayAuth, setTodayAuth] = useState();
  // const [challengeState, setChallengeState] = useState(params.status);
  // console.log(challengeState);

  useEffect(() => {
    getChallengeDetail(
      params.challengeSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.data.status === 200) {
          console.log(response.data.data.myChallengeDetail);
          setChallengeInfo(response.data.data.myChallengeDetail);
          setTodayAuth(response.data.data.myChallengeDetail.todayAuth);
        } else {
          alert("오류가 발생했습니다.");
          navigate("/mypage/challenge");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }, []);

  const [postfiles, setPostfiles] = useState({
    file: [],
    previewURL: "",
  });

  const [files, setFiles] = useState();
  const [button, setButton] = useState(false);
  const uploadFile = (e) => {
    e.stopPropagation();
    let reader = new FileReader();
    let file = e.target.files[0];
    if (e.target.files.length > 5) {
      alert("사진은 최대 5장까지 업로드 가능합니다.");
      setButton(true);
    } else {
      setButton(false);
      const filesInArr = Array.from(e.target.files);

      reader.onloadend = () => {
        setPostfiles({
          file: filesInArr,
          previewURL: reader.result,
        });
      };

      if (file) {
        reader.readAsDataURL(file);
      }
    }
  };

  function handleTitle(event) {
    setTitle(event.target.value);
  }
  function handleContent(event) {
    setContent(event.target.value);
  }

  const onSubmit = (e) => {
    console.log("ee");
    let formData = new FormData();
    postfiles.file.map((eachfile) => formData.append("files", eachfile));

    let reqChallengeAuth = {
      title: title,
      content: content,
    };
    formData.append(
      "reqChallengeAuth",
      new Blob([JSON.stringify(reqChallengeAuth)], {
        type: "application/json",
      })
    );

    const config = {
      headers: {
        Accept: "application/json",
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${jwtToken}`,
      },
    };

    axios
      .post(
        URL + `/api/v1/challengeauths/${params.challengeSeq}`,
        formData,
        config
      )
      .then(function (response) {
        console.log(response);
        alert("인증 완료");
        navigate("/mypage/challenge");
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <>
      {challengeInfo !== {} && (
        <ChallengeDetailPresenter
          button={button}
          postfiles={postfiles}
          uploadFile={uploadFile}
          challengeInfo={challengeInfo}
          todayAuth={todayAuth}
          onSubmit={onSubmit}
          handleTitle={handleTitle}
          handleContent={handleContent}
          // onImageChange={onImageChange}
        />
      )}
    </>
  );
}

export default ChallengeDetailContainer;

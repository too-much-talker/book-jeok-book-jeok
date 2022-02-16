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
          setChallengeInfo(response.data.data.challengeInfo);
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

  // const onImageChange = async (e) => {
  //   e.stopPropagation();
  //   let reader = new FileReader();
  //   let file = e.target.files[0];
  //   if (e.target.files.length > 5) {
  //     alert("사진은 최대 5장까지 업로드 가능합니다.");
  //     // setButton(true);
  //   } else {
  //     // setButton(false);
  //     const filesInArr = Array.from(e.target.files);

  //     reader.onloadend = () => {
  //       setPostfiles({
  //         file: filesInArr,
  //         previewURL: reader.result,
  //       });
  //     };

  //     if (file) {
  //       reader.readAsDataURL(file);
  //     }
  //   }
  // };

  // function onSubmit() {
  //   alert("인증합니다.");

  //   let formData = new FormData();
  //   postfiles.file.map((eachfile) => formData.append("files", eachfile));

  //   axios
  //     .post(URL + `/api/v1/reading-groups/boards`, formData, {
  //       headers: {
  //         Accept: "application/json",
  //         "Content-Type": "multipart/form-data",
  //         Authorization: `Bearer ${jwtToken}`,
  //       },
  //     })
  //     .then(function (response) {
  //       console.log(response.data);
  //       alert("인증이 완료 되었습니다..");
  //       navigate("/mypage/challenge");
  //     })
  //     .catch(function (error) {
  //       console.log(error);
  //     });
  // }

  const [files, setFiles] = useState("");

  const onImageChange = (e) => {
    const file = e.target.files;
    console.log(file);
    setFiles(file);
  };

  const onSubmit = (e) => {
    const formdata = new FormData();
    formdata.append("uploadImage", files[0]);

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
        formdata,
        config
      )
      .then(() => {
        alert("인증 완료");
        navigate("/mypage/challenge");
      });
  };

  return (
    <>
      <ChallengeDetailPresenter
        challengeInfo={challengeInfo}
        onSubmit={onSubmit}
        onImageChange={onImageChange}
      />
    </>
  );
}

export default ChallengeDetailContainer;

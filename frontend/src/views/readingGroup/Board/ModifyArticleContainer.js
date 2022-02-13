import ModifyArticlePresenter from "./ModifyArticlePresenter";
import axios from "axios";
import React, { useState, useEffect, useReducer } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
function ModifyArticleContainer({ articleSeq }) {
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const url = "https://i6a305.p.ssafy.io:8443";
  let useParam = useParams();
  const [readingGroupSeq, setReadingGroupSeq] = useState(useParam.boardSeq);
  //const [articleSeq, setArticleSeq] = useState(useParam.articleSeq);
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [files, setFiles] = useState();

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    centerMode: true,
  };

  const [postfiles, setPostfiles] = useState({
    file: [],
    previewURL: "",
  });

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

  useEffect(() => {
    getArticle();
    console.log("컴포넌트가 화면에 나타남");
    return () => {
      console.log("컴포넌트가 화면에서 사라짐");
    };
  }, []);

  function getArticle() {
    axios
      .get(url + `/api/v1/reading-groups/boards/${articleSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response.data.data);
        setTitle(response.data.data.readingGroupArticle.title);
        setContent(response.data.data.readingGroupArticle.content);
        setFiles(response.data.data.imagePaths);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function handleTitle(event) {
    setTitle(event.target.value);
  }
  function handleContent(event) {
    setContent(event.target.value);
  }
  function handleFiles() {}

  function onCreate() {
    let formData = new FormData();
    postfiles.file.map((eachfile) => formData.append("files", eachfile));
    let reqReadingGroupBoard = {
      readingGroupSeq: readingGroupSeq,
      title: title,
      content: content,
    };
    formData.append(
      "reqReadingGroupBoard",
      new Blob([JSON.stringify(reqReadingGroupBoard)], {
        type: "application/json",
      })
    );
    axios
      .put(url + `/api/v1/reading-groups/boards/${articleSeq}`, formData, {
        headers: {
          Accept: "application/json",
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${jwtToken}`,
        },
      })
      .then(function (response) {
        console.log(response.data);
        alert("수정되었습니다.");
        document.location.href = `/readinggroup/detail/${useParam.meetingSeq}`;
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  return (
    <ModifyArticlePresenter
      uploadFile={uploadFile}
      button={button}
      postfiles={postfiles}
      settings={settings}
      handleTitle={handleTitle}
      handleContent={handleContent}
      handleFiles={handleFiles}
      title={title}
      content={content}
      files={files}
      onCreate={onCreate}
    ></ModifyArticlePresenter>
  );
}
export default ModifyArticleContainer;

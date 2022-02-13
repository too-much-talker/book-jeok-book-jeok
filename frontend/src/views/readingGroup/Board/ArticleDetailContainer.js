import axios from "axios";
import React, { useState, useEffect, useReducer } from "react";
import { useCallback, useRef } from "react";
import { useSelector } from "react-redux";
import ArticleDetailPresenter from "./ArticleDetailPresenter";
import { useParams } from "react-router-dom";
import Slider from "react-slick";

function ArticleDetailContainer() {
  const user = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const url = "https://i6a305.p.ssafy.io:8443";
  let useParam = useParams(); ////게시글 번호 useParam으로 받아올거임
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [nickname, setNickname] = useState();
  const [createdDate, setCreatedDate] = useState();
  const [views, setViews] = useState();
  const [comments, setComments] = useState();
  const [file, setFile] = useState();
  const [disabled, setDisabled] = useState(true);
  const [commentOpen, setCommentOpen] = useState(false);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    centerMode: true,
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
      .get(url + `/api/v1/reading-groups/boards/${useParam.articleSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response.data.data);
        setTitle(response.data.data.readingGroupArticle.title);
        setContent(response.data.data.readingGroupArticle.content);
        setNickname(response.data.data.readingGroupArticle.nickname);
        setCreatedDate(response.data.data.readingGroupArticle.createDate);
        setViews(response.data.data.readingGroupArticle.views);
        setComments(response.data.data.readingGroupArticle.comments);
        // setFile("https://i6a305.p.ssafy.io:8443/"+response.data.data.imagePaths);
        setFile(response.data.data.imagePaths);
        if (
          response.data.data.readingGroupArticle.memberSeq ===
          user.memberInfo.seq
        ) {
          setDisabled(false);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goModify() {
    document.location.href = `/article/modify/${useParam.boardSeq}/${useParam.articleSeq}`;
  }

  function goDelete() {
    axios
      .delete(url + `/api/v1/reading-groups/boards/${useParam.articleSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response.data.data.msg);
        alert(response.data.data.msg);
        //목록으로 이동
        document.location.href = `/readinggroup/detail/${useParam.boardSeq}`;
      })
      .catch(function (error) {
        console.log(error);
        alert("삭제 중 문제가 발생하였습니다.");
      });
  }

  function handleCommentOpen() {
    setCommentOpen(true);
    console.log(commentOpen);
  }
  function handleCommentClose() {
    setCommentOpen(false);
  }
  return (
    <>
      <ArticleDetailPresenter
        disabled={disabled}
        settings={settings}
        goDelete={goDelete}
        goModify={goModify}
        file={file}
        comments={comments}
        title={title}
        content={content}
        nickname={nickname}
        createdDate={createdDate}
        views={views}
        handleCommentOpen={handleCommentOpen}
        handleCommentClose={handleCommentClose}
        commentOpen={commentOpen}
        setCommentOpen={setCommentOpen}
      ></ArticleDetailPresenter>
    </>
  );
}

export default ArticleDetailContainer;

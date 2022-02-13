import { useState, useEffect } from "react";
import CommentPresenter from "./CommentPresenter";
import axios from "axios";
import { useParams } from "react-router-dom";
import React from "react";

function CommentContainer({ articleSeq, isOpen, onCancel }) {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";
  const [content, setContent] = useState();
  function handleContent(event) {
    setContent(event.target.value);
    console.log(content);
  }
  const handleClose = () => {
    onCancel();
  };

  function register() {
    axios
      .post(
        url + `/api/v1/reading-groups/comments`,
        { readingGroupArticleSeq: articleSeq, content: content },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        console.log(response);
        alert("댓글이 작성되었습니다.");
        setContent("");
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <CommentPresenter
      content={content}
      handleContent={handleContent}
      register={register}
      isOpen={isOpen}
      onCancel={onCancel}
    ></CommentPresenter>
  );
}

export default CommentContainer;

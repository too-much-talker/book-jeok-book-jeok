import { useState, useEffect } from "react";
import CommentPresenter from "./CommentPresenter";
import axios from "axios";
import { useParams } from "react-router-dom";
import React from "react";

function CommentContainer({ articleSeq }) {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";
  const [content, setContent] = useState();
  const [totalCnt, setTotalCnt] = useState(0);
  const [page, setPage] = useState(1);

  const [comments, setComments] = useState([
    {
      nickname: "",
      createdDate: "",
      content: "",
    },
    {
      nickname: "",
      createdDate: "",
      content: "",
    },
  ]);

  function handleContent(event) {
    setContent(event.target.value);
    console.log(content);
  }

  useEffect(() => {
    getComment();
  }, []);

  function paginate(pagenumber) {
    setPage(pagenumber);
  }

  const indexOfLastPost = page * 5;
  const indexOfFirstPost = indexOfLastPost - 5;
  const currentComments = comments.slice(indexOfFirstPost, indexOfLastPost);
  function getComment() {
    axios
      .get(url + `/api/v1/reading-groups/comments/list/${articleSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        console.log(response.data.data);
        setComments(response.data.data.comments);
        setTotalCnt(response.data.data.commentCount);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

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
        getComment();
        setContent("");
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <CommentPresenter
      paginate={paginate}
      totalCnt={totalCnt}
      currentComments={currentComments}
      content={content}
      handleContent={handleContent}
      register={register}
    ></CommentPresenter>
  );
}

export default CommentContainer;

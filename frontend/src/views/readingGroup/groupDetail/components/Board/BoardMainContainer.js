import BoardMainPresenter from "./BoardMainPresenter";
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import ArticleDetailContainer from "../../../Board/ArticleDetailContainer";

function BoardMainContainer({
  handleSetSelected,
  printDetail,
  tab,
  readingGroupSeq,
}) {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";
  const [page, setPage] = useState(1);
  const [articles, setArticles] = useState();
  const [totalCnt, setTotalCnt] = useState();

  useEffect(() => {
    getBoardList();
    console.log("컴포넌트가 화면에 나타남");
    return () => {
      console.log("컴포넌트가 화면에서 사라짐");
    };
  }, []);

  useEffect(() => {
    getBoardList();
    return () => {};
  }, [page]);

  function getBoardList() {
    axios
      .get(url + `/api/v1/reading-groups/boards/list/${readingGroupSeq}`, {
        params: {
          page: page,
          size: 10,
        },
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        setArticles(response.data.data.readingGroupBoards);
        setTotalCnt(response.data.data.totalCnt);
      })

      .catch(function (error) {
        console.log(error);
      });
  }
  function gotoRegister() {
    document.location.href = `/article/write/${readingGroupSeq}`;
  }
  function goArticle(readingGroupBoardSeq) {
    document.location.href = `/article/detail/${readingGroupSeq}/${readingGroupBoardSeq}`;
  }
  function handlePageChange(event) {
    setPage(event);
  }
  return (
    <>
      <BoardMainPresenter
        printDetail={printDetail}
        handleSetSelected={handleSetSelected}
        page={page}
        totalCnt={totalCnt}
        goArticle={goArticle}
        handlePageChange={handlePageChange}
        articles={articles}
        gotoRegister={gotoRegister}
      ></BoardMainPresenter>
    </>
  );
}
export default BoardMainContainer;

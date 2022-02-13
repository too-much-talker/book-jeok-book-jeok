import styled from "styled-components";
import React from "react";
import ArticleItem from "./ArticleItem";
import Pagination from "react-js-pagination";
const Block = styled.div`
  height: 100vh;
  width: 100%;
`;
const Head = styled.div`
  position: relative;
  height: 8%;
  display: flex;
  margin-bottom: 2%;
  border-bottom: 1px solid black;
`;
const Title = styled.div`
  position: relative;
  width: 85%;
  font-size: 30px;
  font-weight: bold;
  margin-left: 3%;
  text-align: left;
  padding-top: 1%;
`;
const RegisterArticle = styled.button`
  width: 7.5%;
  height: 40px;
  margin-top: 10px;
  background: white;
  border: 1px solid #d8d8d8;
`;
const Articles = styled.div`
  position: relative;
  margin-left: 20%;
  height: 90%;
`;
const Article = styled.div``;
function BoardMainPresenter({
  handlePageChange,
  page,
  totalCnt,
  articles,
  gotoRegister,
  goArticle,
}) {
  return (
    <>
      <Block>
        <Head>
          <Title>독서 모임 게시판</Title>
          <RegisterArticle onClick={gotoRegister}>글쓰기</RegisterArticle>
        </Head>

        <Articles>
          {articles &&
            articles[0].memberSeq !== "" &&
            articles[0].memberSeq !== null &&
            articles[0].memberSeq !== undefined &&
            articles.length > 0 &&
            articles.map((article) => (
              <Article onClick={() => goArticle(article.readingGroupBoardSeq)}>
                <ArticleItem
                  readingGroupBoardSeq={article.readingGroupBoardSeq}
                  title={article.title}
                  nickname={article.nickname}
                  createDate={article.createDate}
                  content={article.content}
                  views={article.views}
                ></ArticleItem>
              </Article>
            ))}
          <Pagination
            activePage={page}
            itemsCountPerPage={12}
            totalItemsCount={totalCnt}
            pageRangeDisplayed={5}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
          />
        </Articles>
      </Block>
    </>
  );
}

export default BoardMainPresenter;

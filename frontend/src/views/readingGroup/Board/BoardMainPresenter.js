import styled from "styled-components";
import React from "react";
import ArticleItem from "./ArticleItem";
const Block = styled.div``;
const RegisterArticle = styled.button``;
const Articles = styled.div`
  height: 100%;
  width: 100%;
`;
const Article = styled.div``;
function BoardMainPresenter({ articles, gotoRegister, goArticle }) {
  console.log(articles);
  return (
    <>
      <Block>
        <RegisterArticle onClick={gotoRegister}>
          게시글 작성하기
        </RegisterArticle>
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
        </Articles>
      </Block>
    </>
  );
}

export default BoardMainPresenter;

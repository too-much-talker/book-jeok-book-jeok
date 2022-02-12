import styled from "styled-components";
import React from "react";

const Block = styled.div`
  width: 91%;
  height: 15%;
  margin-left: 30px;
  margin-bottom: 7px;
  // background:blue;
`;
const ArticleHeader = styled.div`
  display: flex;
`;
const ArticleTitle = styled.div`
  position: relative;
  font-weight: bold;
  // background:red;
  font-size: 16px;
  margin-right: 5px;
`;
const ArticleDate = styled.div`
  // background:green;
  margin-top: 2px;
  font-size: 13px;
`;
const Nickname = styled.div``;
const Views = styled.div``;

const ArticleContents = styled.div`
  position: relative;
  font-size: 13px;
  // background:yellow;
  text-align: left;
`;

function ArticleItem({
  readingGroupBoardSeq,
  title,
  nickname,
  createDate,
  content,
  views,
}) {
  return (
    <Block>
      <ArticleHeader>
        <ArticleTitle>{title}</ArticleTitle>
        <ArticleDate>{createDate}</ArticleDate>
        <Nickname>{nickname}</Nickname>
        <Views>{views}</Views>
      </ArticleHeader>
      <ArticleContents>{content}</ArticleContents>
    </Block>
  );
}

export default ArticleItem;

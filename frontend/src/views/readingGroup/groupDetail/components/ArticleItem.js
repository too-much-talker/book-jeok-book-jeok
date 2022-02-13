import styled from "styled-components";
import React from "react";

const Block = styled.div`
  width: 91%;
  height: 15%;
  margin-left: 40px;
  margin-bottom: 3%;
  // background:blue;
`;
const ArticleHeader = styled.div`
  display: flex;
  margin-bottom: 2px;
`;
const ArticleTitle = styled.div`
  position: relative;
  // font-weight: bold;
  // background: red;
  width: 80%;
  text-align: left;
  font-size: 16px;
  margin-right: 5px;
`;
const ArticleDate = styled.div`
  margin-top: 2px;
  width: 15%;
  // background: blue;
  font-size: 14px;
  text-align: left;
  margin-right: 5px;
`;
const Nickname = styled.div`
  // background: yellow;
  margin-right: 5px;
  text-align: left;
  width: 15%;
  font-size: 14px;
`;
const Views = styled.div`
  width: 5%;
  padding-top: 2px;
  font-size: 14px;
  // background: green;
  text-align: left;
`;

const ArticleContents = styled.div`
  position: relative;
  font-size: 13px;
  // background: yellow;
  text-align: left;
  width: 66%;
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
        <Nickname>{nickname}</Nickname>
        <ArticleDate>{createDate}</ArticleDate>
        <Views>{views}</Views>
      </ArticleHeader>
      {/* <ArticleContents>{content}</ArticleContents> */}
    </Block>
  );
}

export default ArticleItem;

import styled from "styled-components";
import axios from "axios";
import { useEffect, useState } from "react";
import React from "react";

const Block = styled.div`
  position: relative;
  width: 100%;
  margin-bottom: 50px;
  margin: 15px;
  &:hover {
    cursor: pointer;
  }
`;

const Image = styled.div``;
const Content = styled.div`
  font-size: 15px;
`;

const Contents = styled.div`
  position: relative;
  width: 180px;
  margin: auto;
  text-align: left;
`;

function BookItem({
  url,
  bookInfoSeq,
  title,
  author,
  largeImgUrl,
  publisher,
  publicationDate,
  starRating,
}) {
  return (
    <>
      <Block>
        <Image>
          <img src={largeImgUrl} height="220" width="160"></img>
        </Image>
        <Contents>
          <Content>제목:{title}</Content>
          <Content>글쓴이 : {author}</Content>
          <Content>출판사: {publisher}</Content>
          <Content>출판일: {publicationDate}</Content>
          <Content>별점: {starRating}</Content>
        </Contents>
      </Block>
    </>
  );
}

export default BookItem;

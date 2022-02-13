import styled from "styled-components";
import icon from "../../../res/img/search.png";
import { Link } from "react-router-dom";
import BookItem from "./BookItem";
import SearchNavContainer from "./SearchNavContainer";
import React from "react";

const ResultBlock = styled.div`
  background: white;
  width: 80%;
  height: 60px;
  // border-top: 2px solid black;
  margin-top: 3%;
  margin: 0 auto;
  font-weight: bold;
`;

const ResultText = styled.div`
  position: absolute;
  margin-left: 3%;
  margin-top: 1%;
  font-size: 22px;
`;

const BestSellerBlock = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  flex-wrap: wrap;
  width: 80%;
  margin: 0 auto;
`;

const Book = styled.div`
  // width: 25%;
`;
function SearchMainPresenter({ goDetail, bestSellers }) {
  return (
    <>
      <h2>책 정보</h2>
      <SearchNavContainer keyword={""}></SearchNavContainer>
      <ResultBlock>
        <ResultText>현재 베스트셀러는?</ResultText>
      </ResultBlock>
      <BestSellerBlock>
        {bestSellers.map((bestSeller) => (
          // <div onClick={goDetail(bestSeller.seq)}>
          <Book onClick={() => goDetail(bestSeller.seq)}>
            <BookItem
              bookInfoSeq={bestSeller.seq}
              key={bestSeller.seq}
              title={bestSeller.title}
              author={bestSeller.author}
              largeImgUrl={bestSeller.largeImgUrl}
              publisher={bestSeller.publisher}
              publicationDate={bestSeller.publicationDate}
              starRating={bestSeller.starRating}
            ></BookItem>
          </Book>
        ))}
      </BestSellerBlock>
    </>
  );
}
export default SearchMainPresenter;

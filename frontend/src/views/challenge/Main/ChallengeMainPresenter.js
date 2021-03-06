import React, { useState } from "react";
import styled from "styled-components";
import ChallengeItem from "./ChallengeItem";
import Pagination from "react-js-pagination";
const Block = styled.div`
  width: 100%;
  height: 100vh;
`;
const Head = styled.div`
  position: relative;
  height: 10%;
  width: 90%;
  margin-left: 5%;
  margin-right: 5%;
  display: flex;
  margin-bottom: 2%;
  border-bottom: 1px solid black;
`;
const Word = styled.div`
  position: relative;
  width: 100%;
  margin-top: 30px;
  padding-bottom: 20px;
  font-size: 30px;
  font-weight: bold;
`;
const Order = styled.div`
  position: absolute;
  display: flex;
  height: 30%;
  bottom: 0;
  right: 0;
`;
const OrderBtn = styled.button`
  width: 130px;
  border: none;
  margin-left: 5px;
  &:hover {
    cursor: pointer;
  }
  // color: ${(props) => (props.active === true ? "black" : "white")};
  font-weight: ${(props) => (props.active === true ? "bold" : "regular")};
`;
const Challenges = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  width: 100%;
  margin: 0 auto;
`;
const Challenge = styled.div``;
const RegisterBtn = styled.button`
  margin-bottom: 2%;
  float: right;
  margin-right: 10%;
  height: 30px;
  width: 100px;
  color: white;
  background: black;
  border: none;
  &:hover {
    cursor: pointer;
  }
  border-radius: 10px;
  box-shadow: 2px 3px 5px 0px #c4c4c4;
`;
const ListBlock = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  flex-wrap: wrap;
  width: 85%;
  margin: 0 auto;
  margin-top: 4 0px;
`;
const ListItem = styled.div``;
function ChallengeMainPresenter({
  page,
  totalCnt,
  handlePageChange,
  challenges,
  onRegister,
  orderFalse,
  orderTrue,
  order,
}) {
  return (
    <>
      <Head>
        <Word>챌린지 리스트</Word>
        <Order>
          <OrderBtn onClick={orderFalse} active={!order}>
            진행 중인 챌린지
          </OrderBtn>
          <OrderBtn onClick={orderTrue} active={order}>
            모든 챌린지
          </OrderBtn>
        </Order>
      </Head>
      <RegisterBtn onClick={onRegister}>챌린지 만들기</RegisterBtn>
      <ListBlock>
        {challenges &&
          challenges.length > 0 &&
          challenges[0].challengeSeq !== "" &&
          challenges[0].challengeSeq !== null &&
          challenges[0].challengeSeq !== undefined &&
          challenges.length > 0 &&
          challenges.map((challenge) => (
            // <Article onClick={() => goArticle(article.readingGroupBoardSeq)}>
            <Challenge
              onClick={() => {
                document.location.href = `challenge/${challenge.challengeSeq}/${challenge.participantCount}`;
              }}
            >
              <ChallengeItem
                challengeSeq={challenge.challengeSeq}
                title={challenge.title}
                deadline={challenge.deadline}
                participantCount={challenge.participantCount}
                views={challenge.views}
                maxMember={challenge.maxMember}
              ></ChallengeItem>
            </Challenge>
          ))}
      </ListBlock>

      <Pagination
        activePage={page}
        itemsCountPerPage={12}
        totalItemsCount={totalCnt}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </>
  );
}
export default ChallengeMainPresenter;

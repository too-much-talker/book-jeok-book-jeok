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
  width: 100%;
  border-bottom: 1px solid black;
  display: flex;
`;
const Word = styled.div`
  position: relative;
  margin-left: 20px;
  margin-top: 30px;
  font-size: 20px;
`;

const Challenges = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  width: 100%;
  margin: 0 auto;
`;
const Challenge = styled.div``;
const RegisterBtn = styled.button``;
const ListBlock = styled.div`
  position: relative;
  display: flex;
  align-items: flex-start;
  flex-wrap: wrap;
  width: 80%;
  margin: 0 auto;
`;
const ListItem = styled.div``;
function ChallengeMainPresenter({
  page,
  totalCnt,
  handlePageChange,
  challenges,
  onRegister,
}) {
  console.log(challenges[0]);
  return (
    <>
      <Head>
        <Word>챌린지 리스트</Word>
      </Head>
      <RegisterBtn onClick={onRegister}>챌린지 만들기</RegisterBtn>
      <ListBlock>
        {challenges &&
          challenges[0].challengeSeq !== "" &&
          challenges[0].challengeSeq !== null &&
          challenges[0].challengeSeq !== undefined &&
          challenges.length > 0 &&
          challenges.map((challenge) => (
            // <Article onClick={() => goArticle(article.readingGroupBoardSeq)}>
            <Challenge
            // onClick={() => {
            //   printDetail();
            //   handleSetSelected(article.readingGroupBoardSeq);
            // }}
            >
              <ChallengeItem
                challengeSeq={challenge.challengeSeq}
                title={challenge.title}
                deadline={challenge.deadline}
                participantCount={challenge.participantCount}
                views={challenge.views}
              ></ChallengeItem>
            </Challenge>
          ))}
        <Pagination
          activePage={page}
          itemsCountPerPage={9}
          totalItemsCount={totalCnt}
          pageRangeDisplayed={5}
          prevPageText={"‹"}
          nextPageText={"›"}
          onChange={handlePageChange}
        />
      </ListBlock>
    </>
  );
}
export default ChallengeMainPresenter;

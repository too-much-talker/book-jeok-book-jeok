import React, { useEffect, useState } from "react";
import styled from "styled-components";
import ChallengeAuth from "./ChallengeAuth";

const Title = styled.h2`
  height: 60px;
  width: 800px;
`;

const Contents = styled.div`
  width: 100%;
  float: center;
  margin-top: 60px;
`;

const Div = styled.div`
  margin: 15px;
`;

function ChallengeDetailPresenter({ challengeInfo, onSubmit }) {
  return (
    <>
      <div>
        <Title>{challengeInfo.title}</Title>
        <hr />
        <Contents>
          <Div>{challengeInfo.content}</Div>
          <Div>
            {challengeInfo.startDate} ~ {challengeInfo.endDate}
          </Div>
          <Div>Reward : ${challengeInfo.reward}</Div>
          <Div>
            <ChallengeAuth onSubmit={onSubmit} />
          </Div>
        </Contents>
      </div>
    </>
  );
}

export default ChallengeDetailPresenter;

import React from "react";
import styled from "styled-components";
import ChallengeItem from "./ChallengeItem";

const Title = styled.div`
  height: 60px;
  width: 800px;
`;

const Contents = styled.div`
  width: 100%;
  float: center;
`;

function CahllengeListPresenter({ challengeList }) {
  return (
    <div className="container">
      <Title>
        <h1>나의 챌린지</h1>
        <br></br>
        <br></br>
      </Title>
      <Contents>
        {challengeList.map((challenge, index) => (
          <ChallengeItem challenge={challenge} key={index} />
        ))}
      </Contents>
    </div>
  );
}

export default CahllengeListPresenter;

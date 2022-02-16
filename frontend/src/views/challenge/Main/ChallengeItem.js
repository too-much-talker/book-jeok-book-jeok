import React, { useEffect, useState } from "react";
import styled from "styled-components";
const Block = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  margin-bottom: 50px;
  margin: 15px;
  &:hover {
    cursor: pointer;
  }
  background: red;
`;

const Challenge = styled.div`
  position: relative;
  width: 180px;
  margin: auto;
  text-align: left;
`;
const Title = styled.div``;
const Deadline = styled.div``;
const Bottom = styled.div`
  display: flex;
`;
const Participant = styled.div``;
const Views = styled.div``;
function ChallengeItem({
  challengeSeq,
  title,
  deadline,
  participantCount,
  views,
}) {
  const setDate = new Date(deadline);
  const setDateYear = setDate.getFullYear();
  const setDateMonth = setDate.getMonth() + 1;
  const setDateDay = setDate.getDate();
  const now = new Date();
  const distance = setDate.getTime() - now.getTime();
  const dday = Math.ceil(distance / (1000 * 60 * 60 * 24));

  return (
    <Block>
      <Challenge>
        <Title>{title}</Title>
        <Deadline>D-{dday}</Deadline>
        <Bottom>
          <Participant>{participantCount}</Participant>
          <Views>{views}</Views>
        </Bottom>
      </Challenge>
    </Block>
  );
}
export default ChallengeItem;

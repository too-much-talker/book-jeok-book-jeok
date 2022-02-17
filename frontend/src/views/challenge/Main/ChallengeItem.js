import React, { useEffect, useState } from "react";
import styled from "styled-components";
import view from "../../../res/img/view.png";
const Block = styled.div`
  height: 200px;
  margin-right: 19px;
  margin-left: 19px;
  margin-bottom: 50px;
  &:hover {
    cursor: pointer;
    transform: scale(1.1);
  }
  border-radius: 10px;
  box-shadow: 4px 5px 7px 2px lightgrey;
  text-align: center;
`;

const Challenge = styled.div`
  position: relative;
  width: 190px;
  margin: auto;
  text-align: left;
`;
const Title = styled.div`
  border-bottom: 1px solid black;
  font-size: 17px;
  padding: 10px;
  padding-top: 15px;
  height: 40px;
  font-weight: bold;
  text-align: center;
`;
const Deadlines = styled.div`
  display: flex;
`;
const Deadline = styled.div`
  color: #ff3002;
  font-weight: bolder;
  font-size: 25px;
  margin-top: 5px;
  margin-left: 15px;
`;
const Deadline2 = styled.div`
  font-size: 12px;
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 21px;
`;
const Bottom = styled.div`
  display: flex;
`;

const Participant = styled.div`
  margin-left: 15px;
  padding-top: 10px;
  height: 40px;
  color: #595959;
`;
const ParticipantMsg = styled.div``;
const ParticipantCnt = styled.div`
  font-weight: bold;
  margin-left: 2px;
  font-size: 20px;
`;
const ViewIcon = styled.img`
  width: 1.1rem;
  text-align: left;
  margin-right: 5px;
  margin-left: 15px;
`;
const ViewMsg = styled.div`
  width: 1.2rem;
  text-align: left;
  margin-right: 10px;
`;
const Views = styled.div`
  display: flex;
  margin-bottom: 20px;
  margin-top: 15px;
`;

function cutText(content, size) {
  if (content !== null && content.length > size) {
    return content.substr(0, size - 1) + "...";
  } else return content;
}

function ChallengeItem({
  challengeSeq,
  title,
  deadline,
  participantCount,
  views,
  maxMember,
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
        <Title>{cutText(title, 18)}</Title>
        <Deadlines>
          <Deadline>D-{dday}</Deadline>
          {/* <Deadline2>마감 : {deadline}</Deadline2> */}
        </Deadlines>

        <Participant>
          <ParticipantMsg> 참가자 현황 :</ParticipantMsg>
          <ParticipantCnt>
            {participantCount}/{maxMember}
          </ParticipantCnt>
        </Participant>
        <Views>
          <ViewIcon src={view}></ViewIcon>
          <ViewMsg>{views}</ViewMsg>
        </Views>
      </Challenge>
    </Block>
  );
}
export default ChallengeItem;

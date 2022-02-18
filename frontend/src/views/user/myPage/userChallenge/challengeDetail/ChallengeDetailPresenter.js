import React, { useEffect, useState } from "react";
import styled from "styled-components";
import ChallengeAuth from "./ChallengeAuth";

const Info = styled.div`
  width: 100%;
  margin-left: 10%;
  padding-top: 20px;
  padding-bottom: 20px;
  box-shadow: 5px 5px 10px #c4c4c4;
`;
const Title = styled.h2`
  height: 30px;
  width: 700px;
`;
const Content = styled.div``;

const Contents = styled.div`
  width: 100%;
  float: center;
`;

const Div = styled.div`
  margin: 15px;
`;

function ChallengeDetailPresenter({
  button,
  postfiles,
  uploadFile,
  challengeInfo,
  onSubmit,
  handleTitle,
  handleContent,
  todayAuth,
}) {
  const setDate = new Date(challengeInfo.startDate);
  const now = new Date();
  const distance = setDate.getTime() - now.getTime();
  const dday = Math.ceil(distance / (1000 * 60 * 60 * 24));
  return (
    <>
      {challengeInfo !== {} && (
        <div>
          <Info>
            <Title>{challengeInfo.title}</Title>
            <Div>{challengeInfo.content}</Div>
            <Div>
              챌린지 기간 : {challengeInfo.startDate} ~ {challengeInfo.endDate}
            </Div>
            <Div>Reward : {challengeInfo.reward} Points</Div>
            <Div>
              <h3>
                참여자 (
                {challengeInfo.participantNicknames &&
                  challengeInfo.participantNicknames.length}
                /{challengeInfo.maxMember})
              </h3>
              <ul>
                {challengeInfo.participantNicknames &&
                  challengeInfo.participantNicknames.map((nickname) => (
                    <li>{nickname}</li>
                  ))}
              </ul>
            </Div>
            <Div>
              <h3>나의 인증 내역</h3>
              <div>
                달성률 : <b>{challengeInfo.authRate}%</b>
              </div>
              {challengeInfo.authDates &&
                challengeInfo.authDates.map((date, index) => (
                  <div key={index}>{date}</div>
                ))}
            </Div>
          </Info>

          <Contents>
            <Div>
              <ChallengeAuth
                dday={dday}
                todayAuth={todayAuth}
                challengeInfo={challengeInfo}
                button={button}
                postfiles={postfiles}
                uploadFile={uploadFile}
                onSubmit={onSubmit}
                handleTitle={handleTitle}
                handleContent={handleContent}
              />
            </Div>
          </Contents>
        </div>
      )}
    </>
  );
}

export default ChallengeDetailPresenter;

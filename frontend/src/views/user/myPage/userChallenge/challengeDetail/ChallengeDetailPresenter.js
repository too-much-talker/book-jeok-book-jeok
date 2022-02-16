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
  console.log(challengeInfo);
  return (
    <>
      {challengeInfo !== {} && (
        <div>
          <Title>{challengeInfo.title}</Title>
          <hr />
          <Contents>
            <Div>
              <ChallengeAuth onSubmit={onSubmit} />
            </Div>
            <br />
            <hr />
            <br />
            <h4>{challengeInfo.content}</h4>
            <Div>
              {challengeInfo.startDate} ~ {challengeInfo.endDate}
            </Div>
            <Div>Reward : ${challengeInfo.reward}</Div>

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

            <Div>
              <h3>
                참여자(
                {challengeInfo.participantNicknames &&
                  challengeInfo.participantNicknames.length}
                )
              </h3>
              <ul>
                {challengeInfo.participantNicknames &&
                  challengeInfo.participantNicknames.map((nickname) => (
                    <li>{nickname}</li>
                  ))}
              </ul>
            </Div>
          </Contents>
        </div>
      )}
    </>
  );
}

export default ChallengeDetailPresenter;

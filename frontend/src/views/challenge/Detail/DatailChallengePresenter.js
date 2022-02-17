import React, { useEffect, useState } from "react";
import styled from "styled-components";
const Block = styled.div`
  width: 80%;
  height: 80vh;
  margin-left: 10%;
  margin-right: 10%;
  margin-top: 20px;
  border: 1px solid #cccccc;
  border-radius: 1.5rem;
`;
const Head = styled.div`
  height: 20%;
  border-bottom: 1px solid black;
  margin-left: 8%;
  margin-right: 8%;
  display: flex;
`;
const Title = styled.div`
  width: 85%;
  // background: red;
  margin-top: 7%;
  margin-left: 1%;
  font-size: 30px;
  text-align: left;
`;
const RightBlock = styled.div`
  width: 15%;
  margin-top: 5%;
`;
const Btn = styled.button`
  margin-bottom: 8%;
  height: 30px;
  width: 80px;
  border-radius: 10px;
  border: none;
  &:hover {
    cursor: pointer;
  }
`;
const Views = styled.div``;

const InfoBlock = styled.div`
  margin-top: 3%;
  height: 25%;
  display: flex;
`;
const Dday = styled.div`
  border: 1px solid #c4c4c4;
  height: 60%;
  width: 16%;
  margin-left: 10%;
  margin-right: 5%;
  font-size: 40px;
  font-weight: bolder;
  padding-top: 5%;
`;
const Infos = styled.div`
  margin-top: 6%;
  width: 30vw;
  text-align: left;
`;
const Deadline = styled.div`
  margin-left: 1%;
  margin-bottom: 2%;
  display: flex;
`;
const DateBlock = styled.div`
  display: flex;
  margin-left: 1%;
  margin-bottom: 2%;
`;
const StartDate = styled.div`
  width: 47%;
  display: flex;
`;
const EndDate = styled.div`
  width: 47%;
  display: flex;
`;
const MiniWord = styled.div`
  margin-left: 1%;
`;
const MaxMember = styled.div`
  height: 5%;
  margin-top: 2%;
  margin-bottom: 2%;
  margin-left: 10%;
  margin-right: 10%;
  text-align: left;
  font-size: 25px;
  display: flex;
`;
const MaxWord = styled.div`
  margin-right: 2%;
`;
const MaxCnt = styled.div`
  font-weight: bold;
`;
const ContentBlock = styled.div`
  margin-top: 1%;
  height: 25%;
  margin-left: 10%;
  margin-right: 10%;

  padding: 15px;
  text-align: left;
  background: #f8f8f8;
`;
const Reward = styled.div`
  margin-left: 1%;
  display: flex;
`;
const Date = styled.div`
  margin-left: 2%;
  margin-right: 1%;
  font-weight: bold;
`;

function DatailChallengePresenter({
  isParticipated,
  goRegister,
  goDelete,
  dday,
  details,
  participant,
  writer,
  user,
}) {
  return (
    <>
      <Block>
        <Head>
          <Title>{details.title}</Title>
          <RightBlock>
            {writer === user.memberInfo.seq ? (
              <Btn style={{ background: "#FFD5AF", width: "120px" }}>
                내가 만든 챌린지
              </Btn>
            ) : !isParticipated ? (
              <Btn onClick={goRegister}>신청하기</Btn>
            ) : (
              <Btn onClick={goDelete} style={{ background: "#FFD5AF" }}>
                신청 취소
              </Btn>
            )}

            <Views>조회수 : {details.views}</Views>
          </RightBlock>
        </Head>
        <InfoBlock>
          <Dday>D-{dday}</Dday>
          <Infos>
            <Deadline>
              <MiniWord>모집 기한 : </MiniWord>
              <Date>
                <b>{details.deadline}</b>
              </Date>{" "}
            </Deadline>
            <DateBlock>
              {/* <StartDate>
                <MiniWord>챌린지 기한 : </MiniWord>
                <Date>{details.startDate}</Date>
                <MiniWord>부터</MiniWord>
              </StartDate>
              <EndDate>
                <Date>{details.endDate}</Date>
                <MiniWord>까지</MiniWord>
              </EndDate> */}
              <div>
                챌린지 기한 :
                <div>
                  <b>
                    {details.startDate} ~ {details.endDate}
                  </b>
                </div>
              </div>
            </DateBlock>
            <Reward>
              <MiniWord>리워드 : </MiniWord> <Date>{details.reward}</Date>
            </Reward>
          </Infos>
        </InfoBlock>
        <MaxMember>
          <MaxWord>모집 인원 현황 : </MaxWord>
          <MaxCnt>
            {participant} / {details.maxMember}
          </MaxCnt>
        </MaxMember>

        <ContentBlock>{details.content}</ContentBlock>
      </Block>
    </>
  );
}
export default DatailChallengePresenter;

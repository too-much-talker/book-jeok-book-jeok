import React, { useState } from "react";
import styled from "styled-components";

const Block = styled.div`
  border: 1px solid black;
  border-radius: 20px;
  width: 100%;
  height: 100vh;
  margin-bottom: 5vh;
`;

const Head = styled.div`
  border-bottom: 1px solid black;
  margin-left: 15%;
  margin-right: 15%;
  margin-bottom: 20px;
  text-align: left;
`;
const Msg = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 25px;
`;
const Word = styled.div`
  text-align: left;

  margin-left: 15%;
  margin-bottom: 1%;
  margin-top: 2%;
  font-size: 18px;
`;

const TitleBlock = styled.div`
  margin-bottom: 60px;
`;
const TitleInput = styled.input`
  float: left;
  margin-left: 15%;
  width: 65%;
`;

const ContentBlock = styled.div`
  margin-bottom: 75px;
`;
const ContentInput = styled.textarea`
  width: 65%;
  height: 10%;
  resize: none;
  float: left;
  margin-left: 15%;
`;
const DeadLineBlock = styled.div`
  height: 80px;
  margin-bottom: 30px;
`;
const DeadLine = styled.input.attrs({
  type: "date",
})`
  float: left;
  margin-left: 15%;
`;

const DateBlock = styled.div`
  height: 90px;
  text-align: left;
`;
const Dates = styled.div`
  display: flex;
  float: left;
  margin-left: 15%;
`;
const StartDate = styled.input.attrs({
  type: "date",
})`
  height: 100%;
`;

const EndDate = styled.input.attrs({
  type: "date",
})`
  height: 100%;
`;
const LimitBlock = styled.div`
  margin-top: 10px;
  margin-bottom: 10px;
  height: 80px;
  text-align: left;
`;
const LimitMember = styled.input`
  float: left;
  margin-left: 15%;
  margin-right: 5px;
  width: 7%;
`;
const RewardBlock = styled.div`
  width: 900px;
  height: 25px;
  text-align: left;
  float: left;
  display: flex;
  margin-left: 15%;
`;
const Reward = styled.input`
  margin-right: 5px;
`;

const LittleMsg = styled.div`
  margin-right: 10px;
  text-align: left;
  margin-left: 5px;
`;
const RegisterBtn = styled.button`
  margin-top: 20px;
`;
const Notice = styled.div`
  text-align: left;

  margin-left: 15%;
  margin-bottom: 1%;
  font-size: 12px;
`;
function RegisterChallengePresenter({
  handleTitle,
  handleContent,
  handleDeadline,
  handleStartDate,
  handleEndDate,
  handleLimitMember,
  handleReward,
  goRegister,
}) {
  return (
    <>
      <Block>
        <Head>
          <Msg>????????? ????????????</Msg>
        </Head>
        <TitleBlock>
          <Word>??????</Word>
          <TitleInput
            placeholder="????????? ??????????????????.(??????11???)"
            onChange={handleTitle}
          ></TitleInput>
        </TitleBlock>
        <ContentBlock>
          <Word>??????</Word>
          <ContentInput
            placeholder="????????? ??????????????????"
            onChange={handleContent}
          ></ContentInput>
        </ContentBlock>
        <DeadLineBlock>
          <Word>?????? ??????</Word>
          <Notice>?????? ????????? ????????? ??????????????? ?????? ?????? ????????? ?????????</Notice>
          <DeadLine onChange={handleDeadline}></DeadLine>
        </DeadLineBlock>

        <DateBlock>
          <Word>????????? ??????</Word>
          <Notice>?????????+6 ????????? ??????????????? ???????????? ????????? ?????????.</Notice>
          <Dates>
            <StartDate onChange={handleStartDate}></StartDate>
            <LittleMsg>??????</LittleMsg>
            <EndDate onChange={handleEndDate}></EndDate>
            <LittleMsg>??????</LittleMsg>
          </Dates>
        </DateBlock>

        <LimitBlock>
          <Word>?????? ?????? ???</Word>
          <Notice>?????? 3??? ?????? 10???</Notice>
          <LimitMember onChange={handleLimitMember}></LimitMember>
          <LittleMsg>???</LittleMsg>
        </LimitBlock>
        <Word>?????????</Word>
        <Notice>?????? 1 ?????? 1,000</Notice>
        <RewardBlock>
          <Reward onChange={handleReward}></Reward>
          <LittleMsg>Point</LittleMsg>
        </RewardBlock>
        <RegisterBtn onClick={goRegister}>????????? ????????????</RegisterBtn>
      </Block>
    </>
  );
}
export default RegisterChallengePresenter;

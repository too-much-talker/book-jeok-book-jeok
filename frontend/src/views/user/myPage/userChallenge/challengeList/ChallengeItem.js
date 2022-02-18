import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const GroupBox = styled.div`
  //   border: solid 1px;
  box-shadow: 5px 5px 10px #c4c4c4;
  width: 500px;
  display: inline-block;
  float: center;
  margin: 10px;
  margin-top: 20px;
  margin-bottom: 10px;
  margin-right: 50px;
  padding: 20px;
  padding-top: 10px;
  padding-buttom: 0px;
  border-radius: 5px;
  text-align: left;
  &:hover {
    cursor: pointer;
    transform: scale(1.1);
  }
`;

const ItemLink = styled(Link)`
  text-decoration: none;
  color: black;
`;

const State = styled.div`
  text-align: right;
  font-weight: bold;
`;

const Img = styled.img`
  width: 100%;
`;

const Week = styled.div`
  font-size: 0.85rem;
`;

function ChallengeItem({ challenge }) {
  const status_val = ["PRE", "ING", "END", "FAIL"];
  const status_kor = ["진행 전", "진행 중", "종료", "폐지"];
  console.log(challenge.status);
  return (
    <>
      <GroupBox>
        <ItemLink to={`/mypage/challenge/${challenge.challengeSeq}/`}>
          <h3>{challenge.title}</h3>
          <span>{challenge.numOfParticipants}명 참여</span>
          <Week>
            {challenge.startDate} ~ {challenge.endDate}
          </Week>
          <State>
            <span>
              {status_val.map((state, index) => {
                if (state === challenge.status) return status_kor[index];
              })}
            </span>
          </State>
          <div>{challenge.reward} Points</div>
          {challenge.status === "PRE" ? (
            <State>
              {/* {challenge.status === "ING" && ( */}
              <span>0% 달성중...</span>
              {/* )} */}
            </State>
          ) : (
            <State>
              {/* {challenge.status === "ING" && ( */}
              <span>{challenge.authRate}% 달성중...</span>
              {/* )} */}
            </State>
          )}
        </ItemLink>
      </GroupBox>{" "}
    </>
  );
}

export default ChallengeItem;

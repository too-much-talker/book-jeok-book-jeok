import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const GroupBox = styled.div`
  //   border: solid 1px;
  box-shadow: 5px 5px 10px grey;
  width: 500px;
  display: inline-block;
  float: center;
  margin: 10px;
  margin-top: 20px;
  margin-buttom: 20px;
  padding: 20px;
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
  console.log(challenge);
  return (
    <>
      <GroupBox>
        <ItemLink to={`/mypage/challenge/${challenge.challengeSeq}`}>
          <h3>{challenge.title}</h3>
          <Week>
            {challenge.startDate} ~ {challenge.endDate}
          </Week>
          <State>
            {/* {status_val.map((state, index) => {
              if (state === challenge.status) return status_kor[index];
            })} */}
          </State>
        </ItemLink>
      </GroupBox>{" "}
    </>
  );
}

export default ChallengeItem;

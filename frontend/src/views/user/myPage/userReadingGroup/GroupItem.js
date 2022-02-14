import React from "react";
import styled from "styled-components";
import FREE from "../../../../res/img/img_free.jpg";
import SEMINAR from "../../../../res/img/img_seminar.jpg";
import STUDY from "../../../../res/img/img_study.jpg";
import DISCUSS from "../../../../res/img/img_discuss.jpg";

import { Link } from "react-router-dom";

const GroupBox = styled.div`
  border: solid 1px;
  width: 200px;
  display: inline-block;
  float: left;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  text-align: left;
  &:hover {
    cursor: pointer;
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

function GroupItem({ group }) {
  const days_val = ["MON", "TUE", "WEN", "TUR", "FRI", "SAT"];
  const days_kor = ["월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

  const status_val = ["PRE", "ING", "END", "FAIL"];
  const status_kor = ["진행 전", "진행 중", "종료", "폐지"];

  return (
    <>
      <GroupBox>
        <ItemLink to={`/readinggroup/detail/${group.readingGroupSeq}`}>
          {group.readingGroupType === "FREE" && <Img src={FREE} />}
          {group.readingGroupType === "DISCUSS" && <Img src={DISCUSS} />}
          {group.readingGroupType === "SEMINAR" && <Img src={SEMINAR} />}
          {group.readingGroupType === "STUDY" && <Img src={STUDY} />}
          <div>{group.title}</div>
          <Week>
            매 주{" "}
            {group.days.map((week, index) =>
              days_val.map((day) => {
                if (day === week) {
                  return <span key={index}>{days_kor[index]} </span>;
                }
              })
            )}
          </Week>
          <State>
            {status_val.map((state, index) => {
              if (state === group.status) return status_kor[index];
            })}
          </State>
        </ItemLink>
      </GroupBox>
    </>
  );
}

export default GroupItem;

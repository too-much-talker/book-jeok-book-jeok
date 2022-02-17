import React from "react";
import styled from "styled-components";
import FREE from "../../../../res/img/img_free.jpg";
import SEMINAR from "../../../../res/img/img_seminar.jpg";
import STUDY from "../../../../res/img/img_study.jpg";
import DISCUSS from "../../../../res/img/img_discuss.jpg";

const Content = styled.div`
  width: 20%;
  float: left;
  height: 100vh;
  background: white;
  width: 80%;
  padding-top: 0;
`;

const CoverBox = styled.div`
  max-height: 150px;
  overflow: hidden;
`;

const Cover = styled.img`
  max-height: initial;
  margin-top: -8%;
`;

function GroupInfo({ groupInfo }) {
  const days_val = ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"];
  const days_kor = [
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일",
    "일요일",
  ];

  const status_val = ["PRE", "ING", "END", "FAIL"];
  const status_kor = ["진행 전", "진행 중", "종료", "폐지"];

  const type_val = ["DISCUSS", "SEMINAR", "STUDY", "FREE"];
  const type_kor = ["토론형", "세미나형", "스터디형", "자유형"];

  return (
    <>
      <Content>
        <CoverBox>
          {groupInfo.readingGroupType === "FREE" && <Cover src={FREE} />}
          {groupInfo.readingGroupType === "DISCUSS" && <Cover src={DISCUSS} />}
          {groupInfo.readingGroupType === "SEMINAR" && <Cover src={SEMINAR} />}
          {groupInfo.readingGroupType === "STUDY" && <Cover src={STUDY} />}
        </CoverBox>
        <h3>{groupInfo.title}</h3>
        <div>모임장 : {groupInfo.writer}</div>
        <div>
          #
          {type_val.map((type, index) => {
            if (type === groupInfo.readingGroupType) {
              return type_kor[index];
            }
          })}
        </div>
        <hr />
        <br />
        <div>{groupInfo.content}</div>
        <div>
          <h4>모임 정보</h4>
          참여 가능 레벨 : {groupInfo.minLevel} 이상 <br />
          활동 상태 :{" "}
          {status_val.map((statue, index) => {
            if (statue === groupInfo.status) {
              return status_kor[index];
            }
          })}
        </div>
        <div>
          <h4>모임 기간</h4>
          {groupInfo.startDate} ~ {groupInfo.endDate}
        </div>
        <div>
          <h4>모임 일정</h4>
          <div>
            매 주{" "}
            {groupInfo.days.map((week, index) =>
              days_val.map((day, idx) => {
                if (day === week) {
                  return <span key={idx}>{days_kor[idx]} </span>;
                }
              })
            )}
          </div>
        </div>
        <h3>참가자</h3>
        <ul>
          {groupInfo.participants.map((member, index) => (
            <li key={index}>{member}</li>
          ))}
        </ul>
      </Content>
    </>
  );
}

export default GroupInfo;

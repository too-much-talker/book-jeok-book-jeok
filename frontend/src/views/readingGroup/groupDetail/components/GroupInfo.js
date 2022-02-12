import React from "react";
import styled from "styled-components";

const Content = styled.div`
  width: 20%;
  float: left;
  height: 100vh;
  background: white;
  width: 80%;
  padding-top: 0;
`;

function GroupInfo({ groupInfo }) {
  return (
    <>
      <Content>
        <h3>{groupInfo.title}</h3>
        <div>{groupInfo.content}</div>
        <div>
          {groupInfo.startDate} ~ {groupInfo.endDate}
        </div>
        <div>
          <h4>모임 일정</h4>
          <div>
            매 주{" "}
            {groupInfo.weeks.map((week, index) => (
              <span key={index}>{week} </span>
            ))}
          </div>
        </div>
        <h3>참가자</h3>
        <ul>
          {groupInfo.members.map((member, index) => (
            <li key={index}>{member}</li>
          ))}
        </ul>
      </Content>
    </>
  );
}

export default GroupInfo;

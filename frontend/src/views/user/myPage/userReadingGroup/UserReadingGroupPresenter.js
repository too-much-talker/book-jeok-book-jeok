import React, { useState } from "react";
import styled from "styled-components";
import GroupItem from "./GroupItem";

const Wrapper = styled.div``;

const H2 = styled.h2``;

const Title = styled.div`
  height: 60px;
  width: 800px;
`;

const Contents = styled.div`
  width: 100%;
  float: center;
  margin-left: 50px;
`;

const PageNav = styled.div`
  button: 0px;
  margin-top: 40px;
`;

function UserReadingGroupPresenter({ groups }) {
  return (
    <>
      <div className="container">
        <Title>
          <h1>나의 독서모임</h1>
          <br></br>
          <br></br>
        </Title>
        <Contents>
          {groups.map((group, index) => (
            <GroupItem group={group} key={index} />
          ))}
        </Contents>
      </div>
    </>
  );
}

export default UserReadingGroupPresenter;

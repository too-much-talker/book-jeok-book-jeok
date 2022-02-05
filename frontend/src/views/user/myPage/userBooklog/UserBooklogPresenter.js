import React, { useState } from "react";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";

function UserBooklogPresenter() {
  const { path } = useParams();

  const Title = styled.div`
    padding-left: 10px;
  `;

  return (
    <div>
      <Title>
        <h2>나의 북로그</h2>
      </Title>
      <div>
        <Link to="/booklogdetail" state={{ logSeq: 1, infoSeq: 1 }}>
          <div>북로그1</div>
        </Link>
        <Link to="/booklogregister">
          <button
            style={{ position: "absolute", right: 0, marginRight: "20px" }}>
            작성
          </button>
        </Link>
      </div>
      <div>{/* <BoardList /> */}</div>
    </div>
  );
}

export default UserBooklogPresenter;

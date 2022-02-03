import React, { useState } from "react";
import styled from "styled-components";
import { useParams } from "react-router-dom";

function UserBooklogPresenter() {
  const { path } = useParams();
  return (
    <div>
      <div className="title">
        <h2>나의 북로그</h2>
        <h3>북로그 제목</h3>
      </div>
    </div>
  );
}

export default UserBooklogPresenter;

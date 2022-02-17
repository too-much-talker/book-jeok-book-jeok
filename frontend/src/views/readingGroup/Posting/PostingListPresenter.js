import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import Pagination from "react-js-pagination";

const Button1 = styled.div`
  margin-left: 730px;
  margin-top: 30px;
  margin-bottom: 30px;
`;

const Button = styled.button`
  padding: 10px;
  padding-top: 5px;
  padding-buttom: 5px;
  background: grey;
  color: white;
  &:hover {
    cursor: pointer;
    background: black;
  }
  border-radius: 5px;
`;

function PostingListPresenter({ groupList }) {
  return (
    <>
      <h1>독서모임 회원을 모집합니다.</h1>
      <Link to="/postingregister">
        <Button1>
          <Button>독서모임 개설</Button>
        </Button1>
      </Link>
      {groupList}
    </>
  );
}

export default PostingListPresenter;

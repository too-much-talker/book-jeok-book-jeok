import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";
import Pagination from "react-js-pagination";

const BookLog = styled.div`
  width: 100%;
  text-align: center;
`;
const Register = styled.div`
  /* text-align: right; */
  align-items: right;
  margin-left: 700px;
`;
const Header = styled.div`
  display: flex;
  margin-left: 70px;
  label {
    display: inline-block;
  }
`;
const Title = styled.div``;
function UserBooklogList({
  isOpen,
  checkBoxHandler,
  context,
  page,
  totalCnt,
  handlePageChange,
}) {
  return (
    <div>
      <Title>
        <h1>나의 북로그</h1>
      </Title>
      <br></br>
      <div>
        <Link to="/booklogregister">
          <Register>
            <button>작성</button>
          </Register>
        </Link>
        <Header>
          <label>공개된 북로그만 보기</label>
          <input value={isOpen} onChange={checkBoxHandler} type="checkbox" />
        </Header>
        <br></br>
        <BookLog>{context}</BookLog>
        <Pagination
          activePage={page}
          itemsCountPerPage={10}
          totalItemsCount={totalCnt}
          pageRangeDisplayed={5}
          prevPageText={"‹"}
          nextPageText={"›"}
          onChange={handlePageChange}
        />
      </div>
    </div>
  );
}
export default UserBooklogList;

import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";
import Pagination from "react-js-pagination";

const BookLog = styled.div`
  width: 100%;
  text-align: center;
`;
const Register = styled.div`
  text-align: right;
`;
const Header = styled.div`
  display: flex;
  justify-content: space-between;
  span {
    margin-left: 8rem;
  }
  label {
    display: inline-block;
  }
`;

function UserBooklogList({
  isOpen,
  checkBoxHandler,
  context,
  page,
  totalCnt,
  handlePageChange
}
) {
  return (
    <div>
      <h2>나의 북로그</h2>
      <br></br>
      <div>
        <Link to="/booklogregister">
          <Register>
            <button>작성</button>
          </Register>
        </Link>
        <Header>
          <span>
            <label>공개된 북로그만 보기</label>
            <input value={isOpen} onChange={checkBoxHandler} type="checkbox" />
          </span>
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

import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import axios from "axios";
import BookLogCard from "../userBooklog/BookLogCard";
import Pagination from "react-js-pagination";

const url = "https://i6a305.p.ssafy.io:8443";

const BookLog = styled.div`
  width: 800px;
  float: center;
`;
const Button = styled.div`
  margin-left: 700px;
`;
const Title = styled.div``;
function UserHeartBooklogPresenter({
  context,
  page,
  totalCnt,
  handlePageChange,
}) {
  return (
    <div>
      <Title>
        <h1>하트 누른 북로그</h1>
      </Title>
      <br></br>
      <div>
        <Link to="/booklogregister">
          <Button>{/* <button>저장</button> */}</Button>
          <div></div>
        </Link>
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
export default UserHeartBooklogPresenter;

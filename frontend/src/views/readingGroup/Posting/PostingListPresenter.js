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

function PostingListPresenter({
    groupList,
    page,
    totalCnt,
    handlePageChange
}) {
  
    
  return (
    <>
    <h1>독서모임 회원을 모집합니다.</h1>
      <Link to="/postingregister">
        <Button1>
          <button>독서모임 개설</button>
        </Button1>
      </Link>
      {groupList}
      <Pagination
        activePage={page}
        totalItemsCount={totalCnt}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </>
  );
}

export default PostingListPresenter;

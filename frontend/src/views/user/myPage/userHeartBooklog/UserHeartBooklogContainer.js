import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import axios from "axios";
import BookLogCard from "../userBooklog/BookLogCard";

import UserHeartBooklogPresenter from "./UserHeartBooklogPresenter";

const url = "https://i6a305.p.ssafy.io:8443";

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
function UserHeartBooklogContainer() {
  const [page, setPage] = useState(1);
  const [totalCnt, setTotalCnt] = useState(0);
  const [context, setContext] = useState([]);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const pageLoading = async () => {
    const books = await axios.get(
      url + `/api/v1/booklogs/likes?page=${page}&size=10`,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    const bookList = books.data.data.booklogs;
    setTotalCnt(books.data.data.totalCnt);
    setContext(
      bookList.map((book) => {
        return (
          <Link
            key={book.booklogSeq}
            to={`/booklogs/detail/${book.booklogSeq}`}
          >
            <BookLogCard key={book.booklogSeq} book={book} />
          </Link>
        );
      })
    );
  };
  useEffect(() => {
    pageLoading();
  }, []);
  const handlePageChange = (event) => {
    setPage(event);
  };
  return (
    <UserHeartBooklogPresenter
      context={context}
      page={page}
      totalCnt={totalCnt}
      handlePageChange={handlePageChange}
    />
  );
}
export default UserHeartBooklogContainer;

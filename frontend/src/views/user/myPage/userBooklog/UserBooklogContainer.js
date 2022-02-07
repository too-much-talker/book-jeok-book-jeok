import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import BookLogCard from "./BookLogCard";
import { useSelector } from "react-redux";
import Pagination from "react-js-pagination";

const url = "https://i6a305.p.ssafy.io:8443";
const Title = styled.div`
  display: flex;
  width: 80rem;
  padding-left: 10px;
  justify-content: center;
`;
const BookLog = styled.div`
  /* display: flex; */
  width: 100%;
  text-align: center;
  /* margin: 5rem; */
`;
const Register = styled.div`
  text-align: right;
  margin-right: 8rem;
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
function UserBooklogPresenter() {
  const { path } = useParams();
  const [totalCnt, setTotalCnt] = useState(0);
  const [context, setContext] = useState([]);
  const [page, setPage] = useState(1);
  const [isOpen, setIsOpen] = useState(false);

  //토큰
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const user = useSelector((state) => state.authReducer);
  const pageLoading = async () => {
    // console.log(enteredText.current.value);
    const books = await axios.get(
      url + `/api/v1/booklogs/me?page=${page}&size=10&all=${!isOpen}`,
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
            to="/booklogdetail"
            state={{ logSeq: book.booklogSeq }}
          >
            <BookLogCard key={book.booklogSeq} book={book} />
          </Link>
        );
      })
    );
  };
  // pageLoading();
  useEffect(() => {
    pageLoading();
  }, [page, isOpen]);
  const handlePageChange = (event) => {
    setPage(event);
  };
  const checkBoxHandler = (event) => {
    setIsOpen(event.target.checked);
    setPage(1);
  };
  return (
    <div>
      <h2>나의 북로그</h2>
      <div>
        <Header>
          <span>
            <label>공개된 북로그만 보기</label>
            <input value={isOpen} onChange={checkBoxHandler} type="checkbox" />
          </span>
        </Header>
        <Link to="/booklogregister">
          <Register>
          <button>작성</button>
          </Register>
        </Link>
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
export default UserBooklogPresenter;

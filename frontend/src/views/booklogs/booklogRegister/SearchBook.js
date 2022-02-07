import axios from "axios";
import { useEffect, useState, useRef } from "react";
import SearchedBook from "./SearchedBook";
import Pagination from "react-js-pagination";
import styled from "styled-components";

const url = "https://i6a305.p.ssafy.io:8443";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
`;
const SearchBook = (props) => {
  const [page, setPage] = useState(1);
  const [totalCnt, setTotalCnt] = useState();
  const enteredTextRef = useRef();
  const [enteredText, setEnteredText] = useState("");
  const [context, setContext] = useState([]);
  const selectBook = (book) => {
    props.setbook(book);
    props.search(true);
  };

  const pageLoading = async () => {
    // console.log(enteredText.current.value);
    const books = await axios.post(url + `/api/v1/bookinfos`, {
      page: page,
      limit: 12,
      searchCategory: "title",
      searchKeyword: enteredText,
      orderCategory: "review"
    });
    const bookList = books.data.data.bookList;
    setTotalCnt(books.data.data.totalCnt);
    setContext(
      bookList.map((book) => {
        return <SearchedBook onClick={selectBook} book={book} key={book.seq} />;
      })
    );
  };
  const searchButtonClick = () => {
    // setEnteredText(enteredTextRef.current.value);
    setPage(1);
    pageLoading();
  };
  useEffect(() => {
    pageLoading();
  }, [page]);
  function handlePageChange(event) {
    setPage(event);
  }
  const inputChangeHandler = (event) => {
    // enteredTextRef.current.value = event.target.value;
    setEnteredText(event.target.value);
  };
  const onOutHandler = () => {
    window.location.replace("/mypage/mybooklog");
  };
  return (
    <div>
      <button onClick={onOutHandler} style={{ right: 0, marginLeft: "93%" }}>
        취소
      </button>
      <Wrapper>
        <h3>책을 검색해주세요.</h3>
        <div>
          <input
            value={enteredText}
            onChange={inputChangeHandler}
            ref={enteredTextRef}
            type="text"
            size="40"
          ></input>
          <button onClick={searchButtonClick}>검색</button>
        </div>
      </Wrapper>
      <div>{context}</div>
      <Pagination
        activePage={page}
        itemsCountPerPage={12}
        totalItemsCount={totalCnt}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </div>
  );
};
export default SearchBook;

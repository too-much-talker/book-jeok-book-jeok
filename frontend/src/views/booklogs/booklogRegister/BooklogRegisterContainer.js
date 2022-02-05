import React, { useState } from "react";
import SearchBook from "./SearchBook";
import Modal from "react-modal";
// import { BOOKINFO } from "../dummydata";
// import "./RegisterPage.css"
import axios from "axios";
import RegisterForm from "./BooklogRegisterPresenter";

const url = "https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io";
// const { title, author, publisher, publicationDate, largeImgUrl, seq } =
//   BOOKINFO;

function BooklogRegisterContainer() {
  const [TitleValue, setTitleValue] = useState("");
  const [ContentValue, setContentValue] = useState("");
  const [isSearched, setIsSearched] = useState(false);
  const [rating, setRating] = useState(0);
  const [toggle, setToggle] = useState(false);
  const [oneSentence, setOneSentence] = useState("");
  const [selectedBook, setSelectedBook] = useState({});
  let tmpUrl;
  if (isSearched) {
    tmpUrl = selectedBook.smallImgUrl.substring(
      0,
      selectedBook.smallImgUrl.length - 5
    );
    tmpUrl = tmpUrl + "s.jpg";
    console.log(tmpUrl);
  }
  const onSentenceChange = (event) => {
    setOneSentence(event.target.value);
  };
  const onTitleChange = (event) => {
    setTitleValue(event.currentTarget.value);
  };

  const onContentChange = (event) => {
    setContentValue(event.currentTarget.value);
  };
  // console.log(ContentValue);

  const onSubmitArticle = (event) => {
    event.preventDefault();
    const article = {
      title: TitleValue,
      content: ContentValue,
      rating: rating,
      sentence: oneSentence,
      private: !toggle,
      date: new Date(),
    };
    if (TitleValue === "" || ContentValue === "") {
      alert("제목과 내용을 입력해주세요.");
    } else {
      axios
        .post(url + `/api/v1/booklogs`, {
          memberSeq: 1,
          bookInfoSeq: selectedBook.seq,
          title: TitleValue,
          isOpen: !toggle,
          content: ContentValue,
          summary: oneSentence,
          starRating: rating,
          readDate: new Date(),
        })
        .then(function (response) {
          console.log(response.status);
          if (response.status === 201) {
            alert(response.data.data.msg);
            document.location.href = "/mypage/mybooklog";
          } else {
            alert(response.data.data.msg);
          }
        });
    }
  };

  const onSubmitChangeBook = (event) => {
    event.preventDefault();
    setIsSearched(false);
    const article = { title: TitleValue, content: ContentValue };
    // console.log(article);
  };

  const ratingHandler = (rate) => {
    setRating(rate);
  };

  const toggleHandler = (tog) => {
    setToggle(tog);
  };

  const setBook = (book) => {
    setSelectedBook(book);
  };

  return (
    <div>
      <Modal isOpen={!isSearched} ariaHideApp={false}>
        <SearchBook search={setIsSearched} setbook={setBook} />
      </Modal>
      {isSearched && (
        <RegisterForm
          toggleHandler={toggleHandler}
          onSubmitChangeBook={onSubmitChangeBook}
          onSubmitArticle={onSubmitArticle}
          tmpUrl={tmpUrl}
          selectedBook={selectedBook}
          ratingHandler={ratingHandler}
          onSentenceChange={onSentenceChange}
          oneSentence={oneSentence}
          onTitleChange={onTitleChange}
          TitleValue={TitleValue}
          onContentChange={onContentChange}
          ContentValue={ContentValue}
          ></RegisterForm>
        )
      }
      </div>
  );
}

export default BooklogRegisterContainer;

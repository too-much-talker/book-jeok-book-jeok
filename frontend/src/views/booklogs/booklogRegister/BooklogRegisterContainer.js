import React, { useState } from "react";
import SearchBook from "./SearchBook";
import Modal from "react-modal";
import axios from "axios";
import RegisterForm from "./BooklogRegisterPresenter";
import { useSelector } from "react-redux";
const url = "https://i6a305.p.ssafy.io:8443";

function BooklogRegisterContainer() {
  const [TitleValue, setTitleValue] = useState("");
  const [ContentValue, setContentValue] = useState("");
  const [isSearched, setIsSearched] = useState(false);
  const [rating, setRating] = useState(0);
  const [toggle, setToggle] = useState(false);
  const [oneSentence, setOneSentence] = useState("");
  const [selectedBook, setSelectedBook] = useState({});

  const onSentenceChange = (event) => {
    setOneSentence(event.target.value);
  };
  const onTitleChange = (event) => {
    setTitleValue(event.currentTarget.value);
  };

  const onContentChange = (event) => {
    setContentValue(event.currentTarget.value);
  };

  //토큰
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const user = useSelector((state) => state.authReducer);

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
        .post(
          url + `/api/v1/booklogs`,
          {
            memberSeq: user.memberInfo.seq,
            bookInfoSeq: selectedBook.seq,
            title: TitleValue,
            isOpen: !toggle,
            content: ContentValue,
            summary: oneSentence,
            starRating: rating,
            // readDate: new Date()
          },
          //토큰
          {
            headers: {
              Authorization: `Bearer ` + jwtToken,
            },
          }
        )
        .then(function (response) {
          console.log(response.status);
          if (response.status === 200) {
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
          bookImg={selectedBook.largeImgUrl}
          selectedBook={selectedBook}
          ratingHandler={ratingHandler}
          onSentenceChange={onSentenceChange}
          oneSentence={oneSentence}
          onTitleChange={onTitleChange}
          TitleValue={TitleValue}
          onContentChange={onContentChange}
          ContentValue={ContentValue}
        ></RegisterForm>
      )}
    </div>
  );
}

export default BooklogRegisterContainer;

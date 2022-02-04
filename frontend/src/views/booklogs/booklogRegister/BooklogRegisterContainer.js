import React, { useState } from "react";
import SearchBook from "./SearchBook";
import Modal from "react-modal";
import Toggle from "./Toggle";
import StarRating from "./StarRating";
// import { BOOKINFO } from "../dummydata";
import "./RegisterPage.css";
import axios from "axios";

const url = 'https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io';
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
  if(isSearched){
    tmpUrl = selectedBook.smallImgUrl.substring(0, selectedBook.smallImgUrl.length-5);
    tmpUrl = tmpUrl + 's.jpg';
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
    if (
        TitleValue === "" ||
        ContentValue === "" 
      ) {
        alert("입력하지 않은 정보가 있습니다. 확인해주세요.");
      } else {axios
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

  const setBook = (book) =>{
    setSelectedBook(book);
  };

  return (
    <div>
      <Modal isOpen={!isSearched} ariaHideApp={false}>
        <SearchBook search={setIsSearched} setbook={setBook}/>
      </Modal>
      {isSearched && (
        <div>
          <div>
            <Toggle toggle={toggleHandler} />
            <button
              onClick={onSubmitChangeBook}
              style={{ position: "absolute", right: 0, marginRight: "150px" }}
            >
              책 변경
            </button>
            <button
              onClick={onSubmitArticle}
              style={{ position: "absolute", right: 0, marginRight: "50px" }}
            >
              저장
            </button>
          </div>
          <div className="wrapper">
            <img className="img" src={tmpUrl}></img>
            <div className="info">
              <table style={{ align: "center" }}>
                  <tbody>
                  <tr>
                    <th> 제목 </th>
                    <td>{selectedBook.title}</td>
                  </tr>
                  <tr>
                    <th> 작가 </th>
                    <td>{selectedBook.author}</td>
                  </tr>
                  <tr>
                    <th> 출판사 </th>
                    <td>{selectedBook.publisher}</td>
                  </tr>
                  <tr>
                    <th> 출판일 </th>
                    <td>{selectedBook.publicationDate}</td>
                  </tr>
                  <tr>
                    <th> 별점 </th>
                    <td>
                      <StarRating rate={ratingHandler} />
                    </td>
                  </tr>
                  <tr>
                    <th> 한줄평 </th>
                    <td>
                      <input
                        type="text"
                        onChange={onSentenceChange}
                        value={oneSentence}
                        size="70"
                      ></input>
                    </td>
                  </tr>
                  </tbody>
              </table>
            </div>
          </div>
          <hr></hr>
          <form className="wrapper">
            <div>
              <input
                size="112"
                onChange={onTitleChange}
                value={TitleValue}
                type="text"
                name="title"
                placeholder="제목을 입력해주세요."
              />
              <br></br>
              <textarea
                style={{ resize: "none", height: "200px", width: "800px" }}
                onChange={onContentChange}
                value={ContentValue}
                name="content"
                placeholder="텍스트를 입력해주세요."
              />
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

export default BooklogRegisterContainer;

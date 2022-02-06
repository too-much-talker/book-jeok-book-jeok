import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import DetailForm from "./BooklogDetailPresenter";

const url = "https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io";

function BooklogDetailContainer(props) {
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  // const [selectedBookLog, setSelectedBookLog] = useState({});
  const [selectedBookInfo, setSelectedBookInfo] = useState({});
  const [enteredSummary, setEnteredSummary] = useState("");
  const [enteredTitle, setEnteredTitle] = useState("");
  const [enteredContent, setEnteredContent] = useState("");
  const [enteredToggle, setEnteredToggle] = useState(false);
  const location = useLocation();
  const bookLogSeq = location.state.logSeq;
  const bookInfoSeq = location.state.infoSeq;
  const [enteredRating, setEnteredRating] = useState(0);

  let bookLogData;
  let bookInfoData;
  const getBookLog = async () => {
    setIsLoading(true);
    const bookData1 = await axios.get(url + `/api/v1/booklogs/${bookLogSeq}`);
    const bookData2 = await axios.get(url + `/api/v1/bookinfos/${bookInfoSeq}`);
    console.log(bookData2);
    bookLogData = bookData1.data.data.booklog;
    bookInfoData = bookData2.data.data.bookInfo;
    setEnteredContent(bookLogData.content);
    setEnteredSummary(bookLogData.summary);
    setEnteredTitle(bookLogData.title);
    setEnteredRating(bookLogData.starRating);
    // setSelectedBookLog(bookLogData);
    setSelectedBookInfo(bookInfoData);
    setEnteredToggle(bookLogData.isOpen);
    setIsLoading(false);
  };

  useEffect(() => {
    getBookLog();
  }, []);

  const saveArticle = async (event) => {
    event.preventDefault();
    const response = await axios.put(url + `/api/v1/booklogs/${bookLogSeq}`, {
      memberSeq: 1,
      booklogSeq: bookInfoSeq,
      bookInfoSeq: bookInfoSeq,
      title: enteredTitle,
      isOpen: !enteredToggle,
      content: enteredContent,
      summary: enteredSummary,
      starRating: enteredRating,
      readDate: new Date()
    });
    setIsEditing(!isEditing);
    alert(response.data.data.msg);
  };

  const onDeleteArticle = async (event) => {
    event.preventDefault();
    const response = await axios.delete(url + `/api/v1/booklogs/${bookLogSeq}`);
    window.location.replace("/mypage/mybooklog");
    alert(response.data.data.msg);
  };
  const editButtonHandler = () => {
    setIsEditing(!isEditing);
  };
  const summaryHandler = (event) => {
    setEnteredSummary(event.target.value);
  };
  const titleHandler = (event) => {
    setEnteredTitle(event.target.value);
  };
  const contentHandler = (event) => {
    setEnteredContent(event.target.value);
  };
  const toggleHandler = (tog) => {
    setEnteredToggle(tog);
  };
  const ratingHandler = (rate) => {
    setEnteredRating(rate);
  };
  return (
    <div>
      {isLoading ? (
        <p>로딩중..</p>
      ) : (
        <DetailForm
          isEditing={isEditing}
          isOpen={enteredToggle}
          toggleHandler={toggleHandler}
          saveArticle={saveArticle}
          editButtonHandler={editButtonHandler}
          onDeleteArticle={onDeleteArticle}
          selectedBookInfo={selectedBookInfo}
          enteredRating={enteredRating}
          ratingHandler={ratingHandler}
          summaryHandler={summaryHandler}
          enteredSummary={enteredSummary}
          titleHandler={titleHandler}
          enteredTitle={enteredTitle}
          contentHandler={contentHandler}
          enteredContent={enteredContent}
        ></DetailForm>
      )}
    </div>
  );
}

export default BooklogDetailContainer;

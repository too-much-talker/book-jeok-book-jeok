import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import DetailForm from "./BooklogDetailPresenter";
import { useSelector } from "react-redux";

const url = "https://i6a305.p.ssafy.io:8443";

function BooklogDetailContainer(props) {
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  // const [selectedBookLog, setSelectedBookLog] = useState({});
  const [selectedBookInfo, setSelectedBookInfo] = useState({});
  const [enteredSummary, setEnteredSummary] = useState("");
  const [enteredTitle, setEnteredTitle] = useState("");
  const [enteredContent, setEnteredContent] = useState("");
  const [enteredToggle, setEnteredToggle] = useState(false);
  const [bookInfoSeq, setBookInfoSeq] = useState(0);
  const location = useLocation();
  const bookLogSeq = location.state.logSeq;
  const [enteredRating, setEnteredRating] = useState(0);

  let bookLogData,bookInfoData,tmpSeq;
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const user = useSelector((state) => state.authReducer);

  const getBookLog = async () => {
    setIsLoading(true);
    const bookData1 = await axios.get(url + `/api/v1/booklogs/${bookLogSeq}`, {
      headers: {
        Authorization: `Bearer ` + jwtToken,
      },
    });
    tmpSeq = bookData1.data.data.booklog.bookInfoSeq;
    setBookInfoSeq(bookData1.data.data.booklog.bookInfoSeq);
    const bookData2 = await axios.get(url + `/api/v1/bookinfos/${tmpSeq}`);
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

  //토큰

  const saveArticle = async (event) => {
    event.preventDefault();
    console.log(bookLogSeq);
    console.log(bookInfoSeq);
    const response = await axios.put(
      url + `/api/v1/booklogs/${bookLogSeq}`,
      {
        memberSeq: user.memberInfo.seq,
        booklogSeq: bookLogSeq,
        bookInfoSeq: bookInfoSeq,
        title: enteredTitle,
        isOpen: !enteredToggle,
        content: enteredContent,
        summary: enteredSummary,
        starRating: enteredRating,
      },
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    console.log(response);
    setIsEditing(!isEditing);
    alert(response.data.data.msg);
  };

  const onDeleteArticle = async (event) => {
    event.preventDefault();
    axios
      .delete(
        url + `/api/v1/booklogs/${bookLogSeq}`,
        {
          headers: {
            Authorization: "Bearer " + jwtToken,
          },
        }
      )
      .then(function (response) {
        console.log(response.status);
        if (response.status === 200) {
          alert(response.data.data.msg);
          window.location.replace("/mypage/mybooklog");
        } else {
          alert(response.data.data.msg);
        }
      });
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

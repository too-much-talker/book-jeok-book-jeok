import React, { useEffect, useState } from "react";
import Toggle from "../booklogRegister/Toggle";
import StarRating from "../booklogRegister/StarRating";
import "./DetailPage.css";
import axios from "axios";
import styled from "styled-components";
// import Spinner from "./Spinner";
import { useLocation } from "react-router-dom";
import DetailForm from "./BooklogDetailPresenter";

const url = "https://77e1dca6-cd01-4930-ae25-870e7444cc55.mock.pstmn.io";

function BooklogDetailContainer (props) {
  const [isEditing, setIsEditing] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedBookLog, setSelectedBookLog] = useState({});
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
    setSelectedBookLog(bookLogData);
    setSelectedBookInfo(bookInfoData);
    // setEnteredToggle(bookLogData.isOpen);
    setIsLoading(false);
  };
  const { title, content, summary, starRating, isOpen, createdDate } =
    selectedBookLog;
  useEffect(() => {
    getBookLog();
  }, []);

  const saveArticle = async (event) => {
    event.preventDefault();
    const response = await axios.put(url + `/api/v1/booklogs/${bookLogSeq}`,{
      memberSeq: 1,
      booklogSeq: bookInfoSeq,
      bookInfoSeq: bookInfoSeq,
      title: enteredTitle,
      isOpen: !enteredToggle,
      content: enteredContent,
      summary: enteredSummary,
      starRating: enteredRating,
      readDate: new Date(),
    });
    console.log(isOpen);
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
  }
//   const IsOpenCircle = styled.div`
//     position: absolute;
//     top: 30px;
//     left: 100px;
//     width: 22px;
//     height: 22px;
//     border-radius: 50%;
//     background-color: black;
//   `;
//   const Desc = styled.div`
//     padding-left: 50px;
//   `;
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
            isOpen={isOpen}
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
            enteredContent={enteredContent}>
            </DetailForm>
        )}
    </div>
    );
}
        

        // <div>
        //   <div>
        //     <br />
        //     {!isEditing ? (
        //       <>
        //       <Desc> {isOpen ? "공개" : "비공개"} </Desc>
        //       <IsOpenCircle
        //         style={isOpen ? { background: "blue" } : { background: "red" }}
        //       ></IsOpenCircle>
        //       </>
        //     ):(
        //       <Toggle toggle={toggleHandler} />
        //     )}
        //     {isEditing ? (
        //       <div>
        //         <button
        //           onClick={saveArticle}
        //           style={{
        //             position: "absolute",
        //             right: 0,
        //             marginRight: "150px",
        //           }}
        //         >
        //           저장
        //         </button>
        //       </div>
        //     ) : (
        //       <div>
        //         <button
        //           onClick={editButtonHandler}
        //           style={{
        //             position: "absolute",
        //             right: 0,
        //             marginRight: "150px",
        //           }}
        //         >
        //           수정
        //         </button>

        //         <button
        //           onClick={onDeleteArticle}
        //           style={{
        //             position: "absolute",
        //             right: 0,
        //             marginRight: "50px",
        //           }}
        //         >
        //           삭제
        //         </button>
        //       </div>
        //     )}
        //   </div>
        //   <div className="wrapper">
        //     <img className="img" src={selectedBookInfo.largeImgUrl}></img>
        //     <div className="info">
        //       <table style={{ align: "center" }}>
        //         <tbody>
        //           <tr>
        //             <th> 제목 </th>
        //             <td>{selectedBookInfo.title}</td>
        //           </tr>
        //           <tr>
        //             <th> 작가 </th>
        //             <td>{selectedBookInfo.author}</td>
        //           </tr>
        //           <tr>
        //             <th> 출판사 </th>
        //             <td>{selectedBookInfo.publisher}</td>
        //           </tr>
        //           <tr>
        //             <th> 출판일 </th>
        //             <td>{selectedBookInfo.publicationDate}</td>
        //           </tr>
        //           <tr>
        //             <th> 별점 </th>
        //             {!isEditing ? (
        //               <td>{enteredRating}점</td>
        //               ) : (
        //               <td><StarRating rate={ratingHandler} /></td>
        //             )}
        //           </tr>
        //           <tr>
        //             <th> 한줄평 </th>
        //             {isEditing ? (
        //               <input onChange={summaryHandler} type="text" value={enteredSummary}/>
        //             ) : (
        //               <td>{enteredSummary}</td>
        //             )}
        //           </tr>
        //         </tbody>
        //       </table>
        //     </div>
        //   </div>
        //   <hr></hr>
        //   <form className="wrapper2">
        //     <br />
        //     <div>
        //       <h5>제목</h5>
        //       <div>
        //         {isEditing ? (
        //           <input
        //             size="112"
        //             onChange={titleHandler}
        //             value={enteredTitle}
        //             type="text"
        //             name="title"
        //           />
        //         ) : (
        //           <p>{enteredTitle}</p>
        //         )}
        //       </div>
        //       <h5>내용</h5>
        //       {isEditing ? (
        //         <textarea
        //           onChange={contentHandler}
        //           value={enteredContent}
        //           type="text"
        //           name="title"
        //           style={{ resize: "none", height: "200px", width: "800px" }}
        //         />
        //       ) : (
        //         <p>{enteredContent}</p>
        //       )}
        //     </div>
        //   </form>
        // </div>

export default BooklogDetailContainer;

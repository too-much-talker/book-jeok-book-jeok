import React, { useEffect, useState } from "react";
import styled from "styled-components";
import Toggle from "../booklogRegister/Toggle";
import StarRating from "../booklogRegister/StarRating";

function DetailForm({
    isEditing,
    isOpen,
    toggleHandler,
    saveArticle,
    editButtonHandler,
    onDeleteArticle,
    selectedBookInfo,
    enteredRating,
    ratingHandler,
    summaryHandler,
    enteredSummary,
    titleHandler,
    enteredTitle,
    contentHandler,
    enteredContent
})
{
    const Desc = styled.div`
    padding-left: 50px;
    `;
    const IsOpenCircle = styled.div`
    position: absolute;
    top: 30px;
    left: 100px;
    width: 22px;
    height: 22px;
    border-radius: 50%;
    background-color: black;
  `;
    return(
        <div>
          <div>
            <br />
            {!isEditing ? (
              <>
              <Desc> {isOpen ? "공개" : "비공개"} </Desc>
              <IsOpenCircle
                style={isOpen ? { background: "blue" } : { background: "red" }}
              ></IsOpenCircle>
              </>
            ):(
              <Toggle toggle={toggleHandler} />
            )}
            {isEditing ? (
              <div>
                <button
                  onClick={saveArticle}
                  style={{
                    position: "absolute",
                    right: 0,
                    marginRight: "150px",
                  }}
                >
                  저장
                </button>
              </div>
            ) : (
              <div>
                <button
                  onClick={editButtonHandler}
                  style={{
                    position: "absolute",
                    right: 0,
                    marginRight: "150px",
                  }}
                >
                  수정
                </button>

                <button
                  onClick={onDeleteArticle}
                  style={{
                    position: "absolute",
                    right: 0,
                    marginRight: "50px",
                  }}
                >
                  삭제
                </button>
              </div>
            )}
          </div>
          <div className="wrapper">
            <img className="img" src={selectedBookInfo.largeImgUrl}></img>
            <div className="info">
              <table style={{ align: "center" }}>
                <tbody>
                  <tr>
                    <th> 제목 </th>
                    <td>{selectedBookInfo.title}</td>
                  </tr>
                  <tr>
                    <th> 작가 </th>
                    <td>{selectedBookInfo.author}</td>
                  </tr>
                  <tr>
                    <th> 출판사 </th>
                    <td>{selectedBookInfo.publisher}</td>
                  </tr>
                  <tr>
                    <th> 출판일 </th>
                    <td>{selectedBookInfo.publicationDate}</td>
                  </tr>
                  <tr>
                    <th> 별점 </th>
                    {!isEditing ? (
                      <td>{enteredRating}점</td>
                      ) : (
                      <td><StarRating rate={ratingHandler} /></td>
                    )}
                  </tr>
                  <tr>
                    <th> 한줄평 </th>
                    {isEditing ? (
                      <input onChange={summaryHandler} type="text" value={enteredSummary}/>
                    ) : (
                      <td>{enteredSummary}</td>
                    )}
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <hr></hr>
          <form className="wrapper2">
            <br />
            <div>
              <h5>제목</h5>
              <div>
                {isEditing ? (
                  <input
                    size="112"
                    onChange={titleHandler}
                    value={enteredTitle}
                    type="text"
                    name="title"
                  />
                ) : (
                  <p>{enteredTitle}</p>
                )}
              </div>
              <h5>내용</h5>
              {isEditing ? (
                <textarea
                  onChange={contentHandler}
                  value={enteredContent}
                  type="text"
                  name="title"
                  style={{ resize: "none", height: "200px", width: "800px" }}
                />
              ) : (
                <p>{enteredContent}</p>
              )}
            </div>
          </form>
        </div>
    );
} 
export default DetailForm;
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
}) {
  const UtilButton = styled.div`
    display: flex;
    justify-content: space-between;
    button {
      margin-left: 1rem;
    }
  `;
  const Wrapper = styled.div`
    text-align: center;
    justify-content: center;
    align-items: center;
    font-size: 1.5rem;
    .summary {
      margin-bottom: 0;
    }
  `;

  const Wrapper2 = styled.div`
    text-align: left;
    padding-left: 20rem;
  `;

  const Info = styled.div`
    display: inline-block;
    padding-left: 5rem;
  `;

  const Table = styled.div`
    align: center;
  `;

  const Desc = styled.div`
    text-align: left;
    font-size: 1.5rem;
  `;

  const IsOpenCircle = styled.div`
    width: 22px;
    height: 22px;
    border-radius: 50%;
    background-color: black;
  `;

  const Circle = styled.div`
    display: inline-block;
  `;

  return (
    <div>
      <div>
        <br />
        <UtilButton>
          <Circle>
            {!isEditing ? (
              <div>
                <Desc> {isOpen ? "공개" : "비공개"} </Desc>
                <IsOpenCircle
                  style={
                    isOpen ? { background: "blue" } : { background: "red" }
                  }
                ></IsOpenCircle>
              </div>
            ) : (
              <Toggle toggle={toggleHandler} />
            )}
          </Circle>

          {isEditing ? (
            <div>
              <button onClick={saveArticle}>저장</button>
            </div>
          ) : (
            <div>
              <button onClick={editButtonHandler}>수정</button>
              <button onClick={onDeleteArticle}>삭제</button>
            </div>
          )}
        </UtilButton>
      </div>
      <Wrapper>
        <img
          src={selectedBookInfo.largeImgUrl}
          alt={selectedBookInfo.title}
        ></img>
        <Info>
          <Table>
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
                  <td>
                    <StarRating rate={ratingHandler} />
                  </td>
                )}
              </tr>
              <tr>
                <th> 한줄평 </th>
                {isEditing ? (
                  <td>
                    <input
                      onChange={summaryHandler}
                      value={enteredSummary}
                      className="summary"
                      size="30"
                    />
                  </td>
                ) : (
                  <td>{enteredSummary}</td>
                )}
              </tr>
            </tbody>
          </Table>
        </Info>
      </Wrapper>
      <hr></hr>
      <Wrapper2>
        <div>
          <h5>제목</h5>
          <div>
            {isEditing ? (
              <input
                size="107"
                onChange={titleHandler}
                value={enteredTitle}
                type="text"
                name="title"
              />
            ) : (
              <p>{enteredTitle}</p>
            )}
          </div>
          <br></br>
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
      </Wrapper2>
    </div>
  );
}
export default DetailForm;

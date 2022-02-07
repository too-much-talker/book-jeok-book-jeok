import Toggle from "./Toggle";
import StarRating from "./StarRating";
import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  .summary {
    margin-bottom: 0;
  }
`;

const Info = styled.div`
  display: inline-block;
  padding-left: 2rem;
`;

const Table = styled.div`
  align: "center";
`;

const UtilButton = styled.div`
  display: flex;
  justify-content: space-between;
  button {
    margin-left: 1rem;
  }
`;

function RegisterForm({
  toggleHandler,
  onSubmitChangeBook,
  onSubmitArticle,
  bookImg,
  selectedBook,
  ratingHandler,
  onSentenceChange,
  oneSentence,
  onTitleChange,
  TitleValue,
  onContentChange,
  ContentValue
}) {
  return (
    <div>
      <div>
        <UtilButton>
          <Toggle toggle={toggleHandler} />
          <div>
            <button onClick={onSubmitChangeBook}>책 변경</button>
            <button onClick={onSubmitArticle}>저장</button>
          </div>
        </UtilButton>
      </div>
      <Wrapper>
        <img src={bookImg} alt={selectedBook.title}></img>
        <Info>
          <Table>
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
                    // type="text"
                    className="summary"
                    onChange={onSentenceChange}
                    value={oneSentence}
                    size="40"
                  ></input>
                </td>
              </tr>
            </tbody>
          </Table>
        </Info>
      </Wrapper>
      <hr></hr>
      <Wrapper>
        <div>
          <input
            size="116"
            onChange={onTitleChange}
            value={TitleValue}
            type="text"
            name="title"
            placeholder="제목을 입력해주세요."
          />
          <br></br>
          <textarea
            style={{ resize: "none", height: "300px", width: "865px" }}
            onChange={onContentChange}
            value={ContentValue}
            name="content"
            placeholder="텍스트를 입력해주세요."
          />
        </div>
      </Wrapper>
    </div>
  );
}

export default RegisterForm;

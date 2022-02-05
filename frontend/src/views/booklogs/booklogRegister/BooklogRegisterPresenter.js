import Toggle from "./Toggle";
import StarRating from "./StarRating";
import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
`;

const Info = styled.div`
  display: inline-block;
  padding-left: 2rem;
`;

const Table = styled.div`
  align: "center";
`;


function RegisterForm({
  toggleHandler,
  onSubmitChangeBook,
  onSubmitArticle,
  tmpUrl,
  selectedBook,
  ratingHandler,
  onSentenceChange,
  oneSentence,
  onTitleChange,
  TitleValue,
  onContentChange,
  ContentValue,
}) {
  return (
    <div>
      <div>
        <Toggle toggle={toggleHandler} />
        <button
          onClick={onSubmitChangeBook}
          style={{ position: "absolute", right: 0, marginRight: "280px" }}
        >
          책 변경
        </button>
        <button
          onClick={onSubmitArticle}
          style={{ position: "absolute", right: 0, marginRight: "170px" }}
        >
          저장
        </button>
      </div>
      <Wrapper>
        <img src={tmpUrl}></img>
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

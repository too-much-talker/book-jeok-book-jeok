import React, { useState } from "react";
import ReactModal from "react-modal";
import Comment from "./Comment";
import styled from "styled-components";
import Pagination from "./Pagination";
const Block = styled.div`
  margin-left: 3%;
`;
const Head = styled.div`
  position: relative;
  display: flex;
  margin-top: 2%;
  margin-bottom: %;
`;
const Title = styled.div`
  position: relative;
  font-size: 20px;
  font-weight: bold;
  width: 95%;
  text-align: left;
  margin-left: 1%;
  margin-bottom: 1%;
`;

const WriteBlock = styled.div`
  position: relative;
  height: 15%;
  width: 100%;
  display: flex;
`;
const WriteInput = styled.textarea`
  position: relative;
  border: 1px solid #e5e5e5;
  width: 85%;
  height: 100%;
  resize: none;
  margin-right: 10px;
  margin-left: 1%;
`;
const WriteBtn = styled.button`
  position: relative;
  height: 30px;
  width: 7%;
  border-radius: 10px;
  border: 1px solid black;
  background: black;
  color: white;
`;
const CommentList = styled.div`
  height: 75%;
  margin-top: 3%;
`;
const Comments = styled.div``;

const CommentPresenter = (props) => {
  const {
    paginate,
    totalCnt,
    currentComments,
    content,
    handleContent,
    register,
  } = props;

  return (
    <Block>
      <Head>
        <Title>댓글</Title>
      </Head>
      <WriteBlock>
        <WriteInput value={content} onChange={handleContent}></WriteInput>
        <WriteBtn onClick={register}>등록</WriteBtn>
      </WriteBlock>
      <Comments>
        <CommentList>
          {currentComments &&
            currentComments.map((comment) => (
              <Comment
                nickname={comment.memberNickname}
                createdDate={comment.modifiedDate}
                content={comment.content}
              ></Comment>
            ))}
        </CommentList>
        <Pagination postPerPage={5} totalPosts={totalCnt} paginate={paginate} />
      </Comments>
    </Block>
  );
};

export default CommentPresenter;

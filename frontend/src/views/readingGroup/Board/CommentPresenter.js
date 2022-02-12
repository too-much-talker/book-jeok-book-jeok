import React, { useState } from "react";
import ReactModal from "react-modal";

import styled from "styled-components";
const Head = styled.div`
  display: flex;
  height: 6%;
`;
const Title = styled.div`
  position: relative;
  font-size: 20px;
  font-weight: bold;
  width: 95%;
  text-align: left;
  margin-left: 1%;
`;
const ExitBtn = styled.div`
  position: relative;
  border: 1px solid black;
  width: 4%;
  height: 70%;
  border-radius: 20px;
`;

const WriteBlock = styled.div`
  position: relative;
  height: 15%;
  width: 100%;
  display: flex;
`;
const WriteInput = styled.input`
  position: relative;
  border: 1px solid black;
  width: 90%;
  height: 100%;
  margin-right: 10px;
`;
const BtnBlock = styled.div`
  height: 100%;
  width: 8%;
  position: relative;
`;
const Blank = styled.div`
  position: relative;
  height: 70%;
`;

const WriteBtn = styled.button`
  position: relative;
  height: 40%;
  width: 100%;
  border-radius: 10px;
  border: 1px solid black;
  background: black;
  color: white;
`;

const CommentList = styled.div`
  height: 75%;
  margin-top: 3%;
`;

const CommentPresenter = (props) => {
  const { isOpen, onCancel, register } = props;

  const handleClose = () => {
    onCancel();
  };

  return (
    <>
      <ReactModal
        isOpen={isOpen}
        style={{
          overlay: {
            position: "fixed",
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: "rgba(255, 255, 255, 0.75)",
          },
          content: {
            width: "50%",
            height: "80%",
            position: "relative",
            top: "50%",
            left: " 50%",
            transform: "translate(-50%, -50%)",
            border: "1px solid #eee",
            borderRadius: "15px",
            background: "#fff",
            overflow: "auto",
            WebkitOverflowScrolling: "touch",
            outline: "none",
            padding: "20px",
            textAlign: "center",
          },
        }}
      >
        <Head>
          <Title>댓글</Title>
          <ExitBtn onClick={handleClose}>X</ExitBtn>
        </Head>
        <WriteBlock>
          <WriteInput></WriteInput>
          <BtnBlock>
            <Blank></Blank>
            <WriteBtn onClick={register}>등록</WriteBtn>
          </BtnBlock>
        </WriteBlock>
        <CommentList></CommentList>
      </ReactModal>
    </>
  );
};

export default CommentPresenter;

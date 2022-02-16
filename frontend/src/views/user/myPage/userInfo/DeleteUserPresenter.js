import React, { useState, useEffect } from "react";
import styled from "styled-components";
import ReactModal from "react-modal";
const Head = styled.div`
  display: flex;
  margin-bottom: 30px;
`;
const Message = styled.div`
  margin-left: 10%;
  width: 70%;
  margin-right: 12%;
`;
const ExitBtn = styled.div`
  align: right;
  border: 1px solid black;
  height: 20px;
  width: 35px;
  border-radius: 20px;
  font-size: 13px;
`;
const Content = styled.div``;
const Block = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  margin-bottom: 10px;
`;
const Word = styled.div`
  width: 20%;
  margin-left: 2%;
`;
const Input = styled.input`
  width: 70%;
  margin-bottom: 2%;
`;
const Btn = styled.button``;
function DeleteUserPresenter({
  isOpen,
  onCancel,
  handleEmail,
  handlePassword,
  handleConfirmPassword,
  onsubmit,
}) {
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
            width: "40%",
            height: "30%",
            position: "relative",
            top: "40%",
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
          <Message>
            탈퇴하시려면 이메일과 비밀번호를 한번 더 입력해주세요.
          </Message>
          <ExitBtn onClick={handleClose}>X</ExitBtn>
        </Head>
        <Content>
          <Block>
            <Word>이메일</Word>
            <Input onChange={handleEmail} size={50}></Input>
          </Block>

          <Block>
            <Word>비밀번호</Word>
            <Input onChange={handlePassword} size={50}></Input>
          </Block>
          <Block>
            <Word>비밀번호 확인</Word>
            <Input onChange={handleConfirmPassword} size={50}></Input>
          </Block>
        </Content>
        <Btn onClick={onsubmit}>탈퇴하기</Btn>
      </ReactModal>
    </>
  );
}
export default DeleteUserPresenter;

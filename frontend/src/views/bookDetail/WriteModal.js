import React, { useState } from "react";
import ReactModal from "react-modal";


import styled from "styled-components";

const Btn = styled.button`
height:10px;
width:10px;
`;
const WriteModal = (props) => {
  const {  isOpen, onCancel } = props;

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
            width: "340px",
            height: "400px",
            position: "absolute",
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

          <Btn onClick={handleClose}>완료</Btn>
          <Btn onClick={handleClose}>닫기</Btn>
      </ReactModal>
    </>
  );
};

export default WriteModal;
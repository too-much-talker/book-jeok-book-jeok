import React, { useState } from "react";
import ReactModal from "react-modal";

import styled from "styled-components";

const Header = styled.div`
position:relaive;
display:flex;

`;
const ExitBtn= styled.div`
position:relative;
right:3%;
border:1px solid black;
width:4%;
height:4%;
border-radius:20px;
`;
const Notice= styled.div`
position:relative;
margin:auto;
`;
const Contents= styled.div`
position:relative;
`;
const Content= styled.input`

`;
const Buttons = styled.div`
display:flex;
position:relative;
justify-content: center;
`;
const Btn = styled.div`
    position:relative;
    border:1px solid black;
width:10%;
height:10%;
border-radius:100px;
`;
const MyModalPresenter = (props) => {
  const {modifyReview,handleStarRating,handleSummary, handleCreatedDate,deleteReview,starRating, summary,createdDate,isOpen, onCancel } = props;

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
      <Header>
        <Notice>내용을 수정한 후 완료를 누르면 수정된 내용이 책 리뷰에 반영됩니다.</Notice>
        <ExitBtn onClick={handleClose}>X</ExitBtn>
      </Header>
         
          <Contents>
              <Content onChange={handleStarRating} value={starRating}></Content>
              <Content onChange={handleSummary} value={summary}></Content>
              <Content onChange={handleCreatedDate} value={createdDate}></Content>
          </Contents>
        <Buttons>
            <Btn onClick={modifyReview}>수정</Btn>
            <Btn onClick={deleteReview}>삭제</Btn>
        </Buttons>

      </ReactModal>
    </>
  );
};

export default MyModalPresenter;
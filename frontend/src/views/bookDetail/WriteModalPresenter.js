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

const Contents= styled.div`
position:relative;
display:flex;
`;
const Star= styled.div`
`;
const Summary= styled.div`
`;
const Content= styled.input`
position:relative;
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
const WriteModalPresenter = (props) => {
  const {RegisterReview,handleStarRating,handleSummary, handleCreatedDate,deleteReview,starRating, summary,createdDate,isOpen, onCancel } = props;

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
        <ExitBtn onClick={handleClose}>X</ExitBtn>
      </Header>
          <Contents>
              <Star>
                  평점을 체크해주세요
                <Content onChange={handleStarRating}></Content>
              </Star>
              <Summary>
                  한줄평을 작성해주세요
                  <Content onChange={handleSummary}></Content>
            </Summary>
              
          </Contents>
        <Buttons>
            <Btn onClick={RegisterReview}>등록하기</Btn>
        </Buttons>
      </ReactModal>
    </>
  );
};

export default WriteModalPresenter;
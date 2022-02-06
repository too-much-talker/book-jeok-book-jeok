import React, { useState } from "react";
import ReactModal from "react-modal";
import StarRating from "../booklogs/booklogRegister/StarRating";
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
font-size:20px;
`;
const Contents= styled.div`
position:relative;
justify-content: center;
background:red;
 align-items: center;
`;
const Star= styled.div`
position:relative;
display:flex;
text-align:center;
`;
const Summary= styled.div`
position:relative;
display:flex;
`;
const Content= styled.input`
position:relative;
size:200px;
`;
const Words= styled.div`
font-size:15px;`;

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
  const {ratingHandler,RegisterReview,handleStarRating,handleSummary, handleCreatedDate,deleteReview,starRating, summary,createdDate,isOpen, onCancel } = props;

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
        <Notice>책리뷰를 작성해주세요 !</Notice>
        <ExitBtn onClick={handleClose}>X</ExitBtn>
      </Header>
          <Contents>
              <Star>
                  <Words>평점을 체크해주세요</Words>
                  <StarRating rate={ratingHandler} />
              </Star>
              <Summary>
                  <Words>한줄평을 작성해주세요</Words>
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
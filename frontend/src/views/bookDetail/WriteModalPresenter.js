import React, { useState } from "react";
import ReactModal from "react-modal";
import StarRating from "../booklogs/booklogRegister/StarRating";
import styled from "styled-components";

const Header = styled.div`
position:relative;
display:flex;
height:35%;
`;
const ExitBtn= styled.div`
position:relative;
border:1px solid black;
width:5%;
height:30%;
border-radius:20px;
`;
const Notice= styled.div`
position:relative;
margin:auto;
font-size:20px;
`;

const Contents= styled.div`
position:relative; 
text-align: center;
align-items : center // 세로 중앙 정렬

`;
const Star= styled.div`
margin: 0 auto;
display:flex;
justify-content:center;
`;
const Summary= styled.div`
margin: 0 auto;
display:flex;
justify-content:center;
`;
const Content= styled.input`
position:relative;
width:40%;
`;

const Words= styled.div`
position:relative;
font-size:15px;
margin-right:10px;
margin-bottom:10px;
`;
const STAR= styled.div`
position:relative;
text-align:left;
`;
const Buttons = styled.div`
display:flex;
position:relative;
justify-content: center;
height:30%;
`;
const Btn = styled.div`
margin-top:3%;
    position:relative;
    border:1px solid black;
width:15%;
height:40%;
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
                  <STAR>
                    <StarRating rate={ratingHandler} />
                  </STAR>
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
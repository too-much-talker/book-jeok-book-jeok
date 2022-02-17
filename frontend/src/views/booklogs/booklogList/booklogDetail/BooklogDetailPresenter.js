import React, { useEffect, useState } from "react";
import styled from "styled-components";
import BooklogDetailBookInfo from "./BooklogDetailBookInfo";
import heart from "../../../../res/img/heart_fill.png";
import heartEmpty from "../../../../res/img/heart_empty.png";
import view from "../../../../res/img/view.png";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  .summary {
    margin-bottom: 0;
  }
  margin-bottom: 4%;
`;

const Wrapper2 = styled.div`
  text-align: left;
`;

const BooklogInfo = styled.div`
  margin-left: 60px;
  display: flex;
`;

const Writer = styled.span`
  float: right;
  margin-left: 380px;
`;

const Like = styled.span`
  margin-right: 1.5rem;
  text-align: left;
  font-size: 1.1rem;
`;

const Icon = styled.img`
  width: 1.2rem;
  text-align: left;
  margin-right: 0.3rem;
`;

const Heart = styled(Icon)`
  cursor: pointer;
`;

const UtilButton = styled.div`
  display: flex;
  justify-content: space-between;
  button {
    margin-left: 1rem;
  }
`;

const Box = styled.div`
  width: 80%;
  margin: 0 auto;
`;

const Line = styled.div`
  width: 90%;
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 1%;
  margin-bottom: 1%;
  border-bottom: 1px solid #595959;
`;

function DetailForm({ isLike, likes, onClickHeart, booklog }) {
  return (
    <Box>
      <div>
        <br />
        {/* <UtilButton>
          <BooklogDetailHeader
            isEditing={isEditing}
            isOpen={isOpen}
            toggleHandler={toggleHandler}
            saveArticle={saveArticle}
            editButtonHandler={editButtonHandler}
          />
        </UtilButton> */}
      </div>
      <Wrapper>
        <BooklogDetailBookInfo booklog={booklog} />
      </Wrapper>
      <Line></Line>
      <Wrapper2>
        <BooklogInfo>
          <span style={{ marginRight: "20px", marginLeft: "20px" }}>
            {booklog.createdDate}
          </span>

          <div>
            <span>
              <Like>
                {isLike ? (
                  <Heart onClick={onClickHeart} src={heart} />
                ) : (
                  <Heart onClick={onClickHeart} src={heartEmpty} />
                )}{" "}
                {likes}
              </Like>
            </span>
            <Like>
              <Icon width="10px" height="20px" src={view} />
              {booklog.views + 1}
            </Like>
          </div>
          <Writer>작성자 : {booklog.nickname}</Writer>
        </BooklogInfo>
        <Line></Line>
        <div style={{ marginLeft: "80px" }}>
          <h3>{booklog.title}</h3>

          <p>{booklog.content}</p>
        </div>
      </Wrapper2>
    </Box>
  );
}
export default DetailForm;

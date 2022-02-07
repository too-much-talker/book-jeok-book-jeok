import React, { useEffect, useState } from "react";
import styled from "styled-components";
import BooklogDetailBookInfo from "./BooklogDetailBookInfo";
import heart from "../../../../res/img/heart_empty.png";
import view from "../../../../res/img/view.png";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  .summary {
    margin-bottom: 0;
  }
`;

const Wrapper2 = styled.div`
  text-align: left;
`;

const BooklogInfo = styled.div`
  width: 100%;
`;

const Writer = styled.span`
  float: right;
`;

const Like = styled.span`
  margin-right: 1rem;
  text-align: left;
`;

const Icon = styled.img`
  width: 1.5rem;
  text-align: left;
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

function DetailForm({ booklog }) {
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
      <Wrapper>{/* <BooklogDetailBookInfo booklog={booklog} /> */}</Wrapper>
      <hr></hr>
      <Wrapper2>
        <BooklogInfo>
          <span>{booklog.createdDate}</span>
          <Writer>bjbj</Writer>
          <div>
            <span>
              <Like>
                <Icon src={heart} /> {booklog.likes}
              </Like>
            </span>
            <span>
              <Icon src={view} />
              views
            </span>
          </div>
        </BooklogInfo>
        <hr />
        <div>
          <h5>제목</h5>
          <div>
            <p>{booklog.title}</p>
          </div>
          <br></br>
          <h5>내용</h5>
          <p>{booklog.content}</p>
        </div>
      </Wrapper2>
    </Box>
  );
}
export default DetailForm;

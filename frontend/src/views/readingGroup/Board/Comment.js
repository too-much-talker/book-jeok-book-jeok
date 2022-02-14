import React from "react";
import styled from "styled-components";
const CommentBlock = styled.div`
  margin-bottom: 10px;
  text-align: left;
  padding-left: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f2f2f2;
`;
const Nickname = styled.div`
  font-size: 13px;
  font-weight: bolder;
`;
const CreatedDate = styled.div`
  color: #a2a2a2;
  font-size: 13px;
`;
const Content = styled.div`
  font-size: 13px;
  margin-bottom: 8px;
`;
function Comment({ nickname, createdDate, content }) {
  return (
    <>
      <CommentBlock>
        <Nickname>{nickname}</Nickname>
        <Content>{content}</Content>
        <CreatedDate>{createdDate}</CreatedDate>
      </CommentBlock>
    </>
  );
}
export default Comment;

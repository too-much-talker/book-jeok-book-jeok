import React, { useState, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";
import { useParams } from "react-router-dom";
const CommentBlock = styled.div`
  margin-bottom: 10px;
  text-align: left;
  padding-left: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f2f2f2;
  display: flex;
`;
const Nickname = styled.div`
  font-size: 13px;
  font-weight: bolder;
`;
const NicknameInput = styled.input``;

const CreatedDate = styled.div`
  color: #a2a2a2;
  font-size: 13px;
`;
const Content = styled.div`
  font-size: 13px;
  margin-bottom: 8px;
  width: 90%;
`;
const ContentInput = styled.input``;
const Block = styled.div`
  display: flex;
`;
const SubmitBtn = styled.button`
  border: none;
  font-size: 12px;
  margin-left: 10px;
  color: #5e5e5e;
`;
const More = styled.div`
  width: 11%;
  height: 25px;
  text-align: left;
  display: flex;
`;
const Btn = styled.button`
  border: none;
  border-radius: 10px;
  width: 90%;
  margin-right: 3px;
  font-size: 12px;
  color: #707070;
`;

function Comment({
  url,
  goModify,
  goDelete,
  user,
  nickname,
  createdDate,
  content,
  commentSeq,
  getComment,
}) {
  const [status, setStatus] = useState("detail");
  const [myContent, setMyContent] = useState();

  let useParam = useParams();
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  useEffect(() => {
    setMyContent(content);
  }, [status]);

  function handleContent(event) {
    setMyContent(event.target.value);
  }

  function goModify() {
    console.log(commentSeq);
    axios
      .put(
        url + `/api/v1/reading-groups/comments/${commentSeq}`,
        {
          readingGroupArticleSeq: useParam.meetingSeq,
          content: myContent,
        },
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        {
          getComment();
        }
        setStatus("detail");
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goDelete() {
    axios
      .delete(url + `/api/v1/reading-groups/comments/${commentSeq}`, {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      })
      .then(function (response) {
        {
          getComment();
        }
        setStatus("detail");
        console.log(response.data.data.msg);
      })
      .catch(function (error) {
        console.log(error);
        alert("?????? ??? ????????? ?????????????????????.");
      });
  }

  function printModify() {
    setStatus("modify");
  }
  return (
    <>
      <CommentBlock>
        {status === "modify" && (
          <Content>
            <Nickname>{nickname}</Nickname>
            <Block>
              <ContentInput
                value={myContent}
                onChange={handleContent}
              ></ContentInput>
              <SubmitBtn onClick={goModify}>????????????</SubmitBtn>
            </Block>
          </Content>
        )}
        {status === "detail" && (
          <Content>
            <Nickname>{nickname}</Nickname>
            <Content>{content}</Content>
            <CreatedDate>{createdDate}</CreatedDate>
          </Content>
        )}

        {nickname === user.memberInfo.nickname ? (
          <More>
            <Btn onClick={printModify}>??????</Btn>
            <Btn onClick={goDelete}>??????</Btn>
          </More>
        ) : (
          <div></div>
        )}
      </CommentBlock>
    </>
  );
}
export default Comment;

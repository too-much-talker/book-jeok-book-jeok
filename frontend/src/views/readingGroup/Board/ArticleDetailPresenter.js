import styled, { css } from "styled-components";
import Comment from "./Comment";
import React from "react";
import { useCallback, useRef } from "react";
import Slick from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { LeftOutlined, RightOutlined } from "@ant-design/icons";
const Block = styled.div`
  height: 100vh;
`;
const Article = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  border: 1px solid black;
  border-radius: 20px;
`;
const Head = styled.div`
  position: relative;
  height: 10%;
  font-size: 30px;
  margin-left: 3%;
`;
const Title = styled.div`
  position: relative;
  margin-top: 2%;
  font-size: 90%;
  text-align: left;
`;
const Rest = styled.div`
  margin-top: 1%;
  display: flex;
  font-size: 70%;
`;

const Writer = styled.div`
  position: relative;
  font-size: 70%;
`;
const Date = styled.div`
  position: relative;
  font-size: 70%;
  margin-left: 2%;
`;
const Views = styled.div`
  position: relative;
  font-size: 70%;
  margin-left: 2%;
`;
const Buttons = styled.div`
  position: absolute;
  font-size: 70%;
  right: 3%;
`;
const Btn = styled.button`
  position: relative;
`;

const Line = styled.div`
  position: relative;
  width: 95%;
  margin: auto;
  border-top: 1px solid black;
`;
const Contents = styled.div`
  position: relative;
  height: 100%;
`;
const Content = styled.div`
  text-align: left;
  margin-top: 15px;
  position: relative;
  height: 60%;
  margin-left: 3%;
  margin-right: 3%;
  margin-bottom: 15px;
`;
const CommentBtn = styled.button`
  position: relative;
  margin-left: 3%;
  margin-right: 90%;
  margin-top: 15px;
`;

const Wrap = styled.div`
  position: relative;
  width: 70%;
  height: 40%;
  margin: auto;
  padding-bottom: 70px;
  margin-top: 15px;
  margin-bottom: 15px;
  overflow: hidden;
  .slick-slide {
    display: inline-block;
  }
  .slick-dots.slick-thumb {
    list-style: none;
    transform: translate(-50%);
    li {
      position: relative;
      display: inline-block;
      &.slick-active {
        span {
          filter: none;
        }
      }
    }
  }
`;

const SlickItems = styled.div`
  position: relative;
  margin-left: 2%;
  height: 300px;
  img {
    max-width: 100%;
    height: 100%;
    vertical-align: top;
  }
`;

// const Comments= styled.div`
// position:relative;
// height:31%;
// background:blue;
// `;
const path = {};
function ArticleDetailPresenter({
  disabled,
  settings,
  goDelete,
  goModify,
  file,
  comments,
  title,
  content,
  nickname,
  createdDate,
  views,
}) {
  return (
    <Block>
      <Article>
        <Head>
          <Title>{title}</Title>
          <Rest>
            <Writer>{nickname}</Writer>
            <Date>{createdDate}</Date>
            <Views> 조회수 : {views}</Views>

            <Buttons>
              <Btn onClick={goModify} disabled={disabled}>
                수정
              </Btn>
              <Btn onClick={goDelete} disabled={disabled}>
                삭제
              </Btn>
            </Buttons>
          </Rest>
        </Head>
        <Line></Line>
        <Contents>
          <Wrap>
            <Slick {...settings}>
              {file &&
                file.map((imagePath) => (
                  <SlickItems>
                    <img
                      src={"https://i6a305.p.ssafy.io:8443/" + imagePath}
                    ></img>
                  </SlickItems>
                ))}
            </Slick>
          </Wrap>
          <Line></Line>
          <CommentBtn>댓글 보기</CommentBtn>
          <Content>{content}</Content>
        </Contents>

        <Comment>
          {comments &&
            comments.map((comment) => (
              <Comment
                nickname={comment.nickname}
                createdDate={comment.createdDate}
                content={comment.content}
              ></Comment>
            ))}
        </Comment>
      </Article>
    </Block>
  );
}

export default ArticleDetailPresenter;

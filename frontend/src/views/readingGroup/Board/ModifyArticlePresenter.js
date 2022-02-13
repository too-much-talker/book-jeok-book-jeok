import React, { useState, useEffect, useReducer } from "react";
import styled from "styled-components";
import Slick from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const Block = styled.div`
  height: 100vh;
`;
const Article = styled.div`
  position: relative;
  margin-left: 20%;
  width: 80%;
  height: 97%;
  // background: red;
`;
const TitleBlock = styled.div`
  margin-top: 5%;
  position: relative;
  height: 5%;
  width: 80%;
  margin-left: 10%;
  text-align: left;
  display: flex;
`;
const Title = styled.input`
  position: relative;
  margin-left: 3%;
  width: 90%;
  height: 100%;
`;
const Label = styled.div`
  position: relative;
  // background: blue;
  width: 5 %;
  height: 100%;
`;
const ContentBlock = styled.div`
  margin-top: 3%;
  position: relative;
  height: 30%;
  width: 80%;
  margin-left: 10%;
  display: flex;
  text-align: left;
`;
const Content = styled.textarea`
  margin-left: 3%;
  width: 90%;
  height: 100%;
  resize: none;
  font-family: bold;
  // background: Red;
`;
const ImageBlock = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 1%;
  display: flex;
  //   background: yellow;
`;
const Image = styled.div`
  margin-top: 1.5%;
  margin-left: 10px;
  width: 300px;
  // background: blue;
`;
const Inputs = styled.div`
  //   background: blue;
`;
const SubmitBtn = styled.button`
  position: relative;
  height: 5%;
  width: 10%;
  border: 1px;
  background: black;
  color: white;
  border-radius: 20px;
  margin-top: 1%;
`;
const Preview = styled.div`
  position: relative;
  text-align: left;
  margin-top: 1%;
  //   background: red;
`;

const Message = styled.div`
  position: relative;
  text-align: left;
  margin-top: 1%;
  font-size: 12px;
`;
const Message1 = styled.div`
  margin-left: 50px;
  margin-bottom: 10px;
`;

const Wrap = styled.div`
  position: relative;
  width: 310px;
  height: 230px;
  padding-bottom: 40px;
  margin-top: 10px;
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

  // background: yellow;
  img {
    max-width: 100%;
    height: 100%;
    vertical-align: top;
  }
`;
function ModifyArticlePresenter({
  button,
  handleTitle,
  handleContent,
  handleFiles,
  title,
  content,
  files,
  onCreate,
  settings,
  postfiles,
  uploadFile,
}) {
  console.log({ postfiles });
  return (
    <Block>
      <Article>
        <TitleBlock>
          <Label>제목</Label>
          <Title onChange={handleTitle} value={title}></Title>
        </TitleBlock>
        <ContentBlock>
          <Label>내용</Label>
          <Content onChange={handleContent} value={content}></Content>
        </ContentBlock>
        <ImageBlock>
          <Wrap>
            <Message1>수정 전</Message1>
            <Slick {...settings}>
              {files &&
                files.map((imagePath) => (
                  <SlickItems>
                    <img
                      src={"https://i6a305.p.ssafy.io:8443/" + imagePath}
                    ></img>
                  </SlickItems>
                ))}
            </Slick>
          </Wrap>
          <Image>
            <Inputs>
              <input
                id="upload-file"
                type="file"
                accept="image/*, video/*"
                multiple
                onChange={uploadFile}
              ></input>
            </Inputs>
            <Message>
              사진을 여러개 선택하고 싶을 경우 한번에 선택해주세요.(최대 5장)
            </Message>
            <Preview>
              {postfiles.file !== null ? (
                <img src={postfiles.previewURL} width="300px" height="180px" />
              ) : null}
            </Preview>
          </Image>
        </ImageBlock>

        <SubmitBtn onClick={onCreate}>수정 완료</SubmitBtn>
      </Article>
    </Block>
  );
}
export default ModifyArticlePresenter;

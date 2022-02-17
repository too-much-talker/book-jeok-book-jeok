import React from "react";
import styled from "styled-components";
const Block = styled.div`
  height: 90vh;
`;
const Article = styled.div`
  position: relative;
  margin-left: 10%;
  width: 100%;
  height: 100%;
  border-radius: 20px;
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
  width: 7%;
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
const Image = styled.div`
  position: relative;
  text-align: left;
  margin-left: 18%;
  margin-top: 3%;
`;
const SubmitBtn = styled.button`
  position: relative;
  height: 5%;
  width: 14%;
  border: 1px;
  background: black;
  color: white;
  border-radius: 20px;
  margin-top: 1%;
  margin-left: 4%;
`;
const Preview = styled.div`
  position: relative;
  text-align: left;
  margin-left: 18%;
  margin-top: 1%;
`;

const Message = styled.div`
  position: relative;
  text-align: left;
  margin-left: 18%;
  margin-top: 1%;
  font-size: 13px;
`;
const Success = styled.div`
  width: 50%;
  margin-left: 35%;
  border-radius: 20px;
  border: 1px solid black;
`;

function ChallengeAuth({
  handleTitle,
  handleContent,
  button,
  postfiles,
  uploadFile,
  onSubmit,
  todayAuth,
  dday,
}) {
  return (
    <>
      {dday > 0 ? (
        <h2 style={{ marginLeft: "20%" }}>아직 챌린지 시작 전입니다.</h2>
      ) : todayAuth === true ? (
        <Success>
          <h2>오늘의 인증 완료</h2>
        </Success>
      ) : (
        <>
          <Block>
            <Article>
              <h2>인증하기</h2>
              <TitleBlock>
                <Label>제목</Label>
                <Title
                  onChange={handleTitle}
                  placeholder="제목을 입력해주세요"
                ></Title>
              </TitleBlock>
              <ContentBlock>
                <Label>내용</Label>
                <Content
                  onChange={handleContent}
                  placeholder="내용을 입력해주세요"
                ></Content>
              </ContentBlock>
              <Image>
                <input
                  id="upload-file"
                  type="file"
                  accept="image/*, video/*"
                  multiple
                  onChange={uploadFile}
                ></input>
              </Image>
              <Message>
                사진을 여러개 선택하고 싶을 경우 한번에 선택해주세요.(최대 5장)
              </Message>
              <Preview>
                {postfiles.file !== null ? (
                  <img
                    src={postfiles.previewURL}
                    width="300px"
                    height="200px"
                  />
                ) : null}
              </Preview>
              <SubmitBtn onClick={onSubmit} disabled={button}>
                작성 완료
              </SubmitBtn>
            </Article>
          </Block>
        </>
      )}
    </>
  );
}

export default ChallengeAuth;

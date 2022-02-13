import React from "react";
import styled from "styled-components";
import Slider from "react-slick";

// const Form= styled.form.attrs(props => ({
//   method:"post",
//    enctype:"multipart/form-data"
// }))``;
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
  width: 4%;
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
// const Content = styled.input`
//   position: relative;
//   margin-left: 3%;
//   width: 90%;
//   height: 100%;
// `;
const Content = styled.textarea`
  margin-left: 3.2%;
  width: 90%;
  width: 100%;
  height: 100%;
  resize: none;
  font-family: bold;
  // background: Red;
`;
const Image = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 3%;
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
  margin-left: 15%;
  margin-top: 1%;
`;

const Message = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 1%;
`;

function RegisterArticlePresenter({
  button,
  postfiles,
  onCreate,
  handleTitle,
  handleContent,
  uploadFile,
}) {
  return (
    <Block>
      <Article>
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
            <img src={postfiles.previewURL} width="300px" height="200px" />
          ) : null}
        </Preview>
        <SubmitBtn onClick={onCreate} disabled={button}>
          작성 완료
        </SubmitBtn>
      </Article>
    </Block>
  );
}
export default RegisterArticlePresenter;

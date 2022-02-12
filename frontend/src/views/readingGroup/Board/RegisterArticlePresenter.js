import React from "react";
import styled from "styled-components";
import Slider from "react-slick";

// const Form= styled.form.attrs(props => ({
//   method:"post",
//    enctype:"multipart/form-data"
// }))``;
const Title = styled.input``;
const Content = styled.input``;
// const Image = styled.input.attrs((props) => ({
//   type: "file",
//   id: "file-id",
//   accept: "image/*",
// }))``;
const Image = styled.div``;
const SubmitBtn = styled.button`
disble
`;
const Preview = styled.div``;
const Message = styled.div``;
function RegisterArticlePresenter({
  button,
  postfiles,
  onCreate,
  handleTitle,
  handleContent,
  uploadFile,
}) {
  return (
    // <Form>
    <>
      <Title onChange={handleTitle} placeholder="제목을 입력해주세요"></Title>
      <Content
        onChange={handleContent}
        placeholder="내용을 입력해주세요"
      ></Content>
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
        사진을 여러개 선택하고 싶을 경우 한번에 선택해주세요.(최대 5장))
      </Message>
      {/* <Image onChange={uploadFile} multiple></Image> */}
      <Preview>
        {postfiles.file !== null ? <img src={postfiles.previewURL} /> : null}
      </Preview>
      <SubmitBtn onClick={onCreate} disabled={button}>
        작성 완료
      </SubmitBtn>
    </>
    // </Form>);
  );
}
export default RegisterArticlePresenter;

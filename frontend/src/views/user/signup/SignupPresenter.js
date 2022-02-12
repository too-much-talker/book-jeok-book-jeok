import React from "react";
import styled from "styled-components";
import { checkPassword } from "../validCheck/ValidCheck";
const SignupTemplateBlock = styled.div`
  width: 950px;
  height: 550px;
  position: relative; /* 추후 박스 하단에 추가 버튼을 위치시키기 */
  border-radius: 16px;
  box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.2);
  margin: 0 auto; /* 페이지 중앙에 나타나도록 */
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const SignupHeadBlock = styled.div`
  padding-top: 10px;
  padding-bottom: 14px;
  margin-bottom: 2%;
  border-bottom: 1px solid black;
  h1 {
    font-size: 26px;
    color: #343a40;
    text-align: center;
  }
  .day {
    margin-top: 4px;
    color: #868e96;
    font-size: 21px;
  }
  .tasks-left {
    color: #20c997;
    font-size: 18px;
    margin-top: 40px;
    font-weight: bold;
  }
`;

const InputItem = styled.div`
  height: 10%;
  width: 100%;
  display: flex;
  margin-bottom: 2%;
`;
const Label = styled.div`
  width: 13%;
  margin-right: 1%;
`;
const Input = styled.input`
  width: 75%;
  margin-right: 1%;
`;
const DuplicateBtn = styled.button``;
const SignupFormBlock = styled.div`
  flex: 1;
  padding: 20px 32px;
  overflow-y: auto;
  text-align: left;
`;

const SignupButton = styled.button`
  width: 8rem;
  height: 2.2rem;
  align-items: center;
  display: flex;
  justify-content: center;
  position: relative;
  margin: auto;
  margin-bottom: 20px;
  background: white;
  border-radius: 1rem;
`;

function Signup({
  onEmailHandler,
  checkEmail,
  onPasswordHandler,
  onPasswordConfirmHandler,
  onNameHandler,
  onNicknameHandler,
  onPhoneNumberHandler,
  checkNameLength,
  checkId,
  checkNickname,
  checkPhoneNumber,
  checkPhoneDuplicate,
  onCreate,
}) {
  return (
    <SignupTemplateBlock>
      <SignupHeadBlock>
        <h1>Sign up</h1>
      </SignupHeadBlock>
      <SignupFormBlock>
        <InputItem>
          <Label>아이디 및 이메일</Label>
          <Input
            placeholder="아이디로 사용될 본인의 이메일을 입력하세요 ( ex.ssafy@naver.com )"
            onChange={onEmailHandler}
            onBlur={checkEmail}
          ></Input>
          <DuplicateBtn onClick={checkId}>중복확인</DuplicateBtn>
        </InputItem>
        <InputItem>
          <Label>비밀번호</Label>
          <Input
            type="password"
            onChange={onPasswordHandler}
            placeholder="비밀번호를 입력해주세요 (영어 소문자, 대문자, 숫자, 특수 문자 4종류 중에 2종류를 포함. 8자 이상 12자 이하)"
          ></Input>
        </InputItem>
        <InputItem>
          <Label>비밀번호 확인</Label>
          <Input
            type="password"
            placeholder="비밀번호를 한번 더 입력해주세요"
            onChange={onPasswordConfirmHandler}
          ></Input>
        </InputItem>
        <InputItem>
          <Label>이름</Label>
          <Input
            placeholder="이름을 입력해주세요"
            onChange={onNameHandler}
            onBlur={checkNameLength}
          ></Input>
        </InputItem>
        <InputItem>
          <Label>닉네임</Label>
          <Input
            placeholder="닉네임을 입력해주세요 (닉네임은 한글로만 혹은 영어로만 이루어져야 합니다.)"
            onChange={onNicknameHandler}
          ></Input>
          <DuplicateBtn onClick={checkNickname}>중복확인</DuplicateBtn>
        </InputItem>
        <InputItem>
          <Label>휴대폰번호</Label>
          <Input
            placeholder="휴대폰 번호를 010-0000-0000 형식으로 입력해주세요"
            onChange={onPhoneNumberHandler}
            onBlur={checkPhoneNumber}
          ></Input>
          <DuplicateBtn onClick={checkPhoneDuplicate}>중복확인</DuplicateBtn>
        </InputItem>
      </SignupFormBlock>
      <SignupButton onClick={onCreate}>가입하기</SignupButton>
    </SignupTemplateBlock>
  );
}

export default Signup;

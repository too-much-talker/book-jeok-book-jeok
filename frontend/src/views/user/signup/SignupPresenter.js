import React from "react";
import styled from "styled-components";
import { checkPassword } from "../validCheck/ValidCheck";
const SignupTemplateBlock = styled.div`
  width: 1100px;
  height: 600px;
  position: relative; /* 추후 박스 하단에 추가 버튼을 위치시키기 */
  background: #6799ff;
  border-radius: 16px;
  box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.04);
  margin: 0 auto; /* 페이지 중앙에 나타나도록 */
  margin-top: 76px;
  margin-bottom: 60px;
  display: flex;
  flex-direction: column;
`;

const SignupHeadBlock = styled.div`
  padding-top: 10px;
  padding-bottom: 14px;
  border-bottom: 1px solid #e9ecef;
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
const SignupFormBlock = styled.div`
  flex: 1;
  padding: 20px 32px;
  padding-bottom: 48px;
  overflow-y: auto;
`;

const SignupButton = styled.button`
  width: 100px;
  height: 50px;
  align-items: center;
  display: flex;
  justify-content: center;
  position: relative;
  margin: auto;
  margin-bottom: 20px;
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
        <h1>회원가입</h1>
      </SignupHeadBlock>
      <SignupFormBlock>
        <div>
          <label for="email">아이디 및 이메일 </label>
          <input
            type="text"
            id="email"
            name="email"
            placeholder="아이디로 사용될 본인의 이메일을 입력하세요 ( ex.ssafy@naver.com )"
            onChange={onEmailHandler}
            size={100}
            onBlur={checkEmail}
          ></input>
          <button onClick={checkId}>중복확인</button>
        </div>
        
        <div>
          <label for="password">비밀번호 </label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호를 입력해주세요.( 영어 소문자, 대문자, 숫자, 특수 문자 4종류 중에 2종류를 포함. 8자 이상 12자 이하 )"
            onChange={onPasswordHandler}
            size={100}
          ></input>
        </div>
        <div>
          <label for="passwordConfirm">비밀번호 확인 </label>
          <input
            type="password"
            id="passwordConfirm"
            name="passwordConfirm"
            placeholder="비밀번호를 한번 더 입력해주세요"
            onChange={onPasswordConfirmHandler}
            size={100}
          ></input>
        </div>
        <div>
          <label for="name">이름 </label>
          <input
            type="text"
            id="name"
            name="name"
            placeholder="이름을 입력해주세요"
            onChange={onNameHandler}
            onBlur={checkNameLength}
            size={100}
          ></input>
        </div>
        <div>
          <label for="nickname">닉네임 </label>
          <input
            type="text"
            id="nickname"
            name="nickname"
            placeholder="닉네임을 입력해주세요"
            onChange={onNicknameHandler}
            size={100}
          ></input>
          <button onClick={checkNickname}>중복확인</button>
        </div>
        
        <div>
          <label for="phoneNumber">휴대폰번호 </label>
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            placeholder="휴대폰 번호를 010-0000-0000 형식으로 입력해주세요"
            onChange={onPhoneNumberHandler}
            onBlur={checkPhoneNumber}
            size={100}
          ></input>
          <button onClick={checkPhoneDuplicate}>중복확인</button>
        </div>
      </SignupFormBlock>
      <SignupButton onClick={onCreate}>가입하기</SignupButton>
    </SignupTemplateBlock>
  );
}

export default Signup;

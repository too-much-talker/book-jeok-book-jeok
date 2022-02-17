import styled from "styled-components";
import { Link } from "react-router-dom";
import React from "react";

const Form = styled.form`
  margin-top: 5rem;
`;

const Input = styled.input`
  display: block;
  margin: 0 auto;
  width: 15rem;
  height: 2rem;
  border-width: 0 0 2px;
  margin-top: 1rem;
  &:focus {
    outline: none;
  }
`;

const Button = styled.button`
  margin-top: 3rem;
  width: 8rem;
  height: 2.2rem;
  background: white;
  border-radius: 1rem;
  &:hover {
    cursor: pointer;
  }
`;

function LoginPresenter({ onIdChange, onPwChange, onSubmit, onKeyPress }) {
  return (
    <div>
      <h2>Login</h2>
      <Form>
        <Input
          onChange={onIdChange}
          onKeyPress={onKeyPress}
          placeholder="email"
          type="text"
        />
        <Input
          onChange={onPwChange}
          onKeyPress={onKeyPress}
          placeholder="password"
          type="password"
        />
        <Button type="button" onClick={onSubmit}>
          로그인
        </Button>
        <p>
          계정이 아직 없으신가요? <Link to="/signup">회원가입</Link>을 해보세요!
        </p>
      </Form>
    </div>
  );
}

export default LoginPresenter;

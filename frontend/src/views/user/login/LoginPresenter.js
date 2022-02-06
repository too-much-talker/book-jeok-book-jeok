import styled from "styled-components";
import { Link } from "react-router-dom";

const Form = styled.form`
  margin-top: 8rem;
`;

const Input = styled.input`
  display: block;
  margin: 0 auto;
  width: 20rem;
  border: 0;
  margin-top: 1rem;
`;

const Button = styled.button`
  margin-top: 3rem;
  width: 20rem;
`;

function LoginPresenter({ onIdChange, onPwChange, onSubmit }) {
  return (
    <div>
      <h2>Login</h2>
      <Form>
        <Input onChange={onIdChange} placeholder="email" type="text" />
        <Input onChange={onPwChange} placeholder="password" type="password" />
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

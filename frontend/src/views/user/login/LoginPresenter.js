function LoginPresenter({ onIdChange, onPwChange, onSubmit }) {
  return (
    <div>
      <h2>Login</h2>
      <form>
        <input onChange={onIdChange} placeholder="email" />
        <input onChange={onPwChange} placeholder="password" type="password" />
        <button type="button" onClick={onSubmit}>
          로그인
        </button>
      </form>
    </div>
  );
}

export default LoginPresenter;

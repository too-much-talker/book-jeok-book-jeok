import React, { useState } from "react";
import styled from "styled-components";
const RegisterBtn = styled.button``;
function ChallengeMainPresenter({ onRegister }) {
  return (
    <>
      <RegisterBtn onClick={onRegister}>챌린지 만들기</RegisterBtn>
    </>
  );
}
export default ChallengeMainPresenter;

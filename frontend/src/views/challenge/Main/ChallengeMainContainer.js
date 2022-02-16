import ChallengeMainPresenter from "./ChallengeMainPresenter";
import React, { useState } from "react";
function ChallengeMainContainer() {
  function onRegister() {
    document.location.href = `/challenge/register`;
  }
  return (
    <ChallengeMainPresenter onRegister={onRegister}></ChallengeMainPresenter>
  );
}
export default ChallengeMainContainer;

import React from "react";

function ChallengeAuth({ onSubmit, onImageChange }) {
  return (
    <>
      <h3>챌린지 인증</h3>
      <div>
        <img />
      </div>
      <form>
        <input type="file" accept="image/*" onChange={onImageChange} />
        <button type="button" onClick={onSubmit}>
          인증
        </button>
      </form>
    </>
  );
}

export default ChallengeAuth;

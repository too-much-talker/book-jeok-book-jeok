import { useState, useEffect } from "react";
import CommentPresenter from "./CommentPresenter";
import axios from "axios";
import { useParams } from "react-router-dom";
import React from "react";

function CommentContainer({ isOpen, onCancel }) {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  let useParam = useParams();
  const url = "https://i6a305.p.ssafy.io:8443";

  const handleClose = () => {
    onCancel();
  };

  function register() {
    axios
      .post(
        url + `/api/v1/bookreviews`,
        {},
        {
          headers: {
            Authorization: `Bearer ` + jwtToken,
          },
        }
      )
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <CommentPresenter
      register={register}
      isOpen={isOpen}
      onCancel={onCancel}
    ></CommentPresenter>
  );
}

export default CommentContainer;


import { useState } from "react";
import WriteModalPresenter from "./WriteModalPresenter";
import axios from "axios";

function WriteModalContainer({ isOpen, onCancel, user,seq }){
    const url = "http://i6a305.p.ssafy.io:8080";
    const handleClose = () => {
        onCancel();
      };

    const [starRating, setStarRating]= useState();
    const [summary, setSummary]= useState();
    const [memberSeq, setMemberSeq]= useState(user.memberInfo.seq);
    


    function handleStarRating(event){
        setStarRating(event.target.value);
    }
    function handleSummary(event){
        setSummary(event.target.value);
    }



    function RegisterReview(){
        axios
        .post(url + `/api/v1/bookreviews`, 
        {
            bookInfoSeq: seq,
            memberSeq: memberSeq,
            starRating: starRating,
            summary:summary
        })
        .then(function (response) {
          console.log(response);
          alert("등록이 완료되었습니다.");
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    return(
    <WriteModalPresenter RegisterReview={RegisterReview} handleStarRating={handleStarRating} handleSummary={handleSummary} starRating={starRating} summary={summary}  isOpen={isOpen} onCancel={onCancel}></WriteModalPresenter>
    );

}

export default WriteModalContainer;
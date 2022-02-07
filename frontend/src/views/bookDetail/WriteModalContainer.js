
import { useState } from "react";
import WriteModalPresenter from "./WriteModalPresenter";
import axios from "axios";

function WriteModalContainer({ isOpen, onCancel, user,seq }){
    const url = "http://i6a305.p.ssafy.io:8080";
    //const url="https://42b4a868-15cd-4d2a-9218-74c0cca488a2.mock.pstmn.io";
    const token=user.jwtToken;
    const handleClose = () => {
        onCancel();
      };
    
    const [starRating, setStarRating]= useState();
    const [summary, setSummary]= useState();
    const [memberSeq, setMemberSeq]= useState(user.memberInfo.seq);
    const [rating, setRating]= useState(0); 
    const [bookseq, setBookSeq]= useState(seq);

    function handleStarRating(event){
        setStarRating(event.target.value);
    }
    function handleSummary(event){
        setSummary(event.target.value);
    }

    function ratingHandler(param){
        setRating((param));
    }
    function RegisterReview(){
        axios
        .post(url + `/api/v1/bookreviews`, 
        {
            bookInfoSeq:seq ,
            memberSeq: memberSeq,
            starRating: rating,
            summary:summary
        }, 
        {
            headers: {
               Authorization: `Bearer `+token
            }
        }
        )
        .then(function (response) {
          console.log(response);
          alert("등록이 완료되었습니다.");
          onCancel();
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    return(
    <WriteModalPresenter ratingHandler={ratingHandler} RegisterReview={RegisterReview} handleStarRating={handleStarRating} handleSummary={handleSummary} starRating={starRating} summary={summary}  isOpen={isOpen} onCancel={onCancel}></WriteModalPresenter>
    );

}

export default WriteModalContainer;
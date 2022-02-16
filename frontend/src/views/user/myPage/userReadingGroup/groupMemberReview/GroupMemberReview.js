import React, { useEffect, useState } from "react";
import styled from "styled-components";
import {
  getGroupDetail,
  submitReview,
} from "../../../../../common/api/readingGroup";
import { useParams } from "react-router";
import { useNavigate } from "react-router";

const Title = styled.div`
  height: 60px;
  width: 800px;
`;

const Contents = styled.div`
  width: 100%;
  float: center;
`;

const FormBox = styled.div`
  padding: 30px;
  border: solid 1px;
  margin: 40px;
  width: 200px;
  display: inline-block;
`;

function GroupMemberReview() {
  const [groupInfo, setGroupInfo] = useState("");
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const [review, setReview] = useState({});

  const params = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    getGroupDetail(
      params.groupSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        console.log(response);
        if (response.status === 200) {
          setGroupInfo(response.data.data.readingGroupDetail);
        } else {
          alert("오류가 발생했습니다.");
        }
      },
      (error) => {
        console.log("오류가 발생했습니다.");
      }
    );
  }, []);

  const onSubmitReview = () => {
    submitReview(
      params.groupSeq,
      review,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        console.log(response);
        navigate("/mypage/mybookclub");
      },
      (error) => {}
    );
  };

  const onChange = (e) => {
    let key = groupInfo.participantSeqs[e.target.name];
    let obj = {};
    obj[key] = e.target.value;
    setReview({
      ...review,
      ...obj,
    });
  };

  console.log(review);

  return (
    <>
      <div>
        <Title>
          <h1>독서모임 회원 리뷰</h1>
          <br></br>
          <br></br>
        </Title>
        {groupInfo ? (
          <>
            <Contents>
              <h2>{groupInfo.title}</h2>
              <div>
                {groupInfo.startDate} ~ {groupInfo.endDate}
              </div>
              <hr />
              {groupInfo.participants.map((p, index) => (
                <FormBox key={index}>
                  <form onChange={onChange}>
                    <div>{p}</div>
                    <span>
                      <input type="radio" value="GOOD" name={index} />
                      <label for="GOOD">GOOD</label>
                    </span>
                    <span>
                      <input type="radio" value="BAD" name={index} />
                      <label for="BAD">BAD</label>
                    </span>
                  </form>
                </FormBox>
              ))}
            </Contents>
            <button type="button" onClick={onSubmitReview}>
              리뷰 완료
            </button>
          </>
        ) : (
          <div>Loading...</div>
        )}
      </div>
    </>
  );
}

export default GroupMemberReview;

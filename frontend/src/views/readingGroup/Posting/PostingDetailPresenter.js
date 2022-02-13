import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";

const url = "https://i6a305.p.ssafy.io:8443";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  margin-top: 50px;
`;

const Wrapper2 = styled.div`
  text-align: left;
  justify-content: center;
  align-items: center;
  font-size: 1.2rem;
  border-radius: 1.5rem;
  padding-left: 10rem;
  border: 1px solid #cccccc;
  padding-top: 40px;
  padding-bottom: 40px;
`;

const Button = styled.div`
  margin-left: 750px;
`;

const HR = styled.div`
  margin-left: -30px;
  margin-right: 130px;
`;
const Items = styled.div`
  display : inline-block;
  margin : 0 .2rem;
  padding : 0 .4rem;
  border : 1px solid #777;
  
  border-radius: 1.5rem;
  text-align: center;
`;
function PostingDetailPresenter({
  group,
  isParticipated,
  subscriptionGroup,
  cancelSubcription,
}) {
  if (group.readingGroupType === "seminar") {
    group.readingGroupType = "세미나형";
  } else if (group.readingGroupType === "discuss") {
    group.readingGroupType = "토론형";
  } else if (group.readingGroupType === "study") {
    group.readingGroupType = "스터디형";
  } else {
    group.readingGroupType = "자유형";
  }
  let korDays;
  if(group.days){
    korDays = group.days.map((day)=>{
      if(day==='MON') return <Items key={'mon'}>월요일</Items>;
      else if(day==='TUE') return <Items key={'tue'}>화요일</Items>;
      else if(day==='WED') return <Items key={'wed'}>수요일</Items>;
      else if(day==='THU') return <Items key={'thu'}>목요일</Items>;
      else if(day==='FRI') return <Items key={'fri'}>금요일</Items>;
      else if(day==='SAT') return <Items key={'sat'}>토요일</Items>;
      else return <Items key={'sun'}>일요일</Items>;
    });
  }else{
    korDays = <div></div>
  }
  
  return (
    <Wrapper>
      <Wrapper2>
        {!isParticipated ? (
          <Button>
            <button onClick={subscriptionGroup}>신청하기</button>
          </Button>
        ) : (
          <Button>
            <button onClick={cancelSubcription}>신청취소</button>
          </Button>
        )}
        <h2>독서모임 포스팅 자세히보기</h2>
        <h5>관심있는 모임이라면 우측 상단의 신청하기 버튼을 눌러주세요.</h5>
        <HR>
          <hr></hr>
        </HR>
        <h3>[ 제목 ] {group.title}</h3>
        <h3>[ 내용 ] {group.content}</h3>

        <table width="600px" height="300px">
          <tr>
            <td>독서모임 기간</td>
            <td>
              {group.startDate} 부터 {group.endDate} 까지
            </td>
          </tr>
          <tr>
            <td>독서모임 요일</td>
            <td>매주 {korDays}</td>
            {/* <td>{group.days}</td> */}
          </tr>
          <tr>
            <td>독서모임 성격</td>
            <td>{group.readingGroupType}</td>
          </tr>

          <tr>
            <td>독서모임 참여조건</td>
            <td>최소 레벨 {group.minLevel} 이상</td>
          </tr>
          <tr>
            <td>모집 마감일</td>
            <td>{group.deadline}</td>
          </tr>
          <tr>
            <td>독서모임 신청명단</td>
            <td>{group.participants ? group.participants.map((p)=>{return <Items key={p}>{p}</Items>}) : <div></div>}</td>
            {/* <td>{group.participants}</td> */}
          </tr>
          <tr>
            <td>독서모임 생성일</td>
            <td>{group.createdDate}</td>
          </tr>
          <tr>
            <td>독서모임 생성자</td>
            <td>{group.writer}</td>
          </tr>
        </table>
        {/* <Label>
          <label>상태</label>
          <p>{group.status}</p>
        </Label> */}
      </Wrapper2>
    </Wrapper>
  );
}
export default PostingDetailPresenter;
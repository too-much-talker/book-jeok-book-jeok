import React, { useEffect, useReducer, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import PostingModifyContainer from "./PostingModifyContainer";

const url = "https://i6a305.p.ssafy.io:8443";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  margin-top: 20px;
`;

const Wrapper2 = styled.div`
  text-align: left;
  justify-content: center;
  align-items: center;
  font-size: 1.2rem;
  border-radius: 1.5rem;
  padding-left: 10rem;
  border: 1px solid #cccccc;
  padding-top: 20px;
  padding-bottom: 40px;
`;

const Button = styled.button`
  margin-left: 725px;
  border: 0;
  height: 30px;
  padding-left: 10px;
  padding-right: 10px;
  background: #faeee0;
  border-radius: 10px;
  color: #4c4c4c;
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 5px;
`;

const HR = styled.div`
  margin-left: -30px;
  margin-right: 130px;
`;
const Items = styled.div`
  display: inline-block;
  margin: 0 0.2rem;
  padding: 0 0.4rem;
  border: 1px solid #777;
  border-radius: 1.5rem;
  text-align: center;
`;

function PostingDetailPresenter({
  userInfo,
  group,
  isParticipated,
  subscriptionGroup,
  cancelSubcription,
  writer,
  goDelete,
  detailState,
  changeModifyState,
  changeDetailState,
  readingGroupSeq,
  jwtToken,
  url,
  getPage,
}) {
  const [readingGroupType, setReadingGroupType] = useState();

  function selectType() {
    console.log(group);
    if (readingGroupType === "SEMINAR") {
      setReadingGroupType("세미나형");
    } else if (readingGroupType === "DISCUSS") {
      setReadingGroupType("토론형");
    } else if (readingGroupType === "STUDY") {
      setReadingGroupType("스터디형");
    } else {
      setReadingGroupType("자유형");
    }
  }
  useEffect(() => {
    selectType();
  }, []);
  let korDays;
  if (group.days) {
    korDays = group.days.map((day) => {
      if (day === "MON") return { days: "월요일", value: 1 };
      else if (day === "TUE") return { days: "화요일", value: 2 };
      else if (day === "WED") return { days: "수요일", value: 3 };
      else if (day === "THU") return { days: "목요일", value: 4 };
      else if (day === "FRI") return { days: "금요일", value: 5 };
      else if (day === "SAT") return { days: "토요일", value: 6 };
      else return { days: "일요일", value: 7 };
    });
  }
  if (korDays) {
    korDays.sort(function (a, b) {
      return a.value - b.value;
    });
  }
  if (korDays) {
    korDays = korDays.map((day) => {
      return <Items key={day.days}>{day.days}</Items>;
    });
  } else {
    korDays = [];
  }

  return (
    <Wrapper>
      <Wrapper2>
        {userInfo.nickname === writer ? (
          detailState === "modify" ? (
            <div></div>
          ) : (
            <>
              <Button onClick={goDelete}>독서모임 삭제하기</Button>
              <Button onClick={changeModifyState}>독서모임 수정하기</Button>
            </>
          )
        ) : (
          group.status === "PRE" &&
          (!isParticipated ? (
            <Button onClick={subscriptionGroup}>신청하기</Button>
          ) : (
            <Button onClick={cancelSubcription}>신청취소</Button>
          ))
        )}
        {/* <h2>독서모임 포스팅 자세히보기</h2> */}

        {detailState === "detail" ? (
          <>
            <h2>관심있는 모임이라면 우측 상단의 신청하기 버튼을 눌러주세요.</h2>
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
                {group.readingGroupType === "STUDY" && (
                  <td>스터디형의 분위기</td>
                )}
                {group.readingGroupType === "DISCUSS" && (
                  <td>토론형의 분위기</td>
                )}
                {group.readingGroupType === "SEMINAR" && (
                  <td>세미나형의 분위기</td>
                )}
                {group.readingGroupType === "FREE" && <td>자유형의 분위기</td>}
              </tr>

              <tr>
                <td>독서모임 참여조건</td>
                <td>최소 레벨 {group.minLevel} 이상 신청 가능합니다.</td>
              </tr>
              <tr>
                <td>모집 마감일</td>
                <td>{group.deadline}에 마감합니다.</td>
              </tr>
              <tr>
                <td>독서모임 신청명단</td>
                <td>
                  {group.participants ? (
                    group.participants.map((p) => {
                      return <Items key={p}>{p}</Items>;
                    })
                  ) : (
                    <div></div>
                  )}
                </td>
                {/* <td>{group.participants}</td> */}
              </tr>
              <tr>
                <td>독서모임 생성일</td>
                <td>{group.createdDate}에 생성되었습니다.</td>
              </tr>
              <tr>
                <td>독서모임 생성자</td>
                <td>{group.writer}님께서 생성하셨습니다.</td>
              </tr>
            </table>
          </>
        ) : (
          <>
            <h2>독서모임 포스팅 수정하기</h2>
            <HR>
              <hr></hr>
            </HR>
            <PostingModifyContainer
              url={url}
              jwtToken={jwtToken}
              readingGroupSeq={readingGroupSeq}
              changeDetailState={changeDetailState}
              group={group}
              getPage={getPage}
            ></PostingModifyContainer>
          </>
        )}
      </Wrapper2>
    </Wrapper>
  );
}
export default PostingDetailPresenter;

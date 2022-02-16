import React, { useEffect, useReducer, useState } from "react";
import axios from "axios";
import styled from "styled-components";

const Word = styled.div`
  margin-top: 20px;
  margin-bottom: 7px;
`;
const Title = styled.input`
  width: 80%;
  margin-bottom: 15px;
`;
const Content = styled.textarea`
  width: 80%;
  margin-bottom: 15px;
`;
const Type = styled.div`
  margin-bottom: 40px;
`;
const DateBlock = styled.div`
  display: flex;
  margin-bottom: 40px;
`;
const DateWord = styled.div`
  margin-right: 8px;
`;
const DateInput = styled.input.attrs((props) => ({
  type: "date",
}))`
  //   width: ${(props) => props.size};
`;
const DayBlock = styled.div`
  margin-bottom: 40px;
`;
const Items = styled.div`
  display: inline-block;
  margin: 0 0.2rem;
  padding: 0 0.4rem;
  border: 1px solid #777;
  border-radius: 1.5rem;
  text-align: center;
`;
const Maximum = styled.div`
  margin-bottom: 40px;
`;

const Deadline = styled.input.attrs((props) => ({
  type: "date",
}))`
  margin-bottom: 20px;
`;
const Btn = styled.button``;

const LimitLevel = styled.div``;

function PostingModifyPresenter({
  title,
  content,
  startDate,
  endDate,
  deadline,
  maximum,
  days,
  limitLevel,
  readingGroupType,
  onTypeButtonHandler,
  onTitleChange,
  onContentChange,
  onStartDateChange,
  onEndDateChange,
  onDayChange,
  onMaximumChange,
  onDeadLineChange,
  onLevelChange,
  changeDetailState,
  submit,
  getPage,
}) {
  let korDays;
  if (days) {
    korDays = days.map((day) => {
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
    <>
      <Word>독서모임 제목</Word>
      <Title value={title} onChange={onTitleChange}></Title>
      <Word>독서모임 내용</Word>
      <Content value={content} onChange={onContentChange}></Content>
      <Word>독서모임 성격</Word>
      <Type>
        <select
          name="type"
          value={readingGroupType}
          onChange={onTypeButtonHandler}
        >
          <option value={"DISCUSS"}>토론형</option>
          <option value={"SEMINAR"}>세미나형</option>
          <option value={"STUDY"}>스터디형</option>
          <option value={"FREE"}>자유형</option>
        </select>
      </Type>
      <Word>독서모임 기간</Word>
      <DateBlock>
        <DateInput value={startDate} onChange={onStartDateChange}></DateInput>
        <DateWord>부터 </DateWord>
        <DateInput value={endDate} onChange={onEndDateChange}></DateInput>
        <DateWord>까지</DateWord>
      </DateBlock>
      <DayBlock>
        <Word>독서모임 요일</Word>
        <div style={{ fontSize: "16px" }}>현재 독서모임 요일:{korDays} </div>
        <input
          id="MON"
          value="MON"
          name="days"
          type="checkbox"
          onChange={onDayChange}
        />
        월
        <input
          id="TUE"
          value="TUE"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        화
        <input
          id="WED"
          value="WED"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        수
        <input
          id="THU"
          value="THU"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        목
        <input
          id="FRI"
          value="FRI"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        금
        <input
          id="SAT"
          value="SAT"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        토
        <input
          id="SUN"
          value="SUN"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />
        일<br></br>
      </DayBlock>
      <Word>참가 최대 인원</Word>
      <Maximum>
        <select name="maxMember" value={maximum} onChange={onMaximumChange}>
          <option value={3}>3명</option>
          <option value={4}>4명</option>
          <option value={5}>5명</option>
          <option value={6}>6명</option>
          <option value={7}>7명</option>
          <option value={8}>8명</option>
          <option value={9}>9명</option>
          <option value={10}>10명</option>
        </select>
      </Maximum>
      <Word>모집 마감 날짜</Word>
      <Deadline value={deadline} onChange={onDeadLineChange}></Deadline>
      <Word>신청 자격 레벨</Word>
      <LimitLevel>
        <select name="limitLevel" value={limitLevel} onChange={onLevelChange}>
          <option value={1}>1 레벨</option>
          <option value={2}>2 레벨</option>
          <option value={3}>3 레벨</option>
          <option value={4}>4 레벨</option>
          <option value={5}>5 레벨</option>
          <option value={6}>6 레벨</option>
          <option value={7}>7 레벨</option>
          <option value={8}>8 레벨</option>
          <option value={9}>9 레벨</option>
          <option value={10}>10 레벨</option>
          <option value={11}>11 레벨</option>
          <option value={12}>12 레벨</option>
          <option value={13}>13 레벨</option>
          <option value={14}>14 레벨</option>
          <option value={15}>15 레벨</option>
          <option value={16}>16 레벨</option>
          <option value={17}>17 레벨</option>
          <option value={18}>18 레벨</option>
          <option value={19}>19 레벨</option>
          <option value={20}>20 레벨</option>
          <option value={21}>21 레벨</option>
          <option value={22}>22 레벨</option>
          <option value={23}>23 레벨</option>
          <option value={24}>24 레벨</option>
          <option value={25}>25 레벨</option>
          <option value={26}>26 레벨</option>
          <option value={27}>27 레벨</option>
          <option value={28}>28 레벨</option>
          <option value={29}>29 레벨</option>
          <option value={30}>30 레벨</option>
          <option value={31}>31 레벨</option>
          <option value={32}>32 레벨</option>
          <option value={33}>33 레벨</option>
          <option value={34}>34 레벨</option>
          <option value={35}>35 레벨</option>
          <option value={36}>36 레벨</option>
          <option value={37}>37 레벨</option>
          <option value={38}>38 레벨</option>
          <option value={39}>39 레벨</option>
          <option value={40}>40 레벨</option>
          <option value={41}>41 레벨</option>
          <option value={42}>42 레벨</option>
          <option value={43}>43 레벨</option>
          <option value={44}>44 레벨</option>
          <option value={45}>45 레벨</option>
          <option value={46}>46 레벨</option>
          <option value={47}>47 레벨</option>
          <option value={48}>48 레벨</option>
          <option value={49}>49 레벨</option>
          <option value={50}>50 레벨</option>
        </select>
      </LimitLevel>
      <Btn onClick={submit}>완료</Btn>
    </>
  );
}
export default PostingModifyPresenter;

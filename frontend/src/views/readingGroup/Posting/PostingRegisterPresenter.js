import React, { Children, useEffect, useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  text-align: center;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
`;

const Info = styled.div`
  display: inline-block;
  padding-left: 4rem;
  text-align: left;
  margin-bottom: 50px;
`;

const Wrapper2 = styled.div`
  text-align: left;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  border-radius: 1.5rem;
  padding-left: 10rem;
  border: 1px solid #cccccc;
  padding-top: 40px;
  padding-bottom: 40px;
  margin-top: 40px;
`;
const Button = styled.div`
  margin-left: 700px;
`;

const url = "https://i6a305.p.ssafy.io:8443";

function PostingRegisterPresenter({
  onTypeButtonHandler,
  onTitleChange,
  onContentChange,
  title,
  content,
  onStartDateChange,
  onEndDateChange,
  onDayChange,
  onMaximumChange,
  onDeadLineChange,
  onLevelChange,
  submitHandler,
  img,
}) {
  return (
    <Wrapper>
      <img
        src={img}
        height="170px"
        width="250px"
        style={{ borderRadius: "30px", marginTop: "30px" }}
      ></img>
      <Info>
        <h2>모임의 성격을 알려주세요 !</h2>
        <h5>선택된 키워드에 맞게 자동으로 이미지가 생성됩니다.</h5>
        <input
          id="discuss"
          value="discuss"
          name="readingGroupType"
          type="radio"
          onChange={onTypeButtonHandler}
        />{" "}
        토론형
        <input
          id="seminar"
          value="seminar"
          name="readingGroupType"
          type="radio"
          onChange={onTypeButtonHandler}
          style={{ marginLeft: 20 }}
        />{" "}
        세미나형
        <input
          id="study"
          value="study"
          name="readingGroupType"
          type="radio"
          onChange={onTypeButtonHandler}
          style={{ marginLeft: 20 }}
        />{" "}
        스터디형
        <input
          id="free"
          value="free"
          name="readingGroupType"
          type="radio"
          onChange={onTypeButtonHandler}
          style={{ marginLeft: 20 }}
        />{" "}
        자유형
      </Info>
      <br></br>
      <hr></hr>
      <Wrapper2>
        <h5>독서모임 제목</h5>
        <input
          size="105"
          onChange={onTitleChange}
          value={title}
          type="text"
          name="title"
          placeholder="제목을 입력해주세요."
        />
        <h5>독서모임 내용</h5>
        <textarea
          style={{ resize: "none", height: "100px", width: "765px" }}
          onChange={onContentChange}
          value={content}
          name="context"
          placeholder="텍스트를 입력해주세요."
        />
        <br></br>
        <h5 style={{ display: "inline-block" }}>독서모임 기간</h5>
        <input
          type="date"
          id="startDate"
          name="startDate"
          onChange={onStartDateChange}
          style={{ display: "inline-block", marginLeft: "30px" }}
        />{" "}
        부터
        <input
          type="date"
          id="endDate"
          name="endDate"
          style={{ marginLeft: 20 }}
          onChange={onEndDateChange}
        />{" "}
        까지
        <br></br>
        <input
          id="mon"
          value="mon"
          name="days"
          type="checkbox"
          onChange={onDayChange}
        />{" "}
        월
        <input
          id="tue"
          value="tue"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        화
        <input
          id="wed"
          value="wed"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        수
        <input
          id="thu"
          value="thu"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        목
        <input
          id="fri"
          value="fri"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        금
        <input
          id="sat"
          value="sat"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        토
        <input
          id="sun"
          value="sun"
          name="days"
          type="checkbox"
          onChange={onDayChange}
          style={{ marginLeft: 20 }}
        />{" "}
        일<br></br>
        <h5 style={{ display: "inline-block", marginRight:"30px"}}>참가 최대 인원</h5>
        <select name="maxMember" onChange={onMaximumChange}>
          <option value={3}>3명</option>
          <option value={4}>4명</option>
          <option value={5}>5명</option>
          <option value={6}>6명</option>
          <option value={7}>7명</option>
          <option value={8}>8명</option>
          <option value={9}>9명</option>
          <option value={10}>10명</option>
        </select>
        <br></br>
        <h5 style={{ display: "inline-block", marginRight:"30px"}}>모집 마감 날짜</h5>
        <input
          type="date"
          id="deadline"
          name="deadline"
          onChange={onDeadLineChange}
        />
        <br></br>
        <h5 style={{ display: "inline-block" , marginRight:"30px"}}>신청 자격 레벨</h5>
        <select name="limitLevel" onChange={onLevelChange}>
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
        <Button>
          <button onClick={submitHandler}>저장</button>
        </Button>
      </Wrapper2>
    </Wrapper>
  );
}
export default PostingRegisterPresenter;

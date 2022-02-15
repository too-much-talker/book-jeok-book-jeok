import PostingModifyPresenter from "./PostingModifyPresenter";
import React, { useEffect, useReducer, useState } from "react";
import axios from "axios";
import styled from "styled-components";
function PostingModifyContainer({
  url,
  jwtToken,
  readingGroupSeq,
  changeDetailState,
  group,
  getPage,
}) {
  //console.log(group);
  const [title, setTitle] = useState(group.title);
  const [content, setContent] = useState(group.content);
  const [startDate, setStartDate] = useState(group.startDate);
  const [endDate, setEndDate] = useState(group.endDate);
  const [deadline, setDeadLine] = useState(group.deadline);
  const [maximum, setMaximum] = useState(group.maxNumOfMember);
  const [days, setDays] = useState(group.days);
  const [changeDays, setChangeDays] = useState([]);
  const [limitLevel, setLimitLevel] = useState(group.minLevel);
  const [readingGroupType, setReadingGroupType] = useState(
    group.readingGroupType
  );

  const onTypeButtonHandler = (event) => {
    setReadingGroupType(event.target.value);
  };
  const onTitleChange = (event) => {
    setTitle(event.target.value);
  };
  const onContentChange = (event) => {
    setContent(event.target.value);
  };
  const onStartDateChange = (event) => {
    setStartDate(event.target.value);
  };
  const onEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const onDayChange = (event) => {
    if (event.target.checked) {
      setChangeDays([...changeDays, event.target.id]);
    } else {
      setChangeDays(changeDays.filter((day) => day !== event.target.id));
    }
  };
  const onMaximumChange = (event) => {
    setMaximum(event.target.value);
  };
  const onDeadLineChange = (event) => {
    setDeadLine(event.target.value);
  };
  const onLevelChange = (event) => {
    setLimitLevel(event.target.value);
  };

  useEffect(() => {
    if (changeDays.length !== 0) {
      setDays(changeDays);
    }
  }, [submit]);

  function submit() {
    if (changeDays.length !== 0) {
      setDays(changeDays);
      console.log(days);
    }
    if (deadline >= startDate) {
      alert("독서모임 시작일 혹은 모집 마감일을 다시 설정해주세요.");
    } else if (startDate + 6 > endDate) {
      alert("독서모임 기간을 다시 설정해주세요.");
    } else {
      axios
        .put(
          url + `/api/v1/reading-groups/${readingGroupSeq}`,
          {
            title: title,
            content: content,
            limitLevel: parseInt(limitLevel),
            maxMember: parseInt(maximum),
            deadline: deadline,
            startDate: startDate,
            endDate: endDate,
            readingGroupType: readingGroupType,
            days: days,
          },
          {
            headers: {
              Authorization: `Bearer ` + jwtToken,
            },
          }
        )
        .then(function (response) {
          console.log(response.data);
          getPage();
          alert("수정되었습니다.");
          changeDetailState();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
  return (
    <PostingModifyPresenter
      title={title}
      content={content}
      startDate={startDate}
      endDate={endDate}
      deadline={deadline}
      maximum={maximum}
      days={days}
      limitLevel={limitLevel}
      readingGroupType={readingGroupType}
      onTypeButtonHandler={onTypeButtonHandler}
      onTitleChange={onTitleChange}
      onContentChange={onContentChange}
      onStartDateChange={onStartDateChange}
      onEndDateChange={onEndDateChange}
      onDayChange={onDayChange}
      onMaximumChange={onMaximumChange}
      onDeadLineChange={onDeadLineChange}
      onLevelChange={onLevelChange}
      changeDetailState={changeDetailState}
      submit={submit}
    ></PostingModifyPresenter>
  );
}
export default PostingModifyContainer;

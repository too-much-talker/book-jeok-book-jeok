import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import Pagination from "react-js-pagination";
import PostingListPresenter from "./PostingListPresenter";
import img_none from "../../../res/img/img_none.webp"
import img_discuss from "../../../res/img/img_discuss.jpg"
import img_seminar from "../../../res/img/img_seminar.jpg"
import img_study from "../../../res/img/img_study.jpg"
import img_free from "../../../res/img/img_free.jpg"

const Postinginfo = styled.div`
  display: flex;
  border: 1px solid #cccccc;
  height: 10rem;
  width: 50rem;
  margin-left: 8rem;
  cursor: pointer;
  p {
    margin: 0;
  }
  margin-bottom: 2rem;
  align-items: center;
  padding: 1rem;
  padding-left: 1.5rem;
  border-radius: 2rem;
  transition: all 0.1s;
  &:hover {
    transform: scale(1.03);
  }
  &:active {
    transform: scale(1.01);
  }
`;

const Wrapper = styled.div`
  display: inline-block;
  width: 50rem;
  margin-left: 3rem;
  font-size: 1.2rem;
  text-align: left;
  tr {
    color: black;
    text-decoration-line: none;
  }
`;


const StyledLink = styled(Link)`
  text-decoration: none;

  &:focus,
  &:hover,
  &:visited,
  &:link,
  &:active {
    text-decoration: none;
  }
`;
const url = "https://i6a305.p.ssafy.io:8443";

function PostingListContainer() {
  const [totalCnt, setTotalCnt] = useState(0);
  const [page, setPage] = useState(1);
  const [groups, setGroups] = useState([]);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  const getList = async () => {
    const response = await axios.get(
      url + `/api/v1/reading-groups?size=5&page=${page}`,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      }
    );
    console.log(response);
    setTotalCnt(response.data.data.readingGroups.totalCnt);
    setGroups(response.data.data.readingGroups.readingGroupMiniDtos);
  };
  const handlePageChange = (page) => {
    setPage(page);
  };
  useEffect(() => {
    getList();
  }, [page]);
  const groupList = groups.map((group) => {
    let img;
  if(group.readingGroupType==="none"){
    img  = img_none;
  }else if(group.readingGroupType==="discuss"){
    img = img_discuss;
  }else if(group.readingGroupType==="seminar"){
    img = img_seminar;
  }else if(group.readingGroupType==="study"){
    img = img_study;
  }else{
    img = img_free;
  }
    if(group.readingGroupType==="seminar"){
      group.readingGroupType = "세미나형"
    }
    else if(group.readingGroupType==="discuss"){
      group.readingGroupType = "토론형"
    }
    else if(group.readingGroupType === "study"){
      group.readingGroupType = "스터디형"
    }
    else{
      group.readingGroupType = "자유형"
    }
    return (
      <StyledLink
        to="/postingdetail"
        key={group.readingGroupSeq}
        state={{ logSeq: group.readingGroupSeq }}
      >
        <Postinginfo>
          <img
            src={img}
            height="150px"
            width="200px"
            style={{ borderRadius: "30px" }}
          ></img>
          <Wrapper>
            <table width="500px">
              <tr>
                <td>모임 제목</td>
                <td>{group.title}</td>
              </tr>
              <tr>
                <td>모임 날짜</td>
                <td>
                  {group.startDate}부터 {group.endDate}까지
                </td>
              </tr>
              <tr>
                <td>모임 성격</td>
                <td>{group.readingGroupType}의 분위기</td>
              </tr>
              <tr>
                <td>모집 마감</td>
                <td>{group.deadline}</td>
              </tr>
              <tr>
                <td>현재 인원</td>
                <td>{group.participantSeqs.length}명</td>
              </tr>
            </table>
          </Wrapper>
        </Postinginfo>
      </StyledLink>
    );
  });
  return (
    <PostingListPresenter 
    groupList={groupList}
    page={page}
    totalCnt={totalCnt}
    handlePageChange={handlePageChange}
    />
  );
}

export default PostingListContainer;

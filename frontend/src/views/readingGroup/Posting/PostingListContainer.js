import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import Pagination from "react-js-pagination";
import PostingListPresenter from "./PostingListPresenter";
import img_none from "../../../res/img/img_none.webp";
import img_discuss from "../../../res/img/img_discuss.jpg";
import img_seminar from "../../../res/img/img_seminar.jpg";
import img_study from "../../../res/img/img_study.jpg";
import img_free from "../../../res/img/img_free.jpg";

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

  useEffect(() => {
    if (jwtToken === null || jwtToken === undefined || jwtToken === "") {
      document.location.href = `/login`;
      alert("로그인 후 이용 가능합니다.");
    }
  }, [jwtToken]);

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
    console.log(page);
  }, [page]);

  function cutText(content, size) {
    if (content !== null && content.length > size) {
      return content.substr(0, size - 1) + "...";
    } else return content;
  }

  const groupList = groups.map((group) => {
    let img;
    if (group.readingGroupType === "none") {
      img = img_none;
    } else if (group.readingGroupType === "DISCUSS") {
      img = img_discuss;
    } else if (group.readingGroupType === "SEMINAR") {
      img = img_seminar;
    } else if (group.readingGroupType === "STUDY") {
      img = img_study;
    } else {
      img = img_free;
    }

    if (group.readingGroupType === "SEMINAR") {
      group.readingGroupType = "세미나형";
    } else if (group.readingGroupType === "DISCUSS") {
      group.readingGroupType = "토론형";
    } else if (group.readingGroupType === "STUDY") {
      group.readingGroupType = "스터디형";
    } else {
      group.readingGroupType = "자유형";
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
                <td>{cutText(group.title, 22)}</td>
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
    <div>
      <PostingListPresenter groupList={groupList} />
      <Pagination
        activePage={page}
        itemsCountPerPage={5}
        totalItemsCount={totalCnt}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </div>
  );
}

export default PostingListContainer;

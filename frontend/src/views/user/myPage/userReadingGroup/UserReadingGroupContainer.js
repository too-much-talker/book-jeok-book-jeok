import React, { useEffect, useState } from "react";
import UserReadingGroupPresenter from "./UserReadingGroupPresenter";
import Pagination from "react-js-pagination";
import { getGroupList } from "../../../../common/api/readingGroup";
import GroupMemberReview from "./groupMemberReview/GroupMemberReview";
import { useNavigate } from "react-router";

function UserReadingGroupContainer() {
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(true);

  const [groups, setGroups] = useState([]);
  const [page, setPage] = useState(1);
  const handlePageChange = (page) => {
    setPage(page);
  };

  const size = 12;
  const [totalCnt, setTotalCnt] = useState(0);

  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const [notReviewdGroup, setNotReviewdGroup] = useState("");

  function getReviewd(groups) {
    for (let i = 0; i < groups.length; i++) {
      if (groups[i].status === "END" && !groups[i].reviewed) {
        alert("리뷰하지 않은 독서모임이 존재합니다.");
        navigate(
          `/mypage/mybookclub/memberreview/${groups[i].readingGroupSeq}`
        );
        break;
      }
    }
  }

  useEffect(() => {
    getGroupList(
      {
        size: size,
        page: page,
      },
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        console.log(response);
        if (response.status === 200) {
          getReviewd(response.data.data.readingGroups);

          setGroups(response.data.data.readingGroups);
          setTotalCnt(response.data.data.totalCnt);

          setIsLoading(false);
        } else {
          alert("접근이 불가합니다.");
        }
      },
      (error) => {
        alert("오류가 발생했습니다.");
        console.log(error);
      }
    );
  }, [page]);

  return (
    <>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div>
          <UserReadingGroupPresenter groups={groups} />
          <Pagination
            activePage={page}
            itemsCountPerPage={size}
            totalItemsCount={totalCnt}
            pageRangeDisplayed={5}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
          />
        </div>
      )}
    </>
  );
}

export default UserReadingGroupContainer;

import React, { useEffect, useState } from "react";
import UserReadingGroupPresenter from "./UserReadingGroupPresenter";
import Pagination from "react-js-pagination";
import { getGroupList } from "../../../../common/api/readingGroup";

function UserReadingGroupContainer() {
  const [isLoading, setIsLoading] = useState(true);

  const [groups, setGroups] = useState([]);
  const [page, setPage] = useState(1);
  const handlePageChange = (page) => {
    setPage(page);
  };

  const size = 10;
  const [totalCnt, setTotalCnt] = useState(0);

  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

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
        if (response.status === 200) {
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

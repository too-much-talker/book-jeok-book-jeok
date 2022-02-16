import React, { useEffect, useState } from "react";
import { getChallengeList } from "../../../../../common/api/challenge";
import Pagination from "react-js-pagination";
import CahllengeListPresenter from "./ChallengeListPresenter";

function ChallengeListContainer() {
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  const [isLoading, setIsLoading] = useState(true);
  const [challengeList, setChallengeList] = useState([]);

  const [page, setPage] = useState(1);
  const handlePageChange = (page) => {
    setPage(page);
  };

  const size = 10;
  const [totalCnt, setTotalCnt] = useState(0);

  useEffect(() => {
    getChallengeList(
      {
        size: size,
        page: page,
        end: false,
      },
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        setChallengeList(response.data.data.myChallenges);
        setTotalCnt(response.data.data.totalCnt);
        setIsLoading(false);
      },
      () => {}
    );
  }, [page]);

  return (
    <>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div>
          <CahllengeListPresenter challengeList={challengeList} />
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

export default ChallengeListContainer;

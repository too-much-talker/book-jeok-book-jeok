import BooklogListPresenter from "./BooklogListPresenter";
import { useEffect, useState } from "react";
import { booklogList, findBooklog } from "../../../common/api/booklog";
import "./Paging.css";
import Pagination from "react-js-pagination";
import { useParams, useNavigate } from "react-router-dom";
import SearchbarContainer from "./SearchbarContainer";
import QueryString from "qs";
import { useLocation } from "react-router";

function BooklogListContainer() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  const location = useLocation(); //바뀐 부분

  const [order, setOrder] = useState("");
  const params = useParams().order;
  if (params !== order) {
    setOrder(params);
  }

  const [data, setData] = useState([]);
  const [page, setPage] = useState(1);
  const handlePageChange = (page) => {
    setPage(page);
  };

  const [totalPage, setTotalPage] = useState(1);
  const size = 15;
  const [totalCnt, setTotalCnt] = useState(0);

  useEffect(() => {
    setLoading(true);

    if (order !== "search") {
      booklogList(
        {
          page: page,
          size: size,
          sort: order,
        },
        (response) => {
          if (response.data.status == 200) {
            setTotalPage(response.data.data.totalPage);
            setData(response.data.data.booklogs);
            setTotalCnt(response.data.data.totalCnt);
          } else {
            alert("오류가 발생했습니다.");
            navigate("/");
          }
        },
        (error) => {
          alert("오류가 발생했습니다.");
          navigate("/");
        }
      );
      setLoading(false);
    } else if (order === "search") {
      const queryData = QueryString.parse(location.search, {
        ignoreQueryPrefix: true,
      });
      setLoading(true);
      findBooklog(
        {
          page: page,
          size: size,
          ...queryData,
        },
        (response) => {
          if (response.data.status == 200) {
            console.log(response);
            setTotalPage(response.data.data.totalPage);
            setData(response.data.data.booklogs);
            setTotalCnt(response.data.data.totalCnt);
          } else {
            alert("오류가 발생했습니다.");
            navigate("/");
          }
        },
        (error) => {
          alert("오류가 발생했습니다.");
          navigate("/");
        }
      );
      setLoading(false);
    }
  }, [page, order, totalPage, location]);

  useEffect(() => {
    setPage(1);
  }, [order]);

  return (
    <>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <>
          <h1>북로그 트랜드</h1>
          <SearchbarContainer />
          <BooklogListPresenter
            order={order}
            data={data}
            isPopular={order === "like"}
            totalCnt={totalCnt}
          />
          <Pagination
            activePage={page}
            itemsCountPerPage={size}
            totalItemsCount={totalCnt}
            pageRangeDisplayed={5}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
          />
        </>
      )}
    </>
  );
}

export default BooklogListContainer;

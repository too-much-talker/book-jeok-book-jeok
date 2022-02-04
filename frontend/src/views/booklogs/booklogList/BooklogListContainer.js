import BooklogListPresenter from "./BooklogListPresenter";
import { useEffect, useState } from "react";
import { booklogList } from "../../../common/api/booklog";
import "./Paging.css";
import Pagination from "react-js-pagination";
import { useParams } from "react-router-dom";

function BooklogListContainer() {
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

  useEffect(() => {
    booklogList(
      {
        page: page,
        size: size,
        sort: order,
      },
      (response) => {
        console.log(response);
        setTotalPage(response.data.data.totalPage);
        setData(response.data.data.booklogs);
      },
      (error) => {
        console.log(error);
      }
    );
  }, [page, order, totalPage]);

  useEffect(() => {
    setPage(1);
  }, [order]);

  return (
    <>
      <BooklogListPresenter data={data} isPopular={order === "like"} />
      <Pagination
        activePage={page}
        itemsCountPerPage={size}
        totalItemsCount={totalPage * size}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </>
  );
}

export default BooklogListContainer;

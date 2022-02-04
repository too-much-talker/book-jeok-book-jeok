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
  const perPageItem = 10;

  useEffect(() => {
    booklogList(
      {
        page: page,
        order: order,
      },
      (response) => {
        setTotalPage(response.data.data.totalPage);
        setData(response.data.data.booklogList);
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
      <BooklogListPresenter data={data} isPopular={order === "popular"} />
      <Pagination
        activePage={page}
        itemsCountPerPage={perPageItem}
        totalItemsCount={totalPage * perPageItem}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </>
  );
}

export default BooklogListContainer;

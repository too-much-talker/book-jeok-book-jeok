import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import BooklogDetailPresenter from "./BooklogDetailPresenter";
import { booklogDetail } from "../../../../common/api/booklog";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

function BooklogDetailContainer() {
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  const [booklog, setBooklog] = useState({});
  let params = useParams();
  // const { jwtToken } = useSelector((state) => state.authReducer);
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  useEffect(() => {
    setLoading(true);
    booklogDetail(
      params.booklogSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.data.status === 200) {
          setBooklog(response.data.data.booklog);
        } else if (response.data.status === 403) {
          alert("로그인 후 사용 가능합니다.");
          navigate("-1");
        } else {
          alert("접근이 불가능한 페이지입니다.");
          navigate("/");
        }
        setLoading(false);
      },
      (error) => {
        alert("접근이 불가능한 페이지입니다.");
        navigate("/");
      }
    );
  }, [params]);

  return (
    <div>
      {loading ? (
        <p>Loading....</p>
      ) : (
        <BooklogDetailPresenter booklog={booklog} />
      )}
    </div>
  );
}

export default BooklogDetailContainer;

import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import BooklogDetailPresenter from "./BooklogDetailPresenter";
import {
  booklogDetail,
  isLikeBooklog,
  setLikeBooklog,
} from "../../../../common/api/booklog";
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

  const [isLike, setIsLike] = useState(false);
  const [likes, setLikes] = useState(0);

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
          setLikes(response.data.data.booklog.likes);
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

  // 북로그 like 세팅
  useEffect(() => {
    isLikeBooklog(
      params.booklogSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.data.status === 200) setIsLike(response.data.data.isLike);
        else alert("에러가 발생했습니다.");
      },
      (error) => {
        alert("에러가 발생했습니다.");
      }
    );
  }, [isLike]);

  // 북로그 like 변경
  function onClickHeart() {
    console.log(isLike);
    setLikeBooklog(
      params.booklogSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      isLike,
      (response) => {
        if (response.data.status === 201 || response.data.status === 204) {
          if (isLike) {
            // true인데 false로 바꾸는 경우
            setLikes(likes - 1);
          } else {
            // false인데 true로 바꾸는 경우
            setLikes(likes + 1);
          }
          setIsLike(!isLike);
        } else alert("에러가 발생했습니다.");
      },
      (error) => {
        console.log(error);
        // alert("에러가 발생했습니다.");
      }
    );
  }

  return (
    <div>
      {loading ? (
        <p>Loading....</p>
      ) : (
        <BooklogDetailPresenter
          isLike={isLike}
          likes={likes}
          onClickHeart={onClickHeart}
          booklog={booklog}
        />
      )}
    </div>
  );
}

export default BooklogDetailContainer;

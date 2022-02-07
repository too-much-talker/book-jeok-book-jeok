import { useEffect, useState } from "react";
import styled from "styled-components";
import { bookInfoDetail } from "../../../../common/api/bookInfo";

const Info = styled.div`
  display: inline-block;
  padding-left: 5rem;
`;

const Table = styled.div`
  align: center;
`;

function BooklogDetailBookInfo({ booklog }) {
  const [loading, setLoading] = useState(true);
  const [bookInfo, setBookInfo] = useState({});
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));

  useEffect(() => {
    setLoading(true);
    bookInfoDetail(
      booklog.bookInfoSeq,
      {
        headers: {
          Authorization: `Bearer ` + jwtToken,
        },
      },
      (response) => {
        if (response.data.status === 200) {
          setBookInfo(response.data.data.bookInfo);
          setLoading(false);
        } else {
          alert("오류가 발생했습니다.");
        }
      },
      (error) => {
        alert("오류가 발생했습니다.");
      }
    );
  }, []);

  return (
    <>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <>
          <img src={bookInfo.largeImgUrl} alt={bookInfo.title}></img>
          <Info>
            <Table>
              <tbody>
                <tr>
                  <th> 제목 </th>
                  <td>{bookInfo.title}</td>
                </tr>
                <tr>
                  <th> 작가 </th>
                  <td>{bookInfo.author}</td>
                </tr>
                <tr>
                  <th> 출판사 </th>
                  <td>{bookInfo.publisher}</td>
                </tr>
                <tr>
                  <th> 출판일 </th>
                  <td>{bookInfo.publicationDate}</td>
                </tr>
                <tr>
                  <th> 별점 </th>
                  <td>{booklog.starRating}점</td>
                </tr>
                <tr>
                  <th> 한줄평 </th>
                  <td>{booklog.summary}</td>
                </tr>
              </tbody>
            </Table>
          </Info>
        </>
      )}
    </>
  );
}

export default BooklogDetailBookInfo;

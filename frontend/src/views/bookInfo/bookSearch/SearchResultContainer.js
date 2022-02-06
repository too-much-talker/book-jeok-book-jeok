import React, { useEffect , useState } from 'react';
import SearchResultPresenter from "./SearchResultPresenter";
import { useParams } from "react-router-dom";
import axios from "axios";


function SearchResultContainer(){
    const [page, setPage]= useState(1);
    let useParam = useParams(); //여기 안에 keyword, category 존재함
    const [orderCategory, setOrderCategory]= useState("latest");
    const [books, setBooks] = useState();
    const [totalCnt, setTotalCnt]= useState();
    const url ="http://i6a305.p.ssafy.io:8080";
    useEffect(() => {
        getBooks();
        console.log(books);
        console.log('컴포넌트가 화면에 나타남');
        return () => {
          console.log('컴포넌트가 화면에서 사라짐');
        };
      }, []);

    useEffect(() => {
      getBooks();
    }, [orderCategory]);

    useEffect(() => {
      getBooks();
    }, [page]);

    function goDetail(seq){
      document.location.href = `/detail/${seq}`;
    }

    function orderHandler(event){
      setOrderCategory(event.target.value);
    }

    function handlePageChange(event){
      setPage(event);
    }

    function getBooks(){
        axios.post(url+`/api/v1/bookinfos/` ,
        {
            page:page,
            limit:12,
            searchCategory : useParam.category, 
            searchKeyword : useParam.keyword,  
            orderCategory: orderCategory
          })
        .then(function (response){
            console.log(response.data.data.bookList)
            setTotalCnt(response.data.data.totalCnt);
            setBooks(response.data.data.bookList);
        })
        .catch(function (error) {
            console.log(error);
          }); 
    }

    return(
        <> 
       <SearchResultPresenter goDetail={goDetail} totalCnt={totalCnt} page={page} handlePageChange={handlePageChange}url={url} totalCnt={totalCnt} orderHandler={orderHandler} useParam={useParam} books={books}></SearchResultPresenter>
       </> 
    );
    }
export default SearchResultContainer;   

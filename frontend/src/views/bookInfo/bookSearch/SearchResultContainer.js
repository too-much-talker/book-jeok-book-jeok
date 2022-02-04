import React, { useEffect , useState } from 'react';
import SearchResultPresenter from "./SearchResultPresenter";
import { useParams } from "react-router-dom";
import axios from "axios";


function SearchResultContainer(){
    const [page, setPage]= useState(1);
    // const [limit, setLimit]= useState();
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

    function orderHandler(event){
      setOrderCategory(event.target.value);
    }

    function handlePageChange(event){
      setPage(event);
    }

    function getBooks(){ ///처음 시작할때
        axios.post(url+`/api/v1/bookinfos/` ,
        {
            page:page,
            limit:12,
            searchCategory : useParam.category, 
            searchKeyword : useParam.keyword,  
            // searchKeyword : "", //이건 테스트 하기 위한거
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

    // getBooks();
    return(
        <> 
       <SearchResultPresenter totalCnt={totalCnt} page={page} handlePageChange={handlePageChange}url={url} totalCnt={totalCnt} orderHandler={orderHandler} useParam={useParam} books={books}></SearchResultPresenter>
       </> 
    );
    }
export default SearchResultContainer;   

import SearchMainPresenter from "./SearchMainPresenter";
import SearchResultPresenter from "./SearchResultPresenter";
import React, { useState } from "react";
import { Route } from 'react-router-dom';
import axios from "axios";
import SearchResultContainer from "./SearchResultContainer";


function SearchMainContainer(){
    const url = "https://983856ce-2d40-4c57-a7c6-9bac4c3e441e.mock.pstmn.io";

    const [searchKeyword , setSearchKeyword ] = useState("");
    const [searchCategory, setSearchCategory]= useState("TOTAL"); 
    const [books,setBooks]= useState();
    const onSearchCategoryHandler= (event)=>{
        setSearchCategory(event.target.value);
        
    }
    const onSearchHandler = (event)=>{
        setSearchKeyword (event.currentTarget.value);
    };

    function OnSearch(){
        axios.get(url+`/api/v1/bookinfos/` ,{
            searchCategory : searchCategory, 
            searchKeyword : searchKeyword  ////이ㅣ거 어떻게 처리할건지
            })
        .then(function (response){
            setBooks(response.data.data.bookInfo);
            // return response.data.data.bookInfo; //이거 목서버랑 진짜 서버랑 다름. 그러니 수정요함.
           //document.location.href = "/bookSearch1";
            //왜 위에처럼 이동하면 데이터가 없어지지 
        })
        .catch(function (error) {
            console.log(error);
          });     
    } 

    
    function getBestSeller(){}

    return(
        <>
        <SearchMainPresenter
                onSearchHandler={onSearchHandler} 
                onSearch={OnSearch}
                onSearchCategoryHandler={onSearchCategoryHandler}>
        </SearchMainPresenter>
        {/* <SearchResultContainer books={books} searchCategory={searchCategory} searchKeyword={searchKeyword}></SearchResultContainer> */}
        </>
    );
}


export {SearchMainContainer};
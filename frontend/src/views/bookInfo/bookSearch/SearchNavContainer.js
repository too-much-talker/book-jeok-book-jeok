import SearchNavPresenter from "./SearchNavPresenter";
import React, { useState,useEffect } from 'react'
import { useParams } from "react-router-dom";
function SearchNavContainer({keyword}){
    const [searchKeyword , setSearchKeyword ] = useState("");
    const [searchCategory, setSearchCategory]= useState("total"); 
    const [KEYWORD, setKEYWORD]= useState(keyword);
    const [temp, setTemp]= useState();
    
    useEffect(() => {
        console.log(searchCategory);
      }, [searchCategory]);
    useEffect(() => {
        onSearchKeywordHandler();
    }, [temp]);

    const onSearchCategoryHandler= (event)=>{
        setSearchCategory(event.target.value);
    }
    const onSearchHandler = (event)=>{
        //setSearchKeyword (event.currentTarget.value);
        setTemp(event.currentTarget.value);
        setKEYWORD(event.currentTarget.value);
    };

    const onSearchKeywordHandler=()=>{
        if(temp!==null){
            setSearchKeyword(temp);
        }
    }

    const onSubmit=(event)=>{
        
        if(event.key === 'Enter'){
            //console.log(searchKeyword);
            let url= `/search/${searchCategory}/${searchKeyword}`;
            document.location.href = url;
        }
        
        // history.push(`/search/${searchKeyword}/${searchCategory}`); 
    }
    return(
        <SearchNavPresenter
            keyword={keyword}
            KEYWORD={KEYWORD}
            searchKeyword={searchKeyword}
            onSearchHandler={onSearchHandler} 
            onSearchCategoryHandler={onSearchCategoryHandler}
            onSubmit={onSubmit}
        ></SearchNavPresenter>

    );

}
export default SearchNavContainer;
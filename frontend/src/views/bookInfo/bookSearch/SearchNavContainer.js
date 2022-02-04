import SearchNavPresenter from "./SearchNavPresenter";
import React, { useState,useEffect } from 'react'
import { useParams } from "react-router-dom";
function SearchNavContainer({keyword}){
    const [searchKeyword , setSearchKeyword ] = useState("");
    const [searchCategory, setSearchCategory]= useState("total"); 
    const [KEYWORD, setKEYWORD]= useState(keyword);
    
    
    useEffect(() => {
        console.log(searchCategory);
      }, [searchCategory]);

    const onSearchCategoryHandler= (event)=>{
        setSearchCategory(event.target.value);
    }
    const onSearchHandler = (event)=>{
        console.log(searchKeyword);
        setSearchKeyword (event.currentTarget.value);
        setKEYWORD(event.currentTarget.value);

    };

    const onSubmit=(event)=>{
        if(event.key === 'Enter'){
            console.log(searchCategory);
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
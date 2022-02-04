import SearchNavPresenter from "./SearchNavPresenter";
import React, { useState,useEffect } from 'react'
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
            console.log(searchKeyword);
            if(searchKeyword!=="" && searchKeyword!==undefined){
                let url= `/search/${searchCategory}/${searchKeyword}`;
                document.location.href = url;
            }
            else{
                alert("검색어를 입력해주세요.")
            }

        }
        
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
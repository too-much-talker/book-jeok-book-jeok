import SearchNavPresenter from "./SearchBarPresenter";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function SearchNavContainer() {
  const navigate = useNavigate();

  const [option, setOption] = useState();

  const onChangeHandler = (e) => {
    setOption(e.currentTarget.value);
  };

  const Options = [
    { key: "default", value: "옵션" },
    { key: "keyword", value: "검색어" },
    { key: "writer", value: "글쓴이" },
  ];

  const [input, setInput] = useState("");

  const onInputChange = (e) => {
    setInput(e.currentTarget.value);
  };

  const onClickBtn = () => {
    if (
      option === "default" ||
      option === undefined ||
      input === null ||
      input === ""
    ) {
      alert("검색어와 옵션을 선택해주세요");
    } else {
      navigate(`/booklogs/search?option=${option}&keyword=${input}`);
    }
  };

  //   const onSubmit = (event) => {
  //     if (event.key === "Enter") {
  //       console.log(searchKeyword);
  //       if (searchKeyword !== "" && searchKeyword !== undefined) {
  //         let url = `/search/${searchCategory}/${searchKeyword}`;
  //         document.location.href = url;
  //       } else {
  //         alert("검색어를 입력해주세요.");
  //       }
  //     }
  //     };

  return (
    <SearchNavPresenter
      onChangeHandler={onChangeHandler}
      onInputChange={onInputChange}
      onClickBtn={onClickBtn}
      Options={Options}
    ></SearchNavPresenter>
  );
}
export default SearchNavContainer;

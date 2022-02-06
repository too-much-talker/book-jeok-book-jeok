import SearchbarPresenter from "../../../components/searchbar/SearchbarPresenter";
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
      navigate(`/booklogs/list/search?${option}=${input}`);
    }
  };

  const onEnter = (e) => {
    if (e.key == "Enter") onClickBtn();
  };

  return (
    <SearchbarPresenter
      onChangeHandler={onChangeHandler}
      onInputChange={onInputChange}
      onClickBtn={onClickBtn}
      onEnter={onEnter}
      Options={Options}
    ></SearchbarPresenter>
  );
}
export default SearchNavContainer;

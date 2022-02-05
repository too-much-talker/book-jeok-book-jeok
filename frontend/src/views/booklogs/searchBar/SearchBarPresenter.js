import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Input = styled.input`
  width: 40%;
`;

const OPTIONS = [
  { key: "default", value: "옵션" },
  { key: "keyword", value: "검색어" },
  { key: "writer", value: "글쓴이" },
];

// const SelectBox = (props) => {
//   const [option, setOption] = useState("");

//   const onChangeHandler = (e) => {
//     setOption(e.currentTarget.value);
//     console.log(option);
//   };

//   return (
//     <select onChange={onChangeHandler} value={option}>
//       {props.options.map((option) => (
//         <option
//           value={option.key}
//           defaultValue={props.defaultValue === option.key}
//         >
//           {option.value}
//         </option>
//       ))}
//     </select>
//   );
// };

function SearchBarPresenter() {
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

  return (
    <div>
      <form>
        {/* <SelectBox options={OPTIONS} defaultValue="default"></SelectBox> */}
        <select onChange={onChangeHandler} value={option}>
          {Options.map((item, index) => (
            <option key={item.key} value={item.key}>
              {item.value}
            </option>
          ))}
        </select>
        <Input
          type="text"
          placeholder="keyword"
          onChange={onInputChange}
        ></Input>
        <input type="button" onClick={onClickBtn} value="Search" />
      </form>
    </div>
  );
}

export default SearchBarPresenter;

import styled from "styled-components";
import icon from "../../../res/img/search.png";

const SearchBlock = styled.div`
  // position: absolute;
  width: 100%;
  height: 60px;
  text-align: center;
  display: inline-block;
`;

const SearchWapper = styled.span`
  border-bottom: solid 2px;
  padding-bottom: 1.5rem;
`;

const SelectBox = styled.select`
  margin-right: 1rem;
  background: url("https://freepikpsd.com/media/2019/10/down-arrow-icon-png-7-Transparent-Images.png")
    calc(100% - 5px) center no-repeat;
  background-size: 20px;
  padding: 5px 10px 5px 5px;
  border-radius: 4px;
`;

const SearchInput = styled.input`
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  //border-bottom-width: 1; // SearchWrapper 안쓸거면 얘로 변경.
  border-bottom-width: 0;

  width: 50rem;
  // height: 50px;
  :focus {
    outline: none;
  }
  font-size: 1.7rem;
`;

const SearchIcon = styled.img.attrs({
  src: icon,
})`
  width: 2.5rem;
  &:hover {
    cursor: pointer;
  }
`;

function SearchNavPresenter({
  onChangeHandler,
  onInputChange,
  onClickBtn,
  Options,
}) {
  return (
    <SearchBlock>
      <SearchWapper>
        <SelectBox defaultValue="default" onClick={onChangeHandler}>
          {Options.map((item, index) => (
            <option key={item.key} value={item.key}>
              {item.value}
            </option>
          ))}
        </SelectBox>
        <SearchInput onChange={onInputChange}></SearchInput>
        <SearchIcon onClick={onClickBtn}></SearchIcon>
      </SearchWapper>
    </SearchBlock>
  );
}

export default SearchNavPresenter;

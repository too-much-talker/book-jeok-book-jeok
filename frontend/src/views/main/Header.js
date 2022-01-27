import React from "react";
import "./Header.css";

const MenuItem = ({ active, children, to }) => (
  <div className="menu-item">{children}</div>
);

const Header = () => {
  return (
    <div>
      <div className="logo">북적북적</div>
      <div className="menu">
        <MenuItem>공지</MenuItem>
        <MenuItem>북로그</MenuItem>
        <MenuItem>독서모임</MenuItem>
        <MenuItem>챌린지</MenuItem>
        <MenuItem>책정보</MenuItem>
      </div>
    </div>
  );
};

export default Header;
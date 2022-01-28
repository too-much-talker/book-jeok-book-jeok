src/styles/fonts/fonts.js

import { createGlobalStyle } from "styled-components";
import MontHeavyDemo from "./Mont-HeavyDEMO.woff";

export default createGlobalStyle`
@font-face {
    font-family: 'Noto Sans KR';
    src:url('./fonts/noto-sans-kr.ttf') format('woff'),
        url('./fonts/noto-sans-kr.ttf') format('woff2');
    /* 이런식으로 weight를 지정해서 사용할 수도 있다!*/
    font-weight: 400;
} 
`;
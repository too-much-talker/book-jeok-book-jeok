import Grass from "./Grass";
import react, { useState } from "react";
import styled from "styled-components";

const StyledWeeds = styled.div `
  margin: 3rem;
  width: 8rem;
  height: 12rem;
`;

const Weeds = (props) => {
  let weed =[];
  let i;
  for(i=0;i<84;i++){
    const now = new Date();
    weed.unshift({
      count : 0,
      date : new Date(now.setDate(now.getDate() - i)),
    });
  }

  weed.map((data)=>{
    let sYear = data.date.getFullYear();
    let sMonth = data.date.getMonth() + 1;
    let sDate = data.date.getDate();
  
    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
    sDate  = sDate > 9 ? sDate : "0" + sDate;
    const result = sYear + "-" + sMonth + "-" + sDate;
    data.date = result;
    // if(grass.date === data.date){
    //   data.count = grass.count;
    // }
    
    // return data.date ===
  });

  console.log(weed);
  // console.log(props.datas);

  props.datas.map((data)=>{
    for(i=0;i<84;i++){
      if(data.date === weed[i].date){
        weed[i].count = data.count;
      }
    }
  })
  // console.log(weed);

  const context = weed.map((data)=>{
    return <Grass count={data.count} date={data.date}/>
  })

  return <StyledWeeds>
    {context}
  </StyledWeeds>
};
export default Weeds;
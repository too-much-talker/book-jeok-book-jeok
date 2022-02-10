import React from "react";
function Comment ({nickname,createdDate,content}){
    return(
        <>
        {nickname}
        {createdDate}
        {content}
        </>

    );
}
export default Comment;
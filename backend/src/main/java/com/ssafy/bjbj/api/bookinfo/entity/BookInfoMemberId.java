package com.ssafy.bjbj.api.bookinfo.entity;

import java.io.Serializable;
import java.util.Objects;

public class BookInfoMemberId implements Serializable {

    private Long bookInfo;
    private Long member;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInfoMemberId that = (BookInfoMemberId) o;
        return Objects.equals(bookInfo, that.bookInfo) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookInfo, member);
    }

}

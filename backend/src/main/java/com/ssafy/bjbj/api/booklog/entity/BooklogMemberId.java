package com.ssafy.bjbj.api.booklog.entity;

import java.io.Serializable;
import java.util.Objects;

public class BooklogMemberId implements Serializable {

    private Long booklog;
    private Long member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooklogMemberId that = (BooklogMemberId) o;
        return Objects.equals(booklog, that.booklog) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booklog, member);
    }

}

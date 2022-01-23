package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_info")
@Entity
public class BookInfo extends BaseLastModifiedEntity {
}

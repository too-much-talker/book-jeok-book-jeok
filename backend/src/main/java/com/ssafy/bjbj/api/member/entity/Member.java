package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.Entity;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseLastModifiedEntity {
}

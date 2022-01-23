package com.ssafy.bjbj.api.challenge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_point_history")
@Entity
public class PointHistory {
}

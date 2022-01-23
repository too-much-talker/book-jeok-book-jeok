package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.common.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_exp_history")
@Entity
public class expHistory extends BaseCreatedEntity {

}

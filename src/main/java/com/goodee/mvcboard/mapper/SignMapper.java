package com.goodee.mvcboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Sign;



@Mapper
public interface SignMapper {
	int insertSignfile(Sign sign);
}

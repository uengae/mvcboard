package com.goodee.mvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	int insertBoardfile(Boardfile boardfile);
	Boardfile selectBoardfileOne(Boardfile boardfile);
	List<Boardfile> selectBoardfile(Board board);
	int deleteFile(Boardfile boardfile);
}

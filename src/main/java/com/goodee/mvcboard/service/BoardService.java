package com.goodee.mvcboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	
	public int removeBoard(Board board) {
		return boardMapper.deleteBoard(board);
	}
	
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	public Board getBoardOne(Board board) {
		return boardMapper.selectBoardOne(board);
	}
	public Map<String, Object> getBoardList(int currentPage, int rowPerPage){
		
//		service layer 역활1 : controller가 넘겨준 매개값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage - 1) * rowPerPage;
		
//		반환값 1
		List<Map<String, Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
//		반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
//		service layer 역활2 : dao에서 반환받은 값을 가공하여 controller에 반환
//		반환값 3
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = (int)Math.ceil((double)boardCount / rowPerPage);
		
//		결과값
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		return resultMap;
	}
	
}

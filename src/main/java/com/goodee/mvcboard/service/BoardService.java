package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private BoardfileMapper boardfileMapper;
	
	public List<Map<String, Object>> getLocalNameList(){
		return boardMapper.selectLocalNameList();
	}
	
	public int addBoard(Board board, String path) {
		int row = boardMapper.insertBoard(board);
		log.debug(board.getBoardNo() + " <- boardNo");
		addFile(row, board, path);
		return row;
	}
	
	public void addFile(int row, Board board, String path) {
//		첨부된 파일이 1개 이상이 있다면
		List<MultipartFile> fileList = board.getMultipartFile(); 
		if(row == 1 && fileList != null && fileList.size() > 0) {
			log.debug(fileList.size() + " <- fileList.size()");
			int boardNo = board.getBoardNo();
			
			for(MultipartFile mf : fileList) {
				if(mf.getSize() > 0) {
					Boardfile bf = new Boardfile();
					bf.setBoardNo(boardNo);
					bf.setOriginFilename(mf.getOriginalFilename());
					bf.setFilesize(mf.getSize());
					bf.setFiletype(mf.getContentType());
					
//					확장자
					String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
					
//					새로운 이름 + 확장자
					bf.setSaveFilename(UUID.randomUUID().toString().replace("-", "") + ext);
					
//					테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					
//					파일 저장(저장위치필요 -> path변수)
//					path위치에 저장파일이름으로 빈파일을 생성
					File f = new File(path + bf.getSaveFilename());
//					빈파일에 첨부된 파일의 스트림을 주입힌다.
					try {
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
//					트랜잭션 작동을 위해 예외(try...catch강요하지 않는 예외 ex: RuntimeException) 발생이 필요하다.
						throw new RuntimeException();
					}
				}
			}
		}
	}
	
	public int removeBoard(Board board, String path) {
		List<Boardfile> list = boardfileMapper.selectBoardfile(board);
		for(Boardfile b : list) {
			removeFile(b, path);
			}
		return boardMapper.deleteBoard(board);
	}

	public int removeFile(Boardfile boardfile, String path) {
		File f = new File(path + boardfile.getSaveFilename());
		if(f.exists()) {
			f.delete();
		}
		return boardfileMapper.deleteFile(boardfile);
	}
	
	public int modifyBoard(Board board, String path) {
		int row = boardMapper.updateBoard(board);
		addFile(row, board, path);
		return row;
	}
	
	public int modifyBoardFile(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	public Board getBoardOne(Board board) {
		return boardMapper.selectBoardOne(board);
	}

	public List<Boardfile> getBoardfile(Board board) {
		return boardfileMapper.selectBoardfile(board);
	}
	
	public Boardfile getBoardfileOne(Boardfile boardfile) {
		return boardfileMapper.selectBoardfileOne(boardfile);
	}
	
	public Map<String, Object> getBoardList(int currentPage, int rowPerPage, String localName){
		
//		service layer 역활1 : controller가 넘겨준 매개값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage - 1) * rowPerPage;
		
//		반환값 1
		List<Map<String, Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("localName", localName);
		
//		반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
//		service layer 역활2 : dao에서 반환받은 값을 가공하여 controller에 반환
//		반환값 3
		int boardCount = boardMapper.selectBoardCount(localName);
		int lastPage = (int)Math.ceil((double)boardCount / rowPerPage);
		
//		결과값
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		return resultMap;
	}
	
}

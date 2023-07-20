package com.goodee.mvcboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.service.SignService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	/*
    ANSI_RESET = "\u001B[0m";
    ANSI_BLACK = "\u001B[30m";
    ANSI_RED = "\u001B[31m";
    ANSI_GREEN = "\u001B[32m";
    ANSI_YELLOW = "\u001B[33m";
    ANSI_BLUE = "\u001B[34m";
    ANSI_PURPLE = "\u001B[35m";
    ANSI_CYAN = "\u001B[36m";
    ANSI_WHITE = "\u001B[37m";
    ANSI_BLACK_BACKGROUND = "\u001B[40m";
    ANSI_RED_BACKGROUND = "\u001B[41m";
    ANSI_GREEN_BACKGROUND = "\u001B[42m";
    ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    ANSI_BLUE_BACKGROUND = "\u001B[44m";
    ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    ANSI_CYAN_BACKGROUND = "\u001B[46m";
    ANSI_WHITE_BACKGROUND = "\u001B[47m";
    */
	
//	게시글 추가
	@GetMapping("/board/addBoard")
	public String addBoard() {
		return "/board/addBoard";
	}

	@PostMapping("/board/addBoard")
//	매개값으로 request객체를 받는다 <- request api 직접호출하귀위함
	public String addBoard(HttpServletRequest request, Board board) { 
		String uploadPath = request.getServletContext().getRealPath("/upload/");
		int boardRow = boardService.addBoard(board, uploadPath);
		log.debug("addBoard boardRow : " + boardRow);
		return "redirect:/board/boardList";
	}
	
//	게시글 삭제
	@GetMapping("/board/removeBoard")
	public String removeBoard(Model model, Board board) {
		board = boardService.getBoardOne(board);
		
//		출력할 board 저장
		model.addAttribute("board", board);
		return "/board/removeBoard";
	}
	
	@PostMapping("/board/removeBoard")
	public String removeBoard(HttpServletRequest request, Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.removeBoard(board, path);
		log.debug("removeBoard row : " + row);
		return "redirect:/board/boardList";
	}
	
//	파일 삭제
	@GetMapping("/board/removeFile")
	public String removeFile(HttpServletRequest request, Boardfile boardfile) {
		String path = request.getServletContext().getRealPath("/upload/");
		log.debug(boardfile.getBoardfileNo() + " <- boardfileNo");
		boardfile = boardService.getBoardfileOne(boardfile);
		int row = boardService.removeFile(boardfile, path);
		log.debug("removeBoard row : " + row);
		return "redirect:/board/modifyBoard?boardNo=" + boardfile.getBoardNo();
	}
	
//	게시글 수정
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model, Board board) {
		board = boardService.getBoardOne(board);
		List<Boardfile> boardfileList = boardService.getBoardfile(board);
		
//		출력할 board 저장
		model.addAttribute("board", board);
		model.addAttribute("boardfileList", boardfileList);
		return "/board/modifyBoard";
	}
	
	@PostMapping("/board/modifyBoard")
	public String modifyBoard(HttpServletRequest request, Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.modifyBoard(board, path);
		log.debug("modifyBoard row : " + row);
		return "redirect:/board/boardOne?boardNo=" + board.getBoardNo();
	}
	
	
//	게시글 하나 출력
	@GetMapping("/board/boardOne")
	public String getBoardOne(Model model, Board board) {
		
		board = boardService.getBoardOne(board);
		List<Boardfile> boardfileList = boardService.getBoardfile(board);
//		출력할 board 저장
		model.addAttribute("board", board);
		model.addAttribute("boardfileList", boardfileList);
		return "/board/boardOne";
	}
//	게시클 리스트 출력
	@GetMapping("/board/boardList")
	public String boardList(Model model,
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName
							) {
		
//		log.debug("localName : " + localName);
		System.out.println("localName : " + localName);
		
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		
//		view로 넘어갈때는 다시 분리해서
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		
		model.addAttribute("currentPage", currentPage);
 		model.addAttribute("localName", localName);
		System.out.println("sysout test " + currentPage);
		log.debug("debug test " + currentPage);
		return "/board/boardList";
	}
}

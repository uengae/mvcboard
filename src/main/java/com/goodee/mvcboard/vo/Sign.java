package com.goodee.mvcboard.vo;

import lombok.Data;

@Data
public class Sign {
	private int signNo;
	private String saveFilename;
	private String saveFiletype;
	private int saveFilesize;
	private String memberId;
	private String createdate;
	private String updatedate;
}

package com.goodee.mvcboard.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import org.apache.tomcat.util.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.mvcboard.mapper.SignMapper;
import com.goodee.mvcboard.vo.Sign;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class SignService {
	@Autowired
	private SignMapper signMapper;
	
	public int addSign(String path, String sign) {
		int row = 0;
		String memberId = "user1";
		String type = sign.split(",")[0].split(";")[0].split(":")[1];
		log.debug("SignService addSign() type : " + type);
		String signBase64 = sign.split(",")[1];
		byte[] decodeSign = Base64.decodeBase64(signBase64);
		int size = decodeSign.length;
		
		Sign s = new Sign();
		s.setMemberId(memberId);
		s.setSaveFilename(UUID.randomUUID().toString().replace("-", "") + "." + type.split("/")[1]);
		s.setSaveFiletype(type);
		s.setSaveFilesize(size);
		row = signMapper.insertSignfile(s);
		
//		빈파일 생성
		File f = new File(path + s.getSaveFilename());
		
		try {
//			빈 파일에 이미지 파일 주입
			FileOutputStream fileOutputStream = new FileOutputStream(f);
			fileOutputStream.write(decodeSign);
			fileOutputStream.close();
			log.debug("SignService addSign() try catch");
		} catch (Exception e) {
			e.printStackTrace();
			
//			트랜잭션 작동을 위해 예외(try/catch를 강요하지 않는 예외: RuntimeException) 발생이 필요
			throw new RuntimeException();
		}
		return row;
	}
}

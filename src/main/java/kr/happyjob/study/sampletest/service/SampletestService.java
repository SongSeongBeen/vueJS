package kr.happyjob.study.sampletest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.happyjob.study.sampletest.model.Sam6list;



public interface SampletestService {
	
	/** 목록 */	
	public List<Sam6list> samplepage6list(Map<String, Object> paramMap) throws Exception;
	
	/** 건수 */	
	public int samplepage6listtotcant(Map<String, Object> paramMap) throws Exception;

	// 한 건 조회
	public Sam6list samplepage6selectone(Map<String, Object> paramMap);

	//등록
	public void samplepage6insert(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;

	//수정
	public void samplepage6update(Map<String, Object> paramMap, HttpServletRequest request);

	//삭제
	public void samplepage6delete(Map<String, Object> paramMap);

}

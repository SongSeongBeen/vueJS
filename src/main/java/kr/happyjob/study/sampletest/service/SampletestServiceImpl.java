package kr.happyjob.study.sampletest.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.sampletest.model.Sam6list;

import kr.happyjob.study.sampletest.dao.SampletestDao;

@Service("SampletestService")
public class SampletestServiceImpl implements SampletestService {

	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private SampletestDao sampletestDao;
	
	//프로퍼티 파일을 읽어오기 위한 부분
	@Value("${fileUpload.rootPath}")
	private String rootPath;
	
	@Value("${fileUpload.noticePath}")
	private String noticePath;
	
	@Value("${fileUpload.virtualRootPath}")
	private String virtualRootPath;
	
	/** 목록*/
	public List<Sam6list> samplepage6list(Map<String, Object> paramMap) throws Exception {
		return sampletestDao.samplepage6list(paramMap);
	}

	/** 건수*/
	public int samplepage6listtotcant(Map<String, Object> paramMap) throws Exception {
		return sampletestDao.samplepage6listtotcant(paramMap);
	}

	//한 건 조회
	public Sam6list samplepage6selectone(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return sampletestDao.samplepage6selectone(paramMap);
	}

	//등록
	public void samplepage6insert(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		//업로드 된 파일을 다루기 위한 형변환 과정 (HttpServletRequest으로는 핸들링이 안 됨) 
		//서버 상의 파일에 접근할 수 있는 객체
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		
		//실제 파일 처리
	    String itemFilePath = noticePath + File.separator; // 업로드 실제 경로 조립 (무나열생성)
	    FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, itemFilePath);
	    Map<String, Object> fileInfo = fileUtil.uploadFiles(); // 실제 업로드 처리
	    
	    //no로 넘겨야 해서 바꿔주기
	    paramMap.put("no", paramMap.get("notieceno"));
	    
	    //글번호로 한 건 조회
	    Sam6list resultone = sampletestDao.samplepage6selectone(paramMap);
	    
	    //그 전에 업로드 했었다는 얘기, 삭제 필요
	    if(!"".equals(resultone.getFile_name())){
	    	File uploadfile = new File(resultone.getPhygical_path());
	    	uploadfile.delete(); //파일 삭제
	    }
	    
	    //map.put("file_nm", file_nm);
	    //map.put("file_size", file_Size);
	    //map.put("file_loc", file_loc);
	    //map.put("fileExtension", fileExtension);
	    
	    //파일첨부 안했을 때
	    if(fileInfo == null){ //빈값 때려넣기
	    	paramMap.put("file_name","");    
		      paramMap.put("logical_path", "" );                          
		      paramMap.put("phygical_path","");    
		      paramMap.put("file_size","");    
	    }else{
	    	paramMap.put("file_name",fileInfo.get("file_nm"));    
		      paramMap.put("logical_path", virtualRootPath + File.separator + itemFilePath + fileInfo.get("file_nm") );                          
		      paramMap.put("phygical_path",fileInfo.get("file_loc"));    
		      paramMap.put("file_size",fileInfo.get("file_size"));    
	    }
	    
	    //디비처리 스타트
	     paramMap.put("file_name",fileInfo.get("file_nm"));    
	      paramMap.put("logical_path", virtualRootPath + File.separator + itemFilePath + fileInfo.get("file_nm") );                          
	      paramMap.put("phygical_path",fileInfo.get("file_loc"));    
	      paramMap.put("file_size",fileInfo.get("file_size"));    

	      logger.info("file_name : " + paramMap.get("file_name")); 
	      logger.info("logical_path : " + paramMap.get("logical_path"));   
	      logger.info("phygical_path : " + paramMap.get("phygical_path"));   
	      logger.info("file_size : " + paramMap.get("file_size"));  
		
		sampletestDao.samplepage6insert(paramMap);
	}

	//수정
	public void samplepage6update(Map<String, Object> paramMap, HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		
		sampletestDao.samplepage6update(paramMap);
	}

	//삭제
	public void samplepage6delete(Map<String, Object> paramMap) {
		
	    //no로 넘겨야 해서 바꿔주기
	    paramMap.put("no", paramMap.get("notieceno"));
	    
	    //글번호로 한 건 조회
	    Sam6list resultone = sampletestDao.samplepage6selectone(paramMap);
	    
	    //그 전에 업로드 했었다는 얘기, 삭제 필요
	    if(!"".equals(resultone.getFile_name())){
	    	File uploadfile = new File(resultone.getPhygical_path());
	    	uploadfile.delete(); //파일 삭제
	    }
		
		sampletestDao.samplepage6delete(paramMap);	
	}	


}

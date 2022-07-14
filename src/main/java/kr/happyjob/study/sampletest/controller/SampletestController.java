package kr.happyjob.study.sampletest.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.common.comnUtils.ComnCodUtil;
import kr.happyjob.study.sampletest.model.Sam6list;

import kr.happyjob.study.sampletest.service.SampletestService;;

@Controller
@RequestMapping("/sampletest/")
public class SampletestController {
   
   @Autowired // 컨트롤러 단에서 서비스를 찾아주기 위해 해놓은 어노테이션 
   // 주의 ** 클래스는 맨 앞글자 대문자! / 임포트 꼭 해주기 
   SampletestService sampletestService;
   
   // Set logger
   private final Logger logger = LogManager.getLogger(this.getClass());

   // Get class name for logger
   private final String className = this.getClass().toString();
  

   
   @RequestMapping("samplepage1.do")
   public String samplepage1(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage1";
   }
     

   @RequestMapping("samplepage2.do")
   public String samplepage2(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage2";
   }
   

   @RequestMapping("samplepage3.do")
   public String samplepage3(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage3";
   }
   

   @RequestMapping("samplepage4.do")
   public String samplepage4(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage4";
   }
   

   @RequestMapping("samplepage5.do")
   public String samplepage5(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage5";
   }
   
   

   
   
   
   
   
   
   
   @RequestMapping("samplepage6.do")
   public String samplepage6(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".samplepage6");
      logger.info("   - paramMap : " + paramMap);
      logger.info("+ End " + className + ".samplepage6");

      return "sampletest/samplepage6"; //디자인 잘 나오는지 확인하기 위해서 했던 부분
   }
     
   
   @RequestMapping("samplepage6list.do") //상단에 선언된 /sampleteat/와 연결           Object로 받는 이유? param에 들어오는 값이 int형일 수도 있고 string일수도 있으니
   public String samplepage6list(Model model, @RequestParam Map<String, Object> rparm) throws Exception {
	   //model : 다른 jsp에 던져주기 위해 사용하는 거 
	   
	   logger.info("+ Start " + className + ".samplepage6list");
	   logger.info("   - rparm : " + rparm);
	  	
	   int pagenum = Integer.parseInt((String) rparm.get("pagenum"));
	   int pagesize = Integer.parseInt((String) rparm.get("pagesize"));
	   
	   /* 형변환의 이유 
	    * 맵 형태로 받을 때,
	    * 형태가 오브젝트 타입이다보니  어떤 벨류값인지 명확하게 정의를 내려줘야하기 때문에 우선 string으로 형변환해준 거고, 
	    * 자바단에서 숫자로 작업을 해줘야하기 때문에 최종적으로 int로 바꿔줌
	    * */
	   
	   int pageindex = (pagenum - 1) * pagesize;
	   
	   rparm.put("pagesize", pagesize);
	   rparm.put("pageindex", pageindex);
	   
	   //여기까지 작업하고 나면 빈껍데기 서비스부터 만들고 시작하는 게 좋음 
	   
	   List<Sam6list> searchlist = sampletestService.samplepage6list(rparm); //목록 조회인 경우엔 list 형태로! 
	   model.addAttribute("searchlist", searchlist); //samplepage6list.jsp에 전달하기 위해서 사용 
	   
	   int totalcnt = sampletestService.samplepage6listtotcant(rparm);
	   
	   model.addAttribute("totalcnt", totalcnt);
	   
	   logger.info("+ End " + className + ".samplepage6list");	   	  
	   
	   return "sampletest/samplepage6list";
   }
   
   @RequestMapping("samplepage6selectone.do")
   @ResponseBody //내가 나한테 보내는 거라서 
   public Map<String, Object> samplepage6selectone(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {
	   
	     logger.info("+ Start " + className + ".initComnCod");
	      logger.info("   - paramMap : " + paramMap);
	      
	      //맵이 초기화하는 방법이 없어 해쉬맵으로 선택
	      Map<String, Object> returnval = new HashMap<String, Object>();

	     Sam6list resultone = sampletestService.samplepage6selectone(paramMap);
	     returnval.put("resultone", resultone);
 
	     logger.info("+ End " + className + ".initComnCod");
	     
	     return returnval;
	      
   }
   
   @RequestMapping("samplepage6save.do")
   @ResponseBody //json 형태로 받는 거니까
   public Map<String, Object> samplepage6save(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {
	   
	   logger.info("+ Start " + className + ".initComnCod");
	   logger.info("   - paramMap : " + paramMap);
	   
	   String action = (String) paramMap.get("action");
	   Map<String, Object> returnval = new HashMap<String, Object>();
	   
	   //세션에서 아이디 꺼내서 넘기기
	   paramMap.put("loginID", session.getAttribute("loginId"));
	   
//	   if(action.equals("I")){ 이렇게 적어주는 사람들 꼭 있다! 이거 위험하다. null값이 들어가면 에러다. null은 equals라는 함수를 못 쓴다.
//		   
//	   }
	   
	   if("I".equals(action)){ // 이렇게 꼭 바꿔서 적어주기
		   sampletestService.samplepage6insert(paramMap, request);
	   }else if("U".equals(action)){
		   sampletestService.samplepage6update(paramMap, request);
	   }else if("D".equals(action)){
		   sampletestService.samplepage6delete(paramMap);
	   }
	   
	   returnval.put("result", "success");
	   
	   logger.info("+ End " + className + ".initComnCod");
	     return returnval;
	      
 }
   
   @RequestMapping("samplepage6download.do")
   public void samplepage6download(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
   
      logger.info("+ Start " + className + ".samplepage6download");
      logger.info("   - paramMap : " + paramMap);
      
      // 파일정보까지 다 셀렉트 (파일이름과 피지컬 정보가 필요하다!)
      Sam6list resultone = sampletestService.samplepage6selectone(paramMap);
      													//풀 경로에 있는 파일을 읽어들임 
      					//형태를 바이트 어레이로 바꿔줌 (바이트를 어레이 형태로 읽어들인 거)
      byte fileByte[] = FileUtils.readFileToByteArray(new File(resultone.getPhygical_path()));
      
      response.setContentType("application/octet-stream");
      							//사이즈 지정
       response.setContentLength(fileByte.length);
       						//브라우저에 실제 파일이름 띄울려고 쓰는 거 						//한글 파일명은 브라우저가 인식을 못할 수도 있음 
       																		//한글을 유니코드로 바꿔줘야함 %2030SDF3844FDS < 이렇게 생긴 애
       																		//파일이 한글명인 걸 대비해서! 
       response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(resultone.getFile_name(),"UTF-8")+"\";");
       response.setHeader("Content-Transfer-Encoding", "binary");
       							//바디영역에 담아줌
       response.getOutputStream().write(fileByte);
        						//실제로 보내주는 역할
       response.getOutputStream().flush();
       response.getOutputStream().close(); //닫기

       // -> 이 정보들은 브라우저가 받는 거임 
       // 팀장님왈 공통모듈화 하는 거 고민중
       
      logger.info("+ End " + className + ".samplepage6download");
   }   

   
   
   
//   @RequestMapping("samplepage6delete.do")
//   @ResponseBody //json 형태로 받는 거니까
//   public Map<String, Object> samplepage6delete(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
//	         HttpServletResponse response, HttpSession session) throws Exception {
//	   
//	   logger.info("+ Start " + className + ".initComnCod");
//	   logger.info("   - paramMap : " + paramMap);
//	   
//	   String action = (String) paramMap.get("action");
//	   Map<String, Object> returnval = new HashMap<String, Object>();
//	  
//	   sampletestService.samplepage6delete(paramMap);
//	   
//	   returnval.put("result", "success");
//	   
//	   logger.info("+ End " + className + ".initComnCod");
//	     return returnval;      
// }

   @RequestMapping("samplepage7.do")
   public String samplepage7(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage7";
   }
   

   @RequestMapping("samplepage8.do")
   public String samplepage8(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage8";
   }
   

   @RequestMapping("samplepage9.do")
   public String samplepage9(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage9";
   }
   

   @RequestMapping("samplepage10.do")
   public String samplepage10(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
         HttpServletResponse response, HttpSession session) throws Exception {
      
      logger.info("+ Start " + className + ".initComnCod");
      logger.info("   - paramMap : " + paramMap);
      
      logger.info("+ End " + className + ".initComnCod");

      return "sampletest/samplepage10";
   }
   
   
   
   
   
   
   
   
   
   
   
}
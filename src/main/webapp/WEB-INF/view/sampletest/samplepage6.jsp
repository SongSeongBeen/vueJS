<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>SamplePage6</title>
<!-- sweet alert import -->
<script src='${CTX_PATH}/js/sweetalert/sweetalert.min.js'></script>
<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>
<!-- sweet swal import -->
                              
<script type="text/javascript">
    var pagesize = 10;
    var pagenavisize = 5;
   
    $(document).ready(function() { //1.목록조회 스타트 
    	
    	//팀장님이 만든 공통모듈
    	comcombo("JOBCD", "samc", "sel");
    
    	fn_searchlit(); //리스트 뿌리는 메소드
    	
    	fRegisterButtonClickEvent();
   });

	/** 버튼 이벤트 등록 */
	function fRegisterButtonClickEvent() {
		$('a[name=btn]').click(function(e) {
			e.preventDefault();

			var btnId = $(this).attr('id');

			switch (btnId) {
				case 'btnSearchlist' :
					fn_searchlit();
					break;
				case 'btnReg' :
					fn_reg();
					break;
				case 'btnSaveGrpCod' :
					fn_save();
					break;
				case 'btnDeleteGrpCod' :
					fn_delete();
					break;
				case 'btnCloseGrpCod' :
					gfCloseModal();
					break;
			}
		});
	}
	   
	//pagenum는 따로 선언하지 않아도 파라미터로 넣었을 때 자동으로 변수처럼 인식해준다! 자바스크립트의 기능
	function fn_searchlit(pagenum) { 
    	
    	pagenum = pagenum || 1;    	//1값을 넣어주기 위해 
    	
    	var searchoption = $("#searchoption").val();
    	var searchword = $("#searchword").val();
    	
    	/*
    	제이쿼리의 특징
    	
    	var item = "searchoption";  < 알아서 id값을 찾아준다?!
    	var index = 0;
    	var searchoption = $(item + index).val();
    						$("#searchword0").val();
    						
    	변수로 지정해서 활용도 가능하다!
    	*/
    	
    	var param = {
    			pagenum : pagenum,
    			pagesize : pagesize,
    			searchoption :searchoption,
    			searchword : searchword
    	}
    	
    	var listcollback = function(returndata) { // 응답 받았던 데이터를 파라매터로 받음 
    		fn_listcallback(returndata, pagenum); //리스트를 가져오게끔 응답받는 함수 
    	}
    	
    	callAjax("/sampletest/samplepage6list.do", "post", "text", true, param, listcollback); 
    	// 어씽크 방식 : 값을 받으면 그때 처리 하겠다  씽크 방식 : 응답 올 때까지 다른 일 안 하고 응답만 기다리겠다 
    	// true가 어씽크! 안 답답하게 저 방식을 주로 쓴다
    }
    
    function fn_listcallback(returndata, pagenum) {

    	console.log("fn_listcallback : " + returndata);
    	
    	$("#listnotice").empty().append(returndata); //바디영역을 다 지운 후에 리턴받은 리스트를 낑겨넣어라 
    	
    	var totalcnt = $("#totalcnt").val(); //리스트jsp에서 히든으로 넘겼던 총 건수값 

		var paginationHtml = getPaginationHtml(pagenum, totalcnt, pagesize, pagenavisize, 'fn_searchlit'); //페이지 네비게이션을 만들어주는 공통함수 
		console.log("paginationHtml : " + paginationHtml);
		//swal(paginationHtml);
		$("#listnation").empty().append( paginationHtml ); //아래의 div 영역에 네비게이션을 끼워넣겠다 
		
		// 현재 페이지 설정
		//$("#currentPageComnGrpCod").val(currentPage);
    	
    	
    	
    	
    	//console.log("fn_listcallback totalcnt : " + $("#totalcnt").val());
    }
    
    function fn_reg(no){
    	
    	if(no == null || no == "" || no == undefined){ // 빈 값이면(등록버튼을 누른거면) 화면 띄운다!
    		$("#action").val("I");
    		
    		//화면 초기화 
    		fn_init();
    		
    		gfModalPop("#regform");
    		
    	}else{
    		$("#action").val("U");
    		
    		//alert(no);
    		//한 건 처리
    		fn_selectone(no);
    	}
    	
    	
    	
    }
    
    function fn_selectone(no){
    	
    	var param = {
    			no:no 			
    	} 
    	
    	var selectonecallback = function(returndata){
    		fn_selectoneproc(returndata);
    	}
    															//한 건 조회하서 리턴형태 json
    	callAjax("/sampletest/samplepage6selectone.do", "post", "json", true, param, selectonecallback); 
    	
    }
    
    function fn_selectoneproc(returndata){
    	
    	console.log("fn_selectone : " + JSON.stringify(returndata));
    	
    	fn_init(returndata.resultone);
    }
    
    function fn_init(object){
    	
    	//등록
    	if(object == null || object == "" || object == undefined){
    		
    		$("#title").val(""); //빈값 넣었으니 초기화
    		$("#cont").val("");
    		$("#noticeno").val("");
    		$("#btnDeleteGrpCod").hide();
    		$('#selfile').val(''); // 파일초기화 
    		
    	//수정	
    	}else{
    		
    		$("#title").val(object.ntc_title);
    		$("#cont").val(object.ntc_content);
    		
    		$("#noticeno").val(object.ntc_no);
    		
    		
    		if(object.file_name == null || object.file_name == ""){
    			$('#filedis').empty();
    		}else{

    			var jbSplit = object.file_name.split('.');
    			var insertdiv = "";
    			
    			if(jbSplit[1] == "jpg" || jbSplit[1] == "png" || jbSplit == "gif"){
    				
    				var insertdiv = "<a href='javascript:fn_filedownload()'>" + "<img style='width:200px' id='filedisimg' name='filedisimg' src='" + object.logical_path + "'>" + "</a>";
    				
    				//html append하기
    				// "<img style='width:200px' id='filedisimg' src=''>" 더블 쿼테이션 싱글로 바꿔주기
    				
    				//$('#filedisimg').attr("src", object.logical_path);
    			}else{
    				
    				 insertdiv = "<a href='javascript:fn_filedownload()'>" + object.file_name + "</a>";
    				 
    				//$('#filedis').empty().append(object.file_name);
    			}
    			
    			$('#filedis').empty().append(insertdiv);

    		}
    		
    		
    		$("#btnDeleteGrpCod").show();
    		
    		gfModalPop("#regform");
    		
    	}
    }
    
    function fn_save(){
    	var title = $("#title").val();
    	var cont = $("#cont").val();
    	var noticeno = $("#noticeno").val();
    	var action = $("#action").val();
    	
    	var param = {
    		title : title, 		//title : $("#title").val 가능
    		cont : cont,
    		noticeno : noticeno,
    		action : action
    	} 
    	
    	var frm = document.getElementById("noticeform");
        frm.enctype = 'multipart/form-data';
        var fileData = new FormData(frm);
    	
    	var savecallback = function(returndata){
    		
    		console.log("fn_save : " + JSON.stringify(returndata));
    		
    		if(returndata.result == "success"){
    			alert("저장 되었습니다");

    			gfCloseModal();
    			fn_searchlit();
    		}
    		
    		
    		//fn_selectoneproc(returndata);
    	}
    															//한 건 조회하서 리턴형태 json
    	//callAjax("/sampletest/samplepage6save.do", "post", "json", true, param, savecallback); 
    	callAjaxFileUploadSetFormData("/sampletest/samplepage6save.do", "post", "json", true, fileData, savecallback);
										
    															
    }
    
    function fn_delete(){
    	
    	if(!confirm("삭제 하겠습니까?")){
    		return;
    	}
    	
    	var noticeno = $("#noticeno").val();
    	
    	var param = {
        		noticeno : noticeno,
        		action : "D"
        	} 
    	
		var deletecallback = function(returndata){
    		
    		console.log("fn_delete : " + JSON.stringify(returndata));
    		
    		if(returndata.result == "success"){
    			alert("삭제 되었습니다");

    			gfCloseModal();
    			fn_searchlit();
    		}
    		
    	}
    	
    	callAjax("/sampletest/samplepage6save.do", "post", "json", true, param, deletecallback); 
    															//한 건 조회하서 리턴형태 json
    	//callAjax("/sampletest/samplepage6delete.do", "post", "json", true, param, deletecallback); 
    															
    }
    
    function fn_filedownload() {
        var params = "";
        params += "<input type='hidden' name='no' value='"+ $("#noticeno").val() +"' />";
        
        jQuery("<form action='/sampletest/samplepage6download.do' method='post'>"+params+"</form>").appendTo('body').submit().remove();
     }

    
</script>

</head>
<body>
	<form id="noticeform">
	
	<input type="hidden" id="action" name="action" value="" />
	<input type="hidden" id="noticeno" name="noticeno" value="" />
	
   <!-- 모달 배경 -->
   <div id="mask"></div>

   <div id="wrap_area">

      <h2 class="hidden">header 영역</h2>
      <jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

      <h2 class="hidden">컨텐츠 영역</h2>
      <div id="container">
         <ul>
            <li class="lnb">
               <!-- lnb 영역 --> <jsp:include
                  page="/WEB-INF/view/common/lnbMenu.jsp"></jsp:include> <!--// lnb 영역 -->
            </li>
            <li class="contents">
               <!-- contents -->
               <h3 class="hidden">contents 영역</h3> <!-- content -->
               <div class="content">

                  <p class="Location">
                     <a href="../dashboard/dashboard.do" class="btn_set home">메인으로</a> <span
                        class="btn_nav bold">Sample</span> <span class="btn_nav bold">SampleTest3
                        </span> <a href="/sampletest/samplepage3.do" class="btn_set refresh">새로고침</a>
                  </p>
                  <table style="margin-top: 10px" width="100%" cellpadding="5" cellspacing="0" border="1"
                        align="left"
                        style="collapse; border: 1px #50bcdf;">
                        <tr style="border: 0px; border-color: blue">
                           <td width="80" height="25" style="font-size: 120%;">&nbsp;&nbsp;</td>
                           <td width="50" height="25" style="font-size: 100%; text-align:left; padding-right:25px;">
     	                       <select id="searchoption" name="searchoption" style="width: 150px;" v-model="searchKey">
									<option value="" >전체</option>
									<option value="title" >제목</option>
									<option value="cont" >내용</option>
							  </select> 
								
								<select id="samc" name="samc" style="width: 150px;" ></select>
								
     	                       <input type="text" style="width: 300px; height: 25px;" id="searchword" name="searchword">                    
	                           <a href="" class="btnType blue" id="btnSearchlist" name="btn"><span>검  색</span></a>	                                             
	                           <a href="" class="btnType blue" id="btnReg" name="btn"><span>등  록</span></a>
                           </td> 
                           
                        </tr>
                     </table>   
                      <br>
	                 <table class="col">
							<caption>caption</caption>
							<colgroup>
								<col width="6%">
								<col width="54%">
								<col width="20%">
								<col width="20%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">글번호</th>
									<th scope="col">제목</th>
									<th scope="col">등록일자</th>
									<th scope="col">작성자</th>
								</tr>
							</thead>
							<tbody id="listnotice"></tbody>
		                 </table>
                  
                         <div class="paging_area"  id="listnation"> </div>
                  
                  
           </div> <!--// content -->

               <h3 class="hidden">풋터 영역</h3>
               <jsp:include page="/WEB-INF/view/common/footer.jsp"></jsp:include>
            </li>
         </ul>
      </div>
   </div>

   <!--// 모달팝업 -->
   <div id="regform" class="layerPop layerType2" style="width: 600px;">
    		<dl>
			<dt>
				<strong>공지사항 관리</strong>
			</dt>
			<dd class="content">
				<!-- s : 여기에 내용입력 -->
				<table class="row">
					<caption>caption</caption>
					<colgroup>
						<col width="120px">
						<col width="*">
						<col width="120px">
						<col width="*">
					</colgroup>

					<tbody>
						<tr>
							<th scope="row">제목<span class="font_red">*</span></th>
							<td colspan="3"><input type="text" class="inputTxt p100" name="title" id="title" /></td>
						</tr>
						<tr>
							<th scope="row">내용 <span class="font_red">*</span></th>
							<td colspan="3">
							<textarea id="cont" name="cont"></textarea>
							</td>
						</tr>
						<tr>
							<th scope="row">파일 <span class="font_red">*</span></th>
							<td>
								<input type="file" id="selfile" name="selfile"> 
							</td>
							<td>
								<div id="filedis" name="filedis"></div>
							</td>
						</tr>
				
					
					</tbody>
				</table>

				<!-- e : 여기에 내용입력 -->

				<div class="btn_areaC mt30">
					<a href="" class="btnType blue" id="btnSaveGrpCod" name="btn"><span>저장</span></a> 
					<a href="" class="btnType blue" id="btnDeleteGrpCod" name="btn"><span>삭제</span></a> 
					<a href=""	class="btnType gray"  id="btnCloseGrpCod" name="btn"><span>취소</span></a>
				</div>
			</dd>
		</dl>
		<a href="" class="closePop"><span class="hidden">닫기</span></a>
   </div>
   </form>
</body>
</html>
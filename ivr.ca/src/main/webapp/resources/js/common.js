/**고객챗팅 탭**/
$(function() { 
	$(".chat_list").hide(); 
		$(".chat_list:first").show(); 
		$("ul.chatRoom li").click(function() { 
		$("ul.chatRoom li").removeClass("active").css("color","#b5b5b9"); 

		$(this).addClass("active").css("color","#fff"); 
		$(".chat_list").hide() 
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).fadeIn() 
	}); 
}); 



/**메뉴 탭**/

$(function() { 
	$(".lnbmenu").hide(); 
		$(".lnbmenu:first").show(); 
		$("ul.topmenu li").click(function() { 
		$("ul.topmenu li").removeClass("active").css("color","#fff"); 

		$(this).addClass("active").css("color","#fff"); 
		$(".lnbmenu").hide() 
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).fadeIn() 
	}); 
}); 



/**카테고리 탭**/

$(function() { 
	$(".tab_content").hide(); 
		$(".tab_content:first").show(); 
		$("ul.tabs li").click(function() { 
		$("ul.tabs li").removeClass("active").css("color","#fff"); 

		$(this).addClass("active").css("color","#fff"); 
		$(".tab_content").hide() 
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).fadeIn() 
	}); 
}); 

/**옵션 탭**/

$(function() { 
	$(".optab_content").hide(); 
		$(".optab_content:first").show(); 
		$("ul.optabs li").click(function() { 
		$("ul.optabs li").removeClass("active").css("color","#fff"); 

		$(this).addClass("active").css("color","#fff"); 
		$(".optab_content").hide() 
		var activeTab = $(this).attr("rel"); 
		$("#"+activeTab).fadeIn() 
	}); 
}); 


/**타이틀 상단 고정**/

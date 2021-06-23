app.system.report = {
	index:function(url) {
		document.search_frm['param[DT]'].value = app.util.formatDate(new Date());
		
		var $list = $('#lists');
		this.search = function() {
			if(!document.search_frm['param[DT]'].value) {
				alert('날짜를 입력하십시요.');
				return;
			}
			
			app.form.unformat(document.search_frm);
			app.request.page('/system/report/daily', $(document.search_frm).serialize(), function(result) {
				app.form.format(document.search_frm);
				$list.children().remove();
				$list.html(result);
			});
		};
		
		$('#btn_mail').on('click', function(e) {
			$('#popupBox').fadeIn();
		});
		
		$('#btn_close, #btn_cancel, #popupDimed').on('click', function(e) {
			$('#popupBox').fadeOut();
		});
		
		$('#btn_send').on('click', function(e) {
			if(!document.search_frm['param[DT]'].value) {
				alert('날짜를 입력하십시요.');
				return;
			}

			var empNr = document.getElementById('empNr');
			if(!empNr.value) {
				alert('직번을 입력하십시요.');
				return;
			}
			var name = document.getElementById('personal');
			if(!name.value) {
				alert('이름을 입력하십시요.');
				return;
			}
			document.search_frm['param[NAME]'].value = name.value;
			document.search_frm['param[EMP_NR]'].value = empNr.value;
			
			app.form.unformat(document.search_frm);
			app.request.get('/system/report/mail', $(document.search_frm).serialize(), function(result) {
				app.form.format(document.search_frm);
				if(result.status == 200)
					alert('메일이 정상적으로 발송되었습니다.');
				else
					alert(result.message);
				$('#popupBox').fadeOut();
			});
		});
	}
};
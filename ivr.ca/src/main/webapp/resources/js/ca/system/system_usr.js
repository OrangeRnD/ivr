app.system.usr = {
	index:function(url) {
		var frm = document.frm;
		var $frm = $(frm);
		var $btn_new = $('#btn_new');
		$btn_new.on('click', function(e) {
			frm.usrId.value = '0';
			$btn_new.parent().find('.view').hide();
			$('#regrNm').parent().hide();
			frm.reset();
		});
		$('#btn_add').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			
			if(!confirm('추가하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/system/usr/add', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 직번이 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('등록되었습니다.');
				app.system.usr.search(1, $frm);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_update').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			if(!confirm('수정하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/system/usr/update', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 직번이 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('수정되었습니다.');
				app.system.usr.search(1, $frm);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_delete').on('click', function(e) {
			if(!confirm('삭제하시겠습니까?')) return;
			app.request.submit('/system/usr/delete', $frm, function(result) {
				alert('삭제되었습니다.');
				app.system.usr.search(1, $frm);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		
		app.system.usr.search(1, $frm);
	},
	search:function(pageNo, $frm) {
		$frm || ($frm = $(document.frm));
		var $target = $('#lists');
		app.request.page('/system/usr/list', 'pageNo='+pageNo, function(result) {
			$target.children().remove();
			$target.html(result);
			app.form.format(frm);
			app.system.usr.list($frm);
		});
	},
	list:function($frm) {
		var $rows = $('#lists').find('tr');
		$rows.on('click', function(e) {
			if(e.target.className && e.target.className == 'txt lgn') {
				window.open(app.constants.webappRoot + '/system/lgnhist/popup?nr=' + e.target.getAttribute('data-nr'));
			}
			if(this.className == 'selectable active' || !this.getAttribute('data-id')) return;

			var _this = this;
			app.request.get('/system/usr/view', 'id='+this.getAttribute('data-id'), function(result) {
				if(result.status == 404) {
					alert('해당 데이터를 찾을 수 없습니다. 다시 한번 시도해 주십시요.');
					return;
				}

				$rows.removeClass('active');
				_this.className = 'selectable active';
				
				var frm = document.frm;
				frm.usrId.value = result.data.usrId;
				frm.empNr.value = result.data.empNr;
				frm.empNm.value = result.data.empNm;
				
				frm.email.value = result.data.email;
				frm.telno.value = app.util.formatTel(result.data.telno);
				
				if(result.data.authyGrpCd == '1')
					document.getElementById('grp1').checked = true;
				else if(result.data.authyGrpCd == '2')
					document.getElementById('grp2').checked = true;
				else if(result.data.authyGrpCd == '3')
					document.getElementById('grp3').checked = true;
				else
					document.getElementById('grp4').checked = true;
				
				if(result.data.dlyRptRcptnAltv == 0)
					document.getElementById('dlyRptRcptnAltv0').checked = true;
				else
					document.getElementById('dlyRptRcptnAltv1').checked = true;
				
				if(result.data.smsNotiftnXmsnAltv)
					frm.smsNotiftnXmsnAltv.checked = true;
				else
					frm.smsNotiftnXmsnAltv.checked = false;
				if(result.data.emailNotiftnXmsnAltv)
					frm.emailNotiftnXmsnAltv.checked = true;
				else
					frm.emailNotiftnXmsnAltv.checked = false;
				
				var regnDt = new Date();
				regnDt.setTime(result.data.regnDt);
				$('#regrNm').html(result.data.regrNm + ' / ' + app.util.formatTimestamp(regnDt)).parent().show();

				var lstUpdtDt = new Date();
				lstUpdtDt.setTime(result.data.lstUpdtDt);
				$('#lstUpdusrNm').html(result.data.lstUpdusrNm + ' / ' + app.util.formatTimestamp(lstUpdtDt));
				
				$('#btn_add').parent().find('.view').show();
			});
		});
	}
};
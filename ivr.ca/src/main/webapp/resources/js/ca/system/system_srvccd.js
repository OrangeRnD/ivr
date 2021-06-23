app.system.srvccd = {
	index:function(url) {
		var frm = document.frm;
		var $frm = $(frm);
		var $btn_new = $('#btn_new');
		$btn_new.on('click', function(e) {
			frm.oldSrvcCd.value = '';
			$btn_new.parent().find('.view').hide();
			$('#regrNm').parent().hide();
			frm.reset();
		});
		$('#btn_add').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			
			if(!confirm('추가하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/system/srvccd/add', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 코드가 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('등록되었습니다.');
				app.system.srvccd.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_update').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			if(!confirm('수정하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/system/srvccd/update', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 코드가 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('수정되었습니다.');
				app.system.srvccd.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_delete').on('click', function(e) {
			if(!confirm('삭제하시겠습니까?')) return;
			app.request.submit('/system/srvccd/delete', $frm, function(result) {
				alert('삭제되었습니다.');
				app.system.srvccd.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});

		this.search = function(url, pageNo) {
			app.system.srvccd.search(1);
		};
		
		app.system.srvccd.search(1);
	},
	search:function(pageNo, $frm) {
		$frm || ($frm = $(document.search_frm));
		
		$frm[0].pageNo.value = pageNo || 1;
		var $target = $('#lists');
		app.request.page('/system/srvccd/list', $frm.serialize(), function(result) {
			$target.children().remove();
			$target.html(result);
			app.form.format(frm);
			app.system.srvccd.list($frm);
		});
	},
	list:function($frm) {
		var $rows = $('#lists').find('tr');
		$rows.on('click', function(e) {
			if(this.className == 'selectable active' || !this.getAttribute('data-id')) return;
			
			var _this = this;
			app.request.get('/system/srvccd/view', 'id='+this.getAttribute('data-id'), function(result) {
				if(result.status == 404) {
					alert('해당 데이터를 찾을 수 없습니다. 다시 한번 시도해 주십시요.');
					return;
				}

				$rows.removeClass('active');
				_this.className = 'selectable active';
				
				var frm = document.frm;
				frm.oldSrvcCd.value = result.data.srvcCd;
				frm.srvcCd.value = result.data.srvcCd;
				frm.srvcNm.value = result.data.srvcNm;
				if(result.data.monitoringYn == '1')
					document.getElementById('monitoringYn1').checked = true;
				else
					document.getElementById('monitoringYn0').checked = true;
				
				var regnDt = new Date();
				regnDt.setTime(result.data.regnDt);
				$('#regrNm').html(app.util.formatTimestamp(regnDt)).parent().show();

				$('#btn_add').parent().find('.view').show();
			});
		});
		$('#paging').find('a').on('click', function(e) {
			app.system.srvccd.search(parseInt(this.getAttribute('data-no'), 10));
		});
	}
};
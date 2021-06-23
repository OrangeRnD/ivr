app.system.cdclsfctn = {
	index:function(url) {

		var $search_frm = $(document.search_frm);
		this.search = function() {
			app.system.cdclsfctn.search(1, $search_frm);
		};
		
		//코드분류
		var $cls_edit = $('#cls_edit');
		var $cd_edit = $('#cd_edit');
		$('#btn_cls_add').on('click', function(e) {
			$cls_edit.slideDown();
			$cd_edit.hide();
		});
		$('#btn_close1').on('click', function(e) {
			$cls_edit.slideUp();
		});
		
		$('#btn_cd_add').on('click', function(e) {
			$cd_edit.slideDown();
			$cls_edit.hide();
		});
		$('#btn_close2').on('click', function(e) {
			$cd_edit.slideUp();
		});
		
		var frm1 = document.frm1;
		var $frm1 = $(frm1);
		var $btn_new1 = $('#btn_new1');
		$btn_new1.on('click', function(e) {
			$btn_new1.parent().find('.view').hide();
			$('#regrNm1').parent().hide();
			frm1.reset();
			frm.cdClsfctn.readonly = false;
		});
		$('#btn_add1').on('click', function(e) {
			if(!app.validation.init($frm1)) return;
			if(!confirm('추가하시겠습니까?')) return;
			
			app.form.unformat(frm1);
			app.request.submit('/system/cdclsfctn/add', $frm1, function(result) {
				if(result.status == 409) {
					alert('동일한 코드분류가 존재합니다.');
					app.form.format(frm);
					return;
				}
				alert('등록되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new1.trigger('click');
				}, 100);
				$cls_edit.slideUp();
			});
		});
		$('#btn_update1').on('click', function(e) {
			if(!app.validation.init($frm1)) return;
			if(!confirm('수정하시겠습니까?')) return;
			
			app.form.unformat(frm1);
			app.request.submit('/system/cdclsfctn/update', $frm1, function(result) {
				alert('수정되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new1.trigger('click');
				}, 100);
				$cls_edit.slideUp();
			});
		});
		$('#btn_delete1').on('click', function(e) {
			if(!confirm('삭제하시겠습니까?')) return;
			app.request.submit('/system/cdclsfctn/delete', $frm1, function(result) {
				alert('삭제되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new1.trigger('click');
				}, 100);
				$cls_edit.slideUp();
			});
		});
		
		var frm2 = document.frm2;
		var $frm2 = $(frm2);
		var $btn_new2 = $('#btn_new2');
		$btn_new2.on('click', function(e) {
			frm2.cdId.value = '0';
			$btn_new2.parent().find('.view').hide();
			$('#regrNm2').parent().hide();
			frm2.reset();
		});
		$('#btn_add2').on('click', function(e) {
			if(!app.validation.init($frm2)) return;
			if(!confirm('추가하시겠습니까?')) return;
			
			app.form.unformat(frm2);
			app.request.submit('/system/cd/add', $frm2, function(result) {
				alert('등록되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new2.trigger('click');
				}, 100);
			});
		});
		$('#btn_update2').on('click', function(e) {
			if(!app.validation.init($frm2)) return;
			if(!confirm('수정하시겠습니까?')) return;
			
			app.form.unformat(frm2);
			app.request.submit('/system/cd/update', $frm2, function(result) {
				alert('수정되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new2.trigger('click');
				}, 100);
				$cd_edit.slideUp();
			});
		});
		$('#btn_delete2').on('click', function(e) {
			if(!confirm('삭제하시겠습니까?')) return;
			app.request.submit('/system/cd/delete', $frm2, function(result) {
				alert('삭제되었습니다.');
				app.system.cdclsfctn.search(1, $search_frm);
				window.setTimeout(function() {
					$btn_new2.trigger('click');
				}, 100);
				$cd_edit.slideUp();
			});
		});
		
		app.system.cdclsfctn.list($search_frm);
	},
	search:function(pageNo, $frm) {
		$frm[0].pageNo.value = pageNo;
		
		var $target = $('#lists');
		app.request.page('/system/cdclsfctn/list', $frm.serialize(), function(result) {
			$target.children().remove();
			$target.html(result);
			app.system.cdclsfctn.list($frm);
		});
	},
	list:function($frm) {
		$('#paging').find('a').on('click', function(e) {
			app.system.cdclsfctn.search(parseInt(this.getAttribute('data-no'), 10), $frm);
		});
		$('#body_list').find('a').on('click', function(e) {
			var id = this.getAttribute('data-id');
			if(id) {
				app.request.get('/system/cdclsfctn/view', 'id='+id, function(result) {
					if(result.status == 404) {
						alert('해당 데이터를 찾을 수 없습니다. 다시 한번 시도해 주십시요.');
						return;
					}
					var frm = document.frm1;
					frm.cdClsfctn.value = result.data.cdClsfctn;
					frm.cdClsfctnNm.value = result.data.cdClsfctnNm;
					
					var regnDt = new Date();
					regnDt.setTime(result.data.regnDt);
					$('#regrNm1').html(result.data.regrNm + ' / ' + app.util.formatTimestamp(regnDt)).parent().show();

					var lstUpdtDt = new Date();
					lstUpdtDt.setTime(result.data.lstUpdtDt);
					$('#lstUpdusrNm1').html(result.data.lstUpdusrNm + ' / ' + app.util.formatTimestamp(lstUpdtDt));
					
					$('#cls_edit').slideDown();
					$('#cd_edit').hide();
					
					$('#btn_add1').parent().find('.view').show();
				});
			} else {
				var cd = this.getAttribute('data-cd');
				if(cd) {
					app.request.get('/system/cd/view', 'id='+cd, function(result) {
						if(result.status == 404) {
							alert('해당 데이터를 찾을 수 없습니다. 다시 한번 시도해 주십시요.');
							return;
						}
						var frm = document.frm2;
						frm.cdId.value = result.data.cdId;
						frm.cdClsfctn.value = result.data.cdClsfctn;
						frm.cdNm.value = result.data.cdNm;
						frm.cd.value = result.data.cd;
						frm.sortOrdr.value = result.data.sortOrdr;
						frm.useAltv.value = result.data.useAltv;
						
						var regnDt = new Date();
						regnDt.setTime(result.data.regnDt);
						$('#regrNm2').html(result.data.regrNm + ' / ' + app.util.formatTimestamp(regnDt)).parent().show();

						var lstUpdtDt = new Date();
						lstUpdtDt.setTime(result.data.lstUpdtDt);
						$('#lstUpdusrNm2').html(result.data.lstUpdusrNm + ' / ' + app.util.formatTimestamp(lstUpdtDt));

						$('#cd_edit').slideDown();
						$('#cls_edit').hide();
						
						$('#btn_add2').parent().find('.view').show();
					});
				}
			}
		});
	}
};
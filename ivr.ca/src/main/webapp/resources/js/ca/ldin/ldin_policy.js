app.ldin.policy = {
	index:function(url) {
		var frm = document.frm;
		var $frm = $(frm);
		
		var $sms_cn = $('#sms_cn');
		var $smsXmsnCn = $(frm.smsXmsnCn);
		$smsXmsnCn.on('change blur', function(e) {
			var value = this.value;
			var len = app.util.byteLenth(value);
			if(len > 80) {
				value = app.util.substringByte(value, 80);
				len = app.util.byteLenth(value);
				this.value = value;
			}
			$sms_cn.html('(' + len + ' byte)');
		}).on('keyup', function(e) {
			$sms_cn.html('(' + app.util.byteLenth(this.value) + ' byte)');
		});
		
		$(frm.anlysPlcyNm).on('blur', function(e) {
			if(this.value && !frm.smsXmsnCn.value) {
				frm.smsXmsnCn.value = '고객성향분석 알림 (' + this.value + ')';
				$smsXmsnCn.trigger('blur');
			}
		});
		
		//전일인 경우 처리
		var disabled = function(b) {
			frm.exclsnStdval.value = '';
			frm.exclsnStdval.disabled = b;
			frm.bizdtSecd[0].disabled = b;
			frm.bizdtSecd[1].disabled = b;
			frm.bizdtSecd[2].disabled = b;
		};
		disabled(true);
		
		$(frm.prdSecd).on('click', function(e) {
			if(this.value == '1' || this.value == '5' || this.value == '6' || this.value == '7') {
				disabled(true);
			} else {
				disabled(false);
			}
		});
		
		var $btn_new = $('#btn_new');
		$btn_new.on('click', function(e) {
			frm.anlysPlcyId.value = '0';
			$btn_new.parent().find('.view').hide();
			$('#regrNm').parent().hide();
			$sms_cn.html('');
			frm.reset();
		});
		$('#btn_add').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			if(!confirm('추가하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/ldin/policy/add', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 설정값이 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('등록되었습니다.');
				app.ldin.policy.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_update').on('click', function(e) {
			if(!app.validation.init($frm)) return;
			if(!confirm('수정하시겠습니까?')) return;
			
			app.form.unformat(frm);
			app.request.submit('/ldin/policy/update', $frm, function(result) {
				if(result.status == 409) {
					alert('동일한 설정값이 존재합니다.');
					app.form.format(frm);
					return;
				}
				
				alert('수정되었습니다.');
				app.ldin.policy.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		$('#btn_delete').on('click', function(e) {
			if(!confirm('삭제하시겠습니까?')) return;
			app.request.submit('/ldin/policy/delete', $frm, function(result) {
				alert('삭제되었습니다.');
				app.ldin.policy.search(1);
				window.setTimeout(function() {
					$btn_new.trigger('click');
				}, 100);
			});
		});
		
		/*app.ldin.policy.list($frm);*/
	},
	search:function(pageNo) {
		var $target = $('#policy_lists');
		app.request.page('/ldin/policy/list', 'pageNo='+pageNo, function(result) {
			$target.children().remove();
			$target.html(result);
			app.ldin.policy.list();
		});
	},
	list:function() {
		$('#paging2').find('a').on('click', function(e) {
			app.ldin.policy.search(parseInt(this.getAttribute('data-no'), 10));
		});
		var $rows = $('#tblList').find('tr');
		$rows.on('click', function(e) {
			if(this.className == 'selectable active' || !this.getAttribute('data-id')) return;
			var _this = this;
			app.request.get('/ldin/policy/view', 'id='+this.getAttribute('data-id'), function(result) {
				if(result.status == 404) {
					alert('해당 데이터를 찾을 수 없습니다. 다시 한번 시도해 주십시요.');
					return;
				}
				
				$rows.removeClass('active');
				_this.className = 'selectable active';
				
				var frm = document.frm;
				frm.anlysPlcyId.value = result.data.anlysPlcyId;
				frm.anlysPlcyNm.value = result.data.anlysPlcyNm;
				frm.exclsnStdval.value = result.data.exclsnStdval;
				frm.notiftnStdval.value = result.data.notiftnStdval;
				
				var disabled = function(b) {
					if(b) frm.exclsnStdval.value = '';
					frm.exclsnStdval.disabled = b;
					frm.bizdtSecd[0].disabled = b;
					frm.bizdtSecd[1].disabled = b;
					frm.bizdtSecd[2].disabled = b;
				};
								
				if(result.data.prdSecd == '1') {
					document.getElementById('prdSecd1').checked = true;
					disabled(true);
				} else if(result.data.prdSecd == '2') {
					document.getElementById('prdSecd2').checked = true;
					disabled(false);
				} else if(result.data.prdSecd == '3') {
					document.getElementById('prdSecd3').checked = true;
					disabled(false);
				} else if(result.data.prdSecd == '4') {
					document.getElementById('prdSecd4').checked = true;
					disabled(false);
				} else if(result.data.prdSecd == '5') {
					document.getElementById('prdSecd5').checked = true;
					disabled(true);
				} else if(result.data.prdSecd == '6') {
					document.getElementById('prdSecd6').checked = true;
					disabled(true);
				} else if(result.data.prdSecd == '7') {
					document.getElementById('prdSecd7').checked = true;
					disabled(true);
				} 
				
				if(result.data.bizdtSecd == '1')
					document.getElementById('bizdtSecd1').checked = true;
				else if(result.data.bizdtSecd == '2')
					document.getElementById('bizdtSecd2').checked = true;
				else if(result.data.bizdtSecd == '3')
					document.getElementById('bizdtSecd3').checked = true;
				
				frm.smsXmsnCn.value = result.data.smsXmsnCn;
				
				$(frm.smsXmsnCn).trigger('blur');
				
				if(result.data.emailXmsnAltv)
					frm.emailXmsnAltv.checked = true;
				else
					frm.emailXmsnAltv.checked = false;
				if(result.data.smsXmsnAltv)
					frm.smsXmsnAltv.checked = true;
				else
					frm.smsXmsnAltv.checked = false;
				
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

app.ldin.policy.result = {
	index:function(url) {
		var $frm = $(document.search_frm);
		if(!$frm.attr('data-session-type'))
			app.util.period($('#lately_week'), 'lately_week');
		else {
			if($frm.attr('data-session-type') == '0') {
				$frm[0]['param[FROM_DT]'].value = app.util.formatDate($frm[0]['param[FROM_DT]'].value, '-');
				$frm[0]['param[TO_DT]'].value = app.util.formatDate($frm[0]['param[TO_DT]'].value, '-');
				$('#std_input').prop('checked', true);
			} else if($frm.attr('data-session-type') == '3')
				app.util.period($('#lately_week'), 'lately_week');
			else if($frm.attr('data-session-type') == '4')
				app.util.period($('#lately_month'), 'lately_month');
			else if($frm.attr('data-session-type') == '5')
				app.util.period($('#lately_3month'), 'lately_3month');
			else
				app.util.period($('#lately_week'), 'lately_week');
		}
		
		var $policy = $('#policy_box');
		$('#btn_policy').on('click', function(e) {
			app.ldin.policy.search(1);
			$policy.fadeIn();
		});
		$('#btn_policy_close, #ico_policy_close, #popupDimed2').on('click', function(e) {
			$policy.fadeOut();
		});
		
		var $popup = $('#popupBox');
		$('#btn_close, #popupDimed').on('click', function(e) {
			$popup.fadeOut();
		});

		var $detail_list = $('#detail_list');
		var $lists = $('#lists');
		
		var $page_wrap = $('#page_wrap');
		var _this = this;
		this.search = function(url, pageNo) {
			$frm[0].pageNo.value = pageNo || 1;
			app.form.unformat($frm[0]);
			
			app.request.page('/ldin/policy/result/list', $frm.serialize(), function(result) {
				$('#loading').show();
				$lists.children().remove();
				$lists.html(result).children().on('click', function(e) {
					if(!this.getAttribute('data-id')) return;
					
					document.search_frm['param[ANLYS_PLCY_ID]'].value = this.getAttribute('data-id');
					var $frm = $(document.search_frm);
					app.form.unformat($frm[0]);
					app.request.page('/ldin/policy/result/dtl', $frm.serialize(), function(result) {
						$detail_list.children().remove();
						$detail_list.html(result);
						$popup.fadeIn();
						document.search_frm['param[ANLYS_PLCY_ID]'].value = '';
						app.form.format($frm[0]);
					});
				});
				
				//paging
				$page_wrap.children().remove();
				$page_wrap.append($('#paging_total')).append($('#paging')).find('a').on('click', function(e) {
					_this.search(url, this.getAttribute('data-no'));
				});

				app.form.format($frm[0]);
				$('#loading').hide();
			});
		};
		
		this.search();
		
		app.ldin.policy.index();
	}
};

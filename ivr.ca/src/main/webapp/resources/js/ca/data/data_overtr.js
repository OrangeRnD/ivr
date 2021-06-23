app.data.overtr = {
	index:function(url) {
		var $frm = $(document.search_frm);
		var $stdValue = $($frm[0]['param[STD_VALUE]']);
		
		if($frm[0].getAttribute('data-session-type')) {
			var type = $frm[0].getAttribute('data-session-type');
			if(type == '0') {
				$frm[0]['param[FROM_DT]'].value = app.util.formatDate($frm[0]['param[FROM_DT]'].value, '-');
				$frm[0]['param[TO_DT]'].value = app.util.formatDate($frm[0]['param[TO_DT]'].value, '-');
				document.getElementById('std_input').checked = true;
			} else {
				for(var i = 0; i < $frm[0]['param[PERIOD]'].length; i++) {
					if($frm[0]['param[PERIOD]'][i].value == type) {
						app.util.period($($frm[0]['param[PERIOD]'][i]), $frm[0]['param[PERIOD]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
			
			if($frm[0]['param[STD_VALUE]'].value != '5' && $frm[0]['param[STD_VALUE]'].value != '10') 
				$stdValue.show().next().show();
		} else {
			app.util.period($('#lately_week'), 'lately_week');
		}
		
		$('#STD_VALUE_5').on('click', function(e) {
			$stdValue.val(this.value).hide().next().hide();
		});
		$('#STD_VALUE_10').on('click', function(e) {
			$stdValue.val(this.value).hide().next().hide();
		});
		$('#STD_VALUE').on('click', function(e) {
			$stdValue.val('0').show().next().show();
		});
		
		var $policy = $('#policy').find('input');
		$policy.on('click', function(e) {
			var $this = $policy.filter(this);
			if(this.type == 'radio') {
				$this.parent().parent().find('input[type="text"]').prop('disabled', true);
				$this.parent().next().find('input[type="text"]').prop('disabled', false);
			} else if(this.type == 'checkbox'){
				if(this.checked) {
					$this.parent().parent().find('input[type="radio"]').prop('disabled', false);
					$this.parent().parent().find('input[type="radio"]').filter(function(index) {
						return this.checked;
					}).trigger('click');
				} else {
					$this.parent().parent().find('input').prop('disabled', true);
				}
				this.disabled = false;
			}
		});
		if($frm[0].getAttribute('data-session-type')) {
			$policy.filter('input[type="text"]').each(function() {
				if(!this.value) {
					this.disabled = true;
				} else {
					$(this).parent().prev().children().prop('checked', true);
				}
			});
		} else {
			$policy.filter('input[type="text"]').prop('disabled', true).filter('._checked_').prop('disabled', false);
			$policy.filter('input[type="radio"]').prop('disabled', true).prop('disabled', false);
		}
		
		$('#btn_std').on('click', function(e) {
			setStdValue();
		});
		
		function setStdValue(){
			if($stdValue.val()) {
				var param = 'param[STD_VALUE]=' + $frm[0]['param[STD_VALUE]'].value.replace(/,/g,'') + '&param[FROM_DT]=' + $frm[0]['param[FROM_DT]'].value.replace(/(\.|\-|\/)/g,'') + '&param[TO_DT]=' + $frm[0]['param[TO_DT]'].value.replace(/(\.|\-|\/)/g,'');
				app.request.get('/data/overtr/value', param, function(result) {
					if(result.status == 200 && result.data.length > 0) {
						for(var i = 0; i < result.data.length; i++) {
							var cnt = result.data[i].CNT;
							if(!cnt) cnt = 0;
							document.getElementById('TYPE_' + result.data[i].TYPE).value = cnt;
							if(result.data[i].TYPE%2 == 0) {
								if(cnt == 0)
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = 0;
								else
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = cnt-1;
							} else
								if(cnt == 0)
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = 0;
								else
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = cnt+1;
						}

						for(var i = 1; i < 8; i++) {
							if(i%2 == 1) {
								var type1 = document.getElementById('TYPE_' + i);
								var type2 = document.getElementById('TYPE_' + (i+1));
								if(parseInt(type1.value, 10) == 0 && parseInt(type2.value, 10) > 1)
									document.getElementById('TYPE_' + i + '_1').value = parseInt(type1.value, 10) + 1;
							}
						}
					} else {
						alert('해당 기간에 데이터가 존재하지 않습니다.');
					}
				});
			}
		}
		
		var $lists = $('#lists');
		var $page_wrap = $('#page_wrap');
		var _this = this;
		this.search = function(url, pageNo) {
			$frm[0].pageNo.value = pageNo || 1;
			
			app.form.unformat($frm[0]);
			
			app.request.page('/data/overtr/list', $frm.serialize(), function(result) {
				$('#loading').show();
				$lists.children().remove();
				$lists.html(result).children().on('click', function(e) {
					if(this.className == 'selectable active') return;
					
					$lists.children().removeClass('active');
					this.className = 'selectable active';
				});
				
				//paging
				var $paging = $('#paging');
				$page_wrap.children().remove();
				if($paging.attr('data-total') && parseInt($paging.attr('data-total'), 10) > 0) {
					$page_wrap.append($paging).find('a').on('click', function(e) {
						_this.search(url, this.getAttribute('data-no'));
					});
					$('#total_cnt').html('패턴 적용 이용자 수 : ' + app.util.formatNumber($paging.attr('data-total'))  + ' 명');
				} else 
					$('#total_cnt').html('패턴 적용 이용자 수 : 0 명');

				app.form.format($frm[0]);
				$('#loading').hide();
			});
		};
	//	기준값 설정
		setStdValue(); 
	},
	select:function(telno, bizno, type) {
		document._temp_['param[TELNO]'].value = telno.replace(/\s/g,'');
		document._temp_['param[RGSNO]'].value = bizno.replace(/\s/g,'');
		document._temp_['param[USER_SECD]'].value = type;
		document._temp_['param[FROM_DT]'].value = document.search_frm['param[FROM_DT]'].value;
		document._temp_['param[TO_DT]'].value = document.search_frm['param[TO_DT]'].value;
		
		document._temp_.action =  app.constants.webappRoot + '/use/call/popup';
		document._temp_.submit();
	}
};
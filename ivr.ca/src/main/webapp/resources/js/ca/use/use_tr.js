app.use.tr = {
	index:function(url) {
		var $frm = $(document.search_frm);
		
		if($frm[0]['param[TELNO]'].getAttribute('data-session-type')) {
			//세션이 있는 경우
			var type1 = $frm[0]['param[TELNO]'].getAttribute('data-session-type');
			if(type1 == '0') {
				$frm[0]['param[FROM_DT]'].value = app.util.formatDate($frm[0]['param[FROM_DT]'].value, '-');
				$frm[0]['param[TO_DT]'].value = app.util.formatDate($frm[0]['param[TO_DT]'].value, '-');
				document.getElementById('std_input').checked = true;
			} else {
				for(var i = 0; i < $frm[0]['param[STANDARD]'].length; i++) {
					if($frm[0]['param[STANDARD]'][i].value == type1) {
						app.util.period($($frm[0]['param[STANDARD]'][i]), $frm[0]['param[STANDARD]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
			
			if($frm[0]['param[TELNO]'].value) 
				$frm[0]['param[TELNO]'].value = app.util.formatTel($frm[0]['param[TELNO]'].value);
		} else {
			//팝업인 경우
			if(!$frm[0]['param[FROM_DT]'].value)
				app.util.period($('#std_this_week'), 'this_week');
			else
				document.getElementById('std_input').checked = true;
		}
		
		$('#regno1').on('click', function(e) {
			$frm[0]['param[RGSNO]'].placeholder = '생년월일';
		});
		$('#regno2').on('click', function(e) {
			$frm[0]['param[RGSNO]'].placeholder = '사업자번호';
		});
		
		for(var i = 0; i < 24; i++) {
			$frm[0]['param[FROM_H]'].options.add(new Option(i+'시', (i < 10 ? '0' : '') + i));
			$frm[0]['param[TO_H]'].options.add(new Option(i+'시', (i < 10 ? '0' : '') + i));
		}
		for(var i = 0; i < 60; i++) {
			$frm[0]['param[FROM_M]'].options.add(new Option(i+'분', (i < 10 ? '0' : '') + i));
			$frm[0]['param[TO_M]'].options.add(new Option(i+'분', (i < 10 ? '0' : '') + i));
		}

		if($frm[0]['param[FROM_H]'].getAttribute('data-value')) {
			//세션 조회 조건이 있거나 팝업인 경우
			$frm[0]['param[FROM_H]'].value = $frm[0]['param[FROM_H]'].getAttribute('data-value');
			$frm[0]['param[TO_H]'].value = $frm[0]['param[TO_H]'].getAttribute('data-value');
			$frm[0]['param[FROM_M]'].value = $frm[0]['param[FROM_M]'].getAttribute('data-value');
			$frm[0]['param[TO_M]'].value = $frm[0]['param[TO_M]'].getAttribute('data-value');

			$frm[0]['param[FROM_H]'].removeAttribute('data-value');
			$frm[0]['param[TO_H]'].removeAttribute('data-value');
			$frm[0]['param[FROM_M]'].removeAttribute('data-value');
			$frm[0]['param[TO_M]'].removeAttribute('data-value');
		} else {
			$frm[0]['param[TO_H]'].value = '23';
			$frm[0]['param[TO_M]'].value = '59';
		}
		
		var $srvc_list = $('#srvc_list');
		var $nm = $('#srvc_nm');
		var $trnm = $('#tr_nm');
		$nm.on('keyup', function(e) {
			var nm = this.value;
			if(nm.length < 2) {
				$frm[0]['param[SRVC_CD]'].value = '';
	    		$srvc_list.children().remove();
				$srvc_list.parent().hide();
				return;
			}
			
		    var code = e.keyCode || e.charCode || 0;
		    if(code == 40) {
		    	//키 아래로
		    	return;
		    } else {
		    	if(app.util.has(app.constants.special_keyCode, code) && code != 8)
		    		return;
		    }

			$frm[0]['param[SRVC_CD]'].value = '';
			
			app.request.get('/system/srvccd/search/nm', 'nm=' + nm, function(result) {
				$srvc_list.children().remove();
				if(result.data.length == 0) {
					$srvc_list.parent().hide();
					return;
				}
				
				for(var i = 0; i < result.data.length; i++) {
					var $li = $(document.createElement('li'));
					$li.append('<a>' + result.data[i].SRVC_NM.replace(new RegExp(nm, 'g'),'<font color="red">' + nm + '</font>') + '</a>');
					$li.attr('data-cd', result.data[i].SRVC_CD);
					$li.attr('data-nm', result.data[i].SRVC_NM);
					$li.on('click', function(e) {
						$nm.val(this.getAttribute('data-nm'));
						$frm[0]['param[SRVC_CD]'].value = this.getAttribute('data-cd');
						$trnm.val('');
						$frm[0]['param[TRAN_CD]'].value = '';

						window.setTimeout(function(e) {
							$srvc_list.children().remove();
							$srvc_list.parent().fadeOut();
						}, 100);
					});
					$srvc_list.append($li);
				}
				if($srvc_list.children().length > 0)
					$srvc_list.parent().slideDown();
			}, true);
		}).on('blur', function(e) {
			if($srvc_list.is(':visible')) return;
			
			window.setTimeout(function(e) {
				if(!$nm.val()) $frm[0]['param[SRVC_CD]'].value = '';
				else if(!$frm[0]['param[SRVC_CD]'].value) $nm.val('');
				
				$srvc_list.children().remove();
				$srvc_list.parent().fadeOut();
			}, 100);
		});
		
		var $tr_list = $('#tr_list');
		$trnm.on('keyup', function(e) {
			var nm = this.value;
			if(nm.length < 2) {
				$frm[0]['param[TRAN_CD]'].value = '';
	    		$tr_list.children().remove();
				$tr_list.parent().hide();
				return;
			}

		    var code = e.keyCode || e.charCode || 0;
		    if(code == 40) {
		    	//키 아래로
		    	return;
		    } else {
		    	if(app.util.has(app.util.has(app.constants.special_keyCode, code) && code != '8'))
		    		return;
		    }

			$frm[0]['param[TRAN_CD]'].value = '';
			
			app.request.get('/system/trcd/search/nm', 'nm=' + nm, function(result) {
				$tr_list.children().remove();
				if(result.data.length == 0) {
					$tr_list.parent().hide();
					return;
				}
				
				for(var i = 0; i < result.data.length; i++) {
					var $li = $(document.createElement('li'));
					$li.append('<a>' + result.data[i].TRAN_NM.replace(new RegExp(nm, 'g'),'<font color="red">' + nm + '</font>') + '</a>');
					$li.attr('data-cd', result.data[i].TRAN_CD);
					$li.attr('data-nm', result.data[i].TRAN_NM);
					$li.on('click', function(e) {
						$trnm.val(this.getAttribute('data-nm'));
						$frm[0]['param[TRAN_CD]'].value = this.getAttribute('data-cd');
						$nm.val('');
						$frm[0]['param[SRVC_CD]'].value = '';

						window.setTimeout(function(e) {
							$tr_list.children().remove();
							$tr_list.parent().fadeOut();
						}, 100);
					});
					$tr_list.append($li);
				}
				if($tr_list.children().length > 0)
					$tr_list.parent().slideDown();
			}, true);
		}).on('blur', function(e) {
			if($tr_list.is(':visible')) return;
			
			window.setTimeout(function(e) {
				if(!$trnm.val()) $frm[0]['param[TRAN_CD]'].value = '';
				else if(!$frm[0]['param[TRAN_CD]'].value) $trnm.val('');
				
				$tr_list.children().remove();
				$tr_list.parent().fadeOut();
			}, 100);
		});
		
		$(document.body).on('click', function(e) {
			if($srvc_list.is(':visible')) {
				if(e.target && e.target.id && e.target.id == 'srvc_nm') return;
				
				window.setTimeout(function(e) {
					if(!$nm.val()) $frm[0]['param[SRVC_CD]'].value = '';
					else if(!$frm[0]['param[SRVC_CD]'].value) $nm.val('');
					
					$srvc_list.children().remove();
					$srvc_list.parent().fadeOut();
				}, 100);
			}
			if($tr_list.is(':visible')) {
				if(e.target && e.target.id && e.target.id == 'tr_nm') return;
				
				window.setTimeout(function(e) {
					if(!$trnm.val()) $frm[0]['param[TRAN_CD]'].value = '';
					else if(!$frm[0]['param[TRAN_CD]'].value) $trnm.val('');
					
					$tr_list.children().remove();
					$tr_list.parent().fadeOut();
				}, 100);
			}
		});
		
		//서비스 코드 팝업 검색
		var $popup_contents = $('#popup_contents');
		var $popupTtl = $('#popupTtl');
		$('#btn_srvc').on('click', function(e) {
			$popupTtl.html('서비스코드');
			app.request.page('/system/srvccd/search', '', function(result) {
				$popup_contents.children().remove();
				$popup_contents.html(result);
				app.form.init(document.search_popup_frm, true);
				var $search_list = $('#srvc_popup_list');

				var $search = $('#btn_popup_search');
				var search_after = function() {
					$('#paging_popup').find('a').on('click', function(e3) {
						document.search_popup_frm.pageNo.value = this.getAttribute('data-no');
						$search.trigger('click');
					});
					
					$search_list.find('.selectable').on('click', function(e) {
						document.getElementById('srvc_nm').value = this.getAttribute('data-nm');
						$frm[0]['param[SRVC_CD]'].value = this.getAttribute('data-cd');

						$trnm.val('');
						$frm[0]['param[TRAN_CD]'].value = '';
						
						$('#search_popup').fadeOut();
					});
				};
				
				$search.on('click', function(e2) {
					app.request.page('/system/srvccd/search/list', $(document.search_popup_frm).serialize(), function(result2) {
						$search_list.children().remove();
						$search_list.html(result2);
						
						search_after();
					});
				});
				
				search_after();
				
				$('#search_popup').fadeIn();
			});
		});
		
		$('#ico_popup_close, #popupDimed').on('click', function(e) {
			$('#search_popup').fadeOut();
		});
		
		//거래코드 팝업 검색
		$('#btn_tr').on('click', function(e) {
			$popupTtl.html('거래코드');
			app.request.page('/system/trcd/search', '', function(result) {
				$popup_contents.children().remove();
				$popup_contents.html(result);
				app.form.init(document.search_popup_frm, true);
				var $search_list = $('#tr_popup_list');

				var $search = $('#btn_popup_search');
				var search_after = function() {
					$('#paging_popup').find('a').on('click', function(e3) {
						document.search_popup_frm.pageNo.value = this.getAttribute('data-no');
						$search.trigger('click');
					});
					
					$search_list.find('.selectable').on('click', function(e) {
						document.getElementById('tr_nm').value = this.getAttribute('data-nm');
						$frm[0]['param[TRAN_CD]'].value = this.getAttribute('data-cd');

						$nm.val('');
						$frm[0]['param[SRVC_CD]'].value = '';
						
						$('#search_popup').fadeOut();
					});
				};
				
				$search.on('click', function(e2) {
					app.request.page('/system/trcd/search/list', $(document.search_popup_frm).serialize(), function(result2) {
						$search_list.children().remove();
						$search_list.html(result2);
						
						search_after();
					});
				});
				
				search_after();
				
				$('#search_popup').fadeIn();
			});
		});
		
		var $lists = $('#lists');
		var $page_wrap = $('#page_wrap');
		var _this = this;
		this.search = function(url, pageNo) {
			if(!($frm[0]['param[TRAN_CD]'].value || $frm[0]['param[SRVC_CD]'].value)) {
				alert('서비스명 또는 거래명을 입력하십시요.')
				return;
			}
			
			$frm[0].pageNo.value = pageNo || 1;
			
			app.form.unformat($frm[0]);
			app.request.page('/use/tr/list', $frm.serialize(), function(result) {
				$('#loading').show();
				$lists.children().remove();
				$lists.html(result);
				app.form.format($frm[0]);
				if($frm[0]['param[TRAN_CD]'].value) {
					$('#cdType').html('서비스');
					$('#cdType2').html('서비스명');
					$('#srvc_cd').html($frm[0]['param[TRAN_CD]'].value);
				} else {
					$('#cdType').html('거래');
					$('#cdType2').html('거래명');
					$('#srvc_cd').html($frm[0]['param[SRVC_CD]'].value);
				}

				/*window.setTimeout(function() {
					if($lists.children().length > 0) {
						if(!$lists.children()[0].getAttribute('data-type'))
							$('#total_cnt').html($lists.children().length);
						else
							$('#total_cnt').html(0);
					}
				}, 100);*/
				
				//paging
				$page_wrap.children().remove();
				$page_wrap.append($('#paging_total')).append($('#paging')).find('a').on('click', function(e) {
					_this.search(url, this.getAttribute('data-no'));
				});
				
				$('#loading').hide();
			});
			$('#std_period').html(app.util.formatDate(document.search_frm['param[FROM_DT]'].value, '년 ', '월 ', '일') + ' ' + parseInt(document.search_frm['param[FROM_H]'].value, 10) + '시 ' + parseInt(document.search_frm['param[FROM_M]'].value, 10) + '분 ~ ' + app.util.formatDate(document.search_frm['param[TO_DT]'].value, '년 ', '월 ', '일') + ' ' + parseInt(document.search_frm['param[TO_H]'].value, 10) + '시 ' + parseInt(document.search_frm['param[TO_M]'].value, 10) + '분');
		};
		
		//고객콜에서 넘어온 경우 해당 값이 존재함.
		if($frm[0]['param[TRAN_CD]'].value) {
			//세셔 조회 조건 값이 없고 팝업인 경우 바로 조회
			if(!$frm[0]['param[TELNO]'].getAttribute('data-session-type'))
				this.search();
		}
	},
	popup:{
		index:function(url) {
			app.use.tr.index.call(this, url);
		}
	}
};
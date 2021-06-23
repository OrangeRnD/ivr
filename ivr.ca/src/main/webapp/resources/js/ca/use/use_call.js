app.use.call = {
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
		
		if($frm[0]['param[TELNO]'].getAttribute('data-session-type')) {
			//세션이 있는 경우
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
		var $trnm = $('#tr_nm');
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
		
		var $call_list = $('#call_list');
		var $call_srvc_list = $('#call_srvc_list');
		var $call_tr_list = $('#call_tr_list');
		var $call_err_list = $('#call_err_list');
		var $page_wrap = $('#page_wrap');
		var _this = this;
		this.search = function(url, pageNo) {
			/*if(!$frm[0]['param[TELNO]'].value) {
				alert('전화번호를 입력하십시요.')
				return;
			}*/
			
			$frm[0].pageNo.value = pageNo || 1;
			
			app.form.unformat($frm[0]);
			
			//평균이용시간
			app.request.submit('/use/call/time', $frm, function(result) {
				if(result.data) {
					var min = parseInt(result.data/60, 10);
					if(min < 0) {
						$('#avg_time').html(result.data + '초');
					} else {
						if(result.data%60 > 0)
							$('#avg_time').html(app.util.formatNumber(min) + '분 ' + (result.data%60) + '초');
						else
							$('#avg_time').html(app.util.formatNumber(min) + '분');
					}
				} else {
					$('#avg_time').html('-');
				}
			});
			//콜리스트
			app.request.page('/use/call/list', $frm.serialize(), function(result) {
				$('#loading').show();
				$call_list.children().remove();
				$call_list.html(result).children().on('click', function(e) {
					if(this.className == 'selectable active') return;
					
					$call_list.children().removeClass('active');
					this.className = 'selectable active';
					var param = 'param[DT]=' + this.getAttribute('data-dt') + '&param[ICID]=' + this.getAttribute('data-icid');
					app.request.page('/use/call/tr', param, function(result) {
						$call_tr_list.children().remove();
						$call_tr_list.html(result);
					});
					app.request.page('/use/call/err', param, function(result) {
						$call_err_list.children().remove();
						$call_err_list.html(result);
					});

					//자주쓰는메뉴
					var param2 = 'param[TELNO]=' + this.getAttribute('data-telno');
					app.request.page('/use/call/srvc', param2, function(result) {
						$call_srvc_list.children().remove();
						$call_srvc_list.html(result);
						/*if($call_srvc_list.children().length < 5) {
							for(var i = $call_srvc_list.children().length; i < 5; i++) {
								$call_srvc_list.append('<tr><td class="sort">-</td><td>-</td></tr>');
							}
						}*/
					});
				});
				$call_srvc_list.children().remove();
				$call_srvc_list.html('<tr><td colspan="3" class="txt">-</td></tr>');
				$call_tr_list.children().remove();
				$call_tr_list.html('<tr><td colspan="3" class="txt">-</td></tr>');
				$call_err_list.children().remove();
				$call_err_list.html('<tr><td colspan="2" class="txt">-</td></tr>');
				
				//paging
				$page_wrap.children().remove();
				$page_wrap.append($('#paging_total')).append($('#paging')).find('a').on('click', function(e) {
					_this.search(url, this.getAttribute('data-no'));
				});

				app.form.format($frm[0]);
				$('#loading').hide();
			});
			
			$('#std_period').html(app.util.formatDate(document.search_frm['param[FROM_DT]'].value, '년 ', '월 ', '일') + ' ' + parseInt(document.search_frm['param[FROM_H]'].value, 10) + '시 ' + parseInt(document.search_frm['param[FROM_M]'].value, 10) + '분 ~ ' + app.util.formatDate(document.search_frm['param[TO_DT]'].value, '년 ', '월 ', '일') + ' ' + parseInt(document.search_frm['param[TO_H]'].value, 10) + '시 ' + parseInt(document.search_frm['param[TO_M]'].value, 10) + '분');
		};
		
		if($frm[0]['param[TELNO]'].value) {
			$frm[0]['param[TELNO]'].value = app.util.formatTel($frm[0]['param[TELNO]'].value);
			//세셔 조회 조건 값이 없고 팝업인 경우 바로 조회
			if(!$frm[0]['param[TELNO]'].getAttribute('data-session-type'))
				this.search();
		}
	},
	popup:{
		index:function(url) {
			app.use.call.index.call(this, url);
		}
	},
	select:function(cd, nm) {
		document.search_frm['param[P_TRAN_CD]'].value = cd.replace(/\s/g,'');
		document.search_frm['param[P_TRAN_NM]'].value = nm.replace(/\s/g,'');
		document.search_frm.target = 'about:blank';
		document.search_frm.action = app.constants.webappRoot + '/use/tr/popup';
		document.search_frm.submit();
	}
};
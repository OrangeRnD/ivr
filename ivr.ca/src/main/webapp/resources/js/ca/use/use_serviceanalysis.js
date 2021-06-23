app.use.serviceanalysis = {
	index:function(url) {
		var frm = document.search_frm;
		
		if(frm.getAttribute('data-session-type1')) {
			var type1 = frm.getAttribute('data-session-type1');
			if(type1 == '0') {
				frm['param[FROM_DT1]'].value = app.util.formatDate(frm['param[FROM_DT1]'].value, '-');
				frm['param[TO_DT1]'].value = app.util.formatDate(frm['param[TO_DT1]'].value, '-');
				$('#std_input').prop('checked', true);
			} else {
				for(var i = 0; i < frm['param[STANDARD]'].length; i++) {
					if(frm['param[STANDARD]'][i].value == type1) {
						app.util.period($(frm['param[STANDARD]'][i]), frm['param[STANDARD]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
			
			var type2 = frm.getAttribute('data-session-type2');
			if(type2 == '0') {
				frm['param[FROM_DT2]'].value = app.util.formatDate(frm['param[FROM_DT2]'].value, '-');
				frm['param[TO_DT2]'].value = app.util.formatDate(frm['param[TO_DT2]'].value, '-');
				$('#compare_input').prop('checked', true);
			} else {
				for(var i = 0; i < frm['param[COMPARE]'].length; i++) {
					if(frm['param[COMPARE]'][i].value == type2) {
						app.util.period($(frm['param[COMPARE]'][i]), frm['param[COMPARE]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
		} else {
			//날짜 초기 설정 - 조회 기간 : 금주, 비교 기간 : 전주
			app.util.period($('#std_this_week'), 'this_week');
			app.util.period($('#compare_pre_week'), 'pre_week');
		}
		app.util.sort($('#tblList').parent());
		
		$('#cdList').find('.svtDelete').on('click', function(e) {
			$(this).parent().parent().remove();
		});
		
		var $popup_list = $('#popup_list');
		var $nm = $('#srvc_nm');
		$nm.on('keyup', function(e) {
			var nm = this.value;
			if(nm.length < 2) {
	    		$popup_list.children().remove();
				$popup_list.parent().hide();
				return;
			}

		    var code = e.keyCode || e.charCode || 0;
		    if(code == 40) {
		    	//키 아래로
		    	return;
		    } else {
		    	if(app.util.has(app.constants.special_keyCode, code) && code != '8')
		    		return;
		    }
			
			app.request.get('/system/srvccd/search/nm', 'nm=' + nm, function(result) {
				$popup_list.children().remove();
				if(result.data.length == 0) {
					$popup_list.parent().hide();
					return;
				}
				
				var exCds = document.search_frm['param[EX_CDS]'];
				
				for(var i = 0; i < result.data.length; i++) {
					if(exCds) {
						if(exCds.length) {
							var b = false;
							for(var j = 0; j < exCds.length; j++) {
								if(exCds[j].value == result.data[i].SRVC_CD) {
									b = true;
									break;
								}
							}
							if(b) continue;
						} else {
							if(exCds.value == result.data[i].SRVC_CD)
								continue;
						}
					}
					
					var $li = $(document.createElement('li'));
					$li.append('<a>' + result.data[i].SRVC_NM.replace(new RegExp(nm, 'g'),'<font color="red">' + nm + '</font>') + '</a>');
					$li.attr('data-cd', result.data[i].SRVC_CD);
					$li.attr('data-nm', result.data[i].SRVC_NM);
					$li.on('click', function(e) {
						app.use.serviceanalysis.addSrvc($nm.parent(), this.getAttribute('data-cd'), this.getAttribute('data-nm'));

						window.setTimeout(function(e) {
							$nm.val('');
							$popup_list.children().remove();
							$popup_list.parent().fadeOut();
						}, 100);
					});
					$popup_list.append($li);
				}
				if($popup_list.children().length > 0)
					$popup_list.parent().slideDown();
			}, true);
		}).on('blur', function(e) {
			if($popup_list.is(':visible')) return;
			
			window.setTimeout(function(e) {
				if(!$nm.val()) {
					$popup_list.children().remove();
					$popup_list.parent().fadeOut();
				}
			}, 100);
		});
		
		$(document.body).on('click', function(e) {
			if(e.target && e.target.id && e.target.id == 'srvc_nm') return;
			
			if($popup_list.is(':visible')) {
				window.setTimeout(function(e) {
					$nm.val('');
					$popup_list.children().remove();
					$popup_list.parent().fadeOut();
				}, 100);
			}
		});

		this.search_after = function(result) {
			$('#std_period').html('조회 기간 ( ' + frm['param[FROM_DT1]'].value + ' ~ ' + frm['param[TO_DT1]'].value + ' )');
			$('#compare_period').html('비교 기간 ( ' + frm['param[FROM_DT2]'].value + ' ~ ' + frm['param[TO_DT2]'].value + ' )');
		};
		
		var $popup_contents = $('#popup_contents');
		$('#btn_srvc').on('click', function(e) {
			app.request.page('/system/srvccd/search', '', function(result) {
				$popup_contents.children().remove();
				$popup_contents.html(result);
				app.form.init(document.search_popup_frm, true);
				var $srvc_list = $('#srvc_popup_list');

				var $search = $('#btn_popup_search');
				var search_after = function() {
					$('#paging_popup').find('a').on('click', function(e3) {
						document.search_popup_frm.pageNo.value = this.getAttribute('data-no');
						$search.trigger('click');
					});
					
					$srvc_list.find('.selectable').on('click', function(e) {
						var cd = this.getAttribute('data-cd');
						var exCds = document.search_frm['param[EX_CDS]'];
						if(exCds) {
							if(exCds.length) {
								for(var j = 0; j < exCds.length; j++) {
									if(exCds[j].value == cd) {
										alert('\'' + this.getAttribute('data-nm') + '\'는 이미 추가된 서비스코드 입니다.');
										return;
									}
								}
							} else {
								if(exCds.value == cd) {
									alert('\'' + this.getAttribute('data-nm') + '\'는 이미 추가된 서비스코드 입니다.');
									return;
								}
							}
						}
						
						app.use.serviceanalysis.addSrvc($nm.parent(), cd, this.getAttribute('data-nm'));
						alert('\'' + this.getAttribute('data-nm') + '\' 서비스 코드가 추가되었습니다.');
					});
				};
				
				$search.on('click', function(e2) {
					app.request.page('/system/srvccd/search/list', $(document.search_popup_frm).serialize(), function(result2) {
						$srvc_list.children().remove();
						$srvc_list.html(result2);
						
						search_after();
					});
				});
				
				search_after();
				
				$('#srvc_popup').fadeIn();
			});
		});
		
		$('#ico_popup_close, #popupDimed').on('click', function(e) {
			$('#srvc_popup').fadeOut();
		});
	},
	addSrvc:function($obj, cd, nm) {
		var li = document.createElement('li');
		var div = document.createElement('div');
		div.className = 'svObj';
		div.innerHTML = nm;

		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = 'param[EX_CDS]';
		input.value = cd;
		div.appendChild(input);

		var $a = $(document.createElement('a'));
		$a.addClass('svtDelete');
		$a.html('삭제');
		$a.on('click', function(e) {
			$a.parent().parent().remove();
		});
		div.appendChild($a[0]);
		li.appendChild(div);
		console.log($obj);
		$obj.before(li);
	}
};
app.system.policy = {
	index:function(url) {
		
		var frm = document.search_frm;
		if(frm.getAttribute('data-session-type')) {
			var type1 = frm.getAttribute('data-session-type');
			if(type1 == '0') {
				frm['param[FROM_DT]'].value = app.util.formatDate(frm['param[FROM_DT]'].value, '-');
				frm['param[TO_DT]'].value = app.util.formatDate(frm['param[TO_DT]'].value, '-');
				$('#std_input').prop('checked', true);
			} else {
				for(var i = 0; i < frm['param[STANDARD]'].length; i++) {
					if(frm['param[STANDARD]'][i].value == type1) {
						app.util.period($(frm['param[STANDARD]'][i]), frm['param[STANDARD]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
		} else {
			app.util.period($('#lately_week'), 'lately_week');
		}
		
		var $lists = $('#lists');
		var $page_wrap = $('#page_wrap');
		var _this = this;
		var $frm = $(frm);
		this.search = function(url, pageNo) {
			$frm[0].pageNo.value = pageNo || 1;
			app.form.unformat($frm[0]);
			
			app.request.page('/system/policy/list', $frm.serialize(), function(result) {
				$('#loading').show();
				$lists.children().remove();
				$lists.html(result);
				
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
	}	
};
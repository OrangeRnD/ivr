app.use.status = {
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
			app.util.period($('#std_this_week'), 'this_week');
		}
		
		this.search_after = function(result) {
			$('#std_period').html(app.util.formatDate(document.search_frm['param[FROM_DT]'].value, '년 ', '월 ', '일') + ' ~ ' + app.util.formatDate(document.search_frm['param[TO_DT]'].value, '년 ', '월 ', '일'));
		};
	}
};
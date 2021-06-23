app.use.usertype = {
	index:function(url) {
		app.util.period($('#std_this_week'), 'this_week');

		var frm = document.search_frm;
		this.search = function(url) {
			app.request.submit('/use/usertype/user', $(frm), function(result) {
				var trs = document.getElementById('lists1').children;
				var type = 0, row = 0;
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(type != result.data[i].TYPE) {
							if(type != 0) {
								for(var r = row; r < 10; r++) {
									var tds = trs[r].children;
									tds[(type*2)-2].innerHTML = '-';
									tds[(type*2)-1].innerHTML = '-';
								}
							}
							row = 0;
							type = result.data[i].TYPE;
						}
						var tds = trs[row].children;
						tds[(type*2)-2].innerHTML = result.data[i].SRVC_NM;
						tds[(type*2)-1].innerHTML = app.util.formatNumber(result.data[i].CNT);
						row++;
					}
					for(var r = row; r < 10; r++) {
						var tds = trs[r].children;
						tds[(type*2)-2].innerHTML = '-';
						tds[(type*2)-1].innerHTML = '-';
					}
				}
			});
			
			app.request.submit('/use/usertype/age', $(frm), function(result) {
				var trs = document.getElementById('lists2').children;
				var type = 0, row = 0;
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(type != result.data[i].TYPE) {
							if(type != 0) {
								for(var r = row; r < 10; r++) {
									var tds = trs[r].children;
									tds[(type*2)-2].innerHTML = '-';
									tds[(type*2)-1].innerHTML = '-';
								}
							}
							row = 0;
							type = result.data[i].TYPE;
						}
						var tds = trs[row].children;
						tds[(type*2)-2].innerHTML = result.data[i].SRVC_NM;
						tds[(type*2)-1].innerHTML = app.util.formatNumber(result.data[i].CNT);
						row++;
					}
					for(var r = row; r < 10; r++) {
						var tds = trs[r].children;
						tds[(type*2)-2].innerHTML = '-';
						tds[(type*2)-1].innerHTML = '-';
					}
				}
			});
		};
		
		this.search();
	}
};
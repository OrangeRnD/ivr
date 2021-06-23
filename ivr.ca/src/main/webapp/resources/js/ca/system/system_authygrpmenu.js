app.system.authygrpmenu = {
	index:function(url) {
		var list = function() {
			app.request.get('/system/authygrpmenu/list', '', function(data) {
				var len = document.frm.elements.length;
				for(var i = 0; i < len; i++) {
					var el = document.frm.elements[i];
					el.checked = false;
					el.removeAttribute('data-sn');
				}
				
				if(data.data) {
					for(var j = 0; j < data.data.length; j++) {
						for(var i = 0; i < len; i++) {
							var el = document.frm.elements[i];
							if(el.getAttribute('data-cd') == data.data[j].authyGrpCd && el.getAttribute('data-menu-id') == data.data[j].menuId) {
								el.checked = true;
								el.setAttribute('data-sn', data.data[j].sn);
								break;
							}
						}
					}
				}
			});
		};
		
		list();
		
		$('#btn_save').on('click', function(e) {
			if(!confirm('저장하시겠습니까?'))
				return;
			
			var datas = [];
			for(var i = 0; i < document.frm.elements.length; i++) {
				var el = document.frm.elements[i];
				if(el.checked) {
					if(!el.getAttribute('data-sn')) {
						var data = {};
						data.authyGrpCd = el.getAttribute('data-cd');
						data.menuId = el.getAttribute('data-menu-id');
						data.sn = 0;//el.getAttribute('data-sn');
						datas.push(data);
					}
				} else {
					if(el.getAttribute('data-sn')) {
						var data = {};
						data.authyGrpCd = el.getAttribute('data-cd');
						data.menuId = el.getAttribute('data-menu-id');
						data.sn = el.getAttribute('data-sn');
						datas.push(data);
					}
				}
			}
			
			app.request.json('/system/authygrpmenu/add', datas, function() {
				alert('저장되었습니다.');
				list();
			});
		});
	}
};
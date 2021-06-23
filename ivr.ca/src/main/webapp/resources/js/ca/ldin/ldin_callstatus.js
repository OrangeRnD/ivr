app.ldin.callstatus = {
	index:function(url) {
		app.util.sort($('#table_meda'));
		app.util.sort($('#table_ivr'));
		
		var frm = document.search_frm;
		
		var f_year = document.getElementById('f_year');
		var t_year = document.getElementById('t_year');
		
		var now = new Date();
		for(var i = 2018; i <= now.getFullYear(); i++) {
			f_year.options.add(new Option(i+'년', i));
			t_year.options.add(new Option(i+'년', i));
		}
		
		var f_month = document.getElementById('f_month');
		var t_month = document.getElementById('t_month');
		for(var i = 1; i <= 12; i++) {
			f_month.options.add(new Option(i+'월', i<10?'0'+i:i));
			t_month.options.add(new Option(i+'월', i<10?'0'+i:i));
		}
		if(f_year.getAttribute('data-value')) {
			t_month.value = t_month.getAttribute('data-value');
			t_year.value = t_year.getAttribute('data-value');
			t_month.removeAttribute('data-value');
			t_year.removeAttribute('data-value');
			
			f_year.value = f_year.getAttribute('data-value');
			f_month.value = f_month.getAttribute('data-value');
			f_year.removeAttribute('data-value');
			f_month.removeAttribute('data-value');
		} else {
			var m = now.getMonth()+1;
			t_month.value = m < 10 ? '0' + m : m;
			t_year.value = now.getFullYear();
			
			now.setMonth(now.getMonth() - 5);
			f_year.value = now.getFullYear();
			m = now.getMonth()+1;
			f_month.value = m < 10 ? '0' + m : m;	
		}
		
		var $meda_list = $('#meda_list');
		var $ivr_list = $('#ivr_list');
		
		var $frm = $(frm);
		this.search = function(url) {
			var param = $frm.serialize();
			app.request.page('/ldin/callstatus/meda', param, function(result) {
				$meda_list.children().remove();
				$meda_list.html(result);
			});
			app.request.page('/ldin/callstatus/ivr', param, function(result) {
				$ivr_list.children().remove();
				$ivr_list.html(result).find('tr').on('click', function(e) {
					var ym = this.getAttribute('data-ym');
					window.location.href = app.constants.webappRoot + '/ldin/ars?ym=' + ym;
				});
			});
			app.request.page('/ldin/callstatus/stat', param, function(result) {
				var userLabels = [], userDatas = [], ageLabels = [], ageDatas = [];
				if(result.status == 200 && result.data.user && result.data.user.length > 0) {
					for(var i = 0; i < result.data.user.length; i++) {
						userLabels.push(result.data.user[i].CD_NM);
						userDatas.push(result.data.user[i].CNT);
					}
				}
				if(result.status == 200 && result.data.age && result.data.age.length > 0) {
					for(var i = 0; i < result.data.age.length; i++) {
						ageLabels.push(result.data.age[i].CD_NM);
						ageDatas.push(result.data.age[i].CNT);
					}
				}
				app.ldin.callstatus.pie('userChart', userLabels, userDatas);
				app.ldin.callstatus.pie('ageChart', ageLabels, ageDatas);
			});
		};
		
		this.search();
	},
	pie:function(id, labels, datas) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		
		app.chart[id] = new Chart(document.getElementById(id), {
			type: 'pie',
		    data: {
		        labels: labels,
		        datasets: [{
					data: datas,
					borderColor: "#fff",
					hoverBorderColor: "#fff",
					borderWidth:"2",
					backgroundColor:["#ff6384", "#5d7bf7", "#2dbfef", "#6cbd30", "#dbd40f", "#f7a33f"]
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				legend: {
					display: true,
					position:'right',
					labels: {
			              boxWidth: 12,
			              fontFamily:'dotum',
			              fontSize:12
			        }
				},
				tooltips:{
					enabled:true,
					titleSpacing:10,
					callbacks: {
				        label: function(tooltipItem, data) {
				        	var cnt = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
				            return app.util.formatNumber(cnt) + ' 명';
				        },
				        title:function(tooltipItem, data) {
				    		return data.labels[tooltipItem[0].index];
				        }
				    }
				},
				plugins: {
				    labels: {
				        render: 'percentage',
				    	fontColor: ['white', 'white', 'white', 'white', 'white', 'white'],
				    	precision: 2
				    }
				}
			}
		});
		/*app.chart.domain[id].generateLegend();*/
	}
};
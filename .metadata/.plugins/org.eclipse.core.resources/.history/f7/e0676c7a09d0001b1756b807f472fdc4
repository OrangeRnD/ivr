app.ldin.speakars = {
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
			var param;
			if(window.location.search && window.location.search.indexOf('ym=') != -1) {
				param = location.search.substring(location.search.indexOf('ym=') + 3);
				if(param.indexOf('&') != -1) 
					param = param.substring(0, param.indexOf('&'));
				
				try {
					var date = app.util.parseDate(param + '01');
					
					frm['param[FROM_DT1]'].value = app.util.formatDate(date);
					date.setMonth(date.getMonth() + 1);
					date.setDate(date.getDate() - 1);
					frm['param[TO_DT1]'].value = app.util.formatDate(date);
					document.getElementById('std_input').checked = true;
					
					date.setDate(1);
					date.setMonth(date.getMonth() - 1);
					frm['param[FROM_DT2]'].value = app.util.formatDate(date)
					date.setMonth(date.getMonth() + 1);
					date.setDate(date.getDate() - 1);
					frm['param[TO_DT2]'].value = app.util.formatDate(date)
					document.getElementById('compare_input').checked = true;
				} catch(e) {
					param = '';
				}
			}
			
			if(!param) {
				//날짜 초기 설정 - 조회 기간 : 금주, 비교 기간 : 전주
				app.util.period($('#std_this_week'), 'this_week');
				app.util.period($('#compare_pre_week'), 'pre_week');
			}
		}
		
		this.search_after = function(result) {
			$('#std_period').html('조회 기간 ( ' + frm['param[FROM_DT1]'].value + ' ~ ' + frm['param[TO_DT1]'].value + ' )');
			$('#compare_period').html('비교 기간 ( ' + frm['param[FROM_DT2]'].value + ' ~ ' + frm['param[TO_DT2]'].value + ' )');

			var $trs = $('#tblList').children();
			var labels = [], std_tot_data = [], compare_tot_data = [], std_avg_data = [], compare_avg_data = [];
			var arsCnt1 = 0, arsCnt2 = 0;/*말로하는 건수*/
			var simpleCnt1 = 0, simpleCnt2 = 0;/*간편송금 건수*/
			for(var i = 0; i < $trs.length; i++) {
				var cd = $trs[i].getAttribute('data-cd');
				if(cd == 'ARS') {
					arsCnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(!arsCnt1) arsCnt1 = 0;
					arsCnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
					if(!arsCnt2) arsCnt2 = 0;
				} else if(cd == 'SIMPLE') {
					simpleCnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(!simpleCnt1) simpleCnt1 = 0;
					simpleCnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
					if(!simpleCnt2) simpleCnt2 = 0;
				}
			}
			
			var multiLine = false;
			if($(document.body).width() <= 1904) multiLine = true;

			for(var i = 0; i < $trs.length; i++) {
				var cd = $trs[i].getAttribute('data-cd');
				
				std_tot_data.push(parseInt($trs[i].getAttribute('data-tot1'), 10));
				compare_tot_data.push(parseInt($trs[i].getAttribute('data-tot2'), 10));
				std_avg_data.push(parseInt($trs[i].getAttribute('data-avg1'), 10));
				compare_avg_data.push(parseInt($trs[i].getAttribute('data-avg2'), 10));
				
				if(multiLine)
					labels.push($trs[i].getAttribute('data-nm').split(' '));
				else
					labels.push($trs[i].getAttribute('data-nm'));
				
				if(cd == 'ARS' || cd == 'SIMPLE') {
					var tot1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(arsCnt1 == 0) {
						$trs[i].children[3].innerHTML = '0 %';
					} else  {
						$trs[i].children[3].innerHTML = '100 %';
					}
					var tot2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
					if(tot2 == 0) {
						$trs[i].children[10].innerHTML = '0 %';
					} else  {
						$trs[i].children[10].innerHTML = '100 %';
					}
				} else if(cd == '1910' || cd == '2114') {
					try {
						var cnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
						if(cnt1 == 0 || arsCnt1 == 0)
							$trs[i].children[cd == '2114' ? 3 : 4].innerHTML = '0 %';
						else
							$trs[i].children[cd == '2114' ? 3 : 4].innerHTML = Math.round(cnt1/arsCnt1*1000)/10 + ' %';

						var cnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
						if(cnt2 == 0 || arsCnt2 == 0)
							$trs[i].children[cd == '2114' ? 10 : 11].innerHTML = '0 %';
						else
							$trs[i].children[cd == '2114' ? 10 : 11].innerHTML = Math.round(cnt2/arsCnt2*1000)/10 + ' %';
					} catch(e) {
						$trs[i].children[cd == '2114' ? 3 : 4].innerHTML = '0 %';
						$trs[i].children[cd == '2114' ? 10 : 11].innerHTML = '0 %';
					}
				} else if(cd == 'simple_Y' || cd == 'simple_N') {
					try {
						var cnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
						if(simpleCnt1 == 0 || cnt1 == 0)
							$trs[i].children[cd == 'simple_N' ? 3 : 4].innerHTML = '0 %';
						else 
							$trs[i].children[cd == 'simple_N' ? 3 : 4].innerHTML = Math.round(cnt1/simpleCnt1*1000)/10 + ' %';

						var cnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
						if(simpleCnt2 == 0 || cnt2 == 0)
							$trs[i].children[cd == 'simple_N' ? 10 : 11].innerHTML = '0 %';
						else 
							$trs[i].children[cd == 'simple_N' ? 10 : 11].innerHTML = Math.round(cnt2/simpleCnt2*1000)/10 + ' %';
					} catch(e) {
						$trs[i].children[cd == 'simple_N' ? 3 : 4].innerHTML = '0 %';
						$trs[i].children[cd == 'simple_N' ? 10 : 11].innerHTML = '0 %';
					}
				}
			}
			app.ldin.speakars.chart('chart1', labels, '조회기간 일평균', '비교기간 일평균', std_avg_data, compare_avg_data);
			app.ldin.speakars.chart('chart2', labels, '조회기간 총건수', '비교기간 총건수', std_tot_data, compare_tot_data);
		};
	},
	chart:function(id, labels, std_label, compare_label, std_data, compare_data) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		
		app.chart[id] = new Chart(document.getElementById(id), {
		    type: 'bar',
		    data: {
		      labels: labels,
		      datasets: [
		        {
		          label: std_label,
		          backgroundColor: "#ff6384",
		          data: std_data
		        }, {
		          label: compare_label,
		          backgroundColor: "#34a2eb",
		          data: compare_data
		        }
		      ]
		    },
		    options: {
		    	responsive: true,
		    	maintainAspectRatio: false,
		    	title: { display: false },
				tooltips:{
					enabled:true,
					titleSpacing:10,
					callbacks: {
		                label: function(tooltipItem, data) {
		                	var label = data.datasets[tooltipItem.datasetIndex].label || '';
		                	if(label) label += ' : ';
		                    return label + app.util.formatNumber(tooltipItem.yLabel) + ' 건';
		                }
		            }
				},
				layout: {
		            padding: {
		                left: 0,
		                right: 0,
		                top:30,
		                bottom: 0
		            }
		        },
				legend: {
					display: true,
					position:'bottom',
					labels: {
			              boxWidth: 12,
			              fontFamily:'dotum',
			              fontSize:12
			        }
				},
				scales: {
					xAxes: [{
						ticks: {
							fontColor: "#333",
							fontSize: 11,
							stepSize:1,
							beginAtZero: true,
							fontFamily:'dotum'
						}
					}],
					yAxes: [{
						ticks: {
							fontColor: "#333",
							fontSize: 11,
							beginAtZero: true,
							fontFamily:'dotum',
							callback: function(value, index, values) {
		                        return app.util.formatNumber(value);
		                    }
						}
					}]
				},
				plugins: {
				    labels: {
				        render: function (args) {
				        	return app.util.formatNumber(args.value);
				        },
				        fontSize: 10
				    }
				}
			}
		});
	}
};
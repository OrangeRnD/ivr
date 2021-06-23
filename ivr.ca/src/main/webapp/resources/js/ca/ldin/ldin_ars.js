app.ldin.ars = {
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
			var pCnt1 = 0, pCnt2 = 0;/*대표번호 건수*/
			var arsCnt1 = 0, arsCnt2 = 0;/*ars 건수 = 대표번호 건수 - 보이는 ars - 말로하는 ars*/
			for(var i = 0; i < $trs.length; i++) {
				var cd = $trs[i].getAttribute('data-cd');
				if(cd == 'P') {
					pCnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(!pCnt1) pCnt1 = 0;
					pCnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
					if(!pCnt2) pCnt2 = 0;
					
					arsCnt1 = pCnt1;
					arsCnt2 = pCnt2;
				} else if(cd == '2' || cd == '3') {
					var cnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(!cnt1) cnt1 = 0;
					var cnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
					if(!cnt2) cnt2 = 0;
					
					arsCnt1 -= cnt1;
					arsCnt2 -= cnt2;
				}
			}
			
			var multiLine = false;
			if($(document.body).width() <= 1904) multiLine = true;

			var utilization1 = 0, utilization2 = 0;
			for(var i = 0; i < $trs.length; i++) {
				var cd = $trs[i].getAttribute('data-cd');
				if(cd != '1') {
					std_tot_data.push(parseInt($trs[i].getAttribute('data-tot1'), 10));
					compare_tot_data.push(parseInt($trs[i].getAttribute('data-tot2'), 10));
					std_avg_data.push(parseInt($trs[i].getAttribute('data-avg1'), 10));
					compare_avg_data.push(parseInt($trs[i].getAttribute('data-avg2'), 10));
				}
				if(multiLine)
					labels.push($trs[i].getAttribute('data-nm').split(' '));
				else
					labels.push($trs[i].getAttribute('data-nm'));
				
				if(cd == 'C') {
					var tot1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
					if(tot1 == 0) {
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
				} else if(cd == 'P') {
					try {
						var ivrCnt1 = parseInt($trs[i-1].getAttribute('data-tot1'), 10);
						if(ivrCnt1 == 0 || pCnt1 == 0)
							$trs[i].children[3].innerHTML = '0 %';
						else
							$trs[i].children[3].innerHTML = Math.round(pCnt1/ivrCnt1*1000)/10 + ' %';

						var ivrCnt2 = parseInt($trs[i-1].getAttribute('data-tot2'), 10);
						if(ivrCnt2 == 0 || pCnt2 == 0)
							$trs[i].children[10].innerHTML = '0 %';
						else
							$trs[i].children[10].innerHTML = Math.round(pCnt2/ivrCnt2*1000)/10 + ' %';
					} catch(e) {
						$trs[i].children[3].innerHTML = '0 %';
						$trs[i].children[10].innerHTML = '0 %';
					}
				} else if(cd == '2' || cd == '3') {
					try {
						var cnt1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
						if(pCnt1 == 0 || cnt1 == 0)
							$trs[i].children[3].innerHTML = '0 %';
						else {
							var utilization = Math.round(cnt1/pCnt1*1000)/10;
							$trs[i].children[3].innerHTML = utilization + ' %';
							utilization1 += utilization;
						}

						var cnt2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
						if(pCnt2 == 0 || cnt2 == 0)
							$trs[i].children[10].innerHTML = '0 %';
						else {
							var utilization = Math.round(cnt2/pCnt2*1000)/10;
							$trs[i].children[10].innerHTML = utilization + ' %';
							utilization2 += utilization;
						}
					} catch(e) {
						$trs[i].children[3].innerHTML = '0 %';
						$trs[i].children[10].innerHTML = '0 %';
					}
				} else if(cd == '1') {
					$trs[i].children[3].innerHTML = app.util.formatNumber(arsCnt1);
					$trs[i].children[10].innerHTML = app.util.formatNumber(arsCnt2);

					std_tot_data.push(arsCnt1);
					compare_tot_data.push(arsCnt2);

					var period1 = parseInt($trs[i].getAttribute('data-period1'), 10);
					var period2 = parseInt($trs[i].getAttribute('data-period2'), 10);
					if(!period1) period1 = 0;
					if(!period2) period2 = 0;
					
					var avg1 = Math.round(arsCnt1 / (period1 == 0 ? 1 : period1));
					var avg2 = Math.round(arsCnt2 / (period2 == 0 ? 1 : period2));
					
					$trs[i].children[2].innerHTML = app.util.formatNumber(avg1);
					$trs[i].children[7].innerHTML = app.util.formatNumber(avg2);
					
					std_avg_data.push(parseInt(avg1, 10));
					compare_avg_data.push(parseInt(avg2, 10));
					/*이용률*/
					try {
						if(pCnt1 == 0 || arsCnt1 == 0)
							$trs[i].children[4].innerHTML = '0 %';
						else
							$trs[i].children[4].innerHTML = Math.round(arsCnt1/pCnt1*1000)/10 + ' %';

						if(pCnt2 == 0 || arsCnt2 == 0)
							$trs[i].children[11].innerHTML = '0 %';
						else
							$trs[i].children[11].innerHTML = Math.round(arsCnt2/pCnt2*1000)/10 + ' %';
					} catch(e) {
						$trs[i].children[4].innerHTML = '0 %';
						$trs[i].children[11].innerHTML = '0 %';
					}
					
					/*일평균 변동건수*/
					if(avg2 == 0) {
						$trs[i].children[8].innerHTML = '-';
						$trs[i].children[9].innerHTML = '-';
					} else if(avg1 == avg2) {
						$trs[i].children[8].innerHTML = '0';
						$trs[i].children[9].innerHTML = '0 %';
					} else if(avg1 > avg2) {
						$trs[i].children[8].innerHTML = app.util.formatNumber(avg1 - avg2);
						$trs[i].children[9].innerHTML = Math.round((avg1-avg2)/avg2*1000)/10 + ' %';
					} else if(avg1 < avg2) {
						$trs[i].children[8].innerHTML = '- ' + app.util.formatNumber(avg2 - avg1);
						$trs[i].children[8].className = $trs[i].children[8].className + ' minus';
						$trs[i].children[9].innerHTML = '- ' + Math.round((avg2-avg1)/avg2*1000)/10 + ' %';
						$trs[i].children[9].className = $trs[i].children[9].className + ' minus';
					}

					/*총건수 변동건수*/
					if(arsCnt2 == 0) {
						$trs[i].children[12].innerHTML = '-';
						$trs[i].children[13].innerHTML = '-';
					} else if(arsCnt1 == arsCnt2) {
						$trs[i].children[12].innerHTML = '0';
						$trs[i].children[13].innerHTML = '0 %';
					} else if(arsCnt1 > arsCnt2) {
						$trs[i].children[12].innerHTML = app.util.formatNumber(arsCnt1 - arsCnt2);
						$trs[i].children[13].innerHTML = Math.round((arsCnt1-arsCnt2)/arsCnt2*1000)/10 + ' %';
					} else if(arsCnt1 < arsCnt2) {
						$trs[i].children[12].innerHTML = '- ' + app.util.formatNumber(arsCnt2 - arsCnt1);
						$trs[i].children[12].className = $trs[i].children[12].className + ' minus';
						$trs[i].children[13].innerHTML = '- ' + Math.round((arsCnt2-arsCnt1)/arsCnt2*1000)/10 + ' %';
						$trs[i].children[13].className = $trs[i].children[13].className + ' minus';
					}
				}
			}
			
			for(var i = 0; i < $trs.length; i++) {
				var cd = $trs[i].getAttribute('data-cd');
				if(cd == '1') {
					/*ARS 이용콜수 이용율 다시 계산 (대표번호 이용율 전체 100% 맞추기)*/
					try {
						if(!(pCnt1 == 0 || arsCnt1 == 0))
							$trs[i].children[4].innerHTML = Math.round((100.0 - utilization1)*10)/10 + '%';

						if(!(pCnt2 == 0 || arsCnt2 == 0))
							$trs[i].children[11].innerHTML = Math.round((100.0 - utilization2)*10)/10 + '%';
					} catch(e) {
					}
				}
			}
			app.ldin.ars.chart('chart1', labels, '조회기간 일평균', '비교기간 일평균', std_avg_data, compare_avg_data);
			app.ldin.ars.chart('chart2', labels, '조회기간 총건수', '비교기간 총건수', std_tot_data, compare_tot_data);
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
app.ldin.callanalysis = {
	index:function(url) {
		app.util.sort($('#tblList').parent());

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
		
		this.search_after = function(result) {
			$('#std_period').html('조회 기간 ( ' + frm['param[FROM_DT1]'].value + ' ~ ' + frm['param[TO_DT1]'].value + ' )');
			$('#compare_period').html('비교 기간 ( ' + frm['param[FROM_DT2]'].value + ' ~ ' + frm['param[TO_DT2]'].value + ' )');

			var $trs = $('#tblList').children();
			var labels = [], std_tot_data = [], compare_tot_data = [], std_avg_data = [], compare_avg_data = [];
			var t_tot1 = 0, t_avg1 = 0, t_tot2 = 0, t_avg2 = 0;

			var multiLine = false;
			if($(document.body).width() <= 1904) multiLine = true;
			
			for(var i = 0; i < $trs.length-1; i++) {
				if(multiLine) {
					var cd = $trs[i].getAttribute('data-cd');
					if(cd == '1')
						labels.push(['1588', '2588']);
					else if(cd == '2')
						labels.push(['1566', '2566']);
					else if(cd == '3')
						labels.push(['카드', '신청']);
					else if(cd == '4')
						labels.push(['고객', '채번']);
					else if(cd == '5')
						labels.push('VIP');
					else if(cd == '6')
						labels.push(['카드', '연체']);
					else if(cd == '7')
						labels.push(['IT', '상담']);
					else if(cd == '8')
						labels.push(['정보', '동의', '철회']);
					else if(cd == '9')
						labels.push(['비밀', '번호', '받기']);
					else if(cd == '10')
						labels.push(['콜백']);
					else if(cd == '11')
						labels.push(['3자', '통화']);
					else if(cd == '12')
						labels.push(['소액', '콜백']);
					else
						labels.push($trs[i].getAttribute('data-nm'));
				} else
					labels.push($trs[i].getAttribute('data-nm'));
				var tot1 = parseInt($trs[i].getAttribute('data-tot1'), 10);
				t_tot1 += tot1;
				std_tot_data.push(tot1);
				var tot2 = parseInt($trs[i].getAttribute('data-tot2'), 10);
				t_tot2 += tot2;
				compare_tot_data.push(tot2);
				var avg1 = parseInt($trs[i].getAttribute('data-avg1'), 10);
				t_avg1 += avg1;
				std_avg_data.push(avg1);
				var avg2 = parseInt($trs[i].getAttribute('data-avg2'), 10);
				t_avg2 += avg2;
				compare_avg_data.push(avg2);
			}
			
			var tTr = $trs[$trs.length-1];
			tTr.children[1].innerHTML = app.util.formatNumber(t_avg1);
			tTr.children[2].innerHTML = app.util.formatNumber(t_tot1);
			tTr.children[5].innerHTML = app.util.formatNumber(t_avg2);
			tTr.children[8].innerHTML = app.util.formatNumber(t_tot2);
			
			//일평균변동
			if(t_avg2 <= 0) {
				tTr.children[6].innerHTML = '-';
				tTr.children[7].innerHTML = '-';
			} else if(t_avg1 == t_avg2) {
				tTr.children[6].innerHTML = '0';
				tTr.children[7].innerHTML = '0 %';
			} else if(t_avg1 > t_avg2) {
				tTr.children[6].innerHTML = '+ ' + app.util.formatNumber(t_avg1 - t_avg2);
				tTr.children[7].innerHTML = '+ ' + app.util.formatNumber(parseInt((t_avg1 - t_avg2) / t_avg2 * 10000, 10)/100) + ' %';
			} else {
				tTr.children[6].innerHTML = '- ' + app.util.formatNumber(t_avg2 - t_avg1);
				if(t_avg1 <= 0)
					tTr.children[7].innerHTML = '0 %';
				else
					tTr.children[7].innerHTML = '- ' + app.util.formatNumber(parseInt((t_avg2 - t_avg1) / t_avg1 * 10000, 10)/100) + ' %';
			}
			//총건수변동
			if(t_tot2 <= 0) {
				tTr.children[9].innerHTML = '-';
				tTr.children[10].innerHTML = '-';
			} else if(t_tot1 == t_tot2) {
				tTr.children[9].innerHTML = '0';
				tTr.children[10].innerHTML = '0 %';
			} else if(t_tot1 > t_tot2) {
				tTr.children[9].innerHTML = '+ ' + app.util.formatNumber(t_tot1 - t_tot2);
				tTr.children[10].innerHTML = '+ ' + app.util.formatNumber(parseInt((t_tot1 - t_tot2) / t_tot2 * 10000, 10)/100) + ' %';
			} else {
				tTr.children[9].innerHTML = '- ' + app.util.formatNumber(t_tot2 - t_tot1);
				if(t_tot1 <= 0)
					tTr.children[10].innerHTML = '0 %';
				else
					tTr.children[10].innerHTML = '- ' + app.util.formatNumber(parseInt((t_tot2 - t_tot1) / t_tot1 * 10000, 10)/100) + ' %';
			}
			
			app.ldin.callanalysis.chart('chart1', labels, '조회기간 일평균', '비교기간 일평균', std_avg_data, compare_avg_data);
			app.ldin.callanalysis.chart('chart2', labels, '조회기간 총건수', '비교기간 총건수', std_tot_data, compare_tot_data);
		};
		
		this.search_old = function(url) {
			app.request.submit('/ldin/callanalysis/list', $(frm), function(result) {
				var labels = [], std_data = [], compare_data = [], month_data = [], quarter_data = [];
				var total = {CALL_CNT1:0, CALL_CNT2:0, CALL_CNT3:0, CALL_CNT4:0};

				if(result.status == 200 && result.data.length > 0) {
					var tdInsert = function($td, data) {
						var chg2 = data.CALL_CNT1 - data.CALL_CNT2;
						var rt2 = chg2/data.CALL_CNT2*100;
						var chg3 = data.CALL_CNT1 - data.CALL_CNT3;
						var rt3 = chg3/data.CALL_CNT3*100;
						var chg4 = data.CALL_CNT1 - data.CALL_CNT4;
						var rt4 = chg4/data.CALL_CNT4*100;
						
						$td.html(app.util.formatNumber(data.CALL_CNT1) + ' 건');
						$td.next().html(app.util.formatNumber(data.CALL_CNT2) + ' 건')
						.next().html((chg2 > 0 ? '+' : '') + app.util.formatNumber(chg2))
						.next().html(Math.round(rt2) + ' %')
						.next().html(app.util.formatNumber(data.CALL_CNT3) + ' 건 / ' + app.util.formatNumber(Math.round(data.CALL_CNT3/data.DT_CNT3)) + ' 건')
						.next().html((chg3 > 0 ? '+' : '') + app.util.formatNumber(chg3))
						.next().html(Math.round(rt3) + ' %')
						.next().html(app.util.formatNumber(data.CALL_CNT4) + ' 건 / ' + app.util.formatNumber(Math.round(data.CALL_CNT4/data.DT_CNT4)) + ' 건')
						.next().html((chg4 > 0 ? '+' : '') + app.util.formatNumber(chg4))
						.next().html(Math.round(rt4) + ' %');
					};
					
					for(var i = 0; i < result.data.length; i++) {
						if(i == 0) {
							$('#std_period').html(frm['param[FROM_DT1]'].getAttribute('data-value') + ' ~ ' + frm['param[TO_DT1]'].getAttribute('data-value').substring(5) + ' (' + result.data[i].DT_CNT1 + '일)');
							$('#compare_period').html(frm['param[FROM_DT2]'].getAttribute('data-value') + ' ~ ' + frm['param[TO_DT2]'].getAttribute('data-value').substring(5) + ' (' + result.data[i].DT_CNT2 + '일)');
						}
						for(var j = 0; j < $trs.length; j++) {
							if($trs[j].getAttribute('data-key') == result.data[i].INPTH_SECD) {
								tdInsert($($trs[j].children[1]), result.data[i]);
								total['DT_CNT3'] = result.data[i]['DT_CNT3'];
								total['DT_CNT4'] = result.data[i]['DT_CNT4'];
								total['CALL_CNT1'] += result.data[i].CALL_CNT1;
								total['CALL_CNT2'] += result.data[i].CALL_CNT2;
								total['CALL_CNT3'] += result.data[i].CALL_CNT3;
								total['CALL_CNT4'] += result.data[i].CALL_CNT4;
								break;
							}
						}
						labels.push(result.data[i].INPTH_SECD_NM);
						std_data.push(result.data[i].CALL_CNT1);
						compare_data.push(result.data[i].CALL_CNT2);
						month_data.push(result.data[i].CALL_CNT3);
						quarter_data.push(result.data[i].CALL_CNT4);
					}
					tdInsert($($trs[$trs.length-1].children[1]), total);
				} else {
					$('#std_period').html(frm['param[FROM_DT1]'].getAttribute('data-value') + ' ~ ' + frm['param[TO_DT1]'].getAttribute('data-value').substring(5));
					$('#compare_period').html(frm['param[FROM_DT2]'].getAttribute('data-value') + ' ~ ' + frm['param[TO_DT2]'].getAttribute('data-value').substring(5));
					$('#pre_month_period').html(app.util.formatDate(frm['param[FROM_DT3]'].value) + ' ~ ' + app.util.formatDate(frm['param[TO_DT3]'].value).substring(4));
					$('#pre_quarter_period').html(app.util.formatDate(frm['param[FROM_DT4]'].value) + ' ~ ' + app.util.formatDate(frm['param[TO_DT4]'].value).substring(4));
				}

				app.ldin.callanalysis.chart('chart1', labels, '비교기간누적', std_data, compare_data);
				app.ldin.callanalysis.chart('chart2', labels, '전월평균', std_data, month_data);
				app.ldin.callanalysis.chart('chart3', labels, '전분기평균', std_data, quarter_data);
			});
		};
		
		//this.search();
	},
	chart:function(id, labels, std_label, compare_label, std_data, compare_data) {
		if(app.ldin.callanalysis['chart_' + id]) 
			app.ldin.callanalysis['chart_' + id].destroy();
		
		app.ldin.callanalysis['chart_' + id] = new Chart(document.getElementById(id), {
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
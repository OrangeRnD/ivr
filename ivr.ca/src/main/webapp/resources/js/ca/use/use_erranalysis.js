app.use.erranalysis = {
	index:function(url) {
		var frm = document.search_frm;
		
		if(!frm.getAttribute('data-session-type')) {
			var now = new Date();
			frm['param[DT]'].value = app.util.formatDate(now, '');
			now.setDate(now.getDate() - 1);
			frm['param[PRE_DT]'].value = app.util.formatDate(now, '');
		}
		
		//전일인 경우 처리
		var disabled = function(b) {
			/**상하위 제외 기본값*/
			if(b) {
				if(frm['param[AVG_ABOVE]'].value) 
					frm['param[AVG_ABOVE]'].setAttribute('data-before', frm['param[AVG_ABOVE]'].value);
				frm['param[AVG_ABOVE]'].value = '';
			} else {
				if(!frm['param[AVG_ABOVE]'].value) {
					if(frm['param[AVG_ABOVE]'].getAttribute('data-before')) 
						frm['param[AVG_ABOVE]'].value = frm['param[AVG_ABOVE]'].getAttribute('data-before');
					else
						frm['param[AVG_ABOVE]'].value = '5';
				}
			}
			frm['param[AVG_ABOVE]'].disabled = b;
			frm['param[RESTDE_ALTV]'][0].disabled = b;
			frm['param[RESTDE_ALTV]'][1].disabled = b;
			frm['param[RESTDE_ALTV]'][2].disabled = b;
		};
		
		$(frm['param[TYPE]']).on('click', function(e) {
			if(this.value == '2') {
				frm['param[FROM_DT]'].value = '';
				frm['param[TO_DT]'].value = '';
				disabled(true);
			} else if(this.value == '101') {
				//전주 동일요일 대비
				var date = new Date();
				date.setDate(date.getDate() - 7);
				frm['param[PRE_DT]'].value = app.util.formatDate(date, '');
				
				frm['param[FROM_DT]'].value = '';
				frm['param[TO_DT]'].value = '';
				disabled(true);
			} else if(this.value == '102') {
				//전월 동일일자
				var date = new Date();
				var month = date.getMonth();
				date.setMonth(date.getMonth() - 1);
				if(month == date.getMonth()) {
					//2월이나 31일이 없는 월은 마지막 일자로
					date.setDate(0);
				}
				frm['param[PRE_DT]'].value = app.util.formatDate(date, '');
				
				frm['param[FROM_DT]'].value = '';
				frm['param[TO_DT]'].value = '';
				disabled(true);
			} else if(this.value == '103') {
				//전분기 동일차월 동일일자
				var date = new Date();
				var month = date.getMonth();
				date.setMonth(date.getMonth() - 3);
				if(month == date.getMonth()) {
					//2월이나 31일이 없는 월은 마지막 일자로
					date.setDate(0);
				}
				frm['param[PRE_DT]'].value = app.util.formatDate(date, '');
				
				frm['param[FROM_DT]'].value = '';
				frm['param[TO_DT]'].value = '';
				disabled(true);
			} else {
				frm['param[PRE_DT]'].value = '';
				disabled(false);
			}
		});

		if(frm.getAttribute('data-session-type')) {
			var types = frm['param[TYPE]'];
			for(var i = 0; i < types.length; i++) {
				if(types[i].value == frm.getAttribute('data-session-type')) {
					$(types[i]).trigger('click');
				}
			}
		} else
			disabled(true);
		
		this.search = function(url) {
			app.request.submit('/use/erranalysis/list', $(frm), function(result) {
				if(!result.data) {
					alert('상하위 제외 범위를 초과하였습니다.');
					document.search_frm['param[AVG_ABOVE]'].select();
					return;
				}

				if(frm['param[FROM_DT]'].value) $('#compare_period').html(app.util.formatDate(frm['param[FROM_DT]'].value, '년 ', '월 ', '일') + ' (' + app.util.getDay(app.util.formatDate(frm['param[FROM_DT]'].value)) + ') ~ ' + app.util.formatDate(frm['param[TO_DT]'].value, '년 ', '월 ', '일') + '(' + app.util.getDay(app.util.formatDate(frm['param[TO_DT]'].value)) + ')');
				else $('#compare_period').html(app.util.formatDate(frm['param[PRE_DT]'].value, '년 ', '월 ', '일') + ' (' + app.util.getDay(app.util.formatDate(frm['param[PRE_DT]'].value)) + ')');
				
				app.util.printDate1($('#std_period'));

				var labels = [], std_data = [], compare_data = [], max_data = [], max_dt = '';
				var b = false;

				if(result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(result.data[i].TYPE == 2) {
							/*if(!b) {
								if(frm['param[FROM_DT]'].value) $('#compare_period').html(':&nbsp;&nbsp;' + app.util.formatDate(frm['param[FROM_DT]'].value) + ' ~ ' + app.util.formatDate(frm['param[TO_DT]'].value) + ' (' + result.data[i].DT_CNT + '일)');
								b = true;
							}*/
							compare_data.push(parseInt(result.data[i].CNT, 10));
						} else if(result.data[i].TYPE == 3) {
							max_dt = result.data[i].DT;
							max_data.push(parseInt(result.data[i].CNT, 10));
						} else {
							labels.push(result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
							std_data.push(result.data[i].CNT);
						}
					}
					if(labels.length == 0) {
						if(compare_data.length != 0) {
							for(var i = 0; i < result.data.length; i++) {
								if(result.data[i].TYPE == 2) {
									labels.push(result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
								}
							}
						}
					}
					if(labels.length > 0) labels[labels.length-1] = '현재 ' + labels[labels.length-1];
				}
				app.use.erranalysis.chart('myChart', labels, std_data, compare_data, max_data, max_dt);
			});
		};
		this.search();

		app.util.sortForScrollable($('#err_header'), $('#err_list'));
		
		$('#ico_popup_close, #popupDimed').on('click', function(e) {
			$('#err_popup').fadeOut();
		});
	},
	chart:function(id, labels, std_data, compare_data, max_data, max_dt) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		var label = '', maxLabel = '', dataType = '0';
		var types = document.search_frm['param[TYPE]'];
		for(var i = 0; i < types.length; i++) {
			if(types[i].checked) {
				if(types[i].value == '2') {
					label = '전일';
				} else if(types[i].value == '4') {
					label = '전주 평균';
					maxLabel = '전주 최대일 (' + app.util.formatDate(max_dt, '-') + ')';
				} else if(types[i].value == '6') {
					label = '전월 평균';
					maxLabel = '전월 최대일 (' + app.util.formatDate(max_dt, '-') + ')';
				} else if(types[i].value == '7') {
					label = '전분기';
					maxLabel = '전분기 최대일 (' + app.util.formatDate(max_dt, '-') + ')';
				} else if(types[i].value == '101') {
					label = '전주 동일요일';
				} else if(types[i].value == '102') {
					label = '전월 동일일자';
				} else if(types[i].value == '103') {
					label = '전분기 동일차월 동일일자';
				}
				dataType = types[i].value;
				break;
			}
		}
		
		var datasets = [{ 
				data: compare_data,
				label: label,
				backgroundColor: ['rgba(21, 122, 210, 0.3)'],
				borderColor: ['rgba(0, 0, 0, 0)'],
				pointStyle:'cross', 
				fill: true,
				pointDot: false,
				//radius: 0, 
				lineTension:0,
				dataType:dataType
		}];
		
		if(max_data.length != 0) {
			datasets.push({ 
				data: max_data,
				label: maxLabel,
				borderColor: "#2dbfef",
				borderWidth:"2",
				pointBackgroundColor: "#2dbfef",
				pointStyle:'triangle', 
				fill: false,
				pointDot: false,
				lineTension:0,
				dataType:dataType
		    });
		} 

		datasets.push({ 
			data: std_data,
			label: '실시간 에러코드 발생 건수',
			borderColor: "#ff6384",
			borderWidth:"2",
			pointBackgroundColor: "#ff6384",
			pointStyle:'rect', 
			fill: false,
			pointDot: false,
			lineTension:0,
			dataType:'0'
	    });
		
		app.chart[id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: labels,
		        datasets: datasets
		    },
		    // Configuration options go here
		    options: {
				responsive: true,
				maintainAspectRatio: false,
				legend: {
					display: true,
					position:'bottom',
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
		                	var label = data.datasets[tooltipItem.datasetIndex].label || '';
		                	if(label) label += ' : ';
		                    return label + app.util.formatNumber(tooltipItem.yLabel) + ' 건';
		                },
		                title:function(tooltipItem, data) {
		                	var value = tooltipItem[0].xLabel;
		                	if(value.length == 8) {
		                		var min = parseInt(value.substring(6), 10);
		                		return '현제 시간  ' + parseInt(value.substring(3, 5), 10) + ' 시 ' + (min == 0 ? '' : min + ' 분');
		                	} else {
			                	var min = parseInt(value.substring(3), 10);
			                	return parseInt(value.substring(0, 2), 10) + ' 시 ' + (min == 0 ? '' : min + ' 분');
		                	}
		                }
		            }
				},
				layout: {
		            padding: {
		                left: 10,
		                right: 20,
		                top:30,
		                bottom: 0
		            }
		        },
				scales: {
					xAxes: [{
						ticks: {
							fontColor: "#999999",
							fontSize: 11,
							stepSize:1,
							beginAtZero: false,
							fontFamily:'dotum'
						},
						display: true,
						scaleLabel: {
							display: true,
							labelString: '시간',
							fontFamily:'dotum',
							fontSize:12
						}
					}],
					yAxes: [{
						ticks: {
							fontColor: "#999999",
							fontSize: 11,
							stepSize: 0,
							beginAtZero: true,
							fontFamily:'dotum',
							callback: function(value, index, values) {
		                        return app.util.formatNumber(value);
		                    }
						},
						display: true,
						scaleLabel: {
							display: true,
							labelString: '건수',
							fontFamily:'dotum',
							fontSize:12
						}
					}]
				},
				onClick: function(e, datas) {
					var activePoints = app.chart[id].getElementAtEvent(e);
					if(!(activePoints && activePoints.length != 0)) return;

					for(var i = 0; i < activePoints.length; i++) {
						var activePoint = activePoints[i];
						if(app.chart[id].data.datasets[activePoint._datasetIndex].dataType == '0') {
					        var label = app.chart[id].data.labels[activePoint._index];
					        /*var value = app.chart[id].data.datasets[activePoint._datasetIndex].data[activePoint._index];*/
					        
					        app.use.erranalysis.err_list(label);
					        return;
						}
					}
					
					activePoints = app.chart[id].getElementsAtEvent(e);
					if(!(activePoints && activePoints.length == 2)) return;
					
					if(app.chart[id].data.datasets[activePoints[0]._datasetIndex].data[activePoints[0]._index] == app.chart[id].data.datasets[activePoints[1]._datasetIndex].data[activePoints[1]._index]) {
				        var label = app.chart[id].data.labels[activePoints[0]._index];
				        app.use.erranalysis.err_list(label);
					}
				}
			}
		});
	},
	err_list:function(label) {
		$('#err_header').find('.sorting2').removeClass('sorting2Down').removeClass('sorting2Up');
		
		var tmp = label.split(':');
		if(tmp[0].indexOf(' ') != -1) tmp[0] = tmp[0].substring(tmp[0].indexOf(' ')+1);
        var params = 'param[HH]=' + tmp[0] + '&param[MM]=' + tmp[1];
        app.request.get('/use/erranalysis/err', params, function(result) {
        	var $err_list = $('#err_list')
        	$err_list.children().remove();
        	if(result.data && result.data.length != 0) {
        		for(var i = 0; i < result.data.length; i++) {
        			var tr = document.createElement('tr');
        			var errCdTd = document.createElement('td');
        			errCdTd.className = 'txt';
        			errCdTd.innerHTML = result.data[i].ERR_CD;
        			errCdTd.title = result.data[i].ERR_CN;
        			tr.appendChild(errCdTd);
        			
        			var globalIdTd = document.createElement('td');
        			globalIdTd.className = 'txt';
        			globalIdTd.innerHTML = result.data[i].GLOBAL_ID;
        			tr.appendChild(globalIdTd);

        			var srvcCdTd = document.createElement('td');
        			srvcCdTd.className = 'txt';
        			srvcCdTd.innerHTML = result.data[i].SRVC_CD;
        			tr.appendChild(srvcCdTd);

        			var srvcNmTd = document.createElement('td');
        			srvcNmTd.className = 'txt';
        			srvcNmTd.innerHTML = result.data[i].SRVC_NM;
        			tr.appendChild(srvcNmTd);

        			var tranCdTd = document.createElement('td');
        			tranCdTd.className = 'txt';
        			tranCdTd.innerHTML = result.data[i].TRAN_CD;
        			tr.appendChild(tranCdTd);

        			var tranNmTd = document.createElement('td');
        			tranNmTd.className = 'txt';
        			tranNmTd.innerHTML = result.data[i].TRAN_NM;
        			tr.appendChild(tranNmTd);

        			var dateTd = document.createElement('td');
        			dateTd.className = 'txt';
        			var date = new Date();
        			date.setTime(result.data[i].RCPTN_DT);
        			dateTd.innerHTML = app.util.formatTimestamp(date, app.constants.date_format, true);
        			tr.appendChild(dateTd);
        			
        			$err_list.append(tr);
        		}
        	}
        	$('#err_popup').fadeIn(400, function() {
        		var headers = $('#err_header').children();
        		if($err_list.children().length > 0) {
	        		var tds = $err_list.children()[0].children;
	        		for(var i = 0; i < headers.length - 1; i++) {
		        		$(headers[i]).css('width', tds[i].offsetWidth);
	        		}
	        		$(headers[headers.length-1]).css('width', (parseInt(tds[headers.length-1].offsetWidth, 10) - 30));
        		} else {
        			$err_list.append('<tr><td colspan="7" style="text-align:center;">데이터가 존재하지 않습니다.</td></tr>')
        		}
        	});
        });
	}
};
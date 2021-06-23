app.use.service = {
	index:function(url) {
		app.util.sort($('#lists').parent());
		app.util.sort($('#lists1').parent());
		app.util.sort($('#lists2').parent());

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
		
		this.search = function(url) {
			app.request.submit('/use/service/list', $(frm), function(result) {
				
				//var bar_labels = {type1:[], type2:[], type3:[]}, bar_data = {type1:[], type2:[], type3:[]};
				var trs = document.getElementById('lists').children;
				var type = 0, row = 0, types = [];
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(type != result.data[i].TYPE) {
							if(type != 0) {
								for(var r = row; r < 10; r++) {
									var tds = trs[r].children;
									tds[(type*2)-2].innerHTML = '-';
									tds[(type*2)-2].title = '';
									tds[(type*2)-1].innerHTML = '-';
								}
							}
							row = 0;
							type = result.data[i].TYPE;
							types.push(type);
						}
						var tds = trs[row].children;
						tds[(type*2)-2].innerHTML = result.data[i].SRVC_NM;
						tds[(type*2)-2].title = result.data[i].SRVC_CD;
						tds[(type*2)-1].innerHTML = app.util.formatNumber(result.data[i].CNT);
						/*if(type != 4) {
							if(bar_labels['type'+type].length < 5) {
								bar_labels['type'+type].push(result.data[i].SRVC_NM);
								bar_data['type'+type].push(result.data[i].CNT);
							}
						}*/
						row++;
					}
					for(var r = row; r < 10; r++) {
						var tds = trs[r].children;
						tds[(type*2)-2].innerHTML = '-';
						tds[(type*2)-2].title = '';
						tds[(type*2)-1].innerHTML = '-';
					}
					
					for(var i= 1; i < 5; i++) {
						if(types.indexOf(i) == -1) {
							for(var r = 0; r < 10; r++) {
								var tds = trs[r].children;
								tds[(i*2)-2].innerHTML = '-';
								tds[(i*2)-2].title = '';
								tds[(i*2)-1].innerHTML = '-';
							}
						}
					}
				} else {
					for(var r = 0; r < 10; r++) {
						var tds = trs[r].children;
						for(var c = 0; c < 8; c++) {
							tds[c].innerHTML = '-';
							tds[c].title = '';
						}
					}
				}

				/*app.use.service.bar_chart('chart1', bar_labels.type1, bar_data.type1);
				app.use.service.bar_chart('chart2', bar_labels.type2, bar_data.type2);
				app.use.service.bar_chart('chart3', bar_labels.type3, bar_data.type3);*/
			});
			
			app.request.submit('/use/service/top5', $(frm), function(result) {
				var labels = [], data = {}, label = {}, keys = [], maxValue = 0;
				var hm = '';
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(hm != result.data[i].HM) {
							labels.push(result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
							hm = result.data[i].HM;
						}
						if(!data[result.data[i].SRVC_CD]) {
							data[result.data[i].SRVC_CD] = [];
							label[result.data[i].SRVC_CD] = result.data[i].SRVC_NM;
							keys.push(result.data[i].SRVC_CD);
						}
						data[result.data[i].SRVC_CD].push(result.data[i].CNT);
						if(maxValue < result.data[i].CNT) maxValue = result.data[i].CNT;
					}
					if(labels.length > 0) labels[labels.length-1] = '현재 ' + labels[labels.length-1];
				}
				app.use.service.chart('myChart', labels, keys, data, label, maxValue);
			});
			

			app.request.submit('/use/service/user', $(frm), function(result) {
				var trs = document.getElementById('lists1').children;
				var type = 0, row = 0, types = [];
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(type != result.data[i].TYPE) {
							if(type != 0) {
								for(var r = row; r < 10; r++) {
									var tds = trs[r].children;
									tds[(type*2)-2].innerHTML = '-';
									tds[(type*2)-2].title = '';
									tds[(type*2)-1].innerHTML = '-';
								}
							}
							row = 0;
							type = result.data[i].TYPE;
							types.push(type);
						}
						var tds = trs[row].children;
						tds[(type*2)-2].innerHTML = result.data[i].SRVC_NM;
						tds[(type*2)-2].title = result.data[i].SRVC_CD;
						tds[(type*2)-1].innerHTML = app.util.formatNumber(result.data[i].CNT);
						row++;
					}
					for(var r = row; r < 10; r++) {
						var tds = trs[r].children;
						tds[(type*2)-2].innerHTML = '-';
						tds[(type*2)-2].title = '';
						tds[(type*2)-1].innerHTML = '-';
					}

					for(var i= 1; i < 3; i++) {
						if(types.indexOf(i) == -1) {
							for(var r = 0; r < 10; r++) {
								var tds = trs[r].children;
								tds[(i*2)-2].innerHTML = '-';
								tds[(i*2)-2].title = '';
								tds[(i*2)-1].innerHTML = '-';
							}
						}
					}
				} else {
					for(var r = 0; r < 10; r++) {
						var tds = trs[r].children;
						for(var c = 0; c < 4; c++) {
							tds[c].innerHTML = '-';
							tds[c].title = '';
						}
					}
				}
			});
			
			app.request.submit('/use/service/age', $(frm), function(result) {
				var trs = document.getElementById('lists2').children;
				var type = 0, row = 0, types = [];
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(type != result.data[i].TYPE) {
							if(type != 0) {
								for(var r = row; r < 10; r++) {
									var tds = trs[r].children;
									tds[(type*2)-2].innerHTML = '-';
									tds[(type*2)-2].title = '';
									tds[(type*2)-1].innerHTML = '-';
								}
							}
							row = 0;
							type = result.data[i].TYPE;
							types.push(type);
						}
						var tds = trs[row].children;
						tds[(type*2)-2].innerHTML = result.data[i].SRVC_NM;
						tds[(type*2)-2].title = result.data[i].SRVC_CD;
						tds[(type*2)-1].innerHTML = app.util.formatNumber(result.data[i].CNT);
						row++;
					}
					for(var r = row; r < 10; r++) {
						var tds = trs[r].children;
						tds[(type*2)-2].innerHTML = '-';
						tds[(type*2)-2].title = '';
						tds[(type*2)-1].innerHTML = '-';
					}

					for(var i= 1; i < 7; i++) {
						if(types.indexOf(i) == -1) {
							for(var r = 0; r < 10; r++) {
								var tds = trs[r].children;
								tds[(i*2)-2].innerHTML = '-';
								tds[(i*2)-2].title = '';
								tds[(i*2)-1].innerHTML = '-';
							}
						}
					}
				} else {
					for(var r = 0; r < 10; r++) {
						var tds = trs[r].children;
						for(var c = 0; c < 12; c++) {
							tds[c].innerHTML = '-';
							tds[c].title = '';
						}
					}
				}
			});

			$('#std_period').html(app.util.formatDate(document.search_frm['param[FROM_DT]'].value, '년 ', '월 ', '일') + ' ~ ' + app.util.formatDate(document.search_frm['param[TO_DT]'].value, '년 ', '월 ', '일'));
		};
		
		this.search();
	},
	chart:function(id, labels, keys, data, label, maxValue) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		
		var datasets = [{
				data: data[keys[0]],
				label: label[keys[0]] ? label[keys[0]] : '-',
				borderColor: "#ff6384",
				borderWidth:"2",
				pointBackgroundColor: "#ff6384",
				pointStyle:'circle', 
				fill: false,
				lineTension:0
	    	}, { 
				data: data[keys[1]],
				label: label[keys[1]] ? label[keys[1]] : '-',
				borderColor: "#5d7bf7",
				borderWidth:"2",
				pointBackgroundColor: "#5d7bf7",
				pointStyle:'rectRot', 
				fill: false,
				lineTension:0
	    	}, { 
				data: data[keys[2]],
				label: label[keys[2]] ? label[keys[2]] : '-',
				borderColor: "#2dbfef",
				borderWidth:"2",
				pointBackgroundColor: "#2dbfef",
				pointStyle:'triangle', 
				fill: false,
				lineTension:0
	    	}, { 
				data: data[keys[3]],
				label: label[keys[3]] ? label[keys[3]] : '-',
				borderColor: "#6cbd30",
				borderWidth:"2",
				pointBackgroundColor: "#6cbd30",
				pointStyle:'rect', 
				fill: false,
				lineTension:0
	    	}, { 
				data: data[keys[4]],
				label: label[keys[4]] ? label[keys[4]] : '-',
				borderColor: "#dbd40f",
				borderWidth:"2",
				pointBackgroundColor: "#dbd40f",
				pointStyle:'crossRot', 
				fill: false,
				lineTension:0
	    	}
	    ];
		
		for(var i = 5 - keys.length; i > 0; i--) {
			datasets.pop();
		}
		
		/*$('#top5_1').html(label[keys[0]]);
		$('#top5_2').html(label[keys[1]]);
		$('#top5_3').html(label[keys[2]]);
		$('#top5_4').html(label[keys[3]]);
		$('#top5_5').html(label[keys[4]]);*/
		
		app.chart[id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: labels,
		        datasets: datasets
			},
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
				/*events: false,
			    tooltips: {
			        enabled: false
			    },*/
			    hover: {
			        animationDuration: 0
			    },
			    animation: {
			        onComplete: function () {
			            var chartInstance = this.chart,
			                ctx = chartInstance.ctx;
			            ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
			            ctx.textAlign = 'center';
			            ctx.textBaseline = 'bottom';

			            this.data.datasets.forEach(function (dataset, i) {
			                var meta = chartInstance.controller.getDatasetMeta(i);
			                if(meta.hidden) return;
			                meta.data.forEach(function (bar, index) {
			                    var data = dataset.data[index];
			                    if(parseInt(data, 10) >= maxValue*0.7) {
			                    	ctx.fillStyle = dataset.borderColor;
			                    	ctx.fillText(app.util.formatNumber(data), bar._model.x, bar._model.y - 5);
			                    }
			                });
			            });
			        }
			    }
		    }
		});
	},
	bar_chart:function(id, labels, data) {
		if(app.use.service['chart_' + id]) 
			app.use.service['chart_' + id].destroy();
		
		app.use.service['chart_' + id] = new Chart(document.getElementById(id), {
		    type: 'bar',
		    data: {
		      labels: labels,
		      datasets: [
		        {
		          label: '',
		          backgroundColor: "#34a2eb",
		          data: data
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
		    	legend: {display: false	},
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
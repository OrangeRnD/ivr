app.analysis.error = {
	index:function(url) {
		var $frm = $(document.search_frm);
		
		if($frm.attr('data-session-type')) {
			if($frm.attr('data-session-type') == '0') {
				$frm[0]['param[FROM_DT]'].value = app.util.formatDate($frm[0]['param[FROM_DT]'].value, '-');
				$frm[0]['param[TO_DT]'].value = app.util.formatDate($frm[0]['param[TO_DT]'].value, '-');
				$('#std_input').prop('checked', true);
			} else if($frm.attr('data-session-type') == '3')
				app.util.period($('#lately_week'), 'lately_week');
			else if($frm.attr('data-session-type') == '4')
				app.util.period($('#lately_month'), 'lately_month');
			else if($frm.attr('data-session-type') == '5')
				app.util.period($('#lately_3month'), 'lately_3month');
			else
				app.util.period($('#lately_week'), 'lately_week');
		} else {
			if(window.location.search && window.location.search.indexOf('dt=') != -1) {
				var param = window.location.search;
				param = param.substring(param.indexOf('dt=')+3);
				if(param) {
					param = app.util.formatDate(param, '-');
					$frm[0]['param[FROM_DT]'].value = param;
					$frm[0]['param[TO_DT]'].value = param;
					document.getElementById('std_input').checked = true;
				} else
					app.util.period($('#lately_week'), 'lately_week');
			} else
				app.util.period($('#lately_week'), 'lately_week');
		}
		
		var $err_list = $('#err_list');
		this.search = function(url) {
			app.request.submit('/analysis/error/stat', $frm, function(result) {
				/*에러코드누계*/
				$('#err_cnt').html(app.util.formatNumber(result.data.ERR_CNT) + '<span>건</span>');
				
				var labels = [], data = [], desc = [];
				for(var i = 0; i < result.data.ERR_LIST.length; i++) {
					labels.push(result.data.ERR_LIST[i].ERR_CD);
					data.push(result.data.ERR_LIST[i].CNT);
					desc.push(result.data.ERR_LIST[i].ERR_CN);
				}
				/*에러코드 TOP5*/
				app.analysis.error.pie('errChart', labels, data, desc);

				var labels2 = [], data2 = [], desc2 = [];
				for(var i = 0; i < result.data.TR_LIST.length; i++) {
					labels2.push(result.data.TR_LIST[i].TRAN_CD);
					data2.push(result.data.TR_LIST[i].CNT);
					desc2.push(result.data.TR_LIST[i].TRAN_NM);
				}
				/*에러코드 연계거래 TOP5*/
				app.analysis.error.pie('trChart', labels2, data2, desc2);
			});
			
			/*에러 리스트*/
			app.request.page('/analysis/error/err', $frm.serialize(), function(result) {
				$err_list.children().remove();
				$err_list.html(result);
			});
			
			/*당일 에러코드 TOP 5 시간별 추이*/
			app.request.submit('/analysis/error/tr', $frm, function(result) {
				var labels = [], datas = {}, label = {}, keys = [], maxValue = 0, desc = [];
				var hm = '';
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(hm != result.data[i].HM) {
							labels.push(result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
							hm = result.data[i].HM;
						}
						if(!datas[result.data[i].ERR_CD]) {
							datas[result.data[i].ERR_CD] = [];
							label[result.data[i].ERR_CD] = result.data[i].ERR_CD;
							keys.push(result.data[i].ERR_CD);
							desc.push(result.data[i].ERR_CN);
						}
						datas[result.data[i].ERR_CD].push(result.data[i].CNT);
						if(maxValue < result.data[i].CNT) maxValue = result.data[i].CNT;
					}
					if(labels.length > 0) labels[labels.length-1] = '현재 ' + labels[labels.length-1];
				}
				app.analysis.error.chart('myChart', labels, keys, datas, label, desc, maxValue);
			});
		};
		
		this.search();
	},
	pie:function(id, labels, datas, desc) {
		if(app.chart[id]) 
			app.chart[id].destroy();

		var $tooltips = $('#tooltips');
		app.chart[id] = new Chart(document.getElementById(id), {
			type: 'pie',
		    data: {
		        labels: labels,
		        desc:desc,
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
			        },
			        onHover:function(e, legendItem) {
			        	var index = legendItem.index;
			            var ci = this.chart;
			            if(ci.data.desc[index])
			            	$tooltips.css({top:e.pageY+10, left:e.pageX}).html(ci.data.desc[index]).show();
			        },
			        onLeave:function(e, legendItem) {
			        	$tooltips.hide();
			        }
				},
				tooltips:{
					enabled:true,
					titleSpacing:10,
					callbacks: {
				        label: function(tooltipItem, data) {
				        	var cnt = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
				            return app.util.formatNumber(cnt) + ' 건';
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
	},
	chart:function(id, labels, keys, datas, label, desc, maxValue) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		
		/*$('#top5_1').html(label[keys[0]]).attr('title', desc[0]);
		$('#top5_2').html(label[keys[1]]);
		$('#top5_3').html(label[keys[2]]);
		$('#top5_4').html(label[keys[3]]);
		$('#top5_5').html(label[keys[4]]);*/
		
		var datasets =[{
				data: datas[keys[0]],
				label: label[keys[0]],
				borderColor: "#ff6384",
				borderWidth:"2",
				pointBackgroundColor: "#ff6384",
				pointStyle:'circle', 
				fill: false,
				lineTension:0
	    	}, { 
				data: datas[keys[1]],
				label: label[keys[1]],
				borderColor: "#5d7bf7",
				borderWidth:"2",
				pointBackgroundColor: "#5d7bf7",
				pointStyle:'rectRot', 
				fill: false,
				lineTension:0
	    	}, { 
				data: datas[keys[2]],
				label: label[keys[2]],
				borderColor: "#2dbfef",
				borderWidth:"2",
				pointBackgroundColor: "#2dbfef",
				pointStyle:'triangle', 
				fill: false,
				lineTension:0
	    	}, { 
				data: datas[keys[3]],
				label: label[keys[3]],
				borderColor: "#6cbd30",
				borderWidth:"2",
				pointBackgroundColor: "#6cbd30",
				pointStyle:'rect', 
				fill: false,
				lineTension:0
	    	}, { 
				data: datas[keys[4]],
				label: label[keys[4]],
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
		
		var $tooltips = $('#tooltips');
		app.chart[id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: labels,
		        desc:desc,
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
			        },
			        onHover:function(e, legendItem) {
			        	var index = legendItem.datasetIndex;
			            var ci = this.chart;
			            if(ci.data.desc[index])
			            	$tooltips.css({top:e.pageY+10, left:e.pageX}).html(ci.data.desc[index]).show();
			        },
			        onLeave:function(e, legendItem) {
			        	$tooltips.hide();
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
	}
};
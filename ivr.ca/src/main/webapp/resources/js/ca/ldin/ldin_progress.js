app.ldin.progress = {
	index:function(url) {
		var dly_year = document.getElementById('dly_year');
		var mly_year = document.getElementById('mly_year');
		var now = new Date();
		for(var i = 2018; i <= now.getFullYear(); i++) {
			dly_year.options.add(new Option(i+'년', i));
			mly_year.options.add(new Option(i+'년', i));
		}
		if(document.search_frm.ym.value) 
			dly_year.value = document.search_frm.ym.value.substring(0, 4);
		else
			dly_year.value = now.getFullYear();
		
		if(document.search_frm.year.value) 
			mly_year.value = document.search_frm.year.value;
		else
			mly_year.value = now.getFullYear();
		
		var dly_month = document.getElementById('dly_month');
		for(var i = 1; i <= 12; i++) {
			dly_month.options.add(new Option(i+'월', i<10?'0'+i:i));
		}
		if(document.search_frm.ym.value) 
			dly_month.value = document.search_frm.ym.value.substring(4);
		else {
			var m = now.getMonth()+1;
			dly_month.value = m < 10 ? '0' + m : m;
		}
		
		this.search = function() {
			app.ldin.progress.daily();
			app.ldin.progress.monthly();
		};
		$(dly_year).on('change', function(e) {
			app.ldin.progress.daily();
		});
		$(mly_year).on('change', function(e) {
			app.ldin.progress.monthly();
		});
		$(dly_month).on('change', function(e) {
			app.ldin.progress.daily();
		});
		$('#restde_altv, #restde_altv0, #restde_altv1').on('click', function(e) {
			app.ldin.progress.monthly();
		});
		
		this.search();
	},
	daily:function() {
		document.search_frm.ym.value = dly_year.value + dly_month.value;
		app.request.get('/ldin/progress/daily', 'ym=' + dly_year.value + dly_month.value, function(result) {
			var labels = [];
			var call = [];
			var tr = [];
			var user = [];
			var maxValue = 0;

			if(result.status == 200 && result.data.length > 0) {
				for(var i = 0; i < result.data.length; i++) {
					/*if(i == 0) {
						var now = new Date();
						if(result.data[i].PRCS_DT) now.setTime(result.data[i].PRCS_DT);
						$('#prcs_dt').html(now.getFullYear() + '<span>년</span> ' + (now.getMonth()+1) + '<span>월</span> ' + (now.getDate()) + '<span>일</span> ' + now.getHours() + '<span>시</span> ' + (now.getMinutes()) + '<span>분 기준</span>');
					}*/
					labels.push(result.data[i].DT.substring(6));
					
					var call_cnt = parseInt(result.data[i].CALL_CNT, 10);
					call.push(call_cnt);
					if(maxValue < call_cnt) maxValue = call_cnt;
					
					var tr_cnt = parseInt(result.data[i].TR_CNT, 10);
					tr.push(tr_cnt);
					if(maxValue < tr_cnt) maxValue = tr_cnt;
					
					var user_cnt = parseInt(result.data[i].USER_CNT, 10);
					user.push(user_cnt);
					if(maxValue < user_cnt) maxValue = user_cnt;
				}
			}
			app.util.printDate1($('#prcs_dt'));
			app.ldin.progress.chart('myChart', labels, '일', tr, user, call, maxValue);
		});
	},
	monthly:function() {
		var param = 'year=' + mly_year.value;
		document.search_frm.year.value = mly_year.value;
		
		if(document.getElementById('restde_altv0').checked) {
			param += '&restde=0';
			document.search_frm.restde.value = '0';
		}
		if(document.getElementById('restde_altv1').checked) {
			param += '&restde=1';
			document.search_frm.restde.value = '1';
		}
		app.request.get('/ldin/progress/monthly', param, function(result) {
			var labels = [];
			var call = [];
			var tr = [];
			var user = [];
			var maxValue = 0;
			if(result.status == 200 && result.data.length > 0) {
				for(var i = 0; i < result.data.length; i++) {
					labels.push(result.data[i].DT.substring(4));
					
					var call_cnt = parseInt(result.data[i].CALL_CNT, 10);
					call.push(call_cnt);
					if(maxValue < call_cnt) maxValue = call_cnt;
					
					var tr_cnt = parseInt(result.data[i].TR_CNT, 10);
					tr.push(tr_cnt);
					if(maxValue < tr_cnt) maxValue = tr_cnt;
					
					var user_cnt = parseInt(result.data[i].USER_CNT, 10);
					user.push(user_cnt);
					if(maxValue < user_cnt) maxValue = user_cnt;
				}
			}
			app.ldin.progress.chart('myChart2', labels, '월', tr, user, call, maxValue);
		});
	},
	chart:function(id, labels, tooltipsTitle, tr, user, call, maxValue) {
		if(app.ldin.progress['chart_' + id]) 
			app.ldin.progress['chart_' + id].destroy();
		
		app.ldin.progress['chart_' + id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: labels,
		        datasets: [{
						data: tr,
						label: "거래",
						borderColor: "#e345ff",
						borderWidth:"2",
						pointBackgroundColor: "#e345ff",
						pointStyle:'circle', 
						fill: false,
						lineTension:0
			    	}, { 
						data: user,
						label: "이용자",
						borderColor: "#15c5ff",
						borderWidth:"2",
						pointBackgroundColor: "#15c5ff",
						pointStyle:'rectRot', 
						fill: false,
						lineTension:0
			    	}, { 
						data: call,
						label: "콜",
						borderColor: "#fd8a19",
						borderWidth:"2",
						pointBackgroundColor: "#fd8a19",
						pointStyle:'triangle', 
						fill: false,
						lineTension:0
			    	}
			    ]
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
	                		return parseInt(tooltipItem[0].xLabel) + ' ' + tooltipsTitle;
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
							display: false,
							labelString: tooltipsTitle,
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
							display: false,
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
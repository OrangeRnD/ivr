app.ldin.status = {
	index:function(url) {
		var $inpth = $('#inpth_list');
		var $list = $('#ldin_status').children().filter('.callNum');
		$list.on('mouseover', function(e) {
			$list.addClass('deselected');
			this.className = this.getAttribute('data-class');
			
			$inpth[0].className = $inpth.attr('data-class');
			$inpth.addClass(this.getAttribute('data-type'));
		});
		
		var $prcs_dt = $('#prcs_dt');
		app.util.printDate2($prcs_dt);
		
		this.search = function() {
			var dt = $('#btn_dt1').html().replace(/(\.|\-|\/)/g,'');
			document.search_frm.dt.value = dt;
			app.request.get('/ldin/status/list', 'dt=' + dt, function(result) {
				if(result.status == 200 && result.data.length > 0) {
					for(var i = 0; i < result.data.length; i++) {
						if(result.data[i].INPTH_SECD == 'C') {
							$('#CALL_CNT').html(app.util.formatNumber(result.data[i].CALL_CNT) + '<span>콜</span>');
							$('#TR_CNT').html(app.util.formatNumber(result.data[i].TR_CNT) + '<span>건</span>');
							$('#USER_CNT').html(app.util.formatNumber(result.data[i].USER_CNT) + '<span>명</span>');
							$('#RLTM_INQIRE_CNT').html(app.util.formatNumber(result.data[i].RLTM_INQIRE_CNT) + '<span>건</span>');
							$('#RLTM_TRANSFR_CNT').html(app.util.formatNumber(result.data[i].RLTM_TRANSFR_CNT) + '<span>건</span>');
						} else if(result.data[i].INPTH_SECD == 'P') {
							$('#PRE_CALL_CNT').html(app.util.formatNumber(result.data[i].CALL_CNT) + '<span>콜</span>');
							$('#PRE_TR_CNT').html(app.util.formatNumber(result.data[i].TR_CNT) + '<span>건</span>');
							$('#PRE_USER_CNT').html(app.util.formatNumber(result.data[i].USER_CNT) + '<span>명</span>');
							$('#PRE_RLTM_INQIRE_CNT').html(app.util.formatNumber(result.data[i].RLTM_INQIRE_CNT) + '<span>건</span>');
							$('#PRE_RLTM_TRANSFR_CNT').html(app.util.formatNumber(result.data[i].RLTM_TRANSFR_CNT) + '<span>건</span>');
						} else {
							$('#INPT_CALL_' + result.data[i].INPTH_SECD).html(app.util.formatNumber(result.data[i].CALL_CNT) + '<span>콜</span>');
							$('#INPT_TR_' + result.data[i].INPTH_SECD).html(app.util.formatNumber(result.data[i].TR_CNT) + '<span>건</span>');
							$('#INPT_USER_' + result.data[i].INPTH_SECD).html(app.util.formatNumber(result.data[i].USER_CNT) + '<span>명</span>');
						}
					}
				}

				app.util.printDate2($prcs_dt);
			});
			app.ldin.status.tr();
		};
		
		var $s_dt1 = $('#s_dt1');
		var $s_dt2 = $('#s_dt2');
		var $s_dt3 = $('#s_dt3');
		app.form.datepicker($s_dt1);
		app.form.datepicker($s_dt2);
		app.form.datepicker($s_dt3);
		var $btn_dt1 = $('#btn_dt1');
		var $btn_dt2 = $('#btn_dt2');
		var $btn_dt3 = $('#btn_dt3');
		var $btn_dt4 = $('#btn_dt4');
		var $btn_dt5 = $('#btn_dt5');
		
		var stat = function(dt) {
			$btn_dt1.html(dt);
			$btn_dt2.html(dt);
			$btn_dt3.html(dt);
			$btn_dt4.html(dt);
			$btn_dt5.html(dt);
			
			dt = dt.replace(/(\.|\-|\/)/g,'');
			document.search_frm.dt.value = dt;
			app.request.get('/ldin/status/stat', 'dt=' + dt, function(result) {
				if(result.status != 200) return;
				
				if(result.data) {
					$('#PRE_CALL_CNT').html(app.util.formatNumber(result.data.CALL_CNT) + '<span>콜</span>');
					$('#PRE_TR_CNT').html(app.util.formatNumber(result.data.TR_CNT) + '<span>건</span>');
					$('#PRE_USER_CNT').html(app.util.formatNumber(result.data.USER_CNT) + '<span>명</span>');
					$('#PRE_RLTM_INQIRE_CNT').html(app.util.formatNumber(result.data.RLTM_INQIRE_CNT) + '<span>건</span>');
					$('#PRE_RLTM_TRANSFR_CNT').html(app.util.formatNumber(result.data.RLTM_TRANSFR_CNT) + '<span>건</span>');
				} else {
					$('#PRE_CALL_CNT').html('- <span>콜</span>');
					$('#PRE_TR_CNT').html('- <span>건</span>');
					$('#PRE_USER_CNT').html('- <span>명</span>');
					$('#PRE_RLTM_INQIRE_CNT').html('- <span>건</span>');
					$('#PRE_RLTM_TRANSFR_CNT').html('- <span>건</span>');
				}
			});
		};
		
		$s_dt1.on('change', function(e) {
			stat(this.value);
		});
		$s_dt2.on('change', function(e) {
			stat(this.value);
		});
		$s_dt3.on('change', function(e) {
			stat(this.value);
		});
		
		$btn_dt1.on('click', function(e) {
			$s_dt1.trigger('focus');
		});
		$btn_dt2.on('click', function(e) {
			$s_dt2.trigger('focus');
		});
		$btn_dt3.on('click', function(e) {
			$s_dt3.trigger('focus');
		});
		
		$('#btn_aft_dt1, #btn_aft_dt2, #btn_aft_dt3, #btn_pre_dt1, #btn_pre_dt2, #btn_pre_dt3').on('click', function(e) {
			var value = this.getAttribute('data-value');
			var now = app.util.parseDate($btn_dt1.html());
			now.setDate(now.getDate()+parseInt(value, 10));
			
			var newValue = app.util.formatDate(now, '-');
			$s_dt1.datepicker('update', newValue);
			$s_dt2.datepicker('update', newValue);
			$s_dt3.datepicker('update', newValue);
			
			stat(newValue);
		});
		
		app.ldin.status.tr();
	},
	tr:function() {
		app.request.json('/ldin/status/tr', '', function(result) {
			var labels = [];
			var datas = [];
			var maxValue = 0;
			if(result.status == 200 && result.data.length > 0) {
				var dt = '';
				for(var i = 0; i < result.data.length; i++) {
					if(!dt) {
						labels.push(result.data[i].DT.substring(6) + '일 ' + result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
						dt = result.data[i].DT;
					} else {
						if(dt == result.data[i].DT)
							labels.push(result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
						else {
							labels.push(result.data[i].DT.substring(6) + '일 ' + result.data[i].HM.substring(0, 2) + ':' + result.data[i].HM.substring(2));
							dt = result.data[i].DT;
						}
					}
					var cnt = parseInt(result.data[i].CNT, 10);
					datas.push(cnt);
					if(maxValue < cnt) maxValue = cnt;
				}
				if(labels.length > 0) labels[labels.length-1] = '현재 ' + labels[labels.length-1];
			}
			app.ldin.status.chart('myChart', labels, datas, maxValue);
		});
	},
	chart:function(id, labels, datas, maxValue) {
		if(app.ldin.status['chart_' + id]) 
			app.ldin.status['chart_' + id].destroy();
		
		app.ldin.status['chart_' + id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: labels,
		        datasets: [{
		            data: datas,
		            backgroundColor: ['rgba(21, 122, 210, 0.3)'],
		            borderColor:  "#157ad2",
		            pointBorderColor: "#157ad2",
		            pointBackgroundColor: "#157ad2",
		            borderWidth: 2,
					fill: true,
					lineTension:0.5
		        }]
		    },
		    // Configuration options go here
		    options: {
				responsive: true,
				maintainAspectRatio: false,
				legend: {
					display: false
				},
				tooltips:{
					enabled:true,
					titleSpacing:10,
					callbacks: {
		                label: function(tooltipItem, data) {
		                    return ' ' + app.util.formatNumber(tooltipItem.yLabel) + ' 건';
		                },
		                title:function(tooltipItem, data) {
		                	var value = tooltipItem[0].xLabel;
		                	var day = '', hour, min;
		                	if(value.length == 5) {
		                		hour = parseInt(value.substring(0, 2), 10);
		                		min = parseInt(value.substring(3), 10);
		                	} else if(value.length == 8) {
		                		day = '현제 시간 ';
		                		hour = parseInt(value.substring(3, 5), 10);
		                		min = parseInt(value.substring(6), 10);
		                	} else {
		                		day = value.substring(0, 3);
		                		hour = parseInt(value.substring(4, 6), 10);
		                		min = parseInt(value.substring(7), 10);
		                	}
	                		return day + ' ' + hour + ' 시 ' + (min == 0 ? '' : min + ' 분');
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
							labelString: '거래건수',
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
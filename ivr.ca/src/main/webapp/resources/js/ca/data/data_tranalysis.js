app.data.tranalysis = {
	index:function(url) {
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
		
		var $frm = $(frm);
		var $target = $('#lists');
		this.search = function(url) {
			app.form.unformat(frm);
			app.request.page('/data/tranalysis/list', $frm.serialize(), function(result) {
				$target.children().remove();
				$target.html(result);
				app.form.format(frm);
				window.setTimeout(function() {
					var sd;
					try {
						sd = parseFloat($('#result_sd').attr('data-sd'));
						$('#avg_sd').html(Math.round(sd*10, 2)/10);
					} catch(e) {
						sd = 0;
					}
					
					var labels = ['일 변동량'];
					var datas = [];
					var sdData = [];
					for(var i = 0; i < 24; i++) {
						sdData.push(parseInt(sd*10, 10)/10);
					}
					datas.push(sdData);
					
					var maxValue = 0;
					var trs = $target[0].children;
					for(var r = 1; r < trs.length; r++) {
						var tds = trs[r].children;
						labels.push(tds[0].innerHTML);
						
						var data = [];
						for(var c = 2; c < tds.length; c++) {
							var value = parseFloat(tds[c].innerHTML);
							data.push(parseInt(value*10, 10)/10);
							if(value > maxValue) maxValue = value;
						}
						datas.push(data);
					}
					
					app.data.tranalysis.chart('myChart', labels, datas, maxValue);
				}, 100);
			});
		};
		this.search();
	},
	color:[
		'#000000'
		,"#ff6384", "#e867bf", "#ac6cf6", "#5d7bf7", "#34a2eb", "#2dbfef", "#3cba9f", "#6cbd30", "#dbd40f", "#f7a33f"
		
		,'#F0F8FF'
		,'#FAEBD7'
		,'#00FFFF'
		,'#7FFFD4'
		,'#F0FFFF'
		,'#F5F5DC'
		,'#FFE4C4'
		,'#808000'
		,'#AFEEEE'
		,'#DB7093'
		,'#CD853F'
		,'#FFC0CB'
		,'#DDA0DD'
		,'#A52A2A'
		,'#DEB887'
		,'#5F9EA0'
		,'#7FFF00'
		,'#D2691E'
		,'#FF7F50'
		,'#FFF8DC'
		,'#00FFFF'
		,'#00008B'
		,'#008B8B'
		,'#B8860B'
		,'#A9A9A9'
		,'#006400'
		,'#BDB76B'
		,'#556B2F'
		,'#E9967A'
		,'#8FBC8F'
		,'#483D8B'
		,'#FF1493'
		,'#FF00FF'
		,'#FFD700'
		,'#DAA520'
		,'#ADFF2F'
		,'#FF69B4'
		,'#F0E68C'
		,'#E6E6FA'
		,'#FFF0F5'
		,'#FFFACD'
		,'#ADD8E6'
		,'#F08080'
		,'#FAFAD2'
		,'#FFB6C1'
		,'#FAF0E6'
		,'#66CDAA'
		,'#00FA9A'
		,'#C71585'
		,'#FFE4E1'
	],
	chart:function(id, labels, datas, maxValue) {
		if(app.chart[id]) 
			app.chart[id].destroy();
		
		var datasets = [];
		for(var i = 0, c = 0; i < labels.length; i++,c++) {
			if(c >= labels.length) c = 0;
			var dataset = {
					data: datas[i],
					label: labels[i],
					borderColor: app.data.tranalysis.color[c],
					borderWidth:"2",
					pointBackgroundColor: app.data.tranalysis.color[c],
					pointStyle:'circle', 
					fill: false,
					lineTension:0
		    };
			datasets.push(dataset);
		}
		
		app.chart[id] = new Chart(document.getElementById(id), {
		    type: 'line',
		    data: {
		        labels: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
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
		                    return label + app.util.formatNumber(tooltipItem.yLabel);
		                },
		                title:function(tooltipItem, data) {
		                	return tooltipItem[0].xLabel + '시';
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
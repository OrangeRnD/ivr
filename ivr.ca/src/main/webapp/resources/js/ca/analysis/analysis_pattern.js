app.analysis.pattern = {
	index:function(url) {
		var $frm = $(document.search_frm);
		var $stdValue = $($frm[0]['param[STD_VALUE]']);
		
		if($frm[0].getAttribute('data-session-type')) {
			var type = $frm[0].getAttribute('data-session-type');
			if(type == '0') {
				$frm[0]['param[FROM_DT]'].value = app.util.formatDate($frm[0]['param[FROM_DT]'].value, '-');
				$frm[0]['param[TO_DT]'].value = app.util.formatDate($frm[0]['param[TO_DT]'].value, '-');
				document.getElementById('std_input').checked = true;
			} else {
				for(var i = 0; i < $frm[0]['param[STANDARD]'].length; i++) {
					if($frm[0]['param[STANDARD]'][i].value == type) {
						app.util.period($($frm[0]['param[STANDARD]'][i]), $frm[0]['param[STANDARD]'][i].getAttribute('data-date-type'));
						break;
					}
				}
			}
			
			if($frm[0]['param[STD_VALUE]'].value != '5' && $frm[0]['param[STD_VALUE]'].value != '10') 
				$stdValue.show().next().show();
		} else {
			app.util.period($('#lately_week'), 'lately_week');
		}
		
		$('#STD_VALUE_5').on('click', function(e) {
			$stdValue.val(this.value).hide().next().hide();
		});
		$('#STD_VALUE_10').on('click', function(e) {
			$stdValue.val(this.value).hide().next().hide();
		});
		$('#STD_VALUE').on('click', function(e) {
			$stdValue.val('0').show().next().show();
		});
		
		var $policy = $('#policy').find('input');
		$policy.on('click', function(e) {
			var $this = $policy.filter(this);
			if(this.type == 'radio') {
				$this.parent().parent().find('input[type="text"]').prop('disabled', true);
				$this.parent().next().find('input[type="text"]').prop('disabled', false);
			} else if(this.type == 'checkbox'){
				if(this.checked) {
					$this.parent().parent().find('input[type="radio"]').prop('disabled', false);
					$this.parent().parent().find('input[type="radio"]').filter(function(index) {
						return this.checked;
					}).trigger('click');
				} else {
					$this.parent().parent().find('input').prop('disabled', true);
				}
				this.disabled = false;
			}
		});
		if($frm[0].getAttribute('data-session-type')) {
			$policy.filter('input[type="text"]').each(function() {
				if(!this.value) {
					this.disabled = true;
				} else {
					$(this).parent().prev().children().prop('checked', true);
				}
			});
		} else {
			$policy.filter('input[type="text"]').prop('disabled', true).filter('._checked_').prop('disabled', false);
			$policy.filter('input[type="radio"]').prop('disabled', true).prop('disabled', false);
		}
		
		$('#btn_std').on('click', function(e) {
			setStdValue();
		});
		
		function setStdValue(){
			if($stdValue.val()) {
				var param = 'param[STD_VALUE]=' + $frm[0]['param[STD_VALUE]'].value.replace(/,/g,'') + '&param[FROM_DT]=' + $frm[0]['param[FROM_DT]'].value.replace(/(\.|\-|\/)/g,'') + '&param[TO_DT]=' + $frm[0]['param[TO_DT]'].value.replace(/(\.|\-|\/)/g,'');
				app.request.get('/analysis/pattern/value', param, function(result) {
					if(result.status == 200 && result.data.length > 0) {
						for(var i = 0; i < result.data.length; i++) {
							var cnt = result.data[i].CNT;
							if(!cnt) cnt = 0;
							
							document.getElementById('TYPE_' + result.data[i].TYPE).value = cnt;
							if(result.data[i].TYPE%2 == 0) {
								if(cnt == 0)
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = 0;
								else
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = cnt-1;
							} else
								if(cnt == 0)
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = 0;
								else
									document.getElementById('TYPE_' + result.data[i].TYPE + '_1').value = cnt+1;
						}
						
						for(var i = 1; i < 8; i++) {
							if(i%2 == 1) {
								var type1 = document.getElementById('TYPE_' + i);
								var type2 = document.getElementById('TYPE_' + (i+1));
								if(parseInt(type1.value, 10) == 0 && parseInt(type2.value, 10) > 1){
									document.getElementById('TYPE_' + i + '_1').value = parseInt(type1.value, 10) + 1;
								}
							}
						}
					} else {
						alert('해당 기간에 데이터가 존재하지 않습니다.');
					}
				});
			}
		}
		
		var $srvc_list = $('#srvc_list');
		this.search = function(url) {
			app.request.submit('/analysis/pattern/user', $frm, function(result) {
				var totalCnt = result.data ? result.data : 0;
				$('#user_cnt').html(app.util.formatNumber(totalCnt) + '<span>명</span>')
			
				app.request.submit('/analysis/pattern/list', $frm, function(result2) {
					var userLabels = [], userDatas = [], ageLabels = [], ageDatas = [], ptnCnt = 0;
					if(result2.status == 200 && result2.data.length > 0) {
						for(var i = 0; i < result2.data.length; i++) {
							if(result2.data[i].TYPE == 1) {
								userLabels.push(result2.data[i].NM);
								userDatas.push(result2.data[i].CNT);
								ptnCnt += result2.data[i].CNT;
							} else if(result2.data[i].TYPE == 2) {
								ageLabels.push(result2.data[i].NM);
								ageDatas.push(result2.data[i].CNT);
							}
						}
						
						$('#ptn_cnt').html(app.util.formatNumber(ptnCnt) + '<span>명</span>')
						if(totalCnt > 0)
							$('#ptn_rt').html('[ ' + app.util.formatNumber(Math.round(ptnCnt/totalCnt*1000)/10) + ' %]');
						else
							$('#ptn_rt').html('-');
						
						var r = 0;
						var rows = $srvc_list.children();
						var srvcTotCnt = 0;
						for(var i = 0; i < result2.data.length; i++) {
							if(result2.data[i].TYPE == 3) {
								srvcTotCnt += result2.data[i].CNT;
							}
						}
						for(var i = 0; i < result2.data.length && r < 5; i++) {
							if(result2.data[i].TYPE == 3) {
								var tr = rows[r++];
								tr.children[0].innerHTML = result2.data[i].NM;
								if(srvcTotCnt > 0) {
									tr.children[1].innerHTML = app.util.formatNumber(Math.round(result2.data[i].CNT/srvcTotCnt*1000)/10) + ' %';
									tr.children[1].title = app.util.formatNumber(result2.data[i].CNT) + '건';
								} else {
									tr.children[1].innerHTML = '-';
									tr.children[1].title = '';
								}
							}
						}
						for(;r < 5; r++) {
							var tr = rows[r];
							tr.children[0].innerHTML = '-';
							tr.children[1].innerHTML = '-';
							tr.children[1].title = '';
						}
					}
					app.analysis.pattern.pie('userChart', userLabels, userDatas);
					app.analysis.pattern.pie('ageChart', ageLabels, ageDatas);
				});
			});
		};
	//	기준값 설정
		setStdValue();
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
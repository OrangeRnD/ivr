app.main = {
	index:function(url) {
		$('#btn_url').on('click', function(e) {
			var param = 'dt=' + app.util.formatDate(new Date(), '');
			window.location.href = app.constants.webappRoot + '/analysis/error?' + param;
		});
		
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
			app.main.stat();
			app.main.plcy();
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
		
		app.main.stat();
		app.main.plcy();
	},
	plcy:function() {
		var $list = $('#plcy_list');
		app.request.page('/main/plcyrslt', '', function(result) {
			$list.children().remove();
			$list.html(result);
		});
	},
	stat:function() {
		app.request.json('/main/stat', '', function(result) {
			var arsLabels = [], arsDatas = [], errLabels = [], errDatas = [];
			if(result.status == 200 && result.data.ars && result.data.ars.length > 0) {
				var pIdx = 0, etcCnt = 0;
				for(var i = 0; i < result.data.ars.length; i++) {
					console.log(result.data.ars[i]);
					if(result.data.ars[i].CD == 'P') {
						arsLabels.push('ARS');
						pIdx = i;
					} else {
						arsLabels.push(result.data.ars[i].NM);
						if(result.data.ars[i].TOT_CNT) etcCnt += result.data.ars[i].TOT_CNT;
					}
					arsDatas.push(result.data.ars[i].TOT_CNT);
				}
				if(arsDatas.length > 0) arsDatas[pIdx] = arsDatas[pIdx] - etcCnt;
			}
			if(result.status == 200 && result.data.err) {
				errLabels.push('거래');
				errDatas.push(result.data.err.TR_CNT);

				errLabels.push('에러');
				errDatas.push(result.data.err.ERR_CNT);
			}
			app.main.pie('arsChart', arsLabels, arsDatas, ' 콜');
			app.main.pie('errChart', errLabels, errDatas, ' 건');
		});
	},
	pie:function(id, labels, datas, tooltip) {
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
					backgroundColor:["#34a2eb", "#ff6384", "#3cba9f"]
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
				            return app.util.formatNumber(cnt) + tooltip;
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
	}
};
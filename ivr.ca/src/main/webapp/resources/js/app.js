var app = {};

app.constants = {
	webappRoot:'',	
	debug:0,
	date_format:'-',
	special_keyCode:[8, 9, 13, 16, 17, 18, 19, 20, 27, 32, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46, 145, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123],
};

app.util = {
	error:function error(jqXHR, textStatus, errorThrown, $messageBox) {
		if(jqXHR.status == 401) {
			alert('인증이 만료되었습니다.');
			window.location.href = app.constants.webappRoot + '/';
			return 401;
		}
		if(jqXHR.status == 404) {
			alert('시스템 장애가 발생하였습니다. 잠시 후에 다시 시도하여 주십시요.');
			return '404'
		} else {
			try {
				var result = $.parseJSON(jqXHR.responseText);
				if(result['status']) {
					alert(result['message'] + '(' + result['status'] + ')');
					return result['status'];
				} else {
					alert((jqXHR.responseText ? jqXHR.responseText : textStatus) + '(' + jqXHR.status + ')');
					return jqXHR.status;
				}
			} catch(e){
				alert(textStatus);
				return jqXHR.status;
			}
		}
	},
	loadJS:function(url) {
		url || (url = window.location.pathname);
		
		try {if(app.constants.debug) console.log(url);} catch(e) {}

		if(url == '/') {
			var obj = app['index'];
			if(obj && obj instanceof Function) obj.call(obj, url);
			return {url:url, js:obj};
		}

		try {if(app.constants.debug) console.log(url);} catch(e) {}
		
		var splited = url.split('/');
		try {if(app.constants.debug) console.log(splited);} catch(e) {}
		
		var i = 0;
		for(;i < splited.length; i++) {
			if(splited[i]) 
				break;
		}

		var obj = app[splited[i++]];
		if(!obj) return {url:url, splited:splited};

		for(var l = splited.length; i < l; i++) {
			if(!splited[i]) continue;
			if(obj) obj = obj[splited[i]];
		}

		if(url.lastIndexOf('/') == url.length-1) 
			if(obj) obj = obj['index'];

		try {if(app.constants.debug) console.log(obj);} catch(e) {}
		if(obj) {
			if(obj instanceof Function) obj.call(obj, url);
			else if(obj instanceof Object) {
				obj = obj['index'];
				if(obj && obj instanceof Function) obj.call(obj, url);
			}
		}
		return {url:url, splited:splited, js:obj};
	},
	call:function(source, attributeName, datas) {
		var name = source instanceof jQuery ? source.attr(attributeName) : source.getAttribute(attributeName);
		if(name) {
			var func;
			if(datas && datas.js && datas.js[name])
				func = datas.js[name];
			else
				func = eval(name);
			
			if(func && func instanceof Function) {
				if(source instanceof jQuery) {
					var evnt = source.attr(attributeName + '-event');
					if(evnt) source.on(evnt, func);
					else return func.call(source[0], datas, attributeName);
				} else {
					var evnt = source.getAttribute(attributeName + '-event');
					if(evnt) $(source).on(evnt, func);
					else return func.call(source, datas, attributeName);
				}
			}
		}
		return false;
	},
	formatNumber:function(value) {
		if(!value || isNaN(parseFloat(value))) return value;
		
		if(typeof value == 'number') value = value + '';
		
		var reg = /(^[+-]?\d+)(\d{3})/;
	    while (reg.test(value)) value = value.replace(reg, '$1' + ',' + '$2');
	    return value;
	},
	formatDate:function(value, format, monthFormat, dayFormat) {
		if(!value) return value;
		if(format != '')
			format || (format = app.constants.date_format);
		
		if(value instanceof Date) {
			var m = value.getMonth()+1;
			var d = value.getDate();
			return value.getFullYear() + format + ((m < 10) ? '0'+m : m) + (monthFormat || format) + ((d < 10) ? '0'+d : d) + (dayFormat || '');
		}
		if(typeof value == 'number') value = value + '';
		else value = value.replace(/(\.|\-|\/)/g,'');
		
		if(value.length == 8) {
			var reg = /(\d{4})(\d{2})(\d{2})/;
			if (reg.test(value)) value = value.replace(reg, '$1' + format + '$2' + (monthFormat || format) + '$3' + (dayFormat || ''));
		    return value;
		} else if(value.length == 6) {
			var reg = /(\d{4})(\d{2})/;
			if (reg.test(value)) value = value.replace(reg, '$1' + format + '$2' + (monthFormat || ''));
		    return value;
		}
		return value;
	},
	formatTimestamp:function(value, format, hasSecond) {
		if(!value) return value;
		if(format != '')
			format || (format = app.constants.date_format);
		if(!value instanceof Date) return value;
		
		var m = value.getMonth()+1;
		var d = value.getDate();
		
		var h = value.getHours();
		var min = value.getMinutes();
		
		if(!hasSecond)
			return value.getFullYear() + format + ((m < 10) ? '0'+m : m) + format + ((d < 10) ? '0'+d : d) + ' ' + ((h < 10) ? '0'+h : h) + ':' + ((min < 10) ? '0'+min : min);
		else {
			var s = value.getSeconds();
			return value.getFullYear() + format + ((m < 10) ? '0'+m : m) + format + ((d < 10) ? '0'+d : d) + ' ' + ((h < 10) ? '0'+h : h) + ':' + ((min < 10) ? '0'+min : min) + ':' + ((s < 10) ? '0'+s : s);
		}
	},
	formatTel:function(value) {
		if(!value || isNaN(parseFloat(value))) return value;
		value = value.replace(/(\-)/g,'');
		
		var reg;
		if(value.indexOf('02') == 0) {
			if(value.length == 10)
				reg = /(\d{2})(\d{4})(\d{4})/;
			else if(value.length == 9)
				reg = /(\d{2})(\d{3})(\d{4})/;
		} else {
			if(value.length == 11)
				reg = /(\d{3})(\d{4})(\d{4})/;
			else if(value.length == 10)
				reg = /(\d{3})(\d{3})(\d{4})/;
			else if(value.length == 9)
				reg = /(\d{2})(\d{3})(\d{4})/;
		}
		if(reg) return value.replace(reg, '$1' + '-' + '$2' + '-' + '$3');
	    return value;
	},
	formatBizno:function(value) {
		if(!value || isNaN(parseFloat(value))) return value;
		value = value.replace(/(\-)/g,'');
		if(value.length == 10)
			return value.replace(/(\d{3})(\d{2})(\d{5})/, '$1' + '-' + '$2' + '-' + '$3');
		return value;
	},
	parseDate:function(value) {
		if(!value) throw 'value is null';
		value = value.replace(/(\.|\-|\/)/g,'');
		
		return new Date(parseInt(value.substring(0,4), 10), parseInt(value.substring(4,6))-1, parseInt(value.substring(6,8)));
	},
	day:['일', '월', '화', '수', '목', '금', '토'],
	getDay:function(value) {
		var date = app.util.parseDate(value);
		return app.util.day[date.getDay()];
	},
	period:function($el, type) {
		if(!$el.prop('checked')) $el.prop('checked', true);
		var tgt1 = $el.attr('data-date1');
		var tgt2 = $el.attr('data-date2');
		var frm = $el[0].form;
		var now = new Date();
		if(type == 'now') {
			/*당일*/
			frm[tgt1].value = app.util.formatDate(now);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'pre_date') {
			/*전일*/
			now.setDate(now.getDate() - 1);
			frm[tgt1].value = app.util.formatDate(now);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'this_week') {
			/*금주*/
			var day = now.getDay();
			var date = now.getDate();
			now.setDate(date - day);
			frm[tgt1].value = app.util.formatDate(now);
			now.setDate(now.getDate() + 6);
			if(date <= now.getDate())
				now.setDate(date);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'pre_week') {
			/*전주*/
			var day = now.getDay();
			now.setDate(now.getDate() - day - 1);
			frm[tgt2].value = app.util.formatDate(now);
			now.setDate(now.getDate() - 6);
			frm[tgt1].value = app.util.formatDate(now);
		} else if(type == 'this_month') {
			/*당월*/
			now.setDate(1);
			frm[tgt1].value = app.util.formatDate(now);
			now.setMonth(now.getMonth() + 1);
			now.setDate(now.getDate() - 1);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'pre_month') {
			/*전월*/
			now.setDate(0);
			frm[tgt2].value = app.util.formatDate(now);
			now.setDate(1);
			frm[tgt1].value = app.util.formatDate(now);
		} else if(type == 'pre_month2') {
			/*전전월*/
			now.setMonth(now.getMonth() - 2);
			now.setDate(1);
			frm[tgt1].value = app.util.formatDate(now);
			now.setMonth(now.getMonth() + 1);
			now.setDate(0);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'pre_quarter') {
			var month = now.getMonth();
			if(month <= 2) {
				now.setFullYear(now.getFullYear() -1);
				now.setMonth(9);
			} else if(month >= 3 && month <= 5) {
				now.setMonth(0);
			} else if(month >= 9) {
				now.setMonth(6);
			} else {
				now.setMonth(3);
			}
			now.setDate(1);
			frm[tgt1].value = app.util.formatDate(now);
			now.setMonth(now.getMonth() + 3);
			now.setDate(0);
			frm[tgt2].value = app.util.formatDate(now);
		} else if(type == 'lately_week') {
			/*최근 1주*/
			frm[tgt2].value = app.util.formatDate(now);
			now.setDate(now.getDate() - 6);
			frm[tgt1].value = app.util.formatDate(now);
		} else if(type == 'lately_month') {
			/*최근 1개월*/
			frm[tgt2].value = app.util.formatDate(now);
			now.setMonth(now.getMonth() - 1);
			frm[tgt1].value = app.util.formatDate(now);
		} else if(type == 'lately_3month') {
			/*최근 3개월*/
			frm[tgt2].value = app.util.formatDate(now);
			now.setMonth(now.getMonth() - 3);
			frm[tgt1].value = app.util.formatDate(now);
		}
	},
	has:function has(array, value) {
		if(!array || !value) return false;
		for(var i in array) 
			if(array[i] == value) return true;
		
		return false;
	},
	chart:function(id, name) {
		var canvas = document.getElementById(id);
		if (canvas.msToBlob) { // IE
	        blob = canvas.msToBlob();
	        window.navigator.msSaveBlob(blob, name + '.png');
	    } else { // CH, FF
	    	var canvasImage = canvas.toDataURL('image/png');
	        var xhr = new XMLHttpRequest();
	        xhr.responseType = 'blob';
	        xhr.onload = function () {
	            var a = document.createElement('a');
	            a.href = window.URL.createObjectURL(xhr.response);
	            a.download = name + '.png';
	            a.style.display = 'none';
	            document.body.appendChild(a);
	            a.click();
	            a.remove()
	        };
	        xhr.open('GET', canvasImage);
	        xhr.send();
	    }
	},
	printDate1:function($target, time) {
		var now = new Date();
		if(time) now.setTime(time);
		$target.html(now.getFullYear() + '<span>년</span> ' + (now.getMonth()+1) + '<span>월</span> ' + (now.getDate()) + '<span>일</span> ' + now.getHours() + '<span>시</span> ' + (now.getMinutes()) + '<span>분 기준</span>');
	},
	printDate2:function($target, time) {
		var now = new Date();
		if(time) now.setTime(time);
		$target.html(now.getFullYear() + '<span>년</span> ' + (now.getMonth()+1) + '<span>월</span> ' + (now.getDate()) + '<span>일</span> ' + now.getHours() + '<span>시</span> ' + (now.getMinutes()) + '<span>분</span>' + (now.getSeconds()) + '<span>초 기준</span>');
	},
	sort:function($table) {
		var $sorting = $table.find('.sorting');
		$sorting.on('click', function(e) {
			var $sort = $(this),
            idx = $sort.index(),
            inverse = true;
			if($sort.hasClass('sortingDown')) 
				inverse = false;
			
			if($sort.attr('data-sort')) {
				if($sort.attr('data-sort') == 'sortingDown')
					inverse = false;
			}
			
			var type = $sort.attr('data-type');
			if(!type) type = 'int';

			if($sort.attr('data-idx')) idx = parseInt($sort.attr('data-idx'), 10);
			
			if(!idx && this.nodeName == 'DIV') 
				idx = $sort.parent().index();
			
			if(type == 'int') {
				$table.find('td').filter(function() {
					var $td = $(this);
	                return $td.index() === idx && !$td.parent().hasClass('total');
	            }).sortElements(function(a, b){
	            	var value1 = parseInt($.text([a]).replace(/(\,|\s|\%|\건|\명)/g,''), 10);
	            	var value2 = parseInt($.text([b]).replace(/(\,|\s|\%|\건|\명)/g,''), 10);
	                return value1 > value2 ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			} else if(type == 'date') {
				$table.find('td').filter(function() {
	                return $(this).index() === idx;
	            }).sortElements(function(a, b){
	            	var value1 = parseInt($.text([a]).replace(/(\.|\-|\/)/g,''), 10);
	            	var value2 = parseInt($.text([b]).replace(/(\.|\-|\/)/g,''), 10);
	                return value1 > value2 ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			} else {
				$table.find('td').filter(function() {
	                return $(this).index() === idx;
	            }).sortElements(function(a, b){
	                return $.text([a]) > $.text([b]) ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			}
			
            inverse = !inverse;

            $sorting.removeClass('sortingDown').removeClass('sortingUp');
            
            if(inverse) $sort.addClass('sortingUp');
            else $sort.addClass('sortingDown');
            if($sort.attr('data-sort')) {
            	if(inverse) $sort.attr('data-sort', 'sortingUp');
                else $sort.attr('data-sort', 'sortingDown');
			}
		});
	},
	sortForScrollable:function($header, $table) {
		var $sorting = $header.find('.sorting2');
		$sorting.on('click', function(e) {
			var $sort = $(this),
            idx = $sort.index(),
            inverse = true;
			if($sort.hasClass('sorting2Down')) 
				inverse = false;
			
			if($sort.attr('data-sort')) {
				if($sort.attr('data-sort') == 'sorting2Down')
					inverse = false;
			}
			
			var type = $sort.attr('data-type');
			if(!type) type = 'int';

			if($sort.attr('data-idx')) idx = parseInt($sort.attr('data-idx'), 10);
			
			if(!idx && this.nodeName == 'DIV') 
				idx = $sort.parent().index();
			
			if(type == 'int') {
				$table.find('td').filter(function() {
					var $td = $(this);
	                return $td.index() === idx && !$td.parent().hasClass('total');
	            }).sortElements(function(a, b){
	            	var value1 = parseInt($.text([a]).replace(/(\,|\s|\%|\건|\명)/g,''), 10);
	            	var value2 = parseInt($.text([b]).replace(/(\,|\s|\%|\건|\명)/g,''), 10);
	                return value1 > value2 ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			} else if(type == 'date') {
				$table.find('td').filter(function() {
	                return $(this).index() === idx;
	            }).sortElements(function(a, b){
	            	var value1 = parseInt($.text([a]).replace(/(\.|\-|\/)/g,''), 10);
	            	var value2 = parseInt($.text([b]).replace(/(\.|\-|\/)/g,''), 10);
	                return value1 > value2 ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			} else {
				$table.find('td').filter(function() {
	                return $(this).index() === idx;
	            }).sortElements(function(a, b){
	                return $.text([a]) > $.text([b]) ? inverse ? -1 : 1 : inverse ? 1 : -1;
	            }, function(){
	                return this.parentNode; 
	            });
			}
			
            inverse = !inverse;

            $sorting.removeClass('sorting2Down').removeClass('sorting2Up');
            
            if(inverse) $sort.addClass('sorting2Up');
            else $sort.addClass('sorting2Down');
            if($sort.attr('data-sort')) {
            	if(inverse) $sort.attr('data-sort', 'sorting2Up');
                else $sort.attr('data-sort', 'sorting2Down');
			}
		});
	},
	byteLenth:function(str) {
		var len = 0;
	    for (var i = 0; i < str.length; i++) {
	        if (escape(str.charAt(i)).length == 6) 
	            len++;
	        len++;
	    }
	    return len;
	},
	substringByte:function(str, limit) {
		var newStr = [];
	    for (var i = 0, len = 0; i < str.length; i++) {
	        if (escape(str.charAt(i)).length == 6) 
	            len++;
	        len++;
	        if(len > limit)
	        	break;
	        newStr.push(str.charAt(i));
	    }
	    return newStr.join('');
	}
};

app.request = {
	json:function(url, params, callback) {
		try {if(app.constants.debug) console.log(url);}catch(e) {}
		$.ajax({
			type: 'post',
			contentType: 'application/json; charset=utf-8',
			data:params instanceof Object ? JSON.stringify(params) : params,
			cache:false,
			url:app.constants.webappRoot+url,
			dataType: 'json',
			beforeSend:function() {
				$('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			try {if(app.constants.debug) console.log(data);}catch(e) {}
			if(callback) {
				if(callback.done) 
					callback.done.call(this, data, textStatus, jqXHR);
				else {
					if(callback instanceof Function) callback.call(this, data, textStatus, jqXHR);
				}
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(callback && callback.fail) callback.fail.call(this, jqXHR, textStatus, errorThrown);
			if(callback && callback.error) callback.error.call(this, jqXHR, textStatus, errorThrown);
			else app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			$('#loading').hide();
			if(callback && callback.always) callback.always.call(this, data, textStatus, jqXHR);
		});
	},
	get:function(url, params, callback, noLoading) {
		try {if(app.constants.debug) console.log(url);}catch(e) {}
		$.ajax({
			type: 'post',
			data:params,
			cache:false,
			url:app.constants.webappRoot+url,
			dataType: 'json',
			beforeSend:function() {
				if(!noLoading) $('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			try {if(app.constants.debug) console.log(data);}catch(e) {}
			if(callback) {
				if(callback.done) 
					callback.done.call(this, data, textStatus, jqXHR);
				else {
					if(callback instanceof Function) callback.call(this, data, textStatus, jqXHR);
				}
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(callback && callback.fail) callback.fail.call(this, jqXHR, textStatus, errorThrown);
			if(callback && callback.error) callback.error.call(this, jqXHR, textStatus, errorThrown);
			else app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			if(!noLoading) $('#loading').hide();
			if(callback && callback.always) callback.always.call(this, data, textStatus, jqXHR);
		});
	},
	post:function(url, data, callback) {
		try {if(app.constants.debug) console.log(url);} catch(e) {}
		$.ajax({
			type:'post',
            contentType: 'application/json; charset=utf-8',
			data:data instanceof Object ? JSON.stringify(data) : data,
			url:app.constants.webappRoot+url,
			beforeSend:function() {
				$('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			alert('정상적으로 처리되었습니다.');
			if(callback) {
				if(callback.done) 
					callback.done.call(this, data, textStatus, jqXHR);
				else {
					if(callback instanceof Function) callback.call(this, data, textStatus, jqXHR);
				}
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(callback && callback.fail) callback.fail.call(this, jqXHR, textStatus, errorThrown);
			if(callback && callback.error) callback.error.call(this, jqXHR, textStatus, errorThrown);
			else app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			$('#loading').hide();
			if(callback && callback.always) callback.always.call(this, data, textStatus, jqXHR);
		});
	},
	submit:function(url, $frm, callback) {
		if(!($frm instanceof jQuery)) $frm = $($frm);
		app.form.unformat($frm[0]);
		
		$.ajax({
			type:'post',
			data:$frm.serialize(),
			url:app.constants.webappRoot + url,
			beforeSend:function() {
				$('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			if(callback) {
				if(callback.done) 
					callback.done.call(this, data, textStatus, jqXHR);
				else {
					if(callback instanceof Function) callback.call(this, data, textStatus, jqXHR);
				}
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(callback && callback.fail) callback.fail.call(this, jqXHR, textStatus, errorThrown);
			if(callback && callback.error) callback.error.call(this, jqXHR, textStatus, errorThrown);
			else app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			app.form.format($frm[0]);
			$('#loading').hide();
			if(callback && callback.always) callback.always.call(this, data, textStatus, jqXHR);
		});
	},
	page:function(url, params, callback) {
		try {if(app.constants.debug) console.log(url);}catch(e) {}
		$.ajax({
			type: 'post',
			data:params,
			cache:false,
			url:app.constants.webappRoot+url,
			beforeSend:function() {
				$('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			if(callback) {
				if(callback.done) 
					callback.done.call(this, data, textStatus, jqXHR);
				else {
					if(callback instanceof Function) callback.call(this, data, textStatus, jqXHR);
				}
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(callback && callback.fail) callback.fail.call(this, jqXHR, textStatus, errorThrown);
			if(callback && callback.error) callback.error.call(this, jqXHR, textStatus, errorThrown);
			else app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			$('#loading').hide();
			if(callback && callback.always) callback.always.call(this, data, textStatus, jqXHR);
		});
	},
	excel:function($btn, frm) {
		if(!$btn.attr('data-url')) return;
		
		frm || (frm = document.search_frm);
		if(!frm) return;

		$('#loading').show();
		if(!window.Blob) {
			app.form.unformat(frm);
			
			frm.setAttribute('action', $btn.attr('data-url'));
			frm.setAttribute('method', 'post');
			
			frm.target = 'temp';
			frm.submit();
			frm.removeAttribute('action');
			frm.removeAttribute('method');

			app.form.format(frm)
			$('#loading').hide();
			return;
		}
		var xhr= null;
		if (window.XMLHttpRequest) 
			xhr = new XMLHttpRequest();
		 else {
			 try {
				 xhr = new ActiveXObject('Msxml2.XMLHTTP');
			 } catch(e) {
				 xhr = new ActiveXObject('Microsoft.XMLHTTP');
			 }
		}
	    xhr.open('post', $btn.attr('data-url'), true);
	    xhr.responseType = 'blob';
	    xhr.setRequestHeader('Cache-Control', 'no-cache');
	    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhr.onreadystatechange= function() {
		        if (this.readyState!==4) return;
		        if (this.status!==200) {
		        	try {
						var blb = new Blob([this.response], {type: "text/plain"});
						var reader = new FileReader();
						reader.onload = function(e) {
						    var result = $.parseJSON(e.target.result);
						    object.alert.show(result['message'] + '(' + result['status'] + ')');
						};
						reader.readAsText(blb);
		        	} catch(e) {
		        		alert(object.message.fail+'('+this.status+')');
		        	}
		        } else {
			        var filename = decodeURIComponent(xhr.getResponseHeader('Content-Disposition'));
			        filename = filename.substring(filename.indexOf('=')+1, filename.length-1);
			        var blob = new Blob([this.response], {type: xhr.getResponseHeader('Content-Type')});
			        if(window.navigator.msSaveOrOpenBlob) {
			        	window.navigator.msSaveOrOpenBlob(blob, filename);
			        } else {
			        	var a = document.createElement('a');
				        a.style = 'display:none';
				        a.download = filename;
				        frm.appendChild(a);
				        var url = window.URL.createObjectURL(blob);
				        a.href = url;
				        a.click();
				        window.URL.revokeObjectURL(url);
				        window.setTimeout(function() {
				        	frm.removeChild(a);
				        }, 1000);
			        }
		        }
				app.form.format(frm)
				$('#loading').hide();
		};
		app.form.unformat(frm)
		xhr.send($(frm).serialize());
	}
};

app.validation = {
	init:function($frm) {
		if(!($frm instanceof jQuery)) $frm = $($frm);
		
		var els = $frm[0].getElementsByTagName('input');
		for(var i=0; i<els.length; i++) {
			var el = els[i];
			if(el.required && !el.disabled) {
				if(!el.value) {
					alert(el.getAttribute('title') + ' 항목은 필수 항목입니다.');
					el.focus();
					return false;
				}
			}
			var type = el.getAttribute('data-type');
			if(type && el.value) {
				if(type == 'date') {
					el.value = el.value.replace(/(\.|\-|\/)/g,'');
					if(!/^[\-\+]?\d+$/.test(el.value) || el.value.length != 8) {
						alert(el.getAttribute('title') + ' 항목은 날짜 형식에 맞지 않습니다.');
						el.focus();
						return false;
					}
				} else if(type == 'int') {
					if(!/^[\-\+]?\d+$/.test(el.value)) {
						alert(el.getAttribute('title') + ' 숫자가 아닌 값이 존재합니다.');
						el.focus();
						return false;
					}
				}
			}
		}
		
		els = $frm[0].getElementsByTagName('textarea');
	    for (var i=0; i<els.length; i++){
	        var el = els[i];
	        if(el.required) {
				if(!el.value) {
					alert(el.getAttribute('title') + ' 항목은 필수 항목입니다.');
					el.focus();
					return false;
				}
			}
	    }
	    return true;
	}	
};

app.form = {
	/**
	 * default keydown event
	 */
	keydown:function(e) {
		var code = e.keyCode || e.charCode;
		
		var stop = function(result) {
			e.stopImmediatePropagation();
			return result;
		};
		/*space(32), 한글(229)**/
	    if(code == 32 || code == 229) return stop(false);
		if(code == 16) {
			this.setAttribute('data-shift', code);
			return stop(true);
		} else if(code == 17) {
			this.setAttribute('data-ctrl', code);
			return stop(true);
		}
		if(app.util.has(app.constants.special_keyCode, code)) return stop(true);
		if(this.getAttribute('data-ctrl')) return stop(true);
		return true;
	},
	/**
	 * default keyup event
	 */
	keyup:function(e) {
		var code = e.keyCode || e.charCode;
		
		var stop = function(result) {
			e.stopImmediatePropagation();
			return result;
		};
		if(code == 16) {
			this.removeAttribute('data-shift');
			return stop(true);
		} else if(code == 17) {
			this.removeAttribute('data-ctrl');
			return stop(true);
		}
		if(code != 38 && code != 40 && code != 8 && code != 46 && app.util.has(app.constants.special_keyCode, code)) return stop(true);
		
		return true;
	},
	dateCheck:function($el, target, b) {
		if(document.search_frm[target].value) {
			var value = $el.val().replace(/-/g,'');
			if(value && value.length == 8) {
				if(b) {
					if(parseInt(document.search_frm[target].value.replace(/-/g,''), 10) > parseInt(value, 10))
						$el.datepicker('update', document.search_frm[target].value);
				} else {
					if(parseInt(document.search_frm[target].value.replace(/-/g,''), 10) < parseInt(value, 10))
						$el.datepicker('update', document.search_frm[target].value);
				}
			}
		}
	},
	datepicker:function($el) {
		if($el.val())
			$el.val(app.util.formatDate($el.val()));
		
    	$el.datepicker({
	        format: 'yyyy-mm-dd',
	        autoclose: true,
	        todayHighlight: true,
	        language: 'ko',
	        maxViewMode: 4,
	        minViewMode: 'days',
	        orientation: 'bottom',
	        setDate: $el.val() ? $el.val() : app.util.formatDate(new Date())
	    }).on('changeDate', function(e) {
	    	if(this.name == 'param[TO_DT]') {
				app.form.dateCheck($el, 'param[FROM_DT]', true);
			} else if(this.name == 'param[TO_DT1]') {
				app.form.dateCheck($el, 'param[FROM_DT1]', true);
			} else if(this.name == 'param[TO_DT2]') {
				app.form.dateCheck($el, 'param[FROM_DT2]', true);
			} else if(this.name == 'param[FROM_DT]') {
				app.form.dateCheck($el, 'param[TO_DT]', false);
			} else if(this.name == 'param[FROM_DT1]') {
				app.form.dateCheck($el, 'param[TO_DT1]', false);
			} else if(this.name == 'param[FROM_DT2]') {
				app.form.dateCheck($el, 'param[TO_DT2]', false);
			} 
	    }).on('clearDate', function() {
	    	if(this.name == 'param[TO_DT]') {
	    		$el.datepicker('update', document.search_frm['param[FROM_DT]'].value);
			} else if(this.name == 'param[TO_DT1]') {
	    		$el.datepicker('update', document.search_frm['param[FROM_DT1]'].value);
			} else if(this.name == 'param[TO_DT2]') {
	    		$el.datepicker('update', document.search_frm['param[FROM_DT2]'].value);
			} else if(this.name == 'param[FROM_DT]') {
	    		$el.datepicker('update', document.search_frm['param[TO_DT]'].value);
			} else if(this.name == 'param[FROM_DT1]') {
	    		$el.datepicker('update', document.search_frm['param[TO_DT1]'].value);
			} else if(this.name == 'param[FROM_DT2]') {
	    		$el.datepicker('update', document.search_frm['param[TO_DT2]'].value);
			} 
	    });
		$el.prop('maxlength', 10);
    	
    	$el.on('keydown', app.form.keydown);
		$el.on('keydown', function(e) {
			if(this.getAttribute('data-shift')) return false;
			
			var code = e.keyCode || e.charCode;
			if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 189) return true;
			return false;
		});
		$el.on('keyup', app.form.keyup);
		$el.on('keyup', function(e) {
			var code = e.keyCode || e.charCode;
			if(code != 8) {
				var value = this.value.replace(/-/g,'');
				if(value.length == 4)
					this.value = value + '-';
				else if(value.length == 5)
					this.value = value.substring(0,4) + '-' + value.substring(4);
				else if(value.length == 6)
					this.value = value.substring(0,4) + '-' + value.substring(4) + '-';
			}
		});
	},
	int:function($el, frm, js) {
		$el.css('text-align', 'right');
		$el.on('keydown', app.form.keydown);
		$el.on('keydown', function(e) {
			if(this.getAttribute('data-shift')) return false;
			
			var code = e.keyCode || e.charCode;
			if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || (code == 189 && !this.value)) return true;
			return false;
		});
		$el.on('keyup', app.form.keyup);
		$el.on('keyup', function(e) {
			var value = this.value.replace(/,/g,'');
			var code = e.keyCode || e.charCode;
			if(code == 38) 
				this.value = parseInt(value == '' ? 0 : value, 10) + 1;
			else if(code == 40)
				this.value = parseInt(value == '' ? 0 : value, 10) - 1;
		});
		$el.on('blur', function(e) {
			this.value = parseFloat(this.value.replace(/,/g,''));
			if(isNaN(this.value)) this.value = '';
			else this.value = app.util.formatNumber(this.value);
		});
		$el.on('focus', function(e) {
			this.value = this.value.replace(/,/g,'');
		});
	},
	float:function($el, frm, js) {
		$el.css('text-align', 'right');
		$el.on('keydown', app.form.keydown);
		$el.on('keydown', function(e) {
			if(this.getAttribute('data-shift')) return false;
			
			var code = e.keyCode || e.charCode;
			if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || (code == 190 && this.value) || (code == 110)) return true;
			return false;
		});
		$el.on('keyup', app.form.keyup);
		$el.on('keyup', function(e) {
			var value = this.value.replace(/,/g,'');
			var code = e.keyCode || e.charCode;
			if(code == 38) 
				this.value = parseInt(value == '' ? 0 : value, 10) + 1;
			else if(code == 40)
				this.value = parseInt(value == '' ? 0 : value, 10) - 1;
		});
		$el.on('blur', function(e) {
			this.value = parseFloat(this.value.replace(/,/g,''));
			if(isNaN(this.value)) this.value = '';
			else this.value = app.util.formatNumber(this.value);
		});
		$el.on('focus', function(e) {
			this.value = this.value.replace(/,/g,'');
		});
	},
	tel:function($el, frm, js) {
		$el.on('keydown', app.form.keydown);
		$el.on('keydown', function(e) {
			if(this.getAttribute('data-shift')) return false;
			
			var code = e.keyCode || e.charCode;
			if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 189) return true;
			return false;
		});
		$el.on('keyup', app.form.keyup);
		$el.on('blur', function(e) {
			this.value = this.value.replace(/-/g,'');
			if(isNaN(this.value)) this.value = '';
			else this.value = app.util.formatTel(this.value);
		});
	},
	bizno:function($el, frm, js) {
		$el.on('keydown', app.form.keydown);
		$el.on('keydown', function(e) {
			if(this.getAttribute('data-shift')) return false;
			
			var code = e.keyCode || e.charCode;
			if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 189) return true;
			return false;
		});
		$el.on('keyup', app.form.keyup);
		$el.on('blur', function(e) {
			this.value = this.value.replace(/-/g,'');
			if(isNaN(this.value)) this.value = '';
			else this.value = app.util.formatBizno(this.value);
		});
	},
	eng:function($el, frm, js) {
		$el.css('ime-mode', 'disabled');
		$el.on('keydown', function(e){
			var code = e.keyCode || e.charCode;
			if(code == 229) /*한글*/
				return false
		});
	},
	kor:function($el, frm, js) {
		$el.css('ime-mode', 'active');
	},
	init:function(frm, isPopup) {
		if(frm.getAttribute('data-type')) {
			for(var j = 0; j < frm.elements.length; j++) {
				app.element.init(frm.elements[j], frm.getAttribute('data-type'), isPopup);
			}
		}
	},
	unformat:function(frm) {
		for(var j = 0; j < frm.elements.length; j++) {
			app.element.unformat(frm.elements[j]);
		}
	},
	format:function(frm) {
		for(var j = 0; j < frm.elements.length; j++) {
			app.element.format(frm.elements[j]);
		}
	}
};

app.element = {
	init:function(el, formType, isPopup) {
		var type = el.getAttribute('data-type');
		var $el;
		if(type) {
			$el = $(el);
			if(type == 'date') {
				if(el.type == 'text') {
					app.form.datepicker($el);
					if($el.attr('data-input')) {
						$el.on('change', function(e) {
							document.getElementById($el.attr('data-input')).checked = true;
						});
					}
					var $btn = $el.next();
					if($btn.attr('data-type') && $btn.attr('data-type') == 'calendar') {
						$btn.on('click', function(e) {
							$el.trigger('focus');
						});
					}
				} else if(el.type == 'radio') {
					var search = el.getAttribute('data-search');
					var date_type = el.getAttribute('data-date-type');
					if(date_type) {
						$el.on('click', function() {
							app.util.period($el, date_type);
							
							if(search && search == 'true') 
								$(!isPopup ? '#btn_search' : '#' + el.form.getAttribute('data-btn-search')).trigger('click');
						});
					} else {
						$el.on('click', function() {
							if(search && search == 'true') 
								$(!isPopup ? '#btn_search' : '#' + el.form.getAttribute('data-btn-search')).trigger('click');
						});
					}
				}
			} else if(type == 'int') {
				app.form.int($el, el.form);
				if(formType == 'search') app.element.search($el, isPopup);
			} else if(type == 'float') {
				app.form.float($el, el.form);
				if(formType == 'search') app.element.search($el, isPopup);
			} else if(type == 'tel') {
				app.form.tel($el, el.form);
				if(formType == 'search') app.element.search($el, isPopup);
			} else if(type == 'bizno') {
				app.form.bizno($el, el.form);
				if(formType == 'search') app.element.search($el, isPopup);
			} else if(type == 'eng') {
				app.form.eng($el, el.form);
			} else if(type == 'kor') {
				app.form.kor($el, el.form);
			}
		} else {
			if(formType == 'search') {
				if(el.type == 'text') {
					var search = el.getAttribute('data-search');
					if(!search || (search && search == 'false')) {
						$el || ($el = $(el));
						app.element.search($el, isPopup);
					}
				} else if(el.type == 'radio') {
					var search = el.getAttribute('data-search');
					if(search && search == 'true') {
						$el || ($el = $(el));
						$el.on('click', function(e) {
							$(!isPopup ? '#btn_search' : '#' + el.form.getAttribute('data-btn-search')).trigger('click');
						});
					}
				}
			}
		}
	}, 
	search:function($el, isPopup) {
		$el.on('keydown', function(e) {
			var code = e.keyCode || e.charCode;
			if(code == 13) 
				$(!isPopup ? '#btn_search' : '#' + this.form.getAttribute('data-btn-search')).trigger('click');
		});
	},
	unformat:function(el) {
		var type = el.getAttribute('data-type');
		if(type) {
			if(type == 'date') {
				if(!el.getAttribute('data-value'))
					el.setAttribute('data-value', el.value);
				el.value = el.value.replace(/(\.|\-|\/)/g,'');
			} else if(type == 'int') {
				if(!el.getAttribute('data-value'))
					el.setAttribute('data-value', el.value);
				el.value = el.value.replace(/,/g,'');
			} else if(type == 'formated') {
				if(!el.getAttribute('data-value'))
					el.setAttribute('data-value', el.value);
				el.value = el.value.replace(/(\,|\.|\-|\/)/g,'');
			} else if(type == 'tel' || type == 'bizno') {
				if(!el.getAttribute('data-value'))
					el.setAttribute('data-value', el.value);
				el.value = el.value.replace(/(\-)/g,'');
			}
		}
	},
	format:function(el) {
		var origin = el.getAttribute('data-value');
		if(origin) 
			el.value = origin;
		el.removeAttribute('data-value');
	}
};

app.dialog = {
	div:'',
	overlay:'',
	show:function(url, params, width) {
		$.ajax({
			type:'post',
			data:(params || ''),
			url:app.constants.webappRoot + url,
			beforeSend:function() {
				$('#loading').show();
			}
		}).done(function(data, textStatus, jqXHR) {
			$('#content_pupup').html(data).show();

			if(!app.dialog.div) app.dialog.div = $('#content_pupup');
			if(!app.dialog.overlay) {
				var $overlay = $(document.createElement('div'));
				$overlay.addClass('overlay');
				document.body.appendChild($overlay[0]);
				app.dialog.overlay = $overlay;
				app.dialog.overlay.on('click', function() {
					app.dialog.close();
				});
			}
			var $window = $(window);
			app.dialog.overlay.fadeIn();
			app.dialog.div.css('width', width).html(data);
			app.dialog.div.css('left', $window.scrollLeft() + Math.round(Math.max($window.width() - app.dialog.div.width(), 0) / 2));
			app.dialog.div.css('top', $window.scrollTop() + (((window.innerHeight ? window.innerHeight : $window.height()) - app.dialog.div.height()) / 2));
			app.dialog.div.fadeIn(400, function() {
				app.dialog.overlay.height(Math.max($window.height()-1, $(document.body).height(), document.body.scrollHeight));
			});

			var result = app.util.loadJS(url);
			app.edit.index((document.edit_frm2 || document.view_frm2), url, params, result.js);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			app.util.error(jqXHR, textStatus, errorThrown);
		}).always(function(data, textStatus, jqXHR) {
			$('#loading').hide();
		});
	},
	close:function() {
		app.dialog.div.fadeOut();
		app.dialog.overlay.fadeOut();
	}
}

app.init = function() {
	var result = app.util.loadJS();
	app.menu.init(result.splited);
	app.common.init(result.url, result.js);
	app.page.index(result.url, result.js);
};

app.menu = {
	init:function(splited) {
		var animationSpeed = 300;
		
		$('#sidebar-menu').find('a').on('click', function(e) {
			var $this = $(this);
		    var checkElement = $this.next();
		    if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
		        checkElement.slideUp(animationSpeed, function() {
		        	checkElement.removeClass('menu-open');
		        });
		        checkElement.parent("li").removeClass("active");
		    } else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
		        var parent = $this.parents('ul').first();
		        var ul = parent.find('ul:visible').slideUp(animationSpeed);
		        ul.removeClass('menu-open');
		        var parent_li = $this.parent("li");

		        checkElement.slideDown(animationSpeed, function() {
		        	checkElement.addClass('menu-open');
		        	parent.find('li.active').removeClass('active');
		        	parent_li.addClass('active');
		        });
		    }
		    if (checkElement.is('.treeview-menu')) {
		    	e.preventDefault();
		    }
		});
		var $mn1 = $('#mn_' + splited[1]);
		$mn1.next().addClass('menu-open').parent().addClass('active');
		var $mn2;
		if(splited.length == 3) 
			$mn2 = $('#mn_' + splited[1] + '_' + splited[2]);
		else if(splited.length == 4)
			$mn2 = $('#mn_' + splited[1] + '_' + splited[2] + '_' + splited[3]);
		
		if($mn2) $mn2.addClass('active');

		$('#location').html('<span>홈</span><span>' + $mn1.text() + '</span>' + ($mn2 && $mn2.length > 0 ? '<span>' + $mn2.text() + '</span>' : ''));
	}	
};

app.common = {
	init:function(url, js) {
		var $body = $(document.body);
		$('#btn_menu').on('click', function(e) {
			if($body.hasClass('wide'))
				$body.removeClass('wide');
			else
				$body.addClass('wide');
		});
		$('#logout').on('click', function() {
			app.request.get('/lgn/logout', '', function(e) {
				window.location.href = app.constants.webappRoot + '/login';
			});
		});
		
		$('#ico_logo').on('click', function() {
			window.location.href = app.constants.webappRoot + '/';
		});
		
		var $reload = $('#select_reload');
		if($reload.length > 0) {
			var timer;
			var reload = function(time) {
				if(timer) window.clearTimeout(timer);
				
				var now = new Date();
				now.setDate(now.getDate() + 30);
				document.cookie = 'reload=' + time + '; path=' + url + '; expires=' + now.toUTCString() + ';';
				
				if(!time) return;

				timer = window.setTimeout(function() {
					$('#btn_search').trigger('click', ["reload"]);
					reload(time);
				}, parseInt(time, 10)*1000);
			};
			$reload.on('change', function(e) {
				if(this.value) $('#btn_search').trigger('click', ["reload"]);
				reload(this.value);
			});
			
			var reloadValue = document.cookie.match('(^|;) ?reload=([^;]*)(;|$)');
			try {
				if(reloadValue && reloadValue.length > 2) {
					var value = reloadValue[2];
					$reload.val(value);
					reload(value);
				} else reload($reload.val());
			} catch(e) {
				reload($reload.val());
			}
		}
	}
};

app.page = {
	index:function(url, js) {
		this.search(url, js);
		this.print(url, js);
		this.excel(url, js);
		this.form(url, js);
	},
	search:function(url, js) {
		if(js && js.search && js.search instanceof Function) {
			$('#btn_search').on('click', function(e) {
				js.search.call(js);
			});
		} else {
			if(document.search_frm && document.search_frm.getAttribute('data-type') == 'search') {
				var frm = document.search_frm;
				var targetUrl = frm.getAttribute('data-url');
				if(!targetUrl) return;
				
				var target = document.getElementById(frm.getAttribute('data-target'));
				if(!target) return;
				
				var $target = $(target);
				var $frm = $(frm);
				var isAppend = $frm.attr('data-append');
				var $btn = $('#btn_search');
				$btn.on('click', function(e) {
					if(js.search_befor && js.search_befor instanceof Function) js.search_befor.call(js);
					app.form.unformat(frm);
					app.request.page(targetUrl, $frm.serialize(), function(result) {
						if(frm.pageNo) frm.pageNo.value = 1;
						if(js.search_result && js.search_result instanceof Function) {
							js.search_result.call(js, result);
						} else {
							if(isAppend == 'true')
								$target.append(result);
							else {
								$target.children().remove();
								$target.html(result);
							}
						}
						if(frm.pageNo) {
							if(!frm.pageNo.value)
								frm.pageNo.value = 1;
							
							$('#paging').find('a').on('click', function(e) {
								frm.pageNo.value = parseInt(this.getAttribute('data-no'), 10);
								$btn.trigger('click');
							});
						}
						app.form.format(frm);
						if(js.search_after && js.search_after instanceof Function) js.search_after.call(js, result);
					});
				});
				if(frm.pageNo && !frm.pageNo.value) frm.pageNo.value = 1;
				
				if($frm.attr('data-search') && $frm.attr('data-search') == 'true') $btn.trigger('click');
			}
		}
	},
	print:function(url, js) {
		var btn = document.getElementById('btn_print hide');
		if(!btn) return;
		
		if(js && js.print && js.print instanceof Function) {
			$(btn).on('click', function(e) {
				js.print.call(js);
			});
		} else {
			$(btn).on('click', function(e) {
				window.print();
			});
		}
	},
	excel:function(url, js) {
		var btn = document.getElementById('btn_excel hide');
		if(!btn) return;
		
		if(js && js.excel && js.excel instanceof Function) {
			$(btn).on('click', function(e) {
				js.excel.call(js);
			});
		} else {
			var $btn = $(btn);
			$btn.on('click', function(e) {
				app.request.excel($btn);
			});
		}
	},
	form:function(url, js) {
		var forms = document.forms;
		for(var i = 0; i < forms.length; i++) {
			app.form.init(forms[i]);
		}
	}
};

app.chart = {};

app.ldin = {};
app.use = {};
app.analysis = {};
app.data = {};
app.policy = {};
app.system = {};

(function($, window, document) {
	$(function() {
		if(window.location.hostname.indexOf('127.0.0.1') != -1 || window.location.hostname.indexOf('localhost') != -1) app.constants.debug = 1;
		app.init();
	});
}(window.jQuery, window, document));
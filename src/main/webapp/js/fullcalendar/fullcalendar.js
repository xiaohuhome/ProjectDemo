$(function(){
	$("#calendar").fullCalendar({
		theme: true,
		customButtons:{
			button1:{
				text:"新建",
				click:function(){
					$(".datepicker").datepicker({
						language:"zh-CN",
						format:"yyyy-MM-dd",
						todayHighlight:true,
						autoclose:true,
						weekStart:0
					});
					$(".timepicki").wickedpicker({
						title:'',
						showSeconds:true,
						twentyFour:true
					});
					$("#isallday").click(function(){
						if($("#isallday").prop("checked") == true){
							$("#isallday").val("1");
							$("#starttime,#endtime").hide();
						}else{
							$("#isallday").val("0");
							$("#starttime,#endtime").show();
						};	
					});
					$("#end").click(function(){
						if($("#end").prop("checked") == true){
							$("#enddate").show();
						}else{
							$("#enddate").hide();
						};
					});
					$("#repeat").click(function(){
						if($("#repeat").prop("checked") == true){
							$("#repeattype,#repeattime").show();
						}else{
							$("#repeattype,#repeattime").hide();
						};
					});
					$("#repeatselect").change(function(){
						switch($("#repeatselect").val()){
							case "1":
								$("#repeatclock").show();
								$("#repeatmonth,#repeatweek,#repeatday").hide();
								break;
							case "2":
								$("#repeatmonth,#repeatday").hide();
								$("#repeatweek,#repeatclock").show();
								break;
							case "3":
								$("#repeatmonth,#repeatweek").hide();
								$("#repeatday,#repeatclock").show();
								break;
							case "4":
								$("#repeatweek").hide();
								$("#repeatmonth,#repeatday,#repeatclock").show();
								break;
							case "5":
								$("#repeatclock").show();
								$("#repeatmonth,#repeatweek,#repeatday").hide();
								break;
						}
					});
					dialog({
						title:"新建日程",
						content:$("#dialog-form"),
						okValue:"确定",
						ok:function(){
							var titledetail = $("#titledetail").val();
							var startdate = $("#startdate").val();
							var starttime = $("#starttime").val().split(" ").join("");
							var enddate = $("#stopdate").val();
							var endtime = $("#endtime").val().split(" ").join("");
							var allDay = $("#isallday").val();
							if(titledetail){
								$.ajax({
									url:$WEB_ROOT_PATH + '/fullcalendar/buildSchedule',
			   						data:{
			   							'title':titledetail,
			   							'sdate':startdate,
			   							'stime':starttime,
			   							'edate':enddate,
			   							'etime':endtime,
			   							'color':'#255',
			   							'allDay':true
			   						},
			   						type:'POST',
			   						dataType:'json',
			  						success:function(data){
			  							var jsonData = data.data;
			  							console.dir(jsonData[0]);
			  							$("#calendar").fullCalendar("renderEvent",jsonData[0],true);
			  						},
			  						error:function(){
			  							alert("buildSchedule error!!!");
			  						}
			   						
								});
							};
						},
						cancelValue:"关闭",
						cancel:function(){
							//$("#ui-datepicker-div").remove();
						}
					}).showModal();
				}
			},
			button2:{
				text:"查询",
				click:function(){
					$(".datepicker").datepicker({
						language:"zh-CN",
						format:"yyyy-MM-dd",
						todayHighlight:true,
						autoclose:true,
						weekStart:0
					});
					dialog({
						title:"查询",
						content:$("#search"),
						okValue:"查询",
						ok:function(){
							$("#ui-datepicker-div").remove();
							alert(123);
						},
						button:[{
							value:"打印"
						}],
						cancelValue:"返回",
						cancel:function(){
							$("#ui-datepicker-div").remove();
						}
					}).showModal();

				}
			},
			button3:{
				text:"设置",
				click:function(){
					$("#slider").slider({
						range:true,
						min:0,
						max:24,
						values:[$("#minTime").val(),$("#maxTime").val()],
						slide: function( event, ui ) {
			        		$( "#amount" ).val(ui.values[ 0 ] + ":00 - " + ui.values[ 1 ]+":00");
			        		
			      		}
					});
					$( "#amount" ).val($( "#slider" ).slider( "values", 0 ) +
  ":00 - " + $( "#slider" ).slider( "values", 1 )+":00");
					dialog({
						title:"设置时间段",
						content:$("#set"),
						okValue:"确定",
						ok:function(){
			      			install_ok();
						},
						cancelValue:"关闭",
						cancel:function(){}
					}).showModal();
				}
			}
		},
		header: {
			left: 'prev,next today button3',
			center: 'title',
			right: 'button1 button2 month,agendaWeek,agendaDay,listMonth'
		},
		firstDay: 1,
		monthNames: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
		monthNamesShort: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
		dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
		dayNamesShort:["日","一","二","三","四","五","六"],
		buttonText:{
			today: "今天",
			month: "月",
			week: "周",
			day: "天",
			listMonth:"列表"
		},
		allDayDefault:false,
		slotLabelFormat:"H",
		businessHours: {
			dow:[1,2,3,4,5],
			start:"8:00",
			end:"17:00"
		},
		allDaySlot: true,
		allDayText: "全天",
		timeFormat: "HH:mm",//设置的是添加的具体的日程上显示的时间
		views:{
			month:{
				titleFormat:"YYYY年M月"
			},
			week:{
				titleFormat:"YYYY年M月D日",
				columnFormat:"M.D dddd"
			},
			day:{
				titleFormat:"YYYY年M月D日 dddd",
				columnFormat:"M/D dddd"
			}
		},
		dayClick: function(date,allDay,jsEvent,view){
			var selDate = $.fullCalendar.formatDate(date,"YYYY-MM-DD");
			var d = dialog({
				title:"新建事件",
				content:"<textarea rows=5 class='taxt' placeholder='内容' id='eventall'></textarea><p>"+selDate+"</p>",
				width:460,
				button:[{
					value:"完整编辑",
					callback:function(){
						$(".datepicker").datepicker({
							language:"zh-CN",
							format:"yyyy-MM-dd",
							todayHighlight:true,
							autoclose:true,
							weekStart:0
						});
						$(".timepicki").wickedpicker({
							title:'',
							showSeconds:true,
							twentyFour:true
						});
						$("#isallday").click(function(){
							if($("#isallday").prop("checked") == true){
								$("#isallday").val("1");
								$("#starttime,#endtime").hide();
							}else{
								$("#isallday").val("0");
								$("#starttime,#endtime").show();
							};	
						});
						$("#end").click(function(){
							if($("#end").prop("checked") == true){
								$("#enddate").show();
							}else{
								$("#enddate").hide();
							};
						});
						$("#repeat").click(function(){
							if($("#repeat").prop("checked") == true){
								$("#repeattype,#repeattime").show();
							}else{
								$("#repeattype,#repeattime").hide();
							};
						});
						$("#repeatselect").change(function(){
							switch($("#repeatselect").val()){
								case "1":
									$("#repeatclock").show();
									$("#repeatmonth,#repeatweek,#repeatday").hide();
									break;
								case "2":
									$("#repeatmonth,#repeatday").hide();
									$("#repeatweek,#repeatclock").show();
									break;
								case "3":
									$("#repeatmonth,#repeatweek").hide();
									$("#repeatday,#repeatclock").show();
									break;
								case "4":
									$("#repeatweek").hide();
									$("#repeatmonth,#repeatday,#repeatclock").show();
									break;
								case "5":
									$("#repeatclock").show();
									$("#repeatmonth,#repeatweek,#repeatday").hide();
									break;
							}
						});
						dialog({
							title:"新建日程",
							content:$("#dialog-form"),
							okValue:"确定",
							ok:function(){
								var titledetail = $("#titledetail").val();
								var startdate = $("#startdate").val();
								var starttime = $("#starttime").val().split(" ").join("");
								var enddate = $("#stopdate").val();
								var endtime = $("#endtime").val().split(" ").join("");
								var allDay = $("#isallday").val();
								if(titledetail){
									$.ajax({
										url:$WEB_ROOT_PATH + '/fullcalendar/buildSchedule',
				   						data:{
				   							'title':titledetail,
				   							'sdate':startdate,
				   							'stime':starttime,
				   							'edate':enddate,
				   							'etime':endtime,
				   							'color':'#255',
				   							'allDay':true
				   						},
				   						type:'POST',
				   						dataType:'json',
				  						success:function(data){
				  							var jsonData = data.data;
				  							console.dir(jsonData[0]);
				  							$("#calendar").fullCalendar("renderEvent",jsonData[0],true);
				  						},
				  						error:function(){
				  							alert("buildSchedule error!!!");
				  						}
				   						
									});
								};
							},
							cancelValue:"关闭",
							cancel:function(){}
						}).showModal();
					},	
				}],
				okValue:"确定",
				ok:function(){
					var titleall = $("#eventall").val();
					if(titleall){
						var url = $WEB_ROOT_PATH + "/fullcalendar/buildEvent";
						var params = {
							'title':titleall,
							'start':selDate,
							'color':'#360',
							'allDay':true
						}
						$.ajax({
							url:url,
	   						data:params,
	   						type:'POST',
	   						dataType:'json',
	  						success:function(data){
	  							var jsonData = data.data;
	  							console.dir(jsonData[0]);
	  							$("#calendar").fullCalendar("renderEvent",jsonData[0],true);
	  						},
	  						error:function(){
	  							alert("buildEvent error!!!");
	  						}
	   						
						});
					};
				},
				cancelValue:"取消",
				cancel:function(){}
			});
			d.showModal();
			
		},
		eventClick:function(event,jsEvent,view){
			var editstarttime = $.fullCalendar.formatDate(event.start,"YYYY-MM-DD HH:mm:ss");
			$("#edittitle").html(event.title);
			var eventtitle = event.title;
			if(event.end){
				var editendtime = $.fullCalendar.formatDate(event.end,"YYYY-MM-DD HH:mm:ss");
				$("#edittime").html(editstarttime+"  至  "+editendtime);
			}else{
				$("#edittime").html(editstarttime);
			};
			// var time = '19:00:00';
			dialog({
				title:"编辑日程",
				content:$("#edit"),
				okValue:"编辑",
				ok:function(){
					$(".datepicker").datepicker({
						language:"zh-CN",
						format:"yyyy-MM-dd",
						todayHighlight:true,
						autoclose:true,
						weekStart:0
					});
					$(".timepicki").wickedpicker({
						// now: time,
						title:'',
						showSeconds:true,
						twentyFour:true
					});
					$("#isallday").click(function(){
						if($("#isallday").prop("checked") == true){
							$("#isallday").val("1");
							$("#starttime,#endtime").hide();
						}else{
							$("#isallday").val("0");
							$("#starttime,#endtime").show();
						};	
					});
					$("#end").click(function(){
						if($("#end").prop("checked") == true){
							$("#enddate").show();
						}else{
							$("#enddate").hide();
						};
					});
					$("#repeat").click(function(){
						if($("#repeat").prop("checked") == true){
							$("#repeattype,#repeattime").show();
						}else{
							$("#repeattype,#repeattime").hide();
						};
					});
					$("#repeatselect").change(function(){
						switch($("#repeatselect").val()){
							case "1":
								$("#repeatclock").show();
								$("#repeatmonth,#repeatweek,#repeatday").hide();
								break;
							case "2":
								$("#repeatmonth,#repeatday").hide();
								$("#repeatweek,#repeatclock").show();
								break;
							case "3":
								$("#repeatmonth,#repeatweek").hide();
								$("#repeatday,#repeatclock").show();
								break;
							case "4":
								$("#repeatweek").hide();
								$("#repeatmonth,#repeatday,#repeatclock").show();
								break;
							case "5":
								$("#repeatclock").show();
								$("#repeatmonth,#repeatweek,#repeatday").hide();
								break;
						}
					});
					dialog({
						title:"新建日程",
						content:$("#dialog-form"),
						okValue:"确定",
						ok:function(){
							var titledetail = $("#titledetail").val();
							var startdate = $("#startdate").val();
							var starttime = $("#starttime").val().split(" ").join("");
							var enddate = $("#stopdate").val();
							var endtime = $("#endtime").val().split(" ").join("");
							var allDay = $("#isallday").val();
							if(titledetail){
								$.ajax({
									url:$WEB_ROOT_PATH + '/fullcalendar/buildSchedule',
			   						data:{
			   							'title':titledetail,
			   							'sdate':startdate,
			   							'stime':starttime,
			   							'edate':enddate,
			   							'etime':endtime,
			   							'color':'#255',
			   							'allDay':true
			   						},
			   						type:'POST',
			   						dataType:'json',
			  						success:function(data){
			  							var jsonData = data.data;
			  							console.dir(jsonData[0]);
			  							$("#calendar").fullCalendar("renderEvent",jsonData[0],true);
			  						},
			  						error:function(){
			  							alert("buildSchedule error!!!");
			  						}
			   						
								});
							};
						},
						cancelValue:"关闭",
						cancel:function(){
							//$("#ui-datepicker-div").remove();
						}
					}).showModal();
					/*$("#calendar").fullCalendar("removeEvents",function(event){
								if(event.title==eventtitle){
									return true;
								}else{
									return false;
								}
					});*/
				},
				button:[{
					value:"删除",
					callback:function(){
						$("#calendar").fullCalendar("removeEvents",function(event){
								if(event.title==eventtitle){
									return true;
								}else{
									return false;
								}
						});
					}
				}],
				cancelValue:"取消",
				cancel:function(){}
			}).showModal();
		}
	
	});
});
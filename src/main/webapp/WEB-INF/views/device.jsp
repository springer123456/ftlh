<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>蓝牙计数器设备测试</title>
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/0.4.3/weui.min.css">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
</head>
<body>

	<!--标题行-->
	<h2
		style="color: white; background-color: green; text-align: center; background-position: center;">蓝牙设备</h2>
	<div class="page">
		<div class="bd spacing">
			<div class="weui_cells weui_cells_form">

				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label" style="width: auto;">当前设备:&nbsp</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<label id="lbdeviceid" class="weui_label" style="width: auto;"></label>
					</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label" style="width: auto;">状态信息:&nbsp</label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<label id="lbInfo" class="weui_label" style="width: auto;"></label>
					</div>
				</div>
				<div class="weui_cell">
					<div class="weui_cell_hd">
						<label class="weui_label">日志: </label>
					</div>
					<div class="weui_cell_bd weui_cell_primary">
						<textarea id="logtext" class="weui_textarea" placeholder="日志"
							rows="5"></textarea>
					</div>
				</div>

			</div>

			<div class="weui_btn_area weui">

				<button class="weui_btn weui_btn_warn" id="CallGetWXrefresh">获取设备</button>
				<button class="weui_btn weui_btn_primary" id="icFuWei">电池电量</button>
				<button class="weui_btn weui_btn_primary" id="iccount">计数目标获取</button>
				<button class="weui_btn weui_btn_primary" id="setdaycount">计数目标设置</button>
		        <button class="weui_btn weui_btn_primary" id="isettime">设置设备时间</button>
				<button class="weui_btn weui_btn_primary" id="ictime">设备时间</button>
				<button class="weui_btn weui_btn_primary" id="iReset">重置设备</button>
			</div>

		</div>

		<div class="weui_dialog_alert" id="Mydialog" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd" id="dialogTitle">
				<strong class="weui_dialog_title">着急啦</strong>
				</div>
				<div class="weui_dialog_bd" id="dialogContent">亲,使用本功能,请先打开手机蓝牙！</div>
				<div class="weui_dialog_ft">
					<a href="#" class="weui_btn_dialog primary">确定</a>
				</div>
			</div>
		</div>


		<!--BEGIN toast-->
		<div id="toast" style="display: none;">
			<div class="weui_mask_transparent"></div>
			<div class="weui_toast">
				<i class="weui_icon_toast"></i>
				<p class="weui_toast_content" id="toast_msg">已完成</p>
			</div>
		</div>
		<!--end toast-->

		<!-- loading toast -->
		<div id="loadingToast" class="weui_loading_toast"
			style="display: none;">
			<div class="weui_mask_transparent"></div>
			<div class="weui_toast">
				<div class="weui_loading">
					<div class="weui_loading_leaf weui_loading_leaf_0"></div>
					<div class="weui_loading_leaf weui_loading_leaf_1"></div>
					<div class="weui_loading_leaf weui_loading_leaf_2"></div>
					<div class="weui_loading_leaf weui_loading_leaf_3"></div>
					<div class="weui_loading_leaf weui_loading_leaf_4"></div>
					<div class="weui_loading_leaf weui_loading_leaf_5"></div>
					<div class="weui_loading_leaf weui_loading_leaf_6"></div>
					<div class="weui_loading_leaf weui_loading_leaf_7"></div>
					<div class="weui_loading_leaf weui_loading_leaf_8"></div>
					<div class="weui_loading_leaf weui_loading_leaf_9"></div>
					<div class="weui_loading_leaf weui_loading_leaf_10"></div>
					<div class="weui_loading_leaf weui_loading_leaf_11"></div>
				</div>
				<p class="weui_toast_content" id="loading_toast_msg">数据加载中</p>
			</div>
		</div>
		<!-- End loading toast -->

		<!--BEGIN dialog1-->
		<div class="weui_dialog_confirm" id="dialog1" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd">
					<strong class="weui_dialog_title">弹窗标题</strong>
				</div>
				<div class="weui_dialog_bd">自定义弹窗内容，居左对齐显示，告知需要确认的信息等</div>
				<div class="weui_dialog_ft">
					<a href="javascript:;" class="weui_btn_dialog default" id="qxBtn">取消</a>
					<a href="javascript:;" class="weui_btn_dialog primary" id="okBtn">确定</a>
				</div>
			</div>
		</div>
		<!--END dialog1-->
		<!--BEGIN dialog2-->
		<div class="weui_dialog_alert" id="dialog2" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="weui_dialog_hd">
					<strong class="weui_dialog_title">弹窗标题</strong>
				</div>
				<div class="weui_dialog_bd">弹窗内容，告知当前页面信息等</div>
				<div class="weui_dialog_ft">
					<a href="javascript:;" class="weui_btn_dialog primary">确定</a>
				</div>
			</div>
		</div>
		<!--END dialog2-->
	</div>

	<div id="myparams" style="display: none">
		<span id="timestamp">${timestamp }</span> <span id="nonceStr">${nonceStr }</span>
		<span id="signature">${signature }</span> <span id="appId">${appid }</span>

	</div>

	<script type="text/javascript">
jQuery(document).ready(function(){
	  //初始化库  
	 loadXMLDoc();
	 //初始化库结束
	 //点击获取设备按钮的函数 开始
	 $("#CallGetWXrefresh").on("click",function(e){  
	    
	     //1. 打开微信设备 
	     my_openWXDeviceLib();
	     //2. 获取设备信息
	     my_getWXDeviceInfos();
	     //3. 接收设备数据
	     my_onReceiveDataFromWXDevice();
	 });
	 //点击获取设备按钮的函数 结束 
	  
	 /***
	   * 发送指令
	   */
	  $("#icFuWei").on("click",function(e){
	         alert(""+C_DEVICEID);
	         var Bytes=CheckBattery();
			 alert(""+Bytes);
	         var x=senddataBytes(Bytes,C_DEVICEID);
	           
	         if(x===0){$("#lbInfo").html('查询电量发送.完成')}
	         else {$("#lbInfo").html('电量.查询失败')};
	    });
	     
	 $("#iccount").on("click",function(e){
		         alert(""+C_DEVICEID);
		         var Bytes=checkcount();
				 alert(""+Bytes);
		         var x=senddataBytes(Bytes,C_DEVICEID);
		           
		         if(x===0){$("#lbInfo").html('发送查询计数.完成')}
		         else {$("#lbInfo").html('发送查询计数.查询失败')};
		    });
	 $("#iReset").on("click",function(e){
		         alert(""+C_DEVICEID);
		         var Bytes=ResetDevice();
				 alert(""+Bytes);
		         var x=senddataBytes(Bytes,C_DEVICEID);
		           
		         if(x===0){$("#lbInfo").html('Reset 数据发送.完成')}
		         else {$("#lbInfo").html('发送Reset.失败')};
		    });
	
	
	 $("#isettime").on("click",function(e){
		         alert(""+C_DEVICEID);
		         var Bytes=setDeviceTime();
				 alert(""+Bytes);
		         var x=senddataBytes(Bytes,C_DEVICEID);
		           
		         if(x===0){$("#lbInfo").html('设定设备时间获取发送.完成')}
		         else {$("#lbInfo").html('发送设定设备时间获取.失败')};
		    });
	 $("#ictime").on("click",function(e){
		         alert(""+C_DEVICEID);
		         var Bytes=checkDeviceTime();
				 alert(""+Bytes);
		         var x=senddataBytes(Bytes,C_DEVICEID);
		           
		         if(x===0){$("#lbInfo").html('设备时间获取发送.完成')}
		         else {$("#lbInfo").html('发送设备时间获取.失败')};
		    });
	//setDayCount
	 $("#setdaycount").on("click",function(e){
			         alert(""+C_DEVICEID);
			         var Bytes=setDayCount(1000,60);
					 alert(""+Bytes);
			         var x=senddataBytes(Bytes,C_DEVICEID);
			           
			         if(x===0){$("#lbInfo").html('设备时间获取发送.完成')}
			         else {$("#lbInfo").html('发送设备时间获取.失败')};
			    });
		
	 });
	
	  
	 //初始化 微信硬件jsapi库
	function loadXMLDoc()
	{
	    var appId =jQuery("#appId").text();
	    var timestamp=jQuery("#timestamp").text();
	    var nonceStr =jQuery("#nonceStr").text();
	    var signature=jQuery("#signature").text();
	    wx.config({
	             beta: true,
	              debug: true,// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	              appId: appId, 
	              timestamp: timestamp,
	              nonceStr: nonceStr,
	              signature: signature,
	              jsApiList: [
	                'openWXDeviceLib',
	                'closeWXDeviceLib',
	                'getWXDeviceInfos',
	                'getWXDeviceBindTicket',
	                'getWXDeviceUnbindTicket',
	                'startScanWXDevice',
	                'stopScanWXDevice',
	                'connectWXDevice',
	                'disconnectWXDevice',
	                'sendDataToWXDevice',
	                'onWXDeviceBindStateChange',
	                'onWXDeviceStateChange',
	                'onScanWXDeviceResult',
	                'onReceiveDataFromWXDevice',
	                'onWXDeviceBluetoothStateChange',
	              ]
	          });
	             alert("初始化库结束");
	}
	//判断调用jsapi返回状态 true表示成功
	wx.error(function (res) {
	  alert("调用微信jsapi返回的状态:"+res.errMsg);
	});
	 
	/******************************分割线************************************************/
	/*********************************************************
	* 打开微信设备
	* 作者： 2016-04-04
	* my_openWXDeviceLib
	* 入口参数：无
	* 出口参数：0表示打开成功；1表示打开失败
	*********************************************************/
	function my_openWXDeviceLib(){
	   var x=0; 
	   WeixinJSBridge.invoke('openWXDeviceLib', {}, 
	   function(res){
	       mlog("打开设备返回："+res.err_msg);
	      if(res.err_msg=='openWXDeviceLib:ok')
	        {
	          if(res.bluetoothState=='off')
	            {    
	              showdialog("太着急啦","亲,使用前请先打开手机蓝牙！");  
	              $("#lbInfo").innerHTML="1.请打开手机蓝牙";
	              $("#lbInfo").css({color:"red"});
	              x=1;
	              isOver();
	            };
	          if(res.bluetoothState=='unauthorized')
	            {
	              showdialog("出错啦","亲,请授权微信蓝牙功能并打开蓝牙！");    
	              $("#lbInfo").html("1.请授权蓝牙功能");
	              $("#lbInfo").css({color:"red"});
	              x=1;
	              isOver();
	            }; 
	          if(res.bluetoothState=='on')
	            {
	              //showdialog("太着急啦","亲,请查看您的设备是否打开！");   
	              $("#lbInfo").html("1.蓝牙已打开,未找到设备");
	              $("#lbInfo").css({color:"red"});
	              //$("#lbInfo").attr(("style", "background-color:#000");
	              x=0;
	              //isOver();
	            };      
	        }
	      else
	        {
	          $("#lbInfo").html("1.微信蓝牙打开失败");
	          x=1; 
	          showdialog("微信蓝牙状态","亲,请授权微信蓝牙功能并打开蓝牙！");   
	        }
	    });
	   return x;  //0表示成功 1表示失败
	}
	 
	 
	 
	 
	 
	/**********************************************
	* 取得微信设备信息
	* 作者：2016-04-04
	* my_getWXDeviceInfos
	* 入口参数：无
	* 出口参数：返回一个已经链接的设备的ID
	**********************************************/
	function my_getWXDeviceInfos(){
	    
	    WeixinJSBridge.invoke('getWXDeviceInfos', {}, function(res){
	        var len=res.deviceInfos.length;  //绑定设备总数量
	        for(i=0; i<=len-1;i++)
	         {
	           //alert(i + ' ' + res.deviceInfos[i].deviceId + ' ' +res.deviceInfos[i].state); 
	           if(res.deviceInfos[i].state==="connected")
	            {
	              $("#lbdeviceid").html(res.deviceInfos[i].deviceId); 
	              C_DEVICEID = res.deviceInfos[i].deviceId;
	              $("#lbInfo").html("2.设备已成功连接");
	              $("#lbInfo").css({color:"green"});
	              
	              break;   
	            }  
	         }
	            
	    }); 
	  return;    
	}
	//打印日志
	function mlog(m){
	    var log=$('#logtext').val();
	    //log=log+m;
	    log = m;
	    $('#logtext').val(log);
	}
	 
	/***************************************************************
	 * 显示提示信息
	***************************************************************/
	function showdialog(DialogTitle,DialogContent){
	   var $dialog = $("#Mydialog");
	   $dialog.find("#dialogTitle").html(DialogTitle);
	   $dialog.find("#dialogContent").html(DialogContent);
	   $dialog.show();
	   $dialog.find(".weui_btn_dialog").one("click", function(){
	        $dialog.hide();
	   });
	}
	 
	 
	/*******************************************************************
	 * 发送数据函数
	 * 作者：V型知识库 www.vxzsk.com 2016-04-04
	 * 入口参数：
	 *     cmdBytes: 需要发送的命令字节
	 *     selDeviceID: 选择的需要发送设备的ID 
	 * 出口参数：
	 *     返回: 0表示发送成功；1表示发送失败
	 *     如果成功，则接收事件应该能够收到相应的数据
	*******************************************************************/
	function senddataBytes(cmdBytes,selDeviceID){
	  //1. 如果输入的参数长度为零，则直接退出
	  if(cmdBytes.length<=0){return 1};
	 // alert("向微信发送指令数据");
	  //1.1 如果设备ID为空，则直接返回
	  if(selDeviceID.length<=0){return 1};
	  //2. 发送数据
	  var x=0;
	  WeixinJSBridge.invoke('sendDataToWXDevice', {
	            "deviceId":selDeviceID, 
	            "base64Data":bytes_array_to_base64(cmdBytes)
	            }, function(res){
	                //alert("向微信发送指令数据返回的状态"+res.err_msg);
	            if(res.err_msg=='sendDataToWXDevice:ok')
	               {
	                 x=0;
	                 alert("数据发送成功");
	               }  
	            else
	               {
	                  
	                 x=1; 
	                 alert("数据发送失败");
	               } 
	        });  
	  return x;      
	}
	 
	/*********************************************************
	* 接收到数据事件
	* 
	* my_onReceiveDataFromWXDevice
	* 入口参数：无
	* 出口参数：无
	*********************************************************/ 
	function my_onReceiveDataFromWXDevice(){
	      
	    WeixinJSBridge.on('onReceiveDataFromWXDevice', function(argv) {
	    mlog("接收的数据-->"+pHex(base64_to_bytes_array(argv.base64Data)));
	    var bytes=base64_to_bytes_array(argv.base64Data);
	    if(bytes[0]==0x03&&bytes[1]==0x04){
	    	var recivedhexstring = Bytes2Str(bytes);
	    	var value=recivedhexstring.substring(4);
	    	alert(value);
	    	var seconds =translateHextoint(value);
	    	alert(seconds);
	    	var devicetime=translateSecondsToTime(seconds);
	    	var df= dateFormatUtil(devicetime);
	    	alert(df);
	    }
	    
	    
	 });
	}
	 /**
	 * 16进制的字符串转换成字节数组
	 * @param hexString
	 */
	function parseToBytes(hexString){
	    var bytes=new Array();
	    if(bytes.length%2==0){
	        var len=hexString.length;
	        for(var i=0,j=0; j<len; i++,j+=2){
	            bytes[i]=parseInt(hexString[j]+hexString[j+1],16);
	        }
	    }else{
	        alert("当前数据字符的  长度  不能被2整除，请在末尾添加0，或者在末尾前一位插入零！");
	    }
	    return bytes;
	}
	/**
	 * 把字节数组按16进制打印,这里需要保证数组的每个元素是Number类型
	 * @param bytes
	 */
	function print_hex(bytes){
	    var hexString="Head:[";
	    for(var i=0;i<bytes.length;i++){
	        if(i===bytes.length-1 || i===11) hexString+=(bytes[i]>0xF?bytes[i].toString(16):"0"+bytes[i].toString(16));
	        else hexString+=(bytes[i]>0xF?bytes[i].toString(16):"0"+bytes[i].toString(16))+",";
	        if(i===11) hexString+="]<br/>Body:[";
	    }
	    hexString+="]";
	    return hexString;
	}
	
	/**
	 * 将Base64字符串转换成Byte数组
	 * @return [0x00,0x00]
	 * @param 字符串
	 */
	function base64_to_bytes_array(base64String) {
	    var result = new Array();
	    if (base64String.length % 4 != 0 || base64String.length == 0) {
	        return result;
	    }
	    var b64Chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
	    // 先将后面的字段转换成原来的Byte数组
	    var len = base64String.length;
	    var endBytes = new Array();
	    if (base64String.charAt(len - 1) == "=") {
	        if (base64String.charAt(len - 2) == "=") {                                // 有两个等号的情况
	            var s1 = base64String.charAt(len - 4);                             // 后面的第一个字符
	            var s2 = base64String.charAt(len - 3);                             // 后面的第二个字符
	            var v1 = ((b64Chars.indexOf(s1) << 2) & 0xFF) + (b64Chars.indexOf(s2) >> 4); //这个就是最后面的一个字节
	             
	            endBytes[0]=v1;
	        } else {                                                              // 只有一个等号的情况
	            var s1 = base64String.charAt(len - 4);                             // 后面的第一个字符
	            var s2 = base64String.charAt(len - 3);                             // 后面的第二个字符
	            var s3 = base64String.charAt(len - 2);                             // 后面的第三个字符
	             
	            var v1= ((b64Chars.indexOf(s1) << 2) & 0xFF) + (b64Chars.indexOf(s2) >> 4);       //这个就是最后面的第一个字节
	            var v2= ((b64Chars.indexOf(s2) << 4) & 0xFF) + (b64Chars.indexOf(s3) >> 2);       //这个就是最后面的第二个字节
	             
	            endBytes[0] = v1;
	            endBytes[1] = v2;
	        }
	    }
	 
	    var times = base64String.length / 4;
	 
	    for (var i = 0; i < times - (endBytes.length == 0 ? 0 : 1); i++) {
	        var startIndex = i * 4;
	        var V1 = base64String.charAt(startIndex + 0);
	        var V2 = base64String.charAt(startIndex + 1);
	        var V3 = base64String.charAt(startIndex + 2);
	        var V4 = base64String.charAt(startIndex + 3);
	 
	        result[i * 3 + 0]=((b64Chars.indexOf(V1) << 2) & 0xFF) + (b64Chars.indexOf(V2) >> 4);
	        result[i * 3 + 1]=((b64Chars.indexOf(V2) << 4) & 0xFF) + (b64Chars.indexOf(V3) >> 2);
	        result[i * 3 + 2]=((b64Chars.indexOf(V3) << 6) & 0xFF) + b64Chars.indexOf(V4);
	    }
	    return result.concat(endBytes);
	}
	 function pHex(bytes){
		    var hexString="[";
		    for(var i=0;i<bytes.length;i++){
		        if(i===bytes.length-1 || i===11) 
		            hexString+=(bytes[i]>0xF?bytes[i].toString(16):"0"+bytes[i].toString(16));
		        else 
		            hexString+=(bytes[i]>0xF?bytes[i].toString(16):"0"+bytes[i].toString(16))+",";
		        if(i===11) hexString+="";
		    }
		    hexString+="]";
		    return hexString;
		}
	      //重置
	    function ResetDevice(){
	     var Bytes=new Array();
	      
	        Bytes[0]=0x05;
	          
	        Bytes[1]=0x00;
	      return Bytes;
	      
	  }
	   //电池电量
	    function CheckBattery(){
		     var Bytes=new Array();
		      
		        Bytes[0]=0x02;
		          
		        Bytes[1]=0x00;
		  
		      return Bytes;
		      
		  }
	
	 //检查次数
	    function checkcount(){
		     var Bytes=new Array();
		      
		        Bytes[0]=0x01;
		        Bytes[1]=0x00;
		      return Bytes;
		      
		  }
	//查询时间
	    function checkDeviceTime(){
		     var Bytes=new Array();
		      
		        Bytes[0]=0x03;
		        Bytes[1]=0x00;
		      return Bytes;
		      
		  }
	//设置单日次数
	 function setDayCount(num,time){
			//时间转为hex 高低位翻转
		    var timehex=decimalToHexString(time) ;
		    var tlength=timehex.length;
		    //两字节不足补0
		    if(tlength<4){
		    	for(var i=0;i<4-length;i++){
		    		Hexstring="0"+Hexstring;
		    	}
		    }
		    var reverseTimeHexString=reverseHex(timehex);
		    timevalue= parseToBytes(reverseTimeHexString);
		   //目标数hex 高低位翻转
		  var Hexstring = decimalToHexString(num) ;
	      console.info(Hexstring);
	      var length=Hexstring.length;
	      //字符串不足16位（4个字节 0x1234 5678 9012 3456）
		    if(length<8){
		    	for(var i=0;i<8-length;i++){
		    		Hexstring="0"+Hexstring;
		    	}
		    }
	      
		  var reverseHexString=reverseHex(Hexstring);
		  console.info(reverseHexString);
		  
		  //header 0x0106
		    var value=new Array(); 
		    value= parseToBytes(reverseHexString);
		  
		  var header=new Array();      
		        header[0]=0x01;
		        header[1]=0x06;
		//拼装数据
		     var res= header.concat(value).concat(timevalue);
		    console.info(res);	
		       return res;
	}
	
	//设置时间
	//0x1234--->ox3412
	//translate('0x12345678'); //2018915346
		function translateHextoint(num){
  			return parseInt('0x' + num.match(/[a-fA-F0-9]{2}/g).reverse().join(''), 16);
		}
	
	   function reverseHex(HexString){
		   if(HexString.length%2!=0)
			   HexString="0"+HexString;
		   return  HexString.match(/[a-fA-F0-9]{2}/g).reverse().join('');
		   
	   }
		//转换低位在前，高位在后
	   function setDeviceTime()
	   {
		     var Bytes=new Array();      
			        Bytes[0]=0x03;
			        Bytes[1]=0x04;
			
				    //先获取秒
				    var seconds = getSecondsNowto20000101();
				    //转为hex数组
				     console.info(seconds);
				    var hexseconds=    decimalToHexString(seconds);
				    //翻转，高低自己
				    var reversehex = hexseconds.match(/[a-fA-F0-9]{2}/g).reverse().join('');
				    console.info(reversehex);
				    console.info(hexseconds);
				    var Bytestime=new Array();
				    Bytestime= parseToBytes(reversehex);
				    var res= Bytes.concat(Bytestime);
				    console.info(res);
				    alert("设定时间的数据=="+res);
				    //调整数组高低字节顺序
				return res;
			
	   }
	
	
	  /**
	 *  Byte数组转Base64字符,原理同上 
	 * @Param [0x00,0x00]
	 * @return Base64字符串
	 **/
	function bytes_array_to_base64(array) {
	    if (array.length == 0) {
	        return "";
	    }
	    var b64Chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
	    var result = "";
	    // 给末尾添加的字符,先计算出后面的字符
	    var d3 = array.length % 3;
	    var endChar = "";
	    if (d3 == 1) {
	        var value = array[array.length - 1];
	        endChar = b64Chars.charAt(value >> 2);
	        endChar += b64Chars.charAt((value << 4) & 0x3F);
	        endChar += "==";
	    } else if (d3 == 2) {
	        var value1 = array[array.length - 2];
	        var value2 = array[array.length - 1];
	        endChar = b64Chars.charAt(value1 >> 2);
	        endChar += b64Chars.charAt(((value1 << 4) & 0x3F) + (value2 >> 4));
	        endChar += b64Chars.charAt((value2 << 2) & 0x3F);
	        endChar += "=";
	    }
	  
	    var times = array.length / 3;
	    var startIndex = 0;
	    // 开始计算
	    for (var i = 0; i < times - (d3 == 0 ? 0 : 1); i++) {
	        startIndex = i * 3;
	  
	        var S1 = array[startIndex + 0];
	        var S2 = array[startIndex + 1];
	        var S3 = array[startIndex + 2];
	  
	        var s1 = b64Chars.charAt(S1 >> 2);
	        var s2 = b64Chars.charAt(((S1 << 4) & 0x3F) + (S2 >> 4));
	        var s3 = b64Chars.charAt(((S2 & 0xF) << 2) + (S3 >> 6));
	        var s4 = b64Chars.charAt(S3 & 0x3F);
	        // 添加到结果字符串中
	        result += (s1 + s2 + s3 + s4);
	    }
	  
	    return result + endChar;
	}
	    
/**
 * var date1=new Date();  //开始时间;
 date1.setFullYear(2000,1,1);date1.setHours(0)	//设置 Date 对象中的小时 (0 ~ 23)。
 date1.setMinutes(0);	//设置 Date 对象中的分钟 (0 ~ 59)。
 date1.setSeconds(0);var date2=new Date();    //结束时间
 var date3=date2.getTime()-date1.getTime();alert(date3);console.info(date1.getTime());
 */
 function getSecondsNowto20000101(){
	 var date1=new Date();  //开始时间;
	 date1.setFullYear(2000,1,1);//设置2000-01-01
	 date1.setHours(0)	//设置 Date 对象中的小时 (0 ~ 23)。
	 date1.setMinutes(0);	//设置 Date 对象中的分钟 (0 ~ 59)。
	 date1.setSeconds(0);
	 var date2=new Date();    //结束时间现在
	 var date3=date2.getTime()-date1.getTime(); //millsesonds
	 console.info(date3/1000); 
	 return parseInt(date3/1000);
 }
 
 function translateSecondsToTime(seconds){
	 var date1=new Date();  //开始时间;
	 date1.setFullYear(2000,1,1);//设置2000-01-01
	 date1.setHours(0)	//设置 Date 对象中的小时 (0 ~ 23)。
	 date1.setMinutes(0);	//设置 Date 对象中的分钟 (0 ~ 59)。
	 date1.setSeconds(0);
    //加上获取到的秒数
	 var date2=date1.getTime()+(seconds*1000) //millsesonds
	 return date2;
 }
 
 
 
//decimal to hex ;
 function decimalToHexString(number)
 {
     if (number < 0)
     {
         number = 0xFFFFFFFF + number + 1;
     }
     return number.toString(16).toUpperCase();
 }
 
 /* 
 时间格式化工具 
 把Long类型的yyyy-MM-dd日期还原yyyy-MM-dd格式日期  
*/  
function dateFormatUtil(longTypeDate){  
 var dateTypeDate = "";  
 var date = new Date();  
 date.setTime(longTypeDate);  
 dateTypeDate += date.getFullYear();   //年  
 dateTypeDate += "-" + getMonth(date); //月   
 dateTypeDate += "-" + getDay(date);   //日  
 return dateTypeDate;  
}  

//返回 01-12 的月份值   
function getMonth(date){  
 var month = "";  
 month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
 if(month<10){  
     month = "0" + month;  
 }  
 return month;  
}  

//返回01-30的日期  
function getDay(date){  
 var day = "";  
 day = date.getDate();  
 if(day<10){  
     day = "0" + day;  
 }  
 return day;  
} 

function Str2Bytes(str)
{
    var pos = 0;
    var len = str.length;
    if(len %2 != 0)
    {
       return null; 
    }
    len /= 2;
    var hexA = new Array();
    for(var i=0; i<len; i++)
    {
       var s = str.substr(pos, 2);
       var v = parseInt(s, 16);
       hexA.push(v);
       pos += 2;
    }
    return hexA;
}
 
//字节数组转十六进制字符串
function Bytes2Str(arr)
{
    var str = "";
    for(var i=0; i<arr.length; i++)
    {
       var tmp = arr[i].toString(16);
       if(tmp.length == 1)
       {
           tmp = "0" + tmp;
       }
       str += tmp;
    }
    return str;
}

 
</script>
</body>
</html>
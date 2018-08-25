<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<button id="videoBtn" onclick="video()">录像</button>
<button id="audioBtn" onclick="audio()">录音</button>
<button id="stopBtn" onclick="stop()">停止录制</button>
<a id="uploadVideoAndAudio" href=""  download="video.wmv" style="color:blue;text-decoration:underline;">下载</a>
<div>
	<video id="video" width="400" height="300"></video>
</div>
<div id="videoDiv"></div>

<script type="text/javascript">
//实现录制音视频
//第一步： 定义兼容的开启/关闭媒体设备 (摄像头麦克风) 工具方法
var MediaUtils = {
  /**
   * 获取用户媒体设备(处理兼容的问题)
   * @param videoEnable {boolean} - 是否启用摄像头
   * @param audioEnable {boolean} - 是否启用麦克风
   * @param callback {Function} - 处理回调
   */
  getUserMedia: function (videoEnable, audioEnable, callback) {
      navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia
          || navigator.msGetUserMedia || window.getUserMedia;
      var constraints = {video: videoEnable, audio: audioEnable};
      var video = document.getElementById("video");
      
      if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
          navigator.mediaDevices.getUserMedia(
          	constraints
          ).then(function (stream) {
          	mediaStreamTrack = typeof stream.stop === 'function' ? stream : stream.getTracks()[1];
         	 
	            video.src = (window.URL || window.webkitURL).createObjectURL(stream);
	            video.play();
              callback(false, stream);
          })['catch'](function(err) {
              callback(err);
          });
      } else if (navigator.getUserMedia) {
          navigator.getUserMedia(
          	constraints, 
          function (stream) {
      		mediaStreamTrack = stream.getTracks()[0];
      		 
	            video.src = (window.URL || window.webkitURL).createObjectURL(stream);
	            video.play();
              callback(false, stream);
          }, function (err) {
              callback(err);
          });
      } else {
          callback(new Error('Not support userMedia'));
      }
  },

  /**
   * 关闭媒体流
   * @param stream {MediaStream} - 需要关闭的流
   */
  closeStream: function (stream) {
      if (typeof stream.stop === 'function') {
          stream.stop();
      }
      else {
          let trackList = [stream.getAudioTracks(), stream.getVideoTracks()];

          for (let i = 0; i < trackList.length; i++) {
              let tracks = trackList[i];
              if (tracks && tracks.length > 0) {
                  for (let j = 0; j < tracks.length; j++) {
                      let track = tracks[j];
                      if (typeof track.stop === 'function') {
                          track.stop();
                      }
                  }
              }
          }
      }
  }
}; 
//第二步：定义录制音视频的各个接口 (录制/停止/播放)
//用于存放 MediaRecorder 对象和音频Track，关闭录制和关闭媒体设备需要用到
var recorder, mediaStream;

//用于存放录制后的音频文件对象和录制结束回调
var recorderFile, stopRecordCallback;

//用于存放是否开启了视频录制
var videoEnabled = false;

//录制短语音
function startRecord(enableVideo) {
  videoEnabled = enableVideo;
  MediaUtils.getUserMedia(enableVideo, true, function (err, stream) {
      if (err) {
          throw err;
      } else {
          // 通过 MediaRecorder 记录获取到的媒体流
          recorder = new MediaRecorder(stream);
          mediaStream = stream;
          var chunks = [], startTime = 0;
          recorder.ondataavailable = function(e) {
              chunks.push(e.data);
          };
          recorder.onstop = function (e) {
              recorderFile = new Blob(chunks, { 'type' : recorder.mimeType });
              chunks = [];
              if (null != stopRecordCallback) {
                  stopRecordCallback();
              }
          };
          recorder.start();
      }
  });
}

//停止录制
function stopRecord(callback) {
  stopRecordCallback = callback;
  // 终止录制器
  recorder.stop();
  // 关闭媒体流
  MediaUtils.closeStream(mediaStream);
}

//播放录制的音频
function playRecord() {
  var url = URL.createObjectURL(recorderFile);
  var dom = document.createElement(videoEnabled ? 'video' : 'audio');
  dom.autoplay = 'autoplay';
  dom.controls = 'controls';
  dom.src = url;
  dom.width = 640;
  dom.height = 480;
  dom.style.zIndex = '9999999';
  dom.style.position = 'fixed';
  dom.style.left = '0';
  dom.style.right = '0';
  dom.style.top = '0';
  dom.style.bottom = '0';
  dom.style.margin = 'auto';
  $("#videoDiv").empty().append(dom);
  $("#uploadVideoAndAudio").attr("href",url);
}

//录像按钮
function video(){
	//启动录制视频 
	startRecord(true);
} 

//录音按钮
function audio(){
	//启动录制音频 
	startRecord(false);
}

//停止录制按钮
function stop(){
	 // 结束
  stopRecord(function() {
      // 播放
      playRecord();
  });
}



//未实现
//录音录像上传
function uploadVideoAndAudio(){
	var data = new FormData();

	data.append("username", "test");
	data.append("userfile", recorderFile);
	
	var uploadUrl = $WEB_ROOT_PATH + '/mupload/uploadAudio';
	
	$.ajax({
		url:uploadUrl,//该处为我们的ajax上传URL
		data:data,
		type: 'POST',   //请求类型
	    contentType: false,//没有设置任何内容类型头信息
	    processData: false, 
		dataType:'json',//ajax返回的url
		success:function(data){
			/*if (data.mResult.msg != '0') {
				alert(data.mResult.msg);
			}
			else {
				for (var i = 0; i < data.mResult.data.length; i++) {
					var htmlStr='';				
				 	if (data.mResult.data[i].fileType=='I') {
						htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
											'	 <img src="'+data.mResult.data[i].url+'"' + ' ondblclick="biggerImg(this);" class="img img_self"  title="双击查看原图"/>' +
											'  <div title="点击删除图片" class="del_img del_img_self" style="color:red;" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
											'</div>';					
					}         
	 				else if (data.mResult.data[i].fileType=='A') {
						htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
											'  <img src="<c:url value="/image/common/mp3.png"/>" ondblclick="displayMp3(\''+data.mResult.data[i].url+'\')" class="img_self" title="双击收听音频"/>' +
											'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
											'</div>';					
					}	 
	 				else if (data.mResult.data[i].fileType=='V') {
						htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
											'  <img src="<c:url value="/image/common/mp4.png"/>" ondblclick="displayMp4(\''+data.mResult.data[i].url+'\')" class="img_self" title="双击观看视频"/>' +
											'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
											'</div>';					
					}					
					else if (data.mResult.data[i].fileType=='P') {
						htmlStr = '<div class="div_img_self" id="data_'+data.mResult.data[i].id+'">' + 
											'  <a href="'+data.mResult.data[i].url+'" target="_blank"><img src=\'<c:url value="/image/common/pdf.png"/>\' class="img_self" title="点击下载文件"/></a>' +
											'  <div class="del_img del_img_self" title="点击删除文件" onclick="del(\'' +data.mResult.data[i].id+'\',\''+ uuid+'\')"></div>' +	
											'</div>';	
					}					 

					$('#resultData_'+uuid).append(htmlStr);	
				}
				$("#canvasPhoto").empty();
			}    */
		},
		error:function(data,status,e){
			alert("图片上传失败...");
		}
	});	
}
	
</script>
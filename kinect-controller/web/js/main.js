var rootDirectory = "/";

$(document).ready(function(){
	$(document).mousemove(function(event){
		$(".cursor").css('left',event.pageX - 64);
		$(".cursor").css('top',event.pageY - 64);
		$(".position").html("(" + event.pageX + "," + event.pageY + ")");
	});
	
	$(".pane").mouseover(function(){
		$(this).stop();
		$(".cursor").css('background',"url('images/handy.png')");
		$(this).animate({
			right: '0'
		},250,function(){
			
		});
	});
	
	$(".pane").mouseout(function(){
		$(this).stop();
		$(".cursor").css('background',"url('images/hand.png')");
		$(this).animate({
			right: '-99px'
		},250,function(){
			
		});
	});
	
	$(".exit_app").click(function(){
		java.exit();
	});
	
	$(".home_app").click(function(){
		java.listFolder(rootDirectory);
	});
});

function fileList(dirDetail, files){
	var dirDetail = $.parseJSON(dirDetail);
	var fileLists = $.parseJSON(files);
	//java.debug(dirDetail.currentDir);
	$(".content").html("<b>"+dirDetail.currentDir+"<b><br>");
	$.each(fileLists,function(key, data){
		if(data.substr(0,6) == "[DIR] ")
			$(".content").append("<a onclick='java.listFolder(\""+dirDetail.currentDir+"/"+data.substr(6)+"\")'>"+data+"</a><br>");
		else 
			$(".content").append("<a onclick='java.fileHandle(\""+dirDetail.currentDir+"/"+data+"\")'>"+data+"</a><br>");
	});
}

function setRootDirectory(dir){
	rootDirectory = dir;
}

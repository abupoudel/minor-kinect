$(document).ready(function(){
	$(document).mousemove(function(event){
		$(".position").html("(" + event.pageX + "," + event.pageY + ")");
	});
	
	$(".pane").mouseover(function(){
		$(this).stop();
		$(this).animate({
			right: '0'
		},250,function(){
			
		});
	});
	
	$(".pane").mouseout(function(){
		$(this).stop();
		$(this).animate({
			right: '-99px'
		},250,function(){
			
		});
	});
	
	$(".exit_app").click(function(){
		java.exit();
	});
	
	$(".home_app").click(function(){
		java.listFolder("/home/fr3ak/Documents/");
	});
});

function fileList(files){
	var fileLists = $.parseJSON(files);
	$.each(fileLists,function(key, data){
		$(".content").append(data+"<br>");
	});
}


$(document).ready(function(){
	$(document).mousemove(function(event){
		$(".position").html("(" + event.pageX + "," + event.pageY + ")");
	});
	
	$(".pane").mouseenter(function(){
		$(this).stop();
		$(this).animate({
			right: '0'
		},250,function(){
			aniPane = 0;
		});
	});
	
	$(".pane").mouseout(function(){
		$(this).stop();
		$(this).animate({
			right: '-99px'
		},250,function(){
			aniPane = 0;
		});
	});
});

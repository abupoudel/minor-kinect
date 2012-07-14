var rootDirectory = "/";
var perPage = 6;
var numbers = 0;
var page = 0;
var currentPage = 0;

$(document).ready(function(){
	$(document).mousemove(function(event){
		$(".cursor").css('left',event.pageX - 64);
		$(".cursor").css('top',event.pageY - 64);
		$(".position").html("(" + event.pageX + "," + event.pageY + ")");
	});
	
	$(".pageUp").live('click',function(){
		if(page == 0 || currentPage == 0){
			$(this).html(currentPage);
		} else {
			currentPage++;
			$(this).html(currentPage);
			$(".frame").animate({
				top: (443*currentPage)
			}, 500, function(){
			})
		}
	});
	
	$(".pageDown").live('click',function(){
		if(page == 1 || currentPage == -page+1){
			$(this).html(currentPage);
		} else {
			currentPage--;
			$(this).html(page);
			$(".frame").animate({
				top: (443*currentPage)
			}, 500, function(){
			})
		}
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
	var content = "";
	numbers = 0;
	content += "<b>"+dirDetail.currentDir+"<b><br><div class='pageUp'></div><div class='lists'><div class='frame'>";
	$.each(fileLists,function(key, data){
		if(data.substr(0,6) == "[DIR] ")
			content += "<div class='lists-sub' onclick='java.listFolder(\""+dirDetail.currentDir+"/"+data.substr(6)+"\")'>"+data+"</div>";
		else 
			content += "<div class='lists-sub' onclick='java.fileHandle(\""+dirDetail.currentDir+"/"+data+"\")'>"+data+"</div>";
		numbers++;
	});
	page = parseInt(numbers/perPage);
	content += "</div></div><div class='pageDown'></div>";
	$('.content').html(content);
	initializePage(page);
	currentPage = 0;
}

function initializePage(myPage){
	if(myPage == 0)
		noPage();
	else{
	}
}

function noPage(){
	
}

function setRootDirectory(dir){
	rootDirectory = dir;
}

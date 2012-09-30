var rootDirectory = "/";
var parentDirectory = "/";
var perPage = 6;
var numbers = 0;
var page = 0;
var currentPage = 0;
var playing = false;

$(document).ready(function(){
    $(document).mousemove(function(event){
        $(".cursor").css('left',event.pageX - 64);
        $(".cursor").css('top',event.pageY - 64);
        $(".position").html("(" + event.pageX + "," + event.pageY + ")");
    });
    
    $(".mousy").live({
        mouseover: function(){
            var myEvent = $(this);
            $('#cursor').animate({
                opacity: 1
            }, 2000, function() {
                myEvent.click();
                $("#cursor").fadeTo("fast", 0.3);
            });
        },
        mouseout: function(){
            $("#cursor").stop(true, false).fadeTo("fast", 0.3);
        } 
    });
	
    $(".pageUp").live({
        click: function(){
            if(page == 0 || currentPage == 0){
            } else {
                currentPage++;
                $(".frame").animate({
                    top: (443*currentPage)
                }, 500, function(){
                    })
            }
        }
    });
	
    $(".pageDown").live({
        click: function(){
            if(page == 1 || currentPage == -page+1){
            } else {
                currentPage--;
                $(".frame").animate({
                    top: (443*currentPage)
                }, 500, function(){
                    })
            }
        }
    });
    
    $(".playpause").live({
        click: function(){
            var player = $("#myPlayer");
            if (playing == true) {
                $(this).css('background',"url('images/play.png')");
                player.trigger("pause");
                setPlaying(!playing);
            } else {
                $(this).css('background',"url('images/pause.png')");
                player.trigger("play");
                setPlaying(!playing);
            }
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
            right: '-139px'
        },250,function(){
			
            });
    });
	
    $(".exit_app").click(function(){
        java.exit();
    });
	
    $(".home_app").click(function(){
        java.listFolder(rootDirectory);
    });
    
    $(".back_app").click(function(){
        java.listFolder(parentDirectory);
    });
});

function getFileExtension(filename) {
    var parts = filename.split('.');
    return parts[parts.length-1];
}

function fileList(dirDetails, files){
    var dirDetail = $.parseJSON(dirDetails);
    var fileLists = $.parseJSON(files);
    var content = "";
    numbers = 0;
    content += "<b>"+dirDetail.currentDir+"<b><br><div class='pageUp mousy'></div><div class='lists'><div class='frame'>";
    $.each(fileLists,function(key, data){
        if(data.substr(0,6) == "[DIR] ")
            content += "<div class='lists-sub mousy folder' onclick='java.listFolder(\""+dirDetail.currentDir+"/"+data.substr(6)+"\")'><div class='file_name'>"+data+"</div></div>";
        else{ 
            var fileext = getFileExtension(data).toLowerCase();
            content += "<div class='lists-sub mousy file "+ fileext +"' onclick='java.fileHandle(\""+dirDetail.currentDir+"/"+data+"\")'><div class='file_name'>"+data+"</div></div>";
        }
        numbers++;
    });
    page = parseInt(numbers/perPage);
    content += "</div></div><div class='pageDown mousy'></div>";
    $('.content').html(content);
    setParentDirectory(dirDetail.parentDir);
    initializePage(page);
    currentPage = 0;
}

function setContent(content){
    var newCont = "";
    newCont += "<br><div style='height: 128px;'></div><div class='lists'><div class='frame'>";
    newCont += content;
    newCont += "</div></div>";
    
    $('.content').html(newCont);
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

function setParentDirectory(dir){
    parentDirectory = dir;
}

function setPlaying(play){
    playing = play;
}
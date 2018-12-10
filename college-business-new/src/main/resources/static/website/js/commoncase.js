$(document).ready(function(){
// 获取上个界面的localstroge    
var ssipt = localStorage.getItem("key");
console.log(ssipt)
// $("#div_popup").css("transform","translate(-50%,-50%)" )
// console.log(ssipt)
// $(".pc_value").mousemove(function(){
// if($(".pc_value").val() == "" || $(".pc_value").val() == null || $(".pc_value").val() == undefined){
//     $(".delect").css("display","none")
// }else{
//     $(".delect").css("display","block")
// }
// })
// $(".delect").click(function(){
//     $(".pc_value").val("")
// })
// 有无参数跳转
if (ssipt == "" || ssipt == null) {
    $(".seach .seach-left input").val("")
    for (var i = 0; i < qa.length; i++) {
        if (qa[i].gid == "10") {
            if(qa[i].a.length<80){
                $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum' style='padding-bottom:15px'>" + qa[i].a + "</p></li>")
            }else{
               var str = qa[i].a.substr(0,80)+"......"
                $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum'>" + str + "</p><span slided='"+qa[i].uuid+"' class='toglebtn'>查看更多</span></li>")
            }
        }
    }
   
}else{
    $(".seach .seach-left input").val(ssipt)
    $(".tab-title").html("")
    $("#box").html("")
    var len = qa.length;
    var arr = [];
    if (ssipt.indexOf('?')  != -1 ) {
        ssipt = ssipt.substring(0,ssipt.indexOf('?'))
        console.log(ssipt)
        if (ssipt.indexOf('(')  != -1 ) {
            ssipt = ssipt.substring(0,ssipt.indexOf('('))
         console.log(ssipt)
  }
 }
    var reg = new RegExp(ssipt)
    for (var i = 0; i < len; i++) {
        if (qa[i].a.match(reg) || qa[i].q.match(reg)) {
            arr.push(qa[i])
        }
    }
    console.log(arr)
     if(arr == [] || arr ==""){
       
        $(".tab-title").html("");
        $("#box").html("<div style=' text-align: center;padding-top:25px'><p style='font-weight:600;line-height:15px;'>抱歉，没有帮您<span style='color:#13811e'>检索</span>到符合条件的问题。<br/></br>您可以尝试搜索别的关键字，也可以扫描下方二维码联系我们的客服</p> <img style='width:400px;height:auto;margin-left:calc(50% - 200px);' src='photos/xcx.jpg' alt=''></div>")
     
    }else{
    for (var i = 0; i < arr.length; i++) {
        if(arr[i].a.length<80){
            $("#box").append("<li><i>·</i><strong>" + arr[i].q + "</strong><p class='bannum'  style='padding-bottom:15px'>" + arr[i].a + "</p></li>")
        }else{
            var str = arr[i].a.substr(0,80)+"......"
            $("#box").append("<li><i>·</i><strong>" + arr[i].q + "</strong><p class='bannum'>" + str + "</p><span slided='"+arr[i].uuid+"' class='toglebtn'>查看更多</span></li>")
        }
    }
}
    $(".content-left>ul>li").removeClass("red-col")
    localStorage.clear();
  
}
// pc端内容切换
$(".pc-menu>ul>li").click(function () {
    $(".pc_value").val("")
    $("#box").html("")
    $(".tab-title").html($(this).text())
    $(this).addClass("red-col").siblings().removeClass("red-col")
    for (var i = 0; i < qa.length; i++) {
        if (qa[i].gid == $(this).attr("abc")) {
            if(qa[i].a.length<80){
            $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum' style='padding-bottom:15px'>" + qa[i].a + "</p></li>")
            }else{
                var str = qa[i].a.substr(0,80)+"......"
                $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum'>" + str + "</p><span  slided='"+qa[i].uuid+"' class='toglebtn'>查看更多</span></li>")
            }
        
        }
      
    }
    
})

// 展开收起
$("#box").on("click",".toglebtn",function(){
    // console.log($(this).attr("slided"))
    var _slided = $(this).attr("slided")
    for(var i = 0;i<qa.length;i++){
        if(qa[i].uuid == _slided){
            var   str = qa[i].a;
            var _str = $(this).prev().text();
            if(str == _str){
                $(this).prev().text(str.substr(0,80)+"......")
                $(this).html("查看更多")
            }else{
                $(this).prev().text(str)
                $(this).html("收起<span></span>")
                var uuid = _slided
                $.ajax({
                    type: 'POST',
                    // url: "http://localhost:8080/qa/add/" + uuid,
                    url: "http://sing.dazonghetong.com/qa/add/" + uuid,
                    data: {},
                    success: function(res){
                        console.log(res);
                        if(res.code == 200){
                            // console.log(res.data);
                        }else{
                            alert(res.data);
                        }
                    }
                });
            }
        }
    }
})


// 移动端列表点击事件
$(".phone-menu>ul>li").click(function(){
    $(".main>.menu-select").html("");
    $(this).toggleClass("red-col").siblings().removeClass("red-col")
    if($(this).hasClass("red-col")){
        for (var i = 0; i < qa.length; i++) {
            if (qa[i].gid == $(this).attr("abc")) {
                $(this).next(".main").find(".menu-select").append("<li kid='"+qa[i].uuid+"'><span>" + qa[i].q + "</span><i>more</i></li>")
            }
        }
    }else{
        $(this).next(".main").find(".menu-select").html("")
    }
}
)
// 移动端弹窗
var btnclick = document.getElementById('btn_test');
    var divmasklayer = document.getElementById('div_masklayer');
    var xx = document.getElementById("xx");
    var divpoppu = document.getElementById('div_popup');
   $(".main>.menu-select").on("click","li",function(){
    // $("#div_popup").css("transform","translatey(-50%)" )
    var uuid = $(this).attr("kid")
    $.ajax({
        type: 'POST',
        url: "http://sing.dazonghetong.com/qa/add/" + uuid,
        data: {},
        success: function(res){
            if(res.code == 200){
            }else{
            }
        }
    });
    $(".t-content").html("")
        var popup = document.getElementById('div_popup');
        var divmasklayer = document.getElementById('div_masklayer');
        var xx = document.getElementById("xx");
        divmasklayer.style.display = 'block';
        popup.style.display = 'block';
        xx.style.display='block'
        $("body").on("touchmove",function(event){
            event.preventDefault;
            }, false)
            $("body").height( $(window).height());
        //var h = popup.clientHeight;
        var h = popup.Height;
        with(popup.style)
        {
            popup.style.marginLeft = -popup.clientWidth / 2 + 'px';
            popup.style.marginTop = -popup.clientHeight / 2 + 'px';
        }
        var litest = $(this).attr("kid");
        
        for(var i=0;i<qa.length;i++){
        if(litest == qa[i].uuid){
            $(".t-content").append("<p>"+qa[i].q+"</p><span>"+qa[i].a+"</span>")
        }
    }
    })
    xx.onclick=function(){ 
        document.getElementById('div_popup').style.display="none"; 
        document.getElementById('div_masklayer').style.display="none";
    }




    
// pc搜索功能
$(".pc-seach input").click(function(){
    var keyword = $(".pc_value").val()
    console.log($(".seach .seach-left input"))
    if(keyword == null || keyword == undefined || keyword == ""){
        $("#box").html("")
        $(".tab-title").html("和君商学院第十二届")
        for (var i = 0; i < qa.length; i++) {
            $(".pc-menu>ul>li:nth-child(1)").addClass("red-col").siblings().removeClass("red-col")
            if (qa[i].gid == "10") {
                if(qa[i].a.length<80){
                    $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum' style='padding-bottom:15px'>" + qa[i].a + "</p></li>")
              
                }else{
                   var str = qa[i].a.substr(0,80)+"......"
                    $("#box").append("<li><i>·</i><strong>" + qa[i].q + "</strong><p class='bannum'>" + str + "</p><span slided='"+qa[i].uuid+"' class='toglebtn'>查看更多</span></li>")
                }
              
            }
        }
     
    }else{
    $("#box").html("")
    $(".tab-title").html("")
    var len = qa.length;
    // console.log(qa)
    var arr = [];
    if (keyword.indexOf('?')  != -1 ) {
        keyword = keyword.substring(0,keyword.indexOf('?'))
        console.log(keyword)
        if (keyword.indexOf('(')  != -1 ) {
         keyword = keyword.substring(0,keyword.indexOf('('))
         console.log(keyword)
  }
 }
    var reg = new RegExp(keyword)
    console.log(reg)
    for (var i = 0; i < len; i++) {
        if (qa[i].a.match(reg) || qa[i].q.match(reg)) {
            arr.push(qa[i])
        }
    }
 
    if(arr == [] || arr ==""){
        $(".tab-title").html("");
        $("#box").html("<div style=' text-align: center;padding-top:25px'><p style='font-weight:600;line-height:15px;'>抱歉，没有帮您<span style='color:#13811e'>检索</span>到符合条件的问题。<br/></br>您可以尝试搜索别的关键字，也可以扫描下方二维码联系我们的客服</p> <img style='width:400px;height:auto;margin-left:calc(50% - 200px);' src='photos/xcx.jpg' alt=''></div>")
    
    }else{
        for (var i = 0; i < arr.length; i++) {
            // $("#box").append("<li><i>·</i><strong>" + arr[i].q + "</strong><p class='bannum'>" + arr[i].a + "</p><span slided='"+qa[i].uuid+"' class='toglebtn'>展开</span></li>")
            if(arr[i].a.length<80){
                $("#box").append("<li><i>·</i><strong>" + arr[i].q + "</strong><p class='bannum' style='padding-bottom:15px'>" + arr[i].a + "</p></li>")
            }else{
                var str = arr[i].a.substr(0,80)+"......"
                $("#box").append("<li><i>·</i><strong>" + arr[i].q + "</strong><p class='bannum'>" + str + "</p><span slided='"+arr[i].uuid+"' class='toglebtn'>查看更多</span></li>")
            }   
        }
      
    }
    $(".content-left>ul>li").removeClass("red-col")

}     

})


// 移动端搜索功能

$(".phone-seach input").click(function(){
    var kw = $(".seach .seach-left input").val()
    if(kw == null || kw == undefined || kw == ""){
        alert("请输入搜索内容")
    }else{
        // $("#div_popup").css("transform","translatey(-50%)" )
        $(".t-content").html("")
        $("#div_popup").css("display","block")
        $("#div_masklayer").css("display","block")
        $("body").on("touchmove",function(event){
            event.preventDefault;
            }, false)
            $("body").height( $(window).height());
            
        // $("#div_popup").css("transform","translate(-50%,-50%)" )
    var len = qa.length;
    var arr = [];

    if (kw.indexOf('?')  != -1 ) {
        kw = kw.substring(0,kw.indexOf('?'))
        console.log(kw)
        if (kw.indexOf('(')  != -1 ) {
         kw = kw.substring(0,kw.indexOf('('))
         console.log(kw)
  }
 }


    var reg = new RegExp(kw)
    for (var i = 0; i < len; i++) {
        if (qa[i].a.match(reg) || qa[i].q.match(reg)) {
            arr.push(qa[i])
        }
    }
    if(arr == [] || arr == ""){
        alert("内容不存在")
        $("#div_popup").css("display","none")
        $("#div_masklayer").css("display","none")
    }else{
        for (var i = 0; i < arr.length; i++) {
            $(".t-content").append("<p>"+arr[i].q+"</p><span>"+arr[i].a+"</span>")
        }
    }
    
    // $(".content-left>ul>li").removeClass("red-col")


}     

})


// pc头部 导航   
$(".ac").click(function(){
    $(this).addClass("ac-active").siblings().removeClass("ac-active")
})


// 移动头部下拉列表
$(".header-right").click(function(){
	$(".select-list").toggle();
    $(this).toggleClass("closed");
    if( $(".select-list").css("display") =="block"){
        $("body").on("touchmove",function(event){
    event.preventDefault;
    }, false)
    $("body").height( $(window).height());
    }else{
        $("body").on("touchmove",function(event){
    event.preventDefault;
    }, true)
    $("body").height( $(window).height());
    }
})



 
})
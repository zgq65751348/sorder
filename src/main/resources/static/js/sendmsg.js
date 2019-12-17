$(document).ready(function(){
    if(window.location.href .indexOf("=")>=0) {
        var mid = window.location.href.split("=")[1];
    }
    if(mid){
        $.ajax({
            url:  addressUrl+'/msg/getMsg',
            type: 'POST',
            data:'jsonString={"mid":"'+mid+'"}',
            dataType:'json',
            success: function (returndata){
                console.log(returndata);
                if(returndata.status=="200") {
                    $("[name='title']").val(returndata.data.title);
                    $("textarea").val(returndata.data.content);
                } else {
                    alert(returndata.info);
                }
            },
            error: function (returndata) {
                console.log(returndata);
                alert("服务器错误，请稍后重试！");
            }
        });

    }
});

$(".cancel").click(function(){
    $("textarea").val(" ");
    $("[name='title']").val(" ");
});

$(".confirm").click(function(){
    if($("textarea").val()&&$("[name='title']").val()){
        $.ajax({
            url:  addressUrl+'/msg/sendPlatformMsg',
            type: 'POST',
            data:'jsonString={suid:'+window.sessionStorage.getItem("suid")+',content:'+$("textarea").val()+',push_platform:'+$("[name='platform']:checked").val()+',title:'+$("[name='title']").val()+'}',
            dataType:'json',
            success: function (returndata) {
                console.log(returndata);
                if (returndata.status == "200") {
                    alert("推送成功");
                    $("textarea").val(" ");
                } else {
                    alert(returndata.info);
                }
            },
            error: function (returndata) {
                alert("服务器错误，请稍后重试！");
            }
        });
    }else{
        alert("消息推送不能为空！")
    }

});
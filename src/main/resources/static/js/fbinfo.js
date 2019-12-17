/**
 * Created by ������Ѷ on 2017/1/14.
 */

$(document).ready(function(){
    var fbid=window.location.href.split('?')[1].split("=")[1];
    $.ajax({
        url:  addressUrl+'/msg/getFB',
        type: 'POST',
        data:'jsonString={"fbid":"'+fbid+'"}',
        dataType:'json',
        success: function (returndata){
            console.log(returndata);
            if(returndata.status=="200"){
                var list=returndata.data;
                var fblist='<li><b>用户名：</b>'+list.uph+'</li><li><b>反馈日期：</b>'+list.crtime+'</li><li><b>反馈内容：</b>'+list.content+'</li>';
                $(".fbinfo").html(fblist);
            } else {
                alert(returndata.info);
            }
        },
        error: function (returndata) {
            console.log(returndata);
        }
    });


});

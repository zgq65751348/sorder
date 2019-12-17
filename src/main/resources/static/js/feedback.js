/**
 * Created by ������Ѷ on 2017/1/14.
 */
var infolist=null, page= 1,row=13, count= 1, crtime=null, uptime=null,statu=null;
$().ready(function(){
    getinfo();
});
$("[ type='radio']").bind('change', function() {
    status=$(this).val();
    crtime=null;
    uptime=null;
    getinfo();
});
$("#topagebtn").click(function(){
    var topage=$("#topage").val();
    fbManager(topage);
});
$("#ready").click(function(){
    crtime =new Date( $("[name='crtime']").val()).getTime();
    if(!crtime){crtime=null}
    uptime=new Date( $("[name='uptime']").val()).getTime();
    if(!uptime){uptime=null}
    getinfo();
});
function getinfo(){
    var datalist={crtime:crtime,uptime:uptime,status:statu};
    console.log(datalist);
    datalist='jsonString='+JSON.stringify(datalist);
    console.log(datalist);
    $.ajax({
        url:  addressUrl+'/msg/getFBList',
        type: 'POST',
        data:datalist,
        dataType:'json',
        success: function (returndata) {
            console.log(returndata);
            if (returndata.status == "200") {
                infolist = returndata.data.feedbacks;
                fbManager(1);
            } else {
                alert(returndata.info);
            }

        },
        error: function (returndata) {
            alert("服务器错误，请稍后重试！");
        }
    });
}
function fbManager(p){
    count= Math.ceil(infolist.length/row);
    if(p<=0){ p=1;}else if(p>=count){p=count}
    page=p;
    console.log(count);
    var list = infolist.slice((p-1)*row,p*row);
    var liststr = "";
    for (var i = 0; i < list.length; i++) {
        liststr += '<tr><td>' + list[i].crtime+ '</td><td>' + list[i].uid + '</td><td style="max-width: 800px;overflow:hidden"><nobr>' + list[i].content + '</nobr></td><td><a  href="../ifram/fbinfo.html?fbid=' + list[i].fbid + '">查看详情</a><a href="javascript:;" class="dele" data-fbid="' + list[i].fbid + '">删除</a></td></tr>';
    }
    $("tbody").html(liststr);
    $(".first").html("当前页数："+page+"总页数："+count);

}
$("tbody").on("click",".dele",function(e){
    var fbid= e.target. getAttribute ('data-fbid') ;
    if(confirm("您确定要删除该反馈信息？")){
        $.ajax({
            url:  addressUrl+'/msg/delFB',
            type: 'POST',
            data:'jsonString={"fbid":"'+fbid+'"}',
            dataType:'json',
            success: function (returndata){
                console.log(returndata);
                if(returndata.status=="200"){
                    getinfo();
                } else {
                    alert(returndata.info);
                }
            },
            error: function (returndata) {
                alert("服务器错误，请稍后重试！");
            }
        });
    }
});
var infolist=null, page= 1, count= 1,row=13, crtime=null, uptime=null,statu=null;
$().ready(function(){
    getinfo();
});
$("[ type='radio']").bind('click', function() {
    statu=$(this).val();
    crtime=null;
    uptime=null;
    getinfo();
});
$("#topagebtn").click(function(){
    var topage=$("#topage").val();
    MsgList(topage);
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
        url:  addressUrl+'/msg/getMsgList',
        type: 'POST',
        data:datalist,
        dataType:'json',
        success: function (returndata) {
            console.log(returndata);
            if (returndata.status == "200") {
                infolist = returndata.data.sysMsgList;
                MsgList(1);
            } else {
                alert(returndata.info);
            }

        },
        error: function (returndata) {
            alert("服务器错误，请稍后重试！");
        }
    });
}
function MsgList(p){
    count= Math.ceil(infolist.length/row);
    if(p<=0){ p=1;}else if(p>=count){p=count}
    page=p;

    var list = infolist.slice((p-1)*row,p*row);
    var liststr = "";
    for (var i = 0; i < list.length; i++) {
        if(ist[i].push_platform==0){ list[i].push_platform="android+ios"}
        else if(ist[i].push_platform==1){ list[i].push_platform="ios"}
        else if(ist[i].push_platform==2){ list[i].push_platform="android"}
        liststr += '<tr><td>'+ list[i].crtime+ '</td><td>'+ list[i].push_platform+ '</td><td style="max-width: 300px;overflow:hidden"><nobr>' + list[i].title+ '</nobr></td><td style="max-width: 500px;overflow:hidden"><nobr>' + list[i].content + '</nobr></td><td><a  href="../ifram/sendmsg.html?mi d=' + list[i].mid + '">编辑发送</a><a href="javascript:;" class="dele" data-mid="' + list[i].mid + '">删除</a></td></tr>';
    }
    $("tbody").html(liststr);
    $(".first").html("当前页数："+page+"总页数："+count);

}
$("tbody").on("click",".dele",function(e){
    var mid= e.target. getAttribute ('data-mid') ;
    if(confirm("您确定要删除该推送信息？")){
        $.ajax({
            url:  addressUrl+'/msg/delMsg',
            type: 'POST',
            data:'jsonString={"mid":'+mid+',suid:'+window.sessionStorage.getItem("suid")+'}',
            dataType:'json',
            success: function (returndata){
                console.log(returndata);
                if(returndata.status=="200"){
                    alert(returndata.info);
                    getinfo();
                }else{
                    alert(returndata.info);
                }
            },
            error: function (returndata) {
                alert("服务器错误，请稍后重试！");
            }
        });
    }
});
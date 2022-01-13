layui.use(['form','jquery','jquery_cookie','layer'], function(){
    var form = layui.form;
    var layer = layui.layer;
    var $ = layui.jquery;
    var ck = layui.jquery_cookie($);


    //表单数据提交
    form.on('submit(login)',function (data) {
        //使用ajax
        $.ajax({
            type:"post",
            url:ctx+"/userController/userLogin",
            data:{
                "userName":data.field.userName,
                "userPwd":data.field.userPwd
            },
            dataType:"json",
            success:function (msg) {
                console.log(msg);
                //判断结果
                if (msg.code == 200) {
                    layer.msg("登录成功");
                    //存储cookie
                    ck.cookie("userIdStr",msg.result.id);
                    ck.cookie("userName",msg.result.userName);
                    ck.cookie("trueName",msg.result.trueName);
                    //页面跳转
                    window.location=ctx+"/main"

                    //判断是否选择记住我
                    if ($("input[type=checkbox]").is(":checked")) {
                        //存储cookie
                        ck.cookie("userIdStr",msg.result.id,{expires:7});
                        ck.cookie("userName",msg.result.userName,{expires:7});
                        ck.cookie("trueName",msg.result.trueName,{expires:7});
                    }
                }else {
                    layer.msg(msg.msg);
                }
            }
        })
        //阻止提交
        return false;
    });
});

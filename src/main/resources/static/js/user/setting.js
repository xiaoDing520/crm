layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    //表单数据提交
    form.on('submit(saveBtn)',function (data) {
        //使用ajax
        $.ajax({
            type:"post",
            url:ctx+"/userController/updateUser",
            data:{
                "userName":data.field.userName,
                "trueName":data.field.trueName,
                "email":data.field.email,
                "phone":data.field.phone,
                "id":data.field.id
            },
            dataType:"json",
            success:function (msg) {
                //判断结果
                if (msg.code == 200) {
                    layer.msg("修改成功，系统将在3秒后退出",function () {
                        //清除cookie
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"});
                    });

                    //页面跳转
                    window.parent.location=ctx+"/index"
                }else {
                    layer.msg(msg.msg);
                }
            }
        })
        //阻止提交
        return false;
    });

});
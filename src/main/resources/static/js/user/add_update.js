layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        formSelects = layui.formSelects;

    //设置监听事件
    form.on('submit(addOrUpdateUser)',function (data) {
        //定义url
        var url = ctx+"/userController/addUser";
        //判断 id 是否有值
        if ($("input[name=id]").val() != "" && $("input[name=id]").val() != undefined) {
            url = ctx+"/userController/updateUser2";
        }

        //使用ajax
        $.ajax({
            type:"post",
            url:url,
            data:{
                "id":data.field.id,
                "userName":data.field.userName,
                "trueName":data.field.trueName,
                "email":data.field.email,
                "phone":data.field.phone,
                "roleId":data.field.roleIds
            },
            dataType:"json",
            success:function (msg) {
                if (msg.code == 200) {
                    layer.msg("成功");
                    //刷新父页面，重新渲染表格数据
                    parent.location.reload();
                }else {
                    layer.msg(msg.msg);
                }
            }
        })
        //阻止提交
        return false;
    })

    //加载下拉框
    formSelects.config("selectId",{
        type: "post",
        searchUrl:ctx+"/roleController/selectRoleAll?userId="+$("input[name=id]").val(),
        keyName:"roleName",
        keyVal:"id"
    },true)

    //关闭按钮点击事件
    $(".layui-btn").click(function () {
        //先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        //再执行关闭
        parent.layer.close(index);
    });
});
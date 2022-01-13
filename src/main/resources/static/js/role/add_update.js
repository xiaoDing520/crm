layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        formSelects = layui.formSelects;

    //设置监听事件
    form.on('submit(addOrUpdateUser)',function (data) {
        //定义url
        var url = ctx+"/roleController/addRole";
        //判断 id 是否有值
        if ($("input[name=id]").val() != "" && $("input[name=id]").val() != undefined) {
            url = ctx+"/roleController/updateRole";
        }

        //使用ajax
        $.ajax({
            type:"post",
            url:url,
            data:{
                "id":data.field.id,
                "roleName":data.field.roleName,
                "roleRemark":data.field.roleRemark
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

    //关闭按钮点击事件
    $(".layui-btn").click(function () {
        //先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        //再执行关闭
        parent.layer.close(index);
    });
});
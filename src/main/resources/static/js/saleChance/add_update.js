layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //设置监听事件
    form.on('submit(addOrUpdateSaleChance)',function (data) {
        //定义url
        var url = ctx+"/saleChanceController/addSaleChance";
        //判断 id 是否有值
        if ($("input[name=id]").val() != "" && $("input[name=id]").val() != undefined) {
            url = ctx+"/saleChanceController/updateSaleChance";
        }

        //使用ajax
        $.ajax({
            type:"post",
            url:url,
            data:{
                "id":data.field.id,
                "customerName":data.field.customerName,
                "chanceSource":data.field.chanceSource,
                "linkMan":data.field.linkMan,
                "linkPhone":data.field.linkPhone,
                "assignMan":data.field.assignMan,
                "overview":data.field.overview,
                "cgjl":data.field.cgjl,
                "description":data.field.description
            },
            dataType:"json",
            success:function (msg) {
                if (msg.code == 200) {
                    layer.msg("成功");
                    //刷新父页面，重新渲染表格数据
                    parent.location.reload();
                }else {
                    layer.msg("失败");
                }
            }
        })
        //阻止提交
        return false;
    });

    //关闭按钮点击事件
    $(".layui-btn").click(function () {
        //先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        //再执行关闭
        parent.layer.close(index);
    });

    //获取指定人数据
    $.ajax({
        type:"post",
        url: ctx+"/userController/selectAssignMan",
        dataType: "json",
        success: function (data) {
            //获取指定人数据
            var man = $("input[name=man]").val();

            //循环遍历
            for (var x in data) {
                //判断是修改还是添加
                if (data[x].id==man) {
                    $("#assignMan").append("<option selected value='"+data[x].id+"'>"+data[x].user_name+"</option>");
                }else {
                    $("#assignMan").append("<option value='"+data[x].id+"'>"+data[x].user_name+"</option>");
                }
                //重新渲染下拉框
                layui.form.render("select");
            }
        }
    })
});
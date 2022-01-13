$(function () {
    //定义数的变量
    var zTreeobj;

    //定义树中按钮，选择取消的监听事件
    function zTreeOnCheck(event, treeId, treeNode) {
        //获取所有被选中的资源
        var nodes = zTreeobj.getCheckedNodes(true);
        //定义数组
        var arry = [];
        console.log(nodes[1].id);
        //循环添加
        for (var x in nodes) {
            arry.push(nodes[x].id);
        }
        //定义ajax发送数据
        $.ajax({
            type: "post",
            url: ctx+"/roleController/addRoleModel",
            data: {
                "roleId":$("#roleId").val(),
                "modelId":arry.toString()
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    alert("添加成功");
                }
            }
        })
    }
    //定义配置
    var setting = {
        //设置
        callback: {
            onCheck: zTreeOnCheck
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    
    //ajax 获取数据
    $.ajax({
        type:"post",
        url:ctx+"/modelController/selectModelAll",
        data:{
            "roleId":$("#roleId").val()
        },
        dataType:"json",
        success:function (data) {
            //加载 ztrre树
            zTreeobj = $.fn.zTree.init($("#ztree"), setting, data);
        }
    })

});
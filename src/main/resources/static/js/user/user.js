layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * 用户列表展示
     */
        //数据加载
    var tableIns = table.render({
            elem: '#userList'
            ,url:ctx+'/userController/list'
            ,cellMinWidth: 95
            ,toolbar:"#toolbarDemo"
            ,page:true
            ,height:"full-125"
            ,limit:[10,15,20,25]
            ,cols: [[
                {type: "checkbox", fixed:"left",width:50}
                ,{field:'id', title: '编号',fixed: "true"}
                ,{field:'userName', title: "用户名",align:"center"}
                ,{field:'trueName', title: '真实姓名',align:"center"}
                ,{field:'email', title: '邮箱',align:"center"}
                ,{field:'phone', title: '联系电话', align:"center"}
                ,{field:'createDate', title: '创建时间', align:"center"}
                ,{field:'updateDate', title: '修改时间',align:"center"}
                ,{title: '操作',templet:'#userListBar',align:"center",fixed: "right",minWidth:125}
            ]]
        });


    //点击事件
    $(".search_btn").click(function () {
        //表格重载
        tableIns.reload({
            where: {
                userName:$("input[name=userName]").val(),
                email:$("input[name=email]").val(),
                phone:$("input[name=phone]").val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

    //添加头部工具栏
    table.on('toolbar(users)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                //加载页面
                openAddOrUpdateUser();
                break;
            case 'del':
                //调用删除函数
                deleteParams(checkStatus.data);
                break;
        };
    });
    //添加行内工具栏
    table.on('tool(users)', function(obj){
        var data = obj.data;
        var layEvent = obj.event;
        var tr = obj.tr;


        if(layEvent === 'del'){ //删除
            //询问框
            layer.confirm("您确定要删除选中的记录吗？",{
                btn:["确认","取消"],
            },function (index) {
                //关闭弹出层
                layer.close(index);

                //ajax请求
                $.ajax({
                    type:"post",
                    url:ctx+"/userController/deleteParams",
                    dataType:"json",
                    data:{
                        "id":data.id
                    },
                    success:function (msg) {
                        if (msg.code == 200) {
                            layer.msg("删除成功");
                            //重新加载表格
                            tableIns.reload();
                        }else {
                            layer.msg("删除失败");
                        }
                    }
                })

            })
        } else if(layEvent === 'edit'){ //编辑
            openAddOrUpdateUser(data.id);
        }
    });

    //定义添加和修改函数
    function openAddOrUpdateUser(id) {
        var title="<h3>用户模块-添加</h3>";
        var url=ctx+"/userController/addOrUpdate";

        //判断是否为空
        if (id!=undefined && id!="") {
            title="<h3>用户模块-修改</h3>";
            url=ctx+"/userController/addOrUpdate?id="+id;
        }

        /*弹出层*/
        layui.layer.open({
            title:title,
            content:url,
            type:2,//ifream
            area:["500px","400px"],
            maxmin:true
        })
    }

    //定义删除函数
    function deleteParams(data) {
        //询问框
        layer.confirm("您确定要删除选中的记录吗？",{
            btn:["确认","取消"],
        },function (index) {
            //关闭弹出层
            layer.close(index);
            //判断要删除的行
            if (data.length == 0) {
                layer.msg("请选择要删除的数据");
                return;
            }
            //定义数据
            var arry = [];
            //循环遍历数据
            for (var x in data) {
                arry.push(data[x].id);
            }
            //ajax请求
            $.ajax({
                type:"post",
                url:ctx+"/userController/deleteParams",
                dataType:"json",
                data:{
                    "id":arry.toString()
                },
                success:function (msg) {
                    if (msg.code == 200) {
                        layer.msg("删除成功");
                        //重新加载表格
                        tableIns.reload();
                    }else {
                        layer.msg("删除失败");
                    }
                }
            })
        })
    }
});
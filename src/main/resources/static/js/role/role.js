layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * 用户列表展示
     */
        //数据加载
    var tableIns = table.render({
            elem: '#roleList'
            ,url:ctx+'/roleController/list'
            ,cellMinWidth: 95
            ,toolbar:"#toolbarDemo"
            ,page:true
            ,height:"full-125"
            ,limit:[10,15,20,25]
            ,cols: [[
                {type: "checkbox", fixed:"left",width:50}
                ,{field:'id', title: '编号',fixed: "true"}
                ,{field:'roleName', title: "角色名称",align:"center"}
                ,{field:'roleRemark', title: '角色备注',align:"center"}
                ,{field:'createDate', title: '创建时间',align:"center"}
                ,{field:'updateDate', title: '修改时间', align:"center"}
                ,{title: '操作',templet:'#userListBar',align:"center",fixed: "right",minWidth:125}
            ]]
        });


    //点击事件
    $(".search_btn").click(function () {
        console.log($("input[name=roleName]").val());
        //表格重载
        tableIns.reload({
            where: {
                roleName:$("input[name=roleName]").val(),
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

    //添加头部工具栏
    table.on('toolbar(roles)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                //加载页面
                openAddOrUpdateUser();
                break;
            case 'mandate':
                //加载角色授权页面
                openRoleModel(checkStatus.data);
                break;
        };
    });
    //添加行内工具栏
    table.on('tool(roles)', function(obj){
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
                    url:ctx+"/roleController/deleteParams",
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
        var title="<h3>角色模块-添加</h3>";
        var url=ctx+"/roleController/addOrUpdate";

        //判断是否为空
        if (id!=undefined && id!="") {
            title="<h3>角色模块-修改</h3>";
            url=ctx+"/roleController/addOrUpdate?id="+id;
        }

        /*弹出层*/
        layui.layer.open({
            title:title,
            content:url,
            type:2,//ifream
            area:["500px","220px"],
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
                url:ctx+"/roleController/deleteParams",
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

    //定义加载授权页面的函数
    function openRoleModel(data) {
        //判断要删除的行
        if (data.length == 0 || data.length > 1) {
            layer.msg("只能选择一个角色");
            return;
        }

        /*弹出层*/
        layui.layer.open({
            title:"角色授权",
            content:ctx+"/roleController/roleMandate?roleId="+data[0].id,
            type:2,//ifream
            area:["500px","220px"],
            maxmin:true
        })

    }
});
layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ?
        layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //数据加载
    var tableIns = table.render({
        elem: '#saleChanceList'
        ,url:ctx+'/saleChanceController/list'
        ,cellMinWidth: 95
        ,toolbar:"#toolbarDemo"
        ,page:true
        ,height:"full-125"
        ,limit:[10,15,20,25]
        ,cols: [[
            {type: "checkbox", fixed:"center"}
            ,{field:'id', title: '编号',fixed: "true"}
            ,{field:'chanceSource', title: '机会来源',align:"center"}
            ,{field:'customerName', title: '客户名称', align:"center"}
            ,{field:'cgjl', width:80, title: '成功几率',align:"center"}
            ,{field:'overview', title: '概要',align:"center" }
            ,{field:'linkMan', title: '联系人', align:"center"}
            ,{field:'linkPhone', title: '练习电话', align:"center"}
            ,{field:'description', title: '描述',align:"center"}
            ,{field:'createMan', title: '创建人',align:"center"}
            ,{field:'assignMan', title: '指定人',align:"center"}
            ,{field:'createDate', title: '分配时间', align:"center"}
            ,{field:'state', title: '分配状态', align:"center",templet:function (d) {
                return formatterState(d.state);
            }}
            ,{field:'devResult', title: '开发状态', align:"center",templet:function (d) {
                return formatterDevResult(d.devResult);
            }}
            ,{title: '操作', templet:'#saleChanceListBar',align:"center",fixed: "right",minWidth:125}
        ]]
    });

    /**
     * 格式化分配状态
     * 0 - 未分配
     * 1 - 已分配
     * 其他 - 未知
     * @param state
     * @returns {string}
     */
    function formatterState(state){
        if(state==0) {
            return "<div style='color: yellow'>未分配</div>";
        } else if(state==1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    /**
     * 格式化开发状态
     * 0 - 未开发
     * 1 - 开发中
     * 2 - 开发成功
     * 3 - 开发失败
     * @param value
     * @returns {string}
     */
    function formatterDevResult(value){
        if(value == 0) {
            return "<div style='color: yellow'>未开发</div>";
        } else if(value==1) {
            return "<div style='color: #00FF00;'>开发中</div>";
        } else if(value==2) {
            return "<div style='color: #00B83F'>开发成功</div>";
        } else if(value==3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }

    //点击事件
    $(".search_btn").click(function () {
        //表格重载
        tableIns.reload({
            where: {
                customerName:$("input[name=customerName]").val(),
                createMan:$("input[name=createMan]").val(),
                state:$("#state").val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        })
    });

    //头部工具栏点击事件
    //触发事件
    table.on('toolbar(saleChances)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                //加载页面
                openAddOrUpdateSaleChanceDialog();
                break;
            case 'del':
                //调用删除函数
                deleteParams(checkStatus.data);
                break;
        };
    });

    //打开添加页面的函数
    function openAddOrUpdateSaleChanceDialog(id) {
        var title="<h3>营销机会-添加</h3>";
        var url=ctx+"/saleChanceController/addOrUpdate";
        //判断是否为空
        if (id!=undefined && id!="") {
            title="<h3>营销机会-修改</h3>";
            url=ctx+"/saleChanceController/addOrUpdate?id="+id;
        }

        /*弹出层*/
        layui.layer.open({
            title:title,
            content:url,
            type:2,//ifream
            area:["500px","620px"],
            maxmin:true
        })
    }

    //行内工具栏点击事件
    //工具条事件
    table.on('tool(saleChances)', function(obj){
        var data = obj.data;
        var layEvent = obj.event;
        var tr = obj.tr;


        if(layEvent === 'del'){ //删除
            console.log("删除");
            //询问框
            layer.confirm("您确定要删除选中的记录吗？",{
                btn:["确认","取消"],
            },function (index) {
                //关闭弹出层
                layer.close(index);

                //ajax请求
                $.ajax({
                    type:"post",
                    url:ctx+"/saleChanceController/deleteParams",
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
            openAddOrUpdateSaleChanceDialog(data.id);
        }
    });

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
                url:ctx+"/saleChanceController/deleteParams",
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

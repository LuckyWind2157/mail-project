
var pageCurr;
var tree;
layui.use(["table","tree"],function(){
    var table = layui.table;
     tree = layui.tree;
    form = layui.form;
    form.render();
    tableIns=table.render({
        elem:"#roleTable"
        ,url: '/tbRole/getList/' //数据接口
        ,cols: [[ //表头
            {field: 'id', title: 'ID',sort: true}
            ,{field: 'name', title: '角色名称', }
            ,{field: 'remake', title: '角色备注', }
            ,{field: 'createTime', title: '创建时间', sort: true}
            ,{field: 'updateTime', title: '修改时间', sort: true}
            ,{field: 'updateUser', title: '修改人', sort: true}
            ,{fixed:'right',title:'操作',align:'center', width:200, toolbar:'#barDemo'}
        ]],
        done: function(res, curr, count){
            pageCurr=curr;
        }

    });


    //监听工具条
    table.on('tool(roleTable)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除
            delRole(data,data.id);
        } else  if(obj.event === 'edit'){
            //编辑
            edit(data);
        }else  if(obj.event === 'permission'){

            permission(data);
        }
    });
    //监听提交
    form.on('submit(roleSubmit)', function(data){
        formSubmit(data);
        return false;
    });

    form.on('submit(permissionSubmit)', function(data){
        permissionSubmit(data);
        return false;
    });

});

function permissionSubmit(data) {
    var treeData = tree.getChecked("menuTree");
    var menuIds=[];
    initTreeData(treeData,menuIds);
    var obj={};
    obj.menuIds = menuIds;
    obj.roleId = $("#roleId").val();
    AsyncPost("/tbRoleMenu/permissionSubmit",obj,function (data) {
        layer.alert(data.message,function(){
            layer.closeAll();
            load(obj);
        });
    });
}

function initTreeData(treeData,menuIds) {
    for (var i=0;i<treeData.length;i++){
        menuIds.push(treeData[i].id);
        if(treeData[i].children.length>0){
            for(var j=0;j<treeData[i].children.length;j++){
                menuIds.push(treeData[i].children[j].id);
            }
        }
    }
}

//提交表单
function formSubmit(obj){
    var type;
    if($("#id").val()=="") type = "post";else type = "put";
    AsyncAjax(type,"/tbRole/setRole",$("#roleForm").serialize(),function (data) {
        if (data.code == 0) {
            layer.alert(data.message,function(){
                layer.closeAll();
                load(obj);
            });
        } else {
            layer.alert(data.message);
        }
    },function (data) {
        layer.alert("系统错误");
    });
}

//新增
function add() {
    cleanRole();
    edit(null,"新增");
}

//打开编辑框
function edit(data,title){
    if(data == null){
        $("#id").val("");
    }else{
        //回显数据
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#remake").val(data.remake);
        form.render();
    }
    var obj = $('#setRole');
    openForm(title,true,false,true,['400px','400px'],obj);
}


function permission(data) {
    $("#roleName").val(data.name);
    $("#roleId").val(data.id);
    var obj = $('#setPermission');
    openForm("权限设置",true,false,true,['400px','400px'],obj);
    AsyncPost("/tbMenu/getPermission",{"id":data.id},function (data) {
            var list = [];
            var obj = data.data;
            console.log(data);
            for(var i=0;i<obj.length;i++){
                if(obj[i].children.length!=0){
                    list.push(obj[i]);
                }
            }
        tree.render({
                elem:"#permissionTree",
                showCheckbox:true,
                data:list,
                id:"menuTree",
                /*click:function(obj){
                    var da = obj.data;
                    layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(da));
                },*/
            });
    })
}


//置空
function cleanRole() {
    $("#name").val("");
    $("#remake").val("");
}
//删除
function delRole(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            AsyncDelete("/tbRole/delete",{"id":id},function(req){
                layer.alert(req.message,function(){
                    layer.closeAll();
                    load(obj);
                });
            });

        }, function(){
            layer.closeAll();
        });
    }
}

//重新加载table
function load(obj){
    tableIns.reload({
        page: {
            curr: pageCurr //从当前页码开始
        }
    });
}


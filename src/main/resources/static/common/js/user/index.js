
var pageCurr=1;
var tableIns;
var pageSize;
var form;
var table;

//数据导出
/*$("#btn_export").on("click", function () {
    AsyncGet("/excel/export",null,function (data) {
        layer.alert("导出成功")
    });
});*/

function getUserByName() {
    var name = $("#search").val();
    AsyncPost("/tbUser/getUserByName/",{"name":name},function (data) {
        initTableByData(data.data);

    })
}
function changeImg() {
    $("#img1").attr("src","common/images/kaishun.jpg");
}

function initTableByData(obj){
    tableIns=table.render({
        elem:"#userTable"
        ,data:obj
        ,cols: [[ //表头
            {field: 'id', title: 'ID',sort: true}
            ,{field: 'userName', title: '用户名', }
            ,{field: 'phone', title: '手机号', }
            ,{field: 'sex', title: '性别', sort: true,templet:"#handleSex"}
            ,{field: 'age', title: '年龄', sort: true}
            ,{field: 'createTime', title: '创建时间', sort: true}
            ,{field: 'updateTime', title: '修改时间', sort: true}
            ,{field: 'updateUser', title: '修改人', sort: true}
            ,{fixed:'right',title:'操作',align:'center', width:200, toolbar:'#barDemo'}
        ]],
        done: function(res, curr, count){
            pageCurr=curr;

        }

    });
}

layui.use(["table"],function(){
    table = layui.table;
    form = layui.form;
    form.render();
    //initTable(table,"/tbUser/getList/");
    AsyncPost("/tbUser/getList/",{"pageNum":pageCurr,"pageSize":10},function (data) {
        initTableByData(data.data);
    })


    //监听工具条
    table.on('tool(userTable)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            //删除
            delUser(data,data.id);
        } else  if(obj.event === 'edit'){
            //编辑
            edit(data);
        }
    });
    //监听提交
    form.on('submit(userSubmit)', function(data){
        formSubmit(data);
        return false;
    });

});


//提交表单
function formSubmit(obj){
    var type;
    if($("#id").val()=="") type = "post";else type = "put";
    AsyncAjax(type,"/tbUser/setUser",$("#userForm").serialize(),function (data) {
        if (data.code == 0) {
            layer.alert(data.message,function(){
                layer.closeAll();
                load(obj);
            });
        } else {
            layer.alert(data.message);
        }
    });
}

//新增
function add() {
    edit(null,"新增");
}

//打开编辑框
function edit(data,title){
    if(data == null){
        $("#id").val("");
    }else{
        //回显数据
        $("#id").val(data.id);
        $("#userName").val(data.userName);
        $("#age").val(data.age);
        $("#phone").val(data.phone);
        $("#sex").val(data.sex);
        form.render();
    }
    var obj = $('#setUser');
    openForm(title,true,false,true,['400px','400px'],obj);
}

//置空
function cleanUser() {
    $("#userName").val("");
    $("#age").val("");
}
//删除
function delUser(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            AsyncDelete("/tbUser/delete",{"id":id},function(req){
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
    AsyncPost("/tbUser/getList/",{"pageNum":pageCurr,"pageSize":10},function (data) {
        initTableByData(data.data);
    })
}



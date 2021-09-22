let pageCurr;
let tableIns;
let form;
let table;
layui.use(["table"], function () {
    table = layui.table;
    form = layui.form;
    form.render();
    initTable(table, "/menu/findByPage");
    //监听工具条
    table.on('tool(menuTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            //删除
            delMenu(data, data.id);
        } else if (obj.event === 'edit') {
            //编辑
            edit(data);
        }
    });
    //监听提交
    form.on('submit(menuSubmit)', function (data) {
        formSubmit(data);
        return false;
    });
});

function getMenuByName() {
    const name = $("#menuSearch").val();
    initTable(table, "/menu/findByPage", {"name": name});

}

function initTable(table, url, data) {
    tableIns = table.render({
        elem: "#menuTable"
        , url: url //数据接口
        , page: true //开启分页
        , where: data
        , cols: [[ //表头
            {field: 'id', title: 'ID', sort: true}
            , {field: 'code', title: '编号',}
            , {field: 'parentCode', title: '上级编号',}
            , {field: 'name', title: '菜单名称',}
            , {field: 'remake', title: '备注信息', sort: true}
            , {field: 'url', title: '路径', sort: true}
            , {field: 'createdTime', title: '创建时间', sort: true}
            , {field: 'updatedTime', title: '修改时间', sort: true}
            , {field: 'updateUser', title: '修改人', sort: true}
            , {fixed: 'right', title: '操作', align: 'center', width: 200, toolbar: '#barDemo'}
        ]],
        done: function (res, curr, count) {
            pageCurr = curr;
        }
    });
}


//提交表单
function formSubmit(obj) {
    debugger
    AsyncAjax("post", "/menu/saveOrUpdate", $("#menuForm").serialize(), function (data) {
        if (data.code == 0) {
            layer.alert(data.message, function () {
                layer.closeAll();
                load(obj);
            });
        } else {
            layer.alert(data.message);
        }
    }, function (data) {
        layer.alert("系统错误");
    });
}

//新增
function add() {
    cleanMenu();
    edit(null, "新增");
}

//打开编辑框
function edit(data, title) {
    if (data == null) {
        $("#id").val("");
    } else {
        //回显数据
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#code").val(data.code);
        $("#remake").val(data.remake);
        $("#url").val(data.url);
        $("#parentCode").val(data.parentCode);
        form.render();
    }
    var obj = $('#setMenu');
    openForm(title, true, false, true, ['600px', '450px'], obj);
}

//置空
function cleanMenu() {
    $("#id").val("");
    $("#name").val("");
    $("#code").val("");
    $("#remake").val("");
    $("#url").val("");
    $("#parentCode").val("");
}

function delMenu(obj, id) {
    if (null != id) {
        layer.confirm('您确定要删除吗？', {
            btn: ['确认', '返回'] //按钮
        }, function () {
            AsyncDelete("/menu/del", {"id": id}, function (req) {
                layer.alert(req.message, function () {
                    layer.closeAll();
                    load(obj);
                });
            });

        }, function () {
            layer.closeAll();
        });
    }
}

//重新加载table
function load(obj) {
    tableIns.reload({
        page: {
            curr: pageCurr //从当前页码开始
        }
    });
}



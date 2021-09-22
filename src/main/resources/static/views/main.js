let element;
let $;
let layer;
layui.use(['element', 'jquery', 'layer'], function () {
    element = layui.element;
    $ = layui.jquery;
    layer = layui.layer;
    $("a").click(function () {
        let url = $(this).data("href");
        $("#content").load(url);
    })
});

function openForm(title, fixed, resize, shadeClose, area, obj) {
    layer.open({
        type: 1,
        title: title,
        fixed: fixed,
        resize: resize,
        shadeClose: shadeClose,
        area: area,
        content: obj,
        success: function (layero) {
            //此处会弹出一个遮罩层（还不知道原因），所以先把他hide掉(我暂时没找到更好的方法)。
            var mask = $(".layui-layer-shade");
            mask.hide();
        },
        end: function () {
            obj.css("display", 'none');
            layer.closeAll();
        }
    });
}

function ajax(url, method, data, isAsync, success_callback, error_callback) {
    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + localStorage.token);
        }
    });
    var header = {
        url: url,
        data: data,
        type: method,
        dataType: 'json',
        async: isAsync,
        traditional: true
    }
    if (data instanceof FormData) $.extend(header, {contentType: false, processData: false, cache: false})
    $.ajax($.extend(header, {
        success: function (result) {
            if (result.code != 0) {
                if (result.code == 100 || result.code == 401 || result.code == 402) {
                    localStorage.removeItem("token");
                    window.location.href = "/"
                } else {
                    layer.alert(result.message, {icon: 2});
                }
            } else {
                if (success_callback) success_callback(result);
            }
        },
        error: function (result) {
            error_callback(result);
        }
    }))
}

//异步
function AsyncPost(url, data, success_callback, error_callback) {
    ajax(url, "post", data, true, success_callback, error_callback);
}

function AsyncGet(url, data, success_callback, error_callback) {
    ajax(url, "get", data, true, success_callback, error_callback);
}

function AsyncPut(url, data, success_callback, error_callback) {
    ajax(url, "put", data, true, success_callback, error_callback);
}

function AsyncDelete(url, data, success_callback, error_callback) {
    ajax(url, "delete", data, true, success_callback, error_callback);
}

function AsyncAjax(type, url, data, success_callback, error_callback) {
    ajax(url, type, data, true, success_callback, error_callback);
}


//同步
function SyncPost(url, data, success_callback, error_callback) {
    ajax(url, "post", data, false, success_callback, error_callback);
}

function SyncGet(url, data, success_callback, error_callback) {
    ajax(url, "get", data, false, success_callback, error_callback);
}

function AsyncPut(url, data, success_callback, error_callback) {
    ajax(url, "put", data, false, success_callback, error_callback);
}

function SyncDelete(url, data, success_callback, error_callback) {
    ajax(url, "delete", data, false, success_callback, error_callback);
}

function SyncAjax(type, url, data, success_callback, error_callback) {
    ajax(url, type, data, false, success_callback, error_callback);
}

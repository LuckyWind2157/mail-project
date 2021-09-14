/**
 * 登录
 */
$(function(){
    layui.use(['form' ,'layer','tree'], function() {
         form = layui.form;
        var layer = layui.layer;
        var tree = layui.tree;
        form.on("submit(login)",function (){
            login();
            return false;
        });
        //监听提交
        form.on('submit(userRegister)', function(data){
            formSubmit(data);
            return false;
        });
        form.on('submit(forgetPassword)', function(data){
            forgetPasswordSubmit(data);
            return false;
        });
    })
})



function login(){
    var username=$("#userName").val();
    var password=$("#userPassword").val();

    AsyncPost("/tbUser/login",$("#useLogin").serialize(),function(data){
        if(data.code == 0){
            window.location.href=data.url;
            localStorage.token=data.data;
        }else{
            layer.alert(data.message);
        }
    },function (data) {
        layer.alert("系统错误");
    });
}

function register() {
    var obj = $("#registerUser");
    openForm("注册用户",true,false,true,['700','400'],obj);
}

function forgetPassword() {
    var obj = $("#forgetPassword");
    openForm("忘记密码",true,false,true,['700','400'],obj);
}

function forgetPasswordSubmit() {
    AsyncPost("/tbUser/forgetPassword",$("#forgetPasswordForm").serialize(),function (data) {
        layer.alert(data.message,function(){
            layer.closeAll();
            cleanUser();
        });
    },function (data) {
        layer.alert(data.message);
    });
}


//提交表单注册用户
function formSubmit(obj){

    AsyncPost("/tbUser/registerUser",$("#registerForm").serialize(),function (data) {
            layer.alert(data.message,function(){
                layer.closeAll();
                cleanUser();
            });
    },function (data) {
        console.log(data);
        layer.alert(data.message);
    });
}
/*置空表单*/
function cleanUser() {
    $("#registerName").val("");
    $("#age").val("");
    $("#phone").val("");
    $("#verificationCode").val("");
    $("#password").val("");
}


/*获取验证码*/
function getVerificationCode(type) {
    var registerPhone = $("#phone").val();
    var forgetPasswordPhone = $("#forgetPasswordPhone").val();
    var data;
    type =="register"?data={"phone":$("#phone").val()}:data= {"phone":$("#forgetPasswordPhone").val()};
    sendSmsCode(data);
}


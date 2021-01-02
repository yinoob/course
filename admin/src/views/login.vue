<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1 class="blue" id="id-company-text">松哥课堂后台管理</h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入用户名密码
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.loginName" type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
                        </label>

                        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input v-model="user.password" type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input v-model="remember" type="checkbox" class="ace" />
                            <span class="lbl"> 记住我</span>
                          </label>

                          <button type="button" class="width-35 pull-right btn btn-sm btn-primary"
                                  v-on:click="login()">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>
                        <div class="space-4"></div>
                      </fieldset>
                    </form>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.login-box -->


            </div><!-- /.position-relative -->

            <div class="navbar-fixed-top align-right">
              <br />
              &nbsp;
              <a id="btn-login-dark" href="#">Dark</a>
              &nbsp;
              <span class="blue">/</span>
              &nbsp;
              <a id="btn-login-blur" href="#">Blur</a>
              &nbsp;
              <span class="blue">/</span>
              &nbsp;
              <a id="btn-login-light" href="#">Light</a>
              &nbsp; &nbsp; &nbsp;
            </div>
          </div>
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.main-content -->
  </div><!-- /.main-container -->
</template>
<script>
  import axios from "axios";
  import qs from "qs";

  export default {
    name: "login",
    data: function () {
      return {
        user: {},
        users: [],
        remember: true
      }
    },
    mounted: function () {
      let _this=this;
      $("body").removeClass("no-skin");
      $("body").attr("class", "login-layout light-login");

      let rememberUser=LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if(rememberUser){
        _this.user=rememberUser;
      }
    },
    methods: {

      login() {
        let _this = this;
        //let passwordShow=_this.user.password;
        //如果密码是从缓存带出来的,则不需要加密
        let md5=hex_md5(_this.user.password);
        let rememberUser=LocalStorage.get(LOCAL_KEY_REMEMBER_USER)||{};
        if(md5!==rememberUser.md5){
          _this.user.password=hex_md5(_this.user.password+KEY);
        }

        //_this.user.password=hex_md5(_this.user.password+KEY);
        axios.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', qs.stringify(_this.user)).then((response) => {
          //Loading.hide();
          let resp = response.data;
          if (resp.success) {
            console.log("登录成功",resp.content);
            let loginUser=resp.content;
            Tool.setLoginUser(resp.content);
            //这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
            if(_this.remember){

              let md5=hex_md5(_this.user.password);
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER,{
                loginName: loginUser.loginName,
                password: _this.user.password,
                md5: md5,
              });
            }else{
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER,null);
            }

            _this.$router.push("/welcome")
          } else {
            Toast.warning(resp.message)
          }
        });
      },
    }
  }
</script>
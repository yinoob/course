<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>手机号</th>
        <th>验证码</th>
        <th>用途</th>
        <th>生成时间</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="sms in smss">
        <td>{{sms.id}}</td>
        <td>{{sms.mobile}}</td>
        <td>{{sms.code}}</td>
        <td>{{SMS_USE | optionKV(sms.used)}}</td>
        <td>{{sms.at}}</td>
        <td>{{SMS_STATUS | optionKV(sms.status)}}</td>
      <td>
        <div class="hidden-sm hidden-xs btn-group">
          <button v-on:click="edit(sms)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          <button v-on:click="del(sms.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>
        </div>
      </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">手机号</label>
                <div class="col-sm-10">
                  <input v-model="sms.mobile" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">验证码</label>
                <div class="col-sm-10">
                  <input v-model="sms.code" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">用途</label>
                <div class="col-sm-10">
                  <select v-model="sms.used" class="form-control">
                    <option v-for="o in SMS_USE" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">生成时间</label>
                <div class="col-sm-10">
                  <input v-model="sms.at" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                  <select v-model="sms.status" class="form-control">
                    <option v-for="o in SMS_STATUS" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
  import Pagination from "../../components/pagination";
  import axios from 'axios'
  import qs from 'qs'

  export default {
    components: {Pagination},
    name: "business-sms",
    data: function() {
      return {
        sms: {},
        smss: [],           //smss: [],
        SMS_USE: SMS_USE,
        SMS_STATUS: SMS_STATUS,
      }
    },
    mounted: function() {
      let _this = this;
      _this.$refs.pagination.size = 5;
      _this.list(1);
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-sms-sidebar");

    },
    methods: {
      /**
       * 点击【新增】
       */
      add() {
        let _this = this;
        _this.sms = {};
        $("#form-modal").modal("show");
      },

      /**
       * 点击【编辑】
       */
      edit(sms) {
        let _this = this;
        _this.sms = $.extend({}, sms);
        $("#form-modal").modal("show");
      },

      /**
       * 列表查询
       */
      list(page) {
        let _this = this;
        //Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/business/admin/sms/list', qs.stringify({
          page: page,
          size: _this.$refs.pagination.size,
        },),{emulateJSON:true})
                .then((response)=>{
                  //Loading.hide();
          let resp = response.data;
          _this.smss = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);

        })
      },

      /**
       * 点击【保存】
       */
      save() {
        let _this = this;

        // 保存校验
        if (1 != 1
          || !Validator.require(_this.sms.mobile, "手机号")
          || !Validator.length(_this.sms.mobile, "手机号", 1, 50)
          || !Validator.require(_this.sms.code, "验证码")
          || !Validator.require(_this.sms.used, "用途")
          || !Validator.require(_this.sms.at, "生成时间")
          || !Validator.require(_this.sms.status, "状态")
        ) {
          return;
        }

        //Loading.show();
        axios.post(process.env.VUE_APP_SERVER + '/business/admin/sms/save', qs.stringify(_this.sms)).then((response)=>{
          //Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },

      /**
       * 点击【删除】
       */
      del(id) {
        let _this = this;
        Confirm.show("删除短信验证码后不可恢复，确认删除？", function () {
          //Loading.show();
          axios.post(process.env.VUE_APP_SERVER + '/business/admin/sms/delete',qs.stringify({id: id})).then((response)=>{
            //Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      }
    }
  }
</script>
<template>
  <main role="main">

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row">
          <div  class="col-md-12">
            <pagination ref="pagination" v-bind:list="listCourse"></pagination>
          </div>
      </div>
        <br>
          <div class="row">
          <div v-for="o in courses" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
          <h3 v-show="courses.length === 0">课程还未上架</h3>
        </div>
      </div>
    </div>
  </main>

</template>

<script>

  import TheCourse from "../components/the-course";
  import axios from 'axios';
  import qs from 'qs';
  import Pagination from "../components/pagination";
  import TheHeader from "../components/the-header";
  export default {
    components: {TheHeader, Pagination, TheCourse},
    name: 'list',
    data: function () {
      return {
        courses: [],

      }
    },
    mounted() {
      let _this = this;
      _this.$refs.pagination.size=2;
      _this.listCourse(2);

    },
    methods: {
      /**
       * 查询课程列表
      */
      listCourse(page) {
        let _this = this;

        // 新上好课不经常变，又经常被访问，适合用缓存
        // 判断是否有缓存
       // let news = SessionStorage.get("news");
       // if (!Tool.isEmpty(news)) {
       //   _this.news = news;
       //   return;
       // }

        axios.post(process.env.VUE_APP_SERVER + '/business/web/course/list',
        qs.stringify({
          page: page,
          size: _this.$refs.pagination.size,
        })).then((response)=>{
          console.log("查询课程列表结果：", response);
          let resp = response.data;
          if (resp.success) {
            _this.courses = resp.content.list;
            _this.$refs.pagination.render(page,resp.content.total);
            // 保存到缓存
           // SessionStorage.set("news", _this.news);
          }
        }).catch((response)=>{
          console.log("error：", response);
        })
      },

    }
  }
</script>

<style>

</style>

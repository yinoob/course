<template>
  <main role="main">

    <div class="album py-5 bg-light">
      <div class="container">
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
  export default {
    components: {TheCourse},
    name: 'list',
    data: function () {
      return {
        courses: [],
      }
    },
    mounted() {
      let _this = this;
      _this.listCourse(1);
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
          size: 3,
        })).then((response)=>{
          console.log("查询课程列表结果：", response);
          let resp = response.data;
          if (resp.success) {
            _this.courses = resp.content.list;
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

  .title1{
    margin-bottom: 2rem;
    color: #fafafa;
    letter-spacing: 0;
    text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
    font-size: 2rem;
  }
  .title2{
    margin-bottom: 2rem;
    color: transparent;
    -webkit-text-stroke: 1px black;
    letter-spacing: 0.04em;
    font-size: 2rem;
  }

</style>

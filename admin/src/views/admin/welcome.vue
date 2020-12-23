<template>
  <div>
    <h1>你好</h1>
  </div>
</template>
<script>
  export default {
    name: "welcome",
    mounted: function() {
      $("body").removeClass("login-layout light-login");
      $("body").attr("class","noskin");
    },
    methods:{
      login(){
        this.$router.push("/admin")
      }
    }
  }
</script>


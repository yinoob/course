<template>
  <div>
    <h1>你好，欢迎！</h1>
  </div>
</template>
<script>
  export default {
    name: "welcome",
    mounted: function() {
      $("body").removeClass("login-layout light-login");
      $("body").attr("class","noskin");
    },
  }
</script>


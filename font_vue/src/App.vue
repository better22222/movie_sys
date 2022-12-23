<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
</script>

<style>
  /*这里的内容，就是静态工程中的 reset.css*/
  html,
  body,
  div,
  span,
  h1,
  h2,
  h3,
  h4,
  h5,
  h6,
  ul,
  ol,
  li,
  p {
    margin: 0;
    padding: 0;
    font-family: '微软雅黑';
  }
  html,
  body,
  #app {
    /*必须要设置，这样总容器高度才能100%*/
    width: 100%;
    height: 100%;
  }
  ul,
  ol {
    list-style: none;
  }
  a {
    text-decoration: none;
  }
</style>

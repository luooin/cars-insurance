<template>
    <div class="header-container">
        <Collapse></Collapse>
        <bread-crumb></bread-crumb>
    </div>
    <LoginOut></LoginOut>
</template>
<script setup lang="ts">
import LoginOut from "./LoginOut.vue";
import Collapse from "./Collapse.vue";
import BreadCrumb from "./BreadCrumb.vue";
</script>
<style scoped lang="scss">
.header-container {
  display: flex;
  align-items: center;
}
</style>

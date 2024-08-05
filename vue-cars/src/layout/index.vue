<template>
        <el-container class="layout">
            <el-aside width="auto" class="aside">
                <MenuBar></MenuBar>
            </el-aside>
            <el-container>
                <el-header class="header">
                  <Header></Header>
                </el-header>
                <el-main class="main">
                    <Tabs style="padding: 0px 20px"></Tabs>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
</template>

<script setup>
import Header from "@/layout/header/Header.vue";
import MenuBar from "@/layout/menu/MenuBar.vue";
import Tabs from "@/layout/tabs/Tabs.vue";
</script>

<style lang="scss" >
.layout {
  height: 100%;
  .aside {
    background-color: #304156;
  }
  .header {
    display: flex;
    align-items: center;
    background-color: #2D4356;
    justify-content: space-between;
  }
  .main {
    padding-top: 4px;
    padding-left: 0px !important;
    padding-right: 0px !important;
  }
}
</style>

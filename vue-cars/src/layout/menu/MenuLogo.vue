<template>
    <div class="logo">
        <!-- <img :src="MenuLogo" alt="logo" /> -->
        <span v-if="show" class="logo-title">{{ title }}</span>
    </div>
</template>

<script setup>
import MenuLogo from '@/assets/logo.png'
import { ref, watch } from "vue";
const title = ref("汽车保险计算系统");

// 接受父组件内传递的参数
const props = defineProps({
  isCollapsed: {
    require: true,
    type: Boolean,
  },
});

// 解决收缩卡顿问题
const show = ref(true);
watch(
  () => props.isCollapsed,
  (collapsed) => {
    if (!collapsed) {
      setTimeout(() => {
        show.value = !collapsed;
      }, 300);
    } else {
      show.value = !collapsed;
    }
  }
);

</script>

<style lang="scss" scoped>
.logo {
  display: flex;
  width: 100%;
  height: 60px;
  line-height: 60px;
  background: #2b2f3a;
  text-align: center;
  cursor: pointer;
  align-items: center;
  img {
    width: 36px;
    height: 36px;
    margin-right: 12px;
    margin-left: 20px;
  }
  .logo-title {
    color: #fff;
    font-weight: 800;
    line-height: 60px;
    font-size: 20px;
    font-family: FangSong;
  }
}
</style>

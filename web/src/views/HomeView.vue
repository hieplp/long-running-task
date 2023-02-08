<template>
  <div class="grid h-screen w-screen place-items-center overflow-x-scroll md:overflow-x-hidden">
    <div class="w-full md:w-2/3 xl:w-2/4 h-auto px-2.5">
      <UploadFile v-model:files="files"/>

      <div v-if="files">
        <FileItem v-for="(file, index) in files" :key="index" :file="file"/>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import UploadFile from "../components/UploadFile.vue";
import FileItem from "../components/FileItem.vue";

const files = ref([]);

</script>

<style lang="scss" scoped>

</style>
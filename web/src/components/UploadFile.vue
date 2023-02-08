<template>
  <div class="flex items-center justify-center w-full"
       @dragleave="dragLeave"
       @dragover.prevent="dragOver"
       @drop.prevent="drop">
    <label
        class="flex flex-col items-center justify-center w-full h-64 xl:h-96 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
        for="dropzone-file">
      <div class="flex flex-col items-center justify-center pt-5 pb-6">
        <svg aria-hidden="true"
             class="w-10 h-10 mb-3 text-gray-400"
             fill="none"
             stroke="currentColor"
             viewBox="0 0 24 24"
             xmlns="http://www.w3.org/2000/svg">
          <path d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2">

          </path>
        </svg>

        <p v-if="isDragging" class="mb-2 text-sm text-gray-500 dark:text-gray-400">
          <span class="font-semibold">Release to drop file here.</span>
        </p>
        <p v-else class="mb-2 text-sm text-gray-500 dark:text-gray-400">
          <span class="font-semibold">Click to upload</span> or drag and drop
        </p>

        <p class="text-xs text-gray-500 dark:text-gray-400">XLSX or XLS</p>
      </div>
    </label>
    <input id="dropzone-file"
           accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
           class="hidden"
           multiple
           type="file"
           @change="onFileChanged"/>
  </div>
</template>

<script setup>
import { computed, getCurrentInstance, ref, toRefs } from "vue";

const props = defineProps({
  files: {
    required: true,
  }
});

const { emit } = getCurrentInstance();
const { files } = toRefs(props);
const filesComputed = computed({
  get() {
    return files.value;
  }, set(value) {
    emit('update:files', value);
  }
});

const isDragging = ref(false);

function onFileChanged(e) {
  filesComputed.value = e.target.files;
}

// Drag and drop
function dragOver(e) {
  isDragging.value = true;
}

function dragLeave() {
  isDragging.value = false;
}

function drop(e) {
  isDragging.value = false;
  filesComputed.value = e.dataTransfer.files;
}

</script>

<script>
export default {
  name: "UploadFile"
}
</script>

<style scoped>

</style>

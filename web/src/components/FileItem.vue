<template>
  <div class="grid grid-cols-12 rounded bg-blue-200 mt-2 py-2">
    <div class="col-span-2 md:col-span-1 grid items-center justify-items-center">
      <ClipboardIcon class="h-6"/>
    </div>

    <div class="col-span-7 md:col-span-9">
      <p class="text-sm">{{ file.name }} ({{ file.size / 1000 }} kb)</p>
      <p class="h-2 mt-2 rounded w-full bg-blue-600"></p>
    </div>

    <div class="col-span-3 md:col-span-2 flex items-center">
      <button class="h-full bg-blue-600 hover:bg-blue-600/75 text-white font-bold py-1 px-6 rounded mx-auto"
              @click="readExcel">
        <PaperAirplaneIcon class="h-5"/>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ClipboardIcon, PaperAirplaneIcon } from '@heroicons/vue/24/outline'
import { toRefs } from "vue";
import { useUserStore } from "../stores/user.store.js";
import { StringHelper } from "../common/helper/string.helper.js";
import { useRouter } from "vue-router";

const props = defineProps({
  file: {
    required: true
  }
})
const { file } = toRefs(props)
const router = useRouter();

const userStore = useUserStore();

async function readExcel() {
  await userStore.readExcel(file.value);

  const refKey = StringHelper.generate(10);
  userStore.refKey = refKey;
  await router.push({ name: 'import-user', params: { refKey: refKey } });
}

</script>


<style scoped>

</style>

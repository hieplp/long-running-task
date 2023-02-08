<template>
  <div class="grid h-screen w-screen place-items-center overflow-x-scroll md:overflow-x-hidden">
    <div class="w-full md:w-5/6 xl:w-3/4 h-auto px-2.5">
      <div class="pt-4">
        <button v-if="!userStore.isInvalidUsersShowed && !userStore.isImporting"
                class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                @click="showInvalidUsers">
          Only Invalid Users
        </button>
        <button v-else-if="!userStore.isImporting"
                class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                @click="showAllUsers">
          All
        </button>
        <button
            class="ml-2 bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
            @click="changeImportFile">
          Another File
        </button>
        <button
            v-if="!userStore.isImporting"
            class="ml-2 bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
            @click="importUsers">
          Import
        </button>

        <UserTable :current-id="currentId"/>
      </div>

      <div v-if="error.isError" class="text-red-600">
        {{ error.message }}
      </div>

      <div v-if="isImportSuccess" class="text-green-400">
        Imported Successfully
      </div>
    </div>
  </div>
</template>

<script setup>
import UserTable from "../components/UserTable.vue";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "../stores/user.store.js";
import { API } from "../common/api.config.js";
import { ERROR_CODE, SUCCESS_CODE } from "../common/api.code.js";

// XXX Common
onMounted(() => {
  if (userStore.refKey === "") {
    userStore.refKey = route.params.refKey;
    userStore.getImportUsers()
        .then(() => {
          userStore.isImporting = true;
          openSSE();
        })
        .catch((e) => {
          router.push({ name: 'home' });
        });
  } else if (!userStore.isLoading && userStore.isUsersEmpty) {
    router.push({ name: 'home' });
  }
});

const route = useRoute();
const router = useRouter();

// XXX Store
const userStore = useUserStore();

// XXX Data
const error = ref({
  isError: false,
  message: 'error'
});
const isImportSuccess = ref(false);
const evtSource = ref(null);
const currentId = ref(-1);

// XXX Methods
function changeImportFile() {
  userStore.users = [];
  userStore.showedUsers = [];
  userStore.from = 0;
  userStore.invalidUsers = [];
  userStore.isImporting = false;
  userStore.setInvalidUsersShowed(false);
  router.push({ name: 'home' });
}

function showInvalidUsers() {
  userStore.from = 0;
  userStore.to = 10;
  userStore.setInvalidUsersShowed(true);
}

function showAllUsers() {
  userStore.setInvalidUsersShowed(false);
}

function openSSE() {
  let es = new EventSource(API.SSE_USER + "?refKey=" + userStore.refKey);
  es.onopen = (e) => {
    console.log(e);
  };

  es.onmessage = (e) => {
    console.log(e);
    if (e.data === ERROR_CODE.SSE_SERVER_ERROR) {
      error.value = {
        isError: true,
        message: 'Unknown error while importing'
      };
    } else if (e.data === SUCCESS_CODE.SSE_SUCCESS) {
      isImportSuccess.value = true;
    } else {
      currentId.value = e.data;
    }
  };

  es.onerror = (e) => {
    console.log(e);
    evtSource.value.close();

    userStore.isImporting = false;
  };

  evtSource.value = es;
}

function closeSSE() {
  if (!evtSource.value) {
    return;
  }
  evtSource.value.close();
}

function importUsers() {
  //
  error.value.isError = false;
  isImportSuccess.value = false;
  //
  openSSE();
  //
  userStore.importUsers()
      .then((response) => {
        userStore.isImporting = true;
        // openSSE();
      })
      .catch((err) => {
        console.log(err);
        switch (err) {
          case ERROR_CODE.DUPLICATED_PHONE:
            error.value = {
              isError: true,
              message: 'Duplicated Phone(s)'
            };
            break;
          case ERROR_CODE.DUPLICATED_PERSONAL_ID:
            error.value = {
              isError: true,
              message: 'Duplicated PersonalId(s)'
            };
            break;
          case ERROR_CODE.NO_USER_SELECTED:
            error.value = {
              isError: true,
              message: 'No User Selected'
            };
            break;
          default:
            console.log('Unknown Error');
        }
        closeSSE();
      })
}

</script>

<style scoped>

</style>

<template>
  <div
      class="bg-gray-600 bg-opacity-70 fixed top-0 left-0 z-50 w-screen h-screen grid place-items-center overflow-x-hidden overflow-y-auto">
    <!-- Main modal -->
    <div class="grid place-items-center w-full md:inset-0 h-modal h-auto">
      <div class="relative w-full h-full max-w-md md:h-auto">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <button
              class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white"
              data-modal-hide="authentication-modal"
              type="button"
              @click="closeModal">
            <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                 xmlns="http://www.w3.org/2000/svg">
              <path clip-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    fill-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
          <div class="px-6 py-6 lg:px-8">
            <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">Edit User</h3>
            <div class="space-y-6">
              <div>
                <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                  Name
                </label>
                <input v-model="user.name"
                       :class="{'border-red-500 border-2':!isNameValid()}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 "
                       placeholder="name@company.com"
                       required type="text">
              </div>

              <div>
                <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                  Personal ID
                </label>
                <input v-model="user.personalId"
                       :class="{'border-red-500 border-2 border-2':!isPersonalIdValid()}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 "
                       placeholder="name@company.com"
                       required type="text">
              </div>

              <div>
                <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                  Phone
                </label>
                <input v-model="user.phone"
                       :class="{'border-red-500 border-2':!isPhoneValid()}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
                       placeholder="name@company.com"
                       required type="text">
              </div>

              <button
                  class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  @click="updateUser">
                Update user
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "../stores/user.store.js";
import { ref } from "vue";
import { nameRegex, personalIdRegex, phoneRegex } from "../common/regex.js";

const userStore = useUserStore();
const user = ref(Object.assign({}, userStore.user));
const emit = defineEmits(['is-visible']);

function closeModal() {
  emit('is-visible', false);
}

function isNameValid() {
  return nameRegex.test(user.value.name);
}

function isPersonalIdValid() {
  return personalIdRegex.test(user.value.personalId);
}

function isPhoneValid() {
  return phoneRegex.test(user.value.phone);
}

function updateUser() {
  if (isNameValid() && isPersonalIdValid() && isPhoneValid()) {
    userStore.updateUser(user.value);
    closeModal();
  }
}

</script>

<style scoped>

</style>
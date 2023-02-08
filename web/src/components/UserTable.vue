<template>
  <div class="rounded-lg bg-gray-50 w-full">
    <div class="relative shadow-md sm:rounded-lg mt-2 overflow-x-auto">
      <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400 text-center">
        <thead class="text-xs text-gray-700 uppercase bg-gray-200 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th v-if="!userStore.isImporting" class="py-3 px-6" scope="col">
            <div class="flex items-center">
              <input id="checkbox-all-search"
                     :checked="isAllUserSelected"
                     :disabled="userStore.isInvalidUsersShowed || userStore.isImporting"
                     class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                     type="checkbox"
                     @click="clickAllUserCheckbox">
              <label class="sr-only" for="checkbox-all-search">checkbox</label>
            </div>
          </th>
          <th class="py-3 px-6" scope="col">
            No.
          </th>
          <th class="py-3 px-6" scope="col">
            Name
          </th>
          <th class="py-3 px-6" scope="col">
            PERSONAL ID
          </th>
          <th class="py-3 px-6" scope="col">
            PHONE
          </th>
          <th v-if="!userStore.isImporting" class="py-3 px-6" scope="col">
            Action
          </th>
          <th v-if="userStore.isImporting" class="py-3 px-6" scope="col">
            Status
          </th>
        </tr>
        </thead>
        <tbody v-if="!isPageSwitching">

        <tr v-for="(user, index) in showedUsers"
            :key="index" class="bg-white border-b hover:bg-gray-50">

          <td v-if="!userStore.isImporting" class="py-3 px-6">
            <div class="flex items-center">
              <input id="checkbox-table-search-1"
                     :checked="user.isSelected"
                     :disabled="!isUserValid(user) || userStore.isImporting"
                     class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                     type="checkbox"
                     @click="clickUserCheckbox(user.id)">
              <label class="sr-only" for="checkbox-table-search-1">checkbox</label>
            </div>
          </td>

          <td class="py-3 px-6">
            {{ userStore.from + index + 1 }}
          </td>

          <td :class="{'text-red-600':user.isNameInvalid}"
              class="py-3 px-6">
            {{ user.name }}
          </td>

          <td :class="{'text-red-600':user.isPersonalIdInvalid}"
              class="py-3 px-6">
            {{ user.personalId }}
          </td>

          <td :class="{'text-red-600':user.isPhoneInvalid}"
              class="py-3 px-6">
            {{ user.phone }}
          </td>

          <td v-if="!userStore.isImporting" class="py-3 px-6">
            <!-- Modal toggle -->
            <a class="mr-2 cursor-pointer font-medium text-blue-600 dark:text-blue-500 hover:underline"
               data-modal-toggle="editUserModal" type="button"
               @click="openEditModal(user)">
              Edit
            </a>

            <a class="cursor-pointer font-medium text-red-500 dark:text-blue-500 hover:underline"
               data-modal-toggle="editUserModal" type="button"
               @click="deleteUser(user)">
              Delete
            </a>
          </td>

          <td v-if="userStore.isImporting" class="py-3 px-6">
            <div v-if="currentId >= user.id || user.status === ImportStatus.DONE"
                 class="rounded-2xl bg-green-500 h-5 w-5 mx-auto"></div>
            <div v-else class="rounded-2xl bg-yellow-300 h-5 w-5 mx-auto"></div>
          </td>

        </tr>

        </tbody>
      </table>

    </div>

    <TablePagination :current-page="currentPage"
                     :from="userStore.from"
                     :page-range="1"
                     :per-page="userStore.perPage"
                     :to="userStore.from + userStore.showedUsers.length"
                     :total="userStore.getTotalUsers"
                     @page-changed="selectPage"/>

    <UserModal v-if="isEditModalVisible" @is-visible="closeEditModal"/>

  </div>
</template>
<script setup>
import TablePagination from "./TablePagination.vue";
import UserModal from "./UserModal.vue";
import { computed, ref, toRefs, watch } from "vue";
import { useUserStore } from "../stores/user.store.js";
import { UserHelper } from "../common/helper/user.helper.js";
import { ImportStatus } from "../common/enum.config.js";

// XXX Store
const userStore = useUserStore();

// XXX Props
const props = defineProps({
  currentId: {
    default: -1
  }
});
const { currentId } = toRefs(props);

// XXX Data
const showedUsers = computed(() => userStore.getShowedUsers);
const currentPage = ref(1);
const isAllUserSelected = ref(true);
const isPageSwitching = ref(false);
const isEditModalVisible = ref(false);

// XXX Watchers
watch(currentId, () => {
  let newPage = Math.ceil(currentId.value / userStore.perPage);
  if (newPage === currentPage.value) {
    return;
  }
  currentPage.value = newPage;
  selectPage(newPage);
});

// XXX Methods
function setShowedUserList() {
  userStore.from = 0;
  userStore.to = 10;
}

function selectPage(e) {
  currentPage.value = e;
  userStore.selectPage(e);
}

function openEditModal(user) {
  userStore.user = user;
  isEditModalVisible.value = true;
}

function closeEditModal() {
  isEditModalVisible.value = false;
}

function deleteUser(user) {
  userStore.deleteUser(user);
}

function isUserValid(user) {
  return UserHelper.isUserValid(user);
}

function clickUserCheckbox(id) {
  userStore.updateIsUserSelected(id);
}

function clickAllUserCheckbox() {
  isAllUserSelected.value = !isAllUserSelected.value;
  userStore.updateAllIsUserSelected(isAllUserSelected.value);
}

</script>


<style lang="scss" scoped>

</style>

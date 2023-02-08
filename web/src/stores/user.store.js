import { defineStore } from "pinia";
import * as XLSX from "xlsx";
import { nameRegex, personalIdRegex, phoneRegex } from "../common/regex.js";
import axios from "axios";
import { API } from "../common/api.config.js";
import { ERROR_CODE, SUCCESS_CODE } from "../common/api.code.js";
import { UserHelper } from "../common/helper/user.helper.js";

export const useUserStore = defineStore({
	id: "user",
	state: () => ({
		refKey: "",
		users: [],
		invalidUsers: [],
		showedUsers: [],
		isInvalidUsersShowed: false,
		perPage: 10,
		from: 0,
		to: 10,
		user: {
			id: "",
			name: "",
			personalId: "",
			phone: "",
			//
			isNameInvalid: true,
			isPersonalIdInvalid: true,
			isPhoneInvalid: true,
			isSelected: true,
		},
		isLoading: false,
		isImporting: false
	}),
	getters: {
		getShowedUsers: (state) => {
			let clonedUsers;
			if (state.isInvalidUsersShowed) {
				clonedUsers = [...state.invalidUsers];
			} else {
				clonedUsers = [...state.users];
			}
			state.showedUsers = clonedUsers.slice(state.from, state.to);
			return state.showedUsers;
		},

		getTotalShowedUser: (state) => {
			return state.showedUsers.length;
		},

		getTotalUsers: (state) => {
			if (state.isInvalidUsersShowed) {
				return state.invalidUsers.length;
			} else {
				return state.users.length;
			}
		},

		isUsersEmpty: (state) => {
			return state.users.length === 0;
		},
	},
	actions: {
		selectPage(page) {
			let previousPage = page - 1;
			this.from = (previousPage <= 0 ? 0 : previousPage) * this.perPage;
			this.to = page * this.perPage;
		},

		readExcel(file) {
			this.isLoading = true;
			const fileReader = new FileReader();
			fileReader.readAsArrayBuffer(file);
			fileReader.onload = (e) => {
				// Parse data
				const result = e.target.result;
				const wb = XLSX.read(result, { type: "binary" });
				// Get first worksheet
				const wsName = wb.SheetNames[0];
				const ws = wb.Sheets[wsName];
				// Convert to json then update state
				this.users = XLSX.utils.sheet_to_json(ws);
				// Validate users
				this.checkUsersValid();

				this.isLoading = false;
			}
		},

		checkUsersValid() {
			this.users.forEach((user, index) => {
				user.id = index
				user.isNameInvalid = false;
				user.isPersonalIdInvalid = false;
				user.isPhoneInvalid = false;
				user.isSelected = true;

				if (!nameRegex.test(user.name)) {
					user.isNameInvalid = true;
				}
				if (!personalIdRegex.test(user.personalId)) {
					user.isPersonalIdInvalid = true;
				}
				if (!phoneRegex.test(user.phone)) {
					user.isPhoneInvalid = true;
				}

				if (!UserHelper.isUserValid(user)) {
					this.invalidUsers.push(user);
					user.isSelected = false;
				}
			});
		},

		setInvalidUsersShowed(bool) {
			this.isInvalidUsersShowed = bool;
		},

		updateUser(user) {
			user.isNameInvalid = false;
			user.isPersonalIdInvalid = false;
			user.isPhoneInvalid = false;
			user.isSelected = true;
			this.users.forEach((u, index) => {
				if (u.id === user.id) {
					this.users[index] = user;
				}
			});
			this.invalidUsers.forEach((invalidUser, index) => {
				if (invalidUser.id === user.id) {
					this.invalidUsers.splice(index, 1);
				}
			});
		},

		deleteUser(user) {
			this.users.forEach((u, index) => {
				if (u.id === user.id) {
					this.users.splice(index, 1);
				}
			});

			this.invalidUsers.forEach((invalidUser, index) => {
				if (invalidUser.id === user.id) {
					this.invalidUsers.splice(index, 1);
				}
			});
		},

		updateIsUserSelected(id) {
			let foundIndex = this.users.findIndex((u) => u.id === id);
			let user = this.users[foundIndex];
			if (!UserHelper.isUserValid(user)) {
				return;
			}
			user.isSelected = !user.isSelected;
		},

		updateAllIsUserSelected(isSelected) {
			this.users.forEach((user) => {
				if (isSelected && !UserHelper.isUserValid(user)) {
					return;
				}
				user.isSelected = isSelected;
			});
		},

		importUsers() {
			return new Promise((resolve, reject) => {
				let importedUsers = this.users.filter((user) => user.isSelected);
				let i = 0;
				importedUsers.forEach((user) => {
					user.id = i++;
				});
				if (importedUsers.length === 0) {
					reject(ERROR_CODE.NO_USER_SELECTED);
					return;
				}

				axios
					.post(API.IMPORT_USER, {
						refKey: this.refKey,
						users: importedUsers,
					})
					.then((response) => {
						let code = response.data.code;
						switch (code) {
							case SUCCESS_CODE.OK:
								this.users = importedUsers;
								resolve();
								break;
							case ERROR_CODE.DUPLICATED_PHONE:
								let duplicatedPhones = response.data.data;
								duplicatedPhones.forEach((phone) => {
									let index = UserHelper.findIndexByPhone(this.users, phone);
									let user = this.users[index];
									user.isPhoneInvalid = true;
									user.isSelected = false;
									this.invalidUsers.push(user);
								});
								reject(ERROR_CODE.DUPLICATED_PHONE);
								break;
							case ERROR_CODE.DUPLICATED_PERSONAL_ID:
								let duplicatedPersonalIds = response.data.data;
								duplicatedPersonalIds.forEach((personalId) => {
									let index = UserHelper.findIndexByPersonalId(this.users, personalId);
									let user = this.users[index];
									user.isPersonalIdInvalid = true;
									user.isSelected = false;
									this.invalidUsers.push(user);
								});
								reject(ERROR_CODE.DUPLICATED_PERSONAL_ID);
								break;
							default:
								reject();
						}
					})
					.catch((error) => {
						reject(error);
					});
			});
		},

		getImportUsers() {
			return new Promise((resolve, reject) => {
				axios.get(API.IMPORT_USER + "/" + this.refKey, {})
					.then((response) => {
						const data = response.data;
						switch (data.code) {
							case SUCCESS_CODE.OK:
								this.users = data.data;
								resolve();
								break;
							default:
								reject(data.code);
						}
					})
					.catch((error) => {
						reject(error);
					});
			});
		}
	}
})

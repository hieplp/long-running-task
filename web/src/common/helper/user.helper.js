export const UserHelper = {
	findIndexByPhone(users, phone) {
		return users.findIndex((user) => user.phone + "" === phone);
	},

	findIndexByPersonalId(users, personalId) {
		return users.findIndex((user) => user.personalId + "" === personalId);
	},

	isUserValid(user) {
		return !user.isNameInvalid
			&& !user.isPersonalIdInvalid
			&& !user.isPhoneInvalid;
	}
}

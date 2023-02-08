import { createRouter, createWebHistory } from "vue-router";

const routes = [
	{
		path: "/",
		name: "home",
		component: () => import("../views/HomeView.vue"),
	},
	{
		path: "/user/:refKey",
		name: "import-user",
		component: () => import("../views/ImportUserView.vue"),
	}
]

const router = createRouter({
	routes,
	history: createWebHistory(import.meta.env.BASE_URL),
})

router.beforeEach((to, from, next) => {
	next();
})

export default router;

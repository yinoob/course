import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Chapter from "./views/admin/chapter.vue"
import Section from "./views/admin/section.vue"
import Course from "./views/admin/course.vue"
import Category from "./views/admin/category.vue"
import Teacher from "./views/admin/teacher.vue"
//import Content from "./views/admin/content.vue"


Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    }, {
        path: "",
        redirect: "/login",
    }, {
        path: "/login",
        component: Login,
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome,
        },{
            path: "business/chapter",
            name: "chapter",
            component: Chapter,
        },{
            path: "business/section",
            name: "section",
            component: Section,
        },{
            path: "business/course",
            name: "course",
            component: Course,
        },{
            path: "business/category",
            name: "category",
            component: Category,
        },{
            path: "business/teacher",
            name: "teacher",
            component: Teacher,
        },
            /** {
            path: "business/content",
            name: "business/content",
            component: Content,
        }**/]
    }]
})

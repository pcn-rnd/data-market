import Vue from 'vue'
import VueRouter from 'vue-router'
import VueMeta from 'vue-meta'

import store from '@/state/store'
import routes from './routes'
import axios from 'axios'
import cors from 'cors'

Vue.use(VueRouter)
//Vue.use(cors)
Vue.use(VueMeta, {
    // The component option name that vue-meta looks for meta info on.
    keyName: 'page',
})
Vue.use(cors);
const router = new VueRouter({
    routes,
    // Use the HTML5 history API (i.e. normal-looking routes)
    // instead of routes with hashes (e.g. example.com/#/about).
    // This may require some server configuration in production:
    // https://router.vuejs.org/en/essentials/history-mode.html#example-server-configurations
    mode: 'history',
    // Simulate native-like scroll behavior when navigating to a new
    // route and using back/forward buttons.
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    },
})

// Before each route evaluates...
router.beforeEach((routeTo, routeFrom, next) => {
    // Check if auth is required on this route
    // (including nested routes).
    const authRequired = routeTo.matched.some(
        (route) => route.meta.authRequired)

    // If auth isn't required for the route, just continue.
    if (!authRequired || store.state.isLogin) {
        return next()
    }

    next(/*{name: 'index', query: {redirectFrom: routeTo.fullPath}}*/)
})

router.beforeResolve(async (routeTo, routeFrom, next) => {
    // Create a `beforeResolve` hook, which fires whenever
    // `beforeRouteEnter` and `beforeRouteUpdate` would. This
    // allows us to ensure data is fetched even when params change,
    // but the resolved route does not. We put it in `meta` to
    // indicate that it's a hook we created, rather than part of
    // Vue Router (yet?).
    try {
        // For each matched route...
        for (const route of routeTo.matched) {
            await new Promise((resolve, reject) => {
                // If a `beforeResolve` hook is defined, call it with
                // the same arguments as the `beforeEnter` hook.
                if (route.meta && route.meta.beforeResolve) {
                    route.meta.beforeResolve(routeTo, routeFrom, (...args) => {
                        // If the user chose to redirect...
                        if (args.length) {
                            // If redirecting to the same route we're coming from...
                            // Complete the redirect.
                            next(...args)
                            reject(new Error('Redirected'))
                        } else {
                            resolve()
                        }
                    })
                } else {
                    // Otherwise, continue resolving the route.
                    resolve()
                }
            })
        }
        // If a `beforeResolve` hook chose to redirect, just return.
    } catch (error) {
        return
    }

    // If we reach this point, continue resolving the route.
    next()
})

Vue.prototype.$http = axios

axios.interceptors.request.use(config => {

    config.headers["Authorization"] = `Bearer ${store.state.token}`;

    config.baseURL = process.env.VUE_APP_API_HOST + "/api/v1";

    return config;

}, error => {
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    return response;
}, error => {
    /*alert(error.response.data.body);*/
    return Promise.reject(error);
});

export default router

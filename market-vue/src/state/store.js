import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist';

const vuexPersistence = new VuexPersistence({
    key: 'vuex-state',
    storage: window.sessionStorage
});

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        isLogin: false,
        token: '',
        curUser : ''
    },
    mutations: {
        ['SETTOKEN'](state, payload) {
            state.token = payload;
        },
        ['SETLOGINSTATE'](state, payload) {
            state.isLogin = payload;
        },
        ['SETCURUSER'](state, payload) {
            state.curUser = payload;
        }
    },
    plugins: [vuexPersistence.plugin],
    // Enable strict mode in development to get a warning
    // when mutating state outside of a mutation.
    // https://vuex.vuejs.org/guide/strict.html
    strict: process.env.NODE_ENV !== 'production',
})

export default store


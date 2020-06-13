import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
// example vuex   https://github.com/vuejs/vuex
export const store = new Vuex.Store({
    //ตัวแปรที่ประกาศไว้ ใช้เป็น Global Value
    state: {

        customer: '',
        drawer: false,
        Login: ''
    },

    //ทำการบันทึกค่าลง state(Global Value)
    mutations: {

        setCustomer(state, value) {
            state.customer = value;
        },
        setDrawer(state, value) {
            state.drawer = value;
        },
        setLogin(state, value) {
            state.Login = value;
        }
    },

    // ถึงค่าออกไปยัง component ที่ต้องการ
    getters: {

        customer: state => state.customer,
        drawer: state => state.drawer
    },

    //ก่อนจะบันทึกค่าลงใน store อยากให้ทำอะไรก่อน
    actions: {

        setCustomer(context, payload) {
            context.commit("setCustomer", payload);
        },
        setDrawer(context, payload) {
            context.commit("setDrawer", payload);
        },

        setLoginTrue(context) {
            this.state.Login = true;
            context.commit("setLogin", this.state.Login);
        },
        setLoginFalse(context) {
            this.state.Login = false;
            context.commit("setLogin", this.state.Login);
        }
    }
})
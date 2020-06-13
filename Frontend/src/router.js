import Vue from 'vue'
import Router from 'vue-router'
import Register from './components/Register'
import Login from './components/Login'
import Dashbord from './components/Dashbord'
import Borrow from './components/Borrow'
import ManageEquipment from './components/ManageEquipment'
import ManageStatus from './components/ManageStatus'
import Returns from './components/Returns'
import Reservations from './components/Reservations'
import Profile from './components/Profile'
import DeleteResvertion from './components/DeleteResvertion'
import ShowEQT from "./components/ShowEQT"
import ShowStatusRoom from './components/ShowStatusRoom'
import ShowReturns from './components/ShowReturns'
import ShowBorrow from './components/ShowBorrow'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Dashbord',
      name: 'Dashbord',
      component: Dashbord
    },
    {
      path: '/Borrow',
      name: 'Borrow',
      component: Borrow
    },
    {
      path: '/ManageEquipment',
      name: 'ManageEquipment',
      component: ManageEquipment
    },
    {
      path: '/ManageStatus',
      name: 'ManageStatus',
      component: ManageStatus
    },
    {
      path: '/Returns',
      name: 'Returns',
      component: Returns
    },
    {
      path: '/Reservations',
      name: 'Reservations',
      component: Reservations
    },
    {
      path: '/Profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: '/DeleteResvertion',
      name: 'DeleteResvertion',
      component: DeleteResvertion
    },
    {
      path: '/ShowEQT',
      name: 'ShowEQT',
      component: ShowEQT
    },
    {
      path: '/ShowStatusRoom',
      name: 'ShowStatusRoom',
      component: ShowStatusRoom
    },
    {
    path: '/ShowReturns',
    name: 'ShowReturns',
    component: ShowReturns
    },
   {
    path: '/ShowBorrow',
    name: 'ShowBorrow',
    component: ShowBorrow
    },
  ]
})
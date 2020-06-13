<template>
  <v-app>
    <CustomerNavigation></CustomerNavigation>
    <Titlebar></Titlebar>
    <v-content>
      <router-view />
    </v-content>
    <footer>
      <v-col class="ml-12">
        <label>Theme</label>
        <v-switch
          v-model="$vuetify.theme.dark"
          primary
          label="Dark"
        ></v-switch>
      </v-col>
    </footer>
  </v-app>
</template>

<script>
/*eslint-disable */

import CustomerNavigation from "./components/CustomerNavigation";
import Titlebar from "./components/Titlebar";
export default {
  name: "App",
  components: {
    Titlebar,
    CustomerNavigation
  },
  data: () => ({
    //
  })
};
/*eslint-disable */
</script>
<style scoped>
span.Reg {
  font: 30px Abyssopelagic, sans-serif;
  width: 100%;
}
</style>>

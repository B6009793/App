<template>
  <v-navigation-drawer
    v-if="ShowLogin"
    app
    :clipped="true"
    :permanent="true"
    color="grey lighten-4"
  >

    <v-divider
      dark
      class="my-3"
    ></v-divider>
    <div class="pa-2">
      <v-btn
        block
        v-on:click="Resvationopen"
      >Reservation</v-btn>
    </div>
    <v-divider
      dark
      class="my-3"
    >
    </v-divider>
    <div class="pa-2">
      <v-btn
        block
        v-on:click="DeleteResvertion"
      >Delete Resvertion</v-btn>
    </div>
    <v-divider
      dark
      class="my-3"
    >
    </v-divider>
    <div class="pa-2">
      <v-btn
        block
        to="Profile"
      >Profile</v-btn>
    </div>
    <v-divider
      dark
      class="my-3"
    ></v-divider>

    <div class="pa-2">
      <v-btn
        block
        to="ShowEQT"
      >Show Equipment</v-btn>
    </div>
    <v-divider
      dark
      class="my-3"
    ></v-divider>

    <template v-slot:append>
      <div class="pa-2">
        <v-btn
          block
          v-on:click="Logout"
        >Logout</v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
/*eslint-disable */
export default {
  // props: ["value"],
  data() {
    return {};
  },
  methods: {
    Resvationopen() {
      this.$router.push({
        name: "Reservations"
        // params: { cus: this.customer.id }
      });
    },
    DeleteResvertion() {
      this.$router.push("DeleteResvertion");
    },
    ShowEQT() {
      this.$router.push("ShowEQT");
    },
    Logout() {
      this.$store.commit("setDrawer", false);
      this.$store.dispatch("setLoginFalse");
      this.$router.push("/");
    },
    //ตัวเปิดปิด Nagavitor Drawer
    ShowDrawer() {
      this.checkShow = this.$store.state.drawer;
    }
  },
  watch: {
    drawer() {
      this.ShowDrawer();
    }
  },
  computed: {
    ShowLogin() {
      return this.$store.state.Login;
    }
  },
  mounted() {}
};
/*eslint-disable */
</script>

<style></style>

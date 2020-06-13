<template>
  <v-container class="ml-auto mt-3 pt-3 mr-auto">
    <div class="LoginPage">
      <v-card>
        <h1 class="text mt-5 pt-5">
          <strong>Login</strong>
        </h1>
        <v-form>
          <v-row justify="center">
            <v-col
              class="ml-12"
              cols="12"
              sm="6"
              md="6"
            >
              <v-text-field
                label="Name"
                v-model="login.Username"
                :rules="[(v) => !!v || 'Item is required']"
                required
              >
              </v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col
              class="ml-10"
              cols="12"
              sm="6"
              md="6"
            >
              <v-text-field
                label="Password"
                :type="'password'"
                v-model="login.Password"
                :rules="[(v) => !!v || 'Item is required']"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col
              class="ml-10"
              cols="12"
              sm="6"
              md="6"
            >
              <v-select
                class=" px-12 mx-12"
                label="Choose"
                outlined
                v-model="login.Choose"
                :items="Chooses"
                :rules="[(v) => !!v || 'Item is required']"
                required
              ></v-select>
            </v-col>
          </v-row>
          <v-row
            justify="center"
            class="pb-12 mb-10"
          >
            <v-col
              class="ml-10"
              cols="12"
              sm="2"
              md="-2"
            >
              <v-btn
                @click="Login"
                class="cyan lighten-1 white--text"
              >
                Login<v-icon
                  size="30px"
                  right
                >mdi-key-variant</v-icon>
              </v-btn>
            </v-col>
            <v-col
              class="ml-10"
              cols="12"
              sm="2"
              md="-2"
            >
              <v-btn
                @click="Register"
                class="cyan lighten-1 white--text"
              >
                Register Customer<v-icon
                  size="30px"
                  right
                >mdi-account-plus</v-icon>
              </v-btn>
            </v-col>
          </v-row>

        </v-form>
      </v-card>
    </div>
    <div v-if="clickLogin == true">
      <div v-if="checkLogin == true">
        <v-alert type="success">Login Success</v-alert>
      </div>
      <div v-if="checkLogin == false">
        <v-alert type="error">Username or Password is not valid or Input not complete</v-alert>
      </div>
    </div>
  </v-container>
</template>

<script>
/*eslint-disable */
import http from "../http-common";
export default {
  name: "Login",
  data() {
    return {
      login: {
        Username: "",
        Password: "",
        Choose: ""
      },
      employee: [],
      customer: [],
      Chooses: ["Employee", "Customer"],
      checkLogin: false,
      clickLogin: false
    };
  },

  methods: {
    Login() {
      this.clickLogin = true;
      if (!this.login.Username || !this.login.Password || !this.login.Choose) {
        this.checkLogin = false;
        // alert("Plese input all data");
      } else if (this.login.Choose == "Employee") {
        this.checkLogin = true;
        http
          .get("/employee/" + this.login.Username + "/" + this.login.Password)
          .then(response => {
            if (
              this.login.Username == response.data.em_name &&
              this.login.Password == response.data.password
            ) {
              this.employee = response.data;
              this.login.Username = response.data.em_name;
              this.login.Password = response.data.password;
              this.$store.dispatch("setLoginFalse");
              // alert("Login complete");
              this.$router.push({
                name: "Dashbord",
                params: { em: this.employee.em_id }
              });
            } else {
              this.checkLogin = false;
              // alert("Username or Password is not valid");
            }
          });
      } else if (this.login.Choose == "Customer") {
        this.checkLogin = true;
        http
          .get("/customer/" + this.login.Username + "/" + this.login.Password)
          .then(response => {
            if (
              this.login.Username == response.data.email &&
              this.login.Password == response.data.password
            ) {
              this.customer = response.data;
              this.login.Username = response.data.email;
              this.login.Password = response.data.password;

              this.$store.dispatch("setLoginTrue");
              this.$store.dispatch("setCustomer", this.customer.id);
              this.$router.push({
                name: "Reservations",
                params: { cus: this.customer.id }
              });
              // this.$router.push({
              //   name: "Customer",
              //   params: { cus: this.customer.id }
              // });
            } else {
              this.checkLogin = false;
              // alert("Username or Password is not valid");
            }
          });
      }
    },
    Register() {
      this.$router.push("Register");
    }
  }
};
/*eslint-disable */
</script>
<style scoped>
div.LoginPage {
  margin: 50px, 20px;
  padding: 20px, 50px;
}
span {
  font: 20px BankGothic Md BT, sans-serif;
  -width: 100%;
}

h1.text {
  text-align: center;
  font: 30px BankGothic Md BT, sans-serif;
  width: 100%;
}
</style>>
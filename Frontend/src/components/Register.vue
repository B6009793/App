<template>
  <v-container class="ml-auto mt-3 pt-3 mr-auto">
    <!--เป็นตัวครอบใช้ห่อเนื้อหาด้านในแล้วSetอยู่กลางจอ><-->
    <div class="FirstPage">
      <v-card>
        <h1 class="text mt-5 pt-5">
          <strong>Register Form</strong>
        </h1>
        <p></p>
        <v-form>
          <v-row>
            <v-col class="ml-12" cols="12" sm="6" md="5">
              <v-text-field v-model="customer.Name" label="Name" :rules="[(v) => !!v || 'Item is required']" required></v-text-field>
            </v-col>
            <v-col class="ml-10" cols="12" sm="6" md="5">
              <v-text-field v-model="customer.LastName" label="Last Name" :rules="[(v) => !!v || 'Item is required']" required></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-12" cols="11" sm="6" md="6">
              <v-text-field v-model="customer.Password" label="Password" :type="'password'" :rules="[(v) => !!v || 'Item is required']" required></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-12" cols="10" sm="6" md="6">
              <v-text-field v-model="customer.Email" label="Email" :rules="[(v) => !!v || 'Item is required']" required></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-12" cols="10" sm="6" md="6">
              <v-text-field v-model="customer.Phone" label="Phone-Number" :type="'number'" :rules="[(v) => !!v || 'Item is required',(v) => !!v && v.length == 10 || 'Phone must be equal 10']" required></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-12" cols="10" sm="6" md="6">
              <v-select
                label="Title Name"
                v-model="customer.TitleId"
                :items="titles"
                item-text="title"
                item-value="id"
   
                :rules="[(v) => !!v || 'Item is required']"
                required
              ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-10" cols="9" sm="6" md="5">
              <v-select
                label="Gender"
                v-model="customer.GenderId"
                :items="genders"
                item-text="gender"
                item-value="id"
                :rules="[(v) => !!v || 'Item is required']"
                required
 
              ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="ml-12" cols="8" sm="6" md="5">
              <v-select
                label="Status Customer"
                v-model="customer.StatusId"
                :items="statusCuss"
                item-text="statusCus"
                item-value="id"
                :rules="[(v) => !!v || 'Item is required']"
                required
              ></v-select>
            </v-col>
          </v-row>
        </v-form>
      </v-card>
    </div>
    <footer>
      <v-container class="ml-auto mt-3 pt-3 mr-auto">
        <div v-if = "clickRegister == true">
        <div v-if = "registerCheck == true">
          <v-alert type="success">Register Completed</v-alert>
        </div>
        <div v-if = "registerCheck == false">
          <v-alert type="error">Can't Register</v-alert>
        </div>
        </div>
        <v-row justify="center">
            <v-btn
              @click="RegisterCustomer"
              class="cyan lighten-1 white--text"
            >
              <v-icon size="35px" left>mdi-account-check</v-icon>Register
            </v-btn>
            <v-btn
              style="margin-left: 15px;"
              @click="clear"
              class="cyan lighten-1 white--text"
            >clear</v-btn>
            <v-btn style="margin-left: 15px;" @click="back" class="cyan lighten-1 white--text" >Back</v-btn>
        </v-row>
      </v-container>
    </footer>
  </v-container>
</template>

<script>
import http from "../http-common";
export default {
  name: "customer",
  data() {
    return {
      customer: {
        GenderId: "",
        TitleId: "",
        StatusId: "",
        Name:"",
        LastName:"",
        Password:"",
        Email:"",
        Phone:"",
      },
      titles:[],
      genders:[],
      statusCuss:[],
      registerCheck: false,
      clickRegister: false,
    };
  },
  methods: {
    /* eslint-disable no-console */
    //ใช้ router ช่วยในการทำงาน
    // ดึงข้อมูล Title ใส่ combobox
    getTitleNames() {
      http
        .get("/title")
        .then(response => {
          this.titles = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    // ดึงข้อมูล gender ใส่ combobox
    getGenders() {
      http
        .get("/gender")
        .then(response => {
          this.genders = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    // ดึงข้อมูล Type ใส่ combobox
    getStatus() {
      http
        .get("/statusCustomer")
        .then(response => {
          this.statusCuss = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    // function เมื่อกดปุ่ม Register
    RegisterCustomer() {
      this.clickRegister = true;
      if(!this.customer.Name||!this.customer.LastName||!this.customer.Password||!this.customer.Phone||!this.customer.Email||!this.customer.GenderId||!this.customer.TitleId||!this.customer.StatusId||this.customer.Phone.length!=10)
      {
        // this.registerCheck = false;
        // alert("Can't not register")
      }
      else{
        
      http
        .post("/customer/" +
            this.customer.Name +
            " " +
            this.customer.LastName +
            "/" +
            this.customer.Password +
            "/" +
            this.customer.Email +
            "/" +
            this.customer.Phone +
            "/" +
            this.customer.GenderId +
            "/" +
            this.customer.TitleId +
            "/" +
            this.customer.StatusId,
            this.customer
            )
        .then(response => {
          //this.types = response.data;
          console.log(response.data);
          this.registerCheck = true;
           this.customer.GenderId = ""
        this.customer.TitleId = ""
        this.customer.StatusId = ""
        this.customer.Name = ""
        this.customer.LastName = ""
        this.customer.Password = ""
        this.customer.Email = ""
        this.customer.Phone=""
        })
        .catch(e => {
          console.log(e);
          this.registerCheck = false;
           this.customer.GenderId = ""
        this.customer.TitleId = ""
        this.customer.StatusId = ""
        this.customer.Name = ""
        this.customer.LastName = ""
        this.customer.Password = ""
        this.customer.Email = ""
        this.customer.Phone=""
        });
       
      // alert('Register complete')
      
      }
    },
    /*ปุ่มclear -- ล้างข้อมูลทั้งหมดใน Register ถ้าต้องลบทั้งหมดที่พิมพ์ไป*/

    clear() {
     // this.$refs.form.reset();
      this.customer.GenderId = ""
      this.customer.TitleId = ""
      this.customer.StatusId = ""
      this.customer.Name = ""
      this.customer.LastName = ""
      this.customer.Password = ""
      this.customer.Email = ""
      this.customer.Phone=""
      this.registerCheck=false;
      this.clickRegister=false;
    },
    back(){
      this.$router.push("/");
    },
    refreshList() {
      this.getTitleNames();
      this.getGenders();
      this.getStatus();
    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.getTitleNames();
    this.getGenders();
    this.getStatus();
  }
};
</script>
<style scoped>
div.FirstPage {
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
</style>
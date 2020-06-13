<template>
  <div class="ManageEquipment">
    <div class="outside">
      <v-row justify="center">
        <br>
        <!-- <v-col justify="center"> -->
        <!-- <v-container> -->
        <!-- <v-layout row wrap> -->
        <v-col
        cols=5
        justify="center"
        >

        <v-toolbar
        color="black"
        dark
        flat
        >
        <!-- กรอบข้างบนสีฟ้า -->
        <v-toolbar-title>

          <h2 class="headline ">

            ระบบจัดการอุปกรณ์

          </h2>

        </v-toolbar-title>
        <div class="flex-grow-1"></div>
      </v-toolbar>
      <br>
      <br>
      <v-sheet>

        <v-select
        v-if="lock"
        v-model="ManageEquipment.EmName"
        :items="Employee"
        label="Employee"
        item-text="em_name"
        item-value="em_id"
        :readonly="true"
        solo
        outlined
        dense
        ></v-select>

        <br>
        <v-select
        v-model="ManageEquipment.Typename"
        :items="EquipmentType"
        label="EquipmentType"
        item-text="type"
        item-value="equipmenttype_id"
        solo
        outlined
        dense
        ></v-select>

        <br>
        <v-select
        v-model="ManageEquipment.EQname"
        :items="EquipmentName"
        label="EquipmentName"
        item-text="name"
        item-value="equipmentname_id"
        solo
        outlined
        dense
        ></v-select>

        <br>
        <v-text-field
        v-model="ManageEquipment.Amount"
        label="amount"
        :rules="[(v) => !!v || 'Item is required']"
        required
        solo
        outlined
        ></v-text-field>

        <!-- <br> -->

        <div class="text-center">
        <v-btn
        @click="saveData"
        tile
        color="success">Add equipment</v-btn>
      </div>
  <br>
      <div class="text-center">
      <v-btn
      @click="back"
      tile
      color="success">Back</v-btn>
    </div>


        <v-snackbar
        v-model="snackbar"
        top
        :color="cl"
        >
        {{ status }}
        <v-btn
        text
        @click="snackbar = false"
        >
        close
      </v-btn></v-snackbar>

    </v-sheet>
    <!-- @click="saveData" -->
  </v-col>

</v-row>
</div>
</div>
</template>

<script>
/* eslint-disable no-console */
import http from "../http-common";
export default {
  name: "ManageEquipment",
  data() {
    return {
      snackbar: false,
      text: 'บันทึกข้อมูลสำเร็จ',
      ManageEquipment: {
        EmName: "",
        Typename: "",
        EQname: "",
        Amount: ""
      },

      Employee: [],
      EquipmentName: [],
      EquipmentType: [],
      EquipmentAmount: "",
      showtable: [],
      lock: "",
      employee: ""
    };
  },
  methods: {
    lockemployee() {
      this.emid = this.$route.params.em;
      this.employee = this.emid;
      this.ManageEquipment.EmName = this.employee;
      this.lock = true;
    },
    saveData() {
      if (
        this.ManageEquipment.EmName == "" ||
        this.ManageEquipment.Typename == "" ||
        this.ManageEquipment.EQname == "" ||
        this.ManageEquipment.Amount == "0"
      ) {
        this.snackbar=true
        this.cl="error"
        this.status="เพิ่มข้อมูลไม่สำเร็จ!"
      } else {
      http
      .post(
        "/manageEquipments/" +
        this.ManageEquipment.EmName +
        "/" +
        this.ManageEquipment.Typename +
        "/" +
        this.ManageEquipment.EQname +
        "/" +
        this.ManageEquipment.Amount
      )
      .then(response => {
        console.log(response);
        this.snackbar=true
        this.status="เพิ่มข้อมูลสำเร็จ!"
        this.cl="success"
        this.getDataTable();
      })
      .catch(e => {
        console.log(e);
        this.snackbar=true
        this.cl="error"
        this.status="เพิ่มข้อมูลไม่สำเร็จ!"
      });
      this.submitted = true;
    }},
    getEmployee() {
      http.get("/employees").then(response => {
        this.Employee = response.data;
      });
      this.submitted = true;
    },
    getEquipmentType() {
      http.get("/equipmentTypes").then(response => {
        this.EquipmentType = response.data;
      });
      this.submitted = true;
    },
    getEquipmentName() {
      http.get("/equipmentNames").then(response => {
        this.EquipmentName = response.data;
      });
      this.submitted = true;
    },
    getDataTable() {
      http.get("/manageEquipments").then(response => {
        this.showtable = response.data;
      });
      this.submitted = true;
    },
    back(){
        this.$router.push({name: 'Dashbord' , params: {em: this.emid} });
      }
  },

  mounted() {
    this.getEmployee();
    this.getEquipmentType();
    this.getEquipmentName();
    this.getDataTable();
    this.lockemployee();
  }
};
/* eslint-disable no-console */
</script>

<template lang="html">
  <v-row justify="center">



    <v-col cols = 4 >
      <v-toolbar
      color="black"
      dark
      flat
      >
      <!-- กรอบข้างบนสีฟ้า -->
      <v-toolbar-title>

        <h2 class="headline ">

          ระบบแสดงอุปกรณ์

        </h2>

      </v-toolbar-title>
      <div class="flex-grow-1"></div>
    </v-toolbar>
      <br>
      <br>
      <v-data-table
      :headers="headers"
    :items="desserts"
    :items-per-page="5"
    class="elevation-1"
      >

    </v-data-table>

    <!-- {{ManageEquipment[0]}} -->
    </v-col>
  </v-row>
</template>

<script>

/*eslint-disable */
import http from "../http-common";
export default {
   status:"ManageEquipment",
  data () {
    return {
      headers: [
                {
                  text: 'ชื่ออุปกรณ์',
                  align: 'left',
                  sortable: false,
                  value: 'name',
                },
                { text: 'จำนวน', value: "amount" }

              ],
              desserts: [],
      Employee: [],
      EquipmentName: [],
      EquipmentType: [],
      ManageEquipment:[],
      EquipmentAmount: "",
      showtable: [
      ],
      totalIP : 0,
      totalWP : 0,
      totalP :  0,
      totalH : 0

    };
  },
  methods:{
    getEvent(){


    for (var i = 0; i < this.ManageEquipment.length; i++) {

        if(this.ManageEquipment[i].equipmentName.name == "Ipad"){

          this.totalIP =this.totalIP+ this.ManageEquipment[i].manageEquipment_amount;

        }else if(this.ManageEquipment[i].equipmentName.name == "Writeboard pen"){

          this.totalWP = this.totalWP + this.ManageEquipment[i].manageEquipment_amount;

        }else if(this.ManageEquipment[i].equipmentName.name == "Plug"){

          this.totalP = this.totalP + this.ManageEquipment[i].manageEquipment_amount;

        }else if(this.ManageEquipment[i].equipmentName.name == "Headphones"){
          this.totalH = this.totalH + this.ManageEquipment[i].manageEquipment_amount;
        }
    }

    this.desserts.push({
      name: "Ipad",
      amount: this.totalIP
    });

        this.desserts.push({
          name: "Writeboard pen",
          amount: this.totalWP
        });

            this.desserts.push({
              name: "Plug",
              amount: this.totalP
            });

                this.desserts.push({
                  name: "Headphones",
                  amount: this.totalH
                });



    },
    getManageEquipment() {
      http.get("/manageEquipments").then(response => {
        this.ManageEquipment = response.data;
      });
      this.submitted = true;
    },
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
  },
  mounted() {
    this.getManageEquipment();
    this.getEmployee();
    this.getEquipmentType();
    this.getEquipmentName();
},
watch:{
  ManageEquipment(){
    this.getEvent();
  }
}

}

/*eslint-disable */
</script>

<style lang="css" scoped>
</style>


<!-- <v-simple-table>
  <template v-slot:default>
    <thead>
      <tr>
        <th class="text-left">ชื่ออุปกรณ์</th>
        <th class="text-left">จำนวน</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in ManageEquipment" :key="item.status" >
        <td class = "text-left">{{ item.Ename }}</td>
        <td class = "text-left">{{ item.manageEquipment_amount }}</td>
      </tr>
    </tbody>
  </template>
</v-simple-table> -->

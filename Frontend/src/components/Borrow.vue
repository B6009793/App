<template>
  <v-container>
    <v-layout text-center wrap>
      <v-flex>
        <v-app id="inspire">
          <v-content>
            <v-container class="fill-height" fluid>
              <v-row align="center" justify="center">
                <v-col cols="12" sm="8" md="4">

                  <div v-if="BorrowCheck == true">
                    <v-alert type="success">ทำรายการเรียบร้อยแล้ว</v-alert>
                  </div>

                  <div v-if="checkAll == true">
                    <v-alert type="error">ใส่ข้อมูลหื้อหมดเด้</v-alert>
                  </div>

                  <div v-if="checkNote == true">
                    <v-alert type="error">ใส่ข้อความหื้อถูกเด้ (ถ้าบ่ใส่ หื้อใส่ " - ")</v-alert>
                  </div>

                  <v-card class="elevation-12">
                    <v-toolbar color="primary" dark flat>
                      <!-- กรอบข้างบนสีฟ้า -->
                      <v-toolbar-title>
                        <h2>Borrow</h2>
                      </v-toolbar-title>
                      <div class="flex-grow-1"></div>
                    </v-toolbar>

                    <v-form>
                      <v-card-text>
                        <!-- ComboBox รายชื่อลูกค้า -->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                              label="รายชื่อลูกค้า"
                              outlined
                              v-model="Borrow.customerId"
                              :items="customers"
                              item-text="name"
                              item-value="id"
                              :rules="[(v) => !!v || 'Item is required']"
                              required
                            ></v-select>
                          </v-col>
                        </v-row>

                        <!-- ComboBox อุปกรณ์-->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                              label="อุปกรณ์"
                              outlined
                              v-model="Borrow.equipId"
                              :items="Result"
                              item-text="name"
                              item-value="id"
                              :rules="[(v) => !!v || 'Item is required']"
                              required
                            ></v-select>
                          </v-col>
                        </v-row>
                        <!-- ComboBox รายชื่อพนักงาน -->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                              label="รายชื่อพนักงาน"
                              outlined
                              :readonly="true"
                              v-model="Borrow.employeeId"
                              :items="employees"
                              item-text="em_name"
                              item-value="em_id"
                              :rules="[(v) => !!v || 'Item is required']"
                              required
                            ></v-select>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-form>
                    <v-row justify="center">
                      <v-col cols="12" sm="6">
                        <v-text-field
                          label="Note"
                          outlined
                          v-model="Borrow.bornote"
                          :counter="50"
                          :rules="Notes"
                          required
                          maxlength="50"
                          size="10"
                        >></v-text-field>
                      </v-col>
                    </v-row>
                    <!-- manageEquipmentss {{manageEquipmentss}} -->

                    <v-card-actions>
                      <!-- ปุ่มกด -->
                      <div class="flex-grow-1"></div>
                      <v-btn color="success" @click="save">ยืมอุปกรณ์</v-btn>
                      <v-btn color="error" @click="clear">ล้างข้อมูล</v-btn>
                      <v-btn color="error" @click="clearNote">ลบ Note</v-btn>
                      <v-btn color="primary" @click="back">ย้อนกลับ</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-content>
        </v-app>
      </v-flex>
    </v-layout>
  </v-container>
</template>


<script>
import http from "../http-common";
export default {
  name: "Borrow",
  data() {
    return {
      manageEquipmentss: [],
      Borrow: {
        customerId: "",
        employeeId: "",
        equipId: "",
        bornote: ""
      },
      
      Notes: [
        v => !!v || "Note is required",
        v => (v && v.length <= 50) || "Note must be less than 10 characters"
      ],
      Borrows: [],
      customers: [],
      employees: [],
      equips: [],
      equipsFull: [],
      lock: "",
      emid: "",
      BorrowCheck: false,
      checkAll: false,
      checkNote: false,
      
    };
  },
  methods: {
    /* eslint-disable */
    ResultALL() {
      this.equipsFull = this.Result;
      // this.
    },
    lockemployee() {
      this.emid = this.$route.params.em;
      this.Borrow.employeeId = this.emid;
      this.lock = true;
    },
    back() {
      this.$router.push({ name: "Dashbord", params: { em: this.emid } });
    },
    SumAllEq() {
      this.manageEquipmentss.forEach((element1, index1) => {});
    },
    // ดึงข้อมูล Employee ใส่ combobox
    getEmployees() {
      http
        .get("/employees")
        .then(response => {
          this.employees = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    getCustomers() {
      http
        .get("/customer")
        .then(response => {
          this.customers = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    getManageequipments() {
      http
        .get("/findAmountManageEquipments")
        .then(response => {
          this.manageEquipmentss = response.data
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    save() {
      if (
        this.Borrow.employeeId == null ||
        this.Borrow.customerId == "" ||
        this.Borrow.equipId == "" ||
        this.Borrow.bornote == ""
      ) {
        this.checkAll = true;
        this.checkNote = false;
        this.BorrowCheck = false;
      } else if (
        this.Borrow.bornote.length > 50 ||
        !this.Borrow.bornote.match(/^([a-zA-z0-9ก-๙-|\u0020])+$/i)
      ) {
        this.checkNote = true;
        this.checkAll = false;
        this.BorrowCheck = false;
      } else {
        http
          .post(
            "/Borrow/" +
              this.Borrow.customerId +
              "/" +
              this.Borrow.employeeId +
              "/" +
              this.Borrow.equipId +
              "/" +
              this.Borrow.bornote,
            // "/" +
            // this.Borrow.bornoteId +
            this.Borrow
          )
          .then(response => {
            console.log(response);
            this.BorrowCheck = true;
            this.checkNote = false;
            this.checkAll = false;
                            
            // this.refreshList(this.equipId = false);
          })
          .catch(e => {
            console.log(e);
             
          });
        this.submitted = true;
      }
    },
    clear() {
      //this.$refs.form.reset();
      //this.$v.$reset();
      //this.Borrow.employeeId = "";
      this.Borrow.customerId = "";
      this.Borrow.equipId = "";
      this.BorrowCheck = false;
      this.clickBorrow = false;
      this.checkAll = false;
      this.checkNote = false;
    },
    clearNote() {
      //this.$refs.form.reset();
      //this.$v.$reset();
      this.Borrow.bornote = "";
      this.BorrowCheck = false;
      this.checkAll = false;
      this.checkNote = false;
    },
    refreshList() {
      // this.getEmployees();
      this.getCustomers();
      this.getManageequipments();
      this.BorrowCheck = false;
      this.checkAll = false;
      this.checkNote = false;
      //this.lockemployee();
    }
  },
  mounted() {
    this.getEmployees();
    this.getCustomers();
    this.getManageequipments();
    this.lockemployee();
    this.ResultALL();
    // this.bornote();
  },
  computed: {
    Result() {
      let result = new Array();
      for(let i = 0; i<this.manageEquipmentss.length;i++){
        if(this.manageEquipmentss[i][0] != 0){
          result.push({
            name: this.manageEquipmentss[i][1],
            id: this.manageEquipmentss[i][2]
          });
        }
      }
      return result;
    }
  }
};
/* eslint-disable no-console*/
</script>
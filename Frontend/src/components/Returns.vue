<template>
  <v-container>
    <v-layout text-center wrap>
      <v-flex>
        <v-app id="inspire">
          <v-content>
            <v-container class="fill-height" fluid>
              <v-row align="center" justify="center">
                <v-col cols="12" sm="6" md="6">
                 
                  <v-card class="elevation-12">
                    <v-toolbar color="purple lighten-3" dark flat>
                      <!-- กรอบข้างบนสีฟ้า -->
                      <v-toolbar-title>
                        <h2>Returns</h2>
                      </v-toolbar-title>
                      <div class="flex-grow-1"></div>
                    </v-toolbar>

                    <v-form>
                      <v-card-text>
                        <!-- ComboBox รายชื่อลูกค้า -->
                        <v-row justify="center">
                          <v-col cols="10">
                             <v-select
                               label="Customer"
                               dense
                               v-model="customerId"
                               :items="customer"
                               item-text="name"
                               item-value="id"
                               :rules="[(v) => !!v || 'Item is required']"
                               required
                              ></v-select>
                          </v-col>
                        </v-row>

                        <!-- ComboBox พนักงาน-->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                             label="Employee"
                             dense
                             :readonly="true"
                             v-model="Returns.employeeId"
                             :items="employees"
                             item-text="em_name"
                             item-value="em_id"
                             :rules="[(v) => !!v || 'Item is required']"
                             required
                            ></v-select>
                          </v-col>
                        </v-row>

                        <!-- ComboBox อุปกรณ์ -->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                             label="Borrow"
                             dense
                             v-model="BorrowID"
                             :items="BorrowsResult"
                             item-text="name"
                             item-value="bid"
                             :rules="[(v) => !!v || 'Item is required']"
                             required
                            ></v-select>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-form>
                        <!-- text field note-->
                    <v-row justify="center">
                      <v-col cols="12" sm="6">
                        <v-text-field
                         v-model="Returns.note"
                         label="หมายเหตุ(ต้องใส่ทุกครั้ง)"
                         :counter="45"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    
                    <v-card-actions >
                      <!-- ปุ่มกด -->
                        <div class="flex-grow-1" ></div>
                      <v-btn  @click="save"  color="green darken-2 "  >Save</v-btn>
                      <v-btn style="margin-left:12px;" @click="clear"  color="red accent-2 ">Clear</v-btn>
                      <v-btn style="margin-left:15px;" @click="back"  color="blue-grey darken-2">Back</v-btn>
                    </v-card-actions>
                    
                  </v-card>
                <p></p>
              <div v-if = "clickReturns == true">
              <div v-if = "returnsCheck == true">
              <v-alert type="success">Returns Completed</v-alert>
              </div>
              <div v-if = "returnsCheck == false">
              <v-alert type="error">Can't Returns</v-alert>
              </div>
              </div>

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

/*eslint-disable */
import http from "../http-common";
export default {
  name: "Returns",
  data() {
    return {
      BorrowID:0,
      manageequipmentREAL:[],
      commitMan:0,
      customerId:'',
      Returns: {
        
        employeeId: "",
        note: ""
      },
     // object: [], //ค่าที่มาจาการกรอง Receipt
      Borrows: [],
      BorrowID:[],
      BorrowbyCustomerId:[],
      items: [],
      customer: [],
      employees: [],
      BorrowsResult: [], // ค่าใหม่ใช้กับ combobox
      employee: "",
      returnsCheck: false,
      clickReturns: false,
    };
  },
  methods: {
    /* eslint-disable no-console */
    lockemployee(){
      this.emid = this.$route.params.em;
      this.employee = this.emid;
      this.Returns.employeeId  = this.employee;
      this.lock = true;
    },
    back(){
      this.$router.push({name: 'Dashbord' , params: {em: this.emid} }); 
    },
    getCustomersBorrowID(){
      http
      .get("/BorrowCustomerId"+
      "/" + 
      this.customerId
      )
      .then(response =>{
        this.BorrowbyCustomerId = [];
        this.BorrowbyCustomerId = response.data;
        this.getMangeEquiment();
        this.comboboxBorrow();
      }).catch(e =>{console.log(e)})
    },
    getCustomers() {
      http
        .get("/customer")
        .then(response => {
          this.customer = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    
    // ดึงข้อมูล Employee ใส่ combobox
    getEmployee() {
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

     getBorrows() {
      http
      .get("/BorrowTrue")
      .then(response => {
        this.Borrows = response.data;
        // this.getMangeEquiment();
        // this.comboboxBorrow();
      })
      .catch(e => {
        console.log(e);
      });
    },
    // เก็บค่า  bid borrow กับ mangeeqiument รวมกัน
    getMangeEquiment(){
      this.manageequipmentREAL = [];
      this.BorrowbyCustomerId.forEach((value) =>{
          this.manageequipmentREAL.push({id:value.bid,
            manid:value.manageequipment.manageEquipment_id});
      })
    },

     save() {
       this.clickReturns = true;
       if(this.customerId==''||this.Returns.employeeId==null||this.BorrowID==''){
       this.returnsCheck = false;
       }
       else if(this.Returns.note.length >50 ||
      !this.Returns.note.match(/^([a-zA-z0-9ก-๙-|\u0020])+$/i)){
        this.returnsCheck = false;
      }
       else{
      this.returnsCheck = true;
      console.log("Save");
      console.log( this.customerId );
      console.log( this.Returns.borrowId);
      console.log( this.Returns.employeeId);
      console.log(this.Returns.note);
      http
        .post(
          "/returns/" +
            this.customerId +
            "/" +
             this.BorrowID +
            "/" +
            this.Returns.employeeId +
            "/" + 
            this.commitMan +
            // this.Borrows[this.BorrowID-1].manageequipment.manageEquipment_id +
            "/" +
            this.Returns.note,
          this.Returns
        )
        .then(responses => {
          
         if(responses.status == 200){
           console.log("บันทึกแล้ว");
         }
         
        })
        .catch(e => {

          console.log(e);
        });
      }
    },
    //เอา borrow มาจัดรวมใส่ bombobox ใหม่
    comboboxBorrow(){
      this.BorrowsResult = [];
      this.BorrowbyCustomerId.forEach((value) =>{

        this.BorrowsResult.push({
          "bid": value.bid,
          "name": `หมายเลขการยืม `+value.bid+`  สิ่งของที่ยืม  `+value.manageequipment.equipmentName.name

        });
      
      })
    },
    commitMangeEquiment(){
      this.manageequipmentREAL.forEach((value,index)=>{
       if(this.BorrowID == value.id){
         this.commitMan = value.manid;
       }
     })
    },
    clear() {
    this.customerId = '' ;
    this.BorrowID = '' ;
    this.Returns.note = '' ;
    this.clickReturns = false;
    this.commitMan = "";

    }, 
     refreshList() {
    this.getEmployee();
    this.getCustomers();
    this.getBorrows();
    this.lockemployee();
      
    }
    
},
     
  mounted() {
    this.getBorrows();
    this.getEmployee();
    this.getCustomers();
    this.lockemployee();
    // this.getMangeEquiment();
    
  },
  
  watch:{
    Borrows(){
      this.comboboxBorrow();
    },
    BorrowID(){
     this.commitMangeEquiment();
    },
    customerId(){
      this.getCustomersBorrowID();
    }
  }
     
};

 
/*eslint-disable */
</script>

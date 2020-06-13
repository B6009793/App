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
                    <v-toolbar color="blue-grey lighten-2" dark flat>
                      <!-- กรอบข้างบนสีฟ้า -->
                      <v-toolbar-title>
                        <h2>Manage Status</h2>
                      </v-toolbar-title>
                      <div class="flex-grow-1"></div>
                    </v-toolbar>

                    <v-form>
                      <v-card-text>
                        <!-- ComboBox รายชื่อห้อง -->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                              label="Room"
                              outlined
                              v-model="ManageStatus.RoomId"
                              :items="Rooms"
                              item-text="room_name"
                              item-value="room_id"
                              :rules="[(v) => !!v || 'Item is required']"
                              required
                            ></v-select>
                          </v-col>
                        </v-row>

                        <!-- ComboBox สถานะ-->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                             label="Status"
                              outlined
                              v-model="ManageStatus.StatusId"
                              :items="Statuss"
                              item-text="status_name"
                              item-value="status_id"
                              :rules="[(v) => !!v || 'Item is required']"
                              required
                            ></v-select>
                          </v-col>
                        </v-row>

                        <!-- ComboBox รายชื่อพนักงาน -->
                        <v-row justify="center">
                          <v-col cols="10">
                            <v-select
                              label="Employee"
                              outlined
                              v-model="ManageStatus.EmployeeId"
                              :items="Employees"
                              item-text="em_name"
                              item-value="em_id"
                              :readonly="true"
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
                          label="Note"
                           outlined
                          v-model="ManageStatus.ManageStatus_note"
                          :counter="50"
                          required
                          maxlength="50"

                        >></v-text-field>
                      </v-col>
                    </v-row>
                    
                    <v-card-actions >
                      <!-- ปุ่มกด -->
                        <div class="flex-grow-1" ></div>
                      <v-btn  @click="saveManageStatus"  color="green darken-1"  >save</v-btn>
                      <v-btn style="margin-left:12px;" @click="clear"  color="red darken-1">clear</v-btn>
                      <v-btn style="margin-left:15px;" @click="Back"  color="blue-grey lighten-1">back</v-btn>
                    </v-card-actions>
                    
                  </v-card>
                   
                <p></p>
  
                <div v-if = "ManageCheck == true">
                  <v-alert type="success">บันทึกข้อมูลสำเร็จ</v-alert>
                </div>
                <div v-if = "Check1 == true">
                   <v-alert type="error">กรุณากรอกข้อมูลให้ครบถ้วน </v-alert>
                </div>
                <div v-if = "Check2 == true">
                  <v-alert type="error">กรุณากรอก note ให้ถูกต้อง (ความยาวห้ามเกิน 50 อักขระ , ห้ามใส่สัญลักษณ์ ,ถ้าหากไม่ป้อนใส่ -)</v-alert>
                </div>
                <div v-if = "Check3 == true">
                  <v-alert type="error">สถานะซ้ำ</v-alert>
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
import http from "../http-common";
export default {
  name: "ManageStatus",
  data() {
    return {
      ManageStatus: {
        RoomId: "",
        StatusId: "",
        EmployeeId: "",
        ManageStatus_note: ""

      },
      Rooms:[],
      Statuss:[],
      Employees:[],
      ManageStatuss:[],
      ManageStatusRoom:[],
      emid:'',
      // ManageStatus_note:[],
      Check1: false,
      Check2: false,
      Check3: false,
      clickManageStatus: false,
      ManageCheck: false
    };
  },
  methods: {
    lockemployee(){
      this.emid = this.$route.params.em;
      this.ManageStatus.EmployeeId  = this.emid;
      this.lock = true;
    },
    Back(){
      this.$router.push({name: 'Dashbord' , params: {em: this.emid} }); 
      },
    /* eslint-disable no-console */
    // ดึงข้อมูล Room ใส่ combobox
    getRooms() {
      http
        .get("/Room")
        .then(response => {
          this.Rooms = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    
    // ดึงข้อมูล Status ใส่ combobox
    getStatuss() {
      http
        .get("/Status")
        .then(response => {
          this.Statuss = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
     // ดึงข้อมูล Employee ใส่ combobox
    getEmployees() {
      http
        .get("/employees")
        .then(response => {
          this.Employees = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },

    getStatus_id(){
       http
        .get("/ManageStatus")
        .then(response => {
          this.ManageStatuss = response.data;
          console.log("MS",response.data);
          console.log("MS",this.ManageStatuss);
        })
        .catch(e => {
          console.log(e);
        });
    },

    getManageStatusRoom(){
      http
      .get("/ManageStatusRoom/"+this.ManageStatus.RoomId)
      //คิวรี่ room_id แะ Deleate_at = null มาเชค
       .then(response => {
          this.ManageStatusRoom = response.data;
          console.log("MSR"+this.ManageStatus.RoomId,response.data);
          console.log("ttt", typeof response.data);
          //เชคว่ามีสถานะอยู่ในดาต้าเบสหรือป่าวถ้าไม่มี เข้าไปเชค ถ้ามี เซฟเลย
          if(typeof response.data == 'object'){
            console.log("object");
            //เชคว่าสถานะที่พิมเข้ามา กับ สถานะของในดาต้าเบส เท่ากันหรือไม่  
            if(response.data.status.status_id == this.ManageStatus.StatusId){
              this.Check3 = true; 
              this.Check1 = false;
               this.Check2 = false;
               this.ManageCheck = false;
              // alert("สถานะของห้องซ้ำ");
            }else{
              //ถ้าไม่ซ้ำทำการเซฟลงดาต้าเบส
              console.log("ไม่ซ้ำ");
              this.savePost()
            }
          }else{
            console.log("else out");
            this.savePost()
          }
        })
        .catch(e => {
          console.log(e);
        });

    },

    savePost(){
      this.ManageCheck = true;
      http
        .post(
          "/ManageStatus/" +
            this.ManageStatus.RoomId+
            "/" +
            this.ManageStatus.StatusId+
            "/"+
             this.ManageStatus.EmployeeId+
            "/"+
            this.ManageStatus.ManageStatus_note
            //  ,this.ManageStatus
        )
        .then(response => {
          console.log(response,"cc");
          
        })
        .catch(e => {
          console.log("error",e);    
        });

        this.submitted = true;

        this.Check1 = false;
        this.Check2 = false;
        this.Check3 = false;
      
      
      // alert("บันทึกข้อมูลสำเร็จ");
      
    },
    
    // function เมื่อกดปุ่ม save
    saveManageStatus() {
      if(this.ManageStatus.RoomId == "" || 
      this.ManageStatus.StatusId == "" ||
      this.ManageStatus.EmployeeId == null||
      this.ManageStatus.ManageStatus_note == ""
       ){
        this.Check1 = true;
        this.ManageCheck = false;
        this.Check2 = false;
        this.Check3 = false;
        // alert("กรุณากรอกข้อมูลให้ครบถ้วน");
      }

      else if(this.ManageStatus.ManageStatus_note.length >50 ||
      !this.ManageStatus.ManageStatus_note.match(/^([a-zA-z0-9ก-๙-|\u0020])+$/i)

      ){
        
        this.Check2 =true;
        this.Check1 = false;
        this.Check3 = false;
        this.ManageCheck = false;
        // alert("กรุณากรอก note ให้ถูกต้อง (ความยาวห้ามเกิน 50 อักขระ , ห้ามใส่สัญลักษณ์ ,ถ้าหากไม่ป้อนใส่ -)");
      }
      

      else {
        //ไปทำการเชคว่า ซ้ำ หรือไม่ ???
          this.getManageStatusRoom();
      
      }
      
    },
    clear() {
      //this.$refs.form.reset();
      //this.$v.$reset();
      this.ManageStatus.RoomId= '';
      this.ManageStatus.StatusId= '';
      this.ManageStatus.ManageStatus_note='';
      this.Check1= false,
      this.Check2= false,
      this.Check3= false,
      this.ManageCheck= false
      

    },
    refreshList() {
      this.getRooms();
      this.getStatuss();

    }
    /* eslint-enable no-console */
  },
  mounted() {
    this.getRooms();
    this.getStatuss();
    this.getEmployees();
    this.getStatus_id();
    this.lockemployee();
    this.getManageStatus_note();
  },

};
</script>

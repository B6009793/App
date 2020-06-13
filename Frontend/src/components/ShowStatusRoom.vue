<template>
  <v-container>
    <v-layout text-center wrap>
      <v-flex>
        <v-app id="inspire">
          <v-content>
            <v-container class="fill-height" fluid>
              <v-row align="center" justify="center">
                <v-col cols="12" sm="4" md="8">
                 
                  <v-card class="elevation-12">
                    <v-toolbar color="blue-grey lighten-2" dark flat>
                      <!-- กรอบข้างบนสีฟ้า -->
                      <v-toolbar-title>
                        <h2>Show StatusRoom</h2>
                      </v-toolbar-title>
                      <div class="flex-grow-1"></div>
                    </v-toolbar>
                        <v-row justify="center">
                          <v-col cols="10">
                           <v-data-table
                           
                            :headers="headers"
                            :items="Shows"
                            :items-per-page="5"
                            class="elevation-2"
                        ></v-data-table>
                          </v-col>
                        </v-row>

                        
                    
                    <v-card-actions >
                      <!-- ปุ่มกด -->
                        <div class="flex-grow-1" ></div>
                      <v-btn style="margin-left:15px;" @click="Back"  color="blue-grey lighten-1">back</v-btn>
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
  data() {
    return {

      headers: [
        { text: "ห้อง", value: "room.room_name" },
        { text: "สถานะ", value: "status.status_name" },
        { text: "หมายเหตุ", value: "manageStatus_note" },
        
      ],
      Shows: []
    };
  },
  methods: {
   /* eslint-disable no-console */
   //ดึงข้อมูล ManageStatusroom  ที่ Delete at เป้น null
    getManageStatus() {
      http
        .get("/StatusRoom")
        .then(response => {
          this.Shows = response.data;
          console.log("bb",response.data);
          console.log("bbshow",this.Shows);
          
        })
        .catch(e => {
          console.log(e);
        });
    },
    //ปุ่มย้อนกลับ
    Back(){
      this.$router.push({name: 'Dashbord' , params: {em: this.emid} }); 
      }
  },

  mounted() {
    this.getManageStatus();
  }
};

</script>

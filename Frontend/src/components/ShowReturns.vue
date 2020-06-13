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
                    <v-toolbar color="purple lighten-3" dark flat>
                      <!-- กรอบข้างบนสีฟ้า -->
                      <v-toolbar-title>
                        <h2>Show Returns</h2>
                      </v-toolbar-title>
                      <div class="flex-grow-1"></div>
                    </v-toolbar>
                        <v-row justify="center">
                          <v-col cols="10">
                           <v-data-table
                             :headers="ShowReturns"
                             :items="Borrow"
                             :items-per-page="5"
                             class="elevation-1"
                        ></v-data-table>
                          </v-col>
                        </v-row>

                        
                    
                    <v-card-actions >
                      <!-- ปุ่มกด -->
                        <div class="flex-grow-1" ></div>
                      <v-btn style="margin-left:15px;" @click="back"  color="blue-grey darken-2">Back</v-btn>
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
/*eslint-disable */
import http from "../http-common";
export default{
  data(){
    return{
      Borrow:[] ,// 
      ShowReturns:[
        {
          text:'Id',
          value:'bid'
        },
        {
          text:'Customer',
          value:'customer.name'
        },
        {
          text:'Item',
          value:'manageequipment.equipmentName.name'
        },
        {
          text:'Employee',
          value:'employee.em_name'
        }
      ]
    }
  },
  methods:{
    getBorrows() {
      http
      .get("/BorrowTrue")
      .then(response => {
        this.Borrow = response.data;
      })
      .catch(e => {
        console.log(e);
      });
    },
    back(){
      this.$router.push({name: 'Dashbord' , params: {em: this.emid} }); 
    },
  },
  
   mounted() {
    this.getBorrows();
    
  }
}
</script>
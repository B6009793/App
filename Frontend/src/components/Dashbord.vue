<template>
    <v-container class="ml-auto mt-3 pt-3 mr-auto">
        
        <v-card justify="center" class="mx-auto" max-width="1000" color="blue-grey lighten-4">
            <h1 class="text mt-5 pt-5">
          <strong>Menu</strong>
            </h1>
            <v-form>
            <p></p>
            <v-row justify="center">
            <v-btn 
            @click="ManageEquipment"
            class="indigo lighten-3">
            Manage Equipment
            </v-btn>
            </v-row>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="ManageStatus"
            class="indigo lighten-3">
            Manage Status
            </v-btn>
            </v-row>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="ShowStatusRoom"
            class="indigo lighten-3">
            Show StatusRoom
            </v-btn>
            </v-row>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="Borrow"
            class="indigo lighten-3">
            Borrow
            </v-btn>
            </v-row>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="ShowBorrow"
            class="indigo lighten-3">
            ShowBorrow
            </v-btn>
            </v-row>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="Returns"
            class="indigo lighten-3">
            Returns
            </v-btn>
            </v-row>
            <p></p>
             <p></p>
            <v-row justify="center">
            <v-btn
            @click="ShowReturns"
            class="indigo lighten-3">
            Show Returns
            </v-btn>
            </v-row>
            <p></p>
            <p></p>
            <v-row justify="center">
            <v-btn
            @click="Logout"
            class="red">
            Logout
            </v-btn>
            </v-row>
            <p></p>
            <br/>
            </v-form>
        </v-card>
    </v-container>
</template>
<script>
export default {
    
    data(){
        return{
            emid:-66
        };
    },
 methods: {
getLockId(){
     this.emid = this.$route.params.em;
     
},
Logout(){
    this.$router.push("/")
},
ManageEquipment(){
     this.$router.push({name: 'ManageEquipment', params: {em: this.emid} });
 },
 ManageStatus(){
     this.$router.push({name: 'ManageStatus', params: {em: this.emid} });
 },
ShowStatusRoom(){
     this.$router.push({name: 'ShowStatusRoom', params: {em: this.emid} });
 },
Borrow(){
     this.$router.push({name: 'Borrow', params: {em: this.emid} });
 },
ShowBorrow(){
     this.$router.push({name: 'ShowBorrow', params: {em: this.emid} });
 },
 Returns(){
     this.$router.push({name: 'Returns', params: {em: this.emid} });
 },
 ShowReturns(){
     this.$router.push({name: 'ShowReturns', params: {em: this.emid} });
 }
},
mounted(){
    this.getLockId();
}
}
</script>
<style scoped>
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
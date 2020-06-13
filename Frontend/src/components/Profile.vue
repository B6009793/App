<template>
    <v-container class="ml-auto mt-3 pt-3 mr-auto">
        <v-card class="ml-auto mt-3 pt-3 mr-auto">
        <v-row justify="center">
            <v-col class="ml-10" cols="9" sm="6" md="5">
                <p>Customer Name : {{CustomerTitleName}}{{CustomerName}}</p>
                <p>Customer Email : {{CustomerEmail}}</p>
                <p>Customer Gender : {{CustomerGender}}</p>
                <p>Customer Phone : {{CustomerPhone}}</p>
                <p>Customer Status : {{CustomerStatus}}</p>
            </v-col>
        </v-row>
        </v-card>
    </v-container>
</template>
<script>
import http from "../http-common";
export default {
    name: "Profile",
    data() {
        return {
      profile: {
        customerId: ""
        // Customer
        },
        CustomerName: "",
        CustomerEmail: "",
        CustomerPassword: "",
        CustomerPhone: "",
        CustomerGender:"",
        CustomerTitleName: "",
        CustomerStatus: "",
        
    };
    },
    methods: {
        /* eslint-disable no-console */
        Show(){
            // this.profile.customerId = this.$route.params.cus;
            // this.profile.customerId = this.$store.getters.customer;
            http
                .get("/customer/" + this.$store.getters.customer)
                .then(response =>{
                    console.log(response);
                    // if(response.data != null){
                        this.CustomerName = response.data.name;
                        this.CustomerEmail = response.data.email;
                        this.CustomerPassword = response.data.password;
                        this.CustomerPhone = response.data.phone
                        this.CustomerGender = response.data.genders.gender;
                        this.CustomerTitleName = response.data.titleNames.title;
                        this.CustomerStatus = response.data.statuss.statusCus;
                        
                    // }

                })
                .catch(e => {
                console.log(e);
        });
        },
    },
mounted(){
    this.Show();
}
}
</script>
<style>
    p {
    font: 24px Copperplate Gothic, sans-serif;
    -width: 100%;
    }
</style>
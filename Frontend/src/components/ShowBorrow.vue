<template>
  <v-container>
    <v-layout text-center wrap>
      <v-flex>
        <v-app id="inspire">
          <v-content>
            <v-container class="fill-height" fluid>
              <v-row align="center" justify="center">
                <v-col cols="12" sm="12" md="10">
                  <v-card>
                    <v-card-title>
                      <v-row align="center" justify="center">
                        <v-col cols="12" sm="12" md="10">
                          <v-chip class="ma-1" color="primary" text-color="white">
                            <h2>ข้อมูลการยืมอุปกรณ์</h2>
                            <v-icon right>mdi-star</v-icon>
                          </v-chip>
                        </v-col>
                      </v-row>
                    </v-card-title>
                    <v-data-table :headers="headers" :items="items"></v-data-table>
                    <v-row align="center" justify="center">
                      <v-col cols="12" sm="6" md="6">
                        <v-btn class="ma-2" outlined color="indigo" @click="back">ย้อนกลับ</v-btn>
                      </v-col>
                    </v-row>
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
        {
          text: "ลำดับ",
          align: "left",
          sortable: false,
          value: "bid"
        },
        { text: "ชื่อลูกค้า", value: "customer.name" },
        { text: "อุปกรณ์ที่ยืม", value: "manageequipment.equipmentName.name" },
        { text: "พนักงานที่ทำรายการ", value: "employee.em_name" },
        { text: "Note", value: "bornote" },
        { text: "วัน/เวลาที่ยืม", value: "bordate" }
      ],
      items: []
    };
  },

  methods: {
    /* eslint-disable */
    back() {
      this.$router.push({ name: "Dashbord", params: { em: this.emid } });
    },
    getBorrow() {
      http
        .get("/Borrow")
        .then(response => {
          this.items = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.getBorrow();
  }
};
/* eslint-disable no-console*/
</script>
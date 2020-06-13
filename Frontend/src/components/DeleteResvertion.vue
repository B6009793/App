<template>
  <v-container>
    <h1 class="display-2 dodoboder">ยกเลิกจองห้องค้นคว้าออนไลน์</h1>
    <v-row class=" dodorow">
      <v-col cols=" 7 md-auto">
        <v-data-table
          :headers="headers"
          :items="data"
          :items-per-page="5"
          class="elevation-1"
        >

        </v-data-table>
      </v-col>
      <v-col cols="5 md-auto">
        <v-card
          outlined
          width="500"
          height="500"
        >
          <p class="ma-6">ชื่อผู้ใช้บริการ</p>
          <v-select
            :readonly="true"
            outlined
            class="selectboxdodo"
            label="Customer"
            :items="customers"
            item-text="name"
            item-value="id"
            v-model="lockcustomer"
          ></v-select>
          <p class="ma-6">รหัสการจองห้อง</p>
          <v-select
            outlined
            class="selectboxdodo"
            label="Resvertion"
            :items="selectdata"
            item-text="name"
            item-value="id"
            v-model="res_id"
          ></v-select>
          <v-btn
            class="buttondodo"
            v-on:click="PutcancelReservation"
          >ยืนยันการยกเลิก</v-btn>
          <v-alert
            type="success"
            class="alertSuccess "
            border="left"
            outlined
            text
            v-if="alertSuccessDodo"
          >
            <strong>ยกเลิกการจองห้องค้นคว้าสำเร็จ</strong>
          </v-alert>

          <v-alert
            type="error"
            class="alertSuccess "
            border="left"
            outlined
            text
            v-if="alertErrorDodo"
          >
            <strong>โปรดตรวจสอบข้อมูลของท่านใหม่</strong>
          </v-alert>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
/*eslint-disable */
import http from "../http-common";
import {
  LocalTime,
  LocalDate,
  LocalDateTime,
  DateTimeFormatter
} from "js-joda";

export default {
  data() {
    return {
      customers: [], // ดึง customer จาก DB
      resvertionCustomer: [], // ดึง resvertion จาก DB
      data: [], //  ทำการดึงข้อมูลจาก DB ใส่ table
      lockcustomer: "", // เอาไว้ล็อคชื่อผู้ใช้
      res_id: "", //  เอาไว้ยกเลิกการจองห้อง
      selectdata: [], // ข้อมูลการจองห้องของลูกค้าใน selectbox
      alertSuccessDodo: "",
      alertErrorDodo: "",
      idBook: 0,
      headers: [
        {
          text: "RESERVATIONS_ID",
          align: "left",
          sortable: true,
          value: "id"
        },
        { text: "START_TIME", value: "start" },
        { text: "END_TIME", value: "end" },
        { text: "ROOM_NAME", value: "room" },
        { text: "DATE", value: "date" }
      ]
    };
  },
  methods: {
    formatDate(dateconString) {
      let datecon = new Date(dateconString);
      let date = datecon.toLocaleDateString();
      return date;
    },
    formatTime(timecon) {
      let time = LocalDateTime.parse(timecon);

      time.toString();
      let gethour = time.hour(); // 23
      if (gethour < 10) {
        gethour = "0" + gethour;
      }
      let getminute = time.minute(); // 55
      if (getminute < 10) {
        getminute = "0" + getminute;
      }
      let getsecond = time.second(); // 42
      if (getsecond < 10) {
        getsecond = "0" + getsecond;
      }
      return gethour + ":" + getminute + ":" + getsecond;
      // this.count = time;
    },
    getdataTotableAndselectbox() {
      this.data = [];
      this.selectdata = [];
      this.resvertionCustomer.forEach((value, index) => {
        let starttime = this.formatTime(value.startTime);
        let endtime = this.formatTime(value.endTime);
        let datecon = this.formatDate(value.bookdate);
        this.data.push({
          id: value.reservations_id,
          start: starttime,
          end: endtime,
          room: value.manageStatus.room.room_name,
          date: datecon
        });

        this.idBook = value.reservations_id;

        this.selectdata.push({
          id: this.idBook,
          name: `รหัสการจองห้องที่   ` + this.idBook
        });
      });
    },
    getcustomer() {
      //ดึงลูกค้าทั้งหมดออกมา
      http
        .get("/customer")
        .then(response => {
          this.customers = response.data;
        })
        .catch(e => {
          console.log(e);
        });

      this.lockcustomer = this.$store.getters.customer;
    },
    getResvertionRoom() {
      //เอา รหัสลูกค้าไปค้นหาการจองห้อง
      http
        .get("/reservationCustomerId" + "/" + this.$store.getters.customer)
        .then(response => {
          this.resvertionCustomer = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    PutcancelReservation() {
      this.alertSuccessDodo = false;
      this.alertErrorDodo = false;
      if (
        (this.res_id != null || this.res_id != "") &&
        (this.lockcustomer != null || this.lockcustomer != "")
      ) {
        http
          .put("/reservationCustomer" + "/" + this.res_id)
          .then(response => {
            if (response.status == 200) {
              this.getResvertionRoom(); //ทำการค้นหาห้องที่ลูกค้าจองใหม่  และจะเอาข้อมูลใส่ตารางต่อด้วย
              this.alertSuccessDodo = true;
            }
          })
          .catch(e => {
            console.log(e);
            this.alertErrorDodo = true;
          });
      } else {
      }
    }
  },
  mounted() {
    this.getcustomer();
    this.getResvertionRoom();
  },
  //ทำทุกครั้งที่ค่าในตัวแปลเปลี่ยน
  watch: {
    resvertionCustomer() {
      this.getdataTotableAndselectbox();
    },
    res_id() {
      this.alertSuccessDodo = false;
      this.alertErrorDodo = false;
    }
  }
};

/*eslint-disable */
</script>
<style scoped>
.dodoboder {
  border: 4px solid #90caf9;
  border-radius: 40px 40px 40px 40px;
  padding: 1.5%;
  margin: 3% 0 3% 30%;
  width: 56%; /*  ความยาว border*/
}
.buttondodo {
  margin: 0 0 0 30%;
}
.dodorow {
  justify-content: center;
  align-content: center;
}
.alertSuccess {
  /* margin: 3% auto auto auto; */
  /* text-align: center; */
  width: 80%;
}
.selectboxdodo {
  margin: 40px 40px 0 40px;
}
</style>

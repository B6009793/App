<template>
  <v-app>
    <v-container grid-list-md>
      <v-layout row wrap>
        <v-flex text-center wrap>
          <h1 class="ma-5 display-4">จองห้องค้นคว้าออนไลน์</h1>
        </v-flex>
        <v-flex>
          <FullCalendar
            schedulerLicenseKey="GPL-My-Project-Is-Open-Source"
            :plugins="calendarPlugins"
            defaultView="resourceTimelineDay"
            resourceGroupField="building"
            minTime="08:00:00"
            maxTime="21:00:00"
            eventTextColor="white"
            :aspectRatio="1.5"
            :header="{
              left: 'today prev,next',
              center: 'title',
              right:
                'resourceTimelineDay,resourceTimelineThreeDay,resourceTimelineWeek,listWeekAll'
            }"
            :views="{
              resourceTimelineThreeDay: {
                type: 'resourceTimeline',
                duration: { days: 3 },
                buttonText: '3 days'
              },
              listWeekAll: {
                type: 'listWeek',
                durations: { week: 3 },
                buttonText: 'listweek'
              }
            }"
            :resourceColumns="[
              {
                labelText: 'Room',
                field: 'title'
              }
            ]"
            :resources="resourcesRoom"
            :events="event"
          />
        </v-flex>
        <v-flex md12>
          <v-layout row wrap>
            <v-flex md12>
              <v-layout row wrap>
                <v-flex md3 text-center wrap>
                  <br />
                  <h2 class="mt-2">ชื่อผู้ใช้บริการ</h2>
                </v-flex>
                <v-flex md7>
                  <br />

                  <v-select
                    v-if="lock"
                    outlined
                    :readonly="true"
                    class="mr-10"
                    :items="customers"
                    item-text="name"
                    item-value="id"
                    v-model="BookRoom.customerBook"
                    label="Customer"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex md12>
              <v-layout row wrap>
                <v-flex md3 text-center wrap>
                  <br />
                  <h2 class="mt-2">จุดประสงค์การใช้ห้อง</h2>
                </v-flex>
                <v-flex md7>
                  <br />
                  <v-select
                    outlined
                    multiple
                    class="mr-10"
                    :items="type"
                    item-text="name"
                    item-value="purposeRoom_id"
                    v-model="BookRoom.typeselect"
                    label="PurposeRoom"
                    :menu-props="{
                      closeOnClick: false,
                      closeOnContentClick: close,
                      disableKeys: true,
                      openOnClick: false,
                      maxHeight: 350
                    }"
                    ><template v-slot:prepend-item>
                      <v-list-item ripple @click="toggle">
                        <v-list-item-action>
                          <v-icon
                            :color="
                              BookRoom.typeselect.length > 0
                                ? 'indigo darken-4'
                                : ''
                            "
                            >{{ icon }}</v-icon
                          >
                        </v-list-item-action>
                        <v-list-item-content>
                          <v-list-item-title>Select All</v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider class="mt-2"></v-divider>
                    </template>
                    <template v-slot:append-item>
                      <v-divider class="mb-2"></v-divider>
                      <v-list-item ripple @click="eventClose">
                        <v-list-item-action>
                          <v-icon>
                            mdi-close
                          </v-icon>
                        </v-list-item-action>
                        <v-list-item-content>
                          <v-list-item-title>
                            Close SelectBox
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </template>
                  </v-select>
                </v-flex>
              </v-layout>
            </v-flex>
            <v-flex md12>
              <v-layout row wrap>
                <v-flex md3 text-center wrap>
                  <br />
                  <h2 class="mt-2">หมายเลขห้อง</h2>
                </v-flex>
                <v-flex md7>
                  <br />
                  <v-select
                    outlined
                    class="mr-10"
                    :items="Room"
                    item-text="room.room_name"
                    item-value="manageStatus_id"
                    v-model="BookRoom.Roomselect"
                    label="RoomNumber"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-flex>

            <v-flex md12>
              <v-layout row wrap>
                <v-flex md3 text-center wrap>
                  <br />
                  <h2 class="mt-2">เวลาเริ่มต้น</h2>
                </v-flex>
                <v-flex md7>
                  <br />
                  <v-select
                    outlined
                    class="mr-10"
                    :items="StartTime"
                    item-text="timestart"
                    item-value="idstarttime"
                    v-model="BookRoom.starttime"
                    label="StartTime"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-flex>

            <v-flex md12>
              <v-layout row wrap>
                <v-flex md3 text-center wrap>
                  <br />
                  <h2 class="mt-2">เวลาสิ้นสุด</h2>
                </v-flex>
                <v-flex md7>
                  <br />
                  <v-select
                    outlined
                    class="mr-10"
                    :items="EndTime"
                    item-text="timeend"
                    item-value="idendtime"
                    v-model="BookRoom.endtimeBook"
                    label="EndTime"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout>

          <v-btn
            style="margin:0 5% 0 25%"
            x-large
            justify="center"
            outlined
            color="primary"
            dark
            v-on:click="SaveRoom"
          >
            ยืนยันการจอง
            <v-icon large dark right>mdi-checkbox-marked-circle</v-icon>
          </v-btn>
          <v-btn
            style="margin:0 2% 0 2%"
            x-large
            outlined
            color="blue-grey"
            dark
            v-on:click="clear"
          >
            เคลียร์ข้อมูล
            <v-icon large dark right>mdi-cancel</v-icon>
          </v-btn>
        </v-flex>
        <v-alert
          type="success"
          class="alertSuccess "
          border="left"
          outlined
          text
          v-if="alertSuccessDodo"
        >
          <strong>บันทึกข้อมูลการจองห้องค้นคว้าสำเร็จ</strong>
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
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
/*eslint-disable */
import CustomerNavigation from "./CustomerNavigation";

import FullCalendar from "@fullcalendar/vue";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import listPlugin from "@fullcalendar/list";
import http from "../http-common";
import { formatDate } from "@fullcalendar/core";
import { LocalDate, LocalDateTime, LocalTime } from "js-joda";
export default {
  components: {
    FullCalendar,
    CustomerNavigation
  },
  data() {
    return {
      calendarPlugins: [resourceTimelinePlugin, listPlugin],
      StartTime: [
        { idstarttime: 1, timestart: "08:00:00" },
        { idstarttime: 2, timestart: "08:30:00" },
        { idstarttime: 3, timestart: "09:00:00" },
        { idstarttime: 4, timestart: "09:30:00" },
        { idstarttime: 5, timestart: "10:00:00" },
        { idstarttime: 6, timestart: "10:30:00" },
        { idstarttime: 7, timestart: "11:00:00" },
        { idstarttime: 8, timestart: "11:30:00" },
        { idstarttime: 9, timestart: "12:00:00" },
        { idstarttime: 10, timestart: "12:30:00" },
        { idstarttime: 11, timestart: "13:00:00" },
        { idstarttime: 12, timestart: "13:30:00" },
        { idstarttime: 13, timestart: "14:00:00" },
        { idstarttime: 14, timestart: "14:30:00" },
        { idstarttime: 15, timestart: "15:00:00" },
        { idstarttime: 16, timestart: "15:30:00" },
        { idstarttime: 17, timestart: "16:00:00" },
        { idstarttime: 18, timestart: "16:30:00" },
        { idstarttime: 19, timestart: "17:00:00" },
        { idstarttime: 20, timestart: "17:30:00" },
        { idstarttime: 21, timestart: "18:00:00" },
        { idstarttime: 22, timestart: "18:30:00" },
        { idstarttime: 23, timestart: "19:00:00" },
        { idstarttime: 24, timestart: "19:30:00" },
        { idstarttime: 25, timestart: "20:00:00" },
        { idstarttime: 26, timestart: "20:30:00" }
      ],
      EndTime: [
        { idendtime: 2, timeend: "08:30:00" },
        { idendtime: 3, timeend: "09:00:00" },
        { idendtime: 4, timeend: "09:30:00" },
        { idendtime: 5, timeend: "10:00:00" },
        { idendtime: 6, timeend: "10:30:00" },
        { idendtime: 7, timeend: "11:00:00" },
        { idendtime: 8, timeend: "11:30:00" },
        { idendtime: 9, timeend: "12:00:00" },
        { idendtime: 10, timeend: "12:30:00" },
        { idendtime: 11, timeend: "13:00:00" },
        { idendtime: 12, timeend: "13:30:00" },
        { idendtime: 13, timeend: "14:00:00" },
        { idendtime: 14, timeend: "14:30:00" },
        { idendtime: 15, timeend: "15:00:00" },
        { idendtime: 16, timeend: "15:30:00" },
        { idendtime: 17, timeend: "16:00:00" },
        { idendtime: 18, timeend: "16:30:00" },
        { idendtime: 19, timeend: "17:00:00" },
        { idendtime: 20, timeend: "17:30:00" },
        { idendtime: 21, timeend: "18:00:00" },
        { idendtime: 22, timeend: "18:30:00" },
        { idendtime: 23, timeend: "19:00:00" },
        { idendtime: 24, timeend: "19:30:00" },
        { idendtime: 25, timeend: "20:00:00" },
        { idendtime: 26, timeend: "20:30:00" },
        { idendtime: 27, timeend: "21:00:00" }
      ],
      BookRoom: {
        typeselect: [],
        starttime: "",
        endtimeBook: "",
        Roomselect: "",
        customerBook: ""
      },
      Room: [], // ห้องที่เปิดให้จองได้
      type: [], // ข้อมูลจุดประสงค์การจองห้อง
      customers: [], //ดึงข้อมูล สมาชิก
      resourcesRoom: [], //ลงข้อมูลห้องไว้ Table
      resvertion: [], //ดึงข้อมูลการจองม
      lock: false,
      checkTimeAndRoom: false, //เอาไว้ check ว่าห้องว่างมั้ย
      close: false, // เอาไว้  selectbox กดปุ่มปิดได้
      alertSuccessDodo: false, //แสดง v-alert Success
      alertErrorDodo: false, // แสดง v-alert Error
      event: []
    };
  },
  methods: {
    eventClose() {
      this.close = true;
      setTimeout(() => {
        this.close = false;
      }, 10);
    },
    lockemployee() {
      //ทำการล็อค Customer ที่เข้ามาหน้านี้
      this.BookRoom.customerBook = this.$store.getters.customer;
      this.lock = true;
    },

    //กด select All ใน combobox
    toggle() {
      this.$nextTick(() => {
        if (this.likesAllPurpose) {
          // หาก นั้นคือจุดประสงค์ทั้งหมดแล้ว ให้จุดประสงค์ที่เราเลือกเท่ากับว่าง
          this.BookRoom.typeselect = [];
        } else {
          // เคลียร์ค่าใน Array ก่อนทำการเพิ่มค่า
          this.BookRoom.typeselect = [];
          this.type.forEach((value, index) => {
            this.BookRoom.typeselect.push(this.type[index].purposeRoom_id);
          });
          // this.BookRoom.typeselect = this.type.slice();
        }
      });
    },

    // กดปุ่ม save
    SaveRoom() {
      // this.getCheckTimeAndRoom();
      this.checkTimeAndRoom = false;
      this.alertSuccessDodo = false;
      this.alertErrorDodo = false;
      http
        .get(
          "/reservationCheckTime" +
            "/" +
            this.StartTime[this.BookRoom.starttime - 1].timestart +
            "/" +
            this.EndTime[this.BookRoom.endtimeBook - 2].timeend +
            "/" +
            this.BookRoom.Roomselect
        )
        .then(response => {
          console.log(response);
          // this.checkTimeAndRoom = response.data;

          if (response.data == "") {
            this.checkTimeAndRoom = true;
          }
        })
        .catch(e => {
          console.log(e);
        });

      setTimeout(() => {
        if (
          this.CheckCorrectTime > 0 /*  && this.CheckCorrectTime <= 5 */ &&
          this.BookRoom.customerBook != "" &&
          this.BookRoom.Roomselect != "" &&
          this.BookRoom.typeselect != "" &&
          this.BookRoom.starttime != "" &&
          this.BookRoom.endtimeBook != "" &&
          // this.checkTimeAndRoom == null

          this.checkTimeAndRoom == true
        ) {
          http
            .post("/Reservation", {
              customerid: this.BookRoom.customerBook,
              roomid: this.BookRoom.Roomselect,
              purosebook: this.BookRoom.typeselect,
              start: this.StartTime[this.BookRoom.starttime - 1].timestart,
              end: this.EndTime[this.BookRoom.endtimeBook - 2].timeend
            })
            .then(res => {
              if (res.status == 200) {
                //เมื่อมีการบันทึกสำเร็จ
                // ข้อความว่าบันทึกสำเร็จ
                this.alertSuccessDodo = true;
                //ให้ดึงห้องกลับมาใหม่
                this.getResvertionRoom();
                //ให้ดึงการจองไปใหม่อีกครั้ง
                this.getEventInTable();
                //ให้เคลียร์ข้อมูลใน combobox ออกบ้างส่วน
                this.clear();
              }
            })
            .catch(error => {
              console.error(error);
              this.alertErrorDodo = true;
            });
        } else {
          this.alertErrorDodo = true;
          this.clear();
        }
      }, 1000);
    },

    clear() {
      // ลบข้อมูลออกจากcombobox
      this.BookRoom.typeselect = [];
      this.BookRoom.starttime = "";
      this.BookRoom.endtimeBook = "";
      this.BookRoom.Roomselect = "";
    },
    getCustomers() {
      http
        .get("/customer")
        .then(response => {
          this.customers = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    getTypePurposeBook() {
      //ดึงวัตถุประสงค์ของห้องมาใส่ combobox
      http
        .get("/PurposeRoom")
        .then(response => {
          this.type = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    getStatusRoom() {
      //ดึงcustomer มาใส combobox
      http
        .get("/ManageStatusD")
        .then(response => {
          this.Room = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },

    getResvertionRoom() {
      http
        .get("/reservationses")
        .then(response => {
          this.resvertion = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    getEventInTable() {
      /**
       * เป็นการนำการจองห้องที่ได้จาก DB มาใส่ในตัวแปร Array ชื่อ event เพื่อแสดง
       * ข้อมูลว่ามีใครที่จองอยู่บ้าง
       */
      this.event = [];
      let i = 1;
      this.resvertion.forEach(value => {
        this.event.push({
          id: ++i,
          title: value.customer.name,
          start: value.startTime,
          end: value.endTime,
          resourceId: value.manageStatus.room.room_id
        });
      });
    }
  },
  mounted() {
    this.getTypePurposeBook();
    this.getStatusRoom();
    this.getCustomers();
    this.getResvertionRoom();
    this.lockemployee();
    this.getEventInTable();
  },
  computed: {
    /*
    getter medthod ทำหน้าที่ตรวจสอบค่าเมื่อมีการเปลี่ยนแปลง
    ให้กระทำตามเงื่อนไขที่เราเขียนแล้ว return ออกไป

    ไม่มีการรับ parameter เข้ามา
    */

    likesAllPurpose() {
      //ความยาว ของจุดประสงที่เราเลือก เท่ากับ จุดประสงที่มีทั้งหมดมั้ย
      return this.BookRoom.typeselect.length === this.type.length;
    },
    likesSomePurpose() {
      // check ว่ามีของจุดประสงค์มั้ย
      return this.BookRoom.typeselect.length > 0 && !this.likesAllPurpose;
    },
    icon() {
      if (this.likesAllPurpose) return "mdi-close-box";
      if (this.likesSomePurpose) return "mdi-minus-box";
      return "mdi-checkbox-blank-outline";
    },
    CheckCorrectTime() {
      // ตรวจสอบว่า เวลาที่จะนำไปบันทึกมันถูกต้องมั้ย เช่น 08.00-10.00  ไม่ใช่ 10.00-08.00
      return this.BookRoom.endtimeBook - this.BookRoom.starttime;
    }
    // CheckcommitTime() {
    //   if (this.CheckCorrectTime > 0 && this.CheckCorrectTime <= 5) return true;
    //   else return false;
    // }
  },
  watch: {
    /**
     * จะตรวจสอบตัวแปร หรือ medthod นั้น ว่ามีการเปลี่ยนแปลงอะไรหรือไม่ ถ้ามีจะกระทำตามที่เราเขียนโค้ดไว้
     * เหมาะจะทำการเขียนเพื่อเชื่อมต่อหลังบ้าน เพราะมันจะทำงานแบบ Asynchronous
     */
    Room() {
      /**
       * เมื่อ Room มีการเปลี่ยนแปลงค่า ทำให้ต้องแสดงว่าห้องที่มาจาก DB มีห้องไหนบ้าง (ที่เปิดใช้งาน)
       */
      this.resourcesRoom = [];
      this.Room.forEach((value, index) => {
        // console.log(value.room.room_name.indexOf("S01"));
        if (value.room.room_name.indexOf("S") == 0) {
          //เช็คว่าเจอจริงมั้ย
          console.log("เจอ S แล้ว");
          this.resourcesRoom.push({
            id: value.room.room_id,
            building: "ห้องเดี่ยว",
            title: `หมายเลขห้อง ` + value.room.room_name
          });
        } else if (value.room.room_name.indexOf("G") == 0) {
          //เช็คว่าเจอจริงมั้ย
          console.log("เจอ G แล้ว");
          this.resourcesRoom.push({
            id: value.room.room_id,
            building: "ห้องกลุ่ม",
            title: `หมายเลขห้อง ` + value.room.room_name
          });
        } else if (value.room.room_name.indexOf("P") == 0) {
          //เช็คว่าเจอจริงมั้ย
          console.log("เจอ P แล้ว");
          this.resourcesRoom.push({
            id: value.room.room_id,
            building: "ห้องประชุม",
            title: `หมายเลขห้อง ` + value.room.room_name
          });
        }
      });
    },
    resvertion() {
      // เมื่อมีการจองเข้ามาจะนำการจองห้องไปใสในตัวแปรที่ชื่อ event เพื่อแสดงในตาราง
      this.getEventInTable();
    }
  }
};
</script>

<style lang="css">
@import "~@fullcalendar/core/main.css";
@import "~@fullcalendar/timeline/main.css";
@import "~@fullcalendar/resource-timeline/main.css";
@import "~@fullcalendar/list/main.css";
.alertSuccess {
  margin: 3% auto auto auto;
  text-align: center;
  width: 50%;
}
</style>

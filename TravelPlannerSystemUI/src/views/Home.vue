<template>
    <Row class="layout-base">
        <Col span="17">
            <Layout>
                <Content class="layout-content">
                    <h1>Bus Travel planner</h1>
                    <Card style="width:100%" v-for="(route, index) in routeList">
                        <div class="title header">
                            {{ route.routeName }}
                        </div>
                        <Table border :columns="stopNameList[index]" :data="tripsList[index]"
                               :key="Math.random()"></Table>
                    </Card>
                </Content>
            </Layout>
        </Col>
        <Col span="7">
            <layout>
                <Content class="layout-content">
                    <h1>Some search function</h1>
                    <Card style="width:100%">
                        <p class="title header">
                            Add new stop to route
                        </p>
                        <Form :model="stopToRoute" :label-width="180">
                            <FormItem label="Stop Name">
                                <Input v-model="stopToRoute.stopName" placeholder="Enter new stop name"
                                       clearable="true"></Input>
                            </FormItem>
                            <FormItem label="Route Name">
                                <Select v-model="stopToRoute.routeIndex" placeholder="Choose the route"
                                        clearable="true" not-found-text="No data">
                                    <Option :value="routeIndex" v-for="(route, routeIndex) in routeList">
                                        {{ route.routeName }}
                                    </Option>
                                </Select>
                            </FormItem>
                            <FormItem label="Before Stop Name">
                                <Select v-model="stopToRoute.beforeStopIndex" placeholder="Choose the before stop"
                                        clearable="true" not-found-text="No data">
                                    <Option :value="stopIndex"
                                            v-for="(stop_map, stopIndex) in stopNameList[stopToRoute.routeIndex]">
                                        {{ stop_map.key }}
                                    </Option>
                                </Select>
                            </FormItem>
                            <Button type="primary" @click="addStopInRoute">Add Stop</Button>
                        </Form>
                    </Card>
                    <Card style="width:100%">
                        <p class="title header">
                            All serving routes given stop
                        </p>
                        <Form :model="searchRoute_stop" :label-width="180">
                            <FormItem label="Stop Name">
                                <Select v-model="searchRoute_stop.stopName" placeholder="Choose the stop"
                                        clearable="true" not-found-text="No data">
                                    <Option :value="stop.stopName" v-for="(stop, stop_index) in stopList">
                                        {{ stop.stopName }}
                                    </Option>
                                </Select>
                            </FormItem>
                            <Button type="primary" @click="allRoutesAtStop">Search</Button>
                        </Form>
                    </Card>
                    <Card style="width:100%">
                        <p class="title header">
                            All serving routes given stop and time
                        </p>
                        <Form :model="searchRoute_stop_time" :label-width="180">
                            <FormItem label="Stop Name">
                                <Select v-model="searchRoute_stop_time.stopName" placeholder="Choose the stop"
                                        clearable="true" not-found-text="No data">
                                    <Option :value="stop.stopName" v-for="(stop, stop_index) in stopList">
                                        {{ stop.stopName }}
                                    </Option>
                                </Select>
                            </FormItem>
                            <FormItem label="Time">
                                <TimePicker v-model="searchRoute_stop_time.time" format="HH:mm"
                                            placeholder="Select time"
                                            style="width: 112px" :steps="[1, 10, 0]" clearable="true"/>
                            </FormItem>
                            <Button type="primary" @click="allRoutesAtStopAndTime">Search</Button>
                        </Form>
                    </Card>
                    <Card style="width:100%">
                        <p class="title header">
                            Show the schedule in stop
                        </p>
                        <Form :model="schedule_stop" :label-width="180">
                            <FormItem label="Stop Name">
                                <Select v-model="schedule_stop.stopName" placeholder="Choose the stop" clearable="true"
                                        not-found-text="No data">
                                    <Option :value="stop.stopName" v-for="(stop, stop_index) in stopList">
                                        {{ stop.stopName }}
                                    </Option>
                                </Select>
                            </FormItem>
                            <Modal v-model="schedule_stop.modalShowing" title="All schedule in stopName" ok-text="OK"
                                   cancel-text="Cancel" @on-ok="changeModal" @on-cancel="changeModal">
                                <p v-for="schedule in scheduleInStops">{{ schedule }}</p>
                            </Modal>
                            <Button type="primary" @click="allScheduleInStop">Search</Button>
                        </Form>
                    </Card>
                    <Button type="primary" @click="getRouteList">RouteList</Button>
                </Content>
            </layout>
        </Col>
    </Row>
</template>

<script>
import api from "../api"
import {
    Button,
    Card,
    Collapse,
    Content,
    Divider,
    FormItem,
    Icon,
    Layout, Modal,
    Panel,
    Row,
    Sider,
    Table,
    TimePicker
} from "view-ui-plus";

export default {
    name: 'Home',
    components: {Modal, TimePicker, FormItem, Row, Content, Layout, Card, Icon, Panel, Collapse, Button, Table},
    data() {
        return {
            routeList: [],
            stopNameList: [],
            tripsList: [],
            stopList: [],
            stopToRoute: {
                stopName: "",
                routeIndex: null,
                beforeStopIndex: null,
            },
            searchRoute_stop: {
                stopName: ""
            },
            searchRoute_stop_time: {
                stopName: "",
                time: ""
            },
            schedule_stop: {
                stopName: "",
                modalShowing: false
            },
            scheduleInStops: []
        }
    },
    mounted() {
        this.getRouteList();
        this.getStopList();
    },
    methods: {
        getRouteList() {
            api.getRouteList().then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    if (message == null) {
                        message = "Loading route list is successful!"
                    }
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.routeList = responseData.data
                    this.resolveTable()
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            });
        },
        resolveTable() {
            this.stopNameList = []
            this.tripsList = []
            this.routeList.forEach(route => {
                var stopList = []
                route.stopsInRoute.forEach(stop => {
                    stopList.push({title: stop.stopName, key: stop.stopName})
                })
                this.stopNameList.push(stopList)
            })
            this.routeList.forEach(route => {
                var tripList = []
                route.trips.forEach(trip => {
                    var timeTableOfTrip = trip.timeTableOfTrip;
                    var entries = Object.entries(timeTableOfTrip)
                    var timeTableMap = {}
                    entries.forEach(entity => {
                        timeTableMap[entity[1].stopName] = entity[0]
                    })
                    tripList.push(timeTableMap)
                })
                this.tripsList.push(tripList)
            })
        },
        getStopList() {
            api.getStopList().then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    if (message == null) {
                        message = "Loading stop list is successful!"
                    }
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.stopList = responseData.data
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            })
        },
        addStopInRoute() {
            var stopName = this.stopToRoute.stopName
            var routeName = this.routeList[this.stopToRoute.routeIndex].routeName
            var beforeStopName
            if (this.stopToRoute.beforeStopIndex === null) {
                beforeStopName = "";
            } else {
                beforeStopName = this.routeList[this.stopToRoute.routeIndex].stopsInRoute[this.stopToRoute.beforeStopIndex].stopName;
            }
            api.addStop(stopName, routeName, beforeStopName).then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.$nextTick(() => {
                        this.getRouteList()
                    })
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            })
        },
        allRoutesAtStop() {
            api.allRoutesAtStop(this.searchRoute_stop.stopName).then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    if (message == null) {
                        message = "Searching successful!"
                    }
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.routeList = responseData.data
                    this.$nextTick(() => {
                        this.resolveTable()
                    })
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            })
        },
        allRoutesAtStopAndTime() {
            api.allRoutesAtStopAndTime(this.searchRoute_stop_time.stopName, this.searchRoute_stop_time.time).then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    if (message == null) {
                        message = "Searching successful!"
                    }
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.routeList = responseData.data
                    this.$nextTick(() => {
                        this.resolveTable()
                    })
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            })
        },
        allScheduleInStop() {
            api.allScheduleInStop(this.schedule_stop.stopName).then(response => {
                var responseData = response.data;
                var code = responseData.code
                var message = responseData.message
                if (code === 200) {
                    if (message == null) {
                        message = "Searching successful!"
                    }
                    this.$Message["success"]({
                        background: true,
                        content: message
                    });
                    this.scheduleInStops = responseData.data;
                    this.schedule_stop.modalShowing = true;
                } else {
                    this.$Message["error"]({
                        background: true,
                        content: message
                    });
                }
            }).catch(error => {
                this.$Message["error"]({
                    background: true,
                    content: "Check for empty data!"
                });
            })
        },
        changeModal() {
            this.schedule_stop.modalShowing = false;
        }
    }
}
</script>
<style scoped>
.layout-base {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    color: black;
    text-align: center;
    overflow: hidden;
}

.layout-content {
    min-height: 100px;
    line-height: 100px;
}

.header {
    font-size: 20px;
}
</style>

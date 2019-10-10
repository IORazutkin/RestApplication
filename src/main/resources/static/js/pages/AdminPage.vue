<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card class="mx-5">
        <v-card-title>
            <v-tabs color="teal" grow>
                <v-tab @click="receiptsClick">Receipts</v-tab>
                <v-tab>Services</v-tab>
                <v-tab-item>
                    <v-row align="center" dense>
                        <v-col>
                            <v-switch
                                    v-model="unpaidOnly"
                                    color="teal"
                                    inset
                                    label="Unpaid only">
                            </v-switch>
                        </v-col>
                        <v-col cols="6">
                            <v-select
                                    v-model="select"
                                    :items="items"
                                    color="teal"
                                    label="Type of service"
                                    append-icon="arrow_drop_down">
                            </v-select>
                        </v-col>
                        <v-col>
                            <v-btn class="white--text float-right" width="50%" color="teal" @click="showClick">Show</v-btn>
                        </v-col>
                    </v-row>
                    <v-divider/>
                    <v-dialog v-model="receiptDialog" width="500">
                        <template v-slot:activator="{ on }">
                            <v-btn @click="selectHouseType = '', houseList = [], el = 1"
                                   color="teal" text width="100%" v-on="on">
                                <v-icon left>add</v-icon>Add new receipts
                            </v-btn>
                        </template>

                        <v-stepper v-model="el" :editable="el == 2">
                            <v-stepper-header>
                                <v-stepper-step editable :complete="el > 1" step="1">Houses</v-stepper-step>

                                <v-divider></v-divider>

                                <v-stepper-step step="2">Apartments<small>Optional</small></v-stepper-step>
                            </v-stepper-header>
                            <v-stepper-items>
                                <v-stepper-content step="1">
                                    <v-text-field
                                            v-model="houseAddress"
                                            label="Search"
                                            append-icon="search"
                                            color="teal"/>
                                    <v-list v-if="houses.length > 0" class="mb-5">
                                        <v-list-item
                                                v-for="(house, i) in houses"
                                                :key="i">
                                            <v-list-item-action>
                                                <v-card outlined elevation="1">
                                                    <v-checkbox v-model="houseList[i]"
                                                                on-icon="done"
                                                                color="teal">
                                                    </v-checkbox>
                                                </v-card>
                                            </v-list-item-action>
                                            <v-list-item-content>{{ house.address }}</v-list-item-content>
                                            <v-list-item-action>
                                                <v-btn text icon color="teal" @click="housesNext(house.address)">
                                                    <v-icon>apartment</v-icon>
                                                </v-btn>
                                            </v-list-item-action>
                                        </v-list-item>
                                    </v-list>
                                    <v-row v-else
                                           class="mt-4 mb-9"
                                           justify="center">
                                        <span class="title font-weight-light">No houses</span>
                                    </v-row>
                                    <v-row align="center">
                                        <v-col cols="8">
                                            <v-select
                                                    v-model="selectHouseType"
                                                    :items="services"
                                                    color="teal"
                                                    label="Type of service"
                                                    append-icon="arrow_drop_down">
                                                <template v-slot:item="{item, on}">
                                                    <v-list-item v-on="on" :disabled="!item.forHouse">
                                                        <v-list-item-content>{{ item.title }}</v-list-item-content>
                                                    </v-list-item>
                                                </template>
                                                <template v-slot:selection="{item}">
                                                    <span class="ml-3">{{ item.title }}</span>
                                                </template>
                                            </v-select>
                                        </v-col>
                                        <v-col cols="4">
                                            <v-btn :disabled="selectHouseType == '' || !isHouse"
                                                   class="white--text" color="teal" @click="houseClick">Add receipts</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-stepper-content>

                                <v-stepper-content step="2">
                                    <v-text-field
                                            v-model="apartmentNumber"
                                            label="Search"
                                            append-icon="search"
                                            color="teal"
                                    ></v-text-field>
                                    <v-list class="mb-5" tile>
                                        <v-list-item-group color="teal">
                                            <v-list-item
                                                    v-model="isApartment"
                                                    v-for="(apartment, i) in apartments"
                                                    @change="apartmentList = apartment"
                                                    :key="i">
                                                <v-list-item-content >
                                                    <v-list-item-title v-text="apartment.apartmentNumber"/>
                                                </v-list-item-content>
                                            </v-list-item>
                                        </v-list-item-group>
                                    </v-list>
                                    <v-row class="mx-0">
                                        <v-select
                                                v-model="selectApartmentType"
                                                :items="services"
                                                color="teal"
                                                label="Type of service"
                                                append-icon="arrow_drop_down">
                                            <template v-slot:item="{item, on}">
                                                <v-list-item v-on="on" :disabled="item.forHouse">
                                                    <v-list-item-content>{{ item.title }}</v-list-item-content>
                                                </v-list-item>
                                            </template>
                                            <template v-slot:selection="{item}">
                                                <span class="ml-3">{{ item.title }}</span>
                                            </template>
                                        </v-select>
                                    </v-row>
                                    <v-row align="center">
                                        <v-col cols="8">
                                            <v-text-field
                                                    v-model="serviceValue"
                                                    :rules="[serviceValueRule]"
                                                    color="teal"
                                                    label="Value"
                                                    class="mt-2"
                                                    :suffix="selectApartmentType.unit">
                                            </v-text-field>
                                        </v-col>
                                        <v-col cols="4">
                                            <v-btn :disabled="selectApartmentType == ''
                                                || !isApartment
                                                || serviceValue == ''
                                                || !serviceValueError"
                                                   class="white--text" color="teal" @click="apartmentClick">Add receipts</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-stepper-content>
                            </v-stepper-items>
                        </v-stepper>
                    </v-dialog>
                    <v-row v-if="receipts.length == 0"
                           class="mt-10 mb-7"
                           justify="center">
                        <span class="title font-weight-light">No receipts</span>
                    </v-row>
                    <v-data-table v-else
                                  dense
                                  :items="receipts"
                                  item-key="receiptId">
                        <template v-slot:item="{item}">
                            <v-footer class="ma-3 subtitle-1" elevation="2"
                                    :color="item.paymentDate ? 'green lighten-5' : 'red lighten-5'">
                                <v-row align="center" style="height: 60px; text-align: center;">
                                    <v-col style="text-align: left;" cols="2"><b>RECEIPT №{{ item.receiptId }}</b></v-col>
                                    <v-divider vertical/>
                                    <v-col cols="3">
                                        {{ item.formationDate.slice(0,10) }} /
                                        <span v-if="item.paymentDate">{{ item.paymentDate.slice(0,10) }}</span>
                                        <span v-else class="red--text"><b>null</b></span>
                                    </v-col>
                                    <v-divider vertical/>
                                    <v-col cols="3">
                                        {{ item.apartment.house.address }}({{ item.apartment.apartmentNumber }})
                                    </v-col>
                                    <v-divider vertical/>
                                    <v-col cols="3">
                                        {{ item.service.title }}
                                    </v-col>
                                    <v-divider vertical/>
                                    <v-col>
                                        <v-btn :color="item.paymentDate ? 'teal' : 'red'"
                                               :disabled="item.paymentDate ? true : false"
                                               @click="deleteReceipt(item)"
                                               text icon>
                                            <v-icon>delete</v-icon>
                                        </v-btn>
                                    </v-col>
                                </v-row>
                            </v-footer>
                        </template>

                    </v-data-table>
                </v-tab-item>
                <v-tab-item>
                    <v-divider/>
                    <div class="text-center">
                        <v-dialog v-model="dialog" width="500">
                            <template v-slot:activator="{ on }">
                                <v-btn @click="serviceId = ''" color="teal" text width="100%" v-on="on">
                                    <v-icon left>add</v-icon>Add new service
                                </v-btn>
                            </template>

                            <v-card>
                                <v-card-title>New service</v-card-title>
                                <v-card-text>
                                    <v-row class="mx-0">
                                        <v-text-field
                                                v-model="serviceTitle"
                                                color="teal"
                                                label="Service name"
                                                clearable clear-icon="clear"
                                                class="mt-2">
                                        </v-text-field>
                                    </v-row>
                                    <v-row>
                                        <v-col>
                                            <v-text-field
                                                    v-model="servicePrice"
                                                    color="teal"
                                                    label="Price"
                                                    append-icon="attach_money"
                                                    clearable clear-icon="clear"
                                                    class="mt-2">
                                            </v-text-field>
                                        </v-col>
                                        <v-col>
                                            <v-text-field
                                                    v-model="serviceUnit"
                                                    color="teal"
                                                    label="Unit"
                                                    clearable clear-icon="clear"
                                                    class="mt-2">
                                            </v-text-field>
                                        </v-col>
                                    </v-row>
                                    <v-row class="mx-0">
                                        <v-switch color="teal" v-model="serviceForHouse"
                                                  inset label="Service for the whole house"/>
                                    </v-row>
                                </v-card-text>
                                <v-card-actions>
                                    <div class="flex-grow-1"></div>
                                    <v-btn color="teal" class="white--text" @click="addService">Save service</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </div>
                    <v-row v-if="services.length == 0"
                           class="mt-10 mb-7"
                           justify="center">
                        <span class="title font-weight-light">No services</span>
                    </v-row>
                    <v-simple-table v-else max-height="400" fixed-header>
                        <template v-slot:default>
                            <thead>
                            <tr>
                                <th class="text-left">Service name</th>
                                <th class="text-left">Price</th>
                                <th class="text-left">Unit</th>
                                <th class="text-right">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="service in services" :key="service.serviceId">
                                <td>{{ service.title }}</td>
                                <td>${{ service.price.toFixed(2) }}</td>
                                <td>{{ service.unit }}</td>
                                <td class="text-right">
                                    <v-btn @click="editService(service)" text icon><v-icon color="teal">edit</v-icon></v-btn>
                                </td>
                            </tr>
                            </tbody>
                        </template>
                    </v-simple-table>
                </v-tab-item>
            </v-tabs>
        </v-card-title>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                el: 0,

                houseAddress: '',
                address: '',
                houseList: [],
                houses: [],
                isHouse: false,

                apartmentNumber: '',
                isApartment: false,
                apartments: [],
                apartmentList: '',

                serviceValue: '',
                serviceValueError: false,

                dialog: false,
                receiptDialog: false,

                serviceId: '',
                serviceTitle: '',
                servicePrice: '',
                serviceUnit: '',
                serviceForHouse: false,

                receiptDeleteDialog: '',

                select: 'All',
                unpaidOnly: false,

                receipts: [],
                services: [],
                selectHouseType: '',
                selectApartmentType: '',
                items: [
                    'All'
                ],
                serviceValueRule: v => {
                    const pattern = /^[1-9][0-9]*((\.|,)[0-9]+)?$/
                    this.serviceValueError = pattern.test(v)
                    return this.serviceValueError || 'Invalid number'
                }
            }
        },
        watch: {
            houseAddress(v) {
                this.houseList = []
                this.$resource('/houses{/id}').get({address: v}).then(result => this.houses = result.data)
            },
            apartmentNumber(v) {
                this.$resource('/apartments{/id}').get({address: this.address, number: v})
                    .then(result => this.apartments = result.data)
            },
            houseList() {
                var isHouse = false
                this.houseList.forEach(item => isHouse = isHouse || item)
                this.isHouse = isHouse
            }
        },
        created() {
            const item = { text: 'admin page', disabled: false, href: '/admin'}
            breadcrumbs.push(item)

            this.$resource('/services{/id}').get({}).then(result => {
                result.data.forEach(service => {
                    this.services.push(service)
                    this.items.push(service.title)
                })
            })

            this.$resource('/houses{/id}').get({}).then(result => this.houses = result.data)

            this.$resource('/receipts{/id}').get({}).then(result => this.receipts = result.data)
        },
        methods: {
            addService() {
                const service = { title: this.serviceTitle,
                                  price: this.servicePrice,
                                  unit: this.serviceUnit,
                                  forHouse: this.serviceForHouse
                }
                if (service.title != ''
                        && service.price != '') {
                    if (this.serviceId == '') {
                        this.$resource('/services{/id}').save({}, service).then(result => {
                            this.services.push(result.data)
                        })
                    } else {
                        this.$resource('/services{/id}').update({id: this.serviceId}, service)
                        for (var i = 0; i < this.services.length; i++) {
                            if (this.services[i].serviceId == this.serviceId) {
                                service.serviceId = this.serviceId
                                this.services.splice(i, 1, service)
                                break
                            }
                        }
                    }
                }
                this.serviceTitle = ''
                this.servicePrice = ''
                this.serviceUnit = ''
                this.serviceForHouse = false

                this.dialog = false
                this.serviceId = ''
            },
            editService(service) {
                this.serviceId = service.serviceId
                this.dialog = true
                this.serviceTitle = service.title
                this.servicePrice = service.price
                this.serviceUnit = service.unit
                this.serviceForHouse = service.forHouse
            },
            receiptsClick() {
                this.items.splice(1, this.items.length - 1)
                this.$resource('/services{/id}').get({}).then(result => {
                    result.data.forEach(service => {
                        this.items.push(service.title)
                    })
                })
            },
            housesNext(address) {
                this.el = 2
                this.address = address
                this.apartmentNumber = ''
                this.selectApartmentType = ''
                this.serviceValue = ''
                this.apartmentList = '',
                this.$resource('/apartments{/id}').get({address}).then(result => this.apartments = result.data)
            },
            houseClick() {
                for (var i = 0; i < this.houseList.length; i++) {
                    if (this.houseList[i]) {
                        this.$resource('/apartments{/id}').get({address: this.houses[i].address}).then(result => {
                            result.data.forEach(data => {
                                const receipt = {
                                    service: this.selectHouseType,
                                    apartment: data,
                                    formationDate: new Date()
                                }
                                this.$resource('/receipts{/id}').save({}, receipt).then(res => this.receipts.unshift(res.data))
                            })
                        })
                    }
                }
            },
            apartmentClick() {
                const receipt = {
                    service: this.selectApartmentType,
                    value: this.serviceValue,
                    apartment: this.apartmentList,
                    formationDate: new Date()
                }
                this.$resource('/receipts{/id}').save({}, receipt).then(result => this.receipts.unshift(result.data))
            },
            showClick() {
                this.$resource('/receipts{/id}')
                    .get({serviceTitle: this.select, unpaidOnly: this.unpaidOnly})
                    .then(result => this.receipts = result.data)
            },
            deleteReceipt(item) {
                const isDelete = confirm('Are you sure you want to delete the receipt №' + item.receiptId + '?')
                if (isDelete) {
                    this.$resource('/receipts{/id}').delete({id: item.receiptId})
                    for (var i = 0; i < this.receipts.length; i++) {
                        if (this.receipts[i].receiptId == item.receiptId) {
                            this.receipts.splice(i, 1)
                            break
                        }
                    }
                }
            }
        }
    }
</script>

<style>

</style>
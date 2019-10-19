<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card class="mx-5">
        <v-card-title>
            <v-tabs color="teal" grow>
                <v-tab>Receipts</v-tab>
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
                        <v-col cols="10">
                            <v-select
                                    v-model="select"
                                    :items="services"
                                    color="teal"
                                    label="Type of service"
                                    append-icon="arrow_drop_down">
                                <template v-slot:item="{item, on}">
                                    <v-list-item v-on="on">
                                        <v-list-item-content>{{ item.title }}</v-list-item-content>
                                    </v-list-item>
                                </template>
                                <template v-slot:selection="{item}">
                                    <span class="ml-3">{{ item.title }}</span>
                                </template>
                            </v-select>
                        </v-col>
                    </v-row>
                    <v-divider/>
                    <v-dialog v-model="receiptDialog" width="500">
                        <template v-slot:activator="{ on }">
                            <v-btn @click="selectHouseType = '', el = 1"
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
                                                v-for="(house, i) in updateHouses()"
                                                :key="i">
                                            <v-list-item-action>
                                                <v-card outlined elevation="1">
                                                    <v-checkbox v-model="house.checked"
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
                                                    <v-list-item :style="(item.title == 'All' || !item.forHouse) ?
                                                        'display: none' : ''" v-on="on">
                                                        <v-list-item-content>{{ item.title }}</v-list-item-content>
                                                    </v-list-item>
                                                </template>
                                                <template v-slot:selection="{item}">
                                                    <span class="ml-3">{{ item.title }}</span>
                                                </template>
                                            </v-select>
                                        </v-col>
                                        <v-col cols="4">
                                            <v-btn :disabled="selectHouseType == '' || !houses.some(house => house.checked)"
                                                   class="white--text" color="teal" @click="houseClick">Add receipts</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-stepper-content>

                                <v-stepper-content step="2">
                                    <v-text-field
                                            v-model="apartmentNumber"
                                            label="Search"
                                            append-icon="search"
                                            color="teal">
                                    </v-text-field>
                                    <v-list class="mb-5" tile>
                                        <v-list-item-group color="teal">
                                            <v-list-item
                                                    v-for="(apartment, i) in updateApartments()"
                                                    @change="apartmentList = (apartmentList == apartment) ?
                                                        '' : apartment"
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
                                                <v-list-item :style="(item.title == 'All' || item.forHouse) ?
                                                        'display: none' : ''" v-on="on">
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
                                  :items="updateReceipts()"
                                  item-key="receiptId">
                        <template v-slot:item="{item}">
                            <v-footer class="ma-3 subtitle-1" elevation="2"
                                    :color="item.paymentDate ? 'green lighten-5' : 'red lighten-5'">
                                <v-row align="center" style="height: 60px; text-align: center;">
                                    <v-col style="text-align: left;" cols="2"><b>RECEIPT №{{ item.receiptId }}</b></v-col>
                                    <v-divider vertical/>
                                    <v-col cols="3">
                                        {{ new Date(item.formationDate).toLocaleDateString() }} /
                                        <span v-if="item.paymentDate">{{ new Date(item.paymentDate).toLocaleDateString() }}</span>
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
                                <v-btn @click="service.serviceId = ''" color="teal" text width="100%" v-on="on">
                                    <v-icon left>add</v-icon>Add new service
                                </v-btn>
                            </template>

                            <v-card>
                                <v-card-title>New service</v-card-title>
                                <v-card-text>
                                    <v-row class="mx-0">
                                        <v-text-field
                                                v-model="service.serviceTitle"
                                                color="teal"
                                                label="Service name"
                                                clearable clear-icon="clear"
                                                class="mt-2">
                                        </v-text-field>
                                    </v-row>
                                    <v-row>
                                        <v-col>
                                            <v-text-field
                                                    v-model="service.servicePrice"
                                                    color="teal"
                                                    label="Price"
                                                    append-icon="attach_money"
                                                    clearable clear-icon="clear"
                                                    class="mt-2">
                                            </v-text-field>
                                        </v-col>
                                        <v-col>
                                            <v-text-field
                                                    v-model="service.serviceUnit"
                                                    color="teal"
                                                    label="Unit"
                                                    clearable clear-icon="clear"
                                                    class="mt-2">
                                            </v-text-field>
                                        </v-col>
                                    </v-row>
                                    <v-row class="mx-0">
                                        <v-switch color="teal" v-model="service.serviceForHouse"
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
                houses: [],
                updateHouses() {
                    return this.houses
                        .filter(house => house.address.startsWith(this.houseAddress))
                },

                apartmentNumber: '',
                apartments: [],
                apartmentList: '',
                updateApartments() {
                    return this.apartments
                        .filter(apartment => apartment.apartmentNumber
                            .toString().startsWith(this.apartmentNumber))
                },

                serviceValueError: false,

                dialog: false,
                receiptDialog: false,

                service: {},

                serviceValue: '',
                selectHouseType: '',
                selectApartmentType: '',

                select: { title: 'All' },
                unpaidOnly: false,

                receipts: [],
                updateReceipts() {
                    return this.receipts.filter(item => {
                        return (!this.unpaidOnly || !item.payer) &&
                            (this.select.title == 'All' || this.select.title == item.service.title)
                    })
                },

                services: [ {title: 'All'} ],
                serviceValueRule: v => {
                    const pattern = /^[1-9][0-9]*((\.|,)[0-9]+)?$/
                    this.serviceValueError = pattern.test(v)
                    return this.serviceValueError || 'Invalid number'
                }
            }
        },
        created() {
            const item = { text: 'admin page', disabled: false, href: '/admin'}
            breadcrumbs.push(item)

            this.$resource('/services{/id}').get({}).then(result => {
                result.data.forEach(service => this.services.push(service))
            })

            this.$resource('/houses{/id}').get({}).then(result => {
                this.houses = result.data.map(house => ({address: house.address, checked: false}))
            })

            this.$resource('/receipts{/id}').get({}).then(result => this.receipts = result.data)
        },
        methods: {
            addService() {
                this.services = [ {title: 'All'} ]
                if (service.title && service.price) {
                    if (!this.service.serviceId) {
                        this.$resource('/services{/id}').save({}, this.service).then(result => {
                            this.services.push(result.data)
                        })
                    } else {
                        this.$resource('/services{/id}').update({id: this.serviceId}, this.service)
                        for (let i = 0; i < this.services.length; i++) {
                            if (this.services[i].serviceId == this.service.serviceId) {
                                this.services[i] = this.service
                                break
                            }
                        }
                    }
                }
                this.service = {}
                this.dialog = false
            },
            editService(service) {
                this.service = service
                this.dialog = true
            },
            housesNext(address) {
                this.el = 2
                this.address = address
                this.apartmentNumber = this.selectApartmentType = this.serviceValue = this.apartmentList = ''
                this.$resource('/apartments{/id}').get({address}).then(result => this.apartments = result.data)
            },
            houseClick() {
                for (let item of this.houses) {
                    if (item.checked) {
                        this.$resource('/apartments{/id}').get({address: item.address}).then(result => {
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
            deleteReceipt(item) {
                const isDelete = confirm(`Are you sure you want to delete the receipt №${item.receiptId}?`)
                if (isDelete) {
                    this.$resource('/receipts{/id}').delete({id: item.receiptId})
                    for (let i in this.receipts.length) {
                        if (this.receipts[i].receiptId === item.receiptId) {
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
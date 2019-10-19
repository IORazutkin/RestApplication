<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card class="mx-5">
        <v-card-title>List of your receipts</v-card-title>
        <v-card-text>
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
                            :items="items"
                            color="teal"
                            label="Type of service"
                            append-icon="arrow_drop_down">
                    </v-select>
                </v-col>
            </v-row>
            <v-divider/>
            <v-row v-if="receipts.length == 0" class="mt-10 mb-7" justify="center">
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
                                {{ item.service.title }}
                            </v-col>
                            <v-divider vertical/>
                            <v-col>
                                {{ item.value }} {{ item.service.unit }}
                            </v-col>
                            <v-divider vertical/>
                            <v-col cols="2">
                                <span v-if="item.value">${{ (item.value * item.service.price).toFixed(2) }}</span>
                                <span v-else>${{ item.service.price.toFixed(2) }}</span>
                            </v-col>
                            <v-divider vertical/>
                            <v-col>
                                <v-dialog v-model="dialog" width="500">
                                    <template v-slot:activator="{ on }">
                                        <v-btn :color="item.paymentDate ? 'teal' : 'red'"
                                               :disabled="item.paymentDate ? true : false"
                                               text icon v-on="on" @click="cardClick(item)">
                                            <v-icon>payment</v-icon>
                                        </v-btn>
                                    </template>

                                    <v-card>
                                        <v-card-title>Payment</v-card-title>
                                        <v-card-text>
                                            <v-text-field
                                                    color="teal"
                                                    prepend-inner-icon="payment"
                                                    label="Card number"
                                                    clearable clear-icon="clear">
                                            </v-text-field>
                                            <v-row>
                                                <v-col>
                                                    <v-text-field
                                                            color="teal"
                                                            prepend-inner-icon="event"
                                                            label="Validity"
                                                            clearable clear-icon="clear">
                                                    </v-text-field>
                                                </v-col>
                                                <v-col>
                                                    <v-text-field
                                                            color="teal"
                                                            prepend-inner-icon="lock_outline"
                                                            label="CVV/CVV2"
                                                            clearable clear-icon="clear">
                                                    </v-text-field>
                                                </v-col>
                                            </v-row>
                                            <v-row align="center">
                                                <v-col cols="8">
                                                    <v-footer class="headline">
                                                        <b>
                                                            <span v-if="item.value">
                                                                ${{ (receipt.value * receipt.service.price).toFixed(2) }}
                                                            </span>
                                                            <span v-else>${{ receipt.service.price.toFixed(2) }}</span>
                                                        </b>
                                                    </v-footer>
                                                </v-col>
                                                <v-col>
                                                    <v-btn width="100%" color="teal" class="white--text"
                                                           @click="payClick">
                                                        Pay
                                                    </v-btn>
                                                </v-col>
                                            </v-row>
                                        </v-card-text>
                                    </v-card>
                                </v-dialog>
                            </v-col>
                        </v-row>
                    </v-footer>
                </template>

            </v-data-table>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                dialog: false,

                receipt: {
                    service: { price: 0 }
                },

                receipts: [],
                items: [
                    'All'
                ],

                select: 'All',
                unpaidOnly: false,

                updateReceipts() {
                    return this.receipts.filter(item => {
                        return (!this.unpaidOnly || !item.payer) &&
                            (this.select == 'All' || this.select == item.service.title)
                    })
                },

                profile: frontendData.profile,
            }
        },
        created() {
            this.$resource('/services{/id}').get({}).then(result => {
                result.data.forEach(service => {
                    this.items.push(service.title)
                })
            })

            this.$resource('/receipts{/id}').get({apartmentId: this.profile.apartment.apartmentId})
                .then(result => this.receipts = result.data)
        },
        methods: {
            payClick() {
                this.$resource('/users{/id}').get({id : this.profile.userId}).then(result => {
                    result.json().then(data => {
                        this.receipt.payer = data
                        this.receipt.paymentDate = new Date()

                        this.$resource('/receipts{/id}').update({id: this.receipt.receiptId}, this.receipt).then(res => {
                            for (let i = 0; i < this.receipts.length; i++) {
                                if (this.receipts[i].receiptId === res.data.receiptId) {
                                    this.receipts[i] = res.data
                                    break
                                }
                            }
                        })
                    })
                })
            },
            cardClick(item) {
                this.receipt = item
            }
        }
    }
</script>

<style>

</style>
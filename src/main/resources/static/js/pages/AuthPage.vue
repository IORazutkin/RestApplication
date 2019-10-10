<template>
    <v-card class="ma-auto" width="40%">
        <v-card-title>
            <v-tabs color="teal" grow>
                <v-tab @click="click">Sign In</v-tab>
                <v-tab @click="click">Sign Up</v-tab>
                <v-tab-item>
                    <form action="/login" method="post">
                        <v-layout row class="mx-0">
                            <v-text-field
                                    v-model="logUser"
                                    :rules="[rules.email]"
                                    name="username"
                                    color="teal"
                                    prepend-inner-icon="mail_outline"
                                    label="E-mail"
                                    clearable clear-icon="clear"
                                    class="mt-2" type="email">
                            </v-text-field>
                        </v-layout>
                        <v-layout row class="mx-0">
                            <v-text-field
                                    v-model="logPass"
                                    name="password"
                                    color="teal"
                                    prepend-inner-icon="lock_outline"
                                    label="Password"
                                    clearable clear-icon="clear"
                                    type="password">
                            </v-text-field>
                        </v-layout>
                        <v-alert
                                dark
                                border="left"
                                :color="isError ? 'red lighten-1' : 'green'"
                                elevation="2"
                                :value="alertValue"
                                dense>
                            <v-layout row>
                                <v-icon class="mx-3">{{ isError ? 'error_outline' : 'done' }}</v-icon>
                                {{ alertText }}
                            </v-layout>
                        </v-alert>
                        <v-btn type="submit" color="teal mb-2" class="white--text" width="100%" @click="signIn">Sign In</v-btn>
                    </form>
                </v-tab-item>
                <v-tab-item>
                    <v-layout row class="mx-0">
                        <v-text-field
                                :rules="[rules.required]"
                                v-model="fullName"
                                color="teal"
                                prepend-inner-icon="perm_identity"
                                label="Full name"
                                clearable clear-icon="clear" class="mt-2">
                        </v-text-field>
                    </v-layout>
                    <v-layout row>
                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required]"
                                    v-model="city"
                                    color="teal"
                                    prepend-inner-icon="location_city"
                                    label="City"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>

                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required]"
                                    v-model="street"
                                    color="teal"
                                    prepend-inner-icon="local_florist"
                                    label="Street"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>
                    </v-layout>
                    <v-layout row>
                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required]"
                                    v-model="houseNumber"
                                    color="teal"
                                    prepend-inner-icon="room"
                                    label="House number"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>

                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required, rules.number]"
                                    v-model="apartmentNumber"
                                    color="teal"
                                    prepend-inner-icon="apartment"
                                    label="Apartment number"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>
                    </v-layout>
                    <v-layout row class="mx-0">
                        <v-text-field
                                :error="usernameError"
                                :rules="[rules.required, rules.email]"
                                v-model="username"
                                color="teal"
                                prepend-inner-icon="mail_outline"
                                label="E-mail"
                                clearable clear-icon="clear"
                                type="email">
                        </v-text-field>
                    </v-layout>
                    <v-layout row>
                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required, rules.min]"
                                    v-model="password"
                                    color="teal"
                                    prepend-inner-icon="lock_outline"
                                    label="Password"
                                    clearable clear-icon="clear"
                                    type="password">
                            </v-text-field>
                        </v-layout>
                        <v-layout col>
                            <v-text-field
                                    :error="confirmError"
                                    :rules="[rules.required, rules.confirm]"
                                    v-model="confirmPassword"
                                    color="teal"
                                    prepend-inner-icon="lock_outline"
                                    label="Confirm password"
                                    clearable clear-icon="clear"
                                    type="password">
                            </v-text-field>
                        </v-layout>
                    </v-layout>
                    <v-alert
                            dark
                            border="left"
                            :color="isError ? 'red lighten-1' : 'green'"
                            elevation="2"
                            :value="alertValue"
                            dense>
                        <v-layout row>
                            <v-icon class="mx-3">{{ isError ? 'error_outline' : 'done' }}</v-icon>
                            {{ alertText }}
                        </v-layout>
                    </v-alert>
                    <v-btn color="teal mb-2" class="white--text" width="100%" @click="signUp">Sign Up</v-btn>
                </v-tab-item>
            </v-tabs>
        </v-card-title>
    </v-card>
</template>

<script>
    import Vue from 'vue'
    import VueResource from 'vue-resource'

    Vue.use(VueResource)

    const userApi = Vue.resource('/users{/id}')
    export default {
        data() {
            return {
                fullName: '',
                city: '',
                street: '',
                houseNumber: '',
                apartmentNumber: '',
                username: '',
                password: '',
                confirmPassword: '',

                alertValue: false,
                alertText: '',
                isError: true,

                confirmError: false,
                usernameError: false,

                logUser: '',
                logPass: '',

                rules: {
                    required: value => !!value || 'Required',
                    min: v => v.length >= 5 || 'Min 5 characters',
                    email: value => {
                        this.usernameError = false
                        const pattern = /^((([a-zA-Z._-]+[0-9]*)+)@([a-z]{2,10})\.([a-z]{2,8}))$/
                        return pattern.test(value) || 'Invalid e-mail'
                    },
                    number: value => {
                        const pattern = /^[1-9]+[0-9]*$/
                        return pattern.test(value) || 'Invalid number'
                    },
                    confirm: value => {
                        this.confirmError = false
                        return value == this.password || 'Passwords do not match'
                    }
                }
            }
        },
        created() {
            const uri = window.location.href.split('?')

            if (uri.length > 1 && uri[1] == 'error') {
                this.alertValue = true
                this.isError = true
                this.alertText = 'Invalid E-mail or password'
            }
        },
        methods: {
            signUp() {
                const user = {
                                  fullName: this.fullName,
                                  username: this.username,
                                  password: this.password,
                                  address: this.city + ', ' + this.street + ', ' + this.houseNumber,
                                  apartmentNumber: this.apartmentNumber
                }

                if (this.fullName == ''
                            || this.city == ''
                            || this.apartmentNumber == ''
                            || this.houseNumber == ''
                            || this.street == ''
                            || this.username == ''
                            || this.password == ''
                            || this.confirmPassword == '') {
                    this.isError = true
                    this.alertValue = true
                    this.alertText = 'All fields required'
                }
                else if (!this.rules.email(this.email)
                            || !this.rules.number(this.apartmentNumber)
                            || !this.rules.min(this.password)) {
                    this.isError = true
                    this.alertValue = true
                    this.alertText = 'Invalid fields'
                }
                else if (this.password != this.confirmPassword) {
                    this.isError = true
                    this.alertValue = true
                    this.alertText = 'Passwords do not match'
                    this.confirmError = true;
                }
                else {
                    userApi.save({}, user).then(result => {
                        if (result.data == '') {
                            this.isError = true
                            this.alertValue = true
                            this.usernameError = true
                            this.alertText = 'A user with the same e-mail already exists'
                        } else {
                            this.isError = false
                            this.alertValue = true
                            this.alertText = 'User is registered'
                        }
                    })
                }
            },
            click() {
                this.fullName = ''
                this.city = ''
                this.apartmentNumber = ''
                this.houseNumber = ''
                this.street = ''
                this.username = ''
                this.password = ''
                this.confirmPassword = ''

                this.alertValue = false

                this.logUser = ''
                this.logPass = ''
            },
            signIn() {

            }
        }
    }
</script>

<style>
</style>
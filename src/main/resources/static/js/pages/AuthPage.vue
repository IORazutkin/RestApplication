<template>
    <v-card class="ma-auto" width="40%">
        <v-card-title>
            <v-tabs color="teal" grow>
                <v-tab @click="click">Sign In</v-tab>
                <v-tab @click="click">Sign Up</v-tab>
                <!--Форма авторизации-->
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
                                :color="alert.isError ? 'red lighten-1' : 'green'"
                                elevation="2"
                                :value="alert.value"
                                dense>
                            <v-layout row>
                                <v-icon class="mx-3">{{ alert.isError ? 'error_outline' : 'done' }}</v-icon>
                                {{ alert.text }}
                            </v-layout>
                        </v-alert>
                        <v-btn type="submit" color="teal mb-2" class="white--text" width="100%">Sign In</v-btn>
                    </form>
                </v-tab-item>
                <!--Форма регистрации-->
                <v-tab-item>
                    <v-layout row class="mx-0">
                        <v-text-field
                                :rules="[rules.required]"
                                v-model="regForm.fullName"
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
                                    v-model="regForm.city"
                                    color="teal"
                                    prepend-inner-icon="location_city"
                                    label="City"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>

                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required]"
                                    v-model="regForm.street"
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
                                    v-model="regForm.house"
                                    color="teal"
                                    prepend-inner-icon="room"
                                    label="House number"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>

                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required, rules.number]"
                                    v-model="regForm.apartmentNumber"
                                    color="teal"
                                    prepend-inner-icon="apartment"
                                    label="Apartment number"
                                    clearable clear-icon="clear">
                            </v-text-field>
                        </v-layout>
                    </v-layout>
                    <v-layout row class="mx-0">
                        <v-text-field
                                :rules="[rules.required, rules.email]"
                                v-model="regForm.username"
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
                                    v-model="regForm.password"
                                    color="teal"
                                    prepend-inner-icon="lock_outline"
                                    label="Password"
                                    clearable clear-icon="clear"
                                    type="password">
                            </v-text-field>
                        </v-layout>
                        <v-layout col>
                            <v-text-field
                                    :rules="[rules.required, rules.confirm]"
                                    v-model="regForm.confirmPassword"
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
                            :color="alert.isError ? 'red lighten-1' : 'green'"
                            elevation="2"
                            :value="alert.value"
                            dense>
                        <v-layout row>
                            <v-icon class="mx-3">{{ alert.isError ? 'error_outline' : 'done' }}</v-icon>
                            {{ alert.text }}
                        </v-layout>
                    </v-alert>
                    <v-btn color="teal mb-2" class="white--text" width="100%" @click="signUp">Sign Up</v-btn>
                </v-tab-item>
            </v-tabs>
        </v-card-title>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                regForm: { fullName: '', city: '', street: '',
                    house: '', apartmentNumber: '',
                    username: '', password: '', confirmPassword: ''},

                alert: {isError: false, text: '', value: false},

                showAlert(isError, text) {
                    this.alert.isError = isError
                    this.alert.text = text
                    this.alert.value = true
                },

                logUser: '',
                logPass: '',

                rules: {
                    required: value => !!value || 'Required',
                    min: v => !v || v.length >= 5 || 'Min 5 characters',
                    email: value => {
                        const pattern = /^((([a-zA-Z._\-]+[0-9]*)+)@([a-z]{2,10})\.([a-z]{2,8}))$/
                        return pattern.test(value) || 'Invalid e-mail'
                    },
                    number: value => {
                        const pattern = /^[1-9]+[0-9]*$/
                        return pattern.test(value) || 'Invalid number'
                    },
                    confirm: v => v == this.regForm.password || 'Passwords do not match'
                },
                checkValidity() {
                    return this.rules.email(this.regForm.username) !== true ||
                           this.rules.number(this.regForm.apartmentNumber) !== true ||
                           this.rules.min(this.regForm.password) !== true
                }
            }
        },
        created() {
            const uri = window.location.href.split('?')

            if (uri.length > 1 && uri[1] == 'error') {
                this.showAlert(true, 'Invalid E-mail or password')
            }
        },
        methods: {
            signUp() {
                if (Object.values(this.regForm).some(field => !field)) {
                    this.showAlert(true, 'All fields required')
                }
                else if (this.checkValidity()) {
                    this.showAlert(true, 'Invalid fields')
                }
                else if (this.regForm.password !== this.regForm.confirmPassword) {
                    this.showAlert(true, 'Passwords do not match')
                }
                else {
                    let user = ({
                        fullName, username, password, apartmentNumber} = this.regForm)
                    user.address = [this.regForm.city, this.regForm.street, this.regForm.house].join(", ")

                    this.$resource('/users{/id}').save({}, user).then(result => {
                        if (!result.data) {
                            this.showAlert(true, 'A user with the same e-mail already exists')
                        } else {
                            this.showAlert(false, 'User is registered')
                        }
                    })
                }
            },
            click() {
                for (let key in this.regForm) {
                    this.regForm[key] = ''
                }
                this.logUser = this.logPass = ''

                this.alert.value = false
            }
        }
    }
</script>

<style>
</style>
<template>
    <v-card class="mx-5">
        <v-card-title>User information</v-card-title>
        <v-card-text>
            <v-layout row class="mx-0">
                <v-text-field
                        :rules="[rules.required]"
                        :readonly="!isEdit"
                        v-model="fullName"
                        color="teal"
                        prepend-inner-icon="perm_identity"
                        label="Full name"
                        :clearable="isEdit" clear-icon="clear" class="mt-2">
                </v-text-field>
            </v-layout>
            <v-layout row>
                <v-col cols="10">
                    <v-text-field
                            :rules="[rules.required]"
                            :readonly="!isEdit"
                            v-model="address"
                            color="teal"
                            prepend-inner-icon="location_city"
                            label="Address"
                            :clearable="isEdit" clear-icon="clear">
                    </v-text-field>
                </v-col>
                <v-col cols="2">
                    <v-text-field
                            :rules="[rules.required, rules.number]"
                            :readonly="!isEdit"
                            v-model="apartmentNumber"
                            color="teal"
                            prepend-inner-icon="apartment"
                            label="Apartment number"
                            :clearable="isEdit" clear-icon="clear">
                    </v-text-field>
                </v-col>
            </v-layout>
            <v-layout row class="mx-0">
                <v-text-field
                        :error="usernameError"
                        :rules="[rules.required, rules.email]"
                        :readonly="!isEdit"
                        v-model="username"
                        color="teal"
                        prepend-inner-icon="mail_outline"
                        label="E-mail"
                        :clearable="isEdit" clear-icon="clear"
                        type="email">
                </v-text-field>
            </v-layout>
            <v-layout row class="mx-0">
                <v-text-field
                        :rules="[rules.required, rules.min]"
                        :readonly="!isEdit"
                        v-model="password"
                        color="teal"
                        prepend-inner-icon="lock_outline"
                        label="Password"
                        :clearable="isEdit" clear-icon="clear"
                        :type="isEdit ? 'password' : 'text'">
                </v-text-field>
            </v-layout>
            <v-alert
                    class="mt-1"
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
            <v-btn :disabled="!isEdit" class="mb-0 mt-2 white--text" color="teal" width="15%" @click="saveClick">Save</v-btn>
            <v-btn :disabled="isEdit" class="mb-0 mt-2 ml-2 white--text" color="teal" width="15%" @click="editClick">Edit</v-btn>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                profile: frontendData.profile,
                fullName: '',
                address: '',
                apartmentNumber: '',
                username: '',
                password: '',

                alertValue: false,
                alertText: '',
                isError: true,

                usernameError: false,
                isEdit: false,

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
                    }
                }
            }
        },
        created() {
            const item = { text: 'profile', disabled: false, href: '/profile'}
            breadcrumbs.push(item)
            this.$resource('/users{/id}').get({id: this.profile.userId}).then(result => {
                this.fullName = result.data.fullName
                this.address = result.data.apartment.house.address
                this.apartmentNumber = result.data.apartment.apartmentNumber
                this.username = result.data.username
                this.password = result.data.password
            })

        },
        methods: {
            editClick() {
                this.isEdit = true
            },
            saveClick() {
                const user = {
                                  fullName: this.fullName,
                                  username: this.username,
                                  password: this.password,
                                  address: this.address,
                                  apartmentNumber: this.apartmentNumber
                }
                if (this.fullName == ''
                            || this.address == ''
                            || this.apartmentNumber == ''
                            || this.username == ''
                            || this.password == '') {
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
                else {
                    this.$resource('/users{/id}').update({id: this.profile.userId}, user).then(result => {
                        if (result.data == '') {
                            this.isError = true
                            this.alertValue = true
                            this.usernameError = true
                            this.alertText = 'A user with the same e-mail already exists'
                        } else {
                            this.isError = false
                            this.alertValue = true
                            this.alertText = 'User data updated'
                            this.isEdit = false

                            this.profile.fullName = this.fullName
                            this.profile.apartment.apartmentNumber = this.apartmentNumber
                            this.profile.apartment.house.address = this.address
                        }
                    })
                }
            }
        }
    }
</script>

<style>

</style>
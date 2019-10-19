<template>
    <v-card class="mx-5">
        <v-card-title>User information</v-card-title>
        <v-card-text>
            <v-layout row class="mx-0">
                <v-text-field
                        :rules="[rules.required]"
                        :readonly="!isEdit"
                        v-model="profileForm.fullName"
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
                            v-model="profileForm.address"
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
                            v-model="profileForm.apartmentNumber"
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
                        v-model="profileForm.username"
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
                        v-model="profileForm.password"
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
                    :color="alert.isError ? 'red lighten-1' : 'green'"
                    elevation="2"
                    :value="alert.value"
                    dense>
                <v-layout row>
                    <v-icon class="mx-3">{{ alert.isError ? 'error_outline' : 'done' }}</v-icon>
                    {{ alert.text }}
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
                profileForm: { fullName: '', address: '', apartmentNumber: '', username: '', password: '' },

                alert: { value: false, isError: false, text: '' },

                usernameError: false,
                isEdit: false,

                rules: {
                    required: value => !!value || 'Required',
                    min: v => !v || v.length >= 5 || 'Min 5 characters',
                    email: value => {
                        this.usernameError = false
                        const pattern = /^((([a-zA-Z._-]+[0-9]*)+)@([a-z]{2,10})\.([a-z]{2,8}))$/
                        return pattern.test(value) || 'Invalid e-mail'
                    },
                    number: value => {
                        const pattern = /^[1-9]+[0-9]*$/
                        return pattern.test(value) || 'Invalid number'
                    }
                },
                checkValidity() {
                    return this.rules.email(this.profileForm.username) !== true ||
                        this.rules.number(this.profileForm.apartmentNumber) !== true ||
                        this.rules.min(this.profileForm.password) !== true
                },
                showAlert(isError, text) {
                    this.alert.isError = isError
                    this.alert.text = text
                    this.alert.value = true
                },
            }
        },
        created() {
            const item = { text: 'profile', disabled: false, href: '/profile'}
            breadcrumbs.push(item)

            this.$resource('/users{/id}').get({id: this.profile.userId})
                .then(result => {
                    let data = result.data
                    this.profileForm.fullName = data.fullName
                    this.profileForm.address = data.apartment.house.address
                    this.profileForm.apartmentNumber = data.apartment.apartmentNumber
                    this.profileForm.username = data.username
                    this.profileForm.password = data.password
                })
        },
        methods: {
            editClick() {
                this.isEdit = true
            },
            saveClick() {
                if (Object.values(this.profileForm).some(field => !field)) {
                    this.showAlert(true, 'All fields required')
                }
                else if (this.checkValidity()) {
                    this.showAlert(true, 'Invalid fields')
                }
                else {
                    this.$resource('/users{/id}').update({id: this.profile.userId}, this.profileForm).then(result => {
                        if (!result.data) {
                            this.showAlert(true, 'A user with the same e-mail already exists')
                            this.usernameError = true
                        } else {
                            this.showAlert(false, 'User data updated')
                            this.isEdit = false;

                            ({ fullName: this.profile.fullName,
                               address: this.profile.apartment.house.address,
                               apartmentNumber: this.profile.apartment.apartmentNumber } = this.profileForm)
                        }
                    })
                }
            }
        }
    }
</script>

<style>
</style>
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app>
        <v-app-bar color="teal"
                app>
            <v-toolbar-title class="white--text">CServices</v-toolbar-title>
            <v-divider vertical class="teal lighten-2 ml-4"></v-divider>
            <v-breadcrumbs v-if="profile" :items="breadcrumbs">
                <template v-slot:item="props">
                    <v-breadcrumbs-item>
                        <v-breadcrumbs-item :key="props.item.text" class="white--text">
                            <v-btn x-large :href="props.item.href" :disabled="props.item.disabled" color="white" text>
                                {{props.item.text.toUpperCase()}}</v-btn>
                        </v-breadcrumbs-item>
                    </v-breadcrumbs-item>
                </template>
                <template v-slot:divider>
                    <v-icon color="white">arrow_forward_ios</v-icon>
                </template>
            </v-breadcrumbs>
            <div class="flex-grow-1"></div>
            <v-btn href="/admin" class="mr-4 black--text" color="white"
                   v-if="profile && profile.roles.length > 1"
                   elevation="0" rounded>admin page</v-btn>
            <v-btn v-if="profile" class="mr-4 white--text" outlined rounded href="/profile">{{profile.fullName}}</v-btn>
            <form v-if="profile" action="/logout" method="post">
                <v-btn type="submit" icon>
                    <v-icon color="white">exit_to_app</v-icon>
                </v-btn>
            </form>
        </v-app-bar>
        <v-content class="grey lighten-3">
            <v-container>
            </v-container>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    export default {
        data() {
            return {
                profile: frontendData.profile,
                breadcrumbs: breadcrumbs
            }
        }
    }
</script>

<style>

</style>
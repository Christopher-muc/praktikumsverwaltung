<template>
  <v-navigation-drawer
    v-model="showDrawer"
    temporary
  >
    <v-list>
      <v-list-item
        v-if="isAdmin"
        to="/students"
      >
        <v-list-item-title> Studenten </v-list-item-title>
      </v-list-item>

      <v-list-item
        v-if="isAdmin"
        to="/studiengaenge"
      >
        <v-list-item-title> Studiengänge </v-list-item-title>
      </v-list-item>

      <v-list-item
        v-if="!isAdmin"
        to="/me"
      >
        <v-list-item-title> Meine Daten </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup lang="ts">
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const showDrawer = defineModel<boolean>({ required: true });

const isAdmin = useHasAnyRole(Role.ADMIN);
</script>

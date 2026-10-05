<template>
  <v-app>
    <the-snackbar-queue />

    <the-app-bar
      :show-navigation="isAdmin"
      @clicked-nav-icon="toggleNavigation"
    />

    <the-navigation-drawer
      v-if="isAdmin"
      v-model="isNavigationShown"
    />

    <v-main>
      <router-view v-slot="{ Component }">
        <v-fade-transition mode="out-in">
          <component :is="Component" />
        </v-fade-transition>
      </router-view>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { useToggle } from "@vueuse/core";

import TheAppBar from "@/components/TheAppBar.vue";
import TheNavigationDrawer from "@/components/TheNavigationDrawer.vue";
import TheSnackbarQueue from "@/components/TheSnackbarQueue.vue";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { Role } from "@/types/Role";

const [isNavigationShown, toggleNavigation] = useToggle();

const isAdmin = useHasAnyRole(Role.ADMIN);
</script>

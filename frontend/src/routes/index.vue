<template>
  <v-container>
    <v-progress-linear indeterminate />
  </v-container>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { useRouter } from "vue-router";

import { hasAnyRole } from "@/composables/useHasAnyRole";
import { useUserInfoStore } from "@/stores/userinfo";
import { Role } from "@/types/Role";

const router = useRouter();
const userInfoStore = useUserInfoStore();

onMounted(async () => {
  if (!userInfoStore.userInfo) {
    await userInfoStore.fetchUserInfo();
  }

  if (hasAnyRole([Role.ADMIN], userInfoStore.currentRoles)) {
    await router.replace("/students");
    return;
  }

  if (hasAnyRole([Role.STUDENT,Role.FACHSTUDENT], userInfoStore.currentRoles)) {
    await router.replace("/me");
  }
});
</script>

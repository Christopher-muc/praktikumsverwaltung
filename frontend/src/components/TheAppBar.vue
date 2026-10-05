<template>
  <v-app-bar color="primary">
    <v-row class="align-center">
      <v-col
        cols="3"
        class="d-flex align-center justify-start"
      >
        <v-app-bar-nav-icon
          :style="{ visibility: showNavigation ? 'visible' : 'hidden' }"
          class="mx-2"
          @click="emit('clickedNavIcon')"
        />

        <router-link
          to="/"
          class="text-decoration-none on-primary"
        >
          <v-toolbar-title class="font-weight-bold">
            {{ t("app.name.part1") }}
          </v-toolbar-title>
        </router-link>
      </v-col>

      <v-col
        cols="6"
        class="d-flex align-center justify-center"
      >
        <v-text-field
          v-if="showNavigation"
          id="searchField"
          v-model="query"
          flat
          variant="solo-inverted"
          hide-details
          :label="t('common.actions.search')"
          clearable
          :prepend-inner-icon="mdiMagnify"
          theme="dark"
          :rules="[rules.maxLength(20)]"
          @keyup.enter="search"
          @click:clear="clearSearch"
        />
      </v-col>

      <v-col
        cols="3"
        class="d-flex align-center justify-end"
      >
        <theme-toggle-btn class="mr-2" />
      </v-col>
    </v-row>
  </v-app-bar>
</template>

<script setup lang="ts">
import { mdiMagnify } from "@mdi/js";
import { ref } from "vue";
import { useI18n } from "vue-i18n";
import { useRouter } from "vue-router";
import { useRules } from "vuetify";

import ThemeToggleBtn from "@/components/common/ThemeToggleBtn.vue";

defineProps<{
  showNavigation: boolean;
}>();

const { t } = useI18n();
const router = useRouter();
const rules = useRules();

const query = ref("");

async function search() {
  const searchQuery = query.value.trim();

  await router.push({
    path: "/students",
    query: searchQuery
      ? {
          search: searchQuery,
        }
      : {},
  });
}

async function clearSearch() {
  query.value = "";

  await router.push({
    path: "/",
  });
}

const emit = defineEmits<{
  clickedNavIcon: [];
}>();
</script>

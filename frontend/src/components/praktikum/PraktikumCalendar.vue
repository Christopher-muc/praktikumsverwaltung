<template>
  <v-card elevation="5">
    <v-card-title>
      {{ t("domain.praktikum.calendar") }}
    </v-card-title>

    <v-date-picker
      v-if="praktikum"
      v-model="selectedDate"
      :min="praktikum.beginnDatum"
      :max="praktikum.endeDatum"
      :events="calendarEvents"
      width="100%"
    />

    <v-card-text
      v-else
      class="text-medium-emphasis"
    >
      Kein Praktikum hinterlegt.
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import type { FullPraktikumDTO } from "@/api/generated/api-spec/models";

import { useI18n } from "vue-i18n";

import { getCalendarEvents } from "@/util/PraktikumCalendarUtil";

const { t } = useI18n();

const selectedDate = defineModel<Date>();

const props = defineProps<{
  praktikum?: FullPraktikumDTO;
}>();

function calendarEvents(date: string): string[] | false {
  return getCalendarEvents(props.praktikum, date);
}
</script>

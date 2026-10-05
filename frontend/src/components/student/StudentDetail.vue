<template>
  <div class="ma-12">
    <!-- Stammdaten -->
    <student-overview
      :student="student"
      :praktikum="praktikum"
    />

    <!-- Praktikum -->
    <v-row
      v-if="student.studentId !== undefined"
      class="mt-4"
    >
      <!-- Kalender -->
      <v-col cols="4">
        <praktikum-calendar
          v-model="selectedDate"
          :praktikum="praktikum"
        />
      </v-col>

      <!-- Zeitgutschriften -->
      <v-col cols="4">
        <time-credit-list
          :student-id="student.studentId"
          :praktikum="praktikum"
          :selected-date="selectedDate"
          :can-write="canWriteZeitgutschrift"
          @changed="emit('changed')"
        />
      </v-col>

      <!-- Tätigkeitsblöcke -->
      <v-col cols="4">
        <activity-list
          :student-id="student.studentId"
          :praktikum="praktikum"
          :selected-date="selectedDate"
          :can-write="canWriteActivity"
          @changed="emit('changed')"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  StudentDTO,
} from "@/api/generated/api-spec/models";

import { ref, watch } from "vue";

import ActivityList from "@/components/praktikum/ActivityList.vue";
import PraktikumCalendar from "@/components/praktikum/PraktikumCalendar.vue";
import TimeCreditList from "@/components/praktikum/TimeCreditList.vue";
import StudentOverview from "@/components/student/StudentOverview.vue";

const props = defineProps<{
  student: StudentDTO;
  praktikum?: FullPraktikumDTO;
  canWriteActivity: boolean;
  canWriteZeitgutschrift: boolean;
}>();

const emit = defineEmits<{
  changed: [];
}>();

const selectedDate = ref<Date>();

watch(
  () => props.praktikum,
  (praktikum) => {
    if (!selectedDate.value && praktikum?.beginnDatum) {
      selectedDate.value = praktikum.beginnDatum;
    }

    if (!praktikum) {
      selectedDate.value = undefined;
    }
  },
  {
    immediate: true,
  }
);
</script>

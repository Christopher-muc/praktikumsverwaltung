<template>
  <student-detail
    v-if="student"
    :student="student"
    :praktikum="praktikum"
    :can-write-activity="true"
    :can-write-zeitgutschrift="true"
    @changed="loadData"
  />
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  StudentDTO,
} from "@/api/generated/api-spec/models";

import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

import { ApiFactory } from "@/api/ApiFactory.ts";
import {
  PraktikumControllerApi,
  StudentControllerApi,
} from "@/api/generated/api-spec";
import StudentDetail from "@/components/student/StudentDetail.vue";
import { Role } from "@/types/Role.ts";
import { isNotFoundError } from "@/util/ApiErrorUtil.ts";

/*
 * Nur ADMIN darf fremde Studenten aufrufen.
 */
definePage({
  meta: {
    hasAnyRole: [Role.ADMIN],
  },
});

const route = useRoute("/students/[id]");

const studentId = Number(route.params.id);

/*
 * APIs
 */
const studentApi = ApiFactory.getInstance(StudentControllerApi);

const praktikumApi = ApiFactory.getInstance(PraktikumControllerApi);

/*
 * State
 */
const student = ref<StudentDTO>();

const praktikum = ref<FullPraktikumDTO>();

/*
 * Student laden
 */
async function loadStudent() {
  student.value = await studentApi.getStudent(studentId);
}

/*
 * Praktikum laden
 */
async function loadPraktikum() {
  try {
    praktikum.value = await praktikumApi.getPraktikum(studentId);
  } catch (error) {
    if (isNotFoundError(error)) {
      praktikum.value = undefined;
      return;
    }

    throw error;
  }
}

/*
 * Student und Praktikum neu laden
 */
async function loadData() {
  await Promise.all([loadStudent(), loadPraktikum()]);
}

/*
 * Initialisierung
 */
onMounted(async () => {
  await loadData();
});
</script>

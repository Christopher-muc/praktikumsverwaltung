<template>
  <v-dialog
    v-model="dialog"
    max-width="600"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2"> Student bearbeiten </v-card-title>

      <v-card-text class="pa-6">
        <v-btn-toggle
          v-model="editMode"
          mandatory
          divided
          class="mb-6"
        >
          <v-btn value="student"> Student </v-btn>

          <v-btn value="praktikum"> Praktikum </v-btn>

          <v-btn value="studiengang"> Studiengang </v-btn>
        </v-btn-toggle>

        <!-- STUDENT -->
        <student-name-form
          v-if="editMode === 'student'"
          v-model:first-name="firstName"
          v-model:last-name="lastName"
        />

        <!-- PRAKTIKUM -->
        <student-praktikum-form
          v-if="editMode === 'praktikum'"
          v-model:beginn-datum="beginnDatum"
          v-model:end-datum="endeDatum"
          v-model:wochenarbeitszeit="praktikumWochenarbeitszeit"
          v-model:benoetigte-wochen="benoetigteWochen"
          :loading="praktikumLoading"
          :praktikum-exists="praktikum !== null"
        />

        <!-- STUDIENGÄNGE -->
        <student-studiengaenge-form
          v-if="editMode === 'studiengang'"
          v-model="studiengaenge"
          :loading="studiengaengeLoading"
        />

        <!-- AKTIONEN -->
        <div class="d-flex justify-end ga-2 mt-6">
          <v-btn
            variant="text"
            @click="close"
          >
            Abbrechen
          </v-btn>

          <v-btn
            color="primary"
            :disabled="praktikumLoading || studiengaengeLoading"
            @click="save"
          >
            Speichern
          </v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  PraktikumDTO,
  PraktikumUpdateDTO,
  StudentDTO,
  Studiengang,
} from "@/api/generated/api-spec/models";

import { ref, watch } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import {
  PraktikumControllerApi,
  StudentControllerApi,
  StudiumControllerApi,
} from "@/api/generated/api-spec";
import { ResponseError } from "@/api/generated/api-spec/runtime";
import StudentNameForm from "@/components/student/StudentNameForm.vue";
import StudentPraktikumForm from "@/components/student/StudentPraktikumForm.vue";
import StudentStudiengaengeForm from "@/components/student/StudentStudiengaengeForm.vue";
import { toDateInputValue } from "@/util/formatter";
import {
  getAddedStudiengangIds,
  getRemovedStudiengangIds,
} from "@/util/StudiengangUtil.ts";

const dialog = defineModel<boolean>({
  default: false,
});

const props = defineProps<{
  student: StudentDTO | null;
}>();

const emit = defineEmits<{
  updated: [];
}>();

const studentApi = ApiFactory.getInstance(StudentControllerApi);
const praktikumApi = ApiFactory.getInstance(PraktikumControllerApi);
const studiumApi = ApiFactory.getInstance(StudiumControllerApi);

const editMode = ref<"student" | "praktikum" | "studiengang">("student");

/*
 * Student
 */
const firstName = ref("");
const lastName = ref("");

/*
 * Praktikum
 */
const praktikum = ref<FullPraktikumDTO | null>(null);

const beginnDatum = ref("");
const endeDatum = ref("");
const praktikumWochenarbeitszeit = ref(0);
const benoetigteWochen = ref(0);

/*
 * Studiengänge
 */
const studiengaenge = ref<Studiengang[]>([]);
const originalStudiengaenge = ref<Studiengang[]>([]);

const praktikumLoading = ref(false);
const studiengaengeLoading = ref(false);

watch(
  () => props.student,
  async (student) => {
    if (!student) {
      return;
    }

    firstName.value = student.vorname ?? "";
    lastName.value = student.nachname ?? "";

    editMode.value = "student";

    await Promise.all([loadPraktikum(), loadStudiengaenge()]);
  },
  {
    immediate: true,
  }
);

async function loadPraktikum() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  praktikumLoading.value = true;

  praktikum.value = null;
  resetPraktikumFields();

  try {
    const loadedPraktikum = await praktikumApi.getPraktikum(studentId);

    praktikum.value = loadedPraktikum;

    beginnDatum.value = toDateInputValue(loadedPraktikum.beginnDatum);

    endeDatum.value = toDateInputValue(loadedPraktikum.endeDatum);

    praktikumWochenarbeitszeit.value = loadedPraktikum.wochenarbeitszeit ?? 0;

    benoetigteWochen.value = loadedPraktikum.benoetigteWochen ?? 0;
  } catch (error) {
    if (error instanceof ResponseError && error.response.status === 404) {
      praktikum.value = null;
      return;
    }

    throw error;
  } finally {
    praktikumLoading.value = false;
  }
}

async function loadStudiengaenge() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  studiengaengeLoading.value = true;

  try {
    const fullStudent = await studentApi.getStudent(studentId);

    studiengaenge.value = [...(fullStudent.studiengaenge ?? [])];

    originalStudiengaenge.value = [...(fullStudent.studiengaenge ?? [])];
  } finally {
    studiengaengeLoading.value = false;
  }
}

async function save() {
  if (editMode.value === "student") {
    await updateStudent();
    return;
  }

  if (editMode.value === "praktikum") {
    await savePraktikum();
    return;
  }

  if (editMode.value === "studiengang") {
    await saveStudiengaenge();
  }
}

async function updateStudent() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  await studentApi.updateStudent(studentId, {
    vorname: firstName.value.trim(),
    nachname: lastName.value.trim(),
  });

  emit("updated");
  close();
}

async function savePraktikum() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  if (!beginnDatum.value || !endeDatum.value) {
    return;
  }

  if (praktikum.value) {
    const request: PraktikumUpdateDTO = {
      beginnDatum: new Date(`${beginnDatum.value}T00:00:00`),
      endeDatum: new Date(`${endeDatum.value}T00:00:00`),
      wochenarbeitszeit: praktikumWochenarbeitszeit.value,
      benoetigteWochen: benoetigteWochen.value,
    };

    await praktikumApi.updatePraktikum(studentId, request);
  } else {
    const request: PraktikumDTO = {
      studentId,
      beginnDatum: new Date(`${beginnDatum.value}T00:00:00`),
      endeDatum: new Date(`${endeDatum.value}T00:00:00`),
      wochenarbeitszeit: praktikumWochenarbeitszeit.value,
      benoetigteWochen: benoetigteWochen.value,
    };

    await praktikumApi.createPraktikum(request);
  }

  emit("updated");
  close();
}

async function saveStudiengaenge() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  const addedStudiengangIds = getAddedStudiengangIds(
    studiengaenge.value,
    originalStudiengaenge.value
  );

  const removedStudiengangIds = getRemovedStudiengangIds(
    studiengaenge.value,
    originalStudiengaenge.value
  );

  for (const studiengangId of addedStudiengangIds) {
    await studiumApi.addStudiumToStudent({
      studentId,
      studiengangId,
    });
  }

  for (const studiengangId of removedStudiengangIds) {
    await studiumApi.removeStudiumfromStudent({
      studentId,
      studiengangId,
    });
  }

  emit("updated");
  close();
}

function resetPraktikumFields() {
  beginnDatum.value = "";
  endeDatum.value = "";
  praktikumWochenarbeitszeit.value = 0;
  benoetigteWochen.value = 0;
}

function close() {
  dialog.value = false;
  editMode.value = "student";
}
</script>

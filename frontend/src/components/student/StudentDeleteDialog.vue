<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2"> Student löschen </v-card-title>

      <v-card-text class="pa-6">
        Möchtest du
        <strong>
          {{ student?.vorname }}
          {{ student?.nachname }}
        </strong>
        wirklich löschen?

        <div class="d-flex justify-end ga-2 mt-6">
          <v-btn
            variant="text"
            @click="close"
          >
            Abbrechen
          </v-btn>

          <v-btn
            color="error"
            @click="deleteStudent"
          >
            Löschen
          </v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type { SimpleStudentDTO } from "@/api/generated/api-spec/models";

import { ApiFactory } from "@/api/ApiFactory";
import { StudentControllerApi } from "@/api/generated/api-spec";

const dialog = defineModel<boolean>({
  default: false,
});

const props = defineProps<{
  student: SimpleStudentDTO | null;
}>();

const emit = defineEmits<{
  deleted: [];
}>();

const studentApi = ApiFactory.getInstance(StudentControllerApi);

async function deleteStudent() {
  const studentId = props.student?.studentId;

  if (studentId === undefined) {
    return;
  }

  await studentApi.deleteStudent(studentId);

  emit("deleted");
  close();
}

function close() {
  dialog.value = false;
}
</script>

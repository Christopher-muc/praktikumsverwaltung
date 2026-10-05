<template>
  <v-container>
    <!-- Überschrift -->
    <div class="d-flex justify-space-between align-center mb-4">
      <h1 class="text-h4">Studenten</h1>

      <v-btn
        variant="outlined"
        @click="createDialog = true"
      >
        Student hinzufügen
      </v-btn>
    </div>

    <!-- Studentenliste -->
    <v-list>
      <template
        v-for="(student, index) in filteredStudents"
        :key="student.studentId"
      >
        <v-list-item
          :title="`${student.vorname ?? ''} ${student.nachname ?? ''}`"
          :to="`students/${student.studentId}`"
        >
          <template #append>
            <v-menu>
              <template #activator="{ props: menuProps }">
                <v-btn
                  v-bind="menuProps"
                  variant="text"
                  size="small"
                  @click.prevent.stop
                >
                  ⋮
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  title="Bearbeiten"
                  @click="openEditDialog(student)"
                />

                <v-list-item
                  title="Löschen"
                  @click="openDeleteDialog(student)"
                />
              </v-list>
            </v-menu>
          </template>
        </v-list-item>

        <v-divider v-if="index < filteredStudents.length - 1" />
      </template>
    </v-list>

    <!-- Keine Studenten gefunden -->
    <div
      v-if="filteredStudents.length === 0"
      class="text-medium-emphasis pa-4"
    >
      Keine Studenten gefunden.
    </div>

    <!-- Dialoge -->
    <student-create-dialog
      v-model="createDialog"
      @created="loadStudents"
    />

    <student-edit-dialog
      v-model="editDialog"
      :student="selectedStudent"
      @updated="loadStudents"
    />

    <student-delete-dialog
      v-model="deleteDialog"
      :student="selectedStudent"
      @deleted="loadStudents"
    />
  </v-container>
</template>

<script setup lang="ts">
import type { SimpleStudentDTO } from "@/api/generated/api-spec/models";

import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";

import { ApiFactory } from "@/api/ApiFactory";
import {
  StudentControllerApi,
  StudiumControllerApi,
} from "@/api/generated/api-spec";
import StudentCreateDialog from "@/components/student/StudentCreateDialog.vue";
import StudentDeleteDialog from "@/components/student/StudentDeleteDialog.vue";
import StudentEditDialog from "@/components/student/StudentEditDialog.vue";
import { Role } from "@/types/Role";
import { filterStudents, getStudiengangId } from "@/util/StudentUtil.ts";

definePage({
  meta: {
    hasAnyRole: [Role.ADMIN],
  },
});

const route = useRoute();

const studentApi = ApiFactory.getInstance(StudentControllerApi);
const studiumApi = ApiFactory.getInstance(StudiumControllerApi);

const students = ref<SimpleStudentDTO[]>([]);

async function loadStudents() {
  const studiengangId = getStudiengangId(route.query.studiengang);

  if (studiengangId !== undefined) {
    students.value = await studiumApi.getStudentsByStudiengang(studiengangId);

    return;
  }

  students.value = await studentApi.getAllStudents();
}

onMounted(loadStudents);

const filteredStudents = computed(() =>
  filterStudents(students.value, route.query.search)
);

const createDialog = ref(false);
const editDialog = ref(false);
const deleteDialog = ref(false);

const selectedStudent = ref<SimpleStudentDTO | null>(null);

function openEditDialog(student: SimpleStudentDTO) {
  selectedStudent.value = student;
  editDialog.value = true;
}

function openDeleteDialog(student: SimpleStudentDTO) {
  selectedStudent.value = student;
  deleteDialog.value = true;
}
</script>

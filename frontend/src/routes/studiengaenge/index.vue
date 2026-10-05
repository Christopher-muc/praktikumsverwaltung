<template>
  <v-container>
    <!-- Überschrift -->
    <div class="d-flex justify-space-between align-center mb-6">
      <div>
        <h1 class="text-h4">Studiengänge</h1>

        <div class="text-medium-emphasis">Übersicht aller Studiengänge</div>
      </div>

      <v-btn
        color="primary"
        variant="outlined"
        @click="createDialog = true"
      >
        Studiengang hinzufügen
      </v-btn>
    </div>

    <!-- Laden -->
    <v-progress-linear
      v-if="loading"
      indeterminate
      class="mb-4"
    />

    <!-- Keine Studiengänge -->
    <div
      v-else-if="studiengaenge.length === 0"
      class="text-medium-emphasis"
    >
      Es sind keine Studiengänge hinterlegt.
    </div>

    <!-- Studiengänge -->
    <v-row v-else>
      <v-col
        v-for="studiengang in studiengaenge"
        :key="studiengang.studiengangNr"
        cols="12"
        md="6"
        lg="4"
      >
        <v-card
          variant="outlined"
          hover
          class="h-100"
        >
          <!-- Kopfzeile -->
          <div class="d-flex align-center pa-4">
            <div class="text-h6">
              {{ studiengang.name }}
            </div>

            <v-spacer />

            <!-- Löschen -->
            <v-btn
              color="error"
              variant="outlined"
              size="small"
              :loading="deletingId === studiengang.studiengangNr"
              @click="deleteStudiengang(studiengang)"
            >
              ×
            </v-btn>
          </div>

          <v-divider />

          <!-- Studenten -->
          <v-card-actions class="pa-4">
            <v-spacer />

            <v-btn
              variant="text"
              append-icon="mdi-chevron-right"
              @click="openStudiengang(studiengang.studiengangNr)"
            >
              Studenten anzeigen
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <!-- Studiengang erstellen -->
    <create-studiengang
      v-model="createDialog"
      @created="loadStudiengaenge"
    />
  </v-container>
</template>

<script setup lang="ts">
import type { StudiengangDTO } from "@/api/generated/api-spec/models";

import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";

import { ApiFactory } from "@/api/ApiFactory";
import { StudiengangControllerApi } from "@/api/generated/api-spec/apis";
import CreateStudiengang from "@/components/studiengang/CreateStudiengang.vue";
import { Role } from "@/types/Role";
import {
  getStudiengangId,
  getStudiengangRoute,
} from "@/util/StudiengangUtil.ts";

definePage({
  meta: {
    hasAnyRole: Role.ADMIN,
  },
});

const router = useRouter();

const api = ApiFactory.getInstance(StudiengangControllerApi);

const studiengaenge = ref<StudiengangDTO[]>([]);

const loading = ref(false);
const createDialog = ref(false);
const deletingId = ref<number>();

async function loadStudiengaenge() {
  loading.value = true;

  studiengaenge.value = await api.getStudiengaenge();

  loading.value = false;
}

async function openStudiengang(studiengangNr?: number) {
  const route = getStudiengangRoute(studiengangNr);

  if (!route) {
    return;
  }

  await router.push(route);
}

async function deleteStudiengang(studiengang: StudiengangDTO) {
  const studiengangId = getStudiengangId(studiengang);

  if (studiengangId === undefined) {
    return;
  }

  deletingId.value = studiengangId;

  try {
    await api.deleteStudiengang(studiengangId);
    await loadStudiengaenge();
  } finally {
    deletingId.value = undefined;
  }
}

onMounted(loadStudiengaenge);
</script>

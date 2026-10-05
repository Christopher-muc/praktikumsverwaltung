<template>
  <v-card elevation="5">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Tätigkeitsblöcke</span>

      <v-btn
        v-if="canWrite"
        icon
        variant="text"
        size="small"
        :disabled="!selectedDate"
        @click="openCreateDialog"
      >
        +
      </v-btn>
    </v-card-title>

    <v-card-subtitle v-if="selectedDate">
      {{ toDateString(selectedDate) }}
    </v-card-subtitle>

    <v-list v-if="selectedTaetigkeiten.length">
      <template
        v-for="(taetigkeit, index) in selectedTaetigkeiten"
        :key="`${taetigkeit.taetigkeitenblockID?.studentId}-${taetigkeit.taetigkeitenblockID?.tag}-${taetigkeit.taetigkeitenblockID?.beginnZeit}-${taetigkeit.taetigkeitenblockID?.endeZeit}`"
      >
        <v-list-item>
          <div class="d-flex align-center">
            <div>
              {{
                toLocalTimeString(taetigkeit.taetigkeitenblockID?.beginnZeit)
              }}
              -
              {{ toLocalTimeString(taetigkeit.taetigkeitenblockID?.endeZeit) }}
              Uhr
            </div>

            <div class="flex-grow-1 text-center">
              {{ taetigkeit.homeoffice ? "Homeoffice" : "Präsenz" }}
            </div>
          </div>

          <template
            v-if="canWrite"
            #append
          >
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
                  @click="openEditDialog(taetigkeit)"
                />

                <v-list-item
                  title="Löschen"
                  @click="deleteTaetigkeit(taetigkeit)"
                />
              </v-list>
            </v-menu>
          </template>
        </v-list-item>

        <v-divider v-if="index < selectedTaetigkeiten.length - 1" />
      </template>
    </v-list>

    <v-card-text
      v-else-if="selectedDate"
      class="text-medium-emphasis"
    >
      Keine Tätigkeitsblöcke hinterlegt.
    </v-card-text>

    <v-card-text
      v-else
      class="text-medium-emphasis"
    >
      Kein Datum ausgewählt.
    </v-card-text>
  </v-card>

  <activity-create
    v-model="createDialog"
    :student-id="studentId"
    :selected-date="selectedDate ?? null"
    @created="handleCreated"
  />

  <activity-edit
    v-model="editDialog"
    :student-id="studentId"
    :activity="selectedTaetigkeit"
    @updated="handleUpdated"
  />
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  TaetigkeitenblockDTO,
} from "@/api/generated/api-spec/models";

import { computed, ref } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { TaetigkeitenblockControllerApi } from "@/api/generated/api-spec";
import { filterAndSortActivities } from "@/util/ActivityListUtil";
import { toDateString, toLocalTimeString } from "@/util/formatter";
import ActivityCreate from "./ActivityCreate.vue";
import ActivityEdit from "./ActivityEdit.vue";

const props = defineProps<{
  studentId: number;
  praktikum?: FullPraktikumDTO;
  selectedDate?: Date;
  canWrite: boolean;
}>();

const emit = defineEmits<{
  changed: [];
}>();

const taetigkeitenblockApi = ApiFactory.getInstance(
  TaetigkeitenblockControllerApi
);

const createDialog = ref(false);
const editDialog = ref(false);

const selectedTaetigkeit = ref<TaetigkeitenblockDTO | null>(null);

const selectedTaetigkeiten = computed<TaetigkeitenblockDTO[]>(() => {
  const selectedDate = props.selectedDate;

  if (!props.praktikum || !selectedDate) {
    return [];
  }

  return filterAndSortActivities(
    props.praktikum.taetigkeiten ?? [],
    selectedDate
  );
});

function openCreateDialog() {
  if (!props.canWrite || !props.selectedDate) {
    return;
  }

  createDialog.value = true;
}

function openEditDialog(taetigkeit: TaetigkeitenblockDTO) {
  if (!props.canWrite) {
    return;
  }

  selectedTaetigkeit.value = taetigkeit;
  editDialog.value = true;
}

async function deleteTaetigkeit(taetigkeit: TaetigkeitenblockDTO) {
  if (!props.canWrite) {
    return;
  }

  const id = taetigkeit.taetigkeitenblockID;

  if (
    id?.studentId === undefined ||
    id.tag === undefined ||
    id.beginnZeit === undefined ||
    id.endeZeit === undefined
  ) {
    return;
  }

  await taetigkeitenblockApi.deleteTaetigkeitenblock(
    id.studentId,
    id.beginnZeit,
    id.endeZeit,
    id.tag
  );

  emit("changed");
}

function handleCreated() {
  createDialog.value = false;
  emit("changed");
}

function handleUpdated() {
  editDialog.value = false;
  selectedTaetigkeit.value = null;
  emit("changed");
}
</script>

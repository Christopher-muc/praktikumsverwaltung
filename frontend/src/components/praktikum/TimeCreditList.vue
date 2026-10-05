<template>
  <v-card elevation="5">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Zeitgutschriften</span>

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

    <v-list v-if="selectedZeitgutschriften.length">
      <template
        v-for="(zeitgutschrift, index) in selectedZeitgutschriften"
        :key="zeitgutschrift.id ?? index"
      >
        <v-list-item>
          <div class="d-flex align-center">
            <div
              class="text-truncate"
              style="width: 50%"
            >
              {{ zeitgutschrift.grund }}
            </div>

            <div
              class="text-center"
              style="width: 50%"
            >
              {{ zeitgutschrift.mengeMinuten }} min
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
                  @click="openEditDialog(zeitgutschrift)"
                />

                <v-list-item
                  title="Löschen"
                  @click="deleteZeitgutschrift(zeitgutschrift)"
                />
              </v-list>
            </v-menu>
          </template>
        </v-list-item>

        <v-divider v-if="index < selectedZeitgutschriften.length - 1" />
      </template>
    </v-list>

    <v-card-text
      v-else-if="selectedDate"
      class="text-medium-emphasis"
    >
      Keine Zeitgutschriften hinterlegt.
    </v-card-text>

    <v-card-text
      v-else
      class="text-medium-emphasis"
    >
      Kein Datum ausgewählt.
    </v-card-text>

    <time-credit-dialog-create
      v-model="createDialog"
      :praktikum-id="studentId"
      :selected-date="selectedDate"
      @created="handleCreated"
    />

    <time-credit-dialog-edit
      v-model="editDialog"
      :praktikum-id="studentId"
      :zeitgutschrift="zeitgutschriftToEdit"
      @updated="handleUpdated"
    />
  </v-card>
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  SimpleZeitgutschriftDTO,
} from "@/api/generated/api-spec/models";

import { computed, ref } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { ZeitgutschriftControllerApi } from "@/api/generated/api-spec";
import TimeCreditDialogCreate from "@/components/praktikum/TimeCreditDialogCreate.vue";
import TimeCreditDialogEdit from "@/components/praktikum/TimeCreditDialogEdit.vue";
import { toDateString } from "@/util/formatter";
import { filterZeitgutschriftenByDate } from "@/util/TimeCreditListUtil";

const props = defineProps<{
  studentId: number;
  praktikum?: FullPraktikumDTO;
  selectedDate?: Date;
  canWrite: boolean;
}>();

const emit = defineEmits<{
  changed: [];
}>();

const zeitgutschriftApi = ApiFactory.getInstance(ZeitgutschriftControllerApi);

const createDialog = ref(false);
const editDialog = ref(false);

const zeitgutschriftToEdit = ref<SimpleZeitgutschriftDTO>();

const selectedZeitgutschriften = computed<SimpleZeitgutschriftDTO[]>(() => {
  if (!props.praktikum || !props.selectedDate) {
    return [];
  }

  return filterZeitgutschriftenByDate(
    props.praktikum.zeitgutschriften ?? [],
    props.selectedDate
  );
});

function openCreateDialog() {
  if (!props.canWrite || !props.selectedDate) {
    return;
  }

  createDialog.value = true;
}

function openEditDialog(zeitgutschrift: SimpleZeitgutschriftDTO) {
  if (!props.canWrite) {
    return;
  }

  zeitgutschriftToEdit.value = zeitgutschrift;
  editDialog.value = true;
}

async function deleteZeitgutschrift(zeitgutschrift: SimpleZeitgutschriftDTO) {
  if (!props.canWrite || zeitgutschrift.id === undefined) {
    return;
  }

  await zeitgutschriftApi.deleteZeitgutschrift(zeitgutschrift.id);

  emit("changed");
}

function handleCreated() {
  createDialog.value = false;
  emit("changed");
}

function handleUpdated() {
  editDialog.value = false;
  zeitgutschriftToEdit.value = undefined;
  emit("changed");
}
</script>

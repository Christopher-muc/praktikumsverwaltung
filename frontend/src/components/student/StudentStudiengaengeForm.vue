<template>
  <div>
    <v-progress-linear
      v-if="loading || optionsLoading"
      indeterminate
      class="mb-4"
    />

    <template v-else>
      <div class="text-h6 mb-2">Studiengänge</div>

      <v-autocomplete
        v-model="studiengaenge"
        :items="availableStudiengaenge"
        item-title="name"
        item-value="studiengangNr"
        label="Studiengänge"
        variant="outlined"
        multiple
        chips
        closable-chips
        clearable
        return-object
        no-data-text="Keine Studiengänge vorhanden"
      />
    </template>
  </div>
</template>

<script setup lang="ts">
import type { StudiengangDTO } from "@/api/generated/api-spec/models";

import { onMounted, ref } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { StudiengangControllerApi } from "@/api/generated/api-spec";

defineProps<{
  loading: boolean;
}>();

const studiengaenge = defineModel<StudiengangDTO[]>({
  required: true,
});

const studiengangApi = ApiFactory.getInstance(StudiengangControllerApi);

const availableStudiengaenge = ref<StudiengangDTO[]>([]);

const optionsLoading = ref(false);

onMounted(loadAvailableStudiengaenge);

async function loadAvailableStudiengaenge() {
  optionsLoading.value = true;

  availableStudiengaenge.value = await studiengangApi.getStudiengaenge();

  optionsLoading.value = false;
}
</script>

<template>
  <v-card
    :title="`${student.vorname ?? ''} ${student.nachname ?? ''}`"
    elevation="5"
  >
    <v-row>
      <!-- Praktikum -->
      <v-col cols="6">
        <v-card-text>
          <template v-if="praktikum">
            Praktikumsstart:
            {{
              praktikum.beginnDatum ? toDateString(praktikum.beginnDatum) : "-"
            }}

            <br />

            Praktikumsende:
            {{ praktikum.endeDatum ? toDateString(praktikum.endeDatum) : "-" }}

            <br />

            Benötigte Wochen:
            {{ praktikum.benoetigteWochen ?? "-" }}

            <br />

            Wochenarbeitszeit:
            {{ praktikum.wochenarbeitszeit ?? "-" }} h
          </template>

          <div
            v-else
            class="text-medium-emphasis py-4"
          >
            Kein Praktikum hinterlegt.
          </div>
        </v-card-text>
      </v-col>

      <!-- Studiengänge -->
      <v-col
        cols="6"
        class="border-s"
      >
        <v-card-text>
          <div class="text-h6 mb-3">Studiengänge</div>

          <v-row
            v-if="studiengaenge.length > 0"
            dense
          >
            <v-col
              v-for="studiengang in studiengaenge"
              :key="studiengang.studiengangNr"
              cols="6"
            >
              <v-chip
                variant="tonal"
                class="w-100 justify-center"
              >
                {{ studiengang.name }}
              </v-chip>
            </v-col>
          </v-row>

          <div
            v-else
            class="text-medium-emphasis py-4"
          >
            Keine Studiengänge hinterlegt.
          </div>
        </v-card-text>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import type {
  FullPraktikumDTO,
  StudentDTO,
  Studiengang,
} from "@/api/generated/api-spec/models";

import { computed } from "vue";

import { toDateString } from "@/util/formatter";

const props = defineProps<{
  student: StudentDTO;
  praktikum?: FullPraktikumDTO;
}>();

const studiengaenge = computed<Studiengang[]>(() => {
  return Array.from(props.student.studiengaenge ?? []).slice(0, 6);
});
</script>

import { getSecurityHeaders } from "@/api/fetch-utils.ts";
import { BaseAPI, Configuration } from "@/api/generated/api-spec";
import { BASE_API_PATH, STATUS_INDICATORS } from "@/constants.ts";
import { useSnackbarStore } from "@/stores/snackbar";
import { useValidationStore } from "@/stores/validation";


type ApiCtor<T extends BaseAPI> = new (config: Configuration) => T;

interface ValidationErrorResponse {
  errors?: Record<string, string[]>;
  globalErrors?: string[];
}

const instances = new Map<ApiCtor<BaseAPI>, BaseAPI>();

async function customFetch(url: string, init?: RequestInit) {
  const customInit: RequestInit = {
    ...init,
    mode: "cors",
    credentials: "same-origin",
    redirect: "manual",
  };

  const validationStore = useValidationStore();

  validationStore.clearErrors();

  const response = await fetch(url, customInit);

  if (!response.ok) {
    await handleErrorResponse(response);
  }

  return response;
}

async function handleErrorResponse(response: Response) {
  const snackbarStore = useSnackbarStore();
  const validationStore = useValidationStore();

  /*
   * 404 wird von den aufrufenden Komponenten behandelt.
   * Beispiel: Student besitzt kein Praktikum.
   */
  if (response.status === 404) {
    return;
  }

  /*
   * Fehlende Berechtigung.
   */
  if (response.status === 403) {
    snackbarStore.push({
      color: STATUS_INDICATORS.ERROR,
      text: "Sie haben nicht die nötigen Rechte um diese Aktion durchzuführen.",
    });

    return;
  }

  try {
    const body = (await response.clone().json()) as ValidationErrorResponse;

      /*
       * FieldErrors werden NICHT in der Snackbar angezeigt.
       * Sie werden für die Formularfelder gespeichert.
       */
      validationStore.setFieldErrors(body.errors ?? {});

      /*
       * Object-/GlobalErrors gehören in die Snackbar.
       */
      const globalMessages = body.globalErrors ?? [];

    for (const message of globalMessages) {
      snackbarStore.push({
        color: STATUS_INDICATORS.ERROR,
        text: cleanErrorMessage(message),
      });
    }

    const hasFieldErrors = Object.keys(body.errors ?? {}).length > 0;
    const hasGlobalErrors = globalMessages.length > 0;

    if (hasFieldErrors || hasGlobalErrors) {
      return;
    }
  } catch {
    // Response enthält kein erwartetes JSON-Fehlerformat.
  }

  snackbarStore.push({
    color: STATUS_INDICATORS.ERROR,
    text: "Es ist ein unbekannter Fehler aufgetreten.",
  });
}

function cleanErrorMessage(message: string): string {
  return message.replace(/^\d{3}\s+[A-Z_ ]+\s+"/, "").replace(/"$/, "");
}

function createConfig(): Configuration {
  return new Configuration({
    basePath: BASE_API_PATH,
    fetchApi: customFetch,
    middleware: [
      {
        pre: async (context) => {
          return {
            url: context.url,
            init: {
              ...context.init,
              headers: {
                ...context.init.headers,
                ...getSecurityHeaders(),
              },
            },
          };
        },
      },
    ],
  });
}

function getInstance<T extends BaseAPI>(ApiClass: ApiCtor<T>): T {
  const existing = instances.get(ApiClass);

  if (existing) {
    return existing as T;
  }

  const api = new ApiClass(createConfig());

  instances.set(ApiClass, api);

  return api;
}

export const ApiFactory = {
  getInstance,
} as const;

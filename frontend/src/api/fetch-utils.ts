/**
 * Returns a default GET-Config for fetch
 */
export function getConfig(): RequestInit {
  return {
    headers: getSecurityHeaders(),
    mode: "cors",
    credentials: "same-origin",
    redirect: "manual",
  };
}

/**
 * Covers the default handling of a response.
 *
 * - Reload the app at session timeout --> HTTP 3xx
 * - Throw an error for HTTP codes != 2xx
 *
 * @param response The response from the fetch command to be checked.
 * @param errorMessage The error message for an HTTP code != 2xx.
 */
export function defaultResponseHandler(
  response: Response,
  errorMessage = "Es ist ein unbekannter Fehler aufgetreten."
): void {
  if (response.ok) {
    return;
  }

  if (response.type === "opaqueredirect") {
    location.reload();
    return;
  }

  if (response.status === 403) {
    throw new Error(
      "Sie haben nicht die nötigen Rechte um diese Aktion durchzuführen."
    );
  }

  throw new Error(errorMessage);
}

/**
 * Default catch handler for service requests.
 *
 * @param error The error from the fetch command.
 * @param errorMessage The error message to throw.
 */
export function defaultCatchHandler(
  error: unknown,
  errorMessage = "Es ist ein unbekannter Fehler aufgetreten."
): PromiseLike<never> {
  throw new Error(errorMessage);
}

/**
 * Builds the security relevant headers.
 */
export function getSecurityHeaders(): HeadersInit {
  const headers: HeadersInit = {};
  const csrfCookie = getXSRFToken();

  if (csrfCookie !== "") {
    headers["X-XSRF-TOKEN"] = csrfCookie;
  }

  return headers;
}

/**
 * Returns the XSRF-TOKEN.
 */
function getXSRFToken(): string {
  const help = document.cookie.match(
    "(^|;)\\s*" + "XSRF-TOKEN" + "\\s*=\\s*([^;]+)"
  );

  return (help ? help.pop() : "") as string;
}

# ZeitgutschriftControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createZeitgutschrift**](ZeitgutschriftControllerApi.md#createzeitgutschrift) | **POST** /zeitgutschrift |  |
| [**deleteZeitgutschrift**](ZeitgutschriftControllerApi.md#deletezeitgutschrift) | **DELETE** /zeitgutschrift/{id} |  |
| [**updateZeitgutschrift**](ZeitgutschriftControllerApi.md#updatezeitgutschrift) | **PUT** /zeitgutschrift |  |



## createZeitgutschrift

> number createZeitgutschrift(zeitgutschriftCreateDTO)



### Example

```ts
import {
  Configuration,
  ZeitgutschriftControllerApi,
} from '';
import type { CreateZeitgutschriftRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new ZeitgutschriftControllerApi();

  const body = {
    // ZeitgutschriftCreateDTO
    zeitgutschriftCreateDTO: ...,
  } satisfies CreateZeitgutschriftRequest;

  try {
    const data = await api.createZeitgutschrift(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **zeitgutschriftCreateDTO** | [ZeitgutschriftCreateDTO](ZeitgutschriftCreateDTO.md) |  | |

### Return type

**number**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## deleteZeitgutschrift

> deleteZeitgutschrift(id)



### Example

```ts
import {
  Configuration,
  ZeitgutschriftControllerApi,
} from '';
import type { DeleteZeitgutschriftRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new ZeitgutschriftControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies DeleteZeitgutschriftRequest;

  try {
    const data = await api.deleteZeitgutschrift(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## updateZeitgutschrift

> updateZeitgutschrift(zeitgutschriftUpdateDTO)



### Example

```ts
import {
  Configuration,
  ZeitgutschriftControllerApi,
} from '';
import type { UpdateZeitgutschriftRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new ZeitgutschriftControllerApi();

  const body = {
    // ZeitgutschriftUpdateDTO
    zeitgutschriftUpdateDTO: ...,
  } satisfies UpdateZeitgutschriftRequest;

  try {
    const data = await api.updateZeitgutschrift(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **zeitgutschriftUpdateDTO** | [ZeitgutschriftUpdateDTO](ZeitgutschriftUpdateDTO.md) |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No Content |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


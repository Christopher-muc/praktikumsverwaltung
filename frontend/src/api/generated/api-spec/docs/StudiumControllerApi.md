# StudiumControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addStudiumToStudent**](StudiumControllerApi.md#addstudiumtostudent) | **PUT** /studium |  |
| [**getStudentsByStudiengang**](StudiumControllerApi.md#getstudentsbystudiengang) | **GET** /studium/{id} |  |
| [**removeStudiumfromStudent**](StudiumControllerApi.md#removestudiumfromstudent) | **DELETE** /studium |  |



## addStudiumToStudent

> addStudiumToStudent(studiumMappingDTO)



### Example

```ts
import {
  Configuration,
  StudiumControllerApi,
} from '';
import type { AddStudiumToStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiumControllerApi();

  const body = {
    // StudiumMappingDTO
    studiumMappingDTO: ...,
  } satisfies AddStudiumToStudentRequest;

  try {
    const data = await api.addStudiumToStudent(body);
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
| **studiumMappingDTO** | [StudiumMappingDTO](StudiumMappingDTO.md) |  | |

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
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getStudentsByStudiengang

> Array&lt;SimpleStudentDTO&gt; getStudentsByStudiengang(id)



### Example

```ts
import {
  Configuration,
  StudiumControllerApi,
} from '';
import type { GetStudentsByStudiengangRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiumControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies GetStudentsByStudiengangRequest;

  try {
    const data = await api.getStudentsByStudiengang(body);
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

[**Array&lt;SimpleStudentDTO&gt;**](SimpleStudentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## removeStudiumfromStudent

> removeStudiumfromStudent(studiumMappingDTO)



### Example

```ts
import {
  Configuration,
  StudiumControllerApi,
} from '';
import type { RemoveStudiumfromStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiumControllerApi();

  const body = {
    // StudiumMappingDTO
    studiumMappingDTO: ...,
  } satisfies RemoveStudiumfromStudentRequest;

  try {
    const data = await api.removeStudiumfromStudent(body);
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
| **studiumMappingDTO** | [StudiumMappingDTO](StudiumMappingDTO.md) |  | |

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
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


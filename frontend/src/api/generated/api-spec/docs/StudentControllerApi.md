# StudentControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createStudent**](StudentControllerApi.md#createstudent) | **POST** /student |  |
| [**deleteStudent**](StudentControllerApi.md#deletestudent) | **DELETE** /student/{id} |  |
| [**getAllStudents**](StudentControllerApi.md#getallstudents) | **GET** /students |  |
| [**getStudent**](StudentControllerApi.md#getstudent) | **GET** /student/{id} |  |
| [**getStudentByName**](StudentControllerApi.md#getstudentbyname) | **GET** /studentByName |  |
| [**updateStudent**](StudentControllerApi.md#updatestudent) | **PUT** /student/{id} |  |



## createStudent

> number createStudent(createUpdateStudentRequestDTO)



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { CreateStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  const body = {
    // CreateUpdateStudentRequestDTO
    createUpdateStudentRequestDTO: ...,
  } satisfies CreateStudentRequest;

  try {
    const data = await api.createStudent(body);
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
| **createUpdateStudentRequestDTO** | [CreateUpdateStudentRequestDTO](CreateUpdateStudentRequestDTO.md) |  | |

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


## deleteStudent

> deleteStudent(id)



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { DeleteStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies DeleteStudentRequest;

  try {
    const data = await api.deleteStudent(body);
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


## getAllStudents

> Array&lt;SimpleStudentDTO&gt; getAllStudents()



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { GetAllStudentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  try {
    const data = await api.getAllStudents();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters

This endpoint does not need any parameter.

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


## getStudent

> StudentDTO getStudent(id)



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { GetStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies GetStudentRequest;

  try {
    const data = await api.getStudent(body);
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

[**StudentDTO**](StudentDTO.md)

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


## getStudentByName

> Array&lt;SimpleStudentDTO&gt; getStudentByName(vorname, nachname)



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { GetStudentByNameRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  const body = {
    // string (optional)
    vorname: vorname_example,
    // string (optional)
    nachname: nachname_example,
  } satisfies GetStudentByNameRequest;

  try {
    const data = await api.getStudentByName(body);
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
| **vorname** | `string` |  | [Optional] [Defaults to `&#39;&#39;`] |
| **nachname** | `string` |  | [Optional] [Defaults to `&#39;&#39;`] |

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


## updateStudent

> StudentDTO updateStudent(id, createUpdateStudentRequestDTO)



### Example

```ts
import {
  Configuration,
  StudentControllerApi,
} from '';
import type { UpdateStudentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudentControllerApi();

  const body = {
    // number
    id: 56,
    // CreateUpdateStudentRequestDTO
    createUpdateStudentRequestDTO: ...,
  } satisfies UpdateStudentRequest;

  try {
    const data = await api.updateStudent(body);
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
| **createUpdateStudentRequestDTO** | [CreateUpdateStudentRequestDTO](CreateUpdateStudentRequestDTO.md) |  | |

### Return type

[**StudentDTO**](StudentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


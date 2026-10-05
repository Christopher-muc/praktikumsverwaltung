
# StudentDTO


## Properties

Name | Type
------------ | -------------
`studentId` | number
`vorname` | string
`nachname` | string
`studiengaenge` | [Array&lt;Studiengang&gt;](Studiengang.md)
`praktikum` | [PraktikumDTO](PraktikumDTO.md)
`url` | string

## Example

```typescript
import type { StudentDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "studentId": null,
  "vorname": null,
  "nachname": null,
  "studiengaenge": null,
  "praktikum": null,
  "url": null,
} satisfies StudentDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as StudentDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)



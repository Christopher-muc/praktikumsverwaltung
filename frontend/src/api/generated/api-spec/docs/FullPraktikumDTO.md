
# FullPraktikumDTO


## Properties

Name | Type
------------ | -------------
`beginnDatum` | Date
`endeDatum` | Date
`studentId` | number
`wochenarbeitszeit` | number
`benoetigteWochen` | number
`taetigkeiten` | [Array&lt;TaetigkeitenblockDTO&gt;](TaetigkeitenblockDTO.md)
`zeitgutschriften` | [Array&lt;SimpleZeitgutschriftDTO&gt;](SimpleZeitgutschriftDTO.md)

## Example

```typescript
import type { FullPraktikumDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "beginnDatum": null,
  "endeDatum": null,
  "studentId": null,
  "wochenarbeitszeit": null,
  "benoetigteWochen": null,
  "taetigkeiten": null,
  "zeitgutschriften": null,
} satisfies FullPraktikumDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as FullPraktikumDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)



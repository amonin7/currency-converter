# Currency converter
## Summary
This is a reactive REST endpoint to handle the currency conversion.

## Details
The REST endpoint responds to a POST request to the URI `/currency/convert` with a body of type `ConversionRequest`, that will contain three fields: `from`, `to`, `amount`.

The fields `from` and `to` are three-letter strings representing the currencies, like USD or EUR and `amount` field contains the decimal number, representing the quantity to be converted, e.g. 123.45.

The result of this POST request will be an object of type `ConversionResponse`, containing the conversion  result in these four fields:
- `from` contains the starting currency from where we are converting
- `to` contains the destination currency
- `amount` contains the original amount to be converted
- `converted` contains the converted result in the destination currency

The external providers which provide the currency conversion rates are two:
- https://open.er-api.com/v6/latest/EUR
- http://api.exchangeratesapi.io/latest?base=EUR&access_key=...

These two providers offer a free currency conversion rates endpoint that can be used to retrieve the actual rate and perform the conversion.

The next approach is followed, when receiving the conversion request: one provider is randomly chosen from the two, and the conversion rates are got from it.
- If the chosen provider is offline or is having problem, the app switches to another provider.
  - If another provider is offline as well, the app returns the error, indicating that there are no providers available.
- If the chosen provider is online and provides the conversion rate, then it is used to convert the  requested amount.

## Serialization format

JSON is used as the serialization format for the `ConversionRequest` and `ConversionResponse` objects.

## Example

Assuming a POST request to `/currency/convert` with the following body:
```json
{
  "from": "EUR",
  "to": "USD",
  "amount": 123.45
}
```
and a conversion rate of `1.1` from EUR to USD (obtained from the external provider of currency rates), the result should be:
```json
{
  "from": "EUR",
  "to": "USD",
  "amount": 123.45,
  "converted": 135.78
}
```
where `135.78` is the result of `123.45 * 1.1`, amount times the conversion rate.

## Requisites

- Java 11
- Spring WebFlux and Spring Boot 2
- Spring Reactive WebClient
- Project Reactor (Mono, Flux, etc), included in Spring WebFlux
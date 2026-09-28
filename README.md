# NeumoNote

Base Android em Kotlin para o app NeumoNote, com Room, DataStore Preferences,
armazenamento seguro da senha mestra e tema Material 3.

## Requisitos

- JDK 17
- Android SDK 34
- Fontes em `app/src/main/res/font/`: `poppins_bold.ttf`,
  `poppins_semibold.ttf`, `montserrat_regular.ttf` e `montserrat_medium.ttf`

## Compilar

Configure o SDK Android no ambiente (`ANDROID_HOME` ou `sdk.dir` em
`local.properties`) e execute:

```shell
./gradlew :app:assembleDebug
```
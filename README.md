[![Discord](https://img.shields.io/discord/591914197219016707.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/D426uQcCbV)
[![GitHub release](https://img.shields.io/github/release/Naereen/StrapDown.js.svg)](https://github.com/stevimeister/combat-plugin/releases/)
[![GitHub forks](https://img.shields.io/github/forks/Naereen/StrapDown.js.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/stevimeister/combat-plugin/network/)
[![GitHub stars](https://img.shields.io/github/stars/Naereen/StrapDown.js.svg?style=social&label=Star&maxAge=2592000)](https://github.com/stevimeister/combat-plugin/stargazers/)
[![GitHub watchers](https://img.shields.io/github/watchers/Naereen/StrapDown.js.svg?style=social&label=Watch&maxAge=2592000)](https://github.com/stevimeister/combat-plugin/watchers/)


# Czym jest combat-plugin?
* combat-plugin jest wtyczką na serwery Minecraft który ma za zadanie karać graczy którzy wyjdą z serwera podczas walki. Wtyczka działa na wersjach od 1.8.x aż do 1.16.x!

# Konfiguracja wtyczki
``` Yaml
actionBar:
  symbol: '●'
  colourOne: '&a'
  colourTwo: '&7'
  message: '&eANTY-LOGOUT &7{TIME} &7[{PATTERN}&7]'
combat:
  time: '20s'
messages:
  started: '&4UWAGA!: &CJestes podczas walki, mozesz sie wylogowac za 20 sekund'
  finished: '&aNie jesteś już podczas walki {N} &amożesz bezpiecznie się wylogować!' # Zmienna {N} oznacza oddzielenie pomiędzy title a subtitle
  combatLogged: '&8&l>> &4Gracz &c{NAME} &4wylogował się podczas walki!'
```


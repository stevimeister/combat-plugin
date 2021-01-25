[![Discord](https://img.shields.io/discord/591914197219016707.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/D426uQcCbV)
[![GitHub release](https://img.shields.io/github/release/Naereen/StrapDown.js.svg)](https://github.com/stevimeister/combat-plugin/releases/)
[![bStats Players](https://img.shields.io/bstats/players/9894)](https://bstats.org/plugin/bukkit/combat-plugin/9894)
[![bStats Players](https://img.shields.io/bstats/servers/9894)](https://bstats.org/plugin/bukkit/combat-plugin/9894)

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
  time: 20 # W sekundach
  bypassPermission: 'combat-bypass' # Uprawnienie pozwalające graczu korzystać z zablokowanych komend oraz wylogowanie się z serwera podczas walki.
  commands: # Lista odblokowanych komend podczas walki.
    - '/msg'
    - '/reply'
    - '/efekty'
messages:
  started: '&4UWAGA!: &CJestes podczas walki, mozesz sie wylogowac za 20 sekund'
  finished: '&aNie jesteś już podczas walki{N}&amożesz bezpiecznie się wylogować!' # Zmienna {N} oznacza oddzielenie pomiędzy title a subtitle.
  combatLogged: '&8&l>> &4Gracz &c{NAME} &4wylogował się podczas walki!'
  commandBlocked: '&8&l>> &4Nie możesz używać komendy &c{COMMAND} &4podczas walki!'
```


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


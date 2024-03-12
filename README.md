# filmi-leidja
CGI suvepraktikale kandideerimise ülesanne

## Kasutatud tehnoloogiad
- Java 21 ☕️
- Spring Boot 🍃
- MongoDB 📊
- Vue.js 🌟

## Rakenduse käivitamine
### Vajalik tarkvara
- Docker Engine 🐋

### Käivita rakendus
```bash
docker-compose up
```
Rakenduse esmakordne käivitus võtab veidike aega, sest _Docker_ peab
vajalikud konteineri pildid allalaadima.

### Rakenduse avamine
Kui _filmi-leidja_ konteiner käivitus edukalt, saab rakenduse avada
veebilehitsejas leheküljel `localhost:8080/`


## Kinokava koostamine
Rakenduse esmakordsel käivitusel koostatakse üheks kuuks suvaline kinokava järgmiste nõuetega:
- päeva esimene seanss algab kino avamisel (09:00)
- seanss ei tohi alata peale kino sulgemist (21:30)
- viimane seanss võib lõppeda peale kino sulgemist
- seansside vahele peab jääma vähemalt 15 minutit
- seanssi algused ümardatakse 5 minuti peale
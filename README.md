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
Rakenduse esmakordsel käivitusel koostatakse üheks nädalaks suvaline kinokava järgmiste nõuetega:
- päeva esimene seanss algab kino avamisel (09:00)
- seanss ei tohi alata peale kino sulgemist (21:30)
- viimane seanss võib lõppeda peale kino sulgemist
- seansside vahele peab jääma vähemalt 15 minutit
- seanssi algused ümardatakse 5 minuti peale

## Kinokava filtreerimine ja sorteerimine
Kinokava lehekülje esma laadimisel näidatakse kasutajale kogu nädala kinokava, seanssi algus
hetke järgi sorteeritult.

### Žanrite filtreerimine
Kasutaja saab kinokava filtreerida žanrite järgi. Talle näidatakse ainult seansse, mille filmid
kuuluvad valitud žanritesse. Kui üksi žanr pole valitud, näidatakse kõiki žanreid.

### Seanssi algusaja filtreerimine ja sorteerimine
Kasutaja saab kinokava seansside algus kellaaja järgi filtreerida. Peale esmast algusaja
seadmist filtreeritakse kinokavast välja seanssid, mis algavad varem kui algus kellaaeg
ja sorteeritakse kellaaja järgi kasvavalt (ehk kõige paremini algusajaga kokku minevad
seanssid näidatakse esimesena).
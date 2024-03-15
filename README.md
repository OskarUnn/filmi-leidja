# filmi-leidja
CGI suvepraktikale kandideerimise ülesanne

## Kasutatud tehnoloogiad
- Java 21 ☕️
- Spring Boot 🍃
- MongoDB 📊
- Vue.js 🌟
- Tailwind CSS 💨

## Rakenduse käivitamine
### Vajalik tarkvara
- Docker 🐋

### Rakenduse käivitamine
1. Klooni projekt oma masinasse käsuga:
```bash
git clone https://github.com/OskarUnn/filmi-leidja.git
```
2. Projekti juurkaustast, loo filmi-leidja ja mongo konteinerid käsuga:
```bash
docker-compose up
```
Rakenduse esmakordne käivitus võtab veidike aega, sest _Docker_ peab
vajalikud konteineri pildid allalaadima.

### Rakenduse avamine
Kui _filmi-leidja_ konteiner käivitus edukalt, saab rakenduse avada
veebilehitsejas leheküljel `localhost:8080/`

## Autori kommentaar
Projektile kulus umbes 20h kokku.

Ma ei olnud varem kasutanud Spring Boot-i, seega investeerisin alguses paar tundi projekti korrektsele
ülesseadmisele ja raamistiku õppimisele. See [õpetus](https://www.youtube.com/watch?v=ssj0CGxv60k)
oli suureks abiks.

Keeruline oli Vue front-end ja Spring Booti back-end harmooniliselt tööle saada. Selle
kohta ei ledinud ma ka materjale (ainsad õpetused, mis ma leidsin, kasutasid Spring
Booti lihtsalt veebiteenusena ja frontend oli täiesti eraldi Node.js serveri peal).

Selle projekti puhul nautisin back-endi kirjutamist kordades rohkem kui front-endi. Andmete töötlus Spring Boot-i
ja MongoDB-ga on väga meeldiv.

Tegemata jäi kasutaja vaatamis ajaloo järgi seanssi soovituste andmine. Selle lahenduseks oleksin teinud
andmebaasi uue repositooriumi, kus hoida iga kasutaja kohta järjendit seanssidest(mitte kogu objekt vaid ID), kus
seanssidel on ta varem käinud. Nende põhjal sorteerida kinokava (sarnaselt istme soovitus algoritmile) võttes arvesse
žanrite kattuvust, seanssi keelt, algusaja sobivust ja ega ta seda filmi veel näinud ei ole.

## Kinokava koostamine
Rakenduse esmakordsel käivitusel koostatakse üheks nädalaks suvaline kinokava järgmiste nõuetega:
- päeva esimene seanss algab kino avamisel (09:00)
- seanss ei tohi alata peale kino sulgemist (21:30)
- viimane seanss võib lõppeda peale kino sulgemist
- seansside vahele peab jääma vähemalt 15 minutit
- seanssi algused ümardatakse 5 minuti peale
- seanssile määratakse juhuslikult juba kinni olevad istekohad

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

## Istekohtade soovitamine
Istekohtade valimis lehekülje avanemisel, valitakse kasutajale soovitud arv istekohti
istekoha soovitus algoritmi põhjal.

### Algoritm
Esimene valitav istekoht valitakse võimalikult kinosaali keskel. Iga järgnev istekoht
valitakse samuti võimalikult kinosaali keskel, aga kaalutletakse ka seda, et järgmine istekoht
oleks samas reas eelnevalt valitud kohaga.

See ei ole ideaalne algoritm, sest võib ikka juhtuda, et mõni istekoht valitakse teisest reast
või jäetakse keegi võõras valitud kohtade vahele.

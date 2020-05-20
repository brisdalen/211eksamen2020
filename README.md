## Oppgave 1  

a) Jeg ville brukt en Prioritetskø for å lagre hendelsene.  
Fra standardbiblioteket finner vi PriorityQueue klassen.
Dette velger jeg fordi oppgavebeskrivelsen spør etter både rask lagring og henting, og at de automatisk skal komme i stigende rekkefølge.
PriorityQueue har O(log(n)) tid når man legger til (add) og fjerner (remove/poll) elementer fra køen, og konstant tid  - O(1) - ved bruk av peek (Oracle, 2020).

Oracle. (2020). Class PriorityQueue<E>. Hentet fra: https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html

## Oppgave 2  

a) Siden vi skal holde på alle personer, og det hverken skal legges til eller fjernes personer under simuleringen så ville jeg brukt en helt vanlig array av Person.
Det trengs da ikke å bruke noen klasser fra Standardbiblioteket for akkurat denne strukturen, da arrays er primitive typer.
Siden alle personer opprettes med en gang vil det være en O(n) tid for opprettelse (da hvert element i arrayen må initialiseres, men det ser ut til å gå an å initialisere en array i konstant tid på bekostning av stort minnebruk(Bendersky, 2008)), og O(1) ved akksessering.  


Men, i min implementasjon skaper dette et lite probelm under getRandomPerson() (forklart i koden). 
I min implementasjon velger getRandomPerosn en tilfeldig person, og sjekker om den personen ikke har staten DEAD; 
om personen har denne staten velger den en ny person, helt til den finner en person som ikke er død. 
Siden jeg bruker en array og aldri fjerner de døde personene, vil metoden for hver gang noen dør ha en større sannsynlighet for å velge allerede døde personer.
Uten å ha my erfaring innen utregning av big O, tror jeg dette vil føre til følgende effektivitet:
si at total populasjon er *n*, DEATH_PROBILITY er *p* og totalt døde er \[D], da vil det bli en big O notasjon på O(n/(n-\[D])). 
Om jeg forstår det riktig representerer denne formelen at den blir mindre effektive jo flere som har dødd, men vel 0 døde er det konstanttid O(1).  

For å løse dette kunne man brukt en ArrayList for levende og en ArrayList for døde, og gitt dem begge en initialCapacity lik POPULATION, og så fjernet de som dør fra listen over levende personer.
Det ville da ikke blitt nødvendig å hverken opp- eller nedskalere de underliggende arrayene til listene. 
Derimot vil det være mye unødvendig plass i bruk, da vi har 2 lister i bruk, der vi maksimalt bruker halve plassen totalt.
En mulig løsning til dette ville vært å gitt listen til de døde en initialCapacity lik POPULATION * DEATH_PROBABILITY, noe som vil gi den underliggende arrayen plass til maksimalt antall døde personer.  

Heldigvis er overnevnte problemer bare et problem under simuleringer der både DEATH_PROBILITY og SICK_PROBABILITY nærmer seg 1.0.
I Italie er dødeligheten for koronaviruset ca. 14% (worldometers, 2020) og ca. 6% i USA (WHO, 2020), så det problemet ville ikke oppstått under denne simuleringen.

Bendersky, E. (2008, 23. august). Initializing an array in constant time. Hentet fra: https://eli.thegreenplace.net/2008/08/23/initializing-an-array-in-constant-time/

World Health Organization. (2020, 20. mai). Situation Report - 120. *Coronavirus disease (COVID-19)*. Hentet fra: https://www.who.int/docs/default-source/coronaviruse/situation-reports/20200519-covid-19-sitrep-120.pdf?sfvrsn=515cabfb_2 

Worldometers. (2020, 20. mai). World / Countries / Italy. Hentet fra: https://www.worldometers.info/coronavirus/country/italy/

## Oppgave 3
Liste over nye Events laget i oppgave 3:
  * SickEvent - happen() i SickEvent sjekker først hvor mange CLEAN personer den syke personen møter på denne dagen.
Til slutt sjekkes det om personen overlever dagen. 
Om personen overlever og ikke blir immun vil det legges inn et nytt SickEvent i eventQueue med den samme personen. 
  * CloseEncounter - oppstår når en syk person møter en frisk person. Hvem den syke personen er spiller ingen rolle, så det har jeg ingen referanse til.
I happen() sjekkes det om den friske personen blir syk eller ei etter hendelsen.  

Jeg fant ingen flere nødvendige Events for å fullføre simuleringene.

## Oppgave 4
For å kunne telle opp antall personer i hver tilstand, så må man første legge til variable for hver tilstand. 
Jeg lagde disse globale, og kalte dem for int clean, int sick, int dead og int immune og disse finnes på linje 40 - 47 i PandemiSim.java.
Alle sammen printes ut på slutten av runDay.
For å kunne endre disse verdiene fra hvert Event lagde jeg 5 public metoder i PandemiSim.java og lagt inn et paramter av typen PandemiSim i hver constructor for hvert Event: decrementClean, incrementSick, decrementSick, incrementDead og incrementImmune. 
Grunnen til at ikke alle verdiene har både en inkrementerende og dekrementerende metode forklares i en kommentar for hver metode.  

I Conf.java har jeg lagt inn kommentarer for hva som var originalverdier, slik at man kan leke seg rundt med forskjellige verdier men alltid med mulighet for å gå tilbake til de man startet med.
I sim.run i main metoden har jeg lagt inn et boolean parameter for om man ønsker detaljert oversikt over alt som skjer hver dag, eller om man kun ønsker statistikken for hver dag.
Ved true får man alle beskjeder, mens med false vil kun statistikken være synlig for hver dag.

## Observasjoner
Det er veldig interessant å se på ulike resultater gjennom denne simuleringen.
Å endre MAX_CONTACTS_PER_DAY fra 3 til 2, minsker antall døde på dag 99 fra rundt 9000 til 7500, samt med over 1500 flere CLEAN personer.  

Øker man også dødeligheten, men beholder MAX_CONTACTS_PER_DAY som 2, vil alle de syke dø før de rekker å spre den til for mange usmittede personer; 
litt motstridende at en mer dødelig sykdom kan føre til færre totalt døde, men det kan selvølgelig være på grunn av den oversimplifiserte simuleringen kontra virkeligheten.

Om man hadde inkludert en eller annen threshold for hvor mye kapasitet helsevesenet hadde hatt, og dette da påvirket DEATH_PROBILITY på et vis, så hadde simuleringen blitt mye mer realistisk og interessant med en gang.
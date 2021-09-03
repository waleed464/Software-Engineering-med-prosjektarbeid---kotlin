Bug i emulatoren (mistenker vi):
Dersom posisjonen til telefonen (blå prikk på kartet) ikke dukker opp i emulatoren
selv om posisjonen er satt i emulatoren, gå inn på Google Maps i emulatoren og finn
posisjonen der. Denne buggen skjer alltid hvis man har en helt ny (wipet) emulator, 
avviser (deny) når appen spør om tillatelse til å bruke posisjonen, og ved neste kjøring 
tillater appen å bruke posisjonen (allow). Da klarer ikke appen å finne telefonens posisjon.
Vi mistenker at det er en emulator bug, og at emulatoren må "oppdage" posisjonen sin, 
som den gjør hvis man bruker Google Maps i emulatoren til å finne posisjonen.
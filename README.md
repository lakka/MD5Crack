tiralabra
=========
Määrittelydokumentti

Tässä työssä tulen toteuttamaan Javalla MD5-hasheja murtavan algoritmin, 
joka käyttää hyväkseen rainbow tables -menetelmää. Lisäksi toteutan myös 
hajautustaulualgoritmin sovellusta varten.

Valitsin rainbow tables -menetelmän ongelman ratkaisuun, sillä sen avulla hashien murtaminen
on tehokkaampaa, kuin brute force -tavalla.

Ohjelma saa syötteeksi murrettavan MD5-hashin ja aakkoston, jota salasanat käyttävät, tai
vaihtoehtoisesti se voidaan käskeä luomaan uusi rainbow table.

Aika- ja tilavaativuudet:
Hajautustaulun insert, search ja delete-operaatiot ajassa O(1)

Lisää rainbow tablesista:
http://lasecwww.epfl.ch/~oechslin/publications/crypto03.pdf
"Making a Faster Cryptanalytic Time-Memory Trade-Off", Philippe Oechslin

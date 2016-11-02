# Authors
 - Pelle Carlsen
 - Kasper Roland Pagh
 
# The feature
REST er et værktøj som fokuserer på at hente ressourcer/data til sit program. Det er et værktøj til
at tilgå data ved hjælp af internettet. REST er designet til at bruge en stateless kommunikations
protokol, http. Ved at bruge standardiserede protokoller, får vi mulighed for at snakke sammen
med forskellige platforme. Dette gør det nemmere at dele kode.
Ens data bliver tilgået igennem REST ved hjælp af URl'er, Det er en måde at identificere hvor man
bliver sendt hen. Hver URl kan have 4 metoder: GET, DELETE, PUT og POST. Ved brug af GET
kan man få data ud, bruger man DELETE kan man slette date. Ved hjælp af PUT kan man ændre i
data, og POST kan man tilføje data.
Når man arbejder med REST giver man sin data et andet format. Dette gør at data'en er lettere
tilgængelig blandt flere platforme/sprog. Her kan man fx. benyttes sig af XML eller JSON, som
bliver lavet til et httprequest/httprespond. Når man arbejder med REST bruger man altid
httprequest/httprespond, som er der hvor al din data bliver gemt. 

# Kode eksempler
```
fun networkChecker(): Boolean
{
 val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
 val networkInfo = connMgr.activeNetworkInfo
 if (networkInfo != null && networkInfo.isConnected)
 {
 return true //Eller kør dit netværks kald direkte!
 }
 else
 {
 return false //evt en toast hvor der står "intet internet" eller
 //deslige
 }
}
```
# Manifest
Disse linjer er vigtige, da din smartphone ikke kan connecte til nettet uden!
```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

# Tjekliste for http request med både input og output
```
val url = URL(myurl)
val conn = url.openConnection() as HttpURLConnection
conn.requestMethod = "GET" //Eller DELETE, PUT, POST etc.
//sæt sending type and receiving type til json eller whatever.
//Da understående er almindelige httpHeaders, kan du selvfølgelig
//også bruge samme metode til andre headers.
conn.setRequestProperty("Content-Type", "application/json")
conn.setRequestProperty("Accept", "application/json")
//Smid din body ind i out
out :OutputStream? = conn.outputStream
out .write(jsonString1.toByteArray(charset("UTF-8")))
//Husk at lukke streams
out .close()
conn.doInput = true //Nødvendig hvis man vil have en body tilbage.
conn.doOutput = true //nødvendig hvis man vil sende en body med.
conn.connect() //Starter forbindelsen og sender requestet
val status = = conn.responseCode //http koden på svaret (200, 404 etc)
input: InputStream? = conn.inputStream
//De to udråbstegn er random, burde virke uden, men converteren sætter dem ind!
val bufferedReader = BufferedReader(InputStreamReader(input!!))
//Kotlin tillader ikke assignment inde i conditional delen af et while loop (eller conditionals generelt)
//(som ellers er den gængse java måde) på denne form while(line = bufferedReader.readLine()) !=
null)
//brug derfor en anden struktur, eks:
do
{
 line = bufferedReader.readLine()
 sb.append(line) //sb er en stringBuilder.
 if (line == null)
 {
 break
 }
}
while (true)
//sb.toString kan da returneres (eller puttes i et intent og sendes til en anden activity eller sådan
noget)

```

# Pitfalls og hacks
Der er intet af Rest som virker hvis ikke man har internet. Derfor hjalp det os meget, at hver gang
vi laver et kald, så tjekker vi lige på om der er internet først. Hvis ikke der er internet så giver vi
besked tilbage til brugere. vores tjekker kan i ser herunder.
```
public boolean
networkChecker()
 {
 ConnectivityManager connMgr = (ConnectivityManager)
getSystemService(Context.CONNECTIVITY_SERVICE);
 NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
 //Denne del er meget vigtig, da netwærk ikke nødvendigvis er et given på mobile devices!
Derfor skal man have noget logik der giver en fejl i tilfælde af intet net!
 if (networkInfo != null && networkInfo.isConnected())
 {
 return true;
 } else
 {
 return false;
 }
 }
```
Vi brugte AsyncTask til at lave vores REST. Det gjorde det nemt at sætte op, dog skal man være
opmærksom på at de kun anbefaler det til små opgaver.
Hertil skal man også huske at sætte en timeout, sådan at hvis der skulle være et eller andet galt
med ens backend, så bliver den stoppet efter x antal tid.

```
URL url =
new
URL(myurl);
 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 conn.setReadTimeout(10000 /* milliseconds */);
 conn.setConnectTimeout(15000 /* milliseconds */);
 conn.setRequestMethod("POST");
```


# Kildeliste
effektive kilder:
Developer.android har været en god kilde igennem det hele. og det er her vi har fået det meste fra.
https://developer.android.com/training/basics/network-ops/connecting.html
Vi har også brugt den her for at have mere end 1 view på det.
http://guruparang.blogspot.dk/2016/01/example-on-working-with-json-and.html

Dårlige kilder:
Vi brugte også stackoverflow en del. men fandt hurtigt ud af at man skal passe meget på når man
går derind, for de anbefaler næsten alle sammen at man bruger ApahceHttpClient.
ApahceHttpClient udgik i android 6,0 som set i artiklen nedenunder.
http://stackoverflow.com/
https://developer.android.com/about/versions/marshmallow/android-6.0-changes.htm

# Github links
I Kotlin: https://github.com/kasperpagh/Android-Kotlin-article

I plain Java: https://github.com/kasperpagh/Android-Kotlin-article-PlainJava

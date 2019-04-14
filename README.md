# Code 4 Venezuela

## Introducción

Esta solución responde al problema de [Médicos por la Salud](https://github.com/code-for-venezuela/2019-april-codeathon/tree/master/challenges/MPV-INF) para poder identificar necesidades y posibles epidemias utilizando Twitter.

Nos enfocamos en poder automatizar la ingesta de datos y la visualización de las mismos.

## Instalación

### Base de Datos

Crear una base de datos Postgres y crear las tablas tal como se indica en [CrearTablas.sql](CrearTablas.sql).


### Bajada de Tweets

#### Virtual Environment

Para la bajada de Tweets se requiere utilizar el archivo [environment.yml](environment.yml) para crear el virtual environment de Anaconda con el comando:

```bash
conda env create -f environment.yml
```

Esto creará el virtual environment `C4V`.

#### Storage

El storage del script es Azure Storage.
	
## Uso

### Bajada de Tweets

La bajada de tweets toma los siguientes parámetros de la base de datos:

Tabla `busquedas`: 

* La columna `busqueda` contiene el texto a buscar en twitter.

Tabla `ubicaciones`:

* `lat`: Latitud del centro de la ciudad a analizar.
* `lon`: Longitud del centro de la ciudad a analizar.
* `rad`: Radio de la ciudad a analizar, es un string y el formato es 'radiounidad de medida' ej: '10km'
* 'ubicacion': La descripción de la ciudad.

Se debe crear una carpeta params dentro de la carpeta `getTweets` que contenga los siguientes json:

`twitterKeys.json`

{
<br>"access_token" : ,
<br>"access_token_secret" : ,
<br>"consumer_key": ,
<br>"consumer_secret": 
<br>}

`database.json`

{
<br>"server":,
<br>"port":,
<br>"user":,
<br>"password":
<br>}

`azureStorage.json`

{
<br>"account_name":,
<br>"key":,
<br>"container":
<br>}

Finalmente, para la bajada automática, se debe configurar el cron, teniendo en cuenta que la ejecución se haga con el virtual environment:

ej: 

```bash
0 0 * * * /home/C4V/miniconda3/envs/C4V/bin/python /home/C4V/Code4Venezuela/getTweets/twDownload.py
```

Al ejecutarse se actualizará la tabla `tweets` que contiene:

* query: La búsqueda heca a Twitter
* ubicacion: Descripción del lugar desde donde se escribió el mensaje
* id: ID del tweet
* tweet_date: Momento de creación del tweet
* usernick: Usuario de Twitter
* userid: ID del usuario
* username: Nombre del usuario de Twitter
* tweet_text: Tweet
* hashtags: Hastags que contiene el mensaje
* is_retweet: Boolean que indica si el mensaje es retweet
* retweet_of: ID del mensaje retwiteado

También se dejará en el storage un archivo .json con la descarga de Twitter.


### API

```GET```: /querys
<br>Devuelve la lista de hashtags / keywords que estan siendo utilizadas para la ingesta de datos.
<br>Ej:
```
[{"query":"azitromicina"},{"query":"avelox"},{"query":"moxifloxacina"},{"query":"medicina"},{"query":"serviciopublico"},{"query":"metronidazol"}]
```

```GET```: /tweets/```query```
<br>Devuelve un objeto con las cantidades de twits por ciudad de la query elegida. Sin contar retweets.
<br>Ej:
```
[{"count":"25","ubicacion":"MARACAIBO","lat":"10.648715","lon":"-71.680717"},{"count":"138","ubicacion":"TÁCHIRA","lat":"7.978732","lon":"-71.979750"},
{"count":"2514","ubicacion":"CARACAS","lat":"10.469676","lon":"-66.880064"},
{"count":"73","ubicacion":"CIUDAD BOLÍVAR","lat":"8.089069","lon":"-63.531783"}]
```


```GET```: /tweets
<br>Devuelve todos los twits en la BD:
<br>Ej:
```
[{"query":"serviciopublico","ubicacion":"CARACAS","id":"1117451571422531584","tweet_date":"2019-04-14T15:36:30.000Z","usernick":"mariauxiran","userid":"1855700706","username":"Maria Auxiliadora","tweet_text":"RT @ElNacionalWeb: #ServicioPúblico Mauricio Lemus  sufrió un ACV hemorrágico y se encuentra hospitalizado en la Unidad de Cuidados Intensi…","hashtags":"ServicioPúblico","is_retweet":true,"retweet_of":"1117451230467502100"},{"query":"serviciopublico","ubicacion":"CARACAS","id":"1117451523917791232","tweet_date":"2019-04-14T15:36:19.000Z","usernick":"Tuozzololidagm1","userid":"847533128629575680","username":"Tuozzololida@gmail.c","tweet_text":"RT @RCR750: #ServicioPúblico URGENTE para niña de 5 años en terapia intensiva se necesita Gluconato de calcio al 10% en ampollas. Contacto:…","hashtags":"ServicioPúblico","is_retweet":true,"retweet_of":"1117408911789838300"},
{"query":"serviciopublico","ubicacion":"CARACAS","id":"1117451397891534848","tweet_date":"2019-04-14T15:35:49.000Z","usernick":"ramorodri","userid":"213440472","username":"Ramon Rodriguez","tweet_text":"RT @ElNacionalWeb: #ServicioPúblico Mauricio Lemus  sufrió un ACV hemorrágico y se encuentra hospitalizado en la Unidad de Cuidados Intensi…","hashtags":"ServicioPúblico","is_retweet":true,"retweet_of":"1117451230467502100"},{"query":"serviciopublico","ubicacion":"CARACAS","id":"1117451377029074944","tweet_date":"2019-04-14T15:35:44.000Z","usernick":"Isabel
```

```GET```: /ubicaciones
<br>Devuelve ubicaciones que estan siendo utilizadas para la ingesta de datos.
```
[{"ubicacion":"TÁCHIRA"},
{"ubicacion":"MARACAIBO"},
{"ubicacion":"CIUDAD BOLÍVAR"},
{"ubicacion":"CARACAS"}]```
```
```GET```: /map
<br>WEB para visualizar en mapa las cantidades de twits relevados por ciudad.
<br><br>[Live DEMO](http://40.117.115.124:8080/map)

## Android 

Se encontrara un [Apk de debug](https://github.com/lucpogo/Code4Venezuela/tree/master/TwitterHelpHackaton/apk-debug), la misma se conecta a un API donde obtiene los querys mas populares para realizar busquedas en twitter, una vez seleccionado uno, se redigira a un mapa donde se clasificaran los tweets por ciudades, indicando los sitios afectados y la cantidad de tweets sobre el tema. 

![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_1.png) 
![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_2.png) 
![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_3.png) 
  
  
## Autores

* **Edermar Dominguez** - [Ederdoski](https://gitlab.com/Ederdoski/about)
* **Lucas Pogorelsky** - [lucpogo](http://www.github.com/lucpogo)
* **Salvador Serruya** - [ttSpeaker](http://www.github.com/ttspeaker)

## Referencias


## Licencia

Este código es un software de código abierto bajo la licencia [MIT license.](https://opensource.org/licenses/MIT)


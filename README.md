# Code 4 Venezuela

## Introducción


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

## Android 

Se encontrara un [Apk de debug](), la misma se conecta a un API donde obtiene los querys mas populares para realizar busquedas en twitter, una vez seleccionado uno, se redigira a un mapa donde se clasificaran los tweets por ciudades, indicando los sitios afectados y la cantidad de tweets sobre el tema. 

![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_1.png) 
![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_2.png) 
![Example](https://github.com/lucpogo/Code4Venezuela/blob/master/TwitterHelpHackaton/images/example_3.png) 
  
  
## Autores

* **Edermar Dominguez** - [Ederdoski](https://gitlab.com/Ederdoski/about)
* **Lucas Pogorelsky** - [lucpogo](http://www.github.com/lucpogo)
* ** ** - []()

## Referencias


## Licencia

Este código es un software de código abierto bajo la licencia [MIT license.](https://opensource.org/licenses/MIT)


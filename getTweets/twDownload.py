import pandas as pd
import tweepy
import json
from tweepy import OAuthHandler
import os
from sqlalchemy import create_engine
import psycopg2 
from datetime import datetime
import numpy as np
import io
import time
from azure.storage.blob import BlockBlobService

print("Empezando @ {}".format(str(datetime.now())))

os.chdir(os.path.dirname(os.path.realpath(__file__)))

#os.chdir('getTweets')

#Params
with open("params/twitterKeys.json") as f:
    twitterKeys=json.load(f)

with open("params/database.json") as f:
    dbParams=json.load(f)

with open("params/azureStorage.json") as f:
    storageParams=json.load(f)

#Twitter Connection
auth = OAuthHandler(twitterKeys['consumer_key'], twitterKeys['consumer_secret'])
auth.set_access_token(twitterKeys['access_token'], twitterKeys['access_token_secret'])
api = tweepy.API(auth)

#DB Connection
db_string = "postgres+psycopg2://" + dbParams['user'] + ":" + dbParams['password'] + "@" + dbParams['server']  + ":" + dbParams['port'] + "/postgres"
engine = create_engine(db_string)

#Locations
location = pd.read_sql('select * from ubicaciones',engine)
#Queries
queries = pd.read_sql('select * from busquedas',engine)
#Existing
oldId = pd.read_sql('select query,ubicacion, max(id) as id from tweets group by query,ubicacion',engine)

data = {}

for _, qRow in queries.iterrows():
    data[qRow.busqueda]={}
    for _, locRow in location.iterrows():
        data[qRow.busqueda][locRow.ubicacion]=[]
        
        since_id = oldId[(oldId['query']==qRow.busqueda) & (oldId['ubicacion']==locRow.ubicacion)].id
        time.sleep(5)
        print("Importando Tweets de {} en {} @ {}".format(qRow.busqueda,locRow.ubicacion,str(datetime.now())))
        if since_id.size>0:
            buffer = api.search(q=qRow.busqueda,
                                geocode='{},{},{}'.format(locRow.lat,locRow.lon,locRow.rad),
                                count=300,
                                tweet_mode='extended',
                                result_type='recent',
                                since_id=int(since_id)+1) 
        else:
            buffer = api.search(q=qRow.busqueda,
                                geocode='{},{},{}'.format(locRow.lat,locRow.lon,locRow.rad),
                                count=300,
                                tweet_mode='extended',
                                result_type='recent') 

        while len(buffer)>0:
            data[qRow.busqueda][locRow.ubicacion].extend([x._json for x in buffer])
            time.sleep(5)
            if since_id.size>0:
                buffer = api.search(q=qRow.busqueda,
                                    geocode='{},{},{}'.format(locRow.lat,locRow.lon,locRow.rad),
                                    count=300,
                                    tweet_mode='extended',
                                    result_type='recent',
                                    since_id=int(since_id)+1,
                                    max_id=min([int(x.id) for x in buffer])-1) 
            else:
                buffer = api.search(q=qRow.busqueda,
                                    geocode='{},{},{}'.format(locRow.lat,locRow.lon,locRow.rad),
                                    count=300,
                                    tweet_mode='extended',
                                    result_type='recent',
                                    max_id=min([int(x.id) for x in buffer])-1) 

def parseTweet(t,ubicacion,query):
    dicc = {}
    dicc['query']=query
    dicc['id']=t['id']
    dicc['tweet_date']=datetime.strptime(t['created_at'][4:20]+t['created_at'][-4:],'%b %d %H:%M:%S %Y')
    dicc['tweet_text']=t['full_text']
    try:
        dicc['hashtags']=','.join([x['text'] for x in t['entities']['hashtags']])
    except:
        dicc['hashtags']='\'\''
    dicc['usernick']=t['user']['screen_name']
    dicc['username']=t['user']['name']
    dicc['userid']=t['user']['id']
    if 'retweeted_status' in t.keys():
        dicc['is_retweet']=True
        dicc['retweet_of']=t['retweeted_status']['id']
    else:
        dicc['is_retweet']=False
        dicc['retweet_of']=np.nan
    dicc['ubicacion']=ubicacion
    return dicc

df = []

print("Procesando Tweets @ {}".format(str(datetime.now())))

for q , ubicaciones in data.items():
    for u, tweets in ubicaciones.items():
        for tweet in tweets:
            df.append(parseTweet(tweet,u,q))

            

df=pd.DataFrame(df).reindex(columns=['query','ubicacion','id','tweet_date','usernick','userid','username','tweet_text','hashtags','is_retweet','retweet_of']).replace(regex=True,to_replace=r'\|',value=r'')

print("Enviando a la Base de Datos @ {}".format(str(datetime.now())))

df.to_sql('tweets',con=engine,if_exists='append',index=False)

blob_service = BlockBlobService(storageParams['account_name'], storageParams['key'])

fileName = str(datetime.now()) + '.json'

with open(fileName,'wt') as f:
    json.dump(data,f)

print("Enviando a Storage @ {}".format(str(datetime.now())))

blob_service.create_blob_from_path(
    storageParams['container'],
    fileName,
    fileName
)

os.remove(fileName)

print("Fin @ {}".format(str(datetime.now())))

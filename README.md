# <h1>Photo Search App Coding Challenge 
This a vanilla android application that does a simple task : 
  * Allow user to search Flickr photos by entring a search text.
  * Enable endless scrolling through photos as long there is more photos available.
  
constrains  : 
* No 3rd party libraries
* Time : around 10 hours were invested  

## Key Highlights that this project tries to domenstrate : 
* MVP 
* Preserving data in case of system killing application 
* Preserving state and running tasks across configuration changes 
* Dependancy inversion and injection 
* Testability 
* Hanlding Image Loading challenges with recycling and lists views : 
  * Throttling Download requests 
  * Prevent jumping photos cause by download callbacks completing for recycled views 
* Lru cachining 

## Bottom up description of application 
 
* ### NetworkAsyncTask<T> : 
  A typed wrapper around async task 
  recives a typed callback and execution function to execute in the background. 
  it will use the execution function and run it in the background wrapped in a try and catch 
  in case of success it will pass the result of execution function to the callback success , in case of expception it will pass the exception to the call back error.
  
 * ### JsonProcessor<T>
 A typed functional interface that receives A string and returns T object 
 subclass : PhotosListResponseProcessor to process photo list web response 
* ### AsyncBitmapDownloader

A functional Interface that receives a url of image to downlaod and a callback for success and error  
subclass :  ```AsyncBitmapDownloaderImp``` uses ```NetworkAsyncTask``` to download the given url image by passing a function that opens a connection with the  url and convert the reads the input stream into bitmap
* ### AsyncHttpClient
an interface contains a single typed function that receives a url , query parameters as map , and typed calback , and finally a jsonprocessor to convert the result into the needed type 
subclass : AsyncHttpClientImp that implements the interface and builds the final url from the given link and query parameters and uses ```NetworkAsyncTask``` to execute a request in the background by passing a funcion ```readFromApiLink``` that makes a web request and translates the result using the passed JsonProcessor

   

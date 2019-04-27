# <h1>Photo Search App Challenge 
This is a vanilla android application that does a simple task : 
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

## Bottom up breakdown of application 
 
* ### NetworkAsyncTask<T> : 
  A typed wrapper around async task 
  recives a typed callback and execution function to execute in the background. 
  it will use the execution function and run it in the background wrapped in a try and catch 
  in case of success it will pass the result of execution function to the callback success , in case of expception it will pass the exception to the call back error.
 If the callback was cancelled then non of the callback methods will be called. 


  
 * ### JsonProcessor<T>
 A typed functional interface that receives A string and returns T object 
 subclass : PhotosListResponseProcessor to process photo list web response 
* ### AsyncBitmapDownloader

A functional Interface that receives a url of image to downlaod and a callback for success and error  
subclass :  ```AsyncBitmapDownloaderImp``` uses ```NetworkAsyncTask``` to download the given url image by passing a function that opens a connection with the  url and convert the reads the input stream into bitmap
* ### AsyncHttpClient
an interface contains a single typed method that receives a url , query parameters as map , and typed calback , and finally a jsonprocessor to convert the result into the needed type 
subclass : AsyncHttpClientImp that implements the interface and builds the final url from the given link and query parameters and uses ```NetworkAsyncTask``` to execute a request in the background by passing a funcion ```readFromApiLink``` that makes a web request and translates the result using the passed JsonProcessor

* ### PhotosRepoClient : 
an interface of repo client that has a single method ```getPhotos``` with parameters : request page number , query and a call back 
a subclass ```RemotePhotosRepoClient``` implements the interface to fetch the photos .
Its cnstructor recives the apikey , an httpclient , and the response processor , and finally the the base url. 
in the implementation of method ```getPhotos``` it will create parameters map from a fixed map of param along with the passed page and query and finally use the passed httpClient ,url , process and callback to get the result. 

* ### PhotosRepo : 
 an interface for photos repository for our case contains a singe method to get photos and receives page , query and callback 
a subclass ```PhotosRepoImp``` receives in its constructor a remote client it will use to fetch the results from web 
While it might seem redundant at this point it is useful for later if we decide to add a local client for caching requests. 
* ### Cache : 
a typed interface for cache with two get and put methods 
a subclass ```InMemoryLRUCache```  is backed by a modified LinkedHashMap ```SizedLinkedHashMap``` to implement LRU cache behaviour 
and its used to cache Bitmaps: to add it wraps the bitmap in  SoftReference to add into the SizedLinkedHashMap to allow them to be cleared in cases of memory needed. 
the get method will check if the bitmap is present in the map , then will check if it hasn't been cleared , if it hasn't it will return it and if it has been cleared it will remove it from the map to free its spot for a new bitmap. 
 
* ### ImageLoader : 
an interface for an image loader with a single method ```displayImage``` display an image from a url with a place holder till its donwloaded 
A subclass is ImageLoaderImpl that implements the interface but does more than just downloading and displaying the images. 
* #### Handling recycled image views while requests is still not complete 
 It makes sure that if it receives the same imageView for a request that hasn't been finished it doesn't display the old one (even  though it will still let the download complete and let the image be cached ) 
This is useful in list views to avoid jumping images when scrolling. 
This is done by using a two way between the image view and the url to when a download request completes it checks which image view  corrosponds to the url and displays it . if the image view has been recycled then then it will be cleared from the mapping and updated  with the new url mapping so when the old request complets it won't find a corrosponding image view and hence wont update a imageview  that has changed position. 
 In case of configuration change  or fast two ways scrolling which could cause the same url requested for differnt image views the double mapping will prevent the leak of image views because it removes the image views corrosponding to a url before maping it to a new one. 
 * #### Handling fast scrolling causing many download requests 
  The image loader keeps track of running download requests and make sure they dont excceed the specified threshold of allowed concurent downloads 
 When download requests come in while max allowed downlaods is reached it will keep the requests in stack like behaviour discarding old requests this will allow serving the lates displayed images without worrying about images that were just passed by during scrolling. 

* ### SearchPhotosContract : 
An interface that defines the contract for the searchPhoto View and presenter and delegates 
it defines : 
 * The View : which has some basic actions to display to the users like showing loading or loading more , showing list of photos , or clearing photos and reciving delegates  and other display features 
 * The delegates : which defines how the view will communicate the actions to the presenter as the view will not communicate with the presenter directly 
 * The Presenter : the interface basically defines some methods that the containing activity will use to communicate with the presenter the view it will use and to handle life cycle and communication changes. like restoreUi , setView , onSaveInstanceState ,onRestoreState
* ### PhotoSearchView a gruplayout that implements SearchPhotosContract.View that  contains the search components and recycler view to display results and will communicate user action through the delegates passed to it.  

* ### PhotoSearchPresenter
implements SearchPhotosContract.Presenter and it receives a PhotoRepo in its constructor that it will use to excute requests to fetch photos the user searches for. 
It will keep track of the search query the user used and also the last page that has been requested for the current query to allow load more features , it will keep track of running requests not to repeat call more requests if one is running. 

It also can receive a bundle where it can save its state to and also can extract its state from a bundle to restore its state. 

* ### MainActivity
wraps the presenter and the view it also passes life cycle actions to the presenter to help it save and restore its state. 
it also maintains the presenter instance through configuration change 

* ### DependenciesManager
 a single instance manager that handles creating depandcies and allowing constructor dependancy injection 

## Testing 
There is a few implemented test to insure parsing is done correctly as well as parecelling objects and behaviour of image loader throtting and caching 




   

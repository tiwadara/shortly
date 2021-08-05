# Shortly App

<img src="/readme/a.png" width="200">

<img src="/readme/b.png" width="200">


### Query DB vs Query list loaded in the view
    As we discussed , this decision is largely based on data size, and the performance could drop with increased record size.
    
    DB Query - pros
    1. Single source of truth, many times the list in the UI could mutate for many reasons and making inexpensive search from persisted data 
    has always been a right choice for me 
    2. Considering that the application was reactive, I didn't have to lock the UI , and the search result was emitted when it was found 
    Room works well with reactive programming 
    
    DB Query - con
    1. Search could get slower with larger records
    
    List Filter - pro
    1. This has the advantage of being faster
    
    List Filter - con
    1. When the records become larger the data presented on the UI gets paginated , so search through the list on the UI won't give access to 
    other records. 
    
    Conclusion : In this shortly app, where all the data is loaded into the UI , a simple filter on the list would have worked just fine. However, 
    since I had built a robust architecture that had data caching designed , I had to make use of it . 

    So , every request , goes through the getCachedShortLink(url) function , and getNewShortLink(url) is called when the exact url isn't in the records

```kotlin
    override suspend fun getCachedShortLink(url: String): Flow<ShortLinkState<Any>> = channelFlow {
        send(ShortLinkState.Loading)
        try {
            CoroutineScope(defaultDispatcher).launch {
                val response = shortLinkDao.getShortLinkByUrl(url)
                response?.let {
                    send(ShortLinkState.ShortLinkExists)
                } ?: run {
                    getNewShortLink(url)
                }
            }
        } catch (e: Exception) {
            send(ShortLinkState.Failed<String>(e.message))
        }
        awaitClose()
    } 
```

    ##  🚀 Room API - For data persistence
    I prefer to setup a database in projects beacause,
    1. They will get larger and you'd need to able to make quick CRUD actions
    2. I chose room cause of it abstractions, and I could make reactive connections to it

    ## 🚀  Flow API - For reactive programming
    I chose Flow because it helps me make reactive calls without having to be on the UI thread
    or hold a reference to it. I however converted to LiveData on the UI beacause it's lifecycle aware

    ## 🚀  Multi-Module
    I have made it a habit to start project with a modular architecture, makes it easier to collaborate,
    extend and could shorten test time when the project gets larger

    ## 🚀  MVI ( Having UI states and Events )
   The model and the View are a given , the interesting parts are the Intents ( States and Events)
    It makes it easy for me to manage the UI state, as there will all ways be a state for the UI. It
    could be a loading state, a failed state, or any other state I define. This makes it easy to track errors,
    replay states,
    This way , all the UI does it respond to states, that come from just one single class and no other.
    Makes it easier to test , as I know how many states I expect a successful request to trigger.
    Give great control over the UI. It's quite similar to the BLOc pattern popular in Flutter


### Target platform version and setup
-  Code was written on a Macbook
-  minSdkVersion - 16, targetSdkVersion - 30
-  Gradle -  gradle-6.5-all
-  IDE - Android Studio 4.1.3 & Android Studio Artic Fox 2020.3.1 Canary 8 ( to run the unit tests )

### Summary
    Task was clear and resource were beautifully provided and documented. I'll highlight a few of the
    APIs I used and their reasons. We can have more conversations as you'd like.

    ##  🚀 Room API - For data persistence
    I prefer to setup a database in projects beacause,
    1. They will get larger and you'd need to able to make quick CRUD actions
    2. I chose room cause of it abstractions, and I could make reactive connections to it

    ## 🚀  Flow API - For reactive programming
    I chose Flow because it helps me make reactive calls without having to be on the UI thread
    or hold a reference to it. I however converted to LiveData on the UI beacause it's lifecycle aware

    ## 🚀  Multi-Module
    I have made it a habit to start project with a modular architecture, makes it easier to collaborate,
    extend and could shorten test time when the project gets larger

    ## 🚀  MVI ( Having UI states and Events )
   The model and the View are a given , the interesting parts are the Intents ( States and Events)
    It makes it easy for me to manage the UI state, as there will all ways be a state for the UI. It
    could be a loading state, a failed state, or any other state I define. This makes it easy to track errors,
    replay states,
    This way , all the UI does it respond to states, that come from just one single class and no other.
    Makes it easier to test , as I know how many states I expect a successful request to trigger.
    Give great control over the UI. It's quite similar to the BLOc pattern popular in Flutter

Tools used :
###### Unit tests ✓
###### MVI Architecture  ✓
###### Clear and clean code  ✓
###### Offline cache using Room ✓
###### multi-module  ( :app & :common ) ✓
###### Kotlin  ✓
###### Dependency Injection using Hilt ✓
###### Flow ✓

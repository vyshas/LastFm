# TestLastFM [   WORK IN PROGRESS  ]

- To run the gradle.properties should contain API key in the format shown below:

LastFm_ApiKey = "YOUR API KEY"


- Code is written in Kotlin/Java.
- Uses Dagger for DI injection.
- Retrofit and GSON library used for network requests.
- Stetho used for debugging.
- Android arch components using ROOM ORM.
- Follows MVVM pattern


## SearcAlbum:
- allows user to search albums and displays album,artist details. UI controller is SearchAlbumFragment.
- SeachAlbumAdapter uses Google's new ListAdapter(that internally uses RecyclerView and diffUtil). If albumId is different then items are not same.
- Uses SearchRepository to load data from db and network
- SearchViewmodel loads search item from repository and posts data to UI controller.
- Clicking list item opens Album details.

##  AlbumDetails:
- UI controller is albumDetailsfragment.
- loads album image using glide.
- shows album tracks, artists and album description.


## Tests:

Unit Test:
- SeachRepositoryTest contains tests for searchrepository.
- ApiResponseTest tests response success and failure.
- LastFmService test mocks api responses and tests the data model.
- Powermock, mockito, junit ,etc are used for tests.




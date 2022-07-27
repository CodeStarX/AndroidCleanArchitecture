# ANDROID CLEAN ARCHITECTURE

Android Movie App for displaying all popular movies, searching any movie, find information about it using [TMDb](https://www.themoviedb.org/) (The Movie Database) API

Note: Use your API key as mine is hidden. After you get it, put it in [RemoteApi Class](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/core/data/src/main/java/mohsen/soltanian/cleanarchitecture/core/data/services/RemoteApi.kt)

`API_KEY="XXXXXXXXXXXXXXXXXXXXXXXX"`

## v1.1 - 2022-07-26
 - use the annotation [@ActivityAttribute(R.layout.xxx, ...)](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/libraries/framework/src/main/java/mohsen/soltanian/cleanarchitecture/libraries/framework/core/base/annotation/ActivityAttribute.kt) on the class of your Activity.
 - use the annotation [@FragmentAttribute(R.layout.xxx, ...)](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/libraries/framework/src/main/java/mohsen/soltanian/cleanarchitecture/libraries/framework/core/base/annotation/FragmentAttribute.kt) on the class of your Fragment.[Example](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/app/src/main/java/mohsen/soltanian/cleanarchitecture/ui/fragments/main/MainFragment.kt)
 - use then annotation [@Layout(R.layout.xxx)](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/libraries/framework/src/main/java/mohsen/soltanian/cleanarchitecture/libraries/framework/core/base/annotation/Layout.kt) on the class of your RecyclerAdapter.[Example](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/app/src/main/java/mohsen/soltanian/cleanarchitecture/ui/fragments/main/adapter/MoviesAdapter.kt)
 - use the annotation [@ModelTesting(...)](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/core/data/src/test/java/mohsen/soltanian/cleanarchitecture/core/data/core/ModelTesting.kt) on the class of your Model test class.[Example](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/core/data/src/test/java/mohsen/soltanian/cleanarchitecture/core/data/models/response/CastModelTest.kt)
 - use the annotation [@ServiceUnitTesting(...)](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/core/data/src/test/java/mohsen/soltanian/cleanarchitecture/core/data/core/ServiceUnitTesting.kt) on the class of your Service test class.[Example](https://github.com/mohsenSoltanian/AndroidCleanArchitecture/blob/master/core/data/src/test/java/mohsen/soltanian/cleanarchitecture/core/data/services/RemoteApiTest.kt)

## Features

- Display all upcoming movie trailers.
- Get a list of popular movies of the current time.
- Search any movie & get its information: Title, Rating, Release Date.

## Libraries included in this project:
- [mockk for testing ](https://mockk.io/)
- [mockWebserver](https://github.com/square/okhttp/tree/master/mockwebserver)
- Paging
- Glide
- Retrofit
- Data Binding (Android Architecture Components)
- Live Data (Android Architecture Components)
- ViewModel (Android Architecture Components)
- RecyclerView
- CardView
- DI with Hilt
- coroutine flow
- Moshi
- Timber
- Chucker

## Screenshots
| <img src="https://i.imgur.com/ddo18DS.jpg" width="250">  | <img src="https://i.imgur.com/aDCqlVW.jpg" width="250"> | <img src="https://i.imgur.com/kzSyLRq.jpg" width="250">




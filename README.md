# Video Game Repository

## Architecture
This project implements Clean Architecture to facilitate testing, maintainability and development,
giving every layer their own role. Also applied MVVM as an architecture pattern, with this we can
easily manage the interaction with the android components.

## Libraries
Implementing the libraries was the first thought process that was faced for the project, thinking in
what can make the life easier around the coding stage. So with this idea in my mind, implementing
Retrofit to connect with the endpoint, Room the store data in local database, using DI with Hilt,
libraries as Coil and Android Jetpack was such an interesting opportunity not only to use the latest
compatible versions, but also to be able to navigate through knowledgeable waters.

## Algorithms
For the filtering and reordering, we use the Database filtering, making it more easy to handle and
optimize because the processing in SQL is lighter and faster.
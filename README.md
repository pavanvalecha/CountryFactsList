# CountryFactsList

App shows the list of Facts About Country, data is fetched from the link 
"https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"

About App Functionality
1. Display List of Facts from API
2. Have SwipeRefreshLayout for updating the data from backend
3. App caches the data in Room DB and refreshes after 2 mins (Test Cache Expiry time saved in SharedPreferences)
4. Lazily loads thumbnail images with circular progressbar and default image in case of error


About App implementaion 
1. Designed with MVVM architecture with Koin for dependency Injection 
2. Saves data from API to Room tables
3. Uses Android DataBinding for Binding Views with data
4. AndroidX Navigation library
5. Uses RXAndroid and RXJava for Observables and LiveData


App Uses below Libraries
1. Koin for Dependency Injecttion
2. Retrofit
3. GSON
4. Room Persistance library for caching API data 
5. Glide For Image loading
6. Timber for logging

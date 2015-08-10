# tutti-frutti
BBC android developer code challenge

<a href="https://play.google.com/store/apps/details?id=com.giljulio.tuttifrutti">
<img alt="Get it on Google Play" src="http://steverichey.github.io/google-play-badge-svg/img/en_get.svg" />
</a>

### Challenge 
> You are tasked with writing a mobile application (native iOS or Android) consisting of two screens for displaying data about fruit.
> 
> The first screen will display a list of fruit. When the user selects an item of fruit the application should show a second screen containing more information about the fruit, specifically price in pounds and pence and weight in kg.
> 
> The application will be required to download a data file (JSON) of fruit hosted on a web server. 
> 
> Units are grams and pence. 
> 
> The URL is:
> 
> https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json
> 
> The user should be able to invoke a reloading of the data from the server. 
> 
> There is an additional requirement to generate usage stats, this is accomplished by issuing a GET request to:
> 
> https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/stats
> 
> Two parameters should be appended to the URL (event and data) as follows:
> 
> event=load – any network request, associated data is the time in ms for the complete request
> 
> event=display – when ever a screen is shown, associated data should be the amount of time taken (in ms) from when the user initiated a request that would show the screen to the point where the screen has finished drawing.
> 
> event=error – sent when ever there is a raised exception or application crash, associated data would be something useful for a developer to use in response to “live issues”
> 
> For Example:
> 
> https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/stats?event=load&data=100
> 
> https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/stats?event=display&data=3000
> 
> https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/stats?event=error&data=null%20pointer%20at%20line%205
> 
> This is an opportunity to demonstrate your understanding of what good object orientated code looks like and how this can be discovered using tests. 
> 
> You will be required to submit zipped source code and optionally a deployable application artifact.

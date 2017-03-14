# BitlyApiApp
Application using Bitly API that allows user to create bitlinks and view bitlinks created.

------Activities------

Acitvity Main:
Default activity that simply contains two buttons that pass info to the two other activities. Simple


Link_History_Activity:
Displays all of my generated bitlinks along side the amount of clicks each link has had.

Uses two of the three main classes Link_History and Clicks.

Currently uses a button to display the info instead of dynamically displaying at the start.

To fix this need to pass that info in from Activity_Main.


New_Bitlink_Activity
Contains a search bar for the user to input the link they would like to see turned into a bitlink.

Displays a message on whether the link was created or not. Also really simple.


-----Other Activity Ideas----------

Search Activity:

Would work similarily to that of the New_Bitlink activity cointaining a search bar.

Would use "/v3/user/link_lookup" to search for a already generated bitlink.


------Classes------

Link_History:

Connects the application to "/v3/user/link_history" using my OauthToken.

Creates two ArrayLists, one of a set of bitlinks, and one of a set of titles.

These are dynamically filled by parsing the JSON from the response.

These then can be accessed via getTitles and getBitlinks.


New_Bitlink:

Connects the application to "/v3/user/link_save" using my oauth token and the link.

The URL that is passed into the class is then checked to make sure it is the proper format.

The application then reads in the response header to see if the link already existed or if it was created *bugged*


Clicks

Connects the application to "/v3/link/clicks" using oauth token and a link.

The response is then read and we grab the amount of clicks from the JSONObject "link_clicks"

The amount of clicks can be accessed via getClicks.


----Issues/Bugs Detected-------

Issue: Link_History_Activity needs to be changed to ScrollView, currently just a constraintlayout.

Issue: Link_History_Activity should display the link history at hte start of the activity.

Bug: New_Bitlink class does not recieve a response as of now. No idea if on applications end or bitly's

Issue: Your UI sucks.

Bug/Issue: Generating clicks within Link_History_Activity.setOutput() requires us to place the main thread
           to sleep. Instead swhould generate the clicks when finding the link history. Might seem slower, but will
           be more consistent.
           
Issue:Currently App only works with my OauthToken. Need  to enable user sign in so the app is less "Lewis"-centric.

# To Do List App

### Authors: Adarsh Suresh and Akshit Deshpande

For the final project of CS 199: Intro to Kotlin, we decided to make a todo list app with Firebase and Google Sign-In integration in order to simultaneously test our skills in Kotlin programming, Android development, and API usage. The first half of development consisted of setting up our APIs, with the end result being a working sign-in activity and secure Firebase connection. After that, we worked through the rest of the app while figuring out how to synchronize our Firestore database with our in-app data.

We structured our Firestore database with each Google user's unique Firebase ID as a document ID within the 'Users' collection. Each document contains fields of Strings mapped to another map, or another set of fields. These lower-level fields are: 'title', 'description', 'location', 'time', and 'isCompleted'. All of these field values are Strings except for 'time' which is a Firebase Timestamp and 'isCompleted' which is a boolean. We edited our database rules so that a user can only access and modify the data of a document if that document's ID is his or her Firebase ID. A user who hasn't used our app before can also create a new document for themselves automatically in our database. The code to read, write, and create documents and the inner data was fairly simple and intuitive, as the Firebase Kotlin documentation was easy to follow. We created a ToDoItem class to model todo items in our database and created a constructor that was able to create a ToDoItem object from the received Firestore data, as well as a function that could convert an object's data into a HashMap to be directly added to the Firestore database.

In the end, we found the development process to be challenging but enjoyable. During lectures we saw how Kotlin simplified many of the tedious and unnecessary features of Java, but it was only through the creation of this app where we truly saw the advantages that Kotlin provided. In the future, we hope to do much more Kotlin-based development.

---

**Update (4/24/20)**
- Set up Firebase Console and created a Cloud Firestore database with an example entry of a todo item
- Set up Google API Console and enabled sign in access to all Google accounts
- Created a working Google sign in activity and UI which allows users to sign in and sign out
- Added logic to the main activity to launch the sign in activity
- Added logic to the main activity to retrieve user info from the database and store it into global variables
- Updated security rules of Cloud Firestore to restrict user access to data associated with their user id
- Added logic to the Google sign in activity to retrieve user-specific info from the database and store it into global variables

**Update (5/1/20)**
- Finished Google sign in activity and UI
  - Dark mode
  - Changed the disconnect button to a continue button to transition to the main activity
- Finished main activity and UI
  - Dark mode
  - Added container to store dynamic UI elements for each todo item
  - Added refresh button to clear completed todo items
  - Added text fields and add button to add new todo items
  - Each addition and removal updates the Firestore database
  - If a user does not have an associated Firestore document, a new one is automatically generated
  

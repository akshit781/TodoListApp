# To Do List App

### Authors: Adarsh Suresh and Akshit Deshpande

This is a Kotlin-based project that emulates a regular To-Do List app. Each item will have a header and options to add a description, time and location. We use Firebase to store the To-Do list items and their details, so that users can login to the app on any device and view their To-Do list.

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
  

# RoomMateApp
Software Engineering Project 


ID and name	UC-1: User Authentication
Primary actor	User	Secondary actors	None
Description	The user logs into the system with a username and a password
Trigger	User wants to access the system
Preconditions	Must have an account
Postconditions	The user enters the account 
Normal flow	1.	User uses a valid username and password to enter the system
2.	The system validates input
3.	User is logged in if valid
4.	User enters system where he has his “Rooms” that he can access
Alternative flows	1.	1. User enters invalid credentials
1.	2. User doesn’t have an account-> the system allows him to create one
Exceptions	1.	E. If the password the user chooses does not contain certain characters the system needs to validate it, it throws an exception and asks for another password
RoomMate APP


ID and name	UC-2:  “Room” entry
Primary actor	User	Secondary actors	Room Admin
Description	The user can choose to enter a room he already has or request to join one
Trigger	User wants to join a room
Preconditions	Must be logged in
Postconditions	User enters a room
Normal flow	1.	User enters code for the room
2.	He waits for admin approval
3.	He is given admin approval and enters the room
4.	The room is now accessible in his account to access whenever
Alternative flows	1.	1. User enters a room he already has by clicking on the room icon
Exceptions	2.	E. The user’s request to join is rejected-> he doesn’t enter the room

ID and name	UC-3:  Room People Management
Primary actor	Room Admin	Secondary actors	User
Description	The room admin can decide who takes part in the room activities and can modify the aspect of the virtual room
Trigger	User wants to join the room
Preconditions	The admin must be logged in to receive the notification
Postconditions	Admin has a new roommate in a certain room or not
Normal flow	1.	Admin receives notification that someone wants to join
2.	He clicks on the requests and approves
Alternative flows	2.  Room admin decides to give the new mate admin status or not
Exceptions	2.	E. Admin rejects request 


ID and name	UC-4:  Expenses Management
Primary actor	Admin	Secondary actors	User
Description	Option for adding a bill and splitting it evenly for all the people in the room 
Trigger	Admin adds a bill 
Preconditions	Must be logged in as admin
Postconditions	Everybody has their share of the bill in an “expenses management” table that they can erase when they’ve paid it
Normal flow	1.	Admin adds the name of the bill/expense and the amount .
2.	By clicking the “add expense” button ,the bill is distributed evenly to all the roommates
3.	The bill is also added to a table all the roommates can access
Alternative flows	
Exceptions	

ID and name	UC-5: Chores and Cleaning
Primary actor	Users	Secondary actors	
Description	Organizes household tasks and shared responsibilities.
Trigger	Roommate adds a task 
Preconditions	Must be part of a room
Postconditions	The task is added to a shared list of chores 
Normal flow	1.	User adds task 
2.	The task is put in a shared list that everybody can access 
3.	Any of the roommates can complete the task 
4.	After completing the task, the others are notified about who completed it
Alternative flows	1.	1. Somebody deletes a task 
Exceptions	1.1.	E. From a task deletion -> the others are notified 

ID and name	UC-6:  RoomMate Chat
Primary actor	Users	Secondary actors	
Description	Private or shared chat with the roommates 
Trigger	User enters the chat
Preconditions	Must be part of a room
Postconditions	Message sent 
Normal flow	1.	User enters a chat
2.	He sends messages to another user or users from the room
Alternative flows	2.  Deletes a message 
Exceptions	


ID and name	UC-7:  Guest Management
Primary actor	Users	Secondary actors	
Description	Roommates can notify the others if they have somebody come over
Trigger	User adds the notification about having somebody over
Preconditions	Must be part of room
Postconditions	The roommates are notified
Normal flow	1.	User adds notification 
2.	He introduces 
3.	The others are notified
Alternative flows	
Exceptions	


ID and name	UC-8:  Gamification
Primary actor	Users	Secondary actors	
Description	Points for active roommates
Trigger	Does a chore 
Preconditions	Must be in a room
Postconditions	Points added 
Normal flow	1.	User checks the chores 
2.	User does a chore
3.	He is rewarded with points 
Alternative flows	
Exceptions	


ID and name	UC-9:  Emergency Alerts and Safety Features
Primary actor	Users	Secondary actors	
Description	Alerts sent to inform the roommates about things that can happen(fire,break in) 
Trigger	User sends alerts to the others
Preconditions	Must be in a room
Postconditions	Alerts sent to everybody from the group
Normal flow	1.	User accesses alert section
2.	Writes the details 
3.	Sends alrts 
Alternative flows	
Exceptions	


ID and name	UC-10: Temporary Subletting Option
Primary actor	Users	Secondary actors	
Description	Allows users to find a temporary roommate if they are traveling.
Trigger	User finds a temporary roomate
Preconditions	Must be authentificated
Postconditions	User is added to your room
Normal flow	1.	User finds another user who looks for a roommate for a period of time
2.	Adds user to room 
Alternative flows	
Exceptions	



	Iterations:
Iteration 1: Core Features Implementation (Authentication and Room Management)
This iteration focuses on building the fundamental system infrastructure and essential functionalities for users.
Included Use Cases:
•	UC-1: User Authentication (Login, Registration)
•	UC-2: “Room” entry 
•	UC-3:  Room People Management

________________________________________

Iteration 2: Shopping and Order Management Features
This phase introduces essential functionalities for buyers and the order placement process.
 Included Use Cases:
•	UC-4: Expenses Management 
•	UC-5: Chores and Cleaning
•	UC-6: RoomMate Chat
________________________________________
Iteration 3: Optimization, Administration, and Reporting
This stage finalizes the system with administrative features, optimizations, and reporting capabilities.
 Included Use Cases:
•	UC-7: Guest Management
•	UC-8: Gamification 
•	UC-9: Emergency Alerts and Safety Features
•	UC-10: Temporary Subletting Option


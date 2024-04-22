**HATFIELD JUNIOR SWIMMING SCHOOL PROJECT REPORT**

1. **INTRODUCTION**

The Hatfield Junior Swimming School (HJSS) software serves as a solution for managing the bookings of swimming lessons made by the learners at Hatfield Junior Swimming School designed following specific requirements and functionalities.

This report offers a brief overview of the design specifications and implementation strategies employed in developing the HJSS software. By identifying the key design elements, creational, structural, and behavioral patterns, and testing methodology utilized throughout the development process, this report seeks to provide an understanding of the HJSS system's architecture and functionality. 

**2.0 DESIGN SPECIFICATIONS**

The design specifications for the Hatfield Junior Swimming School (HJSS) software outline the requirements and functionality expected from the system. These specifications serve as the foundation for the design and implementation of the software. Key components of the design specifications include:

**Swimming Lessons:** The system accommodates lessons for five grade levels (Grade 1 to Grade 5), each lasting one hour and capable of hosting a maximum of four learners.

**Lesson Schedule:** Lessons are scheduled on weekdays (Monday, Wednesday, Friday) from 4-7 pm and on Saturday from 2-4 pm.

**Learner Information:** Learner records include name, gender, age (between 4 and 11), emergency contact phone number, and current grade level.

**Coaches:** Multiple coaches are available, capable of teaching all grade levels.

**Timetable Viewing:** Learners can view the timetable by specifying the day, grade level, or coach's name.

**Booking Process:** Learners select a lesson from the displayed timetable and can book multiple lessons, ensuring no duplicate bookings. Grade 1 learners can book Grade 1 or Grade 2 lessons.

**Grade Level Update:** Learners' grade levels are updated after attending higher grade level lessons.

**Booking Modifications:** Learners can change or cancel bookings before the lesson's attendance.

**Lesson Review:** After each lesson, learners provide a numerical rating (1 to 5) and a review, recorded in the system.

**Reporting:** After four weeks, the system generates reports detailing each learner's booked, canceled, and attended lessons, along with a report on each coach's average ratings.

**3.0 DESIGN AND IMPLEMENTATION**

The HJSS software is developed using the JAVA programming language through the implementation of object-oriented programming and strict adherence to SOLID principles. During the development of HJSS software, several design patterns from the gang of four were utilized. Before discussing these patterns, we will highlight the assumptions made during the development process.

**3.1 ASSUMPTIONS**

In addition to the provided design specifications, several assumptions were made during the design and implementation process:

1. ` `**Lesson Duration:** The assumption is made that all swimming lessons have a fixed duration of one hour.
1. **Maximum Learners per Lesson:** It is assumed that each swimming lesson can accommodate a maximum of four learners at a time.
1. **Lesson Availability:** The system assumes that swimming lessons are available only on specific days and times, as outlined in the specifications.
1. **Learner Age:** The system assumes that learners' ages are between 4 and 11 years old, as per the age range specified.
1. **Grade Level Booking Restrictions:** Grade 1 learners can only book Grade 1 or Grade 2 lessons, assuming a progressive learning structure within the grades.
1. **Booking Modifications:** Learners are allowed to change or cancel bookings before attending the lesson, assuming no penalty or restriction for such modifications.
1. **No Duplicate Bookings:** It is assumed that the system prevents learners from making duplicate bookings for the same lesson.
1. **Lesson Review and Rating:** The system assumes that learners are required to provide a review and numerical rating after attending each lesson.
1. **Reporting Frequency:** The system generates reports every four weeks, assuming a regular reporting schedule for learner and coach performance evaluation.
1. **Pre-Registration**: The system is assumed to pre-register at least 15 learners and 5 coaches.

These assumptions provide clarity and guide the design and implementation of the HJSS software, ensuring it meets the specified requirements effectively.



**3.2 PROJECT STRUCTURE**

The structure of the HJSS software follows a modular design, consisting of several interconnected components:

**Main Class:** This serves as the entry point of the application. It instantiates the App singleton instance and starts the application. This is specifically implemented to accommodate code expansion in other to run multiple instances of the App class.

**App Class:** It represents the main application instance i.e. HJSS object. It follows the singleton design pattern to ensure that only one instance of the application is created. This class is designed to be open for expansion. In the case of a franchise or multiple instances of the application running concurrently, more instances of the App class can be created and run in the main class. Each instance can have its state and manage its own set of data independently from other instances, providing a scalable solution for managing multiple instances of the application.

**Model**: Contains the data structures of the application, including classes for learners, coaches, lessons, bookings, reviews, and a record class timeslot.

**Repository**: Repositories manage the business logic, persistence, and retrieval of data from storage as well as holding the storage. The software includes repositories for learners, coaches, lessons, bookings, and reviews.

**Menu**: Handles user interaction via the command line interface within the application, including menus for selecting learners, booking lessons, and viewing timetables.

**Enums:** Defines enumerations for representing grade levels, days of the week, timeslots, and ratings.

**Exceptions:** Contains custom exception classes to handle error conditions such as grade mismatches, duplicate bookings, and invalid age ranges.

**3.3 DESIGN PATTERNS**

The application follows several design patterns to manage its structure and functionality:

1. **Singleton Design Pattern:** The App class is implemented as a singleton to ensure that only a needed number of instances of the application are created with all its data, repository, etc. encapsulated in it.
1. **Template Design Pattern:** The repositories and menus within the application adhere to the template design pattern to facilitate uniformity and consistency in their implementations. A repository interface was defined to abstract common operations like data seeding and handling CRUD operations on the stored data entities**.** It is to be noted that all model classes have their respective repository class**.**  An abstract Menu class serves as the template for all menu classes in the application. It encapsulates common menu functionalities such as printing menu options, validating user input, and handling user interactions. Subclasses of Menu extend this template to implement menu-specific behavior while maintaining a consistent structure across all menus.
1. **Repository Pattern:** The Repository Pattern is a design pattern commonly used in Java applications to manage interactions between the domain model and the data access layer. In the context of the Hatfield Junior Swimming School (HJSS) application, the Repository Pattern serves to abstract the data access logic from the domain model, promoting a clean separation of concerns and facilitating efficient data management. The Repository Pattern isolates domain objects, such as learners, lessons, and bookings, from the complexities of CRUD operations and model object business logic. This isolation shields the model from the implementation details of data persistence mechanisms. By centralizing data access logic within repository classes, the Repository Pattern helps minimize the scattering and duplication of query code across the application. Each repository class encapsulates the logic for CRUD (Create, Read, Update, Delete) operations related to a specific model entity, ensuring consistency and reusability of data access.

   4. **UML CLASS DIAGRAM**

![](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.001.png)

**4.0 TESTS**

Junit tests were implemented to validate the functionality of repository classes. These classes hold the main business logic and operation of the application, which does not require user inputs. The purpose of these tests is to ensure that the repositories are working as expected and that any changes made to the code do not break the existing functionality. 

The test cases cover a range of scenarios such as booking creation, grade mismatch handling, duplicate bookings, and vacancy management. Five (5) test suites were created, one for each repository, and a total of 51 test cases have been written. All these test cases have been rigorously tested and have passed successfully. 

Additionally, a test coverage of 70% was achieved, which means that 70% of the code has been tested through these JUnit test cases. This ensures that we have a high level of confidence in the reliability and robustness of our codebase.

**5.0 REFACTORING**

During the development of the system, refactoring techniques were employed to improve code readability, maintainability, and performance. This included renaming variables and methods for clarity, removing code bad smells by extracting repeated code into reusable methods, and optimizing data structures and algorithms for efficiency. Some notable refactoring done during the development include:

1. Eliminated code bad smells in my **handleRegistration** App method by reducing the method length and implementing private methods to individually prompt users for data.

![A screen shot of a computer screen

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.002.png)

![A screen shot of a computer screen

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.003.png)

![A screen shot of a computer screen

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.004.png)

1. Refactoring was implemented to eliminate code redundancy in the process of reading user bookings and prompting the user to select a booking for action. This functionality has been consolidated into a single method within the App class. Subsequently, this method is invoked in three other instances where similar functionality is required. This refactoring eliminates code bad smells, enhances code maintainability, and reduces duplication, ensuring that changes made to this functionality are applied uniformly across the class.

![A screenshot of a computer program

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.005.png)







**6.0 VERSION CONTROL SNAPSHOTS**

Version control was managed using Git, with GitHub serving as the remote repository. Utilizing Git and GitHub allowed for efficient tracking of modifications, and easy integration of new features or fixes into the codebase. Additionally, it ensured code integrity and provided a backup of the project's entire history, enhancing overall project management and development workflow.

Here is also the link to my git repository: <https://github.com/Intuneteq/HJSS>

![A screenshot of a computer

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.006.png)![A screenshot of a computer

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.007.png)

![A screenshot of a computer

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.008.png)![A screenshot of a computer

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.009.png)![A screenshot of a computer

Description automatically generated](Aspose.Words.87a6ef31-d7d9-4fec-97bf-c33783533010.010.png)

**7.0 RUNNING THE APPLICATION**

***java -jar hatfieldJuniorSwimmingSchool.jar***

**8.0 CONCLUSION**

The design and implementation of the HJSS software adhere to the provided specifications while incorporating additional features and enhancements to improve usability and maintainability. By following best practices in software design and development, the HJSS system provides a robust and scalable solution for managing swimming lesson bookings effectively.
PSE\_CW\_20055695


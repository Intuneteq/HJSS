package com.hjss.tests;

import com.hjss.enums.Day;
import com.hjss.enums.Grade;
import com.hjss.enums.Time;
import com.hjss.model.Coach;
import com.hjss.model.Lesson;
import com.hjss.model.TimeSlot;
import com.hjss.repository.CoachRepository;
import com.hjss.repository.LessonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LessonRepositoryTest {
    private LessonRepository lessonRepository;
    private Lesson testLesson;

    @BeforeEach
    void setUp() {
        lessonRepository = new LessonRepository(new CoachRepository());
        testLesson = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Test Coach"));
    }

    @AfterEach
    void tearDown() {

        lessonRepository.removeAll();
    }

    @Test
    void seed() {
        // clear db;
        lessonRepository.removeAll();

        // run seed again
        lessonRepository.seed();

        List<Lesson> lessons = lessonRepository.read();

        // Lessons should be 44
        assertEquals(44, lessons.size());
    }

    @Test
    void testRead() {
        // clear db;
        lessonRepository.removeAll();

        Lesson lesson = lessonRepository.create(testLesson);

        List<Lesson> lessons = lessonRepository.read();

        assertEquals(1, lessons.size());

        assertEquals(lesson, lessons.getLast());
    }

    @Test
    void testReadByDay() {
        // clear db;
        lessonRepository.removeAll();

        Lesson lesson1 = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Test Coach"));
        Lesson lesson2 = new Lesson(Grade.TWO, new TimeSlot(Day.MONDAY, Time.FIVE), new Coach("Test Coach 2"));

        Lesson testLesson1 = lessonRepository.create(lesson1);
        Lesson testLesson2 = lessonRepository.create(lesson2);

        List<Lesson> lessons = lessonRepository.read(Day.MONDAY);

        // Assert two lessons are returned
        assertEquals(2, lessons.size());

        // Assert the first is testLesson1
        assertEquals(testLesson1, lessons.getFirst());

        // Assert the second is testLesson2
        assertEquals(testLesson2, lessons.getLast());

        // Assert their days is monday
        assertEquals(Day.MONDAY, lessons.getFirst().getTimeSlot().day());
        assertEquals(Day.MONDAY, lessons.getLast().getTimeSlot().day());
    }

    @Test
    void testReadByGrade() {
        // clear db;
        lessonRepository.removeAll();

        Lesson lesson1 = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Test Coach"));
        Lesson lesson2 = new Lesson(Grade.FOUR, new TimeSlot(Day.WEDNESDAY, Time.FIVE), new Coach("Test Coach 2"));

        Lesson testLesson1 = lessonRepository.create(lesson1);
        Lesson testLesson2 = lessonRepository.create(lesson2);

        List<Lesson> lessons = lessonRepository.read(Grade.FOUR);

        // Assert two lessons are returned
        assertEquals(2, lessons.size());

        // Assert the first is testLesson1
        assertEquals(testLesson1, lessons.getFirst());

        // Assert the second is testLesson2
        assertEquals(testLesson2, lessons.getLast());

        // Assert their grades is Four
        assertEquals(Grade.FOUR, lessons.getFirst().getGrade());
        assertEquals(Grade.FOUR, lessons.getLast().getGrade());
    }

    @Test
    void testReadByCoach() {
        // clear db;
        lessonRepository.removeAll();

        Coach coach = new Coach("tester");

        Lesson lesson1 = new Lesson(Grade.FOUR, new TimeSlot(Day.MONDAY, Time.FOUR), coach);
        Lesson lesson2 = new Lesson(Grade.FIVE, new TimeSlot(Day.SATURDAY, Time.TWO), coach);

        Lesson testLesson1 = lessonRepository.create(lesson1);
        Lesson testLesson2 = lessonRepository.create(lesson2);

        List<Lesson> lessons = lessonRepository.read(coach);

        // Assert two lessons are returned
        assertEquals(2, lessons.size());

        // Assert the first is testLesson1
        assertEquals(testLesson1, lessons.getFirst());

        // Assert the second is testLesson2
        assertEquals(testLesson2, lessons.getLast());

        // Assert their coach is the coach object
        assertEquals(coach, lessons.getFirst().getCoach());
        assertEquals(coach, lessons.getLast().getCoach());
    }

    @Test
    void testReadById() {
        Lesson testLesson1 = lessonRepository.create(testLesson);

        Lesson lesson = lessonRepository.readById(testLesson1.getId());

        assertNotNull(lesson);

        assertEquals(testLesson1, lesson);
    }

    @Test
    void testCreate() {
        Lesson lesson = lessonRepository.create(testLesson);

        List<Lesson> lessons = lessonRepository.read();

        // Assert a lesson is returned
        assertNotNull(lesson);

        // Assert returned lesson is the lesson created.
        assertEquals(testLesson, lesson);

        // Assert the lesson is added to the lesson db
        assertEquals(testLesson, lessons.getLast());
    }

    @Test
    void testCreateLessons() {
        lessonRepository.removeAll();

        try {
            // Get private method createTimeSlot
            Method createTimeSlots = LessonRepository.class.getDeclaredMethod("createTimeSlots");

            // Make it accessible
            createTimeSlots.setAccessible(true);

            // Invoke the method
            createTimeSlots.invoke(lessonRepository);

            // Get private method to create lessons
            Method createLessons = LessonRepository.class.getDeclaredMethod("createLessons");

            // Make it accessible
            createLessons.setAccessible(true);

            // Invoke the private method
            createLessons.invoke(lessonRepository);

            // Assert that the lessons have been created and added to the lesson repository
            List<Lesson> lessons = lessonRepository.read();
            assertNotNull(lessons);

            // Assert there are 44 lessons created in total
            assertEquals(44, lessons.size());


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Unexpected Error occurred when creating timeSlots test " + e.getMessage());
        }
    }

    @Test
    void testShowTimeTable() {
        lessonRepository.removeAll();
        try {
            Field lessonCount = Lesson.class.getDeclaredField("count");
            lessonCount.setAccessible(true);

            // Reset Lesson count
            lessonCount.setInt("count", 0);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Unexpected Error occurred when showing the timetable test " + e.getMessage());
        }

        Lesson lesson1 = new Lesson(Grade.ONE, new TimeSlot(Day.MONDAY, Time.FOUR), new Coach("Coach 1"));
        Lesson lesson2 = new Lesson(Grade.ONE, new TimeSlot(Day.MONDAY, Time.FIVE), new Coach("coach 2"));
        Lesson lesson3 = new Lesson(Grade.TWO, new TimeSlot(Day.MONDAY, Time.SIX), new Coach("Coach 3"));

        lessonRepository.create(lesson1);
        lessonRepository.create(lesson2);
        lessonRepository.create(lesson3);

        String expectedOutput = "Week 1:\n" +
                lesson1 + "\n\n" + lesson2 + "\n\n" + lesson3 + "\n\n";

        String result = lessonRepository.showTimeTable(lessonRepository.read());
        System.out.println(result);
        assertEquals(expectedOutput, result);
    }
}
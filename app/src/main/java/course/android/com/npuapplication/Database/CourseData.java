package course.android.com.npuapplication.Database;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bansari on 10/9/2017.
 */

public class CourseData {
    private DataSnapshot allCourseInfo;
    private DataSnapshot selectedCourseInfo;

    public DataSnapshot getAllCourseInfo() {
        return allCourseInfo;
    }

    public void setAllCourseInfo(DataSnapshot allCourseInfo) {
        this.allCourseInfo = allCourseInfo;
    }

    public DataSnapshot getSelectedCourseInfo() {
        return selectedCourseInfo;
    }

    public void setSelectedCourseInfo(DataSnapshot selectedCourseInfo) {
        this.selectedCourseInfo = selectedCourseInfo;
    }

    //return all courses info (entire course list)
    public DataSnapshot fetchAllCourseData(DataSnapshot dataSnapshot) {
        allCourseInfo = dataSnapshot.child("Course");
        return allCourseInfo;
    }

    //return specific course info
    public DataSnapshot fetchSelectedCourseInfo(String courseId) {
        selectedCourseInfo = allCourseInfo.child(courseId);
        return selectedCourseInfo;
    }

    //return credits of specific course (courseId as an argument)
    public String fetchCourseCredits(String courseId) {
        return String.valueOf(fetchSelectedCourseInfo(courseId).child("Credits").getValue());
    }

    //return Credits of specific course (if we already have data of specific course, then no need to specify courseId in argument)
    public String fetchCourseCredits() {
        return String.valueOf(selectedCourseInfo.child("Credits").getValue());
    }

    //return course name of specific course (courseId as an argument)
    public String fetchCourseName(String courseId) {
        return String.valueOf(fetchSelectedCourseInfo(courseId).child("Name").getValue());
    }

    //return course name of specific course (if we already have data of specific course, then no need to specify courseId in argument)
    public String fetchCourseName() {
        return String.valueOf(selectedCourseInfo.child("Name").getValue());
    }

    public HashMap<String, String> fetchCourseDetailsForCourseDetailsPage() {
        HashMap<String, String> courseDetailsStrArray = new HashMap<>();
        courseDetailsStrArray.put("Contact Hours", String.valueOf(selectedCourseInfo.child("Contact Hours").getValue()));
        courseDetailsStrArray.put("Credits", String.valueOf(selectedCourseInfo.child("Credits").getValue()));
        courseDetailsStrArray.put("Load", String.valueOf(selectedCourseInfo.child("Load").getValue()));
        courseDetailsStrArray.put("Location", String.valueOf(selectedCourseInfo.child("Location").getValue()));
        courseDetailsStrArray.put("On-line", String.valueOf(selectedCourseInfo.child("On-line").getValue()));
        courseDetailsStrArray.put("Prerequisite", String.valueOf(selectedCourseInfo.child("Prerequisite").getValue()));
        courseDetailsStrArray.put("Schedule", String.valueOf(selectedCourseInfo.child("Schedule").getValue()));

        return courseDetailsStrArray;
    }

    public static List<String> getChildKeys(DataSnapshot parent) {
        List<String> childKeys = new ArrayList<String>();
        if (parent.exists()) {
            for(DataSnapshot d : parent.getChildren()) {
                childKeys.add(d.getKey());
            }
        }
        return childKeys;
    }

    public HashMap<String, List<String>> fetchCourseDetailsForSyllabusPage() {
        HashMap<String, List<String>> syllabusDetails = new HashMap<>();

        DataSnapshot weeklyInformation = selectedCourseInfo.child("Weekly Information");
        List<String> weeks = getChildKeys(weeklyInformation);
        syllabusDetails.put("Weekly Information", weeks);

        DataSnapshot textBook = selectedCourseInfo.child("Textbook");
        List<String> textBookInformation = new ArrayList<String>();

        syllabusDetails.put("Textbook", textBookInformation);

        DataSnapshot refTextBook = selectedCourseInfo.child("Reference Book");
        List<String> referenceBookInformation = getChildKeys(refTextBook);
        syllabusDetails.put("Reference Book", referenceBookInformation);
        return syllabusDetails;
    }

    public HashMap<String, String> fetchTextBookData(String bookName) {
        HashMap<String, String> textBookDetails = new HashMap<>();
        DataSnapshot refTextBook;
        if (bookName.equals("Textbook")) {
            refTextBook = selectedCourseInfo.child("Textbook");
        } else {
            DataSnapshot refTextBooks = selectedCourseInfo.child("Reference Book");
            refTextBook = refTextBooks.child(bookName);
        }
        textBookDetails.put("Author", String.valueOf(refTextBook.child("Author").getValue()));
        textBookDetails.put("ISBN", String.valueOf(refTextBook.child("ISBN").getValue()));
        textBookDetails.put("Publisher", String.valueOf(refTextBook.child("Publisher").getValue()));
        textBookDetails.put("Title", String.valueOf(refTextBook.child("Title").getValue()));
        textBookDetails.put("Url", String.valueOf(refTextBook.child("Url").getValue()));

        return textBookDetails;
    }

    public HashMap<String, String> fetchWeeklyData(String weekName) {
        HashMap<String, String> weekDetails = new HashMap<>();

        DataSnapshot weekInfo = selectedCourseInfo.child("Weekly Information");
        DataSnapshot weekNum = weekInfo.child(weekName);
        weekDetails.put("Assignments", String.valueOf(weekNum.child("Assignments").getValue()));
        weekDetails.put("Instructive Coverage & Activities", String.valueOf(weekNum.child("Instructive Coverage & Activities").getValue()));
        weekDetails.put("Objectives", String.valueOf(weekNum.child("Objectives").getValue()));
        weekDetails.put("Others", String.valueOf(weekNum.child("Others").getValue()));

        return weekDetails;
    }
}

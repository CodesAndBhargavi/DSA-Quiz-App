# PROJECT REPORT: DSA QUIZ APPLICATION

**Program:** Android App Development + Java Training Program  
**Organization:** InternsElite  
**Track:** Minor Project (Option 4: Quiz App)  
**Submission Date:** September 2026  
**Developer:** Bhargavi Digu Naik  

---

## 1. Project Title
**DSA Quiz — Interactive Data Structures & Algorithms Quiz Application**

---

## 2. Project Objective
The objective of this project is to develop a native, interactive Android application in Java that helps students and developers test, practice, and sharpen their Data Structures and Algorithms (DSA) knowledge. The app provides a timed quiz environment, instant solution feedback, scoring metrics, answer review capabilities, and local persistence for session tracking and leaderboards.

---

## 3. Project Description
**DSA Quiz** is a native Android application built with a modern, pastel-themed visual style. It allows learners to select specific DSA topics (such as Arrays, Linked Lists, Stacks & Queues, Trees, Graphs, Sorting, Dynamic Programming, and Time Complexity) and choose appropriate difficulty levels (Basic, Medium, Hard). 

The app features a 30-second per-question countdown timer, a 50:50 lifeline feature, instant answer checking with color-coded feedback and technical explanations, detailed session scoring, a question review screen, a local leaderboard podium, and lifetime progress statistics.

---

## 4. Features Implemented
- **Multi-Topic DSA Selection:** Support for 8 core DSA domains (Arrays, LinkedLists, Stacks/Queues, Trees, Graphs, Sorting, DP, Time Complexity).
- **Difficulty Grading:** Basic, Medium, and Hard difficulty levels.
- **Timed Quiz Challenge Mode:** Per-question 30-second countdown timer with animated progress bar and automatic timeout handling.
- **Untimed Practice Mode:** Stress-free study mode for conceptual understanding without time constraints.
- **50:50 Lifeline:** Eliminates two incorrect options to assist the user.
- **Instant Solution & Explanation:** Color-coded card feedback (Green for Correct, Red for Incorrect) with in-depth technical explanations.
- **No-Repeat Question Logic:** Ensures questions within an active session are not repeated.
- **Result & Performance Summary:** Displays score, accuracy percentage, time taken, streak counter, and achievement badges (Grandmaster 🏆, Great Performance 🥈, Keep Practicing 📚).
- **Review Answers Screen:** Enables users to examine all questions, their chosen answers vs correct answers, and corresponding explanations.
- **Top 3 Leaderboard Podium & Ranking:** Displays top scores with gold, silver, and bronze podium cards, highlighting the user's current standing.
- **Statistics & Mastery Progress:** Tracks total points, quizzes completed, daily streak, and topic mastery meters.
- **DSA Study Cheat Sheet:** Quick reference guides for key algorithms, time complexities, and properties.
- **Settings & Reset Progress:** Allows clearing local database history and resetting performance scores.

---

## 5. Technologies Used
- **Language:** Java (JDK 11 / 17)
- **IDE:** Android Studio (Ladybug / Koala)
- **Local Database:** Room Database (SQLite ORM)
- **UI Framework:** Android Material Components (MaterialCardView, ChipGroup, ShapeDrawables)
- **Version Control:** Git & GitHub

---

## 6. Android Components Used
- **`Activity`**: Multiple structured screens (`SplashActivity`, `HomeActivity`, `TopicSelectionActivity`, `QuizActivity`, `ResultActivity`, `ReviewAnswersActivity`, `LeaderboardActivity`, `StatsActivity`, `StudyActivity`, `SettingsActivity`).
- **`Intent`**: Passing topic parameters, difficulty levels, scores, and serialized question lists between activities.
- **`RecyclerView` & `Adapter`**: Dynamic display of leaderboard rankings and answer review items (`LeaderboardAdapter`, `ReviewAnswersAdapter`).
- **`Room Database (Entity, DAO, Database)`**: Persistent storage of DSA questions, user quiz history, and leaderboard records.
- **`SharedPreferences`**: Persistent storage of streaks, total stars/points, user profile name, and rotating tip index.
- **`CountDownTimer`**: Real-time timer for question countdowns.
- **`MaterialCardView` & `LinearLayout` / `ConstraintLayout`**: Clean, responsive pastel card design with rounded corners.
- **`AlertDialog`**: Confirmation dialogs for quitting ongoing quizzes and resetting user progress.

---

## 7. Project Architecture & Structure

```
com.example.quizapp
 ├── activities/
 │    ├── SplashActivity.java          (App launcher & background DB seeder)
 │    ├── HomeActivity.java            (Dashboard with greeting, stats, 2x2 grid)
 │    ├── TopicSelectionActivity.java  (Topic chips & difficulty cards)
 │    ├── QuizActivity.java            (Quiz engine, timer, lifeline, scoring)
 │    ├── ResultActivity.java          (Score summary, accuracy, badges)
 │    ├── ReviewAnswersActivity.java   (Detailed question-by-question review)
 │    ├── LeaderboardActivity.java     (Podium + scrollable rankings)
 │    ├── StatsActivity.java           (Overall statistics & topic mastery)
 │    ├── StudyActivity.java           (DSA cheat sheet notes)
 │    └── SettingsActivity.java        (Preferences & reset data)
 ├── adapters/
 │    ├── LeaderboardAdapter.java      (RecyclerView adapter for leaderboard)
 │    └── ReviewAnswersAdapter.java    (RecyclerView adapter for answer review)
 ├── database/
 │    ├── AppDatabase.java             (Room database singleton)
 │    ├── QuestionDao.java             (Data access object for questions)
 │    ├── HistoryDao.java              (Data access object for quiz history)
 │    ├── LeaderboardDao.java          (Data access object for leaderboard)
 │    └── DataSeeder.java              (Seed question bank with 25+ DSA MCQs)
 ├── models/
 │    ├── Question.java                (Question entity)
 │    ├── QuizHistory.java             (Quiz session record entity)
 │    ├── LeaderboardEntry.java        (Leaderboard entry entity)
 │    └── QuizSessionItem.java         (Session review item model)
 └── utils/
      ├── DsaTips.java                 (Daily algorithm tips collection)
      └── PreferenceManager.java       (SharedPreferences manager)
```

---

## 8. Project Setup Instructions
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/DSA-Quiz-App.git
   ```
2. **Open in Android Studio:**
   - Open Android Studio.
   - Click **File > Open** and select the cloned project folder.
3. **Gradle Sync & Build:**
   - Allow Gradle to sync all dependencies (`Room`, `Material Components`, `ConstraintLayout`).
4. **Run Application:**
   - Connect an Android device with USB Debugging enabled or launch an Android Virtual Device (Pixel 7 / Android 11+ recommended).
   - Click the green **Run (▶)** button or press `Shift + F10`.

---

## 9. Screenshots of the Working Application
*(Insert your screenshots here)*

1. **Splash Screen & Home Dashboard:** Displays time-based greeting, user statistics (Stars, Accuracy %, Sessions), and pastel cards for Study, Practice, Quiz, and Stats.
2. **Topic & Difficulty Selection:** Interactive topic chips (Arrays, Trees, DP) and difficulty selection cards.
3. **Live Quiz with Countdown Timer:** Question card with 4 option pill buttons, active timer (`⏱ 00:25`), streak counter, and 50:50 lifeline.
4. **Instant Solution Explanation:** Detailed green/red feedback banner showing the technical explanation upon answering.
5. **Result Summary & Answer Review:** Final score breakdown, accuracy percentage, points earned, and question-by-question answer review list.
6. **Leaderboard & Statistics:** Top 3 podium with Gold, Silver, and Bronze badges along with lifetime topic mastery meters.

---

## 10. Challenges Faced & Solutions
1. **Preventing Question Repetition:**  
   *Challenge:* Ensuring questions were not repeated within a single quiz session.  
   *Solution:* Implemented in-memory `Set<Integer> usedQuestionIds` combined with Room queries to filter out previously seen IDs in the session.
2. **Smooth State & Timer Management:**  
   *Challenge:* Handling countdown timer cancellation on fast answer submission or app pause without leaking memory.  
   *Solution:* Managed `CountDownTimer` lifecycle hooks in `onDestroy()` and explicitly cancelled the timer when checking answers.
3. **Dynamic Explanation Card Animation:**  
   *Challenge:* Displaying technical explanations dynamically with appropriate coloring without cluttering the screen.  
   *Solution:* Integrated a `MaterialCardView` that toggles visibility post-submission and dynamically modifies background tint and stroke colors.

---

## 11. Key Learning Outcomes
- Deepened understanding of native Android development using **Java**.
- Gained hands-on proficiency with **Room Database (SQLite ORM)** for offline data persistence.
- Mastered **RecyclerView** and custom adapters for complex, dynamic lists.
- Learned clean UI/UX design principles utilizing pastel color schemes, custom shape drawables, and Material Design guidelines.
- Practiced professional Git version control workflows and modular code organization.

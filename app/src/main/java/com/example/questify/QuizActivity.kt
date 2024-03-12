package com.example.questify;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.toColor;
import com.example.questify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

import java.util.ArrayList

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)

class QuizActivity : AppCompatActivity() {

    lateinit var topicName: String
    private lateinit var questionText: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var nextButton: Button
    private lateinit var optionText: List<TextView>
    private lateinit var optionButtonIcons: List<ImageView>

    private var selectedOption = -1
    private var hasAnsweredCorrectly = false

    private var currentQuestionIndex = 0
    private var score = 0

    // Removed pre-populated question lists
    // private val scienceQuestions = ...
    // private val historyQuestions = ...
    // private val genKQuestions = ...
    // private val techQuestions = ...

    private lateinit var questions: MutableList<Question>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Initialize Firebase
        Firebase.initialize(this)

        // Get Firebase Database reference
        database = FirebaseDatabase.getInstance().reference

        topicName = intent.getStringExtra("topic") ?: "science"

        questions = ArrayList() // Initialize empty question list

        // Fetch questions based on topic from Firebase Realtime Database
        fetchQuestionsFromFirebase(topicName)

        // Initialize views
        questionText = findViewById(R.id.questionText)
        optionButtons = listOf(
            findViewById(R.id.optABtn),
            findViewById(R.id.optBBtn),
            findViewById(R.id.optCBtn),
            findViewById(R.id.optDBtn)
        )
        optionText = listOf(
            findViewById(R.id.optionAText),
            findViewById(R.id.optionBText),
            findViewById(R.id.optionCText),
            findViewById(R.id.optionDText)
        )

        optionButtonIcons = listOf(
            findViewById(R.id.ABtnIcon),
            findViewById(R.id.BBtnIcon),
            findViewById(R.id.CBtnIcon),
            findViewById(R.id.DBtnIcon)
        )

        nextButton = findViewById(R.id.nextBtn)

        // ... (rest of your code remains the same)
    }

    private fun fetchQuestionsFromFirebase(chosenTopic: String) {
        val questionsRef = database.child("quizzes").child(chosenTopic)

        questionsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                questions.clear() // Clear existing questions before fetching new ones

                for (subjectSnapshot in dataSnapshot.children) {
                    try {
                        val questionMap = subjectSnapshot.value as HashMap<*, *>
                        val text = questionMap["question"] as String
                        val options = questionMap["options"] as List<String>
                        val correctAnswerIndex = questionMap["answerIndex"] as Long

                        // Ensure data types are correct before adding to list
                        questions.add(Question(text, options, correctAnswerIndex.toInt()))
                    } catch (e: Exception) {
                        Log.e("QuizActivity", "Error parsing question data: $e")
                        // Handle potential data parsing errors (optional)
                    }
                }
                Log.d("QuizActivity","$questions")
                // Now you have the retrieved questions in the 'questions' list
                // You can proceed with displaying the first question (or perform other actions)
                displayQuestion(currentQuestionIndex)

                nextButton.setOnClickListener {
                    checkAnswer()  // Removed the argument (i)
                    currentQuestionIndex++
                    if (currentQuestionIndex < questions.size) {
                        displayQuestion(currentQuestionIndex)
                    } else {
                        // All questions answered, show results
                        showResults()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("QuizActivity", "Failed to read questions from Firebase", error.toException())
                // Handle database read errors (optional)
            }
        })
    }


    private fun displayQuestion(index: Int) {
        hasAnsweredCorrectly = false;
        changeIcon(-1);
        val question = questions[index];
        questionText.text = question.text;
        optionText.forEachIndexed { i, textView ->
            textView.text = question.options[i];
            optionButtons[i].setOnClickListener { checkAnswer(i); }
        }
    }


    private fun checkAnswer(newSelectedOption: Int = -1) {
        if (selectedOption != newSelectedOption) {
            selectedOption = newSelectedOption;
            val question = questions[currentQuestionIndex];
            val correctAnswer = question.correctAnswer;

            if (selectedOption == correctAnswer && !hasAnsweredCorrectly) {
                score++;
                hasAnsweredCorrectly = true;  // Set flag to prevent multiple score increments
                changeIcon(selectedOption);
                //optionButtons[selectedOption].setBackgroundColor(selectedOptionColor);
                Log.d(
                    "QuizActivity",
                    "Correct answer!  hasanc: $hasAnsweredCorrectly . score: $score"
                );
            } else {
                // Provide visual feedback for incorrect answer (e.g., shake option)
                changeIcon(selectedOption);
                Log.d(
                    "QuizActivity",
                    "Incorrect answer. Correct answer was option ${correctAnswer + 1}. hasanc: $hasAnsweredCorrectly . score: $score"
                );
            }
        }
    }

    private fun changeIcon(currentSelectedOption: Int = -1) {
        for (i in 0..3) {
            if (i == currentSelectedOption) {
                optionButtonIcons[i].setImageResource(R.drawable.checkfilled);
            } else {
                optionButtonIcons[i].setImageResource((R.drawable.checkempty));
            }
        }
    }

    private fun showResults() {
        // Start a new activity to display the final score
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("totalQuestions", questions.size)
        startActivity(intent)
    }
}